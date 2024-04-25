package com.example.recyclerview_example;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.grid_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         // Setting the layout manager for the RecyclerView
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        // Generate a list of dummy items
        final ArrayList<String> dummyList = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            dummyList.add("item " + (i + 1));

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(new RecyclerAdapter(dummyList ));

        // add on click listener for the RecyclerView
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return true;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                // Get the action of the MotionEvent
                // Values are MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP, MotionEvent.ACTION_MOVE,
                // MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE, MotionEvent.ACTION_POINTER_DOWN,
                // MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_HOVER_MOVE, MotionEvent.ACTION_SCROLL,
                // MotionEvent.ACTION_HOVER_ENTER, MotionEvent.ACTION_HOVER_EXIT,
                // MotionEvent.ACTION_BUTTON_PRESS, MotionEvent.ACTION_BUTTON_RELEASE
                final int action = e.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    // Get the position of the item in the RecyclerView
                    final int position = rv.getChildAdapterPosition(rv.findChildViewUnder(e.getX(), e.getY()));
                    // If the position is not RecyclerView.NO_POSITION, get the value of the item at
                    // the position in the list of items and change the case of the value.
                    if (position != RecyclerView.NO_POSITION) {
                        // get the value of the item at the position in the list of items
                        final String value = dummyList.get(position);
                        // Toggle the case of the value
                        dummyList.set(position, value.equals(value.toUpperCase()) ? value.toLowerCase() : value.toUpperCase());
                        // Notify the adapter that the item at the position has changed
                        recyclerView.getAdapter().notifyItemChanged(position);
                    }
                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        // Add intent to go to grid btn
        Button goToListBtn = findViewById(R.id.go_to_list_btn);
        goToListBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        });
    }
}