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
    private void moveToOnboarding() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, OnboardingActivity.class));
            finish();
        }, 1000);
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