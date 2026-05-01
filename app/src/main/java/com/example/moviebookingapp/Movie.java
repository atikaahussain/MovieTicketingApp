package com.example.moviebookingapp;

public class Movie {

    private String name;
    private String genre;
    private String imageName; // For JSON parsing
    private int imageResId;
    //    private int duration;
    private String trailerUrl;

    public Movie(String name, String genre, int imageResId, String trailerUrl) {
        this.name = name;
        this.genre = genre;
//        this.duration=duration;
        this.imageResId = imageResId;
        this.trailerUrl = trailerUrl;
    }

    // Add this helper to convert name string to drawable ID
    public void convertImageNameToId(android.content.Context context) {
        this.imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getImageResId() {
        return imageResId;
    }
//    public int getDuration() {
//        return duration;
//    }

    public String getTrailerUrl() {
        return trailerUrl;
    }
}