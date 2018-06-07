

package parkingmanagerservice;

import java.util.Date;

enum MessageType {
    ECHO, ACK_ECHO,                                                                                         // Connection check
    PARKING_SPOT_TAKEN, PARKING_SPOT_FREED,                                                                 // Notify parking spot taken and freed
    SET_PAKING_SPOT_LED_OFF, SET_PAKING_SPOT_LED_RED, SET_PAKING_SPOT_LED_GREEN, SET_PAKING_SPOT_LED_BLUE,  // Set led color
    UPDATE_COUNTER,                                                                                         // Update counter to new value
    SET_SIGN_LEFT, SET_SIGN_RIGHT, SET_SIGN_FORWARD, SET_SIGN_BACK, SET_SIGN_NO_ENTRY;                      // Set sign post picture
}

public class messages implements java.io.Serializable{
    private int Id;
    private Date Date;
    private MessageType Type;
    private int Num;

    public messages(int id, Date date, MessageType type, int num) {
        Id = id;
        Date = date;
        Type = type;
        Num = num;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean compareId(int id) {
        return Id == id;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        this.Date = date;
    }

    public boolean compareDate(Date date) {
        return this.Date == date;
    }

    public MessageType getType() {
        return Type;
    }

    public void setType(MessageType type) {
        Type = type;
    }

    public boolean compareType(MessageType type) {
        return this.Type == type;
    }

    public void setNum(int num) {
        Num = num;
    }
    public int getNum() {
    	return Num;
    }
    public boolean compareNum(int num) {
        return Num == num;
    }

    public boolean compare(messages msg) {
        return this.compareDate(msg.getDate()) && this.compareId(msg.getId()) && this.compareType(msg.getType()) && this.compareNum(msg.getNum());
    }

    public void print() {
        System.out.println("Message Printout:\n");
        System.out.println("Id:" + this.getId() + "\n");
        System.out.println("Date:" + this.getDate() + "\n");
        System.out.println("Type:" + this.getType() + "\n");
        System.out.println("Num:" + this.getNum() + "\n");
        System.out.println("End of message.\n");
    }
}
