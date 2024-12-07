package com.concertposter.service;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class ResizingStrategy implements ImageProcessingStrategy {
    private final int width;
    private final int height;

    public ResizingStrategy(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public File process(File image) throws IOException {
        File resizedImage = new File(image.getParent(), "resized-" + image.getName());
        Thumbnails.of(image).size(width, height).toFile(resizedImage);
        System.out.println("Resized image: " + resizedImage.getName());
        return resizedImage;
    }
}
