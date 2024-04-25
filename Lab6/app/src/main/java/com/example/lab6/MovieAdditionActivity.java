package com.example.lab6;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MovieAdditionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.movie_addition);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        final MovieDBHelper newMovie = new MovieDBHelper(getApplicationContext());
        final EditText movieName = findViewById(R.id.new_movie_txt);
        Button saveMovie = findViewById(R.id.save_movie_btn);

        saveMovie.setOnClickListener(v -> {
            newMovie.createNew(movieName.getText().toString());
            Toast.makeText(MovieAdditionActivity.this, "Movie Added", Toast.LENGTH_LONG).show();
        });

        // Add intent to save and go to movies list btn
        Button goToMoviesListBtn = findViewById(R.id.show_movies_btn);
        goToMoviesListBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MoviesListActivity.class);
            startActivity(intent);
        });
    }
}