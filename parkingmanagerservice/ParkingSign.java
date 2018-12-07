package parkingmanagerservice;

import java.io.Serializable;
import java.util.Date;

public class ParkingSign extends ParkingElement implements Serializable {
    protected int counter;

    public ParkingSign(SignId i, StatusElement s, ConfigurationElement c) {
        Id = i;
        Status = s;
        Configuration = c;
        TimeStamp = new Date();
        counter = 0;
    }

    public ParkingSign(IdElement i, StatusElement s, ConfigurationElement c) {
        Id = i;
        Status = s;
        Configuration = c;
        TimeStamp = new Date();
        counter = 0;
    }


    public ParkingSign (ParkingSign c) {
        set(c.getId(), c.getStatus(), c.getConfiguration(), c.getTimeStamp(), c.getCounter());

    }

    public ParkingSign (ParkingElement c) {
        ParkingSign s = (ParkingSign) c;
        set(s.getId(), s.getStatus(), s.getConfiguration(), s.getTimeStamp(), s.getCounter());
    }

    public void set(IdElement i, StatusElement s, ConfigurationElement c, Date t, int n) {
        this.setCounter(n);
        super.set(i, s, c, t);
    }

    public void set(SignId i, StatusElement s, ConfigurationElement c, Date t, int n) {
        this.setCounter(n);
        super.set(i, s, c, t);
    }


    public void setId (SignId i) {
        Id = i;
    }

    public void setCounter(int n) {
        counter = n;
    }

    public int getCounter() {
        return counter;
    }


    public boolean compare (ParkingSign c) {
        ParkingElement s = (ParkingElement) c;
        return super.compare(s);
    }

    public void print() {
        System.out.printf("Parking Sign - Id: ");
        Id.print();
        System.out.printf("%n");
    }
}
