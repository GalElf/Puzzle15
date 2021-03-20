package com.gale_matany.ex1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.annotation.SuppressLint;
import android.app.DirectAction;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    final int SIZE = 4;
    private int mm;
    private int ss;
    private int moves;
    private TextView countMove;
    private TextView[][] game;
    private GameBoard puzzle;
    private Button startNewGame;
    private TextView gameTime;
    private boolean playMusic;
    private boolean stopTime;
    private MediaPlayer mp;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle bundle = getIntent().getExtras();
        playMusic = bundle.getBoolean("music");
        mp = MediaPlayer.create(this, R.raw.heart_of_courage);
        stopTime = false;
        if(playMusic) {
            mp.setLooping(true);
            mp.start();
        }

        countMove = (TextView) findViewById(R.id.move_count);
        startNewGame = (Button) findViewById(R.id.reset_game);
        gameTime = (TextView) findViewById(R.id.game_time);
        game = new TextView[SIZE][SIZE];
        puzzle = new GameBoard();

        startNewGame.setOnClickListener(this);
        for (int i = 0, num = 1; i < SIZE * SIZE; i++, num++) {
            String txtID = "num" + i;
            int resID = getResources().getIdentifier(txtID, "id", getPackageName());
            game[i / SIZE][i % SIZE] = ((TextView) findViewById(resID));
            game[i / SIZE][i % SIZE].setOnClickListener(this);
        }

        thread = null;
        initializeGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopTime = false;
        countTime();
        if(!mp.isPlaying() && playMusic)
            mp.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopTime = true;
        if(mp.isPlaying())
            mp.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mp.release();
        mp = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.reset_game){
            puzzle.restartGame();
            initializeGame();
            countTime();
        }

        boolean legalMove = false;
        int iBlank = puzzle.getIBlank();
        int jBlank = puzzle.getJBlank();
        int i = -1, j = -1;

        for (int index = 0; index < SIZE * SIZE; index++) {
            if(game[index / SIZE][index % SIZE].getId() == v.getId()){
                legalMove = puzzle.checkIfMoveIsAllowed(game[index / SIZE][index % SIZE].getText().toString());
                i = index / SIZE;
                j = index % SIZE;
            }
        }

        if (legalMove) {
            moves = puzzle.getCountMoves();
            countMove.setText(String.format("Moves: %s", String.format(Locale.getDefault(), "%04d", moves)));
            String lastNum = game[i][j].getText().toString();
            game[i][j].setText("");
            game[i][j].setBackgroundResource(R.drawable.non_wood);
            game[iBlank][jBlank].setText(lastNum);
            game[iBlank][jBlank].setBackgroundResource(R.drawable.wood);
        }
        if (puzzle.checkIfGameOver()) {
            turnOffClicks(false);
            Toast.makeText(this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
            stopTime = true;
        }
    }

    private void initializeGame() {
        for (int i = 0, num = 1; i < SIZE * SIZE; i++, num++) {
            game[i / SIZE][i % SIZE].setText(puzzle.game[i / SIZE][i % SIZE]);
            game[i / SIZE][i % SIZE].setBackgroundResource(R.drawable.wood);
        }
        gameTime.setText(String.format("Time: %s:%s", "00", "00"));
        moves = puzzle.getCountMoves();
        countMove.setText(String.format("Moves: %s", String.format(Locale.getDefault(), "%04d", moves)));
        mm = 0;
        ss = 0;
        stopTime = false;
        turnOffClicks(true);
        game[puzzle.getIBlank()][puzzle.getJBlank()].setBackgroundResource(R.drawable.non_wood);
//        countTime();
    }

    private void countTime(){
        if (thread == null) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!stopTime) {
                        ss++;
                        if (ss == 60) {
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
                    thread = null;
                }
            });
            thread.start();
        }
    }

    private void turnOffClicks(boolean onOrOff) {
        for (int index = 0; index < SIZE * SIZE; index++) {
            game[index / SIZE][index % SIZE].setClickable(onOrOff);
        }
    }
}