package com.example.thinkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

   private Switch Noob,Pro,Expert;
   public int difficulty;

   TextView difficultyText;

    Button backButton;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Noob = findViewById(R.id.switch1);
        Pro = findViewById(R.id.switch2);
        Expert = findViewById(R.id.switch3);

        difficultyText = findViewById(R.id.textView);

        backButton = findViewById(R.id.backButton);


        //shared preferences to store difficulty state
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        difficulty = sharedPreferences.getInt("difficulty", 1); //get difficulty or set to 1 if not found
        updateSwitchState(difficulty);






        Noob.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Pro.setChecked(false);
                    Expert.setChecked(false);
                    difficulty = 1;
                    difficultyText.setText("Difficulty : Noob");
                    Noob.setEnabled(false);
                    Pro.setEnabled(true);
                    Expert.setEnabled(true);
                    saveDifficultyState(difficulty);

                    //to change other buttons color

                    Noob.setBackgroundResource(R.drawable.switch_background);
                    Pro.setBackgroundResource(R.drawable.switch_background_red);
                    Expert.setBackgroundResource(R.drawable.switch_background_red);
                }
            }
        });

        Pro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Noob.setChecked(false);
                    Expert.setChecked(false);
                    difficulty = 2;
                    difficultyText.setText("Difficulty : Pro");
                    Pro.setEnabled(false);
                    Noob.setEnabled(true);
                    Expert.setEnabled(true);
                    saveDifficultyState(difficulty);

                    //to change other buttons color

                    Noob.setBackgroundResource(R.drawable.switch_background_red);
                    Pro.setBackgroundResource(R.drawable.switch_background);
                    Expert.setBackgroundResource(R.drawable.switch_background_red);

                }
            }
        });

        Expert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Noob.setChecked(false);
                    Pro.setChecked(false);
                    difficulty = 3;
                    difficultyText.setText("Difficulty : Expert");
                    Expert.setEnabled(false);
                    Noob.setEnabled(true);
                    Pro.setEnabled(true);
                    saveDifficultyState(difficulty);

                    //to change other buttons color

                    Noob.setBackgroundResource(R.drawable.switch_background_red);
                    Pro.setBackgroundResource(R.drawable.switch_background_red);
                    Expert.setBackgroundResource(R.drawable.switch_background);


                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Home activity
                Intent homeIntent = new Intent(getApplicationContext(), Home.class);
                homeIntent.putExtra("difficulty", difficulty);
                startActivity(homeIntent);

                // Send difficulty value to MainActivity

                // Finish Settings activity to prevent it from staying in the back stack
                finish();
            }
        });



    }

    //method to save the difficulty
    private void saveDifficultyState(int difficulty) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("difficulty", difficulty);
        editor.apply();
    }

    private void updateSwitchState(int difficulty){

        if(difficulty == 1){
            Noob.setChecked(true);
        }
        else if(difficulty == 2){
            Pro.setChecked(true);
        }
        else{
            Expert.setChecked(true);
        }
    }

}