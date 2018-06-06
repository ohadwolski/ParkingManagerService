/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagerservice;

import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.*;

import static parkingmanagerservice.MessageType.*;

/**
 *
 * @author Ohad Wolski
 */
public class ConnectionThread implements Runnable{
    
    
   private Thread t;
   private String threadName;
   private ServerSocket serverSocket;
   private Socket server;
   private DataInputStream in;
   private DataOutputStream out;

   ConnectionThread( String name) {
      threadName = name;
      System.out.println("Creating " +  threadName );
      try {
         serverSocket = new ServerSocket(5000);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   @Override
   public void run() {
      System.out.println("Running " +  threadName );
      while (true)
      {
         try {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            server = serverSocket.accept();
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            in = new DataInputStream(server.getInputStream());
            out = new DataOutputStream(server.getOutputStream());

            messages echo = new messages(0, new Date(), ECHO, 0);
            ObjectOutputStream outObject = new ObjectOutputStream(server.getOutputStream());
            outObject.writeObject(echo);

            ObjectInputStream inObject = new ObjectInputStream(server.getInputStream());
            echo = (messages) inObject.readObject();
            echo.print();

            // while with delay, send echo every now and than, update status and break for retrying

            //System.out.println(in.readUTF());
            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
            server.close();

         } catch (SocketTimeoutException s) { // not suppose to happen because haven't set timeout
            System.out.println("Socket timed out!");
            continue; // try again
         } catch (IOException e) {
            e.printStackTrace();
            break;
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         }
      }
      System.out.println("Thread " +  threadName + " exiting.");
   }
   
   public void start () {
      System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
//test
   public void test() {
      System.out.println("Called root.ConnectionThread.test() from root.ListenerThread");
   }
    
}
