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
    private int a, b;
    
    public ThreeStonesServer() {
        this.port = 7;  
        a = -1;
        b = -1;
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
                    System.out.println("---->>   sent");
                }
                
            } catch (IOException ex) {
                System.out.println("something went bad:  " + ex.getMessage());
            }
        }           
    }

    private byte[] parseIncomingPacket(byte[] input) {
        
         System.out.println("in parseIncomingPacket");
         byte[] response = new byte[10];
         switch(input[0]){
             //Session initialized.
             case 0:
                 System.out.println(a + "  ^^1^  "  + b);
                 this.session = new ThreeStonesServerSessionImpl();
                 System.out.println("Session made");
                 response[0] = 1;
                 this.a = input[1];
                 this.b = input[2];
                 System.out.println(a + "  ^^2^  "  + b);
                 break;
             //Request for new game.
             case 1:
                 System.out.println(a + "  ^^3^  "  + b);
                 session.newGame();
                 response[0] = 1;
                 break;
             //Game in progress and a move was made    
             case 2:
                 //int x = Integer.parseInt(userMsg[0]);
                 //int y = Integer.parseInt(userMsg[1]);
                 //session.setClientMove(userMsg[1], userMsg[2]);
                 response[0] = 1;
                 response[1] = 4;
                 response[2] = 2;
                 response[3] = 0;
                 break;
             //Restart Game    
             case 3: 
                 session.resstartGame();
                 response[0] = 1;
                 break;
             //End game
            // case "4":               
             
         }
         return response;
    }
}