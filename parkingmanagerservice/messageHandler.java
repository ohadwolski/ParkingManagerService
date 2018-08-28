package parkingmanagerservice;

import java.util.Date;
import java.util.Iterator;

import static parkingmanagerservice.MessageType.*;
import static parkingmanagerservice.StateMachine.*;

public class messageHandler {
    public static void handleMessage(messages msg) {
            switch (ParkingManagerService.StateMachine) {
                case INIT:
                    break;
                case WAIT_FOR_INITIAL_CONNECTION:
                    break;
                case GENERAL_CONFIGURATION:
                    break;
                case WAIT_FOR_GENERAL_CONFIGURATION_FINISH:
                    wait_for_general_configuration_finish(msg);
                    break;
                case SENSORS_CONFIGURATION:
                    break;
                case WAIT_FOR_AUTO_SENSOR_RESPONSE:
                    wait_for_auto_sensor_response(msg);
                    break;
                case WAIT_FOR_SENSORS_CONFIGURATION:
                    break;
                case GROUP_AND_DISPLAYS_CONFIGURATION:
                    break;
                case WAIT_FOR_GROUP_AND_DISPLAYS_CONFIGURATION:
                    break;
                case OPERATION_MODE_CONFIGURATION:
                    break;
                case WAIT_FOR_OPERATION_MODE_CONFIGURATION:
                    break;
                case STANDBY:
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

    private static void wait_for_auto_sensor_response(messages msg) {
        if (msg.getType() == AUTO_INIT_FINISHED) {
            ParkingManagerService.StateMachine = GET_SENSORS_DATA_FOR_AUTO_BUILD;
        } else if (msg.getType() == AUTO_INIT_NOT_FINISHED) {
            ParkingManagerService.StateMachine = SENSORS_CONFIGURATION;
        } else if (msg.getType() == GET_AUTO_INIT_FINISHED_FAILED) {
            ParkingManagerService.StateMachine = GENERAL_CONFIGURATION;
        }
    }

    private static void wait_for_general_configuration_finish(messages msg) {
        if (msg.getType() == INIT_INITIATED || msg.getType() == INIT_WAITING || msg.getType() == INIT_SUCCEEDED
                ||  msg.getType() == INIT_MAX_GROUP_NUM_SUCCEEDED
                ||  msg.getType() == INIT_MAX_DISPLAY_NUM_SUCCEEDED
                || msg.getType() ==  INIT_MAX_CONTROLLERS_NUM_SUCCEEDED) {
            Iterator itr = ParkingManagerService.ExpectedEventsList.iterator();
            while (itr.hasNext())
            {
                if (itr.next() == msg.getType()) {
                    itr.remove();
                    break;
                }
            }
        }
    }


}
