package parkingmanagerservice;

public class SensorId extends IdElement {
    private int ZoneControllerId;
    private int ControllerId;
    private int SensorId;

    public SensorId() {
        ZoneControllerId = -1;
        ControllerId = -1;
        SensorId = -1;
    }

    public  SensorId(int z, int c, int s) {
        ZoneControllerId = z;
        ControllerId = c;
        SensorId = s;
    }

    public SensorId(SensorId c) {
        ZoneControllerId = c.getZoneControllerId();
        ControllerId = c.getControllerId();
        SensorId = c.getSensorId();
    }

    public SensorId(IdElement c) {
         if (c instanceof  SensorId) {
            SensorId s = (SensorId) c;
             ZoneControllerId = s.getZoneControllerId();
             ControllerId = s.getControllerId();
             SensorId = s.getSensorId();
         }
    }

    public int getZoneControllerId() {return  ZoneControllerId; }
    public int getControllerId() { return  ControllerId; }
    public int getSensorId() { return SensorId; }
    public void setZoneControllerId(int n) {ZoneControllerId = n;}
    public void setControllerId(int n) {ControllerId = n;}
    public void setSensorId(int n) {SensorId = n;}


    public boolean compare (SensorId c)
    {
        if (ZoneControllerId == c.getZoneControllerId() &
                ControllerId == c.getControllerId() &
                SensorId == c.getSensorId()) return true;
        return  false;
    }

    public boolean compare (IdElement c)
    {
        if (c instanceof  SensorId) {
            SensorId s = (SensorId) c;
            return compare(s);
        }
        return  false;
    }

    public void print() {
        System.out.printf("SensorId: ZoneControllerId=" + ZoneControllerId + " ControllerId=" + ControllerId + " SensorId=" + SensorId);
    }
}
