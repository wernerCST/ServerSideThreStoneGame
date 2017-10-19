/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverside.connection;

import java.io.IOException;
import static javafx.application.Application.launch;
import java.net.*;

/**
 *
 * @author 1511430
 */
public class MainApp {
    
    public static void main(String[] args) {
        Connection con = new Connection();
        con.startServer(); 
        
       
    }
    
}
