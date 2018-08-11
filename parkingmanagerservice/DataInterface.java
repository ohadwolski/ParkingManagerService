package parkingmanagerservice;


import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class DataInterface {
    private Node<ParkingElement> root;

    public DataInterface() {
        root = null;
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