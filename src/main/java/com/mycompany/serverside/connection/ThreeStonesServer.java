package com.mycompany.serverside.connection;

import com.mycompany.serverside.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 1511430
 */
public class ThreeStonesServer { 
    private final  int port;
    private static final int BUFSIZE = 32;  
    private ThreeStonesServerSession session;
    
    public ThreeStonesServer() {
        this.port = 7;        
    }
    public void startServer() {        
        int msgSize;
        byte[] bb = new byte[BUFSIZE];
        for (;;) {
            try {
                InetAddress address = InetAddress.getLocalHost();
                System.out.println("Three Stones server online.");
                System.out.println("Server's IP address: " 
                        + address.getHostAddress());
                System.out.println("Running on port " + port);
                
            } catch (UnknownHostException e) {
                System.out.println("Unable to determine this server's address");
            }
            
            try (ServerSocket sk = new ServerSocket(this.port);
                    Socket client = sk.accept();) {
                System.out.println("Client msg recived");
                InputStream in = client.getInputStream();
                OutputStream out = client.getOutputStream();
                while((msgSize = in.read(bb)) != -1) {
                    byte[] serverRespones = parseIncomingPacket(bb);
                    System.out.println("---->> " + msgSize);
                    out.write(serverRespones, 0, serverRespones.length);              
                }
                
            } catch (IOException ex) {
                System.out.println("something went bad:  " + ex.getMessage());
            }
        }           
    }

    private byte[] parseIncomingPacket(byte[] input) {
         String response = "0";
         System.out.println("in parseIncomingPacket");
         switch(input[0]){
             //Session initialized.
             case 0:
                 this.session = new ThreeStonesServerSessionImpl();
                 System.out.println("Session made");
                 response = "1";
                 break;
             //Request for new game.
             case 1:
                 session.newGame();
                 response = "1";
                 break;
             //Game in progress and a move was made    
             case 2:
                 String[] userMsg = clientPacket[1].split(",");
                 int x = Integer.parseInt(userMsg[0]);
                 int y = Integer.parseInt(userMsg[1]);
                 session.setClientMove(x, y);
                 response = "1x" + session.getAIMove() + "," + session.getScores() + "," + session.getStones();
                 break;
             //Restart Game    
             case 3: 
                 session.resstartGame();
                 response = "1";
                 break;
             //End game
            // case "4":               
             
         }
         return response.getBytes();
    }
}