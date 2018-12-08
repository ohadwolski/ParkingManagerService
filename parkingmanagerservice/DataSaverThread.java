package parkingmanagerservice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class DataSaverThread implements Runnable {
    private Thread t;
    private String threadName;
    private boolean run;
    private long SavingInterval = 15000;

    DataSaverThread(String name) {
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
            switch (ParkingManagerService.StateMachine) {
                case REQ_MODE_WAIT_FOR_TIMER:
                case REQ_MODE_REQUEST_STATUS:
                case REQ_MODE_WAIT_FOR_STATUS:
                case ON_EVENT_MODE_STANDBY:
                case ON_EVENT_MODE_REQUEST_STATUS:
                case ON_EVENT_MODE_WAIT_FOR_STATUS:
                case T_TIME_MODE_STANDBY:
                    SaveDataToFile();
                    break;
                default:
                    break;
            }
            try {
                t.sleep(SavingInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Thread " +  threadName + " exiting.");
    }

    private void SaveDataToFile() {
        System.out.println("Saving data to file...");
        try {
            FileOutputStream fileOut = new FileOutputStream("/ParkingManagerDatabase.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(ParkingManagerService.Data);
            out.close();
            fileOut.close();
            System.out.println("Data saved to /ParkingManagerDatabase.ser");
        } catch (IOException i) {
            System.out.println("Can't save file /ParkingManagerDatabase.ser");
            i.printStackTrace();
            ParkingManagerService.exit();
        }
    }

    public void exit() {
        run = false;
    }

    public void join() throws InterruptedException {
        t.join();
    }
}
