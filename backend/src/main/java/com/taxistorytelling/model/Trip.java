package com.taxistorytelling.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "taxi_trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime pickupDateTime;
    private LocalDateTime dropoffDateTime;
    private int passengerCount;
    private double tripDistance;
    private double fareAmount;
    private String pickupLocation;
    private String dropoffLocation;
}
