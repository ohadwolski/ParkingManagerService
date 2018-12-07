package parkingmanagerservice;

import java.io.Serializable;

public class SignId extends IdElement implements Serializable {
    private int SignId;
    private int SubSignId;

    public SignId() {
        SignId = -1;
        SubSignId = -1;
    }

    public  SignId(int n, int m) {
        SignId = n;
        SubSignId = m;
    }

    public SignId(SignId c) {
        SignId = c.getSignId();
        SubSignId = c.getSubSignId();
    }

    public SignId(IdElement c) {
        if (c instanceof  SignId) {
            SignId s = (SignId) c;
            SignId = s.getSignId();
            SubSignId = s.getSubSignId();
        }
    }

    public int getSignId() { return  SignId; }
    public void setSignId(int n) { SignId = n; }


    public boolean compare (SignId c)
    {
        if (SignId == c.getSignId() && SubSignId == c.getSubSignId()) return true;
        return  false;
    }

    public boolean compare (IdElement c)
    {
        if (c instanceof  SignId) {
            SignId s = (SignId) c;
            return compare(s);
        }
        return  false;
    }

    public void print() {
        System.out.printf("SignId: " + SignId + " SubSignId: " + SubSignId);
    }

    public int getSubSignId() {
        return SubSignId;
    }

    public void setSubSignId(int subSignId) {
        SubSignId = subSignId;
    }
}

