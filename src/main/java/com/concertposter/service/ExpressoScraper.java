package com.concertposter.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExpressoScraper {
    String baseUrl = "https://www.expressobeans.com";
    String targetUrl;

    public ExpressoScraper(String url) {
        this.targetUrl = url;
    }

    public void getImages() {
        try {
            Document doc = Jsoup.connect(targetUrl).get();
            Elements images = doc.select(".itemImage img");
            for (Element img : images) {
                String imageUrl = img.attr("src");
                String absoluteImageUrl = baseUrl + imageUrl;

                String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                System.out.println("Downloading: " + absoluteImageUrl);

                downloadImage(absoluteImageUrl, "/images", fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void downloadImage(String imageUrl, String saveDirectory, String fileName) {
        try {
            URL url = new URL(imageUrl);
            var connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            try (InputStream in = connection.getInputStream()) {
                Path filePath = Paths.get(saveDirectory, fileName);
                Files.copy(in, filePath);
                System.out.println("Saved: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Error downloading image: " + imageUrl);
            e.printStackTrace();
        }
    }
}
