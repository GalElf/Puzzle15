package com.gale_matany.ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;

import java.util.Locale;

public class GameActivity extends AppCompatActivity {

    private int mm;
    private int ss;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    ss++;
                    if(ss == 60){
                        mm++;
                        ss = 0;
                    }
                    String seconds = String.format(Locale.getDefault(), "%02d", ss);
                    String minutes = String.format(Locale.getDefault(), "%02d", mm);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gameTime.setText(String.format("Time: %s:%s", minutes, seconds));
                        }
                    });
                    SystemClock.sleep(1000);
                }
            }
        }).start();
    }
}