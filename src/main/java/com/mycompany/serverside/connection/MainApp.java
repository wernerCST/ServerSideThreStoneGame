package com.mycompany.serverside.connection;

import java.io.IOException;

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
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        ThreeStonesServer connection = new ThreeStonesServer();
        connection.startServer();    
    }
    
}
