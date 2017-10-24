package com.mycompany.serverside.data;

/**
 *  A ThreeStonesPlayer defines a player in a game of Three Stones. A player 
 * can be human, or computer-controlled. A game of Three Stones has two 
 * players. A ThreeStonePlayer has a number of points they have scored, a 
 * specific colour of Stone they play with, and a specific number of Stones to 
 * use.
 * 
 * @author Peter Bellefleur MacCaul
 */
public class ThreeStonesPlayer {
   private int score;
    private Stone stoneColour;
    private int numStones;
    
    /**
     * Two-parameter constructor for a ThreeStonesPlayer object. Allows a 
     * custom number of Stones to be defined.
     * 
     * @param stoneColour   The colour of the player's Stones.
     * @param numStones     The number of Stones the player has.
     */
    public ThreeStonesPlayer(Stone stoneColour, int numStones) {
        this.score = 0;
        this.stoneColour = stoneColour;
        this.numStones = numStones;
    }
    
    /**
     * One-parameter constructor for a ThreeStonesPlayer object. Calls the two 
     * parameter constructor, with 15 set as the default number of Stones.
     * @param stoneColour 
     */
    public ThreeStonesPlayer(Stone stoneColour) {
        this(stoneColour, 15);
    }
    
    /**
     * Returns the player's current score.
     * 
     * @return The number of points a player has earned.
     */
    public int getScore() {
        return this.score;
    }
    
    /**
     * Adds one point to the player's current score.
     */
    public void incrementScore(int scoreToAdd) {
        this.score += scoreToAdd;
    }
    
    /**
     * Returns the player's current number of remaining Stones.
     * 
     * @return The number of Stones remaining.
     */
    public int getNumStones() {
        return this.numStones;
    }
    
    /**
     * Removes one Stone from the player's current total Stones.
     */
    public void decrementNumStones() {
        this.numStones--;
    }
    
    /**
     * Returns the player's current Stone colour.
     * 
     * @return The colour of Stone the player has to play.
     */
    public Stone getStoneColour() {
        return this.stoneColour;
    } 
}
