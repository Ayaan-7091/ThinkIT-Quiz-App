package com.example.thinkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    ImageView replayResult,homeResult;
    TextView  txtCorrectResult,txtWrongResult,txtScoreResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        replayResult = findViewById(R.id.replayResult);
        homeResult = findViewById(R.id.homeResult);

        txtCorrectResult = findViewById(R.id.txtCorrectResult);
        txtWrongResult = findViewById(R.id.txtWrongResult);
        txtScoreResult = findViewById(R.id.txtScoreResult);

        Intent intent = getIntent();

        int score = intent.getIntExtra(constants.SCORE,0);
        int correct = intent.getIntExtra(constants.CORRECT,0);
        int wrong = intent.getIntExtra(constants.WRONG,0);

        txtScoreResult.setText(constants.SCORE+String.valueOf(score));
        txtCorrectResult.setText(constants.CORRECT+String.valueOf(correct));
        txtWrongResult.setText(constants.WRONG+String.valueOf(wrong));
    }

    public void replay(){

        Intent replay = new Intent(Result.this,MainActivity.class);
        startActivity(replay);
    }

    public void home(){

        Intent home = new Intent(Result.this, Home.class);
        startActivity(home);
    }

}