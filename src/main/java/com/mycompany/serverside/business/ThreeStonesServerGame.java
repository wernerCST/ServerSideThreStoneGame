package com.mycompany.serverside.business;

import com.mycompany.serverside.data.Stone;
import com.mycompany.serverside.data.ThreeStonesBoard;
import com.mycompany.serverside.data.ThreeStonesPlayer;

/**
 *
 * @author Peter Bellefleur MacCaul
 */
public class ThreeStonesServerGame {
    //bean classes
    private ThreeStonesBoard board;
    private ThreeStonesPlayer playerClient;
    private ThreeStonesPlayer playerServer;
    //flag representing if a game has ended
    private boolean isGameOver;
    //flags representing if players have earned a point
    private int nextScore;
    //coordinates for the client's last move
    private int clientX;
    private int clientY;
    //coordinates for the server's last move
    private int serverX;
    private int serverY;
    
    public ThreeStonesServerGame() {
        board = new ThreeStonesBoard();
        playerClient = new ThreeStonesPlayer(Stone.BLACK);
        playerServer = new ThreeStonesPlayer(Stone.WHITE);
        isGameOver = false;
        nextScore = 0;
        clientX = -1;
        clientY = -1;
        serverX = -1;
        serverY = -1;
    }
    
    /**
     * Plays a round of Three Stones. Accepts the coordinates of a client 
     * player's Stone placement, and places it on the board. The server then 
     * determines where it can next place a Stone, and plays there. The server 
     * determines whether each player has earned a point for their move. If 
     * both players have no stones remaining by the end of a round, the game 
     * is considered over.
     * 
     * @param x The x-coordinate of the client's move on the board.
     * @param y The y-coordinate of the client's move on the board.
     * 
     * @return true if this round was the last round of the game, 
     *         false otherwise.
     */
    public boolean playRoundOfGame(int x, int y) {
        //given input from client, place their stone
        clientX = x;
        clientY = y;
        board.setStoneAt(playerClient.getStoneColour(), clientX, clientY);
        
        //player loses a Stone after a play; the Stone is now on the board
        playerClient.decrementNumStones();
        
        //calculate points client has earned, add to their total score
        nextScore = determineScore(playerClient.getStoneColour(), 
                clientX, clientY);
        playerClient.incrementScore(nextScore);
        
        //determine where server should next play, then play
        findNextServerMove();
        board.setStoneAt(playerServer.getStoneColour(), serverX, serverY);
        
        //server loses a Stone after a play; the Stone is now on the board
        playerServer.decrementNumStones();
        
        //calculate points server has earned, add to their total score
        nextScore = determineScore(playerServer.getStoneColour(),
                serverX, serverY);
        playerServer.incrementScore(nextScore);
        
        //if both players are out of stones, the game is over
        if (playerClient.getNumStones() <= 0 
                && playerServer.getNumStones() <= 0)
            isGameOver = true;
        
        //return game status flag
        return isGameOver;
    }
    
    /**
     * Calculates the number of points a player's Stone placement has earned. 
     * Looks at the colour of Stones on the checked Stone's vertical and 
     * horizontal axes, as well as Stones diagonal to it.
     * 
     * @param colourToCheck The stone colour to check for on the board.
     * @param x The x-coordinate of the stone to check.
     * @param y The y-coordinate of the stone to check.
     * @return the number of points a move has earned - 0 or more.
     */
    private int determineScore(Stone colourToCheck, int x, int y) {
        int scoreToAdd = 0;
        scoreToAdd += checkHorizontal(colourToCheck, x, y);
        scoreToAdd += checkVertical(colourToCheck, x, y);
        scoreToAdd += checkDiagonalLeftRight(colourToCheck, x, y);
        scoreToAdd += checkDiagonalRightLeft(colourToCheck, x, y);
        return scoreToAdd;
    }
    
    /**
     * Helper method for determineIfScored that checks the colour of Stones on 
     * the same horizontal axis as a given Stone, and calculates the points 
     * this has earned.
     * 
     * @param colourToCheck The Stone colour to check for on the board.
     * @param x The x-coordinate of the stone to check.
     * @param y The y-coordinate of the stone to check.
     * @return  the number of points a move has earned - 0 or more.
     */
    private int checkHorizontal(Stone colourToCheck, int x, int y) {
        int score = 0;
        if (board.getStoneAt(x-1, y) == colourToCheck 
                && board.getStoneAt(x-2, y) == colourToCheck)
            score++;
        if (board.getStoneAt(x-1, y) == colourToCheck 
                && board.getStoneAt(x+1, y) == colourToCheck)
            score++;
        if (board.getStoneAt(x+1, y) == colourToCheck 
                && board.getStoneAt(x+2, y) == colourToCheck)
            score++;
        return score;
    }
    
    /**
     * Helper method for determineIfScored that checks the colour of Stones on 
     * the same vertical axis as a given Stone, and calculates the points 
     * this has earned.
     * 
     * @param colourToCheck The Stone colour to check for on the board.
     * @param x The x-coordinate of the stone to check.
     * @param y The y-coordinate of the stone to check.
     * @return  the number of points a move has earned - 0 or more.
     */
    private int checkVertical(Stone colourToCheck, int x, int y) {
        int score = 0;
        if (board.getStoneAt(x, y-1) == colourToCheck 
                && board.getStoneAt(x, y-2) == colourToCheck)
            score++;
        if (board.getStoneAt(x, y-1) == colourToCheck 
                && board.getStoneAt(x, y+1) == colourToCheck)
            score++;
        if (board.getStoneAt(x, y+1) == colourToCheck 
                && board.getStoneAt(x, y+2) == colourToCheck)
            score++;
        return score;
    }
    
