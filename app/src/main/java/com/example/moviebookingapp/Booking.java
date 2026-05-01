package com.example.moviebookingapp;

public class Booking {
    public String bookingId;
    public String movieName;
    public int seatCount;
    public double totalPrice;
    public String dateTime;

    public Booking() {} // Required for Firebase

    public Booking(String bookingId, String movieName, int seatCount, double totalPrice, String dateTime) {
        this.bookingId = bookingId;
        this.movieName = movieName;
        this.seatCount = seatCount;
        this.totalPrice = totalPrice;
        this.dateTime = dateTime;
    }
}