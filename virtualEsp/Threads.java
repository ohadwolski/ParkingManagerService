package virtualEsp;

public class Threads {

    public ConnectionThread ConnectionThread;
    public ListenerThread ListenerThread;

    public void Start(){
        ConnectionThread = new ConnectionThread( "ConnectionThread");
        ConnectionThread.start();

        ListenerThread = new ListenerThread( "ListenerThread");
        ListenerThread.start();
    }

}
