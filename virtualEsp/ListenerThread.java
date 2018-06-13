package virtualEsp;

import parkingmanagerservice.messages;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Ohad Wolski
 */
public class ListenerThread implements Runnable{


    private Thread t;
    private String threadName;
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

            while (virtualEsp.Threads.ConnectionThread.getConnectionState()) {
                try {
                    in = virtualEsp.Threads.ConnectionThread.getInputStream();
                    //out = virtualEsp.Threads.ConnectionThread.getOutputStream();

                    messages msg;
                    msg = (messages) in.readObject();
                    // print message received from server:
                    System.out.println("Received message from server: \n");
                    msg.print();
                    //
                } catch (IOException | ClassNotFoundException e) {
                    //e.printStackTrace();
                    if (run) {
                        System.out.println("Network error! Trying to connect again . . .");
                        virtualEsp.Threads.ConnectionThread.reconnect();
                        break;
                    }
                }

            }

            //if ((!virtualEsp.Threads.ConnectionThread.getConnectionState()) && run) {
            //    virtualEsp.Threads.ConnectionThread.reconnect();
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
