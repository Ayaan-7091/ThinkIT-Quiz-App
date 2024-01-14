package com.example.thinkit;

import android.content.Context;
import android.media.MediaPlayer;

public class playAudio {
   private Context mContext;
   private MediaPlayer mediaPlayer;

   public playAudio(Context context){
       this.mContext = context;
   }

   public void correctMusic(){
       int correctAudio = R.raw.correct;
       play(correctAudio);
   }

    public void wrongMusic(){
        int wrongAudio = R.raw.wrong;
        play(wrongAudio);
    }


    public void timeOverMusic(){
        int correctAudio = R.raw.time_over;
        play(correctAudio);
    }

   public void play(int audioFile){

       mediaPlayer = MediaPlayer.create(mContext,audioFile);

       mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
           @Override
           public void onPrepared(MediaPlayer mediaPlayer) {
               mediaPlayer.start();
           }
       });

       mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
           @Override
           public void onCompletion(MediaPlayer mediaPlayer) {
               mediaPlayer.release();
           }
       });
   }



}
