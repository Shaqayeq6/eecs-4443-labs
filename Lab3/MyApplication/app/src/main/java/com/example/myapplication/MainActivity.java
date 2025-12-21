package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.TaskAdapter;
import com.example.myapplication.data.PrefsStorage;
import com.example.myapplication.model.Task;
import com.example.myapplication.data.SQLiteStorage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etTitle, etDeadline, etNotes;
    private RadioGroup rgStorage;
    private RadioButton rbPrefs, rbSqlite;
    private Button btnSave;
    private RecyclerView rvTasks;

    private final List<Task> tasks = new ArrayList<>();
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etDeadline = findViewById(R.id.etDeadline);
        etNotes = findViewById(R.id.etNotes);

        rgStorage = findViewById(R.id.rgStorage);
        rbPrefs = findViewById(R.id.rbPrefs);
        rbSqlite = findViewById(R.id.rbSqlite);

        btnSave = findViewById(R.id.btnSave);
        rvTasks = findViewById(R.id.rvTasks);

        adapter = new TaskAdapter(tasks, new TaskAdapter.Listener() {
            @Override
            public void onTap(Task task) {

                Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                i.putExtra("id", task.getId());
                i.putExtra("title", task.getTitle());
                i.putExtra("deadline", task.getDeadline());
                i.putExtra("notes", task.getNotes());
                startActivity(i);
            }

            @Override
            public void onLongPress(Task task, int position) {
                confirmDelete(task, position);
            }
        });

        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setAdapter(adapter);

        etDeadline.setOnClickListener(v -> showDatePicker());
        btnSave.setOnClickListener(v -> saveTask());


        rgStorage.setOnCheckedChangeListener((group, checkedId) -> reloadList());


        reloadList();
    }

    private void reloadList() {
        tasks.clear();

        if (rbPrefs.isChecked()) {
            tasks.addAll(PrefsStorage.loadTasks(this));
        } else {
            tasks.addAll(SQLiteStorage.loadTasks(this));
        }

        adapter.notifyDataSetChanged();
    }


    private void showDatePicker() {
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String date = String.format(Locale.US, "%04d-%02d-%02d",
                            year, (month + 1), dayOfMonth);
                    etDeadline.setText(date);
                },
                y, m, d
        );

        dialog.show();
    }

    private void saveTask() {
        String title = etTitle.getText().toString().trim();
        String deadline = etDeadline.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            etTitle.setError("Title is required");
            etTitle.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(deadline)) {
            etDeadline.setError("Deadline is required");
            etDeadline.requestFocus();
            return;
        }

        Task task = new Task(title, deadline, notes);
        task.setId(System.currentTimeMillis()); // unique id

        if (rbPrefs.isChecked()) {
            PrefsStorage.addTask(this, task);
            Toast.makeText(this, "Saved (SharedPreferences)", Toast.LENGTH_SHORT).show();
        } else {
            SQLiteStorage.addTask(this, task);
            Toast.makeText(this, "Saved (SQLite)", Toast.LENGTH_SHORT).show();
        }


        clearForm();
        reloadList();
    }

    private void clearForm() {
        etTitle.setText("");
        etDeadline.setText("");
        etNotes.setText("");
        etTitle.requestFocus();
    }

    private void confirmDelete(Task task, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Do you want to delete this task?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    if (rbPrefs.isChecked()) {
                        PrefsStorage.deleteTaskById(this, task.getId());
                    } else {
                        SQLiteStorage.deleteTaskById(this, task.getId());
                    }
                    reloadList();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
