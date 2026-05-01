package com.example.moviebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreen extends AppCompatActivity {

    ImageView ivLogo;
    TextView tvAppName;
    Animation logoAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        applyAnimation();
        moveToOnboarding();
    }
    // Update this method in your SplashScreen.java
    private void moveToOnboarding() {
        new Handler().postDelayed(() -> {
            // Check Session Management
            android.content.SharedPreferences pref = getSharedPreferences("cinefast_session_pref_v3", MODE_PRIVATE);
            boolean isLoggedIn = pref.getBoolean("isLoggedIn", false);

            if (isLoggedIn) {
                // If already logged in, skip Login and go to MainActivity
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
            } else {
                // Otherwise, go to Onboarding as usual
                startActivity(new Intent(SplashScreen.this, OnboardingActivity.class));
            }
            finish();
        }, 5000); // Updated to 5 seconds as per Assignment 3 requirements
    }
    private void applyAnimation() {
        ivLogo.setAnimation(logoAnimation);
    }
    private void init() {
        ivLogo = findViewById(R.id.logoImage);
        tvAppName = findViewById(R.id.tvAppName);
        logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
    }
}