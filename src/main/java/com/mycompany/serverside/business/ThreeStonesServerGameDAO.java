/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverside.business;

import com.mycompany.serverside.data.Stone;

/**
 *
 * @author 1511430
 */
public interface ThreeStonesServerGameDAO {
    public boolean playRoundOfGame(int x, int y);
    public int getServerX();
    public int getServerY();
    public int getClientScore();
    public int getServerScore();
    public int getClientStones();
    
    
}
