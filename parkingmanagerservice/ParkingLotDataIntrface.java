
import java.util.Date;
//import java.util.Vector;



public class ParkingLotDataIntrface {

	private ParkingLotData ParkingLotData;
	
	public ParkingLotDataIntrface() {
		
		ParkingLotData = new ParkingLotData();
		
	}
	
	//DB Configuration functions
	
	void setGeneralType(int id, Type type) {   
		
		ParkingLotData.setType(id, type);		
	}

	ParkingLotData.Area createNewArea( int id, Type genT) {
	    	
		return ParkingLotData.createNewArea(id, genT);
	}
	
	ParkingLotData.Sign createNewSign( int id, Type genT) {
    	
	    return ParkingLotData.createNewSign(id, genT);
	}
	
	ParkingLotData.Spot createNewSpot( int id, Type genT) {
    	
	    return ParkingLotData.createNewSpot(id, genT);
	}
	
	void addObject(ParkingLotData.ConfigObj obj, int containingElementId) {
		
		ParkingLotData.addObject(obj, containingElementId);
	}
	
	void removeObject(ParkingLotData.ConfigObj obj, int containingElementId) {
	
		ParkingLotData.removeObject(obj, containingElementId);
	}

	void addSign(int signId, Type type, int containingElementId) { 
		
		ParkingLotData.addSign(signId, type, containingElementId);
	}
	
	void removeSignOrSpot(int Id, int containingElementId) {  
		
		ParkingLotData.removeSignOrSpot(Id, containingElementId);
	}
	
	void addParkingSpot(int spotId, Type type, int containingElementId) {
		
		ParkingLotData.addParkingSpot(spotId, type, containingElementId); 
	}
	
	void addArea (int areaId, Type type, int containingElementId) {
		
		ParkingLotData.addArea(areaId, type, containingElementId);
	}
	
	void removeArea(int areaId, int containingElementId) {
		
		ParkingLotData.removeArea(areaId, containingElementId);
	}
	
	int typeOfObject(ParkingLotData.ConfigObj obj) {
		
		return ParkingLotData.typeOfObject(obj);	
	}
	
	
	//DB Status 
	
