package com.example.moviebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SeatSelectionActivity extends AppCompatActivity {

    private static final int SEAT_ROWS = 8;
    private static final int SEAT_COLUMNS = 10;
    private static final double SEAT_PRICE = 16.0;

    GridLayout seatGrid;
    TextView tvMovieName;
    TextView tvTotalAmount;
    Button btnProceedToSnacks;
    Button btnBookSeats;

    ArrayList<Button> selectedSeats = new ArrayList<>();
    ArrayList<Integer> bookedSeats = new ArrayList<>();
    String movieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        init();
        setBookedSeats();
        loadMovieData();
        createSeatGrid();
        setListeners();
    }

    private void setBookedSeats() {
        bookedSeats.add(15);
        bookedSeats.add(16);
        bookedSeats.add(34);
        bookedSeats.add(35);
        bookedSeats.add(52);
        bookedSeats.add(53);
    }

    private void loadMovieData() {
        movieName = getIntent().getStringExtra("MOVIE_NAME");
        if (movieName != null) {
            tvMovieName.setText(movieName);
        }
    }

    private void setListeners() {

        btnProceedToSnacks.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(SeatSelectionActivity.this,
                        getString(R.string.select_seat_first), Toast.LENGTH_SHORT).show();
            } else {
                proceedToSnacks();
            }
        });

        btnBookSeats.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(SeatSelectionActivity.this,
                        getString(R.string.select_seat_first), Toast.LENGTH_SHORT).show();
            } else {
                bookSeatsDirectly();
            }
        });
    }

    private void proceedToSnacks() {
        Intent intent = new Intent(SeatSelectionActivity.this, SnacksActivity.class);
        intent.putExtra("MOVIE_NAME", movieName);
        intent.putExtra("SEAT_COUNT", selectedSeats.size());
        intent.putExtra("TICKET_TOTAL", selectedSeats.size() * SEAT_PRICE);
        startActivity(intent);
    }

    private void bookSeatsDirectly() {
        Intent intent = new Intent(SeatSelectionActivity.this, TicketSummaryActivity.class);
        intent.putExtra("MOVIE_NAME", movieName);
        intent.putExtra("SEAT_COUNT", selectedSeats.size());
        intent.putExtra("TICKET_TOTAL", selectedSeats.size() * SEAT_PRICE);
        intent.putExtra("SNACKS_TOTAL", 0.0);
        intent.putExtra("SNACKS_DETAILS", "");
        startActivity(intent);
    }

    private void createSeatGrid() {
        int seatNumber = 0;

        for (int row = 0; row < SEAT_ROWS; row++) {
            for (int col = 0; col < SEAT_COLUMNS; col++) {
                final Button seatButton = new Button(this);
                final int currentSeatNumber = seatNumber;

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 80;
                params.height = 80;
                params.setMargins(4, 4, 4, 4);
                seatButton.setLayoutParams(params);

                seatButton.setText("");
                seatButton.setTextSize(10);

                // Check if seat is alr booked or not
                if (bookedSeats.contains(seatNumber)) {
                    seatButton.setBackgroundColor(getResources().getColor(R.color.seat_booked));
                    seatButton.setEnabled(false); // Disable booked seats
                } else {
                    seatButton.setBackgroundColor(getResources().getColor(R.color.seat_available));
                }

                // Seat clickking actions
                seatButton.setOnClickListener(v -> {
                    if (selectedSeats.contains(seatButton)) {
                        selectedSeats.remove(seatButton);
                        seatButton.setBackgroundColor(getResources().getColor(R.color.seat_available));
                    } else {
                        selectedSeats.add(seatButton);
                        seatButton.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                    updateTotalAmount();
                });

                seatGrid.addView(seatButton);
                seatNumber++;
            }
        }
    }

    private void updateTotalAmount() {
        double total = selectedSeats.size() * SEAT_PRICE;
        tvTotalAmount.setText(String.format("%.2f USD", total));

        if (selectedSeats.isEmpty()) {
            btnProceedToSnacks.setEnabled(false);
            btnProceedToSnacks.setAlpha(0.5f);
        } else {
            btnProceedToSnacks.setEnabled(true);
            btnProceedToSnacks.setAlpha(1.0f);
        }
    }

    private void init() {
        seatGrid = findViewById(R.id.seatGrid);
        tvMovieName = findViewById(R.id.tvMovieName);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        btnProceedToSnacks = findViewById(R.id.btnProceedToSnacks);
        btnBookSeats = findViewById(R.id.btnBookSeats);

    }
}