package com.concertposter.service;

import java.io.File;
import java.io.IOException;
import java.util.IllformedLocaleException;

public class ImageProcessor {
    private ImageProcessingStrategy strategy;

    public void setStrategy(ImageProcessingStrategy strategy) {
        this.strategy = strategy;
    }

    public File applyStrategy(File image) throws IOException {
        if (strategy == null) {
            throw new IllegalStateException("No processing strategy set.");
        }
        return strategy.process(image);
    }
}
