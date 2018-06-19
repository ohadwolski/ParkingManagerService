package parkingmanagerservice;

public enum MessageType {
    ECHO, ACK_ECHO,                                                                                          //Connection check
    PARKING_SPOT_TAKEN, PARKING_SPOT_FREED,                                                                  //Notify parking spot taken and freed
    SET_PARKING_SPOT_LED_OFF, SET_PARKING_SPOT_LED_RED, SET_PARKING_SPOT_LED_GREEN, SET_PARKING_SPOT_LED_BLUE,   //Set led color
    SET_UPDATE_COUNTER,                                                                                      //Server set the update counter to new value
    SET_SIGN_LEFT, SET_SIGN_RIGHT, SET_SIGN_FORWARD, SET_SIGN_BACK, SET_SIGN_NO_ENTRY,                      //Server set the sign post picture
	
	CURRENT_UPDATE_COUNTER,                                                                                 //Current status of counter value
	CURRENT_SIGN_LEFT, CURRENT_SIGN_RIGHT, CURRENT_SIGN_FORWARD, CURRENT_SIGN_BACK, CURRENT_SIGN_NO_ENTRY; //Current sign post picture

}
