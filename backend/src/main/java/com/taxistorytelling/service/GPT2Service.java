package com.taxistorytelling.service;

import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.MalformedModelException;
import ai.djl.translate.TranslateException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GPT2Service {

    private ZooModel<String, String> model;

    public GPT2Service() throws IOException, ModelNotFoundException, MalformedModelException {
        try {
            // Load GPT-2 model
            Criteria<String, String> criteria = Criteria.builder()
                    .setTypes(String.class, String.class)
                    .optModelUrls("djl://ai.djl.huggingface.pytorch/gpt2")
                    .build();
            this.model = criteria.loadModel();
            System.out.println("GPT-2 model loaded successfully.");
        } catch (ModelNotFoundException e) {
            System.err.println("Failed to load GPT-2 model: " + e.getMessage());
            throw e; // Re-throw the exception to fail fast
        }
    }

    public String generateStory(String prompt) {
        if (model == null) {
            return "Model is not loaded. Please check the model configuration.";
        }

        try (Predictor<String, String> predictor = model.newPredictor()) {
            return predictor.predict(prompt);
        } catch (TranslateException e) {
            e.printStackTrace();
            return "Failed to generate story due to translation error.";
        } catch (Exception e) {
            e.printStackTrace();
            return "An unexpected error occurred while generating the story.";
        }
    }
}
