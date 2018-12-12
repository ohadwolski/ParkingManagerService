package parkingmanagerconfig;

import parkingmanagerservice.*;
import parkingmanagerdata.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class Setup {

	private DataInterface parkData = null;
	private ArrayList<AreaId> areaIDList = new ArrayList<AreaId>();
	private ArrayList<SignId> signIDList = new ArrayList<SignId>();
	private ArrayList<SensorId> sensorIDList = new ArrayList<SensorId>();
	private ArrayList<Node<ParkingElement>> elementsToRemove = new ArrayList<Node<ParkingElement>>();
	private ArrayList<Node<ParkingElement>> areaElementsToRemove = new ArrayList<Node<ParkingElement>>();
	
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
	
	public Node<ParkingElement> getParkRoot() {
		
		return parkData.getRoot();
	}	
	
	public ArrayList<AreaId> getAreas(ArrayList<AreaId> areasList, Node<ParkingElement> parkingLot) {
		
		return parkData.getAreasOfParkingLot( areasList, parkingLot);
	}
	
	void setAutoInit(boolean answer) {
    	
		parkData.setAutoInit(answer);
    }
	
	boolean getAutoInit() {
    	
		return parkData.getAutoInit();
    }
	
	void setWorkingMode(int mode) {
    	
		parkData.setwWrkingMode(mode);
    }
	
	int getWorkingMode() {
    	
		return parkData.getwWrkingMode();
    }
	
	void setUpdateInterval(int interval) {
    	
		parkData.setUpdateInterval(interval);
    }
	
	int getUpdateInterval() {
    	
		return parkData.getUpdateInterval();
    }
	
	void setIpAddress(String ipAddress) {
    	
		parkData.setEspIpAddress(ipAddress);
    }
	
	String getEspIpAddress() {
    	
		return parkData.getEspIpAddress();
    }
	
	void setPort(int port) {
    	
		parkData.setPort(port);
    }
	
	int getPort() {
    	
		return parkData.getPort();
    }
		
	public ArrayList<AreaId> getAreaIdArray() {
    	
    	return areaIDList;
    }
    
    public ArrayList<SignId> getSignIdArray() {
    	
    	return signIDList;
    }
    
    public ArrayList<SensorId> getSensorIdArray() {
    	
    	return sensorIDList;
    }
	
    public ParkingElement getRootParkingElement( ) {
    		
    	return parkData.getRoot().getData();
    }

    public ParkingElement getParkingElement(IdElement id) {
    	
    	return parkData.getParkingElement(id);
    }
    
    public Node<ParkingElement> getParkingElementNode(IdElement id) {
    	
    	return parkData.getParkingElementNode(id);
    }
    
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
	
	public void addParkingElement (Node<ParkingElement> element, IdElement parent) {
		
		element.setParent(parkData.getParkingElementNode(parent));
		element.getParent().addChild(element);
	}
	
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
	
	public void changeParentOfParkingElement(IdElement Id, IdElement newParent) {

		Node<ParkingElement> element = parkData.getParkingElementNode(Id);
		int idx = parkData.getParkingElementNode(Id).getParent().getChildren().indexOf(parkData.getParkingElementNode(Id));
		parkData.getParkingElementNode(Id).getParent().getChildren().remove(idx);
		addParkingElement(element, newParent);	
	}
	
	public void changeElementConfig (IdElement Id, ConfigurationElement newConfig) {
		
		parkData.getParkingElement(Id).setConfiguration(newConfig);
	}
	
	public void initAreasArray( ) {
		
		parkData.getAreasOfParkingLot(areaIDList, parkData.getRoot());
	}
	
	public void initSignsArray( ) {
		
		parkData.getSignsOfParkingLot(signIDList, parkData.getRoot());
	}
	
	public void initSensorsArray( ) {
		
		parkData.getSensorsOfParkingLot(sensorIDList, parkData.getRoot());
	}
	
	public void saveDataInterface() {
		
			parkData.saveDataInterface();
		   /*try {
		        FileOutputStream fileOut = new FileOutputStream("C:/Users/alonjaro/Desktop/saveLoad/data.ser");
		        ObjectOutputStream out = new ObjectOutputStream(fileOut);
		        //out.writeObject(parkData);
		        out.writeObject(parkData.getData());
		        out.close();
		        fileOut.close();
		        System.out.printf("Serialized data is saved in /Users/alonjaro/Desktop/data.ser");
		   } 
		   catch (IOException i) {
			   	i.printStackTrace();
		   }*/
	}
	
	public void loadDataInterface() {
		
			parkData.loadDataInterface();
			/*try {
				FileInputStream fileIn = new FileInputStream("C:/Users/alonjaro/Desktop/saveLoad/data.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				//parkData = (DataInterface) in.readObject();
				ParkingManagerData Data = (ParkingManagerData) in.readObject();
				parkData.setData(Data);
				in.close();
				fileIn.close();
			} 
			catch (IOException i) {
				i.printStackTrace();
				return;
			} 
			catch (ClassNotFoundException c) {
				System.out.println("Data class not found");
	        	c.printStackTrace();
	        	return;
			}*/
	}
}
