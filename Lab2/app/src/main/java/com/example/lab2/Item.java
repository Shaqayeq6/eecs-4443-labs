package com.example.lab2;

public class Item {
    private final String title;
    private final int imageResId;
    private final String description;

    public Item(String title, int imageResId, String description) {
        this.title = title;
        this.imageResId = imageResId;
        this.description = description;
    }

    public String getTitle() { return title; }
    public int getImageResId() { return imageResId; }
    public String getDescription() { return description; }
}

