package parkingmanagerservice;


import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class DataInterface {
    private Node<ParkingElement> root;
    private boolean auto_init;
    private int working_mode; // assume 0: manual by server, 1: on event, 2: every T seconds
    private int update_interval; // in seconds
    private String esp_ip_address;
    private int esp_port_number;

    public DataInterface() {
        root = null;
        working_mode = 0;
        update_interval = 0;
        esp_ip_address = null;
        esp_port_number = 0;
    }

    public DataInterface(Node<ParkingElement> r) {
        root = r;
    }

    public ParkingElement getParkingElement(IdElement id) {
        return FindParkingElement(id, root);
    }

    public Node<ParkingElement> getParkingElementNode(IdElement id) {
        return FindParkingElementNode(id, root);
    }

    private ParkingElement FindParkingElement(IdElement id, Node<ParkingElement> node) {
        if (node == null || id == null)
            return null;
        if (node.getData().getId().compare(id))
            return node.getData();
        List<Node<ParkingElement>> childrenNodes = node.getChildren();
        for (Node<ParkingElement> child : childrenNodes) {
            ParkingElement result = FindParkingElement(id, child);
            if (result != null) return result;
        }
        return null;
    }

    private Node<ParkingElement> FindParkingElementNode(IdElement id, Node<ParkingElement> node) {
        if (node == null || id == null)
            return null;
        if (node.getData().getId().compare(id))
            return node;
        List<Node<ParkingElement>> childrenNodes = node.getChildren();
        for (Node<ParkingElement> child : childrenNodes) {
            Node<ParkingElement> result = FindParkingElementNode(id, child);
            if (result != null) return result;
        }
        return null;
    }

    public List<ParkingElement> getSignsForParkingElement(ParkingElement s) {
        if (s != null)
            return getSignsForParkingElement(s.getId());
        return null;
    }

    public List<ParkingElement> getSignsForParkingElement(IdElement id) {
        Node<ParkingElement> element = getParkingElementNode(id);
        List<ParkingElement> ListOfSigns = new ArrayList<ParkingElement>();
        if (element == null) return null;
        FindSignsForParkingElement(ListOfSigns, element.getParent());
        return ListOfSigns;
    }

    private void FindSignsForParkingElement(List<ParkingElement> ListOfSigns, Node<ParkingElement> node) {
        if (node == null) return;
        List<Node<ParkingElement>> childrenNodes = node.getChildren();
        for (Node<ParkingElement> child : childrenNodes) {
            if (child.getData() instanceof ParkingSign) {
                ListOfSigns.add(child.getData());
            }
        }
        if (node.getParent() == null) return;
        FindSignsForParkingElement(ListOfSigns, node.getParent());
    }

    public int getWorking_mode() {
        return working_mode;
    }

    public void setWorking_mode(int working_mode) {
        this.working_mode = working_mode;
    }

    public int getUpdate_interval() {
        return update_interval;
    }

    public void setUpdate_interval(int update_interval) {
        this.update_interval = update_interval;
    }

    public String getEsp_ip_address() {
        return esp_ip_address;
    }

    public void setEsp_ip_address(String esp_ip_address) {
        this.esp_ip_address = esp_ip_address;
    }

    public int getEsp_port_number() {
        return esp_port_number;
    }

    public void setEsp_port_number(int esp_port_number) {
        this.esp_port_number = esp_port_number;
    }

    public boolean isAuto_init() {
        return auto_init;
    }

    public void setAuto_init(boolean auto_init) {
        this.auto_init = auto_init;
    }

    public List<IdElement> getParkingSensorList() {
        List<IdElement> ParkingSensorIDList = new ArrayList<IdElement>();
        if (root == null)
            return null;
        FindParkingSensors(ParkingSensorIDList, root);
        return ParkingSensorIDList;
    }

    public void FindParkingSensors(List<IdElement> ParkingSensorList, Node<ParkingElement> node) {
        if (node == null)
            return;
        if (node.getData() instanceof ParkingSensor) {
            ParkingSensorList.add(node.getData().getId());
        }
        for (Node<ParkingElement> child : node.getChildren()) {
            FindParkingSensors(ParkingSensorList,child);
        }
    }

    public List<IdElement> getParkingAreaList() {
        List<IdElement> ParkingAreaIDList = new ArrayList<IdElement>();
        if (root == null)
            return null;
        FindParkingAreas(ParkingAreaIDList, root);
        return ParkingAreaIDList;
    }

    public void FindParkingAreas(List<IdElement> ParkingAreaIDList, Node<ParkingElement> node) {
        if (node == null)
            return;
        if (node.getData() instanceof ParkingArea) {
            ParkingAreaIDList.add(node.getData().getId());
        }
        for (Node<ParkingElement> child : node.getChildren()) {
            FindParkingAreas(ParkingAreaIDList,child);
        }
    }

    public void getAreaSigns(List<IdElement> signsUnderArea, Node<ParkingElement> parkingElementNode) {
        if (parkingElementNode == null) return;
        for (Node<ParkingElement> child : parkingElementNode.getChildren()) {
            if (child.getData() instanceof ParkingSign) {
                signsUnderArea.add(child.getData().getId());
            }
        }
    }
}

/*
    get by ID

    get signs under ID

    (actual change of states will perform in message handler

    (building of dummy tree will be in caller function)

    (constructor will get root from caller function)



 */



/*
    Node<String> parentNode = new Node<String>("Parent");
    Node<String> childNode1 = new Node<String>("Child 1", parentNode);
    Node<String> childNode2 = new Node<String>("Child 2");

childNode2.setParent(parentNode);

        Node<String> grandchildNode = new Node<String>("Grandchild of parentNode. Child of childNode1", childNode1);
        List<Node<String>> childrenNodes = parentNode.getChildren();

*/