package com.mycompany.serverside.data;

/**
 *  A Stone defines a playing piece in a game of Three Stones. Stones can be 
 * black or white, depending on the player order. For purposes of simplifying 
 * how the game board is represented programmatically, empty spaces on a board 
 * are also considered Stones - EMPTY represents an empty board space, where a 
 * player can place their Stone, CENTER represents the center of the board, 
 * which cannot have a Stone placed in it but has its own rules for score 
 * calculation, and CORNER represents the corner edges of the board, where no 
 * Stone can be placed.
 * 
 * @author Peter Bellefleur MacCaul
 */
public enum Stone {
    BLACK, WHITE, EMPTY, CENTER, CORNER
}
