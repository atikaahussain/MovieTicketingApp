package com.example.moviebookingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvSignup;
    private CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignup = findViewById(R.id.tvSignup);
        cbRemember = findViewById(R.id.cbRemember);

        btnLogin.setOnClickListener(v -> loginUser());

        tvSignup.setOnClickListener(v -> {
            startActivity(new Intent(this, SignupActivity.class));
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // FIREBASE LOGIN
        com.google.firebase.auth.FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(authResult -> {
                    // Success: Now Firebase knows who is logged in!

                    // You can keep SharedPreferences for UI/Session persistence if you want
                    SharedPreferences pref = getSharedPreferences("cinefast_session_pref_v3", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("userEmail", email);
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "Welcome Back!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Login Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}