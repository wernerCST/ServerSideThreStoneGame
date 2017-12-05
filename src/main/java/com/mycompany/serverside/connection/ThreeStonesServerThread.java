package com.mycompany.serverside.connection;

import com.mycompany.serverside.persistence.ThreeStonesServerSession;
import com.mycompany.serverside.persistence.ThreeStonesServerSessionImpl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * The ThreeStonesServerThread class defines a thread that will be created by a 
 * ThreeStonesServer object to handle a client session. 
 * ThreeStonesServerThreads allow multiple clients to connect to the same 
 * server at the same time with their own independent sessions.
 * 
 * @author Peter Bellefleur MacCaul
 * @author Werner Castanaza
 * @author Hannah Ly
 */
public class ThreeStonesServerThread implements Runnable {
    private static final int BUFSIZE = 6;
    private Socket clientSocket;
    private ThreeStonesServerSession session;
    
    /**
     * Constructor for a ThreeStonesServerThread class. Sets given input to the 
     * client socket object.
     * 
     * @param clientSocket The Socket object, representing the client
     */
    public ThreeStonesServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    
    /**
     * Runs logic to handle client packets when the thread is started. Creates 
     * I/O streams from the client socket, reads the incoming client packet, 
     * and generates a response based on its contents.
     */
    @Override
    public void run() {
        try {
            System.out.println("Received data from client.");            
            //get the input and output I/O streams from socket
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();
            
            int msgSize;
            byte[] bb = new byte[BUFSIZE];
            
            //buffer for incoming packets
            while((msgSize = in.read(bb)) != -1) {
                //generate response based on client packet contents
                byte[] serverResponse = parseIncomingPacket(bb);
                //send response to client
                out.write(serverResponse, 0, serverResponse.length);    
                System.out.println("Sending data to client.");
                //if client is closing game, close their socket
                if (bb[0] == 4) {
                    clientSocket.close();
                    System.out.println("Disconnecting from client.");
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    
    /**
     * Parses a byte array received from a Three Stones client application, and 
     * creates a response to send back. Server makes changes to the current 
     * session based on packets sent by the client; packets starting with 0 
     * create the session, packets starting with 1 start a game within the 
     * session, packets starting with 2 commit a client player's move to the 
     * game board, packets starting with 3 restart the game, and packets 
     * starting with 4 close the current session.
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
                 System.out
                         .println("Array header 2: received move from client");
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
                 session.restartGame();
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
