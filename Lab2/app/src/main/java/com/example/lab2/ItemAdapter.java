package com.example.lab2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.R;
import com.example.lab2.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemVH> {

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    private final List<Item> items;
    private final OnItemClickListener listener;

    public ItemAdapter(List<Item> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ItemVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVH holder, int position) {
        Item item = items.get(position);

        holder.tvTitle.setText(item.getTitle() == null ? "Untitled" : item.getTitle());
        holder.tvSummary.setText(item.getDescription() == null ? "No description" : item.getDescription());
        holder.imgThumb.setImageResource(item.getImageResId());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ItemVH extends RecyclerView.ViewHolder {
        ImageView imgThumb;
        TextView tvTitle, tvSummary;

        public ItemVH(@NonNull View itemView) {
            super(itemView);
            imgThumb = itemView.findViewById(R.id.imgThumb);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSummary = itemView.findViewById(R.id.tvSummary);
        }
    }
}
