package parkingmanagerservice;


import parkingmanagerdata.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataInterface implements Serializable {
    //private Node<ParkingElement> root;
    //private boolean auto_init;
    //private int working_mode; // assume 0: manual by server, 1: on event, 2: every T seconds
    //private int update_interval; // in seconds
    //private String esp_ip_address;
    //private int esp_port_number;

    private ParkingManagerData Data = new ParkingManagerData();

    public DataInterface() {
        Data.root = null;
        Data.working_mode = 0;
        Data.update_interval = 0;
        Data.esp_ip_address = null;
        Data.esp_port_number = 0;
    }

    public DataInterface(Node<ParkingElement> r) {
        Data.root = r;
    }
    public DataInterface(DataInterface d, Node<ParkingElement> r) {
        Data.root = r;
        Data.auto_init = d.Data.auto_init;
        Data.working_mode = d.Data.working_mode;
        Data.update_interval = d.Data.update_interval;
        Data.esp_ip_address = d.Data.esp_ip_address;
        Data.esp_port_number = d.Data.esp_port_number;
    }

    public void LoadData() {
        System.out.println("Loading data from file...");
        try {
            FileInputStream fileIn = new FileInputStream("./ParkingManagerData.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Data = (ParkingManagerData) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            System.out.println("Can't access file /ParkingManagerData.ser");
            i.printStackTrace();
            ParkingManagerService.exit();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("ParkingManagerData class not found");
            c.printStackTrace();
            ParkingManagerService.exit();
            return;
        }
    }

    public void SaveData() {
        try {
            FileOutputStream fileOut = new FileOutputStream("./ParkingManagerData.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Data);
            out.close();
            fileOut.close();
            System.out.println("Data saved to /ParkingManagerData.ser");
        } catch (IOException i) {
            System.out.println("Can't save file /ParkingManagerData.ser");
            i.printStackTrace();
            ParkingManagerService.exit();
        }
    }

    public ParkingElement getParkingElement(IdElement id) {
        return FindParkingElement(id, Data.root);
    }

    public Node<ParkingElement> getParkingElementNode(IdElement id) {
        return FindParkingElementNode(id, Data.root);
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
        return Data.working_mode;
    }

    public void setWorking_mode(int working_mode) {
        this.Data.working_mode = working_mode;
    }

    public int getUpdate_interval() {
        return Data.update_interval;
    }

    public void setUpdate_interval(int update_interval) {
        this.Data.update_interval = update_interval;
    }

    public String getEsp_ip_address() {
        return Data.esp_ip_address;
    }

    public void setEsp_ip_address(String esp_ip_address) {
        this.Data.esp_ip_address = esp_ip_address;
    }

    public int getEsp_port_number() {
        return Data.esp_port_number;
    }

    public void setEsp_port_number(int esp_port_number) {
        this.Data.esp_port_number = esp_port_number;
    }

    public boolean isAuto_init() {
        return Data.auto_init;
    }

    public void setAuto_init(boolean auto_init) {
        this.Data.auto_init = auto_init;
    }

    public List<IdElement> getParkingSensorIdList() {
        List<IdElement> ParkingSensorIDList = new ArrayList<IdElement>();
        if (Data.root == null)
            return null;
        FindParkingSensorsIds(ParkingSensorIDList, Data.root);
        return ParkingSensorIDList;
    }

    public void FindParkingSensorsIds(List<IdElement> ParkingSensorList, Node<ParkingElement> node) {
        if (node == null)
            return;
        if (node.getData() instanceof ParkingSensor) {
            ParkingSensorList.add(node.getData().getId());
        }
        for (Node<ParkingElement> child : node.getChildren()) {
            FindParkingSensorsIds(ParkingSensorList,child);
        }
    }

    public List<ParkingElement> getParkingSensorList() {
        List<ParkingElement> ParkingSensorList = new ArrayList<ParkingElement>();
        if (Data.root == null)
            return null;
        FindParkingSensors(ParkingSensorList, Data.root);
        return ParkingSensorList;
    }

    public void FindParkingSensors(List<ParkingElement> ParkingSensorList, Node<ParkingElement> node) {
        if (node == null)
            return;
        if (node.getData() instanceof ParkingSensor) {
            ParkingSensorList.add(node.getData());
        }
        for (Node<ParkingElement> child : node.getChildren()) {
            FindParkingSensors(ParkingSensorList,child);
        }
    }

    public List<IdElement> getParkingAreaList() {
        List<IdElement> ParkingAreaIDList = new ArrayList<IdElement>();
        if (Data.root == null)
            return null;
        FindParkingAreas(ParkingAreaIDList, Data.root);
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


    public void PrintParkingStructure(boolean PrintStatus, boolean PrintConfiguration, boolean PrintTimeStamp) {
        System.out.println("Printing Parking Lot Structure:");
        PrintRecursive("--", Data.root, PrintStatus, PrintConfiguration, PrintTimeStamp);
    }

    public void PrintRecursive(String prefix, Node<ParkingElement> NodeToPrint, boolean PrintStatus, boolean PrintConfiguration, boolean PrintTimeStamp) {
        if (NodeToPrint == null) return;

        System.out.printf(prefix + " ");
        NodeToPrint.getData().print();

        if (PrintStatus) {
            System.out.printf(prefix + "--> Status: " + NodeToPrint.getData().getStatus() + "%n");
        }
        if (PrintConfiguration) {
            System.out.printf(prefix + "--> Configuration: " + NodeToPrint.getData().getConfiguration() + "%n");
        }
        if (PrintTimeStamp) {
            System.out.printf(prefix + "--> TimeStamp: " + NodeToPrint.getData().getTimeStamp() + "%n");
        }

        if (! NodeToPrint.isLeaf()) {
            List<Node<ParkingElement>> children = NodeToPrint.getChildren();
            for (Node<ParkingElement> child : children) {
                PrintRecursive((prefix + "--"), child, PrintStatus, PrintConfiguration, PrintTimeStamp);
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