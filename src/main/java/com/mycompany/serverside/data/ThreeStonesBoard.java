package com.mycompany.serverside.data;

/**
 *  A ThreeStonesBoard defines the playing area for a game of Three Stones. The 
 * ThreeStonesBoard is an 11-by-11 grid of Stones, and places for players to 
 * place Stones. 
 * 
 * @author Peter Bellefleur MacCaul
 */
public class ThreeStonesBoard {
    //represent board with 2-dimensional array
    private Stone[][] board;
    
    /**
     * Default constructor for a ThreeStonesBoard object.
     */
    public ThreeStonesBoard() {
        this.board = new Stone[11][11];
    }
    
    /**
     * Places a Stone on the board at the given coordinates.
     * 
     * @param stone The Stone to place on the board.
     * @param x The x-coordinate of the placement location.
     * @param y The y-coordinate of the placement location.
     */
    public void setStoneAt(Stone stone, int x, int y) {
        board[x][y] = stone;
    }
    
    /**
     * Retrieves the value of a Stone on the board at the given coordinates.
     * 
     * @param x The x-coordinate of the Stone to find.
     * @param y The y-coordinate of the Stone to find.
     * @return  The Stone at the given coordinates.
     */
    public Stone getStoneAt(int x, int y) {
        return board[x][y];
    }
}
