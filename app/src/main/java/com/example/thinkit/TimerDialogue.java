package com.example.thinkit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerDialogue {
    private Context mContext;
    private Dialog TimerDialog;

    private TextView TxtViewTimeUp;

    public TimerDialogue(Context mContext) {
        this.mContext = mContext;
    }

    public void timerDialog(){
        TimerDialog = new Dialog(mContext);
        TimerDialog.setContentView(R.layout.timer_dialogue);

        final Button btTimer = (Button) TimerDialog.findViewById(R.id.btTimer);

        btTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerDialog.dismiss();
                Intent intent = new Intent(mContext,Home.class);
                mContext.startActivity(intent);
            }
        });

        TimerDialog.show();
        TimerDialog.setCancelable(false);
        TimerDialog.setCanceledOnTouchOutside(false);

//        FOR SETTING TRANSPARENT BACKGROUND WINDOW
        TimerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}


