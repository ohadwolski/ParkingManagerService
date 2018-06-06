/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualEsp;

import parkingmanagerservice.messages;

import java.io.*;
import java.net.*;
import java.util.Date;

//import static parkingmanagerservice.MessageType.ECHO;

/**
 *
 * @author Ohad Wolski
 */
public class ConnectionThread implements Runnable {
    
    
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

   public void join() throws InterruptedException {
       t.join();
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
               System.out.println("Just connected to " + client.getRemoteSocketAddress());
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
               //e.printStackTrace();
               System.out.println("Could not connect!");
               connectionInitialized = false;
                try {
                    t.wait(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                break;
            }
         }



         try {
             //wait(1000);
             messages msg; // = new messages(0, new Date(), ECHO, 0);
             //ObjectOutputStream outObject = new ObjectOutputStream(server.getOutputStream());
             //outObject.writeObject(echo);

             ObjectInputStream inObject = new ObjectInputStream(inputStream);
             msg = (messages) inObject.readObject();
             msg.print();
             //System.out.println("Server says " + msg + "\n");

         } catch (UnknownHostException e) {
             System.err.println("Don't know about host : \n");
             continue;
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {

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
