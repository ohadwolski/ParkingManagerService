package parkingmanagerservice;

import java.util.Date;

import static parkingmanagerservice.MessageType.*;

@SuppressWarnings("Duplicates")
public class MessageConverter {
    public static messages convertStringToMessage(String msg) {
        messages msg_in_messages_format;

        String[] tokens = msg.split(" ");
        msg_in_messages_format = createMessageFormat(tokens);

        return  msg_in_messages_format;
    }

    public static String convertMessageToString(messages msg) {
        String msg_in_string_format = createStringFormat(msg);
        return  msg_in_string_format;
    }


    private static messages createMessageFormat(String[] s) {
        switch (s[0]) {

            // initiate
            case "0":
                switch (s[1]) {
                    // already initiated
                    case "0"://\n":
                        return new messages(null, new Date(), INIT_INITIATED, 0);
                    // waiting for init settings
                    case "1"://\n":
                        return new messages(null, new Date(), INIT_WAITING, 0);
                    // init succeeded
                    case "2"://\n":
                        return new messages(null, new Date(), INIT_SUCCEEDED, 0);
                    default:
                        break;
                }
                break;
            case "1":
                // maxNumOfGroups
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), INIT_MAX_GROUP_NUM_SUCCEEDED, 0);
                    case "2"://\n":
                        return new messages(null, new Date(), INIT_MAX_GROUP_NUM_FAILED, 0);
                    default:
                        break;
                }
                break;
            case "2":
                // maxNumOfDisplays
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), INIT_MAX_DISPLAY_NUM_SUCCEEDED, 0);
                    case "2"://\n":
                        return new messages(null, new Date(), INIT_MAX_DISPLAY_NUM_FAILED, 0);
                    default:
                        break;
                }
                break;
            case "3":
                // maxNumOfControllers
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), INIT_MAX_CONTROLLERS_NUM_SUCCEEDED, 0);
                    case "2"://\n":
                        return new messages(null, new Date(), INIT_MAX_CONTROLLERS_NUM_FAILED, 0);
                    default:
                        break;
                }
                break;
            case "4":
                // init parking sensor
                switch (s[1]) {
                    case "0"://\n":
                    case "2"://\n":
                        return new messages(null, new Date(), INIT_SENSOR_FAILED, 0);
                    case "1"://\n":
                        return new messages(null, new Date(), INIT_SENSOR_SUCCEEDED, 0);
                    default:
                        break;
                }
                break;
            //case "5":
                // deleting parking sensor
            //
            //case "6":
            //    changing zone controller of parking sensor
            //
            //case "7":
            //    change port of sensor
            //
            //case "8":
            //    change id of sensor
            //
            case "9":
                // report on sensor
                // could have problems here if format is incorrect
                switch (s[1]) {
                    case "0"://\n":
                    case "2"://\n":
                    case "3"://\n":
                        return new messages(null, new Date(), GET_SENSOR_STATE_FAILED, 0);
                    default:
                        if (s.length < 5) break;
                        MessageType type = (s[4].equals("0") ? PARKING_SPOT_FREED : (s[4].equals("1")) ? PARKING_SPOT_TAKEN : PARKING_SPOT_ERROR);
                        return new messages(new SensorId(Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3])), new Date(), type, 0);
                }
            case "10":
                // get state of all the sensors
                switch (s[1]) {
                case "0"://\n":
                    return new messages(null, new Date(), GET_SENSOR_STATE_FAILED, 0);
                case "1"://\n":
                    return new messages(null, new Date(), GET_ALL_SENSORS_STATE_START, 0);
                case "2"://\n":
                    return new messages(null, new Date(), GET_ALL_SENSORS_STATE_END, 0);
                default:
                    break;
                }
                break;
            case "11":
                // create new group
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), CREATE_GROUP_SUCCEEDED, 0);
                    case "0"://\n":
                    case "2"://\n":
                    case "3"://\n":
                    case "4"://\n":
                        return new messages(null, new Date(), CREATE_GROUP_FAILED, 0);
                    default:
                        break;
                }
                break;
            //case "12":
            //    deleting a group
            //
            //case "13":
            //    change id of group
            //
            case "14":
                // attach a sensor to group
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), ATTACH_SENSOR_TO_GROUP_SUCCEEDED, 0);
                    case "0"://\n":
                    case "2"://\n":
                    case "3"://\n":
                    case "4"://\n":
                    case "5"://\n":
                        return new messages(null, new Date(), ATTACH_SENSOR_TO_GROUP_FAILED, 0);
                    default:
                        break;
                }
                break;
            //case "15":
            //    cancel attachment of sensor to group
            //
            case "16":
                // report on a group of sensors
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), GET_GROUP_SENSORS_STATE_START, 0);
                    case "2"://\n":
                        return new messages(null, new Date(), GET_GROUP_SENSORS_STATE_END, 0);
                    case "3":
                        // this message will be discarded - no use
                        return new messages(null, new Date(), GET_GROUP_SENSORS_STATE_FREE_SPOTS, 0);
                    case "0"://\n":
                    case "4"://\n":
                    case "5"://\n":
                        return new messages(null, new Date(), GET_GROUP_SENSORS_STATE_FAILED, 0);
                    default:
                        break;
                }
                break;
            case "17":
                // create new display
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), CREATE_DISPLAY_SUCCEEDED, 0);
                    case "0"://\n":
                    case "2"://\n":
                    case "3"://\n":
                    case "4"://\n":
                        return new messages(null, new Date(), CREATE_DISPLAY_FAILED, 0);
                    default:
                        break;
                }
                break;
            //case "18":
            //    delete display
            //
            //case "19":
            //    change id of display
            //
            case "20":
                // Attach group to sub-display in a display
                // not formatted correctly right now
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), ATTACH_DISPLAY_TO_GROUP_SUCCEEDED, 0);
                    case "0"://\n":
                    case "2"://\n":
                    case "3"://\n":
                    case "4"://\n":
                    case "5"://\n":
                        return new messages(null, new Date(), ATTACH_DISPLAY_TO_GROUP_FAILED, 0);
                    default:
                        break;
                }
                break;
            //case "21":
            //    cancel attachment of group to display
            //
            //case "22":
                // change symbol on display
                // not formatted correctly
            //
            //case "23":
                // report on state of display
                //
            //
            case "24":
                // report on all of the sensor every T time
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), START_REPORT_WITH_INTERVAL_SUCCEEDED, 0);
                    case "0"://\n":
                    case "2"://\n":
                        return new messages(null, new Date(), START_REPORT_WITH_INTERVAL_FAILED, 0);
                    default:
                        break;
                }
                break;
            case "25":
                // stop reporting every T time
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), STOP_REPORT_WITH_INTERVAL_SUCCEEDED, 0);
                    case "0"://\n":
                        return new messages(null, new Date(), STOP_REPORT_WITH_INTERVAL_FAILED, 0);
                    default:
                        break;
                }
                break;
            case "26":
                // report on event
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), START_REPORT_ON_EVENT_SUCCEEDED, 0);
                    case "0\n":
                        return new messages(null, new Date(), START_REPORT_ON_EVENT_FAILED, 0);
                    default:
                        break;
                }
                break;
            case "27":
                // stop report on event
                switch (s[1]) {
                    case "1\n":
                        return new messages(null, new Date(), STOP_REPORT_ON_EVENT_SUCCEEDED, 0);
                    case "0"://\n":
                        return new messages(null, new Date(), STOP_REPORT_ON_EVENT_FAILED, 0);
                    default:
                        break;
                }
                break;
            case "28":
                // error reporting
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), GET_ERROR_REPORT_START, 0);
                    case "2"://\n":
                        return new messages(null, new Date(), GET_ERROR_REPORT_END, 0);
                    case "0"://\n":
                        return new messages(null, new Date(), GET_ERROR_REPORT_FAILED, 0);
                    default:
                        break;
                }
                break;
            case "29":
                // reset esp
                switch (s[1]) {
                    case "0"://\n":
                        return new messages(null, new Date(), RESET_ESP_SUCCEEDED, 0);
                    default:
                        break;
                }
                break;
            case "30"://\n":
                // unknown command
                return new messages(null, new Date(), UNKNOWN_COMMAND, 0);
            case "31":
                // get auto init finished
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), AUTO_INIT_NOT_FINISHED, 0);
                    case "2"://\n":
                        return new messages(null, new Date(), AUTO_INIT_FINISHED, 0);
                    case "0"://\n":
                        return new messages(null, new Date(), GET_AUTO_INIT_FINISHED_FAILED, 0);
                    default:
                        break;
                }
                break;
            case "32":
                // change symbol of sub_display
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), CHANGE_DISPLAY_SYMBOL_SUCCEEDED, 0);
                    case "0"://\n":
                    case "2"://\n":
                    case "3"://\n":
                    case "4"://\n":
                        return new messages(null, new Date(), CHANGE_DISPLAY_SYMBOL_FAILED, 0);
                    default:
                        break;
                }
                break;
            //case "33":
                // report on sub-display
                //
            //
            case "34":
                // change the manual sub-display parameters
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), CHANGE_DISPLAY_SYMBOL_MANUAL_SUCCEEDED, 0);
                    case "0"://\n":
                    case "2"://\n":
                    case "3"://\n":
                    case "4"://\n":
                        return new messages(null, new Date(), CHANGE_DISPLAY_SYMBOL_MANUAL_FAILED, 0);
                    default:
                        break;
                }
                break;
            case "35":
                // change sub-display mode to auto or manual
                switch (s[1]) {
                    case "1"://\n":
                        return new messages(null, new Date(), CHANGE_DISPLAY_SUCCEEDED, 0);
                    case "0"://\n":
                    case "2"://\n":
                    case "3"://\n":
                    case "4"://\n":
                        return new messages(null, new Date(), CHANGE_DISPLAY_FAILED, 0);
                    default:
                        break;
                }
                break;
            default:
                return null;
        }
    return null;
    }

    private static String createStringFormat(messages s) {
        switch (s.getType()) {
            case INIT_MANUAL:
                return "0 0\n";
            case INIT_AUTO:
                return "0 1\n";
            case INIT_MAX_GROUP_NUM:
                return "1 " + String.valueOf(s.getNum()) + "\n";
            case INIT_MAX_DISPLAY_NUM:
                return "2 " + String.valueOf(s.getNum()) + "\n";
            case INIT_MAX_CONTROLLERS_NUM:
                return  "3 " + String.valueOf(s.getNum()) + "\n";
            case INIT_SENSOR:
                return "4 " + String.valueOf(((SensorId)(s.getId())).getZoneControllerId()) + " "
                        + String.valueOf(((SensorId)(s.getId())).getControllerId()) + " "
                        + String.valueOf(((SensorId)(s.getId())).getSensorId()) + "\n";
            case GET_SENSOR_STATE:
                return "9 " + String.valueOf(((SensorId)(s.getId())).getZoneControllerId()) + " "
                        + String.valueOf(((SensorId)(s.getId())).getControllerId()) + " "
                        + String.valueOf(((SensorId)(s.getId())).getSensorId()) + "\n";
            case GET_ALL_SENSORS_STATE:
                return "10\n";
            case CREATE_GROUP:
                // Replace 100 with variable in the future
                return "11 " + String.valueOf(s.getNum()) + " " + "100" + "\n";
            case ATTACH_SENSOR_TO_GROUP:
                return "14 " + String.valueOf(s.getNum()) + " "
                        + String.valueOf(((SensorId)(s.getId())).getZoneControllerId()) + " "
                        + String.valueOf(((SensorId)(s.getId())).getControllerId()) + " "
                        + String.valueOf(((SensorId)(s.getId())).getSensorId()) + "\n";
            case GET_GROUP_SENSORS_STATE:
                return "16 " + String.valueOf(((AreaId)(s.getId())).getAreaId()) + "\n";
            case CREATE_DISPLAY:
                return "17 " + String.valueOf(((SignId)(s.getId())).getSignId()) + "\n";
            case ATTACH_DISPLAY_TO_GROUP_WITH_SYMBOL_UP:
                // Need to change format
                // currently: #display #group #sub-display #direction
                return "20 " + String.valueOf(((SignId)(s.getId())).getSignId()) + " "
                        + String.valueOf(s.getNum()) + " "
                        + String.valueOf(((SignId)(s.getId())).getSubSignId()) + "2\n";
            case ATTACH_DISPLAY_TO_GROUP_WITH_SYMBOL_DOWN:
                // Need to change format
                // currently: #display #group #sub-display #direction
                return "20 " + String.valueOf(((SignId)(s.getId())).getSignId()) + " "
                        + String.valueOf(s.getNum()) + " "
                        + String.valueOf(((SignId)(s.getId())).getSubSignId()) + "3\n";
            case ATTACH_DISPLAY_TO_GROUP_WITH_SYMBOL_LEFT:
                // Need to change format
                // currently: #display #group #sub-display #direction
                return "20 " + String.valueOf(((SignId)(s.getId())).getSignId()) + " "
                        + String.valueOf(s.getNum()) + " "
                        + String.valueOf(((SignId)(s.getId())).getSubSignId()) + "0\n";
            case ATTACH_DISPLAY_TO_GROUP_WITH_SYMBOL_RIGHT:
                // Need to change format
                // currently: #display #group #sub-display #direction
                return "20 " + String.valueOf(((SignId)(s.getId())).getSignId()) + " "
                        + String.valueOf(s.getNum()) + " "
                        + String.valueOf(((SignId)(s.getId())).getSubSignId()) + "1\n";

            case START_REPORT_WITH_INTERVAL:
                return  "24 " + String.valueOf(s.getNum()) + "\n";
            case STOP_REPORT_WITH_INTERVAL:
                return "25\n";
            case START_REPORT_ON_EVENT:
                return "26\n";
            case STOP_REPORT_ON_EVENT:
                return "27\n";
            case GET_ERROR_REPORT:
                return "28\n";
            case RESET_ESP:
                return "29\n";
            case GET_AUTO_INIT_FINISHED:
                return "31\n";
            case CHANGE_DISPLAY_SYMBOL_UP: // NO-ENTRY type is missing
                return "32 " + String.valueOf(((SignId)(s.getId())).getSignId()) + " "
                        + String.valueOf(((SignId)(s.getId())).getSubSignId()) + " "
                        + "2\n";
            case CHANGE_DISPLAY_SYMBOL_DOWN:
                return "32 " + String.valueOf(((SignId)(s.getId())).getSignId()) + " "
                        + String.valueOf(((SignId)(s.getId())).getSubSignId()) + " "
                        + "3\n";
            case CHANGE_DISPLAY_SYMBOL_LEFT: // NO-ENTRY type is missing
                return "32 " + String.valueOf(((SignId)(s.getId())).getSignId()) + " "
                        + String.valueOf(((SignId)(s.getId())).getSubSignId()) + " "
                        + "0\n";
            case CHANGE_DISPLAY_SYMBOL_RIGHT: // NO-ENTRY type is missing
                return "32 " + String.valueOf(((SignId)(s.getId())).getSignId()) + " "
                        + String.valueOf(((SignId)(s.getId())).getSubSignId()) + " "
                        + "1\n";
            case CHANGE_DISPLAY_SYMBOL_UP_MANUAL:
                return "34 " + String.valueOf(((SignId)(s.getId())).getSignId()) + " "
                        + String.valueOf(((SignId)(s.getId())).getSubSignId()) + " "
                        + "2" + " "
                        + String.valueOf(s.getNum()) + " "
                        + "\n";
            case CHANGE_DISPLAY_SYMBOL_DOWN_MANUAL:
                return "34 " + String.valueOf(((SignId)(s.getId())).getSignId()) + " "
                        + String.valueOf(((SignId)(s.getId())).getSubSignId()) + " "
                        + "3" + " "
                        + String.valueOf(s.getNum()) + " "
                        + "\n";
            case CHANGE_DISPLAY_SYMBOL_LEFT_MANUAL:
                return "34 " + String.valueOf(((SignId)(s.getId())).getSignId()) + " "
                        + String.valueOf(((SignId)(s.getId())).getSubSignId()) + " "
                        + "0" + " "
                        + String.valueOf(s.getNum()) + " "
                        + "\n";
            case CHANGE_DISPLAY_SYMBOL_RIGHT_MANUAL:
                return "34 " + String.valueOf(((SignId)(s.getId())).getSignId()) + " "
                        + String.valueOf(((SignId)(s.getId())).getSubSignId()) + " "
                        + "1" + " "
                        + String.valueOf(s.getNum()) + " "
                        + "\n";
            case CHANGE_DISPLAY_TO_MANUAL:
                return "35 " + String.valueOf(((SignId)(s.getId())).getSignId()) + " "
                        + String.valueOf(((SignId)(s.getId())).getSubSignId()) + " "
                        + "1" + " "
                        + "\n";
            case CHANGE_DISPLAY_TO_AUTO:
                return "35 " + String.valueOf(((SignId)(s.getId())).getSignId()) + " "
                        + String.valueOf(((SignId)(s.getId())).getSubSignId()) + " "
                        + "0" + " "
                        + "\n";
            default:
                return null;
        }
    }
}
