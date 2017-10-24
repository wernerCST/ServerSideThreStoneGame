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
        ThreeStonesServer connection = new ThreeStonesServer();
        connection.startServer(); 
        
       
    }
    
}
