/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagerservice;

import java.io.IOException;
import java.net.*;
import java.io.*;

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
            in = new DataInputStream(server.getInputStream());
            out = new DataOutputStream(server.getOutputStream());




            // while with delay, send echo every now and than, update status and break for retrying

            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            System.out.println(in.readUTF());
            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
            server.close();

         } catch (SocketTimeoutException s) { // not suppose to happen because haven't set timeout
            System.out.println("Socket timed out!");
            continue; // try again
         } catch (IOException e) {
            e.printStackTrace();
            break;
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
