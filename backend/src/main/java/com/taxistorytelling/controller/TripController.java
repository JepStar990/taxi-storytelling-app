package com.taxistorytelling.controller;

import com.taxistorytelling.model.Trip;
import com.taxistorytelling.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @GetMapping
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping("/{id}")
    public Optional<Trip> getTripById(@PathVariable Long id) {
        return tripService.getTripById(id);
    }

    @GetMapping("/{id}/narrative")
    public String getTripNarrative(@PathVariable Long id) {
        Optional<Trip> trip = tripService.getTripById(id);
        return trip.map(tripService::generateNarrative).orElse("Trip not found.");
    }
}
