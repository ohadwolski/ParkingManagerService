
import java.util.Date;
//import java.util.Vector;

public class DBIntrface {

	private DB DB;
	
	public DBIntrface() {
		
		DB = new DB();
		
	}
	
	
	void setGeneralType(int id, Type symbol) {    //same as parktype
		
		DB.DBConfiguration.elementAt(id).setGeneralType(symbol);		
	}
	/*
	void setParkType(int id, Type type) {
		
		DB.DBConfiguration.elementAt(id).setGeneralType(type);		
	}*/
	
	void addSign(int signId, Type type, int containingElementId) { //same as add spot
		
		DB.Sign newSign = new DB.Sign(signId, type);
		((DB.Area) DB.DBConfiguration.get(containingElementId)).Areas.insertElementAt(newSign, signId); 
	}
	
	void removeSign(int signId, int containingElementId) {  //same as remove spot
		
		((DB.Area) DB.DBConfiguration.get(containingElementId)).Areas.removeElementAt(signId);
	}
	
	void addParkingSpot(int spotId, Type type, int containingElementId) {
		
		DB.Spot newSpot = new DB.Spot(spotId, type);
		((DB.Area) DB.DBConfiguration.get(containingElementId)).Areas.insertElementAt(newSpot, spotId);
	}
	
	void removeSpot(int spotId, int containingElementId) {
		
		((DB.Area) DB.DBConfiguration.get(containingElementId)).Areas.removeElementAt(spotId);
	}
	
	void addArea (int areaId, int containingElementId) {
		
		DB.Area newArea = new DB.Area(areaId, Type.OPEN);
		((DB.Area) DB.DBConfiguration.get(containingElementId)).Areas.insertElementAt(newArea, areaId);
	}
	
	void removeArea(int areaId, int containingElementId) {
		
		((DB.Area) DB.DBConfiguration.get(containingElementId)).Areas.removeElementAt(areaId);
	}
	
	
	//DB Status 
	
	
	void addSignToDBStatus(int signId, Type Status, Date date) { 
		
		DB.SignStatus newSign = new DB.SignStatus(signId, Status, date);
		DB.DBStatus.insertElementAt(newSign, signId);
	}
	
	void addspotToDBStatus(int spotId, Type Status, Date date) { 
		
		DB.SpotStatus newSign = new DB.SpotStatus(spotId, Status, date);
		DB.DBStatus.insertElementAt(newSign, spotId);
	}
	
	void removeElementFromDBStatus(int Id) {
		
		DB.DBStatus.remove(Id);
	}
	
	void setParkFree(int id) {
		
		DB.DBStatus.get(id).Status = Type.FREE;
	}
	
	void setParkTaken(int id) {
		
		DB.DBStatus.get(id).Status = Type.TAKEN;
	}
	
	void setSignOKStatus(int id) {
		
		DB.DBStatus.get(id).Status = Type.OK;
	}
	
	void setSignErrorStatus(int id) {
		
		DB.DBStatus.get(id).Status = Type.ERROR;
	}
	
	Type getElementStatus(int id) {
		
		return DB.DBStatus.get(id).Status;
	}
	
	void setElementLastResponse(int id, Date date) {
		
		DB.DBStatus.get(id).LastResponse = date;
	}
	
	Date getElementLastResponse(int id) {
		
		return DB.DBStatus.get(id).LastResponse;
	}
	

}
