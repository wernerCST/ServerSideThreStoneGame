/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverside.persistence;

/**
 *
 * @author 1511430
 */
public interface GameStateDAO {
    
    public void setTesting(int playerMove);
    
    public int getTesting();
}
