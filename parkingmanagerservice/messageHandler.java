package parkingmanagerservice;

import java.util.Date;

public class messageHandler {
    public static void handleMessage(messages msg) {
        System.out.println("Handling message :\n");
        if (msg == null) {
            System.out.println("Message is null :\n");
            return;
        }
        msg.print();
        
        MessageType messageType = msg.getType();
        System.out.println("ECHO");
        System.out.println("ECHO_ACK");
        switch (messageType) {
        
        	//case ECHO: 	
        	//case ACK_ECHO:
        		
        	case PARKING_SPOT_TAKEN:  	Date parkingSpotTaken = new Date();
        								if (ParkingManagerService.parkingLot.getStatusElement(msg.getId()) == TypeData.TAKEN) {
        									ParkingManagerService.parkingLot.setStatusElementLastResponse(msg.getId(), parkingSpotTaken);
        									System.out.println(msg.getId() + "Last Response was updated");
        								}
        								else {
	        								ParkingManagerService.parkingLot.setStatusElement(msg.getId(), TypeData.TAKEN);
	        								System.out.println("Parking spot " + msg.getId() + "was taken");
	        								ParkingManagerService.parkingLot.changeSignCounters(msg.getId(), 1);
	        								System.out.println("Counters were updated");
	        								ParkingManagerService.parkingLot.setStatusElementLastResponse(msg.getId(), parkingSpotTaken);
	        								System.out.println(msg.getId() + "Last Response was updated");
	        								messages changeSpotLedToRed = new messages(msg.getId(),parkingSpotTaken, MessageType.SET_PAKING_SPOT_LED_RED,0);
	        								ParkingManagerService.Threads.SenderQueueThread.addMessage(changeSpotLedToRed);
	        								//message to update counters are handled inside the change counter function in ParkingLotData
        								}
	        								
        	case PARKING_SPOT_FREED:	Date parkingSpotFreed = new Date();
        								if (ParkingManagerService.parkingLot.getStatusElement(msg.getId()) == TypeData.FREE) {
        									ParkingManagerService.parkingLot.setStatusElementLastResponse(msg.getId(), parkingSpotFreed);
        									System.out.println(msg.getId() + "Last Response was updated");
        								}
        								else {
	        								ParkingManagerService.parkingLot.setStatusElement(msg.getId(), TypeData.FREE);
	        								System.out.println("Parking spot " + msg.getId() + "was freed");
											ParkingManagerService.parkingLot.changeSignCounters(msg.getId(), 0);
											System.out.println("Counters were updated");
											ParkingManagerService.parkingLot.setStatusElementLastResponse(msg.getId(), parkingSpotFreed);
											System.out.println(msg.getId() + "Last Response was updated");
		        							messages changeSpotLedToGreen = new messages(msg.getId(),parkingSpotFreed, MessageType.SET_PAKING_SPOT_LED_GREEN,0);
		        							ParkingManagerService.Threads.SenderQueueThread.addMessage(changeSpotLedToGreen);
		        							//message to update counters are handled inside the change counter function in ParkingLotData
        								}	
        	//case SET_PAKING_SPOT_LED_OFF:  Do nothing
        		
        		
        	//case SET_PAKING_SPOT_LED_RED:  Do nothing
        		
        		
        	//case SET_PAKING_SPOT_LED_GREEN:Do nothing
        		
        		
        	//case SET_PAKING_SPOT_LED_BLUE: Do nothing
        		
        	
        	case CURRENT_UPDATE_COUNTER:ParkingManagerService.parkingLot.setStatusElement(msg.getId(), TypeData.OK);
        								System.out.println(msg.getId() + "status was updated");
        								Date updateCounterDate = new Date();
        							    ParkingManagerService.parkingLot.setStatusElementLastResponse(msg.getId(), updateCounterDate);
        							    System.out.println(msg.getId() + "last response was updated");
        		
        	case CURRENT_SIGN_LEFT:		ParkingManagerService.parkingLot.setStatusElement(msg.getId(), TypeData.OK);
        								System.out.println(msg.getId() + "status was updated");
        								Date signLeftDate = new Date();
        								ParkingManagerService.parkingLot.setStatusElementLastResponse(msg.getId(), signLeftDate);	
        								System.out.println(msg.getId() + "last response was updated");
        	
        	case CURRENT_SIGN_RIGHT:	ParkingManagerService.parkingLot.setStatusElement(msg.getId(), TypeData.OK);
        								System.out.println(msg.getId() + "status was updated");
										Date currentSignRight = new Date();
										ParkingManagerService.parkingLot.setStatusElementLastResponse(msg.getId(), currentSignRight);	
										System.out.println(msg.getId() + "last response was updated");
        	
        	case CURRENT_SIGN_FORWARD:	ParkingManagerService.parkingLot.setStatusElement(msg.getId(), TypeData.OK);
        								System.out.println(msg.getId() + "status was updated");
										Date currentSignForward = new Date();
										ParkingManagerService.parkingLot.setStatusElementLastResponse(msg.getId(), currentSignForward);	
										System.out.println(msg.getId() + "last response was updated");
        		
        	case CURRENT_SIGN_BACK:		ParkingManagerService.parkingLot.setStatusElement(msg.getId(), TypeData.OK);
								        System.out.println(msg.getId() + "status was updated");
								        Date CURRENT_SIGN_BACK = new Date();
										ParkingManagerService.parkingLot.setStatusElementLastResponse(msg.getId(), CURRENT_SIGN_BACK);	
										System.out.println(msg.getId() + "last response was updated");
        		
        	case CURRENT_SIGN_NO_ENTRY:	ParkingManagerService.parkingLot.setStatusElement(msg.getId(), TypeData.OK);
        								System.out.println(msg.getId() + "status was updated");
										Date currentSignNoEntry = new Date();
										ParkingManagerService.parkingLot.setStatusElementLastResponse(msg.getId(), currentSignNoEntry);	
										System.out.println(msg.getId() + "last response was updated");
										
        	//case SET_UPDATE_COUNTER:    Do nothing
        	
        	//case SET_SIGN_LEFT:         Do nothing  
										
        	//case SET_SIGN_RIGHT:        Do nothing
        	
        	//case SET_SIGN_FORWARD:      Do nothing
        	
        	//case SET_SIGN_BACK:         Do nothing
        	
        	//case SET_SIGN_NO_ENTRY:     Do nothing
        }
        
        System.out.println("Decides to do nothing.\n");
    }
}
