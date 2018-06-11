package virtualEsp;

import parkingmanagerservice.MessageType;
import parkingmanagerservice.messages;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Ohad Wolski
 */
public class virtualEsp {

    public static Threads Threads = new Threads();
    public static Vector<messages> sendQueue;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        initialize();

        Threads.Start();


    }

    public static void initialize() {
        // TODO code application logic here
        // initialize all of the data structures
        sendQueue = new Vector<messages>();

        // add messages flow here
        // Create and add messages for test here:
        messages msg;

        msg = new messages(10,new Date(), MessageType.ECHO, 0);
        sendQueue.add(msg);

        msg = new messages(11,new Date(), MessageType.ACK_ECHO, 0);
        sendQueue.add(msg);

        msg = new messages(12,new Date(), MessageType.SET_SIGN_BACK, 0);
        sendQueue.add(msg);

        msg = new messages(13,new Date(), MessageType.SET_SIGN_FORWARD, 0);
        sendQueue.add(msg);

        msg = new messages(14,new Date(), MessageType.SET_SIGN_LEFT, 0);
        sendQueue.add(msg);

        msg = new messages(15,new Date(), MessageType.SET_SIGN_RIGHT, 0);
        sendQueue.add(msg);
        // in the future: add messages in delay to simulate a day

    }
}
