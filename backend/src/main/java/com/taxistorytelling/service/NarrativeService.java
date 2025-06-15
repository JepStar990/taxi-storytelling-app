package com.taxistorytelling.service;

import com.taxistorytelling.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NarrativeService {

    @Autowired
    private WikipediaService wikipediaService;

    @Autowired
    private OpenStreetMapService openStreetMapService;

    @Autowired
    private GPT2Service gpt2Service;

    public String generateNarrative(Trip trip) {
        String pickup = trip.getPickupLocation();
        String dropoff = trip.getDropoffLocation();
        double distance = trip.getTripDistance();
        double fare = trip.getFareAmount();

        // Fetch fun facts and landmarks
        String pickupFact = wikipediaService.getFunFact(pickup);
        String dropoffFact = wikipediaService.getFunFact(dropoff);
        String landmarks = openStreetMapService.getLandmarksAlongRoute(pickup, dropoff);

        // Create a prompt for GPT-2
        String prompt = String.format(
            "Write a creative story about a taxi ride from %s to %s. " +
            "The trip was %.2f miles long and cost $%.2f. " +
            "Along the way, the passenger saw %s. " +
            "Here are some fun facts: %s and %s.",
            pickup, dropoff, distance, fare, landmarks, pickupFact, dropoffFact
        );

        // Generate the story using GPT-2
        return gpt2Service.generateStory(prompt);
    }
}
