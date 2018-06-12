package parkingmanagerservice;

public enum MessageType {
    ECHO, ACK_ECHO,                                                                                         // Connection check
    PARKING_SPOT_TAKEN, PARKING_SPOT_FREED,                                                                 // Notify parking spot taken and freed
    SET_PAKING_SPOT_LED_OFF, SET_PAKING_SPOT_LED_RED, SET_PAKING_SPOT_LED_GREEN, SET_PAKING_SPOT_LED_BLUE,  // Set led color
    UPDATE_COUNTER,                                                                                         // Update counter to new value
    SET_SIGN_LEFT, SET_SIGN_RIGHT, SET_SIGN_FORWARD, SET_SIGN_BACK, SET_SIGN_NO_ENTRY;                      // Set sign post picture
}
