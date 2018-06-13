package parkingmanagerservice;

import java.io.IOException;
import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;

/**
 *
 * @author Ohad Wolski
 */
public class ListenerThread implements Runnable{


    private Thread t;
    private String threadName;
    //private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean run;

    ListenerThread( String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
        run = true;
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

        while (run) {

            while (ParkingManagerService.Threads.ConnectionThread.getConnectionState()) {
                try {
                    in = ParkingManagerService.Threads.ConnectionThread.getInputStream();
                    //out = ParkingManagerService.Threads.ConnectionThread.getOutputStream();

                    messages msg;
                    msg = (messages) in.readObject();
                    // add message to queue:
                    ParkingManagerService.Threads.ListenerQueueThread.addMessage(msg);
                    //
                } catch (IOException | ClassNotFoundException e) {
                    //e.printStackTrace();
                    if (run) {
                        System.out.println("Network error! Trying to connect again . . .");
                        ParkingManagerService.Threads.ConnectionThread.reconnect();
                        break;
                    }
                }

            }

            //if ((!ParkingManagerService.Threads.ConnectionThread.getConnectionState()) && run) {
            //    System.out.println("GOT HERE");
            //    ParkingManagerService.Threads.ConnectionThread.reconnect();
            //}

            try {
                t.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Thread " +  threadName + " exiting.");

    }

    public void exit() {
        run = false;
    }

}
