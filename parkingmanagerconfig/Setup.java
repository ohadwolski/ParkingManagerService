package parkingmanagerconfig;

import parkingmanagerdata.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

//The main Class of the Setup Application
public class Setup {

	private DataInterface parkData = null; //interface variable
	private ArrayList<AreaId> areaIDList = new ArrayList<AreaId>(); //areas Id List
	private ArrayList<SignId> signIDList = new ArrayList<SignId>(); //signs Id List	
	private ArrayList<SensorId> sensorIDList = new ArrayList<SensorId>(); //sensors Id List
	private ArrayList<Node<ParkingElement>> elementsToRemove = new ArrayList<Node<ParkingElement>>(); //hold sensors and signs which should be removed
	private ArrayList<Node<ParkingElement>> areaElementsToRemove = new ArrayList<Node<ParkingElement>>(); //hold areas which should be removed
	
	//constructor
	public Setup(boolean New) {
		
		if (New) {
			AreaId id = new AreaId(0);
			areaIDList.add(id);
			parkData = new DataInterface(true);
		}
		else {
			parkData = new DataInterface(false);
		}
	}
	
	//returns the root of the parking's data
	public Node<ParkingElement> getParkRoot() {
		
		return parkData.getRoot();
	}	
	
	//get list of areas id in the parking lot
	public ArrayList<AreaId> getAreas(ArrayList<AreaId> areasList, Node<ParkingElement> parkingLot) {
		
		return parkData.getAreasOfParkingLot( areasList, parkingLot);
	}
	
	//set server setup initial mode
	void setAutoInit(boolean answer) {
    	
		parkData.setAutoInit(answer);
    }
	
	//get server setup initial mode
	boolean getAutoInit() {
    	
		return parkData.getAutoInit();
    }
	
	//set server working mode
	void setWorkingMode(int mode) {
    	
		parkData.setwWrkingMode(mode);
    }
	
	//get server working mode
	int getWorkingMode() {
    	
		return parkData.getwWrkingMode();
    }
	
	//set server update interval
	void setUpdateInterval(int interval) {
    	
		parkData.setUpdateInterval(interval);
    }
	
	//get server update interval
	int getUpdateInterval() {
    	
		return parkData.getUpdateInterval();
    }
	
	//set server esp ip address
	void setIpAddress(String ipAddress) {
    	
		parkData.setEspIpAddress(ipAddress);
    }
	
	//get server esp ip address
	String getEspIpAddress() {
    	
		return parkData.getEspIpAddress();
    }
	
	//set server port
	void setPort(int port) {
    	
		parkData.setPort(port);
    }
	
	//get server port
	int getPort() {
    	
		return parkData.getPort();
    }
		
	//returns the areas Id array
	public ArrayList<AreaId> getAreaIdArray() {
    	
    	return areaIDList;
    }
    
	//returns the signs Id array
    public ArrayList<SignId> getSignIdArray() {
    	
    	return signIDList;
    }
    
    //returns the sensors Id array
    public ArrayList<SensorId> getSensorIdArray() {
    	
    	return sensorIDList;
    }
	
    //get the data of the root of the parking's data
    public ParkingElement getRootParkingElement( ) {
    		
    	return parkData.getRoot().getData();
    }

    //returns a parking element according to Id
    public ParkingElement getParkingElement(IdElement id) {
    	
    	return parkData.getParkingElement(id);
    }
    
    //returns a parking element node according to Id
    public Node<ParkingElement> getParkingElementNode(IdElement id) {
    	
    	return parkData.getParkingElementNode(id);
    }
    
    //add area to the data structure
    public ParkingElement addParkingArea (int Id, StatusElement s, ConfigurationElement c, IdElement parent) {
		
		IdElement id = new AreaId(Id);
		AreaId idToList = new AreaId(Id);
		areaIDList.add(idToList);
		ParkingElement newArea = new ParkingArea(id, s, c);
		Node<ParkingElement> newAreaNode = new Node<ParkingElement>(newArea);	
		newAreaNode.setParent(parkData.getParkingElementNode(parent));
		newAreaNode.getParent().addChild(newAreaNode);
		return newAreaNode.getData();		
	}
    
    //add sign to the data structure
	public ParkingElement addParkingSign (int SignId, int SubSignId, StatusElement s, ConfigurationElement c, IdElement parent) {
		
		IdElement id = new SignId(SignId,SubSignId);
		SignId idToList = new SignId(SignId,SubSignId);
		signIDList.add(idToList);
		ParkingElement newSign = new ParkingSign(id, s, c);
		Node<ParkingElement> newSignNode = new Node<ParkingElement>(newSign);	
		newSignNode.setParent(parkData.getParkingElementNode(parent));
		newSignNode.getParent().addChild(newSignNode);
		return newSignNode.getData();
	}
	
