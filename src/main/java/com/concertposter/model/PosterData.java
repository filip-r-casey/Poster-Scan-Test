package com.concertposter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PosterData {
    private String date;

    private Venue venue;

    private String cost;

    @JsonProperty("doors_time")
    private String doorsTime;

    @JsonProperty("show_start_time")
    private String showStartTime;

    @JsonProperty("age_requirement")
    private int ageRequirement;

    private String description;

    private String title;

    private List<Band> bands;

    // Default Constructor
    public PosterData() {}

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDoorsTime() {
        return doorsTime;
    }

    public void setDoorsTime(String doorsTime) {
        this.doorsTime = doorsTime;
    }

    public String getShowStartTime() {
        return showStartTime;
    }

    public void setShowStartTime(String showStartTime) {
        this.showStartTime = showStartTime;
    }

    public int getAgeRequirement() {
        return ageRequirement;
    }

    public void setAgeRequirement(int ageRequirement) {
        this.ageRequirement = ageRequirement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Band> getBands() {
        return bands;
    }

    public void setBands(List<Band> bands) {
        this.bands = bands;
    }

    // Venue Class
    public static class Venue {
        @JsonProperty("db_name")
        private String dbName;

        @JsonProperty("stylized_name")
        private String stylizedName;

        @JsonProperty("address_protected")
        private boolean addressProtected;

        private String address;

        private String website;

        // Default Constructor
        public Venue() {}

        // Getters and Setters
        public String getDbName() {
            return dbName;
        }

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }

        public String getStylizedName() {
            return stylizedName;
        }

        public void setStylizedName(String stylizedName) {
            this.stylizedName = stylizedName;
        }

        public boolean isAddressProtected() {
            return addressProtected;
        }

        public void setAddressProtected(boolean addressProtected) {
            this.addressProtected = addressProtected;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }
    }

    // Band Class
    public static class Band {
        @JsonProperty("db_name")
        private String dbName;

        @JsonProperty("stylized_name")
        private String stylizedName;

        private String genre;

        private String biography;

        private String website;

        @JsonProperty("music_link")
        private String musicLink;

        @JsonProperty("contact_email")
        private String contactEmail;

        @JsonProperty("instagram_username")
        private String instagramUsername;

        @JsonProperty("twitter_username")
        private String twitterUsername;

        @JsonProperty("tiktok_username")
        private String tiktokUsername;

        @JsonProperty("is_active")
        private boolean isActive;

        // Default Constructor
        public Band() {}

        // Getters and Setters
        public String getDbName() {
            return dbName;
        }

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }

        public String getStylizedName() {
            return stylizedName;
        }

        public void setStylizedName(String stylizedName) {
            this.stylizedName = stylizedName;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public String getBiography() {
            return biography;
        }

        public void setBiography(String biography) {
            this.biography = biography;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getMusicLink() {
            return musicLink;
        }

        public void setMusicLink(String musicLink) {
            this.musicLink = musicLink;
        }

        public String getContactEmail() {
            return contactEmail;
        }

        public void setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
        }

        public String getInstagramUsername() {
            return instagramUsername;
        }

        public void setInstagramUsername(String instagramUsername) {
            this.instagramUsername = instagramUsername;
        }

        public String getTwitterUsername() {
            return twitterUsername;
        }

        public void setTwitterUsername(String twitterUsername) {
            this.twitterUsername = twitterUsername;
        }

        public String getTiktokUsername() {
            return tiktokUsername;
        }

        public void setTiktokUsername(String tiktokUsername) {
            this.tiktokUsername = tiktokUsername;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }
    }
}