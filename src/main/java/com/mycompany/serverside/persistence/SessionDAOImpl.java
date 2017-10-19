/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverside.persistence;

/**
 *
 * @author 1511430
 */
public class SessionDAOImpl implements SessionDAO {
    private GameStateDAO gameState;
    
    @Override
    public void newGame() {
        System.out.println("New Game Made");
        gameState = new GameStateDAOImpl();
    }

    @Override
    public void resstartGame() {
        this.gameState.setTesting(-1);
    }

    @Override
    public void setClientMove(int clientMove) {
        System.out.println("A move by the client has been made --> " + clientMove);
        this.gameState.setTesting(clientMove);
    }

    @Override
    public String getAIMove() {
        return this.gameState.getTesting() + "";
    }

    @Override
    public String getScores() {
        return "stones";
    }

    @Override
    public String getStones() {
        return "42";
    }
    
}
