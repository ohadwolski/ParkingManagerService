/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagerservice;

import virtualEsp.MessagesParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import static java.lang.Thread.sleep;
import static parkingmanagerservice.MessageType.*;
import static parkingmanagerservice.StateMachine.*;

/**
 *
 * @author Ohad Wolski
 */
public class ParkingManagerService {
    public static StateMachine StateMachine = INIT;
    public static Threads Threads = new Threads();
    public static boolean run = true;
    public static DataInterface Data = new DataInterface();
    public static List<MessageType> ExpectedEventsList = new ArrayList<MessageType>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        while (run) {
            switch (StateMachine) {
                case INIT:
                    LoadData();
                    Threads.Start();
                    StateMachine = WAIT_FOR_INITIAL_CONNECTION;
                    break;
                case WAIT_FOR_INITIAL_CONNECTION:
                    if (Threads.ConnectionThread.getConnectionState() == true) {
                        StateMachine = GENERAL_CONFIGURATION;
                    }
                    break;
                case GENERAL_CONFIGURATION:
                    CreateGeneralConfigurationMessages();
                    StateMachine = WAIT_FOR_GENERAL_CONFIGURATION_FINISH;
                    break;
                case WAIT_FOR_GENERAL_CONFIGURATION_FINISH:
                    if (ExpectedEventsList.isEmpty()) {
                        StateMachine = SENSORS_CONFIGURATION;
                    }
                    break;
                case SENSORS_CONFIGURATION:
                    CreateSensorsConfiguration();
                    break;
                case WAIT_FOR_AUTO_SENSOR_RESPONSE:
                    break;
                case GET_SENSORS_DATA_FOR_AUTO_BUILD:
                    break;
                case WAIT_FOR_SENSORS_DATA_RESPONSE:
                    break;
                case BUILD_DATA_TREE_FROM_AUTO_CONFIGURATION:
                    StateMachine = GROUP_AND_DISPLAYS_CONFIGURATION;
                    break;
                case WAIT_FOR_SENSORS_CONFIGURATION:
                    if (SensorsConfigurationDone()) {
                        StateMachine = GROUP_AND_DISPLAYS_CONFIGURATION;
                    }
                    break;
                case GROUP_AND_DISPLAYS_CONFIGURATION:
                    CreateGroupAndDisplayConfiguration();
                    StateMachine = WAIT_FOR_GROUP_AND_DISPLAYS_CONFIGURATION;
                    break;
                case WAIT_FOR_GROUP_AND_DISPLAYS_CONFIGURATION:
                    if (ExpectedEventsList.isEmpty()) {
                        StateMachine = OPERATION_MODE_CONFIGURATION;
                    }
                    break;
                case OPERATION_MODE_CONFIGURATION:
                    CreateOperationModeConfiguration();
                    StateMachine = WAIT_FOR_OPERATION_MODE_CONFIGURATION;
                    break;
                case WAIT_FOR_OPERATION_MODE_CONFIGURATION:
                    if (ExpectedEventsList.isEmpty()) {
                        StateMachine = STANDBY;
                    }
                    break;
                case STANDBY:
                    // case for per-request, on-event, every T
                    // assume 0: manual by server, 1: on event, 2: every T seconds
                    switch (Data.getWorking_mode()) {
                        case 0:
                            StateMachine = REQ_MODE_WAIT_FOR_TIMER;
                            break;
                        case 1:
                            StateMachine = ON_EVENT_MODE_REQUEST_STATUS;
                            break;
                        case 2:
                            StateMachine = T_TIME_MODE_STANDBY;
                            break;
                    }
                    break;

                case REQ_MODE_WAIT_FOR_TIMER:
                    break;
                case REQ_MODE_REQUEST_STATUS:
                    break;
                case REQ_MODE_WAIT_FOR_STATUS:
                    break;
                case ON_EVENT_MODE_REQUEST_STATUS:
                    break;
                case ON_EVENT_MODE_WAIT_FOR_STATUS:
                    break;
                case ON_EVENT_MODE_STANDBY:
                    break;
                case T_TIME_MODE_STANDBY:
                    break;

            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




    }

    private static void CreateOperationModeConfiguration() {
        // create message for operating mode
        // add to expected list
    }


    private static void CreateSensorsConfiguration() {
        if (Data.isAuto_init()) {
            // if init is automatic: get info from server and build tree
            messages chk_if_auto_is_finished = new messages(null, new Date(), GET_AUTO_INIT_FINISHED, 0);
            Threads.SenderQueue.addMessage(chk_if_auto_is_finished);
            //ExpectedEventsList.add(AUTO_INIT_FINISHED);
            StateMachine = WAIT_FOR_AUTO_SENSOR_RESPONSE;
            // create messages for getting auto detect
            // add messages of finish auto detect to expected list
        } else {
            // create sensors messages for init
            // according to data
            // add messages to queue
            // add to Expected List the correct info
            StateMachine = WAIT_FOR_SENSORS_CONFIGURATION;
        }
    }

    private static boolean SensorsConfigurationDone() {
        if (ExpectedEventsList.isEmpty()) {
            // check if auto or manual
            if (Data.isAuto_init())
            {

                // auto: go through messages and build parking area than return true
                // send indication to receive queue to process messages

            } else {
                // manual continue: return true

            }
            return true;
        }
        return false;
    }

    private static void CreateGroupAndDisplayConfiguration() {
        // create groups messages, attach sensors to groups
        // create displays init messages & attachment to groups
        // add everything to expected list
    }


    private static void CreateGeneralConfigurationMessages() {
        // creating messages:
        messages init = new messages(null,new Date(),((Data.isAuto_init())? INIT_AUTO : INIT_MANUAL),0);
        messages init_max_group = new messages(null,new Date(), INIT_MAX_GROUP_NUM, 100);
        messages init_max_display = new messages(null,new Date(), INIT_MAX_DISPLAY_NUM, 100);
        messages init_max_controllers = new messages(null,new Date(), INIT_MAX_CONTROLLERS_NUM, 100);

        // adding messages to list

        Threads.SenderQueue.addMessage(init);
        Threads.SenderQueue.addMessage(init_max_group);
        Threads.SenderQueue.addMessage(init_max_display);
        Threads.SenderQueue.addMessage(init_max_controllers);

        // adding messages to expected list
        ExpectedEventsList.add(INIT_SUCCEEDED);
        ExpectedEventsList.add(INIT_MAX_GROUP_NUM_SUCCEEDED);
        ExpectedEventsList.add(INIT_MAX_DISPLAY_NUM_SUCCEEDED);
        ExpectedEventsList.add(INIT_MAX_CONTROLLERS_NUM_SUCCEEDED);
    }

    private static void LoadData() {

        // code for loading data
    }



}

