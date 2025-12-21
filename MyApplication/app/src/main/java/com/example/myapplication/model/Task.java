package com.example.myapplication.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Task {
    private long id;
    private String title;
    private String deadline;
    private String notes;

    public Task(long id, String title, String deadline, String notes) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
        this.notes = notes;
    }


    public Task(String title, String deadline, String notes) {
        this(-1, title, deadline, notes);
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getTitle() { return title; }
    public String getDeadline() { return deadline; }
    public String getNotes() { return notes; }


    public JSONObject toJson() throws JSONException {
        JSONObject o = new JSONObject();
        o.put("id", id);
        o.put("title", title);
        o.put("deadline", deadline);
        o.put("notes", notes);
        return o;
    }

    public static Task fromJson(JSONObject o) throws JSONException {
        long id = o.getLong("id");
        String title = o.getString("title");
        String deadline = o.getString("deadline");
        String notes = o.optString("notes", "");
        return new Task(id, title, deadline, notes);
    }
}
