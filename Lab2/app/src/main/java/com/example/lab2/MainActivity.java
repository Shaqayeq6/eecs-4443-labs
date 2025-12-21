package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.adapter.ItemAdapter;
import com.example.lab2.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Item> buildItems() {
        List<Item> list = new ArrayList<>();

        list.add(new Item("Morning Routine", R.drawable.morning, "Start the day with calm habits and energy."));
        list.add(new Item("Study Zone", R.drawable.study, "Focus methods, tips, and better concentration."));
        list.add(new Item("Productivity", R.drawable.productivity, "Plan your day and finish tasks faster."));
        list.add(new Item("Fitness", R.drawable.fitness, "Simple workouts and daily movement."));
        list.add(new Item("Food", R.drawable.food, "Easy meal ideas and healthy choices."));
        list.add(new Item("Self-Care", R.drawable.selfcare, "Mindset, skincare, and rest routines."));
        list.add(new Item("Relaxation", R.drawable.relax, "De-stress and recharge mentally."));
        list.add(new Item("Music", R.drawable.music, "Playlists for studying, workouts, and chill."));
        list.add(new Item("Reading", R.drawable.reading, "Books to grow your mind and vocabulary."));
        list.add(new Item("Planning", R.drawable.plan, "Goals, weekly planning, and reminders."));
        list.add(new Item("Shopping", R.drawable.shopping, "Smart buying and avoiding impulse spending."));
        list.add(new Item("Social Media", R.drawable.socialmedia, "Use it intentionally, not endlessly."));
        list.add(new Item("Entertainment", R.drawable.entertainment, "Shows, movies, and fun breaks."));
        list.add(new Item("Finance", R.drawable.finance, "Budget basics and saving habits."));
        list.add(new Item("Travel", R.drawable.travel, "Ideas for exploring and planning trips."));

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        ItemAdapter adapter = new ItemAdapter(buildItems(), item -> {
            Intent i = new Intent(MainActivity.this, DetailActivity.class);
            i.putExtra("title", item.getTitle());
            i.putExtra("imageResId", item.getImageResId());
            i.putExtra("description", item.getDescription());
            startActivity(i);
        });

        rv.setAdapter(adapter);
    }
}
