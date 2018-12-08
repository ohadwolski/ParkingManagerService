/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagerservice;

import sun.awt.windows.ThemeReader;

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

        System.out.println("Welcome to Parking Manager Service.");
        System.out.println("Initializing...");

        while (run) {
            switch (StateMachine) {
                case INIT:
                    LoadData();
                    Threads.Start();
                    StateMachine = WAIT_FOR_INITIAL_CONNECTION;
                    System.out.println(StateMachine + ":");
                    break;
                case WAIT_FOR_INITIAL_CONNECTION:
                    if (Threads.ConnectionThread.getConnectionState() == true) {
                        StateMachine = GENERAL_CONFIGURATION;
                        System.out.println(StateMachine + ":");
                    }
                    break;
                case GENERAL_CONFIGURATION:
                    //CreateGeneralConfigurationMessages();
                    StateMachine = GENERAL_CONFIGURATION_MAX_GROUPS; //WAIT_FOR_GENERAL_CONFIGURATION_FINISH;
                    System.out.println(StateMachine + ":");
                    break;
                case GENERAL_CONFIGURATION_INIT:
                    CreateGeneralConfigurationMessages();
                    StateMachine = WAIT_FOR_GENERAL_CONFIGURATION_INIT;
                    System.out.println(StateMachine + ":");
                    break;
                case WAIT_FOR_GENERAL_CONFIGURATION_INIT:
                    if (ExpectedEventsList.isEmpty()) {
                        StateMachine = WAIT_FOR_GENERAL_CONFIGURATION_FINISH;
                        System.out.println(StateMachine + ":");
                    }
                    break;
                case GENERAL_CONFIGURATION_MAX_GROUPS:
                    CreateGeneralConfigurationMessages();
                    StateMachine = WAIT_FOR_GENERAL_CONFIGURATION_MAX_GROUPS;
                    System.out.println(StateMachine + ":");
                    break;
                case WAIT_FOR_GENERAL_CONFIGURATION_MAX_GROUPS:
                    if (ExpectedEventsList.isEmpty()) {
                        StateMachine = GENERAL_CONFIGURATION_MAX_DISPLAYS;
                        System.out.println(StateMachine + ":");
                    }
                    break;
                case GENERAL_CONFIGURATION_MAX_DISPLAYS:
                    CreateGeneralConfigurationMessages();
                    StateMachine = WAIT_FOR_GENERAL_CONFIGURATION_MAX_DISPLAYS;
                    System.out.println(StateMachine + ":");
                    break;
                case WAIT_FOR_GENERAL_CONFIGURATION_MAX_DISPLAYS:
                    if (ExpectedEventsList.isEmpty()) {
                        StateMachine = GENERAL_CONFIGURATION_MAX_CONTROLLERS;
                        System.out.println(StateMachine + ":");
                    }
                    break;
                case GENERAL_CONFIGURATION_MAX_CONTROLLERS:
                    CreateGeneralConfigurationMessages();
                    StateMachine = WAIT_FOR_GENERAL_CONFIGURATION_MAX_CONTROLLERS;
                    System.out.println(StateMachine + ":");
                    break;
                case WAIT_FOR_GENERAL_CONFIGURATION_MAX_CONTROLLERS:
                    if (ExpectedEventsList.isEmpty()) {
                        StateMachine = GENERAL_CONFIGURATION_INIT;
                        System.out.println(StateMachine + ":");
                    }
                    break;
                case WAIT_FOR_GENERAL_CONFIGURATION_FINISH:
                    if (ExpectedEventsList.isEmpty()) {
                        System.out.println("General configuration done.");
                        StateMachine = SENSORS_CONFIGURATION;
                        System.out.println(StateMachine + ":");
                    }
                    break;
                case SENSORS_CONFIGURATION:
                    CreateSensorsConfiguration();
                    System.out.println(StateMachine + ":");
                    break;
                case WAIT_FOR_AUTO_SENSOR_RESPONSE:
                    WaitForAutoSensorResponse();  // go to sleep
                    break;
                case GET_SENSORS_DATA_FOR_AUTO_BUILD:
                    // initiate data tree for auto build
                    System.out.println(StateMachine + ":");
                    InitiateDataForAutoBuild();
                    RequestAllSensorsStatus();
                    System.out.println("Requesting server for sensors data configured by auto detection.");
                    StateMachine = WAIT_FOR_SENSORS_DATA_RESPONSE;
                    System.out.println(StateMachine + ":");
                    break;
                case WAIT_FOR_SENSORS_DATA_RESPONSE:  // finish state for both manual and auto init
                    if (ExpectedEventsList.isEmpty()) {
                        System.out.println("Finished configuring sensors.");
                        Data.PrintParkingStructure(false, false, false);
                        StateMachine = GROUP_AND_DISPLAYS_CONFIGURATION;
                        System.out.println(StateMachine + ":");
                    }
                    break;
                case GROUP_AND_DISPLAYS_CONFIGURATION:
                    CreateGroupAndDisplayConfiguration();
                    StateMachine = WAIT_FOR_GROUP_AND_DISPLAYS_CONFIGURATION;
                    System.out.println(StateMachine + ":");
                    break;
                case WAIT_FOR_GROUP_AND_DISPLAYS_CONFIGURATION:
                    if (ExpectedEventsList.isEmpty()) {
                        System.out.println("Groups and displays configuration succeeded.");
                        StateMachine = OPERATION_MODE_CONFIGURATION;
                        System.out.println(StateMachine + ":");
                    }
                    break;
                case OPERATION_MODE_CONFIGURATION:
                    CreateOperationModeConfiguration();
                    StateMachine = WAIT_FOR_OPERATION_MODE_CONFIGURATION;
                    System.out.println(StateMachine + ":");
                    break;
                case WAIT_FOR_OPERATION_MODE_CONFIGURATION:
                    if (ExpectedEventsList.isEmpty()) {
                        System.out.println("Setting operation mode succeeded. Proceeding to standby.");
                        StateMachine = STANDBY;
                        System.out.println(StateMachine + ":");
                    }
                    break;
                case STANDBY:
                    // case for per-request, on-event, every T
                    // assume 0: manual by server, 1: on event, 2: every T seconds
                    switch (Data.getWorking_mode()) {
                        case 0:
                            System.out.println("On-request mode: Will ask server for status every T seconds.");
                            StateMachine = REQ_MODE_REQUEST_STATUS;
                            System.out.println(StateMachine + ":");
                            break;
                        case 1:
                            StateMachine = ON_EVENT_MODE_REQUEST_STATUS;
                            System.out.println(StateMachine + ":");
                            break;
                        case 2:
                            StateMachine = T_TIME_MODE_STANDBY;
                            System.out.println(StateMachine + ":");
                            System.out.println("Standing by, waiting for updates from all the sensors every T seconds by server.");
                            break;
                    }
                    break;
                case REQ_MODE_REQUEST_STATUS:
                    System.out.println("Requesting status of all the sensors.");
                    RequestAllSensorsStatus();
                    StateMachine = REQ_MODE_WAIT_FOR_STATUS;
                    System.out.println(StateMachine + ":");
                    break;
                case REQ_MODE_WAIT_FOR_STATUS:
                    if (ExpectedEventsList.isEmpty()) {
                        System.out.println("Received response from all the sensors.");
                        StateMachine = REQ_MODE_WAIT_FOR_TIMER;
                        System.out.println(StateMachine + ":");
                    }
                    break;
                case REQ_MODE_WAIT_FOR_TIMER:
                    System.out.println("Waiting T seconds then requesting again...");
                    WaitForTimer();
                    StateMachine = REQ_MODE_REQUEST_STATUS;
                    System.out.println(StateMachine + ":");
                    break;
                case ON_EVENT_MODE_REQUEST_STATUS:
                    System.out.println("Requesting status of all the sensors.");
                    RequestAllSensorsStatus();
                    StateMachine = ON_EVENT_MODE_WAIT_FOR_STATUS;
                    System.out.println(StateMachine + ":");
                    break;
                case ON_EVENT_MODE_WAIT_FOR_STATUS:
                    if (ExpectedEventsList.isEmpty()) {
                        System.out.println("Received response from all the sensors.");
                        StateMachine = ON_EVENT_MODE_STANDBY;
                        System.out.println(StateMachine + ":");
                        System.out.println("Standing by, waiting for on-event updates from server.");
                    }
                    break;
                case ON_EVENT_MODE_STANDBY:
                    WaitForTimer();
                    break;
                case T_TIME_MODE_STANDBY:
                    WaitForTimer();
                    break;
                case RESET:
                    Reset();
                    break;
                case WAIT_FOR_RESET:
                    if (ExpectedEventsList.isEmpty()) {
                        System.out.println("Resetting done, rebooting.");
                        StateMachine = GENERAL_CONFIGURATION;
                        System.out.println(StateMachine + ":");
                    }
                    break;
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        System.out.println("Waiting for threads to finish...");
        Threads.join();
        System.out.println("Parking Manager Service is exiting...");

    }


    private static void Reset() {
        System.out.println("Resetting ESP.");
        messages reset_msg = new messages(null, new Date(), RESET_ESP, 0);
        Threads.ListenerQueueThread.ClearQueue();
        Threads.SenderQueue.ClearQueue();
        Threads.SenderQueue.addMessage(reset_msg);
        ExpectedEventsList.add(RESET_ESP_SUCCEEDED);
        StateMachine = WAIT_FOR_RESET;
        System.out.println(StateMachine + ":");
    }

    private static void InitiateDataForAutoBuild() {
        // Overwrites old data tree!
        ParkingElement NewRootArea = new ParkingArea(new AreaId(0), StatusElement.OK, ConfigurationElement.REGULAR);
        Node<ParkingElement> NewRoot = new Node<ParkingElement>(NewRootArea);
        DataInterface NewInterface = new DataInterface(Data, NewRoot);
        Data = NewInterface;
    }


    private static void WaitForAutoSensorResponse() {
        try {

            Thread.sleep(5000);  // wait 5 sec, than check if auto setup is complete
                                   // check is being made in messageHandler
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void RequestAllSensorsStatus() {
        messages req_status_of_all_sensors_message = new messages(null, new Date(), GET_ALL_SENSORS_STATE, 0);
        Threads.SenderQueue.addMessage(req_status_of_all_sensors_message);
        ExpectedEventsList.add(GET_ALL_SENSORS_STATE_START);
        ExpectedEventsList.add(GET_ALL_SENSORS_STATE_END);
    }

    private static void WaitForTimer() {
        try {
            Thread.sleep((long) Data.getUpdate_interval() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void CreateOperationModeConfiguration() {
        // create message for operating mode
        System.out.println("Creating and sending operation mode configuration messages.");
        // assume 0: manual by server, 1: on event, 2: every T seconds
        messages workingModeMessage;
        switch (Data.getWorking_mode()) {
            case 0:
                // nothing to send
                return;
            case 1:
                workingModeMessage = new messages(null, new Date(), START_REPORT_ON_EVENT, 0);
                Threads.SenderQueue.addMessage(workingModeMessage);
                ExpectedEventsList.add(START_REPORT_ON_EVENT_SUCCEEDED);
                break;
            case 2:
            default:
                workingModeMessage = new messages(null, new Date(), START_REPORT_WITH_INTERVAL, Data.getUpdate_interval());
                Threads.SenderQueue.addMessage(workingModeMessage);
                ExpectedEventsList.add(START_REPORT_WITH_INTERVAL_SUCCEEDED);
                break;
        }
        // add to expected list
    }


    private static void CreateSensorsConfiguration() {
        if (Data.isAuto_init()) {
            System.out.println("Asking the server for sensors configuration.");
            // if init is automatic: get info from server and build tree
            messages chk_if_auto_is_finished = new messages(null, new Date(), GET_AUTO_INIT_FINISHED, 0);
            Threads.SenderQueue.addMessage(chk_if_auto_is_finished);
            //ExpectedEventsList.add(AUTO_INIT_FINISHED);
            System.out.println("Waiting for server to finish auto detection of sensors.");
            StateMachine = WAIT_FOR_AUTO_SENSOR_RESPONSE;
            // create messages for getting auto detect
            // add messages of finish auto detect to expected list
        } else {
            System.out.println("Creating and sending sensors configuration messages.");
            // create sensors messages for init
            // according to data
            List<IdElement> ParkingSensorList = Data.getParkingSensorIdList();
            // add messages to queue
            for (IdElement sensorId : ParkingSensorList) {
                messages sensorMessage = new messages(sensorId, new Date(), INIT_SENSOR, 0);
                Threads.SenderQueue.addMessage(sensorMessage);
                ExpectedEventsList.add(INIT_SENSOR_SUCCEEDED);
            }
            // add to Expected List the correct info
            StateMachine = WAIT_FOR_SENSORS_DATA_RESPONSE;
        }
    }

    private static void CreateGroupAndDisplayConfiguration() {
        // create groups messages, attach sensors to groups
        System.out.println("Creating and sending groups and displays configuration messages.");
        List<IdElement> ParkingAreaList = Data.getParkingAreaList();
        for (IdElement areaId : ParkingAreaList) {
            // create groups
            messages areaMessage = new messages(areaId, new Date(), CREATE_GROUP, 0);
            Threads.SenderQueue.addMessage(areaMessage);
            ExpectedEventsList.add(CREATE_GROUP_SUCCEEDED);

            // attach sensors to group (all sensors and sub sensors)
            List<IdElement> sensorsUnderArea = new ArrayList<IdElement>();
            Data.FindParkingSensorsIds(sensorsUnderArea, Data.getParkingElementNode(areaId));
            for (IdElement sensor : sensorsUnderArea) {
                messages attachSensorToGroupMessage = new messages(sensor, new Date(), ATTACH_SENSOR_TO_GROUP, ((AreaId)areaId).getAreaId());
                Threads.SenderQueue.addMessage(attachSensorToGroupMessage);
                ExpectedEventsList.add(ATTACH_SENSOR_TO_GROUP_SUCCEEDED);
            }

            List<IdElement> signsUnderArea = new ArrayList<IdElement>();
            Data.getAreaSigns(signsUnderArea, Data.getParkingElementNode(areaId));
            for (IdElement sign : signsUnderArea ) {
                // create displays
                messages createSign = new messages(sign, new Date(), CREATE_DISPLAY, 0);
                Threads.SenderQueue.addMessage(createSign);
                ExpectedEventsList.add(CREATE_DISPLAY_SUCCEEDED);

                // attach display to group
                messages attachSignToGroup;
                switch ((Data.getParkingElement(sign).getConfiguration())) {
                    case LEFT:
                        attachSignToGroup = new messages(sign, new Date(), ATTACH_DISPLAY_TO_GROUP_WITH_SYMBOL_LEFT, ((AreaId)areaId).getAreaId());
                        break;
                    case RIGHT:
                        attachSignToGroup = new messages(sign, new Date(), ATTACH_DISPLAY_TO_GROUP_WITH_SYMBOL_RIGHT, ((AreaId)areaId).getAreaId());
                        break;
                    case UP:
                        attachSignToGroup = new messages(sign, new Date(), ATTACH_DISPLAY_TO_GROUP_WITH_SYMBOL_UP, ((AreaId)areaId).getAreaId());
                        break;
                    case DOWN:
                    default:
                        attachSignToGroup = new messages(sign, new Date(), ATTACH_DISPLAY_TO_GROUP_WITH_SYMBOL_DOWN, ((AreaId)areaId).getAreaId());
                        break;
                    //case NO_ENTRY: -- not supported
                    //    break;
                }
                Threads.SenderQueue.addMessage(attachSignToGroup);
                ExpectedEventsList.add(ATTACH_DISPLAY_TO_GROUP_SUCCEEDED);
            }
        }

        // create displays init messages & attachment to groups
        // add everything to expected list
    }


    private static void CreateGeneralConfigurationMessages() {
        // creating messages
        // adding messages to list
        // adding messages to expected list

        switch (StateMachine) {
            case GENERAL_CONFIGURATION_INIT:
                System.out.println("Creating general configuration messages:");
                System.out.println("init, init max groups, init max displays, init max controllers.");
                messages init = new messages(null,new Date(),((Data.isAuto_init())? INIT_AUTO : INIT_MANUAL),0);
                Threads.SenderQueue.addMessage(init);
                ExpectedEventsList.add(INIT_SUCCEEDED);
                break;
            case GENERAL_CONFIGURATION_MAX_GROUPS:
                messages init_max_group = new messages(null,new Date(), INIT_MAX_GROUP_NUM, 5);
                Threads.SenderQueue.addMessage(init_max_group);
                ExpectedEventsList.add(INIT_MAX_GROUP_NUM_SUCCEEDED);
                break;
            case GENERAL_CONFIGURATION_MAX_DISPLAYS:
                messages init_max_display = new messages(null,new Date(), INIT_MAX_DISPLAY_NUM, 5);
                Threads.SenderQueue.addMessage(init_max_display);
                ExpectedEventsList.add(INIT_MAX_DISPLAY_NUM_SUCCEEDED);
                break;
            case GENERAL_CONFIGURATION_MAX_CONTROLLERS:
                messages init_max_controllers = new messages(null,new Date(), INIT_MAX_CONTROLLERS_NUM, 5);
                Threads.SenderQueue.addMessage(init_max_controllers);
                ExpectedEventsList.add(INIT_MAX_CONTROLLERS_NUM_SUCCEEDED);
                break;
        }
    }

    private static void LoadData() {

        // code for loading data

        //Data.setEsp_ip_address("192.168.4.1");
        Data.setEsp_ip_address("localhost");
        //Data.setEsp_port_number(9001);
        Data.setEsp_port_number(64810);
        Data.setWorking_mode(0);
        Data.setUpdate_interval(5);
        Data.setAuto_init(true);

/*
        try {
         FileInputStream fileIn = new FileInputStream("/ParkingManagerDatabase.ser");
         ObjectInputStream in = new ObjectInputStream(fileIn);
         Data = (DataInterface) in.readObject();
         in.close();
         fileIn.close();
      } catch (IOException i) {
         System.out.println("Can't access file /ParkingManagerDatabase.ser");
         i.printStackTrace();
         exit();
         return;
      } catch (ClassNotFoundException c) {
         System.out.println("DataInterface class not found");
         c.printStackTrace();
         exit();
         return;
      }




 */


    }


    public static void exit() {
        run = false;
        Threads.exit();
    }

}

