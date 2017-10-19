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
public class GameStateDAOImpl implements GameStateDAO {
    
    private int num;

    @Override
    public void setTesting(int playerMove) {
        this.num = playerMove;
    }

    @Override
    public int getTesting() {
       return this.num;
    }
    
}
