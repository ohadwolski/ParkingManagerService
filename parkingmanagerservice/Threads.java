package parkingmanagerservice;

import virtualEsp.MessagesParser;

import java.util.Vector;

public class Threads {

    public ConnectionThread ConnectionThread;
    public ListenerThread ListenerThread;
    public SenderThread SenderThread;
    public ListenerQueueThread ListenerQueueThread;
    public SenderQueue SenderQueue;
    public WatchdogThread WatchdogThread;

    public void Start(){ // need to check that we open input here first and in ESP we open output first so we don't get deadlocked
        ConnectionThread = new ConnectionThread( "ConnectionThread");
        ConnectionThread.start();

        ListenerThread = new ListenerThread( "ListenerThread");
        ListenerThread.start();

        ListenerQueueThread = new ListenerQueueThread( "ListenerQueueThread");
        ListenerQueueThread.start();

        SenderThread = new SenderThread( "SenderThread");
        SenderThread.start();

        SenderQueue = new SenderQueue( "SenderQueueThread");
        //initializeSenderQueueForTest();
        //SenderQueueThread.start();

        WatchdogThread = new WatchdogThread("WatchdogThread");
        WatchdogThread.start();

    }

    private void initializeSenderQueueForTest() {

        /*
        MessagesParser sender_messages_parser = new MessagesParser("sender_messages_for_demo_esp.xml");
        Vector<messages> sender_messages_for_demo = sender_messages_parser.getMessagesList();
        SenderQueueThread.addVector(sender_messages_for_demo);
        */
        /*
        // Create and add messages for test here:
        messages msg;

        msg = new messages(0,new Date(), MessageType.PARKING_SPOT_FREED, 0);
        SenderQueueThread.addMessage(msg);

        msg = new messages(1,new Date(), MessageType.PARKING_SPOT_TAKEN, 0);
        SenderQueueThread.addMessage(msg);

        msg = new messages(2,new Date(), MessageType.SET_SIGN_NO_ENTRY, 0);
        SenderQueueThread.addMessage(msg);

        msg = new messages(3,new Date(), MessageType.SET_PAKING_SPOT_LED_OFF, 0);
        SenderQueueThread.addMessage(msg);

        msg = new messages(4,new Date(), MessageType.UPDATE_COUNTER, 5);
        SenderQueueThread.addMessage(msg);

        msg = new messages(5,new Date(), MessageType.SET_PAKING_SPOT_LED_GREEN, 0);
        SenderQueueThread.addMessage(msg);
        */
    }

    public void exit() {
        ParkingManagerService.Threads.ConnectionThread.disconnect();
        ParkingManagerService.Threads.ListenerThread.exit();
        ParkingManagerService.Threads.SenderThread.exit();
        ParkingManagerService.Threads.ListenerQueueThread.exit();
        ParkingManagerService.Threads.WatchdogThread.exit();
    }

}
