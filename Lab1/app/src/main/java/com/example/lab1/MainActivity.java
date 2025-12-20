package com.example.lab1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etPassword;
    private boolean isPasswordVisible = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPassword = findViewById(R.id.etPassword);
        etPassword.setClickable(true);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCancel = findViewById(R.id.btnCancel);
        TextView tvMessage = findViewById(R.id.tvMessage);
        EditText etEmail = findViewById(R.id.etUsername);
        TextView tvRegister = findViewById(R.id.tvRegister);
        SharedPreferences sp = getSharedPreferences("lab1_prefs", MODE_PRIVATE);
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                tvMessage.setText("Please enter email and password");
                return;
            }

            // Hardcoded credentials (from lab example)
            boolean hardcodedOk = email.equals("admin") && password.equals("1234");
            String regEmail = sp.getString("reg_email", null);
            String regPass = sp.getString("reg_pass", null);
            boolean registeredOk = email.equals(regEmail) && password.equals(regPass);

            if (hardcodedOk || registeredOk) {
                tvMessage.setText("Login Successful");
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                intent.putExtra("username", email);
                startActivity(intent);
            }
            else {
                tvMessage.setText("Invalid username/password");

            }


        });


        btnCancel.setOnClickListener(v -> {
            etEmail.setText("");
            etPassword.setText("");
            tvMessage.setText("");
        });
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });


    }





    private void togglePasswordVisibility() {
        int cursorPos = etPassword.getSelectionEnd();

        if (isPasswordVisible) {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        else {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }

        etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.lock, 0, R.drawable.disabled_visible, 0
        );
        isPasswordVisible = !isPasswordVisible;
        etPassword.setSelection(cursorPos);
    }

}