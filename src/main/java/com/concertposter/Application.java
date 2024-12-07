package com.concertposter;

import com.concertposter.config.AppConfig;
import com.concertposter.controller.PosterController;

public class Application {
    public static void main(String[] args) {
        // Load configurations
        AppConfig config = AppConfig.getInstance();
        config.setOpenApiKey(System.getenv("OPENAI_API_KEY"));
        config.setImageDirectory("images/");
        config.setOutputJsonFile("output/data.json");
        config.setInstagramPageUrl("https://www.instagram.com/d3artswestwood/");
        config.setImageLimit(10);

        // Initialize controller and execute the workflow
        PosterController controller = new PosterController(config);
        controller.processPosters();
    }
}