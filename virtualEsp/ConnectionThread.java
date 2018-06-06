/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualEsp;

import java.io.*;
import java.net.*;

/**
 *
 * @author Ohad Wolski
 */
public class ConnectionThread implements Runnable{
    
    
   private Thread t;
   private String threadName;
   private Socket client;
   private OutputStream outputStream;
   private InputStream inputStream;
   private DataInputStream in;
   private DataOutputStream out;

   private boolean connectionInitialized;

   ConnectionThread( String name) {
      threadName = name;
      System.out.println("Creating " +  threadName );
      connectionInitialized = false;

   }
   
   @Override
   public void run() {
      System.out.println("Running " +  threadName );
      while (true)
      {
         while (connectionInitialized == false) {
            try {
               System.out.println("Trying to connect to server on localhost with port 5000 ...");
               client = new Socket("localhost", 5000);
               outputStream = client.getOutputStream();
               out = new DataOutputStream(outputStream);
               inputStream = client.getInputStream();
               in = new DataInputStream(inputStream);

               connectionInitialized = true;

            } catch (SocketTimeoutException s) { // not suppose to happen because haven't set timeout
               System.out.println("Socket timed out!");
               connectionInitialized = false;
               break;
            } catch (IOException e) {
               e.printStackTrace();
               connectionInitialized = false;
               break;
            }
         }
         try {
            wait(1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      //try {
      //   client.close();
      //} catch (IOException e) {
      //   e.printStackTrace();
      //}
      //System.out.println("Thread " +  threadName + " exiting.");
   }
   
   public void start () {
      System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }

}
