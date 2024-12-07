package com.concertposter.config;

public class AppConfig {
    private static AppConfig instance;
    private String openAiApiKey;
    private String imageDirectory;
    private String outputJsonFile;
    private String instagramPageUrl;
    private int imageLimit;



    private AppConfig() {
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public String getOpenAiApiKey() {
        return openAiApiKey;
    }

    public void setOpenApiKey(String apiKey) {
        this.openAiApiKey = apiKey;
    }

    public String getImageDirectory() {
        return imageDirectory;
    }

    public void setImageDirectory(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }

    public String getOutputJsonFile() {
        return outputJsonFile;
    }

    public void setOutputJsonFile(String outputJsonFile) {
        this.outputJsonFile = outputJsonFile;
    }

    public String getInstagramPageUrl() {
        return instagramPageUrl;
    }

    public void setInstagramPageUrl(String instagramPageUrl) {
        this.instagramPageUrl = instagramPageUrl;
    }

    public int getImageLimit() {
        return imageLimit;
    }

    public void setImageLimit(int imageLimit) {
        this.imageLimit = imageLimit;
    }
}