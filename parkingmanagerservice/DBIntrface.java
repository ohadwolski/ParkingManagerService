
import java.util.Date;
import java.util.Vector;
//import java.util.Vector;

public class DBIntrface {

	private DB DB;
	
	public DBIntrface() {
		
		DB = new DB();
		
	}
	
	void setGeneralType(int id, Type type) {    //same as parktype
		
		(findAnObject(DB.DBConfiguration, id)).setGeneralType(type);		
	}
	/*
	void setParkType(int id, Type type) {
		
		DB.DBConfiguration.elementAt(id).setGeneralType(type);		
	}*/
	
	void addObject(DB.ConfigObj obj, int containingElementId) {
		
		int index = findIndex((findAnObject(DB.DBConfiguration, containingElementId)).Areas,obj.getID());
		(findAnObject(DB.DBConfiguration, containingElementId)).Areas.insertElementAt(obj, index); 
	}
	
	void removeObject(DB.ConfigObj obj, int containingElementId) {
	
		removeStatusElementFromDBStatusAfterDeleteOfContaining((findAnObject(DB.DBConfiguration, containingElementId)));
		(findAnObject(DB.DBConfiguration, containingElementId)).Areas.removeElementAt(obj.getID()); 
	}

	void addSign(int signId, Type type, int containingElementId) { //same as add spot
		
		DB.Sign newSign = new DB.Sign(signId, type);
		int index = findIndex((findAnObject(DB.DBConfiguration, containingElementId)).Areas,newSign.getID());
		(findAnObject(DB.DBConfiguration, containingElementId)).Areas.insertElementAt(newSign, index); 
	}
	
	void removeSign(int signId, int containingElementId) {  //same as remove spot
		
		(findAnObject(DB.DBConfiguration, containingElementId)).Areas.removeElementAt(signId);
	}
	
	void addParkingSpot(int spotId, Type type, int containingElementId) {
		
		DB.Spot newSpot = new DB.Spot(spotId, type);
		int index = findIndex((findAnObject(DB.DBConfiguration, containingElementId)).Areas,newSpot.getID());
		(findAnObject(DB.DBConfiguration, containingElementId)).Areas.insertElementAt(newSpot, index);
	}
	
	void removeSpot(int spotId, int containingElementId) {
		
		(findAnObject(DB.DBConfiguration, containingElementId)).Areas.removeElementAt(spotId);
	}
	
	void addArea (int areaId, int containingElementId) {
		
		DB.Area newArea = new DB.Area(areaId, Type.OPEN);
		int index = findIndex((findAnObject(DB.DBConfiguration, containingElementId)).Areas,newArea.getID());
		(findAnObject(DB.DBConfiguration, containingElementId)).Areas.insertElementAt(newArea, index);
	}
	
	void removeArea(int areaId, int containingElementId) {
		
		(findAnObject(DB.DBConfiguration, containingElementId)).Areas.removeElementAt(areaId);
	}
	
	int whatKindOfObject(DB.ConfigObj obj) {
		
		if(obj instanceof DB.Area) {
			
			return 1;
		}
		else if(obj instanceof DB.Sign) {
			
			return 2;
		}
		else if(obj instanceof DB.Spot) {
			
			return 3;
		}
		return 0;
		
	}
	
	int findIndex(Vector<DB.ConfigObj> v,int id) {
		
		int index = 0;
		for (DB.ConfigObj item : v) {
			if (item.getID()>id) {
				index = v.indexOf(item);
				break;
			}
		}
		return index;
	}
	
	
	
	DB.ConfigObj findAnObject(Vector<DB.ConfigObj> v, int id ){
		
		
		for (DB.ConfigObj item: v) {
			
			if (item.getID()==id) {
				
				return item;
			}
			else if(!item.Areas.isEmpty()) {
				
				return findAnObject(item.Areas, id);
			}
			
		}
		
		return null;
	}
		
	
	
	
	//DB Status 
	
	int findIndexToInsertInDBStatus(int id) {
			
		int index = 0;
		for (DB.StatusElementObj item : DB.DBStatus) {
			if (item.getID()>id) {
				index = DB.DBStatus.indexOf(item);
				break;
			}
		}
		return index;
	}
	
	int findTheIndexOfID(int id) {
		
		for (DB.StatusElementObj item : DB.DBStatus) {
			if (item.getID() == id) {
				return DB.DBStatus.indexOf(item);
			}
		}
		return -1;
	}
	
	void addStatusElementToDBStatus(DB.StatusElementObj obj ) {
		
		int index = findIndexToInsertInDBStatus(obj.getID());
		DB.DBStatus.insertElementAt(obj, index);
	}
	
	
	void removeStatusElementFromDBStatus(int id ) {

		DB.DBStatus.remove(findTheIndexOfID(id));
			
	}	
	
	void removeStatusElementFromDBStatusAfterDeleteOfContaining(DB.ConfigObj obj) {
		
		for (DB.ConfigObj item: obj.Areas) {
			
			if ((item instanceof DB.Sign) || (item instanceof DB.Spot) ) {
				
				removeStatusElementFromDBStatus(item.getID());
			}
			else if(!item.Areas.isEmpty()) {
				
				removeStatusElementFromDBStatusAfterDeleteOfContaining(item);
			}
		}	
	
	}
	
	void editStatusElementID(int oldID,int newID) {
		
		DB.DBStatus.get(findTheIndexOfID(oldID)).setID(newID);
	}
	
	void setStatusElementLastResponse(int id, Date lastResponse) {
		
		DB.DBStatus.get(findTheIndexOfID(id)).setLastResponse(lastResponse);
	}
	
	Date getStatusElementLastResponse(int id) {
		
		return DB.DBStatus.get(findTheIndexOfID(id)).getLastResponse();
	}
	
	void setStatusElement(int id, Type status) {
			
		DB.DBStatus.get(findTheIndexOfID(id)).setStatus(status);;
	}
	 
	Type getStatusElement(int id) {
			
		return DB.DBStatus.get(findTheIndexOfID(id)).getStatus();
	}
	
	void setNewCounter(int id, int newCounter) {
		
		DB.DBStatus.get(findTheIndexOfID(id)).setCounter(newCounter);
	}
	
	void changeCounter(int id, int flag) {
		
		if (flag == 0) {
			
			DB.DBStatus.get(findTheIndexOfID(id)).decCounter();
		}
		else {
			
			DB.DBStatus.get(findTheIndexOfID(id)).incCounter();
		}
	}
	
	
	 
		
	
	
	
	
	/*
	void addSignToDBStatus(int signId, Type Status, Date date) {            //same as spot?
		
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
	
	
	void setStatus(int id, Type status) {
		
		
		
	}
	
	
	/*
	void setParkFree(int id) {          //same as taken
		
		DB.DBStatus.get(id).Status = Type.FREE;
	}
	
	void setParkTaken(int id) {
		
		DB.DBStatus.get(id).Status = Type.TAKEN;
	}
	
	void setSignOKStatus(int id) {      //same as error
		
		DB.DBStatus.get(id).Status = Type.OK;
	}
	*/
	/*
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
	*/

}