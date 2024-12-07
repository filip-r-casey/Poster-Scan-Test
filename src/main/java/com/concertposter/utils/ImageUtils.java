package com.concertposter.utils;
import java.io.IOException;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageUtils {
    public static String encodeImage(String imagePath) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(imagePath));
        return Base64.getEncoder().encodeToString(fileContent);
    }
}