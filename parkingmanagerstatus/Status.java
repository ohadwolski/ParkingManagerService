package parkingmanagerstatus;


import parkingmanagerdata.*;
import java.util.ArrayList;

//The main Class of the Status Application
public class Status {

	private DataInterface parkData;	//interface variable
	public ArrayList<AreaId> areaIDList = new ArrayList<AreaId>(); //areas Id List
	public ArrayList<SignId> signIDList = new ArrayList<SignId>(); //signs Id List	
	public ArrayList<SensorId> sensorIDList = new ArrayList<SensorId>(); //sensors Id List

	//constructor
	public Status() {
		
		parkData = new DataInterface();
	}
	
	//returns the root of the parking's data
	public Node<ParkingElement> getParkRoot() {
		
		return parkData.getRoot();
	}	
	
	//returns the areas Id array
	public ArrayList<AreaId> getAreaIdArray() {
    	
    	return areaIDList;
    }
	
	//returns the sensors Id array
	public ArrayList<SensorId> getSensorIdArray() {
    	
    	return sensorIDList;
    }
	
	//returns the signs Id array
	public ArrayList<SignId> getSignIdArray() {
    	
    	return signIDList;
    }

	//returns a parking element according to Id
	public ParkingElement getParkingElement(IdElement id) {
	    	
	    return parkData.getParkingElement(id);
	}
	
	//returns a parking element node according to Id
	public Node<ParkingElement> getParkingElementNode(IdElement id) {
    	
    	return parkData.getParkingElementNode(id);
    }
	
	//initialize the areas Id array with all the current areas in the parking lot
	public void initAreasArray( ) {
		
		parkData.getAreasOfParkingLot(areaIDList, parkData.getRoot());
	}
	
	//initialize the signs Id array with all the current areas in the parking lot
	public void initSignsArray( ) {
		
		parkData.getSignsOfParkingLot(signIDList, parkData.getRoot());
	}
	
	//initialize the sensors Id array with all the current areas in the parking lot
	public void initSensorsArray( ) {
		
		parkData.getSensorsOfParkingLot(sensorIDList, parkData.getRoot());
	}
	
	//returns the root of the data structure of the parking lot
    public ParkingElement getRootParkingElement( ) {
    		
    	return parkData.getRoot().getData();
    }

    //load data file of the data structure
	public void loadDataInterface() {
		 
		parkData.loadDataInterface();  
	}
	
	//get the data of the Data structure
	public ParkingManagerData getData() {
		 
	    return parkData.getData();
	}
}		 
	 
	
	

