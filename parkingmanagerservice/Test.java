package parkingmanagerservice;

import java.util.Date;
import java.util.List;

public class Test {


    public static void main(String[] args) {
        /*
        Integer n = new Integer(0);
        Node<Integer> root = new Node<Integer>(n);
        System.out.println(root.getData());
        n. = 8;
        System.out.println(root.getData());
        */

        /*
        ParkingElement p = new ParkingElement();
        IdElement id = new IdElement(2);

        p.set(id,StatusElement.TAKEN,ConfigurationElement.REGULAR, new Date());
        p.print();
        id.set(5);
        p.print();
        */




        ParkingElement area = new ParkingArea(new AreaId(0),StatusElement.OK, ConfigurationElement.REGULAR);
        ParkingElement area2 = new ParkingArea(new AreaId(1),StatusElement.OK, ConfigurationElement.REGULAR);
        ParkingElement area3 = new ParkingArea(new AreaId(2),StatusElement.OK, ConfigurationElement.REGULAR);

        ParkingElement rootSignGeneral = new ParkingSign(new SignId(0, 0),StatusElement.OK, ConfigurationElement.UP);
        ParkingElement rootSignRight = new ParkingSign(new SignId(1, 0),StatusElement.OK, ConfigurationElement.RIGHT);
        ParkingElement rootSignLeft = new ParkingSign(new SignId(2, 0),StatusElement.OK, ConfigurationElement.LEFT);

        ParkingElement Sensor1 = new ParkingSensor(new SensorId(0,2,31), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor2 = new ParkingSensor(new SensorId(0,2,1), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor3 = new ParkingSensor(new SensorId(0,2,2), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor4 = new ParkingSensor(new SensorId(0,2,5), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor5 = new ParkingSensor(new SensorId(0,2,7), StatusElement.FREE, ConfigurationElement.REGULAR);

        ParkingElement Sensor11 = new ParkingSensor(new SensorId(0,0,7), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor22 = new ParkingSensor(new SensorId(0,0,15), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor33 = new ParkingSensor(new SensorId(0,0,31), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor44 = new ParkingSensor(new SensorId(0,0,1), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor55 = new ParkingSensor(new SensorId(0,0,2), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor66 = new ParkingSensor(new SensorId(0,0,5), StatusElement.FREE, ConfigurationElement.REGULAR);

        Node<ParkingElement> root = new Node<ParkingElement>(area);
        Node<ParkingElement> leaf1 = new Node<ParkingElement>(area2);
        Node<ParkingElement> leaf2 = new Node<ParkingElement>(area3);

        Node<ParkingElement> leaf3 = new Node<ParkingElement>(rootSignGeneral);
        Node<ParkingElement> leaf4 = new Node<ParkingElement>(rootSignRight);
        Node<ParkingElement> leaf5 = new Node<ParkingElement>(rootSignLeft);

        Node<ParkingElement> leaf6 = new Node<ParkingElement>(Sensor1);
        Node<ParkingElement> leaf7 = new Node<ParkingElement>(Sensor2);
        Node<ParkingElement> leaf8 = new Node<ParkingElement>(Sensor3);
        Node<ParkingElement> leaf9 = new Node<ParkingElement>(Sensor11);
        Node<ParkingElement> leaf10 = new Node<ParkingElement>(Sensor22);
        Node<ParkingElement> leaf11 = new Node<ParkingElement>(Sensor33);

        Node<ParkingElement> leaf12 = new Node<ParkingElement>(Sensor4);
        Node<ParkingElement> leaf13 = new Node<ParkingElement>(Sensor5);
        Node<ParkingElement> leaf14 = new Node<ParkingElement>(Sensor44);
        Node<ParkingElement> leaf15 = new Node<ParkingElement>(Sensor55);
        Node<ParkingElement> leaf16 = new Node<ParkingElement>(Sensor66);

        root.addChild(leaf1);
        leaf1.setParent(root);
        root.addChild(leaf2);
        leaf2.setParent(root);

        root.addChild(leaf3);
        leaf3.setParent(root);

        leaf1.addChild(leaf6);
        leaf6.setParent(leaf1);
        leaf1.addChild(leaf7);
        leaf7.setParent(leaf1);
        leaf1.addChild(leaf8);
        leaf8.setParent(leaf1);
        leaf1.addChild(leaf4);
        leaf4.setParent(leaf1);
        leaf1.addChild(leaf12);
        leaf12.setParent(leaf1);
        leaf1.addChild(leaf13);
        leaf13.setParent(leaf1);

        leaf2.addChild(leaf9);
        leaf9.setParent(leaf2);
        leaf2.addChild(leaf10);
        leaf10.setParent(leaf2);
        leaf2.addChild(leaf11);
        leaf11.setParent(leaf2);
        leaf2.addChild(leaf5);
        leaf5.setParent(leaf2);
        leaf2.addChild(leaf14);
        leaf14.setParent(leaf2);
        leaf2.addChild(leaf15);
        leaf15.setParent(leaf2);
        leaf2.addChild(leaf16);
        leaf16.setParent(leaf2);
        //print(root);


        DataInterface IF = new DataInterface(root);
        IF.PrintParkingStructure(false, false, false);



/*
        System.out.println("now checking search:");
        Node<ParkingElement> finding = IF.getParkingElementNode(new AreaId(1));
        finding.getData().print();
        System.out.println("now getting signs:");
        List<ParkingElement> listofsigns = IF.getSignsForParkingElement(new SensorId(0,0,0));
        for (ParkingElement sign : listofsigns) {
            sign.print();
        }
*/


        /*
        messages msg = new messages(new SensorId(1,0,5),  new Date(), MessageType.ATTACH_SENSOR_TO_GROUP,3);
        System.out.printf(MessageConverter.convertMessageToString(msg));
        */


        /*
        String msg = "9 3 2 1 0\n";
        messages parsed = MessageConverter.convertStringToMessage(msg);
        if (parsed != null) parsed.print();
        */

    }


    public static DataInterface getTestData() {
        ParkingElement area = new ParkingArea(new AreaId(0),StatusElement.OK, ConfigurationElement.REGULAR);
        ParkingElement area2 = new ParkingArea(new AreaId(1),StatusElement.OK, ConfigurationElement.REGULAR);
        ParkingElement area3 = new ParkingArea(new AreaId(2),StatusElement.OK, ConfigurationElement.REGULAR);

        ParkingElement rootSignGeneral = new ParkingSign(new SignId(0, 0),StatusElement.OK, ConfigurationElement.UP);
        ParkingElement rootSignRight = new ParkingSign(new SignId(1, 0),StatusElement.OK, ConfigurationElement.RIGHT);
        ParkingElement rootSignLeft = new ParkingSign(new SignId(2, 0),StatusElement.OK, ConfigurationElement.LEFT);

        ParkingElement Sensor1 = new ParkingSensor(new SensorId(0,2,31), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor2 = new ParkingSensor(new SensorId(0,2,1), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor3 = new ParkingSensor(new SensorId(0,2,2), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor4 = new ParkingSensor(new SensorId(0,2,5), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor5 = new ParkingSensor(new SensorId(0,2,7), StatusElement.FREE, ConfigurationElement.REGULAR);

        ParkingElement Sensor11 = new ParkingSensor(new SensorId(0,0,7), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor22 = new ParkingSensor(new SensorId(0,0,15), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor33 = new ParkingSensor(new SensorId(0,0,31), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor44 = new ParkingSensor(new SensorId(0,0,1), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor55 = new ParkingSensor(new SensorId(0,0,2), StatusElement.FREE, ConfigurationElement.REGULAR);
        ParkingElement Sensor66 = new ParkingSensor(new SensorId(0,0,5), StatusElement.FREE, ConfigurationElement.REGULAR);

        Node<ParkingElement> root = new Node<ParkingElement>(area);
        Node<ParkingElement> leaf1 = new Node<ParkingElement>(area2);
        Node<ParkingElement> leaf2 = new Node<ParkingElement>(area3);

        Node<ParkingElement> leaf3 = new Node<ParkingElement>(rootSignGeneral);
        Node<ParkingElement> leaf4 = new Node<ParkingElement>(rootSignRight);
        Node<ParkingElement> leaf5 = new Node<ParkingElement>(rootSignLeft);

        Node<ParkingElement> leaf6 = new Node<ParkingElement>(Sensor1);
        Node<ParkingElement> leaf7 = new Node<ParkingElement>(Sensor2);
        Node<ParkingElement> leaf8 = new Node<ParkingElement>(Sensor3);
        Node<ParkingElement> leaf9 = new Node<ParkingElement>(Sensor11);
        Node<ParkingElement> leaf10 = new Node<ParkingElement>(Sensor22);
        Node<ParkingElement> leaf11 = new Node<ParkingElement>(Sensor33);

        Node<ParkingElement> leaf12 = new Node<ParkingElement>(Sensor4);
        Node<ParkingElement> leaf13 = new Node<ParkingElement>(Sensor5);
        Node<ParkingElement> leaf14 = new Node<ParkingElement>(Sensor44);
        Node<ParkingElement> leaf15 = new Node<ParkingElement>(Sensor55);
        Node<ParkingElement> leaf16 = new Node<ParkingElement>(Sensor66);

        root.addChild(leaf1);
        leaf1.setParent(root);
        root.addChild(leaf2);
        leaf2.setParent(root);

        root.addChild(leaf3);
        leaf3.setParent(root);

        leaf1.addChild(leaf6);
        leaf6.setParent(leaf1);
        leaf1.addChild(leaf7);
        leaf7.setParent(leaf1);
        leaf1.addChild(leaf8);
        leaf8.setParent(leaf1);
        leaf1.addChild(leaf4);
        leaf4.setParent(leaf1);
        leaf1.addChild(leaf12);
        leaf12.setParent(leaf1);
        leaf1.addChild(leaf13);
        leaf13.setParent(leaf1);

        leaf2.addChild(leaf9);
        leaf9.setParent(leaf2);
        leaf2.addChild(leaf10);
        leaf10.setParent(leaf2);
        leaf2.addChild(leaf11);
        leaf11.setParent(leaf2);
        leaf2.addChild(leaf5);
        leaf5.setParent(leaf2);
        leaf2.addChild(leaf14);
        leaf14.setParent(leaf2);
        leaf2.addChild(leaf15);
        leaf15.setParent(leaf2);
        leaf2.addChild(leaf16);
        leaf16.setParent(leaf2);
        //print(root);


        DataInterface IF = new DataInterface(root);
        return IF;

    }

    public static void print(Node<ParkingElement> leaf) {
        if (leaf != null) {
            leaf.getData().print();
            if (! leaf.isLeaf()) {
                List<Node<ParkingElement>> childrenNodes = leaf.getChildren();
                for (Node<ParkingElement> child : childrenNodes) {
                    print(child);
                }
            }
        }
    }
}
