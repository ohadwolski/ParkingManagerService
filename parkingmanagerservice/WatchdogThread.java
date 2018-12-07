package parkingmanagerservice;




import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.Vector;

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
                    case T_TIME_MODE_STANDBY:
                        CheckSensorsErrorState();
                        break;
                    case ON_EVENT_MODE_STANDBY:
                        // In On_Event mode, a request for all-sensor status update is
                        // being made then after the check interval the sensors are being
                        // checked to prevent the possibility of a sensor that is OK but
                        // just hadn't changed all this time (prevent false positives).
                        if (RequestModeChecked == false) {
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
                t.sleep(ParkingManagerService.Data.getUpdate_interval() * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Thread " +  threadName + " exiting.");
    }

    private void CheckSensorsErrorState() {
        List<ParkingElement> ListOfSensors = ParkingManagerService.Data.getParkingSensorList();
        
    }

    public void exit() {
        run = false;
    }
}
