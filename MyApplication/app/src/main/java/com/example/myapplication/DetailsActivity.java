package com.example.myapplication;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView tvTitle = findViewById(R.id.tvDTitle);
        TextView tvDeadline = findViewById(R.id.tvDDeadline);
        TextView tvNotes = findViewById(R.id.tvDNotes);

        String title = getIntent().getStringExtra("title");
        String deadline = getIntent().getStringExtra("deadline");
        String notes = getIntent().getStringExtra("notes");

        tvTitle.setText(title);
        tvDeadline.setText(deadline);
        tvNotes.setText((notes == null || notes.isEmpty()) ? "No notes." : notes);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
