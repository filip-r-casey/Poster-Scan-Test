package com.concertposter.controller;

import com.concertposter.config.AppConfig;
import com.concertposter.model.PosterData;
import com.concertposter.service.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PosterController {
    private final AppConfig config;
    private final FileService fileService;
    private final ImageService imageService;
    private final AIProcessingService aiProcessingService;

    public PosterController(AppConfig config) {
        this.config = config;
        this.fileService = new FileService();
        this.imageService = new ImageService();
        PayloadFactory payloadFactory = new PosterPayloadFactory();
        this.aiProcessingService = new AIProcessingService(config.getOpenAiApiKey(), payloadFactory);
    }

    public void processPosters() {
        try {
            System.out.println("Starting poster processing...");

            System.out.println("Step 1: Downloading images...");
            fileService.downloadImages(config.getInstagramPageUrl(), config.getImageDirectory(), config.getImageLimit());
            fileService.scrapeImages("https://expressobeans.com/public/hottoday.php");

            System.out.println("Step 2: Flattening and filtering images...");
            List<File> filteredImages = fileService.flattenAndFilterImages(config.getImageDirectory());

            System.out.println("Step 3: Checking and resizing images...");
            List<File> resizedImages = new ArrayList<>();
            for (File image : filteredImages) {
                if (imageService.isAlreadyResized(image)) {
                    System.out.println("Image already resized: " + image.getName());
                    resizedImages.add(image);
                } else {
                    try {
                        File resizedImage = imageService.resizeImage(image, 150, 150);
                        resizedImages.add(resizedImage);
                        System.out.println("Resized image: " + resizedImage.getName());
                    } catch (Exception e) {
                        System.err.println("Failed to resize image: " + image.getName() + ". Skipping.");
                    }
                }
            }

            if (resizedImages.isEmpty()) {
                System.out.println("No resized images available for processing. Exiting.");
                return;
            }

            System.out.println("Step 4: Encoding resized images and sending to AI API...");
            List<PosterData> results = new ArrayList<>();
            for (File resizedImage : resizedImages) {
                try {
                    String encodedImage = imageService.encodeToBase64(resizedImage);
                    PosterData data = aiProcessingService.processImage(encodedImage);
                    if (data != null) {
                        results.add(data);
                        System.out.println("Received data for image: " + resizedImage.getName());
                    } else {
                        System.err.println("No data received for image: " + resizedImage.getName());
                    }
                } catch (Exception e) {
                    System.err.println("Error processing resized image: " + resizedImage.getName() + ". Skipping.");
                }
            }

            System.out.println("Step 5: Saving results...");
            fileService.saveResultsAsJson(results, config.getOutputJsonFile());

            System.out.println("Poster processing completed successfully.");
        } catch (Exception e) {
            System.err.println("Error during processing: " + e.getMessage());
        }
    }
}