package com.gale_matany.ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle bundle = getIntent().getExtras();
        playMusic = bundle.getBoolean("music");
        mp = MediaPlayer.create(this, R.raw.heart_of_courage);
        stopTime = false;
        if(playMusic)
            mp.start();

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


        initializeGame();
    }

    private void initializeGame() {
        for (int i = 0, num = 1; i < SIZE * SIZE; i++, num++) {
            game[i / SIZE][i % SIZE].setText(puzzle.game[i / SIZE][i % SIZE]);
        }
        gameTime.setText(String.format("Time: %s:%s", "00", "00"));
        moves = puzzle.getCountMoves();
        countMove.setText(String.format("Moves: %s", String.format(Locale.getDefault(), "%04d", moves)));
        mm = 0;
        ss = 0;
        stopTime = false;
        for (int index = 0; index < SIZE * SIZE; index++) {
            game[index / SIZE][index % SIZE].setClickable(true);
        }
        countTime(gameTime);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        boolean legalMove = false;
        int iBlank = puzzle.getIBlank();
        int jBlank = puzzle.getJBlank();
        int i = -1, j = -1;

        boolean winner = false;
        switch (v.getId()) {
            case R.id.num0:
                legalMove = puzzle.checkIfMoveIsAllowed(game[0][0].getText().toString());
                i = 0; j = 0;
                break;
            case R.id.num1:
                legalMove = puzzle.checkIfMoveIsAllowed(game[0][1].getText().toString());
                i = 0; j = 1;
                break;
            case R.id.num2:
                legalMove = puzzle.checkIfMoveIsAllowed(game[0][2].getText().toString());
                i = 0; j = 2;
                break;
            case R.id.num3:
                legalMove = puzzle.checkIfMoveIsAllowed(game[0][3].getText().toString());
                i = 0; j = 3;
                break;
            case R.id.num4:
                legalMove = puzzle.checkIfMoveIsAllowed(game[1][0].getText().toString());
                i = 1; j = 0;
                break;
            case R.id.num5:
                legalMove = puzzle.checkIfMoveIsAllowed(game[1][1].getText().toString());
                i = 1; j = 1;
                break;
            case R.id.num6:
                legalMove = puzzle.checkIfMoveIsAllowed(game[1][2].getText().toString());
                i = 1; j = 2;
                break;
            case R.id.num7:
                legalMove = puzzle.checkIfMoveIsAllowed(game[1][3].getText().toString());
                i = 1; j = 3;
                break;
            case R.id.num8:
                legalMove = puzzle.checkIfMoveIsAllowed(game[2][0].getText().toString());
                i = 2; j = 0;
                break;
            case R.id.num9:
                legalMove = puzzle.checkIfMoveIsAllowed(game[2][1].getText().toString());
                i = 2; j = 1;
                break;
            case R.id.num10:
                legalMove = puzzle.checkIfMoveIsAllowed(game[2][2].getText().toString());
                i = 2; j = 2;
                break;
            case R.id.num11:
                legalMove = puzzle.checkIfMoveIsAllowed(game[2][3].getText().toString());
                i = 2; j = 3;
                break;
            case R.id.num12:
                legalMove = puzzle.checkIfMoveIsAllowed(game[3][0].getText().toString());
                i = 3; j = 0;
                break;
            case R.id.num13:
                legalMove = puzzle.checkIfMoveIsAllowed(game[3][1].getText().toString());
                i = 3; j = 1;
                break;
            case R.id.num14:
                legalMove = puzzle.checkIfMoveIsAllowed(game[3][2].getText().toString());
                i = 3; j = 2;
                break;
            case R.id.num15:
                legalMove = puzzle.checkIfMoveIsAllowed(game[3][3].getText().toString());
                i = 3; j = 3;
                break;
            case R.id.reset_game:
                puzzle.restartGame();
                initializeGame();
                break;
        }
        if (legalMove) {
            moves = puzzle.getCountMoves();
            countMove.setText(String.format("Moves: %s", String.format(Locale.getDefault(), "%04d", moves)));
            String lastNum = game[i][j].getText().toString();
            game[i][j].setText("");
            game[iBlank][jBlank].setText(lastNum);
        }
        if (puzzle.checkIfGameOver()) {
            for (int index = 0; index < SIZE * SIZE; index++) {
                game[index / SIZE][index % SIZE].setClickable(false);
            }
            Toast.makeText(this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
            stopTime = true;
        }
    }

    private void countTime(TextView gameTime){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stopTime) {
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