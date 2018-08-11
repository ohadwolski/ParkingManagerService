package parkingmanagerservice;

public class AreaId extends IdElement {
    private int AreaId;

    public AreaId() {
        AreaId = -1;
    }

    public  AreaId(int n) {
        AreaId = n;
    }

    public AreaId(AreaId c) {
        if (c != null) AreaId = c.getAreaId();
    }

    public AreaId(IdElement c) {
        if (c != null && c instanceof  AreaId) {
            AreaId s = (AreaId) c;
            AreaId = s.getAreaId();
        }
    }

    public int getAreaId() { return  AreaId; }
    public void setAreaId(int n) { AreaId = n; }


    public boolean compare (AreaId c)
    {
        if (c != null && AreaId == c.getAreaId()) return true;
        return  false;
    }

    public boolean compare (IdElement c)
    {
        if (c != null && c instanceof  AreaId) {
            AreaId s = (AreaId) c;
            return compare(s);
        }
        return  false;
    }

    public void print() {
        System.out.printf("AreaId " + AreaId);
    }
}
