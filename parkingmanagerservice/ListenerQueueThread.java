package parkingmanagerservice;

import java.util.Vector;



/**
 *
 * @author Ohad Wolski
 */
public class ListenerQueueThread implements Runnable{
    private Thread t;
    private String threadName;
    private boolean run;
    private Vector<messages> ReceiveQueue; //vector is synchronized so no lock is needed

    ListenerQueueThread( String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
        run = true;
        // initializing receive queue:
        ReceiveQueue = new Vector<messages>();

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
        try {
            while (run) {
                //System.out.println("Checking receive queue . . .");
                if (ReceiveQueue.isEmpty()) {
                    //System.out.println("No messages found in receive queue. Trying again later.");
                    t.sleep(1000);
                    //continue;
                } else {
                    System.out.println("Found " + ReceiveQueue.size() + " new messages in receive queue. Handling:\n");
                    // work on vector
                    while (! ReceiveQueue.isEmpty()) {
                        messages msg = ReceiveQueue.firstElement();
                        messageHandler.handleMessage(msg);
                        ReceiveQueue.remove(0);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Thread " +  threadName + " exiting.");
    }


    public void addMessage (messages msg) {
        ReceiveQueue.add(msg); // Do we need to copy the msg or is it ok to use reference?
    }

    public void ClearQueue() {
        ReceiveQueue.clear();
    }

    public void exit() {
        run = false;
    }

    public void join() throws InterruptedException {
        t.join();
    }
}
