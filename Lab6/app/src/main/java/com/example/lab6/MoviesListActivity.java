package com.example.lab6;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.movies_list_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MovieDBHelper movies = new MovieDBHelper(getApplicationContext());
        Cursor cursor = movies.fetchAll();

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(new MyRecyclerAdapter(cursor));

        // Add intent to go to movie addition activity
        Button addMovieBtn = findViewById(R.id.add_movie_btn);
        addMovieBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MovieAdditionActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        System.out.println("onRestart() called");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        MyRecyclerAdapter adapter = (MyRecyclerAdapter) recyclerView.getAdapter();

        MovieDBHelper movies = new MovieDBHelper(getApplicationContext());
        Cursor cursor = movies.fetchAll();

        adapter.cursor = cursor;
        adapter.notifyDataSetChanged();
    }
}