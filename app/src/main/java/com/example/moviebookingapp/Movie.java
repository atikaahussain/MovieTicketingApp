package com.example.moviebookingapp;

public class Movie {
    private String name;
    private String genre;
    private String imageName;
    private int imageResId;
    private String trailerUrl;
    private String date; // Add this field

    // Update constructor to include date
    public Movie(String name, String genre, int imageResId, String trailerUrl, String date) {
        this.name = name;
        this.genre = genre;
        this.imageResId = imageResId;
        this.trailerUrl = trailerUrl;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Keep your helper and other getters...
    public void convertImageNameToId(android.content.Context context) {
        this.imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    public String getName() { return name; }
    public String getGenre() { return genre; }
    public int getImageResId() { return imageResId; }
    public String getTrailerUrl() { return trailerUrl; }
}