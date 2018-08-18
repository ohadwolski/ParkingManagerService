package parkingmanagerservice;


import java.util.Vector;

/**
 *
 * @author Ohad Wolski
 */
public class SenderQueue {//implements Runnable {
    private Thread t;
    //private String threadName;
    //private boolean run;
    private Vector<messages> SendQueue;

    SenderQueue( String name) {
        //threadName = name;
        SendQueue = new Vector<messages>();

        //System.out.println("Creating " +  threadName );
        //run = true;
        // initializing receive queue:


    }
/*
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
                //System.out.println("Checking send queue . . .");
                //if (SendQueue.isEmpty()) {
                //    System.out.println("No messages found in send queue. Trying again later.");
                //    t.sleep(5000);
                //    //continue;
                //} else {
                //    System.out.println("Found " + SendQueue.size() + "messages in send queue. Handling:\n");
                    // work on vector
                //}
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Thread " +  threadName + " exiting.");
    }
*/
    public boolean is_empty() {
        return SendQueue.isEmpty();
    }

    public messages get_first_message() {
        if (! is_empty()) {
            return SendQueue.firstElement();
        } else {
            return null;
        }
    }

    public void remove_first_message() {
        if (! is_empty()) {
            SendQueue.remove(0);
        }
    }

    public void addMessage (messages msg) {
        SendQueue.add(msg); // Do we need to copy the msg or is it ok to use reference?
    }

    public void addVector ( Vector<messages> messagesVector) {
        SendQueue.addAll(messagesVector);
    }

/*
    public void exit() {
        run = false;
    }
*/

}