	//add sensor to the data structure
	public ParkingElement addParkingSensor (int ZoneControllerId, int ControllerId, int SensorId, StatusElement s, ConfigurationElement c, IdElement parent) {
		
		IdElement id = new SensorId(ZoneControllerId, ControllerId, SensorId);
		SensorId idToList = new SensorId(ZoneControllerId, ControllerId, SensorId);
		sensorIDList.add(idToList);
		ParkingElement newSensor = new ParkingSensor(id, s, c);
		Node<ParkingElement> newSensorNode = new Node<ParkingElement>(newSensor);	
		newSensorNode.setParent(parkData.getParkingElementNode(parent));
		newSensorNode.getParent().addChild(newSensorNode);
		return newSensorNode.getData();
	}
	
	//add parking element to the data strcture
	public void addParkingElement (Node<ParkingElement> element, IdElement parent) {
		
		element.setParent(parkData.getParkingElementNode(parent));
		element.getParent().addChild(element);
	}
	
	//remove elements from the data structure
	public void removeElements() {
		
		Iterator<Node<ParkingElement>> iter = elementsToRemove.iterator();
		while (iter.hasNext()) {
			Node<ParkingElement> item = iter.next();
			if (item.getData().getId() instanceof SensorId) {
				Iterator<SensorId> sensorIter = sensorIDList.iterator();
				while (sensorIter.hasNext()) {
					SensorId id = sensorIter.next();
					if (id.compare(item.getData().getId())) {
						sensorIter.remove();
						parkData.getParkingElementNode(id).getParent().getChildren().remove(item);
					}
				}			
			}
			else if(item.getData().getId() instanceof SignId) {
				Iterator<SignId> signIter = signIDList.iterator();
				while (signIter.hasNext()) {
					SignId id = signIter.next();
					if (id.compare(item.getData().getId())) {
						signIter.remove();
						parkData.getParkingElementNode(id).getParent().getChildren().remove(item);
					}					
				}
			}	
			iter.remove();
		}
		
		Iterator<Node<ParkingElement>> iter2 = areaElementsToRemove.iterator();
		while (iter2.hasNext()) {	
			Iterator<AreaId> areaIter = areaIDList.iterator();
			Node<ParkingElement> item = iter2.next();
			if (item.getData().getId() instanceof AreaId) {
				while (areaIter.hasNext()) {
					AreaId id = areaIter.next();
					if (id.compare(item.getData().getId())) {
						areaIter.remove();
						parkData.getParkingElementNode(id).getParent().getChildren().remove(item);
					}
				}
				iter2.remove();
			}
		}		
	}
	
	//finds recursively elements which should be removed
	public void findElementsTorRemove(IdElement Id) {
			
		Iterator<Node<ParkingElement>> iter = parkData.getParkingElementNode(Id).getParent().getChildren().iterator();
		while (iter.hasNext()) {	
			Node<ParkingElement> item = iter.next();
			if (item.getData().getId().compare(Id)) {
				if (item.getData().getId() instanceof AreaId) {
					for (AreaId id : areaIDList) {
						if (id.compare(item.getData().getId())) {	
							if (!item.isLeaf()) {
								Iterator<Node<ParkingElement>> iter2 = parkData.getParkingElementNode(Id).getChildren().iterator(); //
								while (iter2.hasNext()) {
									findElementsTorRemove(iter2.next().getData().getId());
								}
							}
							areaElementsToRemove.add(item);
						}		
					}			
				}
				else if (item.getData().getId() instanceof SignId) {
					for (SignId id: signIDList) {
						if (id.compare(item.getData().getId())) {	
							elementsToRemove.add(item);
						}
					}	
				}
				else if(item.getData().getId() instanceof SensorId) {
					for (SensorId id: sensorIDList) {
						if (id.compare(item.getData().getId())) {	
							elementsToRemove.add(item);
						}	
					}
				}
			}
		}	
	}
	
	//change hierarchy of a parking element
	public void changeParentOfParkingElement(IdElement Id, IdElement newParent) {

		Node<ParkingElement> element = parkData.getParkingElementNode(Id);
		int idx = parkData.getParkingElementNode(Id).getParent().getChildren().indexOf(parkData.getParkingElementNode(Id));
		parkData.getParkingElementNode(Id).getParent().getChildren().remove(idx);
		addParkingElement(element, newParent);	
	}
	
	//change configuration of an element
	public void changeElementConfig (IdElement Id, ConfigurationElement newConfig) {
		
		parkData.getParkingElement(Id).setConfiguration(newConfig);
	}
	
	//initialize the areas array
	public void initAreasArray( ) {
		
		parkData.getAreasOfParkingLot(areaIDList, parkData.getRoot());
	}
	
	//initialize the signs array
	public void initSignsArray( ) {
		
		parkData.getSignsOfParkingLot(signIDList, parkData.getRoot());
	}
	
	//initialize the sensors array
	public void initSensorsArray( ) {
		
		parkData.getSensorsOfParkingLot(sensorIDList, parkData.getRoot());
	}
	
	//save as method
	public void saveDataInterface() throws IOException {
		
		parkData.saveDataInterface();
	}
	
	//load as method
	public void loadDataInterface() throws ClassNotFoundException, IOException {
		
		parkData.loadDataInterface();		
	}
}
