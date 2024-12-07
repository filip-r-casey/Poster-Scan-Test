package com.concertposter.service;

import java.io.File;

public class FilteringStrategy implements ImageProcessingStrategy {
    @Override
    public File process(File image) {
        String name = image.getName().toLowerCase();
        if (!name.endsWith(".jpg") && !name.endsWith(".jpeg")) {
            System.out.println("Filtered out non-JPEG image: " + image.getName());
            return null;
        }
        return image;
    }
}
