package com.example.moviebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SnacksActivity extends AppCompatActivity {

    private static final double POPCORN_PRICE = 8.99;
    private static final double NACHOS_PRICE = 7.99;
    private static final double SOFT_DRINK_PRICE = 5.99;
    private static final double CANDY_PRICE = 6.99;
    private static final double HOT_DOG_PRICE = 4.99;
    private TextView tvQuantity1, tvQuantity2, tvQuantity3, tvQuantity4, tvQuantity5;
    private int quantity1 = 0;
    private int quantity2 = 0;
    private int quantity3 = 0;
    private int quantity4 = 0;
    private int quantity5 = 0;

    private String movieName;
    private int seatCount;
    private double ticketTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks);

        movieName = getIntent().getStringExtra("MOVIE_NAME");
        seatCount = getIntent().getIntExtra("SEAT_COUNT", 0);
        ticketTotal = getIntent().getDoubleExtra("TICKET_TOTAL", 0.0);

        init();
        setupSnackButtons();

        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToTicketSummary();
            }
        });
    }

    private void setupSnackButtons() {
        Button btnPlus1 = findViewById(R.id.btnPlus1);
        Button btnMinus1 = findViewById(R.id.btnMinus1);

        Button btnPlus2 = findViewById(R.id.btnPlus2);
        Button btnMinus2 = findViewById(R.id.btnMinus2);

        Button btnPlus3 = findViewById(R.id.btnPlus3);
        Button btnMinus3 = findViewById(R.id.btnMinus3);

        Button btnPlus4 = findViewById(R.id.btnPlus4);
        Button btnMinus4 = findViewById(R.id.btnMinus4);

        Button btnPlus5 = findViewById(R.id.btnPlus5);
        Button btnMinus5 = findViewById(R.id.btnMinus5);

        btnPlus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity1++;
                tvQuantity1.setText(String.valueOf(quantity1));
            }
        });

        btnMinus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity1 > 0) {
                    quantity1--;
                    tvQuantity1.setText(String.valueOf(quantity1));
                }
            }
        });


        btnPlus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity2++;
                tvQuantity2.setText(String.valueOf(quantity2));
            }
        });

        btnMinus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity2 > 0) {
                    quantity2--;
                    tvQuantity2.setText(String.valueOf(quantity2));
                }
            }
        });

        btnPlus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity3++;
                tvQuantity3.setText(String.valueOf(quantity3));
            }
        });

        btnMinus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity3 > 0) {
                    quantity3--;
                    tvQuantity3.setText(String.valueOf(quantity3));
                }
            }
        });

        btnPlus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity4++;
                tvQuantity4.setText(String.valueOf(quantity4));
            }
        });

        btnMinus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity4 > 0) {
                    quantity4--;
                    tvQuantity4.setText(String.valueOf(quantity4));
                }
            }
        });

        btnPlus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity5++;
                tvQuantity5.setText(String.valueOf(quantity5));
            }
        });

        btnMinus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity5 > 0) {
                    quantity5--;
                    tvQuantity5.setText(String.valueOf(quantity5));
                }
            }
        });
    }

    private void proceedToTicketSummary() {
        double snacksTotal = 0.0;
        StringBuilder snacksDetails = new StringBuilder();

        if (quantity1 > 0) {
            snacksTotal += quantity1 * POPCORN_PRICE;
            snacksDetails.append(quantity1).append("x Popcorn (")
                    .append(String.format("$%.2f", quantity1 * POPCORN_PRICE)).append(")\n");
        }
        if (quantity2 > 0) {
            snacksTotal += quantity2 * NACHOS_PRICE;
            snacksDetails.append(quantity2).append("x Nachos (")
                    .append(String.format("$%.2f", quantity2 * NACHOS_PRICE)).append(")\n");
        }
        if (quantity3 > 0) {
            snacksTotal += quantity3 * SOFT_DRINK_PRICE;
            snacksDetails.append(quantity3).append("x Soft Drink (")
                    .append(String.format("$%.2f", quantity3 * SOFT_DRINK_PRICE)).append(")\n");
        }
        if (quantity4 > 0) {
            snacksTotal += quantity4 * CANDY_PRICE;
            snacksDetails.append(quantity4).append("x Candy Mix (")
                    .append(String.format("$%.2f", quantity4 * CANDY_PRICE)).append(")\n");
        }
        if (quantity5 > 0) {
            snacksTotal += quantity5 * HOT_DOG_PRICE;
            snacksDetails.append(quantity5).append("x Hot Dog (")
                    .append(String.format("$%.2f", quantity5 * HOT_DOG_PRICE)).append(")\n");
        }

        Intent i = new Intent(SnacksActivity.this, TicketSummaryActivity.class);
        i.putExtra("MOVIE_NAME", movieName);
        i.putExtra("SEAT_COUNT", seatCount);
        i.putExtra("TICKET_TOTAL", ticketTotal);
        i.putExtra("SNACKS_TOTAL", snacksTotal);
        i.putExtra("SNACKS_DETAILS", snacksDetails.toString());
        startActivity(i);
    }

    private void init(){
        tvQuantity1 = findViewById(R.id.tvQuantity1);
        tvQuantity2 = findViewById(R.id.tvQuantity2);
        tvQuantity3 = findViewById(R.id.tvQuantity3);
        tvQuantity4 = findViewById(R.id.tvQuantity4);
        tvQuantity5 = findViewById(R.id.tvQuantity5);
    }
}