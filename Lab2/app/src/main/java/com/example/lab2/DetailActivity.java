package com.example.lab2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.detailToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Details");
        }


        ImageView img = findViewById(R.id.imgLarge);
        TextView title = findViewById(R.id.tvDetailTitle);
        TextView desc = findViewById(R.id.tvDetailDesc);

        String t = getIntent().getStringExtra("title");
        String d = getIntent().getStringExtra("description");
        int resId = getIntent().getIntExtra("imageResId", 0);

        title.setText(t == null ? "Untitled" : t);
        desc.setText(d == null ? "No description available." : d);

        if (resId != 0) img.setImageResource(resId);
        else img.setImageResource(R.drawable.morning); // fallback
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
