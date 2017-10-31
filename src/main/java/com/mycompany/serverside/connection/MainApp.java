package com.mycompany.serverside.connection;

import java.io.IOException;
import static javafx.application.Application.launch;
import java.net.*;

/**
 * Main application class for the ThreeStonesServer.
 * 
 * @author Werner Castanaza
 */
public class MainApp {
    
    /**
     * The main method for the ThreeStonesServer creates the server object, 
     * then starts the server.
     * 
     * @param args Any arguments included with the application on launch.
     */
    public static void main(String[] args) {
        ThreeStonesServer connection = new ThreeStonesServer();
        connection.startServer(); 
        
       
    }
    
}
