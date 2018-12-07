package parkingmanagerservice;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static parkingmanagerservice.MessageType.*;
import static parkingmanagerservice.StateMachine.*;

public class messageHandler {
    public static void handleMessage(messages msg) {
        msg.print();
        //ParkingManagerService.PushOneMessage = true;
            switch (ParkingManagerService.StateMachine) {
                case INIT:
                    NotApplicable(msg);
                    break;
                case WAIT_FOR_INITIAL_CONNECTION:
                    NotApplicable(msg);
                    break;
                case GENERAL_CONFIGURATION:
                    NotApplicable(msg);
                    break;
                case GENERAL_CONFIGURATION_INIT:
                case GENERAL_CONFIGURATION_MAX_GROUPS:
                case GENERAL_CONFIGURATION_MAX_DISPLAYS:
                case GENERAL_CONFIGURATION_MAX_CONTROLLERS:
                    NotApplicable(msg);
                    break;
                case WAIT_FOR_GENERAL_CONFIGURATION_INIT:
                case WAIT_FOR_GENERAL_CONFIGURATION_MAX_GROUPS:
                case WAIT_FOR_GENERAL_CONFIGURATION_MAX_DISPLAYS:
                case WAIT_FOR_GENERAL_CONFIGURATION_MAX_CONTROLLERS:
                case WAIT_FOR_GENERAL_CONFIGURATION_FINISH:
                    WaitForGeneralConfigurationFinish(msg);
                    break;
                case SENSORS_CONFIGURATION:
                    NotApplicable(msg);
                    break;
                case WAIT_FOR_AUTO_SENSOR_RESPONSE:
                    WaitForAutoSensorResponse(msg);
                    break;
                case GET_SENSORS_DATA_FOR_AUTO_BUILD:
                    NotApplicable(msg);
                    break;
                case WAIT_FOR_SENSORS_DATA_RESPONSE:
                    WaitForSensorsDataResponse(msg);
                    break;
                case GROUP_AND_DISPLAYS_CONFIGURATION:
                    NotApplicable(msg);
                    break;
                case WAIT_FOR_GROUP_AND_DISPLAYS_CONFIGURATION:
                    WaitForGroupAndDisplaysConfiguration(msg);
                    break;
                case OPERATION_MODE_CONFIGURATION:
                    NotApplicable(msg);
                    break;
                case WAIT_FOR_OPERATION_MODE_CONFIGURATION:
                    WaitForOperationModeConfiguration(msg);
                    break;
                case STANDBY:
                    NotApplicable(msg);
                    break;
                case REQ_MODE_WAIT_FOR_TIMER:
                    NotApplicable(msg);
                    break;
                case REQ_MODE_REQUEST_STATUS:
                    NotApplicable(msg);
                    break;
                case REQ_MODE_WAIT_FOR_STATUS:
                    UpdateDataAccordingToMessage(msg);
                    break;
                case ON_EVENT_MODE_REQUEST_STATUS:
                    NotApplicable(msg);
                    break;
                case ON_EVENT_MODE_WAIT_FOR_STATUS:
                    UpdateDataAccordingToMessage(msg);
                    break;
                case ON_EVENT_MODE_STANDBY:
                    UpdateDataAccordingToMessage(msg);
                    break;
                case T_TIME_MODE_STANDBY:
                    UpdateDataAccordingToMessage(msg);
                    break;
                case RESET:
                    NotApplicable(msg);
                    break;
                case WAIT_FOR_RESET:
                    Iterator itr = ParkingManagerService.ExpectedEventsList.iterator();
                    while (itr.hasNext())
                    {
                        if (itr.next() == msg.getType()) {
                            itr.remove();
                            break;
                        }
                    }
            }


        /*

        System.out.println("Handling message :\n");
        if (msg == null) {
            System.out.println("Message is null :\n");
            return;
        }
        msg.print();
        
        MessageType messageType = msg.getType();
        switch (messageType) {
        	//case ECHO: 	
        	//case ACK_ECHO:
        		
        	case PARKING_SPOT_TAKEN:
                Date parkingSpotTaken = new Date();
                if (ParkingManagerService.parkingLot.getStatusElement(msg.getId()) == TypeData.TAKEN) {
                    ParkingManagerService.parkingLot.setStatusElementLastResponse(msg.getId(), parkingSpotTaken);
                    System.out.println(msg.getId() + " Last Response was updated");
                }
                else {
                    ParkingManagerService.parkingLot.setStatusElement(msg.getId(), TypeData.TAKEN);
                    System.out.println("Parking spot " + msg.getId() + " was taken");
                    ParkingManagerService.parkingLot.changeSignCounters(msg.getId(), 1);
                    System.out.println("Counters were updated");
                    ParkingManagerService.parkingLot.setStatusElementLastResponse(msg.getId(), parkingSpotTaken);
                    System.out.println(msg.getId() + " Last Response was updated");
                    messages changeSpotLedToRed = new messages(msg.getId(),parkingSpotTaken, MessageType.SET_PARKING_SPOT_LED_RED,0);
                    ParkingManagerService.Threads.SenderQueueThread.addMessage(changeSpotLedToRed);
                    //message to update counters are handled inside the change counter function in ParkingLotData
                }
                break;
	        								
        	case PARKING_SPOT_FREED:
                Date parkingSpotFreed = new Date();
                if (ParkingManagerService.parkingLot.getStatusElement(msg.getId()) == TypeData.FREE) {
                    ParkingManagerService.parkingLot.setStatusElementLastResponse(msg.getId(), parkingSpotFreed);
                    System.out.println(msg.getId() + " Last Response was updated");
                }
                else {
                    ParkingManagerService.parkingLot.setStatusElement(msg.getId(), TypeData.FREE);
                    System.out.println("Parking spot " + msg.getId() + " was freed");
                    ParkingManagerService.parkingLot.changeSignCounters(msg.getId(), 0);
                    System.out.println("Counters were updated");
                    ParkingManagerService.parkingLot.setStatusElementLastResponse(msg.getId(), parkingSpotFreed);
                    System.out.println(msg.getId() + " Last Response was updated");
                    messages changeSpotLedToGreen = new messages(msg.getId(),parkingSpotFreed, MessageType.SET_PARKING_SPOT_LED_GREEN,0);
                    ParkingManagerService.Threads.SenderQueueThread.addMessage(changeSpotLedToGreen);
                    //message to update counters are handled inside the change counter function in ParkingLotData
                }
                break;
        	//case SET_PARKING_SPOT_LED_OFF:  Do nothing
        		
        		
        	//case SET_PARKING_SPOT_LED_RED:  Do nothing
        		
        		
        	//case SET_PARKING_SPOT_LED_GREEN:Do nothing
        		
        		
        	//case SET_PARKING_SPOT_LED_BLUE: Do nothing
        		
        	
        	case CURRENT_UPDATE_COUNTER:
        	case CURRENT_SIGN_LEFT:
        	case CURRENT_SIGN_RIGHT:
        	case CURRENT_SIGN_FORWARD:
        	case CURRENT_SIGN_BACK:
        	case CURRENT_SIGN_NO_ENTRY:
                ParkingManagerService.parkingLot.setStatusElement(msg.getId(), TypeData.OK);
                System.out.println(msg.getId() + " status was updated");
                Date updateCounterDate = new Date();
                ParkingManagerService.parkingLot.setStatusElementLastResponse(msg.getId(), updateCounterDate);
                System.out.println(msg.getId() + " last response was updated");
                break;
            default:
                System.out.println("Decides to do nothing.\n");
                break;

										
        	//case SET_UPDATE_COUNTER:    Do nothing
        	
        	//case SET_SIGN_LEFT:         Do nothing  
										
        	//case SET_SIGN_RIGHT:        Do nothing
        	
        	//case SET_SIGN_FORWARD:      Do nothing
        	
        	//case SET_SIGN_BACK:         Do nothing
        	
        	//case SET_SIGN_NO_ENTRY:     Do nothing


        }
        
*/
    }

