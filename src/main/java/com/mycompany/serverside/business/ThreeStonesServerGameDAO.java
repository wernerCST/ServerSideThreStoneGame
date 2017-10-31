package com.mycompany.serverside.business;

import com.mycompany.serverside.data.Stone;

/**
 *  The ThreeStonesServerGameDAO defines the rules that any implementation of a 
 * Three Stones server-side game logic class must follow.
 * 
 * @author Hannah Ly
 * @author Werner Castanaza
 * @author Peter Bellefleur MacCaul
 */
public interface ThreeStonesServerGameDAO {
    public void buildBoard();
    public boolean playRoundOfGame(int x, int y);
    public int getServerX();
    public int getServerY();
    public int getClientScore();
    public int getServerScore();
    public int getClientStones();
    
    
}
