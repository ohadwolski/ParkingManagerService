package virtualEsp;

import parkingmanagerservice.messages;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

/**
 *
 * @author Ohad Wolski
 */
public class SenderThread implements Runnable{
    private Thread t;
    private String threadName;
    private ObjectOutputStream out;
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

            while (virtualEsp.Threads.ConnectionThread.getConnectionState()) {
                try {
                    out = virtualEsp.Threads.ConnectionThread.getOutputStream();

                    // get message from sender queue:
                    if (! virtualEsp.sendQueue.isEmpty()) {
                        messages msg_to_send = virtualEsp.sendQueue.firstElement();
                        System.out.println("Sending message:\n");
                        msg_to_send.print();
                        out.writeObject(msg_to_send);
                        System.out.println("Sent successfully.\n");
                        virtualEsp.sendQueue.remove(0);
                    } else {
                        System.out.println("Send queue is empty. Will try again later . . .");
                        t.sleep(5000);
                    }
                    // sleep for random time between 1s to 5s
                    Random rand = new Random();
                    int  n = rand.nextInt(5000) + 1000;
                    t.sleep(n);

                } catch (IOException e) {
                    if (run) {
                        System.out.println("Network error! Trying to connect again . . .");
                        virtualEsp.Threads.ConnectionThread.reconnect();
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            //if ((!virtualEsp.Threads.ConnectionThread.getConnectionState()) && run) {
            //    virtualEsp.Threads.ConnectionThread.reconnect();
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
