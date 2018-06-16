package parkingmanagerservice;

import java.io.IOException;
//import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 *
 * @author Ohad Wolski
 */
public class SenderThread implements Runnable {
    private Thread t;
    private String threadName;
    private ObjectOutputStream out;
    //private ObjectInputStream in;
    private boolean run;

    SenderThread (String name) {
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
                    //in = ParkingManagerService.Threads.ConnectionThread.getInputStream();
                    out = ParkingManagerService.Threads.ConnectionThread.getOutputStream();

                    // get message from sender queue:
                    if (! ParkingManagerService.Threads.SenderQueueThread.is_empty()) {
                        messages msg_to_send = ParkingManagerService.Threads.SenderQueueThread.get_first_message();
                        System.out.println("Sending message:\n");
                        msg_to_send.print();
                        out.writeObject(msg_to_send);
                        System.out.println("Sent successfully.\n");
                        ParkingManagerService.Threads.SenderQueueThread.remove_first_message();
                    } else {
                        System.out.println("Send queue is empty. Will try again later . . .");
                        t.sleep(5000);
                    }
                    t.sleep(1000);
                } catch (IOException e) {
                    //e.printStackTrace();
                    if (run) {
                        System.out.println("Network error! Trying to connect again . . .");
                        ParkingManagerService.Threads.ConnectionThread.reconnect();
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            //if ((!ParkingManagerService.Threads.ConnectionThread.getConnectionState()) && run) {
            //    ParkingManagerService.Threads.ConnectionThread.reconnect();
            //}

            try {
                t.sleep(5000);
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