package parkingmanagerservice;

import java.io.IOException;
//import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;


/**
 *
 * @author Ohad Wolski
 */
public class SenderThread implements Runnable {
    private Thread t;
    private String threadName;
    private PrintWriter out;
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

                    //out = new PrintWriter(ParkingManagerService.Threads.ConnectionThread.getOutputStream(), true);

                    // get message from sender queue:
                    if (! ParkingManagerService.Threads.SenderQueue.is_empty()) { // && ParkingManagerService.PushOneMessage) {
                        messages msg_to_send = ParkingManagerService.Threads.SenderQueue.get_first_message();
                        System.out.println("Sending message:");
                        msg_to_send.print();
                        String msg_in_text_format = MessageConverter.convertMessageToString(msg_to_send);
                        out.printf(msg_in_text_format);
                        System.out.println("Raw message sent: " + msg_in_text_format);
                        if (out.checkError()) {
                            throw new IOException();
                        }

                        System.out.println("Sent successfully.");
                        ParkingManagerService.Threads.SenderQueue.remove_first_message();


                    } else {
                        //System.out.println("Send queue is empty. Will try again later . . .");
                        t.sleep(500);
                    }
                    t.sleep(500);
                } catch (IOException e) {
                    e.printStackTrace();
                    if (run) {
                        System.out.println("Network error in SenderThread! Trying to connect again . . .");
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

    public void SetOutput(PrintWriter b) {
        out = b;
    }
}
