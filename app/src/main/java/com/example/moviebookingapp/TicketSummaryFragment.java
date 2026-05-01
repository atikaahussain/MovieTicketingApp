package com.example.moviebookingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class TicketSummaryFragment extends Fragment {

    private static final String PREFS_NAME = "MovieBookingPrefs";
    private static final String KEY_LAST_MOVIE = "last_movie_name";
    private static final String KEY_LAST_SEATS = "last_seat_count";
    private static final String KEY_LAST_TOTAL = "last_ticket_total";

    private TextView tvMovieName;
    private TextView tvTotalAmount;
    private LinearLayout ticketContainer;
    private LinearLayout snacksContainer;
    private Button btnSendTicket;

    private String movieName;
    private String movieDate;
    private int seatCount;
    private double ticketTotal;
    private double snacksTotal;
    private String snacksDetails;


    public static TicketSummaryFragment newInstance(String movieName,String movieDate, int seatCount,double ticketTotal, double snacksTotal,String snacksDetails) {

        TicketSummaryFragment fragment = new TicketSummaryFragment();

        Bundle args = new Bundle();
        args.putString("MOVIE_NAME", movieName);
        args.putString("MOVIE_DATE", movieDate);
        args.putInt("SEAT_COUNT", seatCount);
        args.putDouble("TICKET_TOTAL", ticketTotal);
        args.putDouble("SNACKS_TOTAL", snacksTotal);
        args.putString("SNACKS_DETAILS", snacksDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieName = getArguments().getString("MOVIE_NAME");
            movieDate = getArguments().getString("MOVIE_DATE"); // Assigned to class variable
            seatCount = getArguments().getInt("SEAT_COUNT", 0);
            ticketTotal = getArguments().getDouble("TICKET_TOTAL", 0.0);
            snacksTotal = getArguments().getDouble("SNACKS_TOTAL", 0.0);
            snacksDetails = getArguments().getString("SNACKS_DETAILS");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ticket_summary, container, false);

        initViews(view);
        displayBookingDetails();
        setupSendTicketButton();

        saveBookingToPreferences();

        saveBookingToFirebase();

        return view;
    }

    private void initViews(View view) {
        tvMovieName = view.findViewById(R.id.tvMovieName);
        tvTotalAmount = view.findViewById(R.id.tvTotalAmount);
        ticketContainer = view.findViewById(R.id.ticketContainer);
        snacksContainer = view.findViewById(R.id.snacksContainer);
        btnSendTicket = view.findViewById(R.id.btnSendTicket);
    }

    private void displayBookingDetails() {
        tvMovieName.setText(movieName);
        displayTickets();
        displaySnacks();

        double grandTotal = ticketTotal + snacksTotal;
        tvTotalAmount.setText(String.format("%.2f USD", grandTotal));
    }

    private void displayTickets() {
        double pricePerSeat = ticketTotal / seatCount;

//        displaying dynamically
        for (int i = 0; i < seatCount; i++) {
            LinearLayout ticketRow = new LinearLayout(getContext());
            ticketRow.setOrientation(LinearLayout.HORIZONTAL);
            ticketRow.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            TextView seatInfo = new TextView(getContext());
            seatInfo.setText("Row E, Seat " + (i + 5));
            seatInfo.setTextColor(getResources().getColor(R.color.light_gray));
            seatInfo.setTextSize(14);
            LinearLayout.LayoutParams seatParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
            );
            seatInfo.setLayoutParams(seatParams);

            TextView seatPrice = new TextView(getContext());
            seatPrice.setText(String.format("%.2f USD", pricePerSeat));
            seatPrice.setTextColor(getResources().getColor(R.color.white));
            seatPrice.setTextSize(14);

            ticketRow.addView(seatInfo);
            ticketRow.addView(seatPrice);

            ticketContainer.addView(ticketRow);

            //margin between rows
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ticketRow.getLayoutParams();
            params.setMargins(0, 0, 0, 8);
            ticketRow.setLayoutParams(params);
        }
    }

    private void displaySnacks() {
        if (snacksDetails == null || snacksDetails.isEmpty()) {
            TextView noSnacks = new TextView(getContext());
            noSnacks.setText("No snacks selected");
            noSnacks.setTextColor(getResources().getColor(R.color.light_gray));
            noSnacks.setTextSize(14);
            snacksContainer.addView(noSnacks);
            return;
        }

        String[] snackLines = snacksDetails.split("\n");

        for (String line : snackLines) {
            if (line.trim().isEmpty()) continue;

            LinearLayout snackRow = new LinearLayout(getContext());
            snackRow.setOrientation(LinearLayout.HORIZONTAL);
            snackRow.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            String[] parts = line.split("\\(");
            String itemName = parts[0].trim();
            String itemPrice = parts.length > 1 ? parts[1].replace(")", "").trim() : "$0.00";

            TextView snackInfo = new TextView(getContext());
            snackInfo.setText(itemName);
            snackInfo.setTextColor(getResources().getColor(R.color.light_gray));
            snackInfo.setTextSize(14);
            LinearLayout.LayoutParams snackParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
            );
            snackInfo.setLayoutParams(snackParams);

            TextView snackPrice = new TextView(getContext());
            snackPrice.setText(itemPrice);
            snackPrice.setTextColor(getResources().getColor(R.color.white));
            snackPrice.setTextSize(14);

            snackRow.addView(snackInfo);
            snackRow.addView(snackPrice);

            snacksContainer.addView(snackRow);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) snackRow.getLayoutParams();
            params.setMargins(0, 0, 0, 8);
            snackRow.setLayoutParams(params);
        }
    }

    private void setupSendTicketButton() {
        btnSendTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTicket();
            }
        });
    }

    private void shareTicket() {
        StringBuilder ticketText = new StringBuilder();
        ticketText.append("🎬 CineFAST Ticket Summary\n\n");
        ticketText.append("Movie: ").append(movieName).append("\n");
        ticketText.append("Seats: ").append(seatCount).append("\n");
        ticketText.append("Theater: Stars (90°Mall)\n");
        ticketText.append("Date: ").append(movieDate).append("\n"); // Using dynamic date
        ticketText.append("Time: 22:15\n\n");
        ticketText.append("Ticket Total: $").append(String.format("%.2f", ticketTotal)).append("\n");

        if (snacksTotal > 0) {
            ticketText.append("Snacks Total: $").append(String.format("%.2f", snacksTotal)).append("\n");
        }

        ticketText.append("\nGrand Total: $").append(String.format("%.2f", ticketTotal + snacksTotal)).append("\n\n");
        ticketText.append("Enjoy your movie! 🍿");

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CineFAST Movie Ticket");
        shareIntent.putExtra(Intent.EXTRA_TEXT, ticketText.toString());

        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_ticket)));
    }

    private void saveBookingToPreferences() {
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(KEY_LAST_MOVIE, movieName);
        editor.putInt(KEY_LAST_SEATS, seatCount);
        editor.putFloat(KEY_LAST_TOTAL, Float.parseFloat(String.format("%.2f", ticketTotal + snacksTotal)));
        editor.apply();
    }

    public static String getLastBookingInfo(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String movieName = prefs.getString(KEY_LAST_MOVIE, "No previous booking");
        int seatCount = prefs.getInt(KEY_LAST_SEATS, 0);
        float ticketTotal = prefs.getFloat(KEY_LAST_TOTAL, 0.0f);

        if (seatCount == 0) {
            return "No previous booking found";
        }

        return String.format("Last Booking:\nMovie: %s\nSeats: %d\nTotal: $%.2f",
                movieName, seatCount, ticketTotal);
    }

    public static void clearLastBooking(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    private void saveBookingToFirebase() {

        com.google.firebase.auth.FirebaseUser user = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser();

        // a check if user is null
        if (user == null) {
            Toast.makeText(getContext(), "Error: User not logged in!", Toast.LENGTH_LONG).show();
            // Optional: Redirect to login activity here
            return;
        }

        // getting user id form auth
        String userId = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();

        // to do this "bookings/{userId}/{bookingId}"
        // get db reference of this -> bookings/{userId}
        com.google.firebase.database.DatabaseReference dbRef =
                com.google.firebase.database.FirebaseDatabase.getInstance().getReference("bookings").child(userId);

        // then make this {bookingId}
        String bookingId = dbRef.push().getKey();
        String showDate = getArguments() != null ? getArguments().getString("MOVIE_DATE", "2026-05-15") : "2026-05-15";
//        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//                .format(Calendar.getInstance().getTime());

        double grandTotal = ticketTotal + snacksTotal;

        //booking obj
        Booking newBooking = new Booking(bookingId, movieName, seatCount, grandTotal, showDate);
        //saving...
        if (bookingId != null) {
            dbRef.child(bookingId).setValue(newBooking)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Booking Done", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}