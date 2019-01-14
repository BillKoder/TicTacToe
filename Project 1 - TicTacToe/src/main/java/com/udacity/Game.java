package com.udacity;

import java.util.Arrays;

/**
 * Created by udacity 2016
 * The Main class containing game logic and backend 2D array
 */
public class Game {

    private char turn; // who's turn is it, 'x' or 'o' ? x always starts
    private boolean twoPlayer; // true if this is a 2 player game, false if AI playing
    private char [][] grid; // a 2D array of chars representing the game grid
    private int freeSpots; // counts the number of empty spots remaining on the board (starts from 9  and counts down)
    private static GameUI gui;

    /**
     * Create a new single player game
     *
     */
    public Game() {
        newGame(false);
    }

    /**
     * Create a new game by clearing the 2D grid and restarting the freeSpots counter and setting the turn to x
     * @param twoPlayer: true if this is a 2 player game, false if playing against the computer
     */
    public void newGame(boolean twoPlayer){
        //sets a game to one or two player
        this.twoPlayer = twoPlayer;

        // initialize all chars in 7x6 game grid to '-'
        grid = new char[7][6];
        //fill all empty slots with -
        for (int i=0; i<7; i++){
            for (int j=0; j<6; j++){
                grid[i][j] = '-';
            }
        }
        //start with 9 free spots and decrement by one every time a spot is taken
        freeSpots = 42;
        //x always starts
        turn = 'x';
    }


    /**
     * Gets the char value at that particular position in the grid array
     * @param i the x index of the 2D array grid
     * @param j the y index of the 2D array grid
     * @return the char value at the position (i,j):
     *          'x' if x has played here
     *          'o' if o has played here
     *          '-' if no one has played here
     *          '!' if i or j is out of bounds
     */
    public char gridAt(int i, int j){
        if(i>=7||j>=6||i<0||j<0)
            return '!';
        return grid[i][j];
    }

    /**
     * Places current player's char at position (i,j)
     * Uses the variable turn to decide what char to use
     * @param i the x index of the 2D array grid
     * @param j the y index of the 2D array grid
     * @return boolean: true if play was successful, false if invalid play
     */
    public boolean playAt(int i, int j){
        //check for index boundries
        if(i>=7||j>=6||i<0||j<0)
            return false;
        //check if this position is available
        if(grid[i][j] != '-'){
            return false; //bail out if not available
        }
        //update grid with new play based on who's turn it is
            int topLocation = 0;
            for (int row = 0; row <= 5; row++){
                if (grid[i][row] == '-')
                    topLocation = row;
                else break;
                }
        grid[i][topLocation] = turn;

        //update free spots
        freeSpots--;
        return true;
    }


    /**
     * Override
     * @return string format for 2D array values
     */
    public String toString(){
        return Arrays.deepToString(this.grid);
    }

    /**
     * Performs the winner check and displays a message if game is over
     * @return true if game is over to start a new game
     */
    public boolean doChecks() {
        //check if there's a winner or tie ?
        String winnerMessage = checkGameWinner(grid);
        if (!winnerMessage.equals("None")) {
            gui.gameOver(winnerMessage);
            newGame(false);
            return true;
        }
        return false;
    }

    /**
     * Allows computer to play in a single player game or switch turns for 2 player game
     */
    public void nextTurn(){
        //check if single player game, then let computer play turn
        if(!twoPlayer){
            if(freeSpots == 0){
                return ; //bail out if no more free spots
            }
            int ai_i;
            //randomly pick a position (ai_i)
            ai_i = (int) (Math.random() * 7);
            //update grid with new play, computer is always o
            int topLocation = 0;
            for (int row = 5; row >= 0; row--){
                if (grid[ai_i][row] == '-') {
                    topLocation = row;
                    break;
                }
            }
            if (grid[ai_i][topLocation] == 'x' || grid[ai_i][topLocation] == 'o') {
                nextTurn();
                return;
            }
            grid[ai_i][topLocation] = 'o';
            //update free spots
            freeSpots--;
        }
        else{
            //change turns
            if(turn == 'x'){
                turn = 'o';
            }
            else{
                turn = 'x';
            }
        }
        return;
    }


