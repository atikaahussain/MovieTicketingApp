package com.example.moviebookingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private static final String TRAILER_URL_1 = "https://www.youtube.com/watch?v=EXeTwQWrcwY";
    private static final String TRAILER_URL_2 = "https://www.youtube.com/watch?v=YoHD9XEInc0";
    private static final String TRAILER_URL_3 = "https://www.youtube.com/watch?v=zSWdZVtXT7E";
    private static final String TRAILER_URL_4 = "https://www.youtube.com/watch?v=6hB3S9bIaco"; // The Shawshank Redemption

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupMovieButtons();
        setupTrailerButtons();
    }

    private void setupMovieButtons() {

        Button btnBookSeats1 = findViewById(R.id.btnBookSeats1);
        btnBookSeats1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSeatSelection(getString(R.string.movie_dark_knight));
            }
        });

        Button btnBookSeats2 = findViewById(R.id.btnBookSeats2);
        btnBookSeats2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSeatSelection(getString(R.string.movie_inception));
            }
        });

        Button btnBookSeats3 = findViewById(R.id.btnBookSeats3);
        btnBookSeats3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSeatSelection(getString(R.string.movie_interstellar));
            }
        });
        Button btnBookSeats4 = findViewById(R.id.btnBookSeats4);
        btnBookSeats4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSeatSelection(getString(R.string.movie_shawshank));
            }
        });
    }

    private void setupTrailerButtons() {

        ImageView btnTrailer1 = findViewById(R.id.btnTrailer1);
        btnTrailer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTrailer(TRAILER_URL_1);
            }
        });


        ImageView btnTrailer2 = findViewById(R.id.btnTrailer2);
        btnTrailer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTrailer(TRAILER_URL_2);
            }
        });

        ImageView btnTrailer3 = findViewById(R.id.btnTrailer3);
        btnTrailer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTrailer(TRAILER_URL_3);
            }
        });

        ImageView btnTrailer4 = findViewById(R.id.btnTrailer4);
        btnTrailer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTrailer(TRAILER_URL_4);
            }
        });
    }

    private void openSeatSelection(String movieName) {
        Intent intent = new Intent(HomeActivity.this, SeatSelectionActivity.class);
        intent.putExtra("MOVIE_NAME", movieName);
        startActivity(intent);
    }

    private void openTrailer(String url) {
        // Implicit iontent
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}