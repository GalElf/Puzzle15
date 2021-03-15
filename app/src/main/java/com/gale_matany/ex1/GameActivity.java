package com.gale_matany.ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private int mm;
    private int ss;
    private String seconds;
    private String minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView gameTime = findViewById(R.id.game_time);
        countTime(gameTime);
        
    }


    private void countTime(TextView gameTime){
        mm = 0;
        ss = 0;
        seconds = "00";
        minutes = "00";

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    ss++;
                    if(ss == 60){
                        mm++;
                        ss = 0;
                    }
                    seconds = "" + ss;
                    minutes = "" + mm;
                    if(ss < 10){
                        seconds = "0" + ss;
                    }
                    if(mm < 10){
                        minutes = "0" + mm;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gameTime.setText("Time: " + minutes + ":" + seconds);
                        }
                    });
                    SystemClock.sleep(1000);
                }
            }
        }).start();
    }
}