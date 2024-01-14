package com.example.thinkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    int difficulty;
    ImageView btnStart,btnSettings;

    TextView difficultyText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnStart = findViewById(R.id.btnStart);
        btnSettings = findViewById(R.id.btnSettings);

        Intent intent = getIntent();
        difficulty = intent.getIntExtra("difficulty", 1); // Default value is 1 if not provided

        difficultyText = findViewById(R.id.difficultyText);
        //setting the value of difficulty
        String easy = "Difficulty : Easy",medium = "Difficulty : Medium",hard = "Difficulty : Hard";

        if (difficulty == 1){
            difficultyText.setText(easy);
        } else if (difficulty == 2) {
            difficultyText.setText(medium);
        }
        else {
            difficultyText.setText(hard);
        }

    }

    public void startQuiz(View view) {
        Intent startIntent = new Intent(Home.this,MainActivity.class);
        startIntent.putExtra("difficulty", difficulty);
        startActivity(startIntent);
    }

    public void settingsQuiz(View view) {
        Intent settingsIntent = new Intent(Home.this,Settings.class);
        startActivity(settingsIntent);
    }
}