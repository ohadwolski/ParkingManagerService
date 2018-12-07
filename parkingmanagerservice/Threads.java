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
    public  DataSaverThread DataSaverThread;

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

        DataSaverThread = new DataSaverThread("DataSaverThread");
        DataSaverThread.start();

    }


    public void exit() {
        ParkingManagerService.Threads.ConnectionThread.disconnect();
        ParkingManagerService.Threads.ListenerThread.exit();
        ParkingManagerService.Threads.SenderThread.exit();
        ParkingManagerService.Threads.ListenerQueueThread.exit();
        ParkingManagerService.Threads.WatchdogThread.exit();
        ParkingManagerService.Threads.DataSaverThread.exit();
    }

}
