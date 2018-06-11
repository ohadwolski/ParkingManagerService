package virtualEsp;

public class Threads {

    public ConnectionThread ConnectionThread;
    public ListenerThread ListenerThread;
    public SenderThread SenderThread;

    public void Start(){
        ConnectionThread = new ConnectionThread( "ConnectionThread");
        ConnectionThread.start();

        SenderThread = new SenderThread( "SenderThread");
        SenderThread.start();

        ListenerThread = new ListenerThread( "ListenerThread");
        ListenerThread.start();

    }


    public void exit() {

        virtualEsp.Threads.ConnectionThread.disconnect();
        virtualEsp.Threads.ListenerThread.exit();
        virtualEsp.Threads.SenderThread.exit();
        //virtualEsp.Threads.ListenerQueueThread.exit();
    }

}
