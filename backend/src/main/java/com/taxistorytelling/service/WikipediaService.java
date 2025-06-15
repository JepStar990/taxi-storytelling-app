package com.taxistorytelling.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WikipediaService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getFunFact(String location) {
        String url = "https://en.wikipedia.org/api/rest_v1/page/summary/" + location.replace(" ", "_");
        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            return root.path("extract").asText();
        } catch (Exception e) {
            return "No fun facts available for " + location + ".";
        }
    }
}
