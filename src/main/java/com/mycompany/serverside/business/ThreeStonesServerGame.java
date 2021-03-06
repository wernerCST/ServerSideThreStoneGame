package com.mycompany.serverside.business;

import com.mycompany.serverside.data.Stone;
import com.mycompany.serverside.data.ThreeStonesBoard;
import com.mycompany.serverside.data.ThreeStonesPlayer;

/**
 *The ThreeStonesServerGame class defines the server-side logic required to 
 * play a game of Three Stones. The game is played between a human client and 
 * the server AI. The game class receives the coordinates for a move made by 
 * a player, then determines the server's move based on the rules of Three 
 * Stones (i.e. a stone must be placed on the same horizontal or vertical axis 
 * as the previous player's stone, if possible). This class also tracks the 
 * current state of the board, as well as each player's current score and 
 * number of stones remaining (as well as all calculations necessary for these 
 * values).
 * 
 * @author Peter Bellefleur MacCaul
 */
public class ThreeStonesServerGame implements ThreeStonesServerGameDAO {
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
    
    /**
     * Default constructor. Instantiates the board and both players, and gives 
     * default values to all internal variables.
     */
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
     * Fills the ThreeStonesBoard with a set of tiles.
     */
    @Override
    public void buildBoard() {
        for (int x = 0; x < 11; x++) {
            for (int y = 0; y < 11; y++) {
                if (x == 0 || x == 1 || x == 9 || x == 10) {
                    //vertical bars at edges of board
                    board.setStoneAt(Stone.CORNER, x, y);
                    System.out.println("Stone of type CORNER added at coords " 
                            + x + ", " + y);
                } else if (y == 0 || y == 1 || y == 9 || y == 10) {
                    //horizontal bars at edges of board
                    board.setStoneAt(Stone.CORNER, x, y);
                    System.out.println("Stone of type CORNER added at coords " 
                            + x + ", " + y);
                } else if ((x == 2 && y == 2) || (x == 2 && y == 3) 
                        || (x == 3 && y == 2)) {
                    //top-left corner
                    board.setStoneAt(Stone.CORNER, x, y);
                    System.out.println("Stone of type CORNER added at coords " 
                            + x + ", " + y);
                } else if ((x == 7 && y == 2) || (x == 8 && y == 2) 
                        || (x == 8 && y == 3)) {
                    //top-right corner
                    board.setStoneAt(Stone.CORNER, x, y);
                    System.out.println("Stone of type CORNER added at coords " 
                            + x + ", " + y);
                } else if ((x == 2 && y == 7) || (x == 2 && y == 8) 
                        || (x == 3 && y == 8)) {
                    //bottom left corner
                    board.setStoneAt(Stone.CORNER, x, y);
                    System.out.println("Stone of type CORNER added at coords " 
                            + x + ", " + y);
                } else if ((x == 8 && y == 7) || (x == 7 && y == 8) 
                        || (x == 8 && y == 8)) {
                    //bottom right corner
                    board.setStoneAt(Stone.CORNER, x, y);
                    System.out.println("Stone of type CORNER added at coords " 
                            + x + ", " + y);
                } else if (x == 5 && y == 5) {
                    //center of board
                    board.setStoneAt(Stone.CENTER, x, y);
                    System.out.println("Stone of type CENTER added at coords " 
                            + x + ", " + y);
                } else {
                    //all other spaces empty
                    board.setStoneAt(Stone.EMPTY, x, y);
                    System.out.println("Stone of type EMPTY added at coords " 
                            + x + ", " + y);
                }
            }
        }
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
    @Override
    public boolean playRoundOfGame(int x, int y) {
        //given input from client, place their stone
        clientX = x;
        clientY = y;
        board.setStoneAt(playerClient.getStoneColour(), clientX, clientY);
        System.out.println("Client stone placed.");
        
        //player loses a Stone after a play; the Stone is now on the board
        playerClient.decrementNumStones();
        //calculate points client has earned, add to their total score
        nextScore = determineScore(playerClient.getStoneColour(), 
                clientX, clientY);
        playerClient.incrementScore(nextScore);
        
        //determine where server should next play, then play
        System.out.println("Calculating next server move...");
        findNextServerMove();
        board.setStoneAt(playerServer.getStoneColour(), serverX, serverY);
        System.out.println("Server stone placed.");
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
     * Returns the x-coordinate of the server's most recent move.
     * @return the x-coordinate of the server's most recent move.
     */
    @Override
    public int getServerX() {
        return serverX;
    }
    
    /**
     * Returns the y-coordinate of the server's most recent move.
     * @return the y-coordinate of the server's most recent move.
     */
    @Override
    public int getServerY() {
        return serverY;
    }
    
    /**
     * Returns the client player's current score.
     * @return the client player's current score.
     */
    @Override
    public int getClientScore() {
        return playerClient.getScore();
    }
    
    /**
     * Returns the server player's current score.
     * @return the server player's current score.
     */
    @Override
    public int getServerScore() {
        return playerServer.getScore();
    }
    
    /**
     * Returns the number of Stones the client player has remaining.
     * @return the number of Stones the client player has remaining.
     */
    @Override
    public int getClientStones() {
        return playerClient.getNumStones();
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
     * Determines the next move the server should make. Checks all spaces 
     * matching the row and column of the most recent Stone played to see if 
     * any spaces are free. If spaces are free, the method checks to see if any 
     * free space will earn the server points; the Stone is placed in the 
     * position that will earn the most points, or the first free space if no 
     * spaces will earn points. If NO free spaces are available on the row and 
     * column of the most recent Stone, the server will play its Stone on the 
     * first empty space it finds in the board.
     */
    private void findNextServerMove() {
        //flag checks if a Stone can be placed anywhere
        boolean canPlaceAnywhere = true;
        //tracks highest scoring move & coordinates for it
        int highestScore = -1;
        int scoreToCompare = -1;
        int bestScoringX = -1;
        int bestScoringY = -1;
        System.out.println("Checking for empty spots horizontal to player "
                + "stone...");
        for (int i = clientX - 1; i >= 0; i--) {
            if (board.getStoneAt(i, clientY) == Stone.EMPTY) {
                System.out.println("Space found!");
                canPlaceAnywhere = false;
                scoreToCompare = determineScore(playerServer.getStoneColour(), 
                        i, clientY);
                if (scoreToCompare > highestScore) {
                    System.out.println("Space can earn server " 
                            + scoreToCompare + " points.");
                    highestScore = scoreToCompare;
                    bestScoringX = i;
                    bestScoringY = clientY;
                }
            }
        }
        for (int j = clientX + 1; j <= 10; j++) {
            if (board.getStoneAt(j, clientY) == Stone.EMPTY) {
                System.out.println("Space found!");
                canPlaceAnywhere = false;
                scoreToCompare = determineScore(playerServer.getStoneColour(),
                        j, clientY);
                if (scoreToCompare > highestScore) {
                    System.out.println("Space can earn server " 
                            + scoreToCompare + " points.");
                    highestScore = scoreToCompare;
                    bestScoringX = j;
                    bestScoringY = clientY;
                }
            }
        }
        System.out.println("Checking for empty spots vertical to player "
                + "stone...");
        for (int k = clientY - 1; k >= 0; k--) {
            if (board.getStoneAt(clientX, k) == Stone.EMPTY) {
                System.out.println("Space found!");
                canPlaceAnywhere = false;
                scoreToCompare = determineScore(playerServer.getStoneColour(),
                        clientX, k);
                if (scoreToCompare > highestScore) {
                    System.out.println("Space can earn server " 
                            + scoreToCompare + " points.");
                    highestScore = scoreToCompare;
                    bestScoringX = clientX;
                    bestScoringY = k;
                }
            }
        }
        for (int l = clientY + 1; l <= 10; l++) {
            if (board.getStoneAt(clientX, l) == Stone.EMPTY) {
                System.out.println("Space found!");
                canPlaceAnywhere = false;
                scoreToCompare = determineScore(playerServer.getStoneColour(),
                        clientX, l);
                if (scoreToCompare > highestScore) {
                    System.out.println("Space can earn server " 
                            + scoreToCompare + " points.");
                    highestScore = scoreToCompare;
                    bestScoringX = clientX;
                    bestScoringY = l;
                }
            }
        }
        //if no empty spots have been found in last Stone's row or col,
        //stone can be placed anywhere free on the board
        if (canPlaceAnywhere) {
            System.out.println("No free horizontal or vertical spaces found. "
                    + "Searching for first empty space on board...");
            //find first empty spot on board
            boolean wasEmptyFound = false;
            for (int x = 0; x < 11 && wasEmptyFound == false; x ++) {
                for (int y = 0; y < 11 && wasEmptyFound == false; y++) {
                    if (board.getStoneAt(x, y) == Stone.EMPTY) {
                        System.out.println("Empty space found!");
                        wasEmptyFound = true;
                        bestScoringX = x;
                        bestScoringY = y;
                    }
                }
            }
        }
        serverX = bestScoringX;
        serverY = bestScoringY;
    }
}
