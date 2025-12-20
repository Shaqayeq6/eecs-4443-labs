package com.example.lab1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        Button btnLogout = findViewById(R.id.btnLogout);

        // Get username passed from MainActivity
        String username = getIntent().getStringExtra("username");

        if (username == null || username.trim().isEmpty()) {
            username = "User";
        }

        tvWelcome.setText("Welcome, " + username + "!");

        btnLogout.setOnClickListener(v -> finish()); // goes back to login
    }
}

