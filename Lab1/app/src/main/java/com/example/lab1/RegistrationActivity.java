package com.example.lab1;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etRegEmail, etRegPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);

        Button btnCreate = findViewById(R.id.btnCreateAccount);
        Button btnBack = findViewById(R.id.btnBackToLogin);
        SharedPreferences sp = getSharedPreferences("lab1_prefs", MODE_PRIVATE);

        btnCreate.setOnClickListener(v -> {
            String email = etRegEmail.getText().toString().trim();
            String pass = etRegPassword.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            sp.edit()
                    .putString("reg_email", email)
                    .putString("reg_pass",pass)
                    .apply();
            Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
            finish(); // go back to login

        });

        btnBack.setOnClickListener(v -> finish());
    }
}