    /**
     * Helper method for determineIfScored that checks the colour of Stones on 
     * a diagonal of a given Stone, and calculates the points this has earned. 
     * This method checks the Stones on the 
     * lower-right and upper-left sides of the given Stone.
     * 
     * @param colourToCheck The Stone colour to check for on the board.
     * @param x The x-coordinate of the stone to check.
     * @param y The y-coordinate of the stone to check.
     * @return  the number of points a move has earned - 0 or more.
     */
    private int checkDiagonalLeftRight(Stone colourToCheck, int x, int y) {
        int score = 0;
        if (board.getStoneAt(x-1, y-1) == colourToCheck 
                && board.getStoneAt(x-2, y-2) == colourToCheck)
            score++;
        else if (board.getStoneAt(x-1, y-1) == colourToCheck 
                && board.getStoneAt(x+1, y+1) == colourToCheck)
            score++;
        else if (board.getStoneAt(x+1, y+1) == colourToCheck 
                && board.getStoneAt(x+2, y+2) == colourToCheck)
            score++;
        return score;
    }
    
    /**
     * Helper method for determineIfScored that checks the colour of Stones on 
     * a diagonal of a given Stone, and calculates the points this has earned. 
     * This method checks the Stones on the 
     * upper-right and lower-left sides of the given Stone.
     * 
     * @param colourToCheck The Stone colour to check for on the board.
     * @param x The x-coordinate of the stone to check.
     * @param y The y-coordinate of the stone to check.
     * @return  the number of points a move has earned - 0 or more.
     */
    private int checkDiagonalRightLeft(Stone colourToCheck, int x, int y) {
        int score = 0;
        if (board.getStoneAt(x-1, y+1) == colourToCheck 
                && board.getStoneAt(x-2, y+2) == colourToCheck)
            score++;
        else if (board.getStoneAt(x-1, y+1) == colourToCheck 
                && board.getStoneAt(x+1, y-1) == colourToCheck)
            score++;
        else if (board.getStoneAt(x+1, y-1) == colourToCheck 
                && board.getStoneAt(x+2, y-2) == colourToCheck)
            score++;
        return score;
    }
    
    /**
     * 
     */
    private void findNextServerMove() {
        //flag checks if a Stone can be placed anywhere
        boolean canPlaceAnywhere = true;
        //tracks highest scoring move & coordinates for it
        int highestScore = -1;
        int scoreToCompare = -1;
        int bestScoringX = -1;
        int bestScoringY = -1;
        
        for (int i = clientX - 1; i >= 0; i--) {
            if (board.getStoneAt(i, clientY) == Stone.EMPTY) {
                canPlaceAnywhere = false;
                scoreToCompare = determineScore(playerServer.getStoneColour(), 
                        i, clientY);
                if (scoreToCompare > highestScore) {
                    highestScore = scoreToCompare;
                    bestScoringX = i;
                    bestScoringY = clientY;
                }
            }
        }
        
        for (int j = clientX + 1; j <= 10; j++) {
            if (board.getStoneAt(j, clientY) == Stone.EMPTY) {
                canPlaceAnywhere = false;
                scoreToCompare = determineScore(playerServer.getStoneColour(),
                        j, clientY);
                if (scoreToCompare > highestScore) {
                    highestScore = scoreToCompare;
                    bestScoringX = j;
                    bestScoringY = clientY;
                }
            }
        }
        
        for (int k = clientY - 1; k >= 0; k--) {
            if (board.getStoneAt(clientX, k) == Stone.EMPTY) {
                canPlaceAnywhere = false;
                scoreToCompare = determineScore(playerServer.getStoneColour(),
                        clientX, k);
                if (scoreToCompare > highestScore) {
                    highestScore = scoreToCompare;
                    bestScoringX = clientX;
                    bestScoringY = k;
                }
            }
        }
        
        for (int l = clientY + 1; l <= 10; l++) {
            if (board.getStoneAt(clientX, l) == Stone.EMPTY) {
                canPlaceAnywhere = false;
                scoreToCompare = determineScore(playerServer.getStoneColour(),
                        clientX, l);
                if (scoreToCompare > highestScore) {
                    highestScore = scoreToCompare;
                    bestScoringX = clientX;
                    bestScoringY = l;
                }
            }
        }
        
        if (canPlaceAnywhere) {
            //find random spot? or first empty spot?
            boolean wasEmptyFound = false;
            while (!wasEmptyFound) {
                bestScoringX = (int)(Math.random() * 11);
                bestScoringY = (int)(Math.random() * 11);
                if (board.getStoneAt(bestScoringX, bestScoringY) == Stone.EMPTY)
                    wasEmptyFound = true;
            }
        }
        
        serverX = bestScoringX;
        serverY = bestScoringY;
    }
    
    /**
     * some pseudocode - might split this between this class & ThreeSTonesServerSession
	 * remove this before final submission!!
     * 
     * playGame()
     *      while (!gameOver)
     *          wait for input
     *          update board with input
     *          calculatePoints()?
     *          decideNextMove()
     *          calculatePoints()
     *          if (playerOne.getStones == 0 & playerTwo.getStones == 0)
     *              gameOver = true;
     *      compare points
     *      "p1 wins!" or something to that effect based on point total
     *      return this somehow
     * 
     * decideNextMove()
     *      if allowed to place anywhere - gen 2 random #'s, use as coords
     *          if Stone at coords == EMPTY, new Stone there! else loop back, gen 2 more #'s
     *      if NOT allowed to place any spot - examine available spots
     *          for each spot: which gives more points? go to one that gives most points
     *          if any spots give same points - pick one at random, or pick first
     */
    
    /**
     * THINGS 2 SEND BACK
     * SERVER MOVE COORDS
     * SCORES
     * PLAYER STONES LEFT
     */
}
