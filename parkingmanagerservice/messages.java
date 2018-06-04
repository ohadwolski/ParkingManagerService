package parkingmanagerservice;

import java.util.Date;

enum MessageType {
    ECHO, ACKECHO;
}
public class messages {
    private int Id;
    private Date Date;
    private MessageType Type;

    public messages(int id, Date date, MessageType type) {
        Id = id;
        this.Date = date;
        Type = type;
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

    public boolean compare(messages msg) {
        return this.compareDate(msg.getDate()) && this.compareId(msg.getId()) && this.compareType(msg.getType());
    }
}
