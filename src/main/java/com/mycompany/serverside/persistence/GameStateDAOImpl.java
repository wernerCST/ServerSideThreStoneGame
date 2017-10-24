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
