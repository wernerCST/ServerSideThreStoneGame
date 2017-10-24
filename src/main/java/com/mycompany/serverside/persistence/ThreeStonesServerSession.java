package com.mycompany.serverside.persistence;

/**
 *
 * @author 1511430
 */
public interface ThreeStonesServerSession {
    
    //A new GameState object is created.
    public void newGame();
    //Resets the GameState object to it's default values (scores, stones on hand ETC)
    public void resstartGame();
    //Sets the clients stone onto the board
    public void setClientMove(int clientMove);
    //Returns the AI move, 
    //NOT SURE ABOUT, maybe it'll be better somewhere else -->  if the game is over it returns a special charecter.
    public String getAIMove();
    //Returns the current scores
    public String getScores();
    //Returns stones left for the player and the AI.
    public String getStones();
    
    
    
    
}
