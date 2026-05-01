package com.example.moviebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OnboardingActivity extends AppCompatActivity {
    Button btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        init();
        setListeners();
    }

    private void setListeners() {
        btnGetStarted.setOnClickListener(view -> moveToHome());
    }

    // Update this method in your OnboardingActivity.java
    private void moveToHome() {
        // Reroute from MainActivity to LoginActivity
        startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));
        finish();
    }

    private void init() {
        btnGetStarted = findViewById(R.id.btnGetStarted);
    }
}