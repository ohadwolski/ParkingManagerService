package parkingmanagerservice;




import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

/**
 *
 * @author Ohad Wolski
 */
public class WatchdogThread implements Runnable{
    private Thread t;
    private String threadName;
    private boolean run;

    WatchdogThread(String name) {
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
        System.out.println("Running " + threadName);
        while (run) {
            // Create configuration messages
            System.out.println("Watchdog: Creating configuration messages and adding them to Send Queue");
            initializeSenderQueueForTest();
            // Check communication status with sensors

            // Update data in xml/json files

            System.out.println("Watchdog: sleeps for 60s");
            try {
                t.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Thread " +  threadName + " exiting.");
    }

    private void initializeSenderQueueForTest() {

        MessagesParser sender_messages_parser = new MessagesParser("sender_messages_for_demo.xml");
        Vector<messages> sender_messages_for_demo = sender_messages_parser.getMessagesList();
        ParkingManagerService.Threads.SenderQueueThread.addVector(sender_messages_for_demo);

    }

    public void exit() {
        run = false;
    }
}