    private static void NotApplicable(messages msg) {
        System.out.println("Message received (" + msg.getType() + ") do not apply to current state: " + ParkingManagerService.StateMachine + ". Ignoring.");
    }

    private static void UpdateDataAccordingToMessage(messages msg) {
        if      (  msg.getType() == GET_ALL_SENSORS_STATE_START
                || msg.getType() == GET_ALL_SENSORS_STATE_END) {
            Iterator itr = ParkingManagerService.ExpectedEventsList.iterator();
            while (itr.hasNext())
            {
                if (itr.next() == msg.getType()) {
                    itr.remove();
                    break;
                }
            }
        } else if (msg.getType() == GET_SENSOR_STATE_FAILED) {  // in case of error try again
            System.out.println("Received error while trying to request for sensors status. Trying again.");
            if (ParkingManagerService.StateMachine == REQ_MODE_WAIT_FOR_STATUS) {
                ParkingManagerService.ExpectedEventsList.clear();
                ParkingManagerService.StateMachine = REQ_MODE_REQUEST_STATUS;
            } else {
                ParkingManagerService.ExpectedEventsList.clear();
                ParkingManagerService.StateMachine = ON_EVENT_MODE_REQUEST_STATUS;
            }
        } else if (msg.getType() == PARKING_SPOT_FREED || msg.getType() == PARKING_SPOT_TAKEN || msg.getType() == PARKING_SPOT_ERROR) {
            // find parking element
            ParkingElement SensorToUpdate = ParkingManagerService.Data.getParkingElement(msg.getId());
            // change to new status
            StatusElement prevStatus = SensorToUpdate.getStatus();
            SensorToUpdate.setStatus(msg.getType() == PARKING_SPOT_FREED ? StatusElement.FREE : msg.getType() == PARKING_SPOT_TAKEN ? StatusElement.TAKEN : StatusElement.ERROR);
            SensorToUpdate.setTimeStamp(new Date());

            System.out.printf("Updating status of sensor: ");
            SensorToUpdate.getId().print();
            System.out.printf(" to " + SensorToUpdate.getStatus() + "%n");

            // find all relevant signs
            List<ParkingElement> SignsToUpdate = ParkingManagerService.Data.getSignsForParkingElement(SensorToUpdate);
            // change counters of signs
            for (ParkingElement sign : SignsToUpdate) {
                if (sign instanceof ParkingSign) {
                    int n = ((ParkingSign) sign).getCounter();
                    if (msg.getType() == PARKING_SPOT_FREED) {
                        if (prevStatus == StatusElement.TAKEN) {
                            n++;
                        } else if (prevStatus == StatusElement.ERROR){
                            n++;
                        }
                    } else if (msg.getType() == PARKING_SPOT_TAKEN) {
                        if (prevStatus == StatusElement.FREE) {
                            n--;
                        }
                    } else {  // changed to error state
                        if (prevStatus == StatusElement.FREE) {
                            n--;
                        }
                    }
                    ((ParkingSign) sign).setCounter(n);
                    sign.setTimeStamp(new Date());

                    System.out.printf("Updating counter of sign: ");
                    sign.getId().print();
                    System.out.printf(" to " + n + "%n");
                }
            }
            // assumption: all signs are initialized at esp as auto-update and
            //             do not need a message to update the actual counter
            //             Our program doesn't support manual change of counters
        }
    }