    /**
     * Checks if the game has ended either because a player has won, or if the game has ended as a tie.
     * If game hasn't ended the return string has to be "None",
     * If the game ends as tie, the return string has to be "Tie",
     * If the game ends because there's a winner, it should return "X wins" or "O wins" accordingly
     * @param grid 2D array of characters representing the game board
     * @return String indicating the outcome of the game: "X wins" or "O wins" or "Tie" or "None"
     */
    public String checkGameWinner(char [][]grid){
        String result = "None";
        //Student code goes here ...
        boolean win;
        char[] solution = new char[4];
        int nextSpace;
        int diagonalYChange;


        // Check first column
        solution[0] = grid[0][0];
        solution[1] = grid[0][1];
        solution[2] = grid[0][2];
        if (checkSolution(solution) == true){
            result = solution[0] + " Wins by first column!";
        }

        // Check second column
        solution[0] = grid[1][0];
        solution[1] = grid[1][1];
        solution[2] = grid[1][2];
        if (checkSolution(solution) == true){
            result = solution[0] + " Wins by second column!";
        }

        // Check third column
        solution[0] = grid[2][0];
        solution[1] = grid[2][1];
        solution[2] = grid[2][2];
        if (checkSolution(solution) == true){
            result = solution[0] + " Wins by third column!";
        }

        // Check first row
        solution[0] = grid[0][0];
        solution[1] = grid[1][0];
        solution[2] = grid[2][0];
        if (checkSolution(solution) == true){
            result = solution[0] + " Wins by first row!";
        }

        // Check second row
        solution[0] = grid[0][1];
        solution[1] = grid[1][1];
        solution[2] = grid[2][1];
        if (checkSolution(solution) == true){
            result = solution[0] + " Wins by second row!";
        }

        // Check third row
        solution[0] = grid[0][2];
        solution[1] = grid[1][2];
        solution[2] = grid[2][2];
        if (checkSolution(solution) == true){
            result = solution[0] + " Wins by third row!";
        }

        // Check Upward angle
        solution[0] = grid[0][2];
        solution[1] = grid[1][1];
        solution[2] = grid[2][0];
        if (checkSolution(solution) == true){
            result = solution[0] + " Wins by upward angle!";
        }

        // Check Downward angle
        solution[0] = grid[0][0];
        solution[1] = grid[1][1];
        solution[2] = grid[2][2];
        if (checkSolution(solution) == true){
            result = solution[0] + " Wins by downward angle!";
        }

        // Check Horizontal
        for (int y = 0; y < 6; y++){
            for (int x = 0; x < 4; x++){
                if (grid[x][y] != '-') {
                    solution[0] = grid[x][y];
                    nextSpace = x;
                    nextSpace++;
                    if (grid[nextSpace][y] == solution[0]) {
                        solution[1] = grid[nextSpace][y];
                        nextSpace++;
                        if (grid[nextSpace][y] == solution[0]) {
                            solution[2] = grid[nextSpace][y];
                            nextSpace++;
                            if (grid[nextSpace][y] == solution[0]) {
                                solution[3] = grid[nextSpace][y];
                                if (checkSolution(solution) == true){
                                    result = solution[0] + " Wins Horizontally!";
                                }
                            }
                        }
                    }
                }
            }
        }

        // Check Vertical
        for (int x = 0; x < 7; x++){
            for (int y = 5; y > 2; y --){
                if (grid[x][y] != '-') {
                    solution[0] = grid[x][y];
                    nextSpace = y;
                    nextSpace--;
                    if (grid[x][nextSpace] == solution[0]) {
                        solution[1] = grid[x][nextSpace];
                        nextSpace--;
                        if (grid[x][nextSpace] == solution[0]) {
                            solution[2] = grid[x][nextSpace];
                            nextSpace--;
                            if (grid[x][nextSpace] == solution[0]) {
                                solution[3] = grid[x][nextSpace];
                                if (checkSolution(solution) == true){
                                    result = solution[0] + " Wins Vertically!";
                                }
                            }
                        }
                    }
                }
            }
        }

        //Check Diagonal Down
        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 3; y ++){
                if (grid[x][y] != '-') {
                    solution[0] = grid[x][y];
                    nextSpace = x;
                    diagonalYChange = y;
                    nextSpace++;
                    diagonalYChange++;
                    if (grid[nextSpace][diagonalYChange] == solution[0]) {
                        solution[1] = grid[nextSpace][diagonalYChange];
                        nextSpace++;
                        diagonalYChange++;
                        if (grid[nextSpace][diagonalYChange] == solution[0]) {
                            solution[2] = grid[nextSpace][diagonalYChange];
                            nextSpace++;
                            diagonalYChange++;
                            if (grid[nextSpace][diagonalYChange] == solution[0]) {
                                solution[3] = grid[nextSpace][diagonalYChange];
                                if (checkSolution(solution) == true){
                                    result = solution[0] + " Wins Diagonally!";
                                }
                            }
                        }
                    }
                }
            }
        }

        //Check Diagonal Up
        for (int x = 0; x < 4; x++){
            for (int y = 5; y > 2; y --){
                if (grid[x][y] != '-') {
                    solution[0] = grid[x][y];
                    nextSpace = x;
                    diagonalYChange = y;
                    nextSpace++;
                    diagonalYChange--;
                    if (grid[nextSpace][diagonalYChange] == solution[0]) {
                        solution[1] = grid[nextSpace][diagonalYChange];
                        nextSpace++;
                        diagonalYChange--;
                        if (grid[nextSpace][diagonalYChange] == solution[0]) {
                            solution[2] = grid[nextSpace][diagonalYChange];
                            nextSpace++;
                            diagonalYChange--;
                            if (grid[nextSpace][diagonalYChange] == solution[0]) {
                                solution[3] = grid[nextSpace][diagonalYChange];
                                if (checkSolution(solution) == true){
                                    result = solution[0] + " Wins Diagonally!";
                                }
                            }
                        }
                    }
                }
            }
        }

        if (freeSpots == 0){
            result = "Tie!";
        }

        return result;
    }

    private boolean checkSolution(char[] solution) {
        char value = solution[0];
        boolean result = false;
        if (value == 'o' || value == 'x') {
            for (int i = 0; i < 4; i++){
                if (value == solution[i]) {
                    result = true;
                }
                else{
                    result = false;
                   break;
                }

            }

        }
        return result;
    }

    /**
     * Main function
     * @param args command line arguments
     */
    public static void main(String args[]){
        Game game = new Game();
        gui = new GameUI(game);
    }

}
