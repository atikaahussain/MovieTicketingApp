package com.example.moviebookingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class SeatSelectionFragment extends Fragment {

    private static final int SEAT_ROWS = 8;
    private static final int SEAT_COLUMNS = 10;
    private static final double SEAT_PRICE = 16.0;

    GridLayout seatGrid;
    TextView tvMovieName;
    TextView tvTotalAmount;
    Button btnProceedToSnacks;
    Button btnBookSeats;
    Button btnComingSoon;
    Button btnWatchTrailer;

    ArrayList<Button> selectedSeats = new ArrayList<>();
    ArrayList<Integer> bookedSeats = new ArrayList<>();
    String movieName;
    String movieType;
    String trailerUrl;

    public SeatSelectionFragment() {
    }

    public static SeatSelectionFragment newInstance(String movieName, String movieType, String trailerUrl) {
        SeatSelectionFragment fragment = new SeatSelectionFragment();
        Bundle args = new Bundle();
        args.putString("MOVIE_NAME", movieName);
        args.putString("MOVIE_TYPE", movieType);
        args.putString("TRAILER_URL", trailerUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seat_selection, container, false);

        init(view);
        setBookedSeats();
        loadMovieData();

        // Check movie type and set ui
        if ("COMING_SOON".equals(movieType)) {
            setupComingSoonUI();
            createSeatGrid();
        } else {
            setupNowShowingUI();
            createSeatGrid();
        }

        setListeners();

        return view;
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
        if (getArguments() != null) {
            movieName = getArguments().getString("MOVIE_NAME");
            movieType = getArguments().getString("MOVIE_TYPE");
            trailerUrl = getArguments().getString("TRAILER_URL");

            if (movieName != null) {
                tvMovieName.setText(movieName);
            }
        }
    }

    private void setupComingSoonUI() {
        // Hide normal buttons
        btnProceedToSnacks.setVisibility(View.GONE);
        btnBookSeats.setVisibility(View.GONE);

        // Show coming soon buttons
        btnComingSoon.setVisibility(View.VISIBLE);
        btnWatchTrailer.setVisibility(View.VISIBLE);

        // Disable coming soon button
        btnComingSoon.setEnabled(false);
        btnWatchTrailer.setEnabled(true);
        btnComingSoon.setAlpha(0.5f);

        // Disable seat selection
        seatGrid.setEnabled(false);
    }

    private void setupNowShowingUI() {
        // Show normal buttons
        btnProceedToSnacks.setVisibility(View.VISIBLE);
        btnBookSeats.setVisibility(View.VISIBLE);

        // Hide coming soon buttons
        btnComingSoon.setVisibility(View.GONE);
        btnWatchTrailer.setVisibility(View.GONE);
    }

    private void setListeners() {

        btnProceedToSnacks.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(getContext(),
                        getString(R.string.select_seat_first), Toast.LENGTH_SHORT).show();
            } else {
                proceedToSnacks();
            }
        });

        btnBookSeats.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(getContext(),
                        getString(R.string.select_seat_first), Toast.LENGTH_SHORT).show();
            } else {
                bookSeatsDirectly();
                Toast.makeText(getContext(),
                        "Booking Confirmed!", Toast.LENGTH_SHORT).show();
            }
        });


        btnWatchTrailer.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
            startActivity(intent);
        });
    }

    private void proceedToSnacks() {
        SnacksFragment snacksFragment = new SnacksFragment();

        Bundle bundle = new Bundle();
        bundle.putString("MOVIE_NAME", movieName);
        bundle.putInt("SEAT_COUNT", selectedSeats.size());
        bundle.putDouble("TICKET_TOTAL", selectedSeats.size() * SEAT_PRICE);
        snacksFragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, snacksFragment)
                .addToBackStack(null)
                .commit();



    }
    private void bookSeatsDirectly() {
        TicketSummaryFragment summaryFragment = TicketSummaryFragment.newInstance(
                movieName,
                selectedSeats.size(),
                selectedSeats.size() * SEAT_PRICE,
                0.0,
                ""
        );

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, summaryFragment)
                .addToBackStack(null)
                .commit();
    }


    private void createSeatGrid() {
        int seatNumber = 0;

        for (int row = 0; row < SEAT_ROWS; row++) {
            for (int col = 0; col < SEAT_COLUMNS; col++) {
                final Button seatButton = new Button(getContext());
                final int currentSeatNumber = seatNumber;

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 80;
                params.height = 80;
                params.setMargins(4, 4, 4, 4);
                seatButton.setLayoutParams(params);

                seatButton.setText("");
                seatButton.setTextSize(10);

                seatButton.setBackgroundColor(getResources().getColor(R.color.seat_disabled));

                if ("NOW_SHOWING".equals(movieType)) {    // Check if seat is already booked or not
                    if (bookedSeats.contains(seatNumber)) {
                        seatButton.setBackgroundColor(getResources().getColor(R.color.seat_booked));
                        seatButton.setEnabled(false);
                    } else {
                        seatButton.setBackgroundColor(getResources().getColor(R.color.seat_available));
                    }

                    // Seat clicking actions
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
                }


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


    private void init(View view) {
        seatGrid = view.findViewById(R.id.seatGrid);
        tvMovieName = view.findViewById(R.id.tvMovieName);
        tvTotalAmount = view.findViewById(R.id.tvTotalAmount);
        btnProceedToSnacks = view.findViewById(R.id.btnProceedToSnacks);
        btnBookSeats = view.findViewById(R.id.btnBookSeats);
        btnComingSoon = view.findViewById(R.id.btnComingSoon);
        btnWatchTrailer = view.findViewById(R.id.btnWatchTrailer);
    }
}