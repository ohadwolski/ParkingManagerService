package parkingmanagerservice;

import java.util.Date;

public class ParkingArea extends ParkingElement {



    public ParkingArea(AreaId i, StatusElement s, ConfigurationElement c) {
        Id = i;
        Status = s;
        Configuration = c;
        TimeStamp = new Date();

    }

    public ParkingArea(IdElement i, StatusElement s, ConfigurationElement c) {
        Id = i;
        Status = s;
        Configuration = c;
        TimeStamp = new Date();

    }

    public ParkingArea (ParkingArea c) {
        set(c.getId(), c.getStatus(), c.getConfiguration(), c.getTimeStamp());

    }

    public ParkingArea (ParkingElement c) {
        ParkingArea s = (ParkingArea) c;
        set(s.getId(), s.getStatus(), s.getConfiguration(), s.getTimeStamp());
    }

    public void set(SensorId i, StatusElement s, ConfigurationElement c, Date t) {
        super.set(i, s, c, t);
    }

    public void setId (AreaId i) {
        Id = i;
    }

    public boolean compare (ParkingArea c) {
        ParkingElement s = (ParkingElement) c;
        return super.compare(s);
    }

    public void print() {
        System.out.printf("Parking Area - Id: ");
        Id.print();
        System.out.printf("%n");
    }

}