    private static void WaitForOperationModeConfiguration(messages msg) {
        if      (  msg.getType() == START_REPORT_ON_EVENT_SUCCEEDED
                || msg.getType() == START_REPORT_WITH_INTERVAL_SUCCEEDED) {
            Iterator itr = ParkingManagerService.ExpectedEventsList.iterator();
            while (itr.hasNext()) {
                if (itr.next() == msg.getType()) {
                    itr.remove();
                    break;
                }
            }
        }
    }

    private static void WaitForGroupAndDisplaysConfiguration(messages msg) {
        // TODO: ERRORS THAT CAN HAPPEN? ERROR MESSAGES THAT CAN RETURN?
        //       Can stuck here. Maybe add a timeout mechanism in WatchDog
        if      (  msg.getType() == CREATE_GROUP_SUCCEEDED
                || msg.getType() == ATTACH_SENSOR_TO_GROUP_SUCCEEDED
                || msg.getType() == CREATE_DISPLAY_SUCCEEDED
                || msg.getType() == ATTACH_DISPLAY_TO_GROUP_SUCCEEDED) {
            Iterator itr = ParkingManagerService.ExpectedEventsList.iterator();
            while (itr.hasNext()) {
                if (itr.next() == msg.getType()) {
                    itr.remove();
                    break;
                }
            }
        }
    }

