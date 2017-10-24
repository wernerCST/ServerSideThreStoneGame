package com.mycompany.serverside.persistence;

import com.mycompany.serverside.business.ThreeStonesServerGame;

/**
 *
 * @author Werner
 * @author Peter
 */
public class ThreeStonesServerSessionImpl implements ThreeStonesServerSession {
    private ThreeStonesServerGame game;
    private boolean isGameOver;
    int[] serverCoords;
    int[] scores;
    
    @Override
    public void newGame() {
        System.out.println("Starting new game...");
        game = new ThreeStonesServerGame();
        isGameOver = false;
        serverCoords = new int[2];
        scores = new int[2];
    }

    @Override
    public void resstartGame() {
        System.out.println("Restarting game...");
        game = new ThreeStonesServerGame();
        isGameOver = false;
        serverCoords = new int[2];
        scores = new int[2];
    }

    @Override
    public void setClientMove(int x, int y) {
        isGameOver = game.playRoundOfGame(x, y);
        serverCoords[0] = game.getServerX();
        serverCoords[1] = game.getServerY();
        scores[0] = game.getClientScore();
        scores[1] = game.getServerScore();
        System.out.println("Client has played Stone at: " + x + ", " + y);
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

    @Override
    public int[] getAIMove() {
        return serverCoords;
    }

    @Override
    public int[] getScores() {
        return scores;
    }

    @Override
    public int getStones() {
        return game.getClientStones();
    }
    
    public boolean getGameOverFlag() {
        return isGameOver;
    }
}
