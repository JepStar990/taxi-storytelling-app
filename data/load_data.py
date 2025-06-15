import pandas as pd
from sqlalchemy import create_engine
import os

# Database connection details
DATABASE_URL = "postgresql://admin:tyN2Vmnr9rAc9NpOv9hzys8P4s7PPqDP@dpg-cv1ql79u0jms738ndeo0-a.oregon-postgres.render.com:5432/nyc_taxi_db"

# Path to the data directory
DATA_DIR = "/mnt/c/Users/Ryomen/Stuff/taxi-storytelling-app/data/"

# List of Parquet files
parquet_files = [
    "fhvhv_tripdata_2024-12.parquet",
    "green_tripdata_2024-12.parquet",
    "yellow_tripdata_2024-12.parquet"
]

# Create a SQLAlchemy engine
engine = create_engine(DATABASE_URL)

# Function to load data from a Parquet file
def load_parquet_to_db(file_path, table_name):
    print(f"Loading data from {file_path}...")
    df = pd.read_parquet(file_path)

    # Handle different file types
    if "fhv" in file_path or "fhvhv" in file_path:
        # For FHV and FHVHV data
        df.rename(columns={
            "pickup_datetime": "pickup_datetime",
            "dropoff_datetime": "dropoff_datetime",
            "PULocationID": "pickup_location_id",
            "DOLocationID": "dropoff_location_id"
        }, inplace=True)
        # Add missing columns with default values
        df["trip_distance"] = 0  # FHV data doesn't have trip distance
        df["fare_amount"] = 0    # FHV data doesn't have fare amount
        df["passenger_count"] = 0  # FHV data doesn't have passenger count

    elif "green" in file_path:
        # For Green Taxi data
        df.rename(columns={
            "lpep_pickup_datetime": "pickup_datetime",
            "lpep_dropoff_datetime": "dropoff_datetime",
            "PULocationID": "pickup_location_id",
            "DOLocationID": "dropoff_location_id",
            "trip_distance": "trip_distance",
            "fare_amount": "fare_amount",
            "passenger_count": "passenger_count"
        }, inplace=True)

    elif "yellow" in file_path:
        # For Yellow Taxi data
        df.rename(columns={
            "tpep_pickup_datetime": "pickup_datetime",
            "tpep_dropoff_datetime": "dropoff_datetime",
            "PULocationID": "pickup_location_id",
            "DOLocationID": "dropoff_location_id",
            "trip_distance": "trip_distance",
            "fare_amount": "fare_amount",
            "passenger_count": "passenger_count"
        }, inplace=True)

    # Select relevant columns
    columns_to_select = [
        "pickup_datetime", "dropoff_datetime", "pickup_location_id",
        "dropoff_location_id", "trip_distance", "fare_amount", "passenger_count"
    ]
    df = df[[col for col in columns_to_select if col in df.columns]]

    # Insert data into the database
    df.to_sql(table_name, engine, if_exists="append", index=False)
    print(f"Data from {file_path} loaded into {table_name} table.")

# Load data from all Parquet files
for file in parquet_files:
    file_path = os.path.join(DATA_DIR, file)
    load_parquet_to_db(file_path, "taxi_trips")

print("Data ingestion complete!")
