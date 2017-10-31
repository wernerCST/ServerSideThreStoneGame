package com.mycompany.serverside.persistence;

/**
 * The ThreeStonesServerSession interface defines the rules that must be 
 * followed by an implementation of a Three Stones server session class.
 * 
 * @author Werner Castanaza
 * @author Peter Bellefleur MacCaul
 */
public interface ThreeStonesServerSession {
    
    //A new GameState object is created.
    public void newGame();
    //Resets the GameState object to it's default values
    public void resstartGame();
    //Sets the clients stone onto the board
    public void setClientMove(int x, int y);
    //Returns the AI move
    public int[] getAIMove();
    //Returns the current scores
    public int[] getScores();
    //Returns stones left for the player and the AI.
    public int getStones();
    //Returns true if game is over, false if not
    public boolean getGameOverFlag();
    
    
    
    
}
