package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskVH> {

    public interface Listener {
        void onTap(Task task);
        void onLongPress(Task task, int position);
    }

    private final List<Task> tasks;
    private final Listener listener;

    public TaskAdapter(List<Task> tasks, Listener listener) {
        this.tasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVH holder, int position) {
        Task t = tasks.get(position);
        holder.tvTitle.setText(t.getTitle());
        holder.tvDeadline.setText("Deadline: " + t.getDeadline());

        holder.itemView.setOnClickListener(view -> listener.onTap(t));
        holder.itemView.setOnLongClickListener(view -> {
            listener.onLongPress(t, holder.getAdapterPosition());
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void removeAt(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
    }

    static class TaskVH extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDeadline;

        TaskVH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDeadline = itemView.findViewById(R.id.tvDeadline);
        }
    }
}
