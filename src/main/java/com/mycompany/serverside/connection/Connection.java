package com.mycompany.serverside.connection;

import com.mycompany.serverside.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 1511430
 */
public class Connection { 
    private final  int port;
    private static final int BUFSIZE = 32;  
    private SessionDAO session;
    
    public Connection() {
        this.port = 7;  
        System.out.println("Connection");        
    }
    public void startServer() {        
        int msgSize;
        byte[] bb = new byte[BUFSIZE];
        for (;;) {
            try (ServerSocket sk = new ServerSocket(this.port);
                    Socket client = sk.accept();) {
                System.out.println("Client msg recived");
                InputStream in = client.getInputStream();
                OutputStream out = client.getOutputStream();
                while((msgSize = in.read(bb)) != -1) {
                    byte[] serverRespones = parseIncomingPacket(new String(bb));
                    System.out.println("---->> " + msgSize);
                    out.write(serverRespones, 0, serverRespones.length);              
                }
                
            } catch (IOException ex) {
                System.out.println("something went bad:  " + ex.getMessage());
            }
        }           
    }
    private byte[] parseIncomingPacket(String msg) {
         String[] clientPacket = msg.split(",");
         String response = "0";
         System.out.println("in parseIncomingPacket");
         switch(clientPacket[0]){
             //Session initialized.
             case "0":
                 this.session = new SessionDAOImpl();
                 System.out.println("Session made");
                 response = "1";
                 break;
             //Request for new game.
             case "1":
                 session.newGame();
                 response = "1";
                 break;
             //Game in progress and a move was made    
             case "2":
                 session.setClientMove(Integer.parseInt(clientPacket[1]));
                 response = session.getAIMove() + "," + session.getScores() + "," + session.getStones();
                 break;
             //Restart Game    
             case "3": 
                 session.resstartGame();
                 response = "1";
                 break;
             //End game
            // case "4":               
             
         }
         return response.getBytes();
    }
}