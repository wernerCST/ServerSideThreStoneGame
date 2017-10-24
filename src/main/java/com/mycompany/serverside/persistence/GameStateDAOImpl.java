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
    
    private int x;
    private int y;

    @Override
    public void setTesting(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
       return this.x;
    }
    @Override
    public int getY() {
       return this.y;
    }
    
}