	void addStatusElementToDBStatus(int id, Type status, Date lastResponse, int counter) {
		
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
	
	void setStatusElement(int id, Type status) {
			
		ParkingLotData.ParkingLotDataStatus.get(ParkingLotData.findTheIndexOfIDInParkingLotDataStatus(id)).setStatusElement(id, status);
	}
	 
	Type getStatusElement(int id) {
			
		return ParkingLotData.ParkingLotDataStatus.get(ParkingLotData.findTheIndexOfIDInParkingLotDataStatus(id)).getStatusElement(id);
	}
	
	void setNewCounter(int id, int newCounter) {
		
		ParkingLotData.ParkingLotDataStatus.get(ParkingLotData.findTheIndexOfIDInParkingLotDataStatus(id)).setNewCounter(id, newCounter);
	}
	
	void changeCounter(int id, int flag) {
		
		ParkingLotData.ParkingLotDataStatus.get(ParkingLotData.findTheIndexOfIDInParkingLotDataStatus(id)).changeCounter(id, flag);
	}
	
	void increaseSignCounters(int id, int flag) {
		
		ParkingLotData.increaseSignCounters(id, flag);
	}
	
	
	
	public static void main(String args[]) {
		
		ParkingLotDataIntrface newPark = new ParkingLotDataIntrface();
		newPark.addArea(1,Type.OPEN, 0);
		//System.out.println("Area was added by admin");
		newPark.addArea(2,Type.OPEN, 0);
	//	System.out.println("Area was added by admin");
		//Level 1
		newPark.addSign(10, Type.RIGHT, 1);
		//System.out.println("Sign was added by admin");
		Date date = new Date();
		newPark.addStatusElementToDBStatus(10, Type.OK, date, 4);
		newPark.addArea(3,Type.OPEN, 1);
		//System.out.println("Area was added by admin");
		newPark.addArea(4,Type.OPEN, 1);
		//System.out.println("Area was added by admin");
		
		//Level 1, area 3
		
		newPark.addSign(11, Type.LEFT, 3);
		//System.out.println("Sign was added by admin");
		newPark.addStatusElementToDBStatus(11, Type.OK, date, 2);
		newPark.addParkingSpot(20, Type.REGULAR, 3);
		//System.out.println("Parking spot was added by admin");
		newPark.addStatusElementToDBStatus(20, Type.FREE, date, 0);
		newPark.addParkingSpot(21, Type.REGULAR, 3);
	//	System.out.println("Parking spot was added by admin");
		newPark.addStatusElementToDBStatus(21, Type.FREE, date, 0);
		
		//Level 1, area 4
		
		newPark.addSign(12, Type.RIGHT, 4);
	//	System.out.println("Sign was added by admin");
		newPark.addStatusElementToDBStatus(12, Type.OK, date, 2);
		newPark.addParkingSpot(22, Type.REGULAR, 4);
		//System.out.println("Parking spot was added by admin");
		newPark.addStatusElementToDBStatus(22, Type.FREE, date, 0);
		newPark.addParkingSpot(23, Type.REGULAR, 4);
		//System.out.println("Parking spot was added by admin");
		newPark.addStatusElementToDBStatus(23, Type.FREE, date, 0);
		
	
		
		//Level 2
		
		newPark.addSign(13, Type.RIGHT, 2);
		//System.out.println("Sign was added by admin");
		Date date2 = new Date();
		newPark.addStatusElementToDBStatus(13, Type.OK, date2, 1);
		newPark.addArea(5,Type.OPEN, 2);
		//System.out.println("Area was added by admin");
		
	
		//Level 2, area 5
		
		newPark.addSign(14, Type.LEFT, 2);
		//System.out.println("Sign was added by admin");
		newPark.addStatusElementToDBStatus(14, Type.OK, date, 1);
		newPark.addParkingSpot(24, Type.REGULAR, 5);
		//System.out.println("Parking spot was added by admin");
		newPark.addStatusElementToDBStatus(24, Type.FREE, date, 0);
		
		
		/*
		for (ParkingLotData.StatusElementObj item : newPark.ParkingLotData.ParkingLotDataStatus) {
			
			System.out.println(item.getID());		
		}
		*/
		newPark.setStatusElement(20, Type.TAKEN);
		
		newPark.increaseSignCounters(20, 1);
		System.out.println(newPark.ParkingLotData.ParkingLotDataStatus.get(0).getCounter());
		//update counters
		newPark.setStatusElement(24, Type.TAKEN);
		//update counters
		newPark.setStatusElement(21, Type.TAKEN);
		newPark.increaseSignCounters(21, 1);
		System.out.println(newPark.ParkingLotData.ParkingLotDataStatus.get(0).getCounter());
		System.out.println(newPark.ParkingLotData.ParkingLotDataStatus.get(1).getCounter());
		//update counters
		newPark.setStatusElement(20, Type.FREE);
		//update counters
		//System.out.println(newPark.ParkingLotData.ParkingLotDataConfiguration.get(0).Areas.get(1).Areas.get(1).getParent().getID());
		//newPark.removeArea(3, 1);
		
		//newPark.setGeneralType(20, Type.VIP);
		//System.out.println(newPark.ParkingLotData.ParkingLotDataConfiguration.get(0).Areas.get(1).Areas.get(1).getGeneralType());
		
		//System.out.println(newPark.ParkingLotData.ParkingLotDataConfiguration.get(0).Areas.get(1).Areas.get(1).getID());
		//System.out.println(newPark.ParkingLotData.ParkingLotDataConfiguration.get(1).Areas.get(1).getGeneralType());
		//newPark.removeSignOrSpot(20, 3);
		
		//newPark.setNewCounter(10, 2);
		
		
		/*
		for (ParkingLotData.StatusElementObj item : newPark.ParkingLotData.ParkingLotDataStatus) {
			
			System.out.println(item.getID());	
			
		}
		/*
		newPark.addSign(5,Type.NO_ENTRY,3);
		newPark.addParkingSpot(6,Type.GUEST,3);
		newPark.addArea(7,Type.ADMIN_LOGOUT, 3);
		newPark.addSign(8,Type.NO_ENTRY,7);
		newPark.addSign(9,Type.NO_ENTRY,7);
		newPark.addSign(4,Type.NO_ENTRY,0);
		Date newdate = new Date();
		ParkingLotData.StatusElementObj elementp= newPark.createNewObjectToStatus(5, Type.OPEN, newdate, 300);
		newPark.addStatusElementToDBStatus(elementp);
		
	
		
		ParkingLotData.StatusElementObj elementb= newPark.createNewObjectToStatus(6, Type.FREE, newdate, 0);
		newPark.addStatusElementToDBStatus(elementb);
		
		elementp.decCounter();
		
		for (ParkingLotData.StatusElementObj item : newPark.ParkingLotData.ParkingLotDataStatus) {
			
			System.out.println(item.getID());	
			System.out.println(item.getCounter());
			System.out.println(item.getLastResponse());
			System.out.println(item.getStatus());
		}

		
		newPark.removeArea(7,3);
		
		
		newPark.removeSignOrSpot(5,3);
		
		for (ParkingLotData.StatusElementObj item : newPark.ParkingLotData.ParkingLotDataStatus) {
			
			System.out.println(item.getID());	
			System.out.println(item.getCounter());
			System.out.println(item.getLastResponse());
			System.out.println(item.getStatus());
		}
		
		newPark.removeSignOrSpot(6,3);
		*/
	}
}