package com.example.lab6;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    public Cursor cursor;

    // The constructor takes a Cursor and stores it in a private field.
    public MyRecyclerAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    // Override the getItemCount method to return the size of the list of items.
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    // Override the onCreateViewHolder method to create a new ViewHolder instance.
    // The method inflates the list_item layout and returns a new ViewHolder instance.
    // The ViewHolder class is defined below as a static inner class in the MyRecyclerAdapter class.
    // The ViewHolder class is a container for the list item view.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, this);
    }

    // Override the onBindViewHolder method to bind the data to the ViewHolder.
    // The method sets the text of the TextView in the ViewHolder to the item at the given position
    // in the list of items.
    // The position parameter is the position of the item in the list of items.
    // The holder parameter is the ViewHolder instance to bind the data to.
    // The method is called by the RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.editText.setText(items.get(position));

        if (!cursor.moveToPosition(position)) {
            return;
        }

        // Retrieve data from the Cursor using getColumnIndex()
        String movieName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        // Bind data to your ViewHolder
        holder.editText.setText(movieName);
    }

    // The ViewHolder class is a container for the list item view.
    // The class is defined as a static inner class in the List
    // Adapter class to access the private field items of the MyRecyclerAdapter class.
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public EditText editText;
        public final Button updateBtn;
        public final Button deleteBtn;
        MyRecyclerAdapter recyclerAdapter;


        public ViewHolder(View view, MyRecyclerAdapter recyclerAdapter) {
            super(view);
            editText = view.findViewById(R.id.movie_name_txt);
            updateBtn = view.findViewById(R.id.update_movie_btn);
            deleteBtn = view.findViewById(R.id.delete_movie_btn);

            // Set the recyclerAdapter to the ViewHolder
            this.recyclerAdapter = recyclerAdapter;

            // Clear focus from the EditText
            editText.clearFocus();

            MovieDBHelper movies = new MovieDBHelper(itemView.getContext());

            updateBtn.setOnClickListener(v -> {
                int moviePosition = getAdapterPosition();
                Cursor cursor = recyclerAdapter.cursor;

                if (moviePosition != RecyclerView.NO_POSITION && cursor.moveToPosition(moviePosition)) {
                    // Get the movieId from the Cursor
                    int movieId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String newMovieName = editText.getText().toString();
                    movies.updateOne(movieId, newMovieName);

                    //Update data in the Cursor
                    recyclerAdapter.cursor = movies.fetchAll();
                    //Notify the adapter that the data has changed to update the RecyclerView
                    recyclerAdapter.notifyItemChanged(moviePosition);

                    Toast.makeText(view.getContext(), "Movie Updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(view.getContext(), "Movie Not Updated", Toast.LENGTH_LONG).show();
                }
            });

            deleteBtn.setOnClickListener(v -> {
                int moviePosition = getAdapterPosition();
                Cursor cursor = recyclerAdapter.cursor;

                if (moviePosition != RecyclerView.NO_POSITION && cursor.moveToPosition(moviePosition)) {
                    // Get the movieId from the Cursor
                    int movieId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    movies.deleteOne(movieId);

                    recyclerAdapter.cursor = movies.fetchAll();
                    recyclerAdapter.notifyItemRemoved(moviePosition);
                    recyclerAdapter.notifyItemRangeChanged(moviePosition, recyclerAdapter.getItemCount());

                    Toast.makeText(view.getContext(), "Movie Deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(view.getContext(), "Movie Not Deleted", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}