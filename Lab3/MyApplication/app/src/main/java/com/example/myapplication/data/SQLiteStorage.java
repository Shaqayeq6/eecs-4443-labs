package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.Task;

import java.util.ArrayList;
import java.util.List;

public class SQLiteStorage {

    public static void addTask(Context ctx, Task t) {
        DBHelper helper = new DBHelper(ctx);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COL_ID, t.getId());
        cv.put(DBHelper.COL_TITLE, t.getTitle());
        cv.put(DBHelper.COL_DEADLINE, t.getDeadline());
        cv.put(DBHelper.COL_NOTES, t.getNotes());

        db.insert(DBHelper.TABLE_TASKS, null, cv);
        db.close();
    }

    public static List<Task> loadTasks(Context ctx) {
        DBHelper helper = new DBHelper(ctx);
        SQLiteDatabase db = helper.getReadableDatabase();

        List<Task> list = new ArrayList<>();

        Cursor c = db.query(
                DBHelper.TABLE_TASKS,
                null,
                null,
                null,
                null,
                null,
                DBHelper.COL_ID + " DESC"
        );

        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndexOrThrow(DBHelper.COL_ID));
            String title = c.getString(c.getColumnIndexOrThrow(DBHelper.COL_TITLE));
            String deadline = c.getString(c.getColumnIndexOrThrow(DBHelper.COL_DEADLINE));
            String notes = c.getString(c.getColumnIndexOrThrow(DBHelper.COL_NOTES));

            list.add(new Task(id, title, deadline, notes));
        }

        c.close();
        db.close();
        return list;
    }

    public static void deleteTaskById(Context ctx, long id) {
        DBHelper helper = new DBHelper(ctx);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(DBHelper.TABLE_TASKS, DBHelper.COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
