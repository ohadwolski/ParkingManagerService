package parkingmanagerservice;

public enum MessageType {
    ECHO, ACK_ECHO,                                                                                          //Connection check
    SET_PARKING_SPOT_LED_OFF, SET_PARKING_SPOT_LED_RED, SET_PARKING_SPOT_LED_GREEN, SET_PARKING_SPOT_LED_BLUE,   //Set led color
    SET_UPDATE_COUNTER,                                                                                      //Server set the update counter to new value
    SET_SIGN_LEFT, SET_SIGN_RIGHT, SET_SIGN_FORWARD, SET_SIGN_BACK, SET_SIGN_NO_ENTRY,                      //Server set the sign post picture

	CURRENT_UPDATE_COUNTER,                                                                                 //Current status of counter value
	CURRENT_SIGN_LEFT, CURRENT_SIGN_RIGHT, CURRENT_SIGN_FORWARD, CURRENT_SIGN_BACK, CURRENT_SIGN_NO_ENTRY, //Current sign post picture




    INIT_AUTO, INIT_MANUAL,
    INIT_INITIATED, INIT_WAITING, INIT_SUCCEEDED,

    GET_AUTO_INIT_FINISHED,
    GET_AUTO_INIT_FINISHED_FAILED,
    AUTO_INIT_NOT_FINISHED,
    AUTO_INIT_FINISHED,

    INIT_MAX_GROUP_NUM,
    INIT_MAX_GROUP_NUM_SUCCEEDED,
    INIT_MAX_GROUP_NUM_FAILED,

    INIT_MAX_DISPLAY_NUM,
    INIT_MAX_DISPLAY_NUM_SUCCEEDED,
    INIT_MAX_DISPLAY_NUM_FAILED,

    INIT_MAX_CONTROLLERS_NUM,
    INIT_MAX_CONTROLLERS_NUM_SUCCEEDED,
    INIT_MAX_CONTROLLERS_NUM_FAILED,

    INIT_SENSOR,
    INIT_SENSOR_SUCCEEDED,
    INIT_SENSOR_FAILED,

    PARKING_SPOT_TAKEN, PARKING_SPOT_FREED, PARKING_SPOT_ERROR,                                                                  //Notify parking spot taken and freed
    GET_SENSOR_STATE,
    GET_SENSOR_STATE_FAILED,

    GET_ALL_SENSORS_STATE,
    GET_ALL_SENSORS_STATE_START,
    GET_ALL_SENSORS_STATE_END,

    GET_GROUP_SENSORS_STATE,
    GET_GROUP_SENSORS_STATE_START,
    GET_GROUP_SENSORS_STATE_END,
    GET_GROUP_SENSORS_STATE_FAILED,
    GET_GROUP_SENSORS_STATE_FREE_SPOTS,

    CREATE_GROUP,
    CREATE_GROUP_SUCCEEDED,
    CREATE_GROUP_FAILED,

    ATTACH_SENSOR_TO_GROUP,
    ATTACH_SENSOR_TO_GROUP_SUCCEEDED,
    ATTACH_SENSOR_TO_GROUP_FAILED,

    CREATE_DISPLAY,
    CREATE_DISPLAY_SUCCEEDED,
    CREATE_DISPLAY_FAILED,

    ATTACH_DISPLAY_TO_GROUP,
    ATTACH_DISPLAY_TO_GROUP_SUCCEEDED,
    ATTACH_DISPLAY_TO_GROUP_FAILED,

    CHANGE_DISPLAY_SYMBOL_UP,
    CHANGE_DISPLAY_SYMBOL_DOWN,
    CHANGE_DISPLAY_SYMBOL_LEFT,
    CHANGE_DISPLAY_SYMBOL_RIGHT,
    CHANGE_DISPLAY_SYMBOL_FAILED,
    CHANGE_DISPLAY_SYMBOL_SUCCEEDED,

    CHANGE_DISPLAY_SYMBOL_UP_MANUAL,
    CHANGE_DISPLAY_SYMBOL_DOWN_MANUAL,
    CHANGE_DISPLAY_SYMBOL_LEFT_MANUAL,
    CHANGE_DISPLAY_SYMBOL_RIGHT_MANUAL,
    CHANGE_DISPLAY_SYMBOL_MANUAL_FAILED,
    CHANGE_DISPLAY_SYMBOL_MANUAL_SUCCEEDED,

    CHANGE_DISPLAY_TO_MANUAL,
    CHANGE_DISPLAY_TO_AUTO,
    CHANGE_DISPLAY_SUCCEEDED,
    CHANGE_DISPLAY_FAILED,

    START_REPORT_WITH_INTERVAL,
    START_REPORT_WITH_INTERVAL_SUCCEEDED,
    START_REPORT_WITH_INTERVAL_FAILED,

    STOP_REPORT_WITH_INTERVAL,
    STOP_REPORT_WITH_INTERVAL_SUCCEEDED,
    STOP_REPORT_WITH_INTERVAL_FAILED,

    START_REPORT_ON_EVENT,
    START_REPORT_ON_EVENT_SUCCEEDED,
    START_REPORT_ON_EVENT_FAILED,

    STOP_REPORT_ON_EVENT,
    STOP_REPORT_ON_EVENT_SUCCEEDED,
    STOP_REPORT_ON_EVENT_FAILED,

    GET_ERROR_REPORT,
    GET_ERROR_REPORT_START,
    GET_ERROR_REPORT_END,
    GET_ERROR_REPORT_FAILED,

    RESET_ESP,
    RESET_ESP_SUCCEEDED,

    UNKNOWN_COMMAND,

    ;
}
