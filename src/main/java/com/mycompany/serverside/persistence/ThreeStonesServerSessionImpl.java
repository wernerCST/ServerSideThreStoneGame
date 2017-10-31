package com.mycompany.serverside.persistence;

import com.mycompany.serverside.business.ThreeStonesServerGame;

/**
 * The ThreeStonesServerSessionImpl class defines an implementation of the 
 * ThreeStonesServerSession interface. This session is to be created by a 
 * ThreeStonesServer when a client wishes to connect. The session tracks scores 
 * earned by each player and the coordinates of the server's most recent move, 
 * and it contains the ThreeStonesServerGame object, which handles logic for 
 * server stone placement and scoring for both players.
 * 
 * @author Werner Castanaza
 * @author Peter Bellefleur MacCaul
 */
public class ThreeStonesServerSessionImpl implements ThreeStonesServerSession {
    //game logic object
    private ThreeStonesServerGame game;
    //flag to track if game is over
    private boolean isGameOver;
    //array to track server moves: [0] is x coord, [1] is y coord
    private int[] serverCoords;
    //array to track player scores: [0] is client score, [1] is server score
    private int[] scores;
    
    /**
     * Default constructor. Instantiates game logic & arrays.
     */
    public ThreeStonesServerSessionImpl() {
        game = new ThreeStonesServerGame();
        isGameOver = false;
        serverCoords = new int [2];
        scores = new int[2];
    }
    
    /**
     * Sets up a new game to be played. Sets default values in scores and 
     * server coordinate arrays.
     */
    @Override
    public void newGame() {
        System.out.println("Starting new game...");
        scores[0] = 0;
        scores[1] = 0;
        serverCoords[0] = -1;
        serverCoords[1] = -1;
        game.buildBoard();
    }

    /**
     * Resets the game logic to allow a new game to be played. 
     */
    @Override
    public void restartGame() {
        System.out.println("Restarting game...");
        game = new ThreeStonesServerGame();
        isGameOver = false;
        newGame();
    }

    /**
     * Places a client's most recent Stone on the board. Retrieves the server's 
     * move, the current scores of each player, and the flag indicating whether 
     * or not the game has ended.
     * 
     * @param x The x-coordinate of a client's Stone placement.
     * @param y The y=coordinate of a client's Stone placement.
     */
    @Override
    public void setClientMove(int x, int y) {
        System.out.println("                        ****1");
        System.out.println("Client has played Stone at: " + x + ", " + y);
        isGameOver = game.playRoundOfGame(x, y);
        serverCoords[0] = game.getServerX();
        serverCoords[1] = game.getServerY();
        scores[0] = game.getClientScore();
        scores[1] = game.getServerScore();
        System.out.println("Server has played Stone at: " + serverCoords[0] 
                + ", " + serverCoords[1]);
        System.out.println("Client score: " + scores[0]);
        System.out.println("Server score: " + scores[1]);
        if (isGameOver) {
            System.out.println("---GAME OVER---");
        } else {
            System.out.println(game.getClientStones() 
                    + " turns until game ends.");
        }
    }

    /**
     * Returns the coordinates of the server's most recent Stone placement. 
     * Index 0 contains the x-coordinate, and index 1 contains the y-coordinate.
     * 
     * @return an array containing the coordinates of the server's most recent 
     * Stone placement.
     */
    @Override
    public int[] getAIMove() {
        return serverCoords;
    }

    /**
     * Returns the current score totals for each player. Index 0 contains the 
     * client's score, and index 1 contains the server's score.
     * 
     * @return an array containing the current score totals for each player.
     */
    @Override
    public int[] getScores() {
        return scores;
    }

    /**
     * Returns the current number of Stones the client has remaining to play.
     * 
     * @return the number of Stones the client has remaining to play.
     */
    @Override
    public int getStones() {
        return game.getClientStones();
    }
    
    /**
     * Returns the value of the flag indicating if a game is still in progress.
     * 
     * @return true if the current game has ended, false otherwise.
     */
    @Override
    public boolean getGameOverFlag() {
        return isGameOver;
    }
}
