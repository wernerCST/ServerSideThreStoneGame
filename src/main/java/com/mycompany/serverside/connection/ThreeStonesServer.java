package com.mycompany.serverside.connection;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A ThreeStonesServer class defines the logic for creating and running a 
 * server for a game of Three Stones. The server creates a ServerSocket on port 
 * 50000. The server sends and receives custom six-byte arrays to and from any 
 * connected clients in order to communicate. Each client has its own session, 
 * handled in its own thread. A ThreeStonesServer will run forever, until the 
 * application it runs in is forcibly closed.
 * 
 * @author Werner Castanaza
 * @author Hannah Ly
 * @author Peter Bellefleur MacCaul
 */
public class ThreeStonesServer { 
    private final  int port;
    
    /**
     * Default constructor. 
     */
    public ThreeStonesServer() {
        this.port = 50000;  
    }
    
    /**
     * Starts the ThreeStonesServer, allowing it to handle connections. Server 
     * will listen for new connections, and create a new thread to handle each 
     * session.
     * 
     * @throws java.io.IOException
     */
    public void startServer() throws IOException {        
        try {
            //get connection information for current machine
            InetAddress address = InetAddress.getLocalHost();
            System.out.println("Three Stones server online.");
            //display server IP and port
            System.out.println("Server's IP address: " 
                    + address.getHostAddress());
            System.out.println("Running on port " + port);
                
        } catch (UnknownHostException e) {
            System.out.println("Unable to determine this server's address");
        }
        
        //create server socket
        ServerSocket serverSocket = new ServerSocket(this.port);
        
        //run forever, until program forcibly closed
        for (;;) {
            //fetch client socket from server socket
            try {
                Socket clientSocket = serverSocket.accept();
                //create new thread for client session
                ThreeStonesServerThread serverThread = 
                        new ThreeStonesServerThread(clientSocket);
                Thread thread = new Thread(serverThread);
                thread.start();
                System.out.println("Created and started thread: " 
                        + thread.getName());
            } catch (IOException ex) {
                System.out.println("Exception:  " + ex.getMessage());
            }
        }           
    }
}