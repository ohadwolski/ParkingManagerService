package parkingmanagerservice;

public class SignId extends IdElement {
    private int SignId;

    public SignId() {
        SignId = -1;
    }

    public  SignId(int n) {
        SignId = n;
    }

    public SignId(SignId c) {
        SignId = c.getSignId();
    }

    public SignId(IdElement c) {
        if (c instanceof  SignId) {
            SignId s = (SignId) c;
            SignId = s.getSignId();
        }
    }

    public int getSignId() { return  SignId; }
    public void setSignId(int n) { SignId = n; }


    public boolean compare (SignId c)
    {
        if (SignId == c.getSignId()) return true;
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
        System.out.printf("SignId: " + SignId);
    }
}

