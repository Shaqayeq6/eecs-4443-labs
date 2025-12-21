package com.example.myapplication.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.model.Task;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class PrefsStorage {

    private static final String PREFS_NAME = "lab3_prefs";
    private static final String KEY_TASKS_JSON = "tasks_json";

    private static SharedPreferences prefs(Context ctx) {
        return ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static List<Task> loadTasks(Context ctx) {
        String json = prefs(ctx).getString(KEY_TASKS_JSON, "[]");
        List<Task> tasks = new ArrayList<>();

        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                tasks.add(Task.fromJson(arr.getJSONObject(i)));
            }
        } catch (JSONException e) {
            // if corrupted, return empty list
            return new ArrayList<>();
        }

        return tasks;
    }

    public static void saveAll(Context ctx, List<Task> tasks) {
        JSONArray arr = new JSONArray();
        try {
            for (Task t : tasks) arr.put(t.toJson());
        } catch (JSONException e) {
            // ignore: won't save if error
            return;
        }

        prefs(ctx).edit().putString(KEY_TASKS_JSON, arr.toString()).apply();
    }

    public static void addTask(Context ctx, Task newTask) {
        List<Task> tasks = loadTasks(ctx);
        tasks.add(0, newTask);
        saveAll(ctx, tasks);
    }

    public static void deleteTaskById(Context ctx, long id) {
        List<Task> tasks = loadTasks(ctx);
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                break;
            }
        }
        saveAll(ctx, tasks);
    }

    public static void clearAll(Context ctx) {
        prefs(ctx).edit().remove(KEY_TASKS_JSON).apply();
    }
}

