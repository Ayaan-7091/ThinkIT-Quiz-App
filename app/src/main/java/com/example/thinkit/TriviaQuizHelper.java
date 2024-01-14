package com.example.thinkit;

import static com.example.thinkit.TriviaQuizContract.*;

import android.annotation.SuppressLint;
import android.content.ContentValues;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;


public class TriviaQuizHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TriviaQuiz.db";
    private static final int DATABASE_VERSION = 2;

    private SQLiteDatabase db;

    public TriviaQuizHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionTable.TABLE_NAME + "( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionTable.COLUMN_ANSWER_NR + " TEXT " +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        fillQuestionsTable();




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);
    }


    private void addQuestion(TriviaQuestion question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionTable.COLUMN_ANSWER_NR, question.getAnswerNr());

        db.insert(QuestionTable.TABLE_NAME,null,cv);

    }

    private void fillQuestionsTable(){
        TriviaQuestion q1 = new TriviaQuestion("Which programming language is commonly used in AI development?","JAVA","C++","Python","JavaScript","Python");
        addQuestion(q1);

        TriviaQuestion q2 = new TriviaQuestion("What is the primary goal of supervised learning?"," Clustering"," Classification","Reinforcement","Regression","Classification");
        addQuestion(q2);

        TriviaQuestion q3 = new TriviaQuestion("Which type of learning algorithm does not require labeled training data?","Unsupervised Learning","Reinforcement Learning","Supervised Learning","Semi-supervised Learning","Unsupervised Learning");
        addQuestion(q3);

        TriviaQuestion q4 = new TriviaQuestion("Which of the following is a subset of AI that focuses on the development of algorithms allowing computers to learn from data?","Natural Language Processing (NLP)","Machine Learning (ML)","Expert Systems (ES)"," Robotics","Machine Learning (ML)");
        addQuestion(q4);

        TriviaQuestion q5 = new TriviaQuestion("What is the \"singularity\" in the context of AI?","AI surpasses human intelligence"," A type of deep learning network","A famous AI research lab","A measure of algorithm efficiency","AI surpasses human intelligence");
        addQuestion(q5);

        TriviaQuestion q6 = new TriviaQuestion("Which AI application is designed to simulate conversation with human users?","Computer Vision","Speech Recognition"," Autonomous Vehicles","Chatbot","Chatbot");
        addQuestion(q6);

        TriviaQuestion q7 = new TriviaQuestion("What is the purpose of a neural network in machine learning?","To simulate human-like decision-making"," To process natural language","To perform complex calculations"," All of the above"," All of the above");
        addQuestion(q4);

        TriviaQuestion q8 = new TriviaQuestion("What is the term for a type of AI that is designed to mimic the way the human brain works?","Artificial General Intelligence"," Neural Network","Expert Systems (ES)","Genetic Algorithm","Artificial General Intelligence");
        addQuestion(q8);

        TriviaQuestion q9 = new TriviaQuestion("What does the acronym \"IoT\" stand for in the context of AI applications?","Internet of Things","Intelligent Operations Technology"," Integrated Object Tracking","Interactive Optimization Technique","Internet of Things");
        addQuestion(q9);

        TriviaQuestion q10 = new TriviaQuestion("Which type of AI system is designed to perform a specific task without being explicitly programmed for that task?","Cesar AI","Strong AI","Narrow AI","General AI","Narrow AI");
        addQuestion(q10);

        TriviaQuestion q11 = new TriviaQuestion("Which test assesses a machine's ability to exhibit human-like intelligence?","Turing Test","Voight-Kampff Test","Hadoop Test","CAPTCHA Test","Turing Test");
        addQuestion(q11);

        TriviaQuestion q12 = new TriviaQuestion("Who is considered the \"Father of Artificial Intelligence\"?","Alan Turing","Sam Altman","John McCarthy","Charles Babbage","John McCarthy");
        addQuestion(q12);

    }


    public ArrayList<TriviaQuestion> getAllQuestions(){
        ArrayList<TriviaQuestion> questionList = new ArrayList<>();
        db = getWritableDatabase();

        String[] projection ={
                QuestionTable._ID,
                QuestionTable.COLUMN_QUESTION,
                QuestionTable.COLUMN_OPTION1,
                QuestionTable.COLUMN_OPTION2,
                QuestionTable.COLUMN_OPTION3,
                QuestionTable.COLUMN_OPTION4,
                QuestionTable.COLUMN_ANSWER_NR

        };

        Cursor c = db.query(QuestionTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null);




        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    do {
                        TriviaQuestion question = new TriviaQuestion();
                        question.setQuestion(getStringFromCursor(c, QuestionTable.COLUMN_QUESTION));
                        question.setOption1(getStringFromCursor(c, QuestionTable.COLUMN_OPTION1));
                        question.setOption2(getStringFromCursor(c, QuestionTable.COLUMN_OPTION2));
                        question.setOption3(getStringFromCursor(c, QuestionTable.COLUMN_OPTION3));
                        question.setOption4(getStringFromCursor(c, QuestionTable.COLUMN_OPTION4));
                        question.setAnswerNr(getStringFromCursor(c, QuestionTable.COLUMN_ANSWER_NR));

                        questionList.add(question);
                    } while (c.moveToNext());
                }
            } finally {
                c.close();
            }
        } else {
            System.out.println("TriviaQuizHelper"+ "Cursor is null");
        }

        return questionList;
    }

    private String getStringFromCursor(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex != -1) {
            return cursor.getString(columnIndex);
        } else {
            Log.e("TriviaQuizHelper", "Column not found: " + columnName);
            return ""; // or handle it appropriately
        }

    }
}