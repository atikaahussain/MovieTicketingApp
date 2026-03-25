package com.example.moviebookingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private static final String TRAILER_URL_1 = "https://www.youtube.com/watch?v=EXeTwQWrcwY";
    private static final String TRAILER_URL_2 = "https://www.youtube.com/watch?v=YoHD9XEInc0";
    private static final String TRAILER_URL_3 = "https://www.youtube.com/watch?v=zSWdZVtXT7E";
    private static final String TRAILER_URL_4 = "https://www.youtube.com/watch?v=6hB3S9bIaco";

    public HomeFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setupMovieButtons(view);
        setupTrailerButtons(view);

        return view;
    }

    private void setupMovieButtons(View view) {

        Button btnBookSeats1 = view.findViewById(R.id.btnBookSeats1);
        Button btnBookSeats2 = view.findViewById(R.id.btnBookSeats2);
        Button btnBookSeats3 = view.findViewById(R.id.btnBookSeats3);
        Button btnBookSeats4 = view.findViewById(R.id.btnBookSeats4);

        btnBookSeats1.setOnClickListener(v ->
                openSeatSelection(getString(R.string.movie_dark_knight)));

        btnBookSeats2.setOnClickListener(v ->
                openSeatSelection(getString(R.string.movie_inception)));

        btnBookSeats3.setOnClickListener(v ->
                openSeatSelection(getString(R.string.movie_interstellar)));

        btnBookSeats4.setOnClickListener(v ->
                openSeatSelection(getString(R.string.movie_shawshank)));
    }

    private void setupTrailerButtons(View view) {

        ImageView btnTrailer1 = view.findViewById(R.id.btnTrailer1);
        ImageView btnTrailer2 = view.findViewById(R.id.btnTrailer2);
        ImageView btnTrailer3 = view.findViewById(R.id.btnTrailer3);
        ImageView btnTrailer4 = view.findViewById(R.id.btnTrailer4);

        btnTrailer1.setOnClickListener(v -> openTrailer(TRAILER_URL_1));
        btnTrailer2.setOnClickListener(v -> openTrailer(TRAILER_URL_2));
        btnTrailer3.setOnClickListener(v -> openTrailer(TRAILER_URL_3));
        btnTrailer4.setOnClickListener(v -> openTrailer(TRAILER_URL_4));
    }

    private void openSeatSelection(String movieName) {

        if (getActivity() == null) return; // safety check

        Intent intent = new Intent(getActivity(), SeatSelectionActivity.class);
        intent.putExtra("MOVIE_NAME", movieName);
        startActivity(intent);
    }

    private void openTrailer(String url) {

        if (getActivity() == null) return; // safety check

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}