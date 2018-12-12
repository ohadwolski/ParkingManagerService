package parkingmanagerdata;

import java.io.Serializable;

public class ParkingManagerData implements Serializable {
    public Node<ParkingElement> root;
    public boolean auto_init;
    public int working_mode; // assume 0: manual by server, 1: on event, 2: every T seconds
    public int update_interval; // in seconds
    public String esp_ip_address;
    public int esp_port_number;

    public ParkingManagerData() {
        root = null;
        working_mode = 0;
        update_interval = 0;
        esp_ip_address = "";
        esp_port_number = 0;
        auto_init = false;
    }
}
