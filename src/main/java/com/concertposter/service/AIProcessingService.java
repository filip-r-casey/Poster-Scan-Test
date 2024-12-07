package com.concertposter.service;

import com.concertposter.model.PosterData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.*;

import java.io.IOException;

public class AIProcessingService {

    private final String apiKey;
    private final OkHttpClient client;
    private final PayloadFactory payloadFactory;

    public AIProcessingService(String apiKey, PayloadFactory payloadFactory) {
        this.apiKey = apiKey;
        this.payloadFactory = payloadFactory;
        this.client = new OkHttpClient();
    }

    public PosterData processImage(String base64Image) {
        String payload = payloadFactory.createPayload(base64Image);
        RequestBody body = RequestBody.create(payload, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Error response from API: " + response.code());
                System.err.println("Response body: " + response.body().string());
                return null;
            }

            String jsonResponse = response.body().string();
            System.out.println("API response: " + jsonResponse);
            return parseResponse(jsonResponse);
        } catch (IOException e) {
            System.err.println("Error communicating with the API: " + e.getMessage());
            return null;
        }
    }

    private PosterData parseResponse(String jsonResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);

            // Navigate to the "content" field
            JsonNode choicesNode = root.get("choices");
            if (choicesNode != null && choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode contentNode = choicesNode.get(0).get("message").get("content");
                if (contentNode != null) {
                    String contentJson = contentNode.asText();

                    // Remove surrounding Markdown-style markers (```json\n ... ```)
                    contentJson = cleanJsonString(contentJson);

                    // Parse content as PosterData
                    return mapper.readValue(contentJson, PosterData.class);
                }
            }

            System.err.println("No valid content found in API response.");
            return null;
        } catch (IOException e) {
            System.err.println("Error parsing API response: " + e.getMessage());
            return null;
        }
    }

    private String cleanJsonString(String jsonString) {
        // Remove ```json\n prefix and ``` suffix if present
        if (jsonString.startsWith("```json")) {
            jsonString = jsonString.substring(7); // Remove "```json\n"
        }
        if (jsonString.endsWith("```")) {
            jsonString = jsonString.substring(0, jsonString.length() - 3); // Remove "```"
        }
        return jsonString.trim();
    }
}