    private static void WaitForSensorsDataResponse(messages msg) {
        if      (  msg.getType() == GET_ALL_SENSORS_STATE_START
                || msg.getType() == GET_ALL_SENSORS_STATE_END) {
            Iterator itr = ParkingManagerService.ExpectedEventsList.iterator();
            while (itr.hasNext())
            {
                if (itr.next() == msg.getType()) {
                    itr.remove();
                    break;
                }
            }
        } else if (msg.getType() == GET_SENSOR_STATE_FAILED) {  // in case of error try again
            System.out.println("Server error while trying to get sensors data from auto detection. Trying again.");
            ParkingManagerService.ExpectedEventsList.clear();
            ParkingManagerService.StateMachine = GET_SENSORS_DATA_FOR_AUTO_BUILD;
        } else if (msg.getType() == PARKING_SPOT_FREED || msg.getType() == PARKING_SPOT_TAKEN || msg.getType() == PARKING_SPOT_ERROR) {
            // add msg to list and build parking spot!

            SensorId NewSensorId = new SensorId(msg.getId());
            StatusElement NewSensorStatus = (msg.getType() == PARKING_SPOT_FREED) ? StatusElement.FREE : (msg.getType() == PARKING_SPOT_TAKEN) ? StatusElement.TAKEN : StatusElement.ERROR;
            ParkingElement NewSensor = new ParkingSensor(NewSensorId, NewSensorStatus, ConfigurationElement.REGULAR);
            Node<ParkingElement> NewSensorNode = new Node<ParkingElement>(NewSensor);

            Node<ParkingElement> rootArea = ParkingManagerService.Data.getParkingElementNode(new AreaId(0));
            rootArea.addChild(NewSensorNode);
            NewSensorNode.setParent(rootArea);
        }
    }

    private static void WaitForAutoSensorResponse(messages msg) {
        if (msg.getType() == AUTO_INIT_FINISHED) {
            System.out.println("Server finished auto detection of sensors.");
            ParkingManagerService.StateMachine = GET_SENSORS_DATA_FOR_AUTO_BUILD;
        } else if (msg.getType() == AUTO_INIT_NOT_FINISHED) {
            System.out.println("Server has not finished auto detection of sensors yet.");
            ParkingManagerService.StateMachine = SENSORS_CONFIGURATION;
        } else if (msg.getType() == GET_AUTO_INIT_FINISHED_FAILED) {
            System.out.println("Server failed auto detection of sensors. Trying again.");
            ParkingManagerService.StateMachine = GENERAL_CONFIGURATION;
        }
    }

    private static void WaitForGeneralConfigurationFinish(messages msg) {
        if      (  msg.getType() == INIT_INITIATED
                || msg.getType() == INIT_WAITING
                || msg.getType() == INIT_SUCCEEDED
                || msg.getType() == INIT_MAX_GROUP_NUM_SUCCEEDED
                || msg.getType() == INIT_MAX_DISPLAY_NUM_SUCCEEDED
                || msg.getType() == INIT_MAX_CONTROLLERS_NUM_SUCCEEDED) {
            Iterator itr = ParkingManagerService.ExpectedEventsList.iterator();
            while (itr.hasNext())
            {
                if (itr.next() == msg.getType()) {
                    System.out.println("Received expected response: " + msg.getType());
                    itr.remove();
                    break;
                }
            }
        }
    }


}
