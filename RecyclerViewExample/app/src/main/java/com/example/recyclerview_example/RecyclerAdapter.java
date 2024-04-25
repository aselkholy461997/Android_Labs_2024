package com.example.recyclerview_example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private final ArrayList<String> items;

    // The constructor takes a list of items and stores it in a private field.
    public RecyclerAdapter(ArrayList<String> items) {
        this.items = items;
    }

    // Override the getItemCount method to return the size of the list of items.
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Override the onCreateViewHolder method to create a new ViewHolder instance.
    // The method inflates the list_item layout and returns a new ViewHolder instance.
    // The ViewHolder class is defined below as a static inner class in the RecyclerAdapter class.
    // The ViewHolder class is a container for the list item view.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    // Override the onBindViewHolder method to bind the data to the ViewHolder.
    // The method sets the text of the TextView in the ViewHolder to the item at the given position
    // in the list of items.
    // The position parameter is the position of the item in the list of items.
    // The holder parameter is the ViewHolder instance to bind the data to.
    // The method is called by the RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(items.get(position));
    }

    // The ViewHolder class is a container for the list item view.
    // The class is defined as a static inner class in the List
    // Adapter class to access the private field items of the RecyclerAdapter class.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView);
        }
    }
}