package com.example.recyclerview_example;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.list_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Setting the layout manager for the RecyclerView
        // The layout manager is responsible for positioning items in the RecyclerView and managing scrolling,
        // as well as determining the policy for when to recycle item views that are no longer visible to the user.
        // For example: The RecyclerView library provides these three layout managers:
        // LinearLayoutManager, GridLayoutManager, and StaggeredGridLayoutManager.
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // To make a grid is largely the same. You just use a GridLayoutManager instead of a
        // LinearLayoutManager when you set the RecyclerView up.
        // recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        // Generate a list of dummy items
        final ArrayList<String> dummyList = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            dummyList.add("Item " + (i + 1));

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(new RecyclerAdapter(dummyList));

        // Add intent to go to grid btn
        Button goToGridBtn = findViewById(R.id.go_to_grid_btn);
        goToGridBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, GridActivity.class);
            startActivity(intent);
        });
    }
}