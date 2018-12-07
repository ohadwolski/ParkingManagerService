package parkingmanagerservice;

import java.io.Serializable;
import java.util.Date;

public class ParkingElement implements Serializable {
    protected IdElement Id = null;
    protected StatusElement Status = null;
    protected ConfigurationElement Configuration = null;
    protected Date TimeStamp = null;

    public ParkingElement() {

    }

    public ParkingElement(ParkingElement c) {

    }

    public void set(IdElement i, StatusElement s, ConfigurationElement c, Date t) {
        this.setConfiguration(c);
        this.setStatus(s);
        this.setTimeStamp(t);
        this.setId(i);
    }

    public void setId (IdElement c) {
        Id = c;
    }

    public void setConfiguration(ConfigurationElement configuration) {
        Configuration = configuration;
    }

    public void setStatus(StatusElement status) {
        Status = status;
    }

    public void setTimeStamp(Date timeStamp) {
        TimeStamp = timeStamp;
    }

    public ConfigurationElement getConfiguration() {
        return Configuration;
    }

    public StatusElement getStatus() {
        return Status;
    }

    public Date getTimeStamp() {
        return TimeStamp;
    }

    public IdElement getId() {
        return Id;
    }

    public boolean compare(ParkingElement c) {
        return Id.compare(c.getId());
    }

    public void print() {
        System.out.printf("Parking Element - Id: ");
        Id.print();
        System.out.printf("%n");
    }
}

