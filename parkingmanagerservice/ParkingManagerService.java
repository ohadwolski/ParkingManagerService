/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagerservice;

/**
 *
 * @author Ohad Wolski
 */
public class ParkingManagerService {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        initialize();
        
        
        parkingmanagerservice.ConnectionThread ConnectionThread;
        ConnectionThread = new ConnectionThread( "ConnectionThread");
        ConnectionThread.start();
        
        //ListenerThread ListenerThread = new ListenerThread( "ConnectionThread");
        //ListenerThread.start();
        
        //SenderThread SenderThread = new SenderThread( "ConnectionThread");
        //SenderThread.start();
        
        //MsgQueuesHandler MsgQueuesHandler = new MsgQueuesHandler( "ConnectionThread");
        //MsgQueuesHandler.start();

        //DataBaseUpdater DataBaseUpdater = new DataBaseUpdater( "ConnectionThread"); // both for DB and user interface
        //DataBaseUpdater.start();

    }
    
     /**
     */
    public static void initialize() {
        // TODO code application logic here
        // initialize all of the data structures?
        // is this how it is made?
        
    }
// alon
}
