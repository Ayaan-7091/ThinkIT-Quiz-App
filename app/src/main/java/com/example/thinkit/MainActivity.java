package com.example.thinkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TriviaQuizHelper triviaQuizHelper;
    TriviaQuestion currentQuestion;

    Settings settings;

    Button buttonA,buttonB,buttonC,buttonD;
    TextView questionText,txtTotalQuesText,timeText,coinText,txtCorrect,txtWrong,difficultyText;

    List<TriviaQuestion> list;

    AnimationDrawable anim;

    Animation wrongAnsAnimation;

    playAudio playAudio;
    int qid=1;
    int sizeofQuiz = 10;

    int correct = 0;
    int wrong = 0;

    int coins = 0;

    private final Handler handler = new Handler();
    private final Handler handler2 = new Handler();

    CountDownTimer countDownTimer;
    int timeValue = 90;

    int difficulty;
    TimerDialogue timerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        triviaQuizHelper = new TriviaQuizHelper(this);

        TriviaQuizHelper dbHelper = new TriviaQuizHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Log.e("Trivia","Error Located");

        timerDialog = new TimerDialogue(this);

            //getting the difficulty value from settings activity intent
        Intent intent = getIntent();
        difficulty = intent.getIntExtra("difficulty", 1); // Default value is 1 if not provided
//        All Variables Declaration
     buttonA = findViewById(R.id.buttonA);

     buttonB = findViewById(R.id.buttonB);
     buttonC = findViewById(R.id.buttonC);
     buttonD = findViewById(R.id.buttonD);

     questionText = findViewById(R.id.txtTriviaQuestion);
     txtTotalQuesText = findViewById(R.id.txtTotalQuestion);
     txtCorrect = findViewById(R.id.txtCorrect);
     txtWrong = findViewById(R.id.txtWrong);
     coinText = findViewById(R.id.txtCoin);
     timeText = findViewById(R.id.txtTimer);



     txtCorrect.setText(String.valueOf(correct));
     txtWrong.setText(String.valueOf(wrong));
     coinText.setText(String.valueOf(coins));






        playAudio = new playAudio(this);
        wrongAnsAnimation = AnimationUtils.loadAnimation(this,R.anim.wromg_ans_animation);
        wrongAnsAnimation.setRepeatCount(3);

//     Fetching the questionns from the Database
        list = triviaQuizHelper.getAllQuestions();
        Collections.shuffle(list);

//        TriviaQuestion currentQuestion;

        currentQuestion = list.get(qid);
        txtTotalQuesText.setText(qid + "/" + sizeofQuiz );
        coinText.setText("0");

      //call the method to start countdownTimer
        setCountdownTimer(difficulty);

        updateQueAnsOptions();


//        error handling bu GPT
//        if (!list.isEmpty() ) {
//            currentQuestion = list.get(qid);
//            int size = list.size();
//
//            questionText.setText(String.valueOf(size));
//
//        } else {
//            questionText.setText("Else Block Was Executed");
//        }


    }




    public void UpdateCorrect(int correct){
        txtCorrect.setText(String.valueOf(correct));
    }

    public void UpdateWrong(int wrong){ txtWrong.setText(String.valueOf(wrong));}

    public void UpdateCoins(int coins){ coinText.setText(String.valueOf(coins));}

    private void updateQueAnsOptions() {
        questionText.setText(currentQuestion.getQuestion());
        buttonA.setText(currentQuestion.getOption1());
        buttonB.setText(currentQuestion.getOption2());
        buttonC.setText(currentQuestion.getOption3());
        buttonD.setText(currentQuestion.getOption4());

//        CODE TO RESET BUTTON COLOR
        buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.yellow));
        buttonB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.yellow));
        buttonC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.yellow));
        buttonD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.yellow));


    }

    private void setNewQuestion(){
        qid++;
        txtTotalQuesText.setText(qid + "/" + sizeofQuiz );

        currentQuestion = list.get(qid);
        enableOptions();
        updateQueAnsOptions();

    }


    public void buttonA(View view){

        disableOptions();
        buttonAnimation(buttonA);




        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            if(currentQuestion.getOption1().equals(currentQuestion.getAnswerNr())){
                buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                correct++;
                UpdateCorrect(correct);
                playAudio.correctMusic();

                int newCoins = calculateCoins(timeValue);
                coins += newCoins;
                UpdateCoins(coins);

                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(qid != sizeofQuiz){
                            setNewQuestion();
                        }
                        else{
                            finalResult();

                        }
                    }
                },1500);
            }
            else{
                wrong++;
                UpdateWrong(wrong);
                buttonA.startAnimation(wrongAnsAnimation);
                playAudio.wrongMusic();
                buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                Handler handler3 = new Handler();
                handler3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(currentQuestion.getOption2().equals(currentQuestion.getAnswerNr())) {
                            buttonB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                        }
                        else if(currentQuestion.getOption3().equals(currentQuestion.getAnswerNr())) {
                            buttonC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                        }
                        else{
                            buttonD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                        }
                    }
                },500);

