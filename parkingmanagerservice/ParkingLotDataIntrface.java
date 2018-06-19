package parkingmanagerservice;


import java.util.Date;
import java.util.Vector;
//import java.util.Vector;



public class ParkingLotDataIntrface {

	private ParkingLotData ParkingLotData;
	
	public ParkingLotDataIntrface() {
		
		ParkingLotData = new ParkingLotData();
		
	}
	
	//DB Configuration functions
	
	void setGeneralType(int id, TypeData type) {   
		
		ParkingLotData.setType(id, type);		
	}

	ParkingLotData.Area createNewArea( int id, TypeData genT) {
	    	
		return ParkingLotData.createNewArea(id, genT);
	}
	
	ParkingLotData.Sign createNewSign( int id, TypeData genT) {
    	
	    return ParkingLotData.createNewSign(id, genT);
	}
	
	ParkingLotData.Spot createNewSpot( int id, TypeData genT) {
    	
	    return ParkingLotData.createNewSpot(id, genT);
	}
	
	void addObject(ParkingLotData.ConfigObj obj, int containingElementId) {
		
		ParkingLotData.addObject(obj, containingElementId);
	}
	
	void removeObject(ParkingLotData.ConfigObj obj, int containingElementId) {
	
		ParkingLotData.removeObject(obj, containingElementId);
	}

	void addSign(int signId, TypeData type, int containingElementId) { 
		
		ParkingLotData.addSign(signId, type, containingElementId);
	}
	
	void removeSignOrSpot(int Id, int containingElementId) {  
		
		ParkingLotData.removeSignOrSpot(Id, containingElementId);
	}
	
	void addParkingSpot(int spotId, TypeData type, int containingElementId) {
		
		ParkingLotData.addParkingSpot(spotId, type, containingElementId); 
	}
	
	void addArea (int areaId, TypeData type, int containingElementId) {
		
		ParkingLotData.addArea(areaId, type, containingElementId);
	}
	
	void removeArea(int areaId, int containingElementId) {
		
		ParkingLotData.removeArea(areaId, containingElementId);
	}
	
	int typeOfObject(ParkingLotData.ConfigObj obj) {
		
		return ParkingLotData.typeOfObject(obj);	
	}

	public Vector<ParkingLotData.ConfigObj> getConfigObjVectorForWatchdog() {

		return null;
	}
	
	
	//DB Status 
	
	void addStatusElementToDBStatus(int id, TypeData status, Date lastResponse, int counter) {
		
		ParkingLotData.addStatusElementToParkingLotDataStatus(id, status, lastResponse, counter);                    
	}
	
	void removeStatusElementFromDBStatus(int id ) {

		ParkingLotData.removeStatusElementFromParkingLotDataStatus(id);
	}	
	/*
	void removeStatusElementFromDBStatusAfterDeleteOfContaining(ParkingLotData.ConfigObj obj) {
		
		ParkingLotData.removeRecursivelyElementFromParkingDataStatus(obj);
	}*/
	
	void editStatusElementID(int oldID,int newID) {
		
		ParkingLotData.ParkingLotDataStatus.get(ParkingLotData.findTheIndexOfIDInParkingLotDataStatus(oldID)).editStatusElementID(oldID, newID);
	}
	
	void setStatusElementLastResponse(int id, Date lastResponse) {
		
		ParkingLotData.ParkingLotDataStatus.get(ParkingLotData.findTheIndexOfIDInParkingLotDataStatus(id)).setStatusElementLastResponse(id, lastResponse);
	}
	
	Date getStatusElementLastResponse(int id) {
		
		return ParkingLotData.ParkingLotDataStatus.get(ParkingLotData.findTheIndexOfIDInParkingLotDataStatus(id)).getStatusElementLastResponse(id);
	}
	
	void setStatusElement(int id, TypeData status) {
			
		ParkingLotData.ParkingLotDataStatus.get(ParkingLotData.findTheIndexOfIDInParkingLotDataStatus(id)).setStatusElement(id, status);
	}
	 
	TypeData getStatusElement(int id) {
			
		return ParkingLotData.ParkingLotDataStatus.get(ParkingLotData.findTheIndexOfIDInParkingLotDataStatus(id)).getStatusElement(id);
	}
	
	void setNewCounter(int id, int newCounter) {
		
		ParkingLotData.ParkingLotDataStatus.get(ParkingLotData.findTheIndexOfIDInParkingLotDataStatus(id)).setNewCounter(id, newCounter);
	}
	
	void changeCounter(int id, int flag) {
		
		ParkingLotData.ParkingLotDataStatus.get(ParkingLotData.findTheIndexOfIDInParkingLotDataStatus(id)).changeCounter(id, flag);
	}
	
	void changeSignCounters(int id, int flag) {
		
		ParkingLotData.changeSignCounters(id, flag);
	}
	
	
	
	
}