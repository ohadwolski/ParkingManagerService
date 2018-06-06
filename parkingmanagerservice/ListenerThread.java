package parkingmanagerservice;

/**
 *
 * @author Ohad Wolski
 */
public class ListenerThread implements Runnable{


    private Thread t;
    private String threadName;

    ListenerThread( String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

    @Override
    public void run() {
        //ParkingManagerService.test();
        //ParkingManagerService.Threads.ConnectionThread.test();

    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }


}