//                code to change the question
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(qid != sizeofQuiz){
                            setNewQuestion();
                        }
                        else{
                            finalResult();


                        }
                    }
                },1500);
            }
            }

        },1500);

    }

//    BUTTON B
    public void buttonB(View view){


        disableOptions();
        buttonAnimation(buttonB);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(currentQuestion.getOption2().equals(currentQuestion.getAnswerNr())){
                    buttonB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                    correct++;
                    UpdateCorrect(correct);
                    playAudio.correctMusic();

                    int newCoins = calculateCoins(timeValue);
                    coins += newCoins;
                    UpdateCoins(coins);

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(qid != sizeofQuiz){
                                setNewQuestion();
                            }
                            else{
                                finalResult();


                            }
                        }
                    },1500);
                }
                else{
                    wrong++;
                    UpdateWrong(wrong);
                    buttonB.startAnimation(wrongAnsAnimation);
                    playAudio.wrongMusic();

                    buttonB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(currentQuestion.getOption1().equals(currentQuestion.getAnswerNr())) {
                                buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }
                            else if(currentQuestion.getOption3().equals(currentQuestion.getAnswerNr())) {
                                buttonC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }
                            else{
                                buttonD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }
                        }
                    },500);

//                code to change the question
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(qid != sizeofQuiz){
                                setNewQuestion();
                            }
                            else{
                                finalResult();


                            }
                        }
                    },1500);
                }
            }

        },1500);

//        BUTTON C
    } public void buttonC(View view){

        disableOptions();
        buttonAnimation(buttonC);




        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(currentQuestion.getOption3().equals(currentQuestion.getAnswerNr())){
                    buttonC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                    correct++;
                    UpdateCorrect(correct);
                    playAudio.correctMusic();

                    int newCoins = calculateCoins(timeValue);
                    coins += newCoins;
                    UpdateCoins(coins);

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(qid != sizeofQuiz){
                                setNewQuestion();
                            }
                            else{
                                finalResult();


                            }
                        }
                    },1500);
                }
                else{
                    wrong++;
                    UpdateWrong(wrong);
                    buttonC.startAnimation(wrongAnsAnimation);
                    playAudio.wrongMusic();

                    buttonC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(currentQuestion.getOption2().equals(currentQuestion.getAnswerNr())) {
                                buttonB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }
                            else if(currentQuestion.getOption1().equals(currentQuestion.getAnswerNr())) {
                                buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }
                            else{
                                buttonD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }
                        }
                    },500);

//                code to change the question
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(qid != sizeofQuiz){
                                setNewQuestion();
                            }
                            else{
                                finalResult();

                            }
                        }
                    },1500);
                }
            }

        },1500);

