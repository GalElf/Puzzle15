package com.gale_matany.ex1;

import java.util.Random;

public class GameBoard {

    String[][] game;
    // save the location of the empty square
    private int iBlank;
    private int jBlank;
    private int countMoves;


    public GameBoard() {
        game = new String[4][4];
        createGame();
    }


    public void createGame() {
        createRandomGame();
        countMoves = 0;
//         create fake game for testing - need to delete before applying
        for (int i = 0, num = 1; i < game.length * game.length - 1; i++, num++) {
            game[i / game.length][i % game.length] = num + "";
        }
        game[3][3] = "15";
        game[3][2] = "";
        iBlank = game.length - 1;
        jBlank = game.length - 2;
    }

    // create new game - reset all the variables
    public void restartGame() {
        createGame();
    }

    // create random puzzle to play
    public void createRandomGame() {
        int[] puzzle = new int[game.length * game.length];
        for (int i = 0, num = 1; i < puzzle.length - 1; i++, num++) {
            puzzle[i] = num;
        }
        puzzle[puzzle.length - 1] = 0;
        Random random = new Random();
        do {
            int i = puzzle.length - 1;
            while (i > 1) {
                int index = random.nextInt(i);
                int num = puzzle[i];
                puzzle[i] = puzzle[index];
                puzzle[index] = num;
                i--;
            }
            // as long the puzzle isn't solvable create new random puzzle
        } while (!checkIfSolvable(puzzle));
        for (int i = 0; i < game.length * game.length; i++) {
            if(puzzle[i] == 0)
                game[i / game.length][i % game.length] = "";
            else
                game[i / game.length][i % game.length] = puzzle[i] + "";
        }
    }

    // check if the random puzzle has solution or not
    private boolean checkIfSolvable(int[] puzzle) {
        int count = 0;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = i; j < puzzle.length; j++) {
                if (puzzle[j] == 0) {
                    iBlank = i / game.length;
                    jBlank = i % game.length;
                }
                if (puzzle[j] != 0 && puzzle[i] > puzzle[j]) {
                    count++;
                }
            }
        }
        if(iBlank % 2 == 0) {
            return count % 2 != 0;
        }
        return count % 2 == 0;
    }

    // check if the chosen move is legal and if yes call makeMove function
    public boolean checkIfMoveIsAllowed(String num) {
        if (jBlank - 1 >= 0 && game[iBlank][jBlank - 1].equalsIgnoreCase(num)) {
            makeMove(iBlank, jBlank - 1);
            return true;
        }
        if (jBlank + 1 < game.length && game[iBlank][jBlank + 1].equalsIgnoreCase(num)) {
            makeMove(iBlank, jBlank + 1);
            return true;
        }
        if (iBlank - 1 >= 0 && game[iBlank - 1][jBlank].equalsIgnoreCase(num)) {
            makeMove(iBlank - 1, jBlank);
            return true;
        }
        if (iBlank + 1 < game.length && game[iBlank + 1][jBlank].equalsIgnoreCase(num)) {
            makeMove(iBlank + 1, jBlank);
            return true;
        }
        return false;
    }

    // make one move only after check is legal
    public void makeMove(int i, int j) {
        String str = game[i][j];
        game[i][j] = game[iBlank][jBlank];
        game[iBlank][jBlank] = str;
        iBlank = i;
        jBlank = j;
        countMoves++;
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

    // Check if the game is over or not
    public boolean checkIfGameOver() {
        for (int i = 0, num = 1; i < game.length * game.length - 1; i++, num++) {
            if (!game[i / game.length][i % game.length].equals(num + "")) {
                return false;
            }
        }
        return true;
    }

}