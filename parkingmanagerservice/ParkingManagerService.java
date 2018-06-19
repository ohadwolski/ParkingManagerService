/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagerservice;

import virtualEsp.MessagesParser;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Ohad Wolski
 */
public class ParkingManagerService {

    public static Threads Threads = new Threads();
    public static ParkingLotDataIntrface parkingLot = new ParkingLotDataIntrface();
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
    	initializeDataStruct();


        
    }

    public static void test() {
        System.out.println("Called root.test() from root.ListenerThread");
    }
    
    public static void initializeDataStruct() {
    	
    	Date date = new Date();
    	parkingLot.addArea(1,TypeData.OPEN, 0);
    	parkingLot.addArea(2,TypeData.OPEN, 0);

		//Level 1
    	parkingLot.addSign(10, TypeData.RIGHT, 1);
    	parkingLot.addStatusElementToDBStatus(10, TypeData.ERROR, date, 0);
		
		parkingLot.addArea(3,TypeData.OPEN, 1);
		parkingLot.addArea(4,TypeData.OPEN, 1);
		
		//Level 1, area 3
		parkingLot.addSign(11, TypeData.LEFT, 3);
		parkingLot.addStatusElementToDBStatus(11, TypeData.ERROR, date, 0);
		parkingLot.addParkingSpot(20, TypeData.REGULAR, 3);
		parkingLot.addStatusElementToDBStatus(20, TypeData.ERROR, date, 0);
		parkingLot.addParkingSpot(21, TypeData.REGULAR, 3);
		parkingLot.addStatusElementToDBStatus(21, TypeData.ERROR, date, 0);
		
		//Level 1, area 4
		parkingLot.addSign(12, TypeData.RIGHT, 4);
		parkingLot.addStatusElementToDBStatus(12, TypeData.ERROR, date, 0);
		parkingLot.addParkingSpot(22, TypeData.REGULAR, 4);
		parkingLot.addStatusElementToDBStatus(22, TypeData.ERROR, date, 0);
		parkingLot.addParkingSpot(23, TypeData.REGULAR, 4);
		parkingLot.addStatusElementToDBStatus(23, TypeData.ERROR, date, 0);
		
		//Level 2
		parkingLot.addSign(13, TypeData.RIGHT, 2);
		parkingLot.addStatusElementToDBStatus(13, TypeData.ERROR, date, 0);
		parkingLot.addArea(5,TypeData.OPEN, 2);
	
		//Level 2, area 5
		parkingLot.addSign(14, TypeData.LEFT, 2);
		parkingLot.addStatusElementToDBStatus(14, TypeData.ERROR, date, 0);
		parkingLot.addParkingSpot(24, TypeData.REGULAR, 5);
		parkingLot.addStatusElementToDBStatus(24, TypeData.ERROR, date, 0);
    	
    }




}

