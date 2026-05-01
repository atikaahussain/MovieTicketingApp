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

    private void moveToHome() {
        startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));
        finish();
    }

    private void init() {
        btnGetStarted = findViewById(R.id.btnGetStarted);
    }
}