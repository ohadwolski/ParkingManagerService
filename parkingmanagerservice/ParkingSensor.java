package parkingmanagerservice;

import java.util.Date;

public class ParkingSensor extends ParkingElement {
    //private SensorId Id;
    //private StatusElement Status;
    //private ConfigurationElement Configuration;
    //private Date TimeStamp;

    public ParkingSensor(SensorId i, StatusElement s, ConfigurationElement c) {
        Id = i;
        Status = s;
        Configuration = c;
        TimeStamp = new Date();

    }

    public ParkingSensor(IdElement i, StatusElement s, ConfigurationElement c) {
        Id = i;
        Status = s;
        Configuration = c;
        TimeStamp = new Date();

    }

    public ParkingSensor (ParkingSensor c) {
        set(c.getId(), c.getStatus(), c.getConfiguration(), c.getTimeStamp());

    }

    public ParkingSensor (ParkingElement c) {
        ParkingSensor s = (ParkingSensor) c;
        set(s.getId(), s.getStatus(), s.getConfiguration(), s.getTimeStamp());
    }

    public void set(SensorId i, StatusElement s, ConfigurationElement c, Date t) {
        super.set(i, s, c, t);
    }

    public void setId (SensorId i) {
        Id = i;
    }

    public boolean compare (ParkingSensor c) {
        ParkingElement s = (ParkingElement) c;
        return super.compare(s);
    }

    public void print() {
        System.out.printf("Parking Sensor - Id: ");
        Id.print();
        System.out.printf("%n");
    }
}


