package com.mycompany.serverside.persistence;

/**
 *
 * @author 1511430
 */
public interface GameStateDAO {
    
    public void setTesting(int x, int y);
    
    public int getX();
    
    public int getY();
}