//        BUTTON D

    } public void buttonD(View view){


        disableOptions();
        buttonAnimation(buttonD);



        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(currentQuestion.getOption4().equals(currentQuestion.getAnswerNr())){
                    buttonD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                    correct++;
                    UpdateCorrect(correct);
                    playAudio.correctMusic();

                    int newCoins = calculateCoins(timeValue);
                    coins += newCoins;
                    UpdateCoins(coins);

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(qid != sizeofQuiz){
                                setNewQuestion();
                            }
                            else{
                                finalResult();


                            }
                        }
                    },1500);
                }
                else{
                    wrong++;
                    UpdateWrong(wrong);
                    buttonD.startAnimation(wrongAnsAnimation);
                    playAudio.wrongMusic();

                    buttonD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(currentQuestion.getOption2().equals(currentQuestion.getAnswerNr())) {
                                buttonB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }
                            else if(currentQuestion.getOption3().equals(currentQuestion.getAnswerNr())) {
                                buttonC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }
                            else{
                                buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }
                        }
                    },500);

//                code to change the question
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(qid != sizeofQuiz){
                                setNewQuestion();
                            }
                            else{
                                finalResult();

                            }
                        }
                    },1500);
                }
            }

        },1500);

    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        countDownTimer.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
        finish();
    }

    private void disableOptions(){
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);
    }

    private void enableOptions(){
        buttonA.setEnabled(true);
        buttonB.setEnabled(true);
        buttonC.setEnabled(true);
        buttonD.setEnabled(true);
    }

    private int calculateCoins(int timeValue) {

        if (difficulty == 3) {
            int timeBonus = 0;
            if (timeValue >= 80) {
                timeBonus = 10;
            } else if (timeValue > 60 && timeValue < 80) {
                timeBonus = 8;
            } else if (timeValue > 40 && timeValue < 60) {
                timeBonus = 6;
            } else if (timeValue > 20 && timeValue < 40) {
                timeBonus = 4;
            } else if (timeValue > 0 && timeValue < 20) {
                timeBonus = 2;
            }
            return timeBonus;
        }
        else if (difficulty == 2) {
            int timeBonus = 0;
            if (timeValue >= 150) {
                timeBonus = 10;
            } else if (timeValue > 120 && timeValue < 150) {
                timeBonus = 8;
            } else if (timeValue > 80 && timeValue < 120) {
                timeBonus = 6;
            } else if (timeValue > 40 && timeValue < 80) {
                timeBonus = 4;
            } else if (timeValue > 0 && timeValue < 40) {
                timeBonus = 2;
            }
            return timeBonus;
        }
        else {
            int timeBonus = 0;
            if (timeValue >= 230) {
                timeBonus = 10;
            } else if (timeValue > 190 && timeValue < 230) {
                timeBonus = 8;
            } else if (timeValue > 130 && timeValue < 190) {
                timeBonus = 6;
            } else if (timeValue > 70 && timeValue < 130) {
                timeBonus = 4;
            } else if (timeValue > 0 && timeValue < 70) {
                timeBonus = 2;
            }
            return timeBonus;
        }
    }

    private void buttonAnimation(Button buttonA){
        //        BUTTON ANIMATION LOGIC - STARTS HERE ------------
        Handler AnimationHandler = new Handler();

        AnimationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
            }
        },100);


        AnimationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
            }
        },600);

        AnimationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.yellow));
            }
        },1100);
    }

    private void finalResult(){
        coinText.setText("0");
        Intent result = new Intent(MainActivity.this,Result.class);
        result.putExtra(constants.SCORE,coins);
        result.putExtra(constants.CORRECT,correct);
        result.putExtra(constants.WRONG,wrong);
        startActivity(result);

    }

    private void setCountdownTimer(int difficulty){

        int totalTime;

        if (difficulty == 1){
            totalTime = 270000;
        }
        else if (difficulty == 2){
            totalTime = 180000;
        }

        else{
            totalTime = 90000;
        }
        //logic to set timer according to the difficulty
        countDownTimer = new CountDownTimer(totalTime,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeValue = (int) millisUntilFinished / 1000; // update timeValue based on remaining time
                timeText.setText(String.valueOf(timeValue));

                if(timeValue == 0){
//                    DISABLE THE BUTTONS
                    Toast.makeText(MainActivity.this, "Time Over !", Toast.LENGTH_SHORT).show();
                    timerDialog.timerDialog();
                    countDownTimer.cancel();
                    playAudio.timeOverMusic();

                }
            }

            @Override
            public void onFinish() {
//                DISPLAY DIALOGUE HERE
            }

        }.start();
    }

}