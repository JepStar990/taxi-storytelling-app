package com.taxistorytelling.service;

import com.taxistorytelling.model.Trip;
import com.taxistorytelling.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private NarrativeService narrativeService;

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Optional<Trip> getTripById(Long id) {
        return tripRepository.findById(id);
    }

    public String generateNarrative(Trip trip) {
        return narrativeService.generateNarrative(trip);
    }
}
