package parkingmanagerservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Ohad Wolski
 */
public class ListenerThread implements Runnable{


    private Thread t;
    private String threadName;
    private BufferedReader in;
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
                    //if (in == null || in.ready() != true) {
                    //    in = new BufferedReader(new InputStreamReader(ParkingManagerService.Threads.ConnectionThread.getInputStream()));
                    //}
                    messages msg;
                    String msg_in_text_format;

                    if ((msg_in_text_format = in.readLine()) != null) {
                        System.out.println("Raw message received: " + msg_in_text_format);
                        msg = MessageConverter.convertStringToMessage(msg_in_text_format);
                        if (msg == null) throw new UnknownMessageFormat();
                        // add message to queue:
                        ParkingManagerService.Threads.ListenerQueueThread.addMessage(msg);
                    }

                    //
                } catch (IOException e) {
                    e.printStackTrace();
                    if (run) {
                        System.out.println("Network error in ListenerThread! Trying to connect again . . .");
                        ParkingManagerService.Threads.ConnectionThread.reconnect();
                        break;
                    }
                } catch (UnknownMessageFormat unknownMessageFormat) {
                    System.out.println("Unknown message format received! Ignoring . . .");
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

    private class UnknownMessageFormat extends Throwable {
    }

    public void SetInput(BufferedReader b) {
        in = b;
    }
}
