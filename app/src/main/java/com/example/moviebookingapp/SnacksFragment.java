package com.example.moviebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SnacksFragment extends Fragment {

    private ListView listViewSnacks;
    private SnackAdapter snackAdapter;
    private ArrayList<Snack> snackList;

    private String movieName;
    private String movieDate; // Add this
    private int seatCount;
    private double ticketTotal;
    private DatabaseHelper dbHelper;

    public static SnacksFragment newInstance(String movieName, int seatCount, double ticketTotal) {
        SnacksFragment fragment = new SnacksFragment();
        Bundle args = new Bundle();
        args.putString("MOVIE_NAME", movieName);
        args.putInt("SEAT_COUNT", seatCount);
        args.putDouble("TICKET_TOTAL", ticketTotal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieName = getArguments().getString("MOVIE_NAME");
            movieDate = getArguments().getString("MOVIE_DATE");
            seatCount = getArguments().getInt("SEAT_COUNT", 0);
            ticketTotal = getArguments().getDouble("TICKET_TOTAL", 0.0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_snacks, container, false);

        dbHelper = new DatabaseHelper(requireContext()); // Initialize DB Helper
        initializeSnackList();
        setupListView(view);
        setupConfirmButton(view);

        return view;
    }

    private void initializeSnackList() {
        snackList = new ArrayList<>();

//        snackList.add(new Snack("Popcorn", "Large / Buttered", 8.99, R.drawable.popcorn_icon));
//        snackList.add(new Snack("Nachos", "With Cheese Dip", 7.99, R.drawable.nachos_icon));
//        snackList.add(new Snack("Soft Drink", "Large / Any Flavor", 5.99, R.drawable.soft_drink_icon));
//        snackList.add(new Snack("Candy Mix", "Assorted Candies", 6.99, R.drawable.candy_icon));
//        snackList.add(new Snack("Hot Dog", "Classic", 4.99, R.drawable.hot_dog_icon));
        snackList = dbHelper.getAllSnacks();
    }

    private void setupListView(View view) {
        listViewSnacks = view.findViewById(R.id.listViewSnacks);
        snackAdapter = new SnackAdapter(getContext(), snackList);
        listViewSnacks.setAdapter(snackAdapter);
    }

    private void setupConfirmButton(View view) {
        Button btnConfirm = view.findViewById(R.id.confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToTicketSummary();
            }
        });
    }

    private void proceedToTicketSummary() {
        double snacksTotal = 0.0;
        StringBuilder snacksDetails = new StringBuilder();

        for (Snack snack : snackList) {
            if (snack.getQuantity() > 0) {
                double itemTotal = snack.getQuantity() * snack.getPrice();
                snacksTotal += itemTotal;
                snacksDetails.append(snack.getQuantity())
                        .append("x ")
                        .append(snack.getName())
                        .append(" ($")
                        .append(String.format("%.2f", itemTotal))
                        .append(")\n");
            }
        }

        // Navigate to TicketSummaryFragment
        TicketSummaryFragment ticketSummaryFragment = TicketSummaryFragment.newInstance(
                movieName,
                movieDate,        // Added this as the 2nd argument
                seatCount,
                ticketTotal,
                snacksTotal,
                snacksDetails.toString()
        );

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ticketSummaryFragment)
                .addToBackStack(null)
                .commit();
    }
}