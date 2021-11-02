package com.example.dootapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dootapp.databinding.ItemTaskBinding;
import com.example.dootapp.ui.cread.TaskModel;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    ArrayList<TaskModel> list;

    public TaskAdapter(ArrayList<TaskModel> list) {
        this.list = list;
    }

    public TaskModel delete(int position) {
        notifyDataSetChanged();
        return list.get(position);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(ItemTaskBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.onFill(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        ItemTaskBinding binding;

        public TaskViewHolder(ItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onFill(TaskModel model) {
            binding.titleTv.setText(model.title);
            binding.timeTv.setText(model.time);
            binding.leftColorV.setBackgroundColor(model.color);
            Glide.with(binding.taskIm).load(model.image).centerCrop().into(binding.taskIm);

        }
    }

}