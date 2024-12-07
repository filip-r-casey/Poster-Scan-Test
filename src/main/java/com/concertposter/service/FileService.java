package com.concertposter.service;

import com.concertposter.model.PosterData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    public void downloadImages(String instagramPageUrl, String outputDirectory, int limit) throws IOException {
        System.out.println("Checking for unprocessed images...");
        if (hasUnprocessedImages(outputDirectory)) {
            System.out.println("Unprocessed images detected. Skipping download step.");
            return;
        }

        System.out.println("No unprocessed images found. Starting download...");
        ProcessBuilder pb = new ProcessBuilder("gallery-dl", "--range", "1-" + limit, instagramPageUrl, "-d", outputDirectory);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("gallery-dl: " + line);
            }
        }

        try {
            if (process.waitFor() != 0) {
                System.err.println("Error: gallery-dl exited with code " + process.exitValue());
                throw new IOException("Error downloading images from Instagram.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Process interrupted while downloading images.", e);
        }

        System.out.println("Download complete.");
    }
    public void scrapeImages(String expressoUrl) {
        ExpressoScraper expressoScraper = new ExpressoScraper(expressoUrl);
        expressoScraper.getImages();
    }

    private boolean hasUnprocessedImages(String directoryPath) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directoryPath), "*.{jpg,jpeg,png}")) {
            return stream.iterator().hasNext();
        } catch (DirectoryIteratorException e) {
            System.err.println("Error checking unprocessed images: " + e.getMessage());
            throw e.getCause();
        }
    }

    public List<File> flattenAndFilterImages(String directoryPath) throws IOException {
        System.out.println("Flattening and filtering images...");
        List<File> filteredImages = new ArrayList<>();
        Files.walk(Paths.get(directoryPath)).forEach(path -> {
            File file = path.toFile();
            if (file.isFile() && isImageFile(file)) {
                File flattenedFile = new File(directoryPath, file.getName());
                try {
                    Files.copy(file.toPath(), flattenedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    filteredImages.add(flattenedFile);
                } catch (IOException e) {
                    System.err.println("Error flattening file: " + file.getName());
                }
            }
        });
        System.out.println("Flattened and filtered " + filteredImages.size() + " images.");
        return filteredImages;
    }

    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
    }

    public void saveResultsAsJson(List<PosterData> results, String outputFilePath) throws IOException {
        File outputFile = new File(outputFilePath);
        File parentDir = outputFile.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                System.err.println("Failed to create output directory: " + parentDir.getAbsolutePath());
                throw new IOException("Failed to create output directory.");
            }
        }

        System.out.println("Saving results to " + outputFilePath + "...");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, results);
        System.out.println("Results saved successfully.");
    }
}