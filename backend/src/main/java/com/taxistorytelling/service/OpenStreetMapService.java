package com.taxistorytelling.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OpenStreetMapService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getLandmarksAlongRoute(String pickup, String dropoff) {
        String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + pickup + " to " + dropoff;
        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            StringBuilder landmarks = new StringBuilder();
            for (JsonNode node : root) {
                landmarks.append(node.path("display_name").asText()).append(", ");
            }
            return landmarks.toString();
        } catch (Exception e) {
            return "No landmarks found along the route.";
        }
    }
}
