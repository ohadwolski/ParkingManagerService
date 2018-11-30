/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagerservice;

import java.io.IOException;
import java.net.*;
import java.io.*;
//import java.util.*;

//import static parkingmanagerservice.MessageType.*;

/**
 *
 * @author Ohad Wolski
 */
public class ConnectionThread implements Runnable{


   private Thread t;
   private String threadName;
   //private ServerSocket serverSocket;
   private Socket server;
   private OutputStream out;
   private InputStream in;
   private boolean connected;
   private boolean run;

   ConnectionThread( String name) {
      threadName = name;
      System.out.println("Creating " +  threadName );
      connected = false;   // possibly we need to put a lock on "connected"!!
      run = true;
      /*try {
         serverSocket = new ServerSocket(5000);
      } catch (IOException e) {
         e.printStackTrace();
      }
      */
   }

   public void start () {
      System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }

   @Override
   public void run() {
      System.out.println("Running " +  threadName );
      while (run)
      {
         while (connected == false)
         {
            try {
               //System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
               System.out.println("Trying to connect to ESP with ip " + ParkingManagerService.Data.getEsp_ip_address() +
                       " and port " + ParkingManagerService.Data.getEsp_port_number() + " ...");
               //server = serverSocket.accept();
               server = new Socket(ParkingManagerService.Data.getEsp_ip_address(), ParkingManagerService.Data.getEsp_port_number());
               System.out.println("Just connected to " + server.getRemoteSocketAddress());
               //out = new ObjectOutputStream(server.getOutputStream());
               //in = new ObjectInputStream(server.getInputStream());
               out = server.getOutputStream();
               in = server.getInputStream();
               connected = true;
               //messages echo = new messages(0, new Date(), ECHO, 0);
               //ObjectOutputStream outObject = new ObjectOutputStream(server.getOutputStream());
               //outObject.writeObject(echo);

               //ObjectInputStream inObject = new ObjectInputStream(server.getInputStream());
               //echo = (messages) inObject.readObject();
               //echo.print();

               //server.close();

            } catch (SocketTimeoutException s) { // not suppose to happen because haven't set timeout
               System.out.println("Socket timed out!");
               connected = false;
               continue; // try again
            } catch (IOException e) {
               connected = false;
               e.printStackTrace();
               break;
            }

         }

         try {
            t.sleep(1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      System.out.println("Thread " +  threadName + " exiting.");
   }


   @SuppressWarnings("Duplicates")
   public void disconnect() {

      try {
         System.out.println("Closing connection . . .");
         server.close();
         System.out.println("Done!");
         run = false;
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public boolean getConnectionState() {
      return connected;
   }

   public void reconnect() {
      connected = false;
   }

   public OutputStream getOutputStream()
   {
      return  out;
   }
   public InputStream getInputStream()
   {
      return  in;
   }

}
