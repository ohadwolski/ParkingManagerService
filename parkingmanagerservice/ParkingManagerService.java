/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagerservice;

import java.util.Vector;

/**
 *
 * @author Ohad Wolski
 */
public class ParkingManagerService {

    public static Threads Threads = new Threads();
    //public static Vector<messages> sender_messages_for_demo;
    //public static SenderQueue SenderQueue;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        initialize();

        Threads.Start();


    }
    
     /**
     */
    public static void initialize() {
        // TODO code application logic here



        
    }

    public static void test() {
        System.out.println("Called root.test() from root.ListenerThread");
    }

}

