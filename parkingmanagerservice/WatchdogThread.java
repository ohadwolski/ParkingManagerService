package parkingmanagerservice;


import java.util.Date;
import java.util.List;

import static parkingmanagerservice.StateMachine.ON_EVENT_MODE_REQUEST_STATUS;

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
        boolean RequestModeChecked = false;
        // This thread checks the last time each sensor was updated
        // In case the sensor wasn't updated for a long period of
        // time, it's status will be updated to error, in order to
        // indicate that there might be a connection problem.
        // The check interval is set to 3 * update_interval
        // configured in the DataInterface class.

        while (run) {
            try {
                switch (ParkingManagerService.StateMachine) {
                    case REQ_MODE_WAIT_FOR_TIMER:
                    case REQ_MODE_REQUEST_STATUS:
                    case REQ_MODE_WAIT_FOR_STATUS:
                    case T_TIME_MODE_STANDBY:
                    case ON_EVENT_MODE_REQUEST_STATUS:
                    case ON_EVENT_MODE_WAIT_FOR_STATUS:
                        CheckSensorsErrorState();
                        RequestModeChecked = false;
                        break;
                    case ON_EVENT_MODE_STANDBY:
                        // In On_Event mode, a request for all-sensor status update is
                        // being made then after the check interval the sensors are being
                        // checked to prevent the possibility of a sensor that is OK but
                        // just hadn't changed all this time (prevent false positives).
                        if (!RequestModeChecked) {
                            ParkingManagerService.StateMachine = ON_EVENT_MODE_REQUEST_STATUS;
                            System.out.println(ParkingManagerService.StateMachine + ":");
                            RequestModeChecked = true;
                        } else {
                            CheckSensorsErrorState();
                            RequestModeChecked = false;
                            continue;
                        }
                        break;
                    default:
                        break;
                }
                // Wait for check interval
                t.sleep(ParkingManagerService.Data.getUpdate_interval() * 1000 * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Thread " +  threadName + " exiting.");
    }

    private void CheckSensorsErrorState() {
        long currentTime = (new Date()).getTime();
        List<ParkingElement> ListOfSensors = ParkingManagerService.Data.getParkingSensorList();
        for (ParkingElement element : ListOfSensors) {
            if (!(element instanceof ParkingSensor)) {
                continue;
            }
            ParkingSensor sensor = (ParkingSensor) element;
            if (currentTime - sensor.getTimeStamp().getTime() > ParkingManagerService.Data.getUpdate_interval() * 1000 * 3) {
                // Did not receive update from sensor 3*T seconds
                // Set sensor status to Error
                if (sensor.getStatus() != StatusElement.ERROR) {
                    sensor.setStatus(StatusElement.ERROR);
                    System.out.printf("Watchdog: Did not receive update from ");
                    sensor.print();
                    System.out.println("Please check sensor. Status changed to Error.");
                }
            }
        }
    }

    public void exit() {
        run = false;
    }

    public void join() throws InterruptedException {
        t.join();
    }
}
