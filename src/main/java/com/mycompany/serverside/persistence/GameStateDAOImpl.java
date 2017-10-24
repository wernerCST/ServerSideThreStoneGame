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
