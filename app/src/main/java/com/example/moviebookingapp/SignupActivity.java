package com.example.moviebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class SignupActivity extends AppCompatActivity {
    private TextInputEditText etName, etEmail, etPassword, etConfirmPassword;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.etSignupName);
        etEmail = findViewById(R.id.etSignupEmail);
        etPassword = findViewById(R.id.etSignupPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();
        String confirmPass = etConfirmPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.length() < 6) { // Note: Firebase requires at least 6 characters
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass.equals(confirmPass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // FIREBASE SIGNUP
        com.google.firebase.auth.FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(authResult -> {
                    // Successfully registered in Firebase!
                    Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    // Redirect to MainActivity or Login
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Signup Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}