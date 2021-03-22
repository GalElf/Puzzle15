package com.gale_matany.ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {
    final int SIZE = 4;
    String[][] game;
    // save the location of the empty square
    private int iBlank;
    private int jBlank;
    private int countMoves;

    public GameBoard() {
        game = new String[SIZE][SIZE];
        createRandomGame();
//        create fake game for testing - need to delete before applying
//        for (int i = 0, num = 1; i < game.length * game.length - 1; i++, num++) {
//            game[i / game.length][i % game.length] = num + "";
//        }
//        game[3][3] = "15";
//        game[3][2] = "";
//        iBlank = game.length - 1;
//        jBlank = game.length - 2;
    }

    // create new game - reset all the variables
    public void restartGame() {
        createRandomGame();
    }

    // create random puzzle to play
    private void createRandomGame() {
        for (int i = 0, num = 1; i < SIZE * SIZE - 1; i++, num++) {
            game[i / SIZE][i % SIZE] = num + "";
        }
        game[SIZE - 1][SIZE - 1] = "";
        iBlank = SIZE - 1;
        jBlank = SIZE - 1;
        Random random = new Random();

        // lastNum use for prevent using the last number we used for the swap between the empty box and number
        String lastNum = "0";
        for(int i=0; i<50; i++) {
            List<String> options = findLegalSquareToSwap(game, iBlank, jBlank, lastNum);
            String num = options.get(random.nextInt(options.size()));
            lastNum = num; // save the number we are going to change
            checkIfMoveIsAllowed(num);
        }
        countMoves = 0;
    }

    // for the random create we search the for the option we can swap
    // we choose random number from the option and swap it with the empty square
    private List<String> findLegalSquareToSwap(String[][] puzzle, int i, int j, String lastNum) {
        List<String> options = new ArrayList<String>();
        if (j - 1 >= 0 && !puzzle[i][j - 1].equals(lastNum)) {
            options.add(puzzle[i][j - 1]);
        }
        if (j + 1 < SIZE && !puzzle[i][j + 1].equals(lastNum)) {
            options.add(puzzle[i][j + 1]);
        }
        if (i - 1 >= 0 && !puzzle[i - 1][j].equals(lastNum)) {
            options.add(puzzle[i - 1][j]);
        }
        if (i + 1 < SIZE && !puzzle[i + 1][j].equals(lastNum)) {
            options.add(puzzle[i + 1][j]);
        }
        return options;
    }

    // check if the chosen move is legal and if yes call makeMove function
    public boolean checkIfMoveIsAllowed(String num) {
        if (jBlank - 1 >= 0 && game[iBlank][jBlank - 1].equals(num)) {
            makeMove(iBlank, jBlank - 1);
            return true;
        }
        if (jBlank + 1 < SIZE && game[iBlank][jBlank + 1].equals(num)) {
            makeMove(iBlank, jBlank + 1);
            return true;
        }
        if (iBlank - 1 >= 0 && game[iBlank - 1][jBlank].equals(num)) {
            makeMove(iBlank - 1, jBlank);
            return true;
        }
        if (iBlank + 1 < SIZE && game[iBlank + 1][jBlank].equals(num)) {
            makeMove(iBlank + 1, jBlank);
            return true;
        }
        return false;
    }

    // make one move only after check is legal
    private void makeMove(int i, int j) {
        String str = game[i][j];
        game[i][j] = game[iBlank][jBlank];
        game[iBlank][jBlank] = str;
        iBlank = i;
        jBlank = j;
        countMoves++;
    }

    // Check if the game is over or not
    public boolean checkIfGameOver() {
        for (int i = 0, num = 1; i < SIZE * SIZE - 1; i++, num++) {
            if (!game[i / SIZE][i % SIZE].equals(num + "")) {
                return false;
            }
        }
        return true;
    }

    // count the legal moves the player do
    public int getCountMoves() {
        return countMoves;
    }

    // return the empty box location - i
    public int getIBlank() {
        return iBlank;
    }

    // return the empty box location - j
    public int getJBlank() {
        return jBlank;
    }


}