package com.concertposter.service;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class ImageService {
    private final ImageProcessor processor;
    public ImageService() {
        this.processor = new ImageProcessor();
    }
    public File resizeImage(File image, int width, int height) throws IOException {
        processor.setStrategy(new ResizingStrategy(width, height));
        return processor.applyStrategy(image);
    }

    public File filterImage(File image) throws IOException {
        processor.setStrategy(new FilteringStrategy());
        return processor.applyStrategy(image);
    }

    public String encodeToBase64(File image) throws IOException {
        try {
            byte[] fileContent = Files.readAllBytes(image.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            System.err.println("Error encoding image to Base64: " + image.getName() + ". Reason: " + e.getMessage());
            throw e;
        }
    }

    public boolean isAlreadyResized(File file) {
        // Check if the file name starts with "resized-"
        return file.getName().startsWith("resized-");
    }
}