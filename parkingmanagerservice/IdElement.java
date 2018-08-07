package parkingmanagerservice;

public class IdElement {
    private int id_number;

    public IdElement() {
        id_number = -1;
    }
    public IdElement(int id) {
        id_number = id;
    }

    public IdElement(IdElement c) {
        id_number = c.get();
    }

    public int get() {
        return id_number;
    }

    public void set(int id) {
        id_number = id;
    }

    public boolean compare (IdElement c)
    {
        if (id_number == c.get()) {
            return true;
        } else {
            return false;
        }
    }

    public void print() {
        System.out.printf("IdElement " + id_number);
    }
}

