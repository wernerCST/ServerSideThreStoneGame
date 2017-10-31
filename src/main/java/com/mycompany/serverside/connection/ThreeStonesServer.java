package com.mycompany.serverside.connection;

import com.mycompany.serverside.business.ThreeStonesServerGame;
import com.mycompany.serverside.business.ThreeStonesServerGameDAO;
import com.mycompany.serverside.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A ThreeStonesServer class defines the logic for creating and running a 
 * server for a game of Three Stones. Server can handle one client connection 
 * at a time. The server creates a ServerSocket on port 50000. The server sends 
 * and receives custom six-byte arrays to and from a connected client in order 
 * to communicate. A ThreeStonesServer will run forever, until the application 
 * it runs in is forcibly closed.
 * 
 * @author Werner Castanaza
 * @author Hannah Ly
 * @author Peter Bellefleur MacCaul
 */
public class ThreeStonesServer { 
    private final  int port;
    private static final int BUFSIZE = 6;  
    private ThreeStonesServerSession session;
    
    /**
     * Default constructor. 
     */
    public ThreeStonesServer() {
        this.port = 50000;  
    }
    
    /**
     * Starts the ThreeStonesServer, allowing it to handle connections. Server 
     * will handle one connection at a time, and listen for incoming 
     * connections when not currently handling one.
     */
    public void startServer() {        
        int msgSize;
        byte[] bb = new byte[BUFSIZE];
        for (;;) {
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
            
            //create server socket, fetch client socket from server socket
            try (ServerSocket sk = new ServerSocket(this.port);
                    Socket client = sk.accept();) {
                System.out.println("Received data from client.");
                InputStream in = client.getInputStream();
                OutputStream out = client.getOutputStream();
                //buffer for incoming packets
                while((msgSize = in.read(bb)) != -1) {
                    byte[] serverRespones = parseIncomingPacket(bb);
                    out.write(serverRespones, 0, serverRespones.length);    
                    System.out.println("Sending data to client.");
                }
                
            } catch (IOException ex) {
                System.out.println("something went bad:  " + ex.getMessage());
            }
        }           
    }

    /**
     * Parses a byte array received from a Three Stones client application, and 
     * creates a response to send back. Server makes changes to the current 
     * session based on packets sent by the client; packets starting with 0 
     * create the session, packets starting with 1 start a game within the 
     * session, packets starting with 2 commit a client player's move to the 
     * game board, and packets starting with 3 restart the game.
     * 
     * @param input A byte array, containing information from the client.
     * @return  A byte array, containing information from the server.
     */
    private byte[] parseIncomingPacket(byte[] input) {
         byte[] response = new byte[BUFSIZE];
         switch(input[0]){
             //Session initialized.
             case 0: 
                 System.out.println("Array header 0: initialize session");
                 this.session  = new ThreeStonesServerSessionImpl();
                 response[0] = 0;
                 break;
             //Request for new game.
             case 1:
                 System.out.println("Array header 1: start new game");
                 session.newGame();                 
                 response[0] = 1;
                 break;
             //Game in progress and a move was made    
             case 2:
                 System.out.println("Array header 2: received move from client");
                 session.setClientMove(input[1], input[2]);
                 int[] ai = session.getAIMove();
                 if (session.getGameOverFlag()) {
                     //game over
                    response[0] = 4;
                 } else {
                     response[0] = 2;
                 }
                 response[1] = (byte)ai[0];
                 response[2] = (byte)ai[1];
                 ai = session.getScores();
                 response[3] = (byte)ai[0];
                 response[4] = (byte)ai[1];
                 response[5] = (byte)session.getStones();
                 break;
             //Restart Game    
             case 3: 
                 System.out.println("Array header 3: restart game");
                 session.resstartGame();
                 response[0] = 3;
                 break;
             //End game
            case 4:      
                System.out.println("Array header 4: closing game"); 
                session = null;
                 break;
             
         }
         return response;
    }
}