package com.example.moviebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TicketSummaryActivity extends AppCompatActivity {

    private TextView tvMovieName;
    private TextView tvTotalAmount;
    private LinearLayout ticketContainer;
    private LinearLayout snacksContainer;

    private String movieName;
    private int seatCount;
    private double ticketTotal;
    private double snacksTotal;
    private String snacksDetails;
    Button btnSendTicket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_summary);

        movieName = getIntent().getStringExtra("MOVIE_NAME");
        seatCount = getIntent().getIntExtra("SEAT_COUNT", 0);
        ticketTotal = getIntent().getDoubleExtra("TICKET_TOTAL", 0.0);
        snacksTotal = getIntent().getDoubleExtra("SNACKS_TOTAL", 0.0);
        snacksDetails = getIntent().getStringExtra("SNACKS_DETAILS");

        init();


        tvMovieName.setText(movieName);

        displayTickets();
        displaySnacks();

        double grandTotal = ticketTotal + snacksTotal;
        tvTotalAmount.setText(String.format("%.2f USD", grandTotal));


        // Send Ticket button
        btnSendTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTicket();
            }
        });
    }

    private void displayTickets() {
        // Create ticket rows dynamically
        double pricePerSeat = ticketTotal / seatCount;

        for (int i = 0; i < seatCount; i++) {
            LinearLayout ticketRow = new LinearLayout(this);
            ticketRow.setOrientation(LinearLayout.HORIZONTAL);
            ticketRow.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            TextView seatInfo = new TextView(this);
            seatInfo.setText("Row E, Seat " + (i + 5));
            seatInfo.setTextColor(getResources().getColor(R.color.light_gray));
            seatInfo.setTextSize(14);
            LinearLayout.LayoutParams seatParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
            );
            seatInfo.setLayoutParams(seatParams);

            TextView seatPrice = new TextView(this);
            seatPrice.setText(String.format("%.2f USD", pricePerSeat));
            seatPrice.setTextColor(getResources().getColor(R.color.white));
            seatPrice.setTextSize(14);

            ticketRow.addView(seatInfo);
            ticketRow.addView(seatPrice);

            ticketContainer.addView(ticketRow);

            // Add margin between rows
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ticketRow.getLayoutParams();
            params.setMargins(0, 0, 0, 8);
            ticketRow.setLayoutParams(params);
        }
    }

    private void displaySnacks() {
        if (snacksDetails == null || snacksDetails.isEmpty()) {
            TextView noSnacks = new TextView(this);
            noSnacks.setText("No snacks selected");
            noSnacks.setTextColor(getResources().getColor(R.color.light_gray));
            noSnacks.setTextSize(14);
            snacksContainer.addView(noSnacks);
            return;
        }

        // Parse snacks details and create rows
        String[] snackLines = snacksDetails.split("\n");

        for (String line : snackLines) {
            if (line.trim().isEmpty()) continue;

            LinearLayout snackRow = new LinearLayout(this);
            snackRow.setOrientation(LinearLayout.HORIZONTAL);
            snackRow.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            // Extract item name and price from the line
            // Format: "2x Popcorn ($17.98)"
            String[] parts = line.split("\\(");
            String itemName = parts[0].trim();
            String itemPrice = parts.length > 1 ? parts[1].replace(")", "").trim() : "$0.00";

            TextView snackInfo = new TextView(this);
            snackInfo.setText(itemName);
            snackInfo.setTextColor(getResources().getColor(R.color.light_gray));
            snackInfo.setTextSize(14);
            LinearLayout.LayoutParams snackParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
            );
            snackInfo.setLayoutParams(snackParams);

            TextView snackPrice = new TextView(this);
            snackPrice.setText(itemPrice);
            snackPrice.setTextColor(getResources().getColor(R.color.white));
            snackPrice.setTextSize(14);

            snackRow.addView(snackInfo);
            snackRow.addView(snackPrice);

            snacksContainer.addView(snackRow);

            // Add margin between rows
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) snackRow.getLayoutParams();
            params.setMargins(0, 0, 0, 8);
            snackRow.setLayoutParams(params);
        }
    }

    private void shareTicket() {
        // Create ticket summary text
        StringBuilder ticketText = new StringBuilder();
        ticketText.append("🎬 CineFAST Ticket Summary\n\n");
        ticketText.append("Movie: ").append(movieName).append("\n");
        ticketText.append("Seats: ").append(seatCount).append("\n");
        ticketText.append("Theater: Stars (90°Mall)\n");
        ticketText.append("Date: 13.04.2025\n");
        ticketText.append("Time: 22:15\n\n");
        ticketText.append("Ticket Total: $").append(String.format("%.2f", ticketTotal)).append("\n");

        if (snacksTotal > 0) {
            ticketText.append("Snacks Total: $").append(String.format("%.2f", snacksTotal)).append("\n");
        }

        ticketText.append("\nGrand Total: $").append(String.format("%.2f", ticketTotal + snacksTotal)).append("\n\n");
        ticketText.append("Enjoy your movie! 🍿");

        // Create implicit intent to share
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CineFAST Movie Ticket");
        shareIntent.putExtra(Intent.EXTRA_TEXT, ticketText.toString());

        // Create chooser
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_ticket)));
    }

    private void init(){
        // Initialize views
        tvMovieName = findViewById(R.id.tvMovieName);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        ticketContainer = findViewById(R.id.ticketContainer);
        snacksContainer = findViewById(R.id.snacksContainer);
        btnSendTicket = findViewById(R.id.btnSendTicket);
    }
}