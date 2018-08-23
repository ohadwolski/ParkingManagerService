package parkingmanageradmin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import parkingmanagerservice.*;


public class Setup {

	private DataInterface parkData;
	
	public Setup() {
		
		Node<ParkingElement> parkNode = new Node<ParkingElement>(null);
		parkData = new DataInterface(parkNode);
	}
	
	public Node<ParkingElement> addParkingArea (int Id, StatusElement s, ConfigurationElement c, int parent) {
		
		AreaId id = new AreaId(Id);
		IdElement parentOfNode = new IdElement(parent);
		ParkingArea newArea = new ParkingArea(id, s, c);
		Node<ParkingElement> newAreaNode = new Node<ParkingElement>(newArea);	
		newAreaNode.setParent(parkData.getParkingElementNode(parentOfNode));
		return newAreaNode;	
	}
	
	public Node<ParkingElement> addParkingSensor (int z, int b, int a, StatusElement s, ConfigurationElement c, int parent) {
		
		SensorId id = new SensorId(z, b, a);
		IdElement parentOfNode = new IdElement(parent);
		ParkingSensor newSensor = new ParkingSensor(id, s, c);
		Node<ParkingElement> newSensorNode = new Node<ParkingElement>(newSensor);	
		newSensorNode.setParent(parkData.getParkingElementNode(parentOfNode));
		return newSensorNode;	
	}
	
	public Node<ParkingElement> addParkingSign (int m, int n, StatusElement s, ConfigurationElement c, int parent) {
		
		SignId id = new SignId(n,m);
		IdElement parentOfNode = new IdElement(parent);
		ParkingSign newSign = new ParkingSign(id, s, c);
		Node<ParkingElement> newSignNode = new Node<ParkingElement>(newSign);	
		newSignNode.setParent(parkData.getParkingElementNode(parentOfNode));
		return newSignNode;	
	}
	
	
	public void removeParkingElement(int Id) {
		
		IdElement elementToRemove = new IdElement(Id);
		List<Node<ParkingElement>> containingList = parkData.getParkingElementNode(elementToRemove).getParent().getChildren();
		for(Node<ParkingElement> item: containingList) {
			
			if (item.getData().getId().compare(elementToRemove)) {
				containingList.remove(containingList.indexOf(item));
			}
		}
	}
	
	public void changeElementConfig (int Id, ConfigurationElement newConfig) {
		
		IdElement elementToChange = new IdElement(Id);
		parkData.getParkingElement(elementToChange).setConfiguration(newConfig);
	}
	
	public void changeElementStatus (int Id, StatusElement newStatus) {
		
		IdElement elementToChange = new IdElement(Id);
		parkData.getParkingElement(elementToChange).setStatus(newStatus);
	}
		
	void setAutoInit() {
    	System.out.println("Do you wish auto init? - 0 no, 1 yes");
    	Scanner in = new Scanner(System.in);
    	if(in.nextInt() == 0) {
    		parkData.setAutoInit(false);
    	}
    	else {
    		parkData.setAutoInit(true);
    	}
    	in.close();
    }
	
	void setUpdateInterval() {
    	System.out.println("which update interval do you want?");
    	Scanner in = new Scanner(System.in);
    	parkData.setUpdateInterval(in.nextInt());
    	in.close();
    }
	
	void setWorkingMode() {
    	System.out.println("which working mode do you want?");
    	Scanner in = new Scanner(System.in);
    	parkData.setwWrkingMode(in.nextInt());
    	in.close();
    }
	
	void setIpAddress() {
    	System.out.println("which ip address do you want?");
    	Scanner in = new Scanner(System.in);
    	parkData.setEspIpAddress(in.nextLine());
    	in.close();
    }
	//public getStructureOfThePark
	
}
