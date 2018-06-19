package parkingmanagerservice;

import java.util.Date;
import java.util.Vector;


enum TypeData {
	OPEN, CLOSED,											//open-closed status 
	REGULAR, VIP, GUEST, DISABLED, VIRTUAL,                 //Parking spot type
	OK, ERROR,											    //generic status
	FREE, TAKEN,  								            //sensor status
	NO_ENTRY, LEFT, RIGHT, FORWARD, BACK,				    //sign arrow
	PARKING_ADDED, PARKING_REMOVED, PARKING_TYPE_MODIFIED,  //admins logs event
	SIGN_ADDED ,SIGN_REMOVED ,AREA_ADDED ,AREA_REMOVED,
    LEVEL_ADDED , LEVEL_REMOVED , ADMIN_LOGIN , ADMIN_LOGOUT,
    USER_LOGIN , USER_LOGOUT,
	PARKING_FREED, PARKING_CAUGHT, PARKING_FULL;			//Parking logs event
}


public class ParkingLotData {
	
	public Vector<ConfigObj> ParkingLotDataConfiguration;   // why is this public?
	public Vector<StatusElementObj> ParkingLotDataStatus;   // why is this public?
	
	public ParkingLotData() {
		
		ParkingLotDataConfiguration = new Vector<ConfigObj>(0,1);
		ParkingLotDataStatus = new Vector<StatusElementObj>(0,1);	
	}
	
	public abstract class ConfigObj {                       // why abstract if no abstract methods?
		
		protected int ID;                                   // why protected? why do we need "sons of" accessing father?
		protected TypeData GeneralType;                     // better to put it inside an object element
		protected ConfigObj Parent;
		protected Vector<ConfigObj> Areas = new Vector<ConfigObj>(0,1);     // better name: treeNodes
		
		public ConfigObj() {                                  
			
		}
		void setID(int id) {
			
			ID = id;
		}
		
		int getID() {
			
			return ID;
		}
		
		void setGeneralType(TypeData genType) {
			
			GeneralType = genType;
		}
		
		TypeData getGeneralType() {
			
			return GeneralType;
		}
		
		void setParent (int parent) {
			
			Parent = findAnObject(ParkingLotDataConfiguration, parent);  // ParkingLotDataConfiguration is public, why do we need reference?
		}
		
		ConfigObj getParent () {
			
			return Parent;	
		}
	}	

	public class Area extends ConfigObj {
			
		public Area (int id, TypeData genT) { // what about setting the parent??
			
			Areas = new Vector<ConfigObj> (0,1);
			setID(id);
			setGeneralType(genT);
		}	
	}
		
	public class Sign extends ConfigObj {    // where are all the inner methods?
			
		public Sign(int id, TypeData genT) { // what about setting the parent??
			setID(id);
			setGeneralType(genT);	
		}
	}
		
	public class Spot extends ConfigObj { // what about setting the parent??
			
		public Spot(int id, TypeData genT) {
			setID(id);
			setGeneralType(genT);	
		}
	}
		
	public class StatusElementObj {
		
		protected int ID;
		protected TypeData Status;
		protected Date LastResponse;
		protected int Counter;
		
		public StatusElementObj(int id, TypeData status, Date lastResponse, int counter) {
			    	
			ID = id;
			Status = status;
			LastResponse = lastResponse;
			Counter = counter;	
		}
		
		public void setID(int id) {
			
			ID = id;
		}
		
		public int getID() {
			
			return ID;
		}
		
		public void setStatus(TypeData status) {
			
			Status = status;
		}
		
		public TypeData getStatus() {
			
			return Status;
		}
		
		public void setLastResponse(Date lastResponse) {
			
			LastResponse = lastResponse;
		}
		
		public Date getLastResponse() {
			
			return LastResponse;
		}
		
		public void setCounter(int counter) {
			
			Counter = counter;	
		}
		
		public int getCounter() {
			
			return Counter;
		}

		public void incCounter() {
			
			Counter++;
		}

		public void decCounter() {
	
			Counter--;
		}
		
		public void setNewCounter(int id, int newCounter) {
			
			ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(id)).setCounter(newCounter);
		}
		
		public void changeCounter(int id, int flag) {
			
			if (flag == 0) {
				ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(id)).decCounter();
			}
			else {
				ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(id)).incCounter();
			}
		}
		
		public void editStatusElementID(int oldID,int newID) {
			
			ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(oldID)).setID(newID);
		}
		
		
		public void setStatusElementLastResponse(int id, Date lastResponse) {
			
			ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(id)).setLastResponse(lastResponse);
		}
		
		public Date getStatusElementLastResponse(int id) {
			
			return ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(id)).getLastResponse();
		}
		
		public void setStatusElement(int id, TypeData status) {
			
			ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(id)).setStatus(status);
			
		}
		
		public TypeData getStatusElement(int id) {
			
			return ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(id)).getStatus();
		}
		
		
	}	
	
	//DB Configuration functions
	
	public Area createNewArea(int id, TypeData genT) {
		
		Area newArea = new Area(id, genT);
		return newArea;
	}
	
	public Sign createNewSign(int id, TypeData genT) {
		
		Sign newSign = new Sign(id, genT);
		return newSign;
	}
	
	public Spot createNewSpot(int id, TypeData genT) {
		
		Spot newSpot = new Spot(id, genT);
		return newSpot;
	}
	
	void addObject(ParkingLotData.ConfigObj obj, int containingElementId) {
		
		if (containingElementId == 0) {
			ParkingLotDataConfiguration.addElement(obj);
		}
		else {
			(findAnObject(ParkingLotDataConfiguration, containingElementId)).Areas.addElement(obj); // NOT checking if null pointer! runtime error!
	
		}
	}	
	
	ConfigObj findAnObject(Vector<ParkingLotData.ConfigObj> v, int id ){
		
		for (ParkingLotData.ConfigObj item: v) {
			if (item.getID()==id) {
				return item;
			}
			ConfigObj temp = findAnObject(item.Areas, id);
			if (temp != null) {
				return temp;
			}
		}
		return null;	
	}
	
	void changeSignCounters(int id, int flag) {     // writing messages from here is not good, also should be in Interface
		
		
		ParkingLotData.ConfigObj temp = findAnObject(ParkingLotDataConfiguration, id);
		ParkingLotData.ConfigObj parent = temp.Parent;
		while (parent!= null) {
			
			for (ParkingLotData.ConfigObj item : parent.Areas) {
				
				if (item instanceof Sign) {
					Date date = new Date();
					MessageType signArrow = messageTypeToTypeData(item.getGeneralType());
					if (flag == 0) {
						ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(item.getID())).incCounter();				
					}
					else if (ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(item.getID())).getCounter() > 0){
						ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(item.getID())).decCounter();
					}
					if ((ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(item.getID())).getCounter() > 0)) {
						messages updateCounterWithDefaultArrow = new messages(item.getID(), date, signArrow, ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(item.getID())).getCounter());
						ParkingManagerService.Threads.SenderQueueThread.addMessage(updateCounterWithDefaultArrow);
					}
					else {
						messages updateCounterWithNoEntry = new messages(item.getID(), date, MessageType.SET_SIGN_NO_ENTRY, ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(item.getID())).getCounter());
						ParkingManagerService.Threads.SenderQueueThread.addMessage(updateCounterWithNoEntry);
					}
				}
			}
			parent = parent.getParent();
		}	
	}

	int findIndexInParkingLotDataConfiguration(Vector<ParkingLotData.ConfigObj> v, int id) {
		
		int index = -1;
		for (ParkingLotData.ConfigObj item : v) {
			if (item.getID()==id) {
				index = v.indexOf(item);
				break;
			}
		}
		return index;
	}
	
	void setType(int id, TypeData type) {    
		(findAnObject(ParkingLotDataConfiguration, id)).setGeneralType(type);		
	}
	
	void removeObject(ParkingLotData.ConfigObj obj, int containingElementId) {
		
		removeRecursivelyElementFromParkingDataStatus((findAnObject(ParkingLotDataConfiguration, containingElementId)) ,obj.getID());
		(findAnObject(ParkingLotDataConfiguration, containingElementId)).Areas.removeElementAt(findIndexInParkingLotDataConfiguration((findAnObject(ParkingLotDataConfiguration, containingElementId)).Areas,obj.getID())); 
	}
	
	void addSign(int signId, TypeData type, int containingElementId) { 
		
		Sign newSign = createNewSign(signId, type);
		if (containingElementId == 0) {
			ParkingLotDataConfiguration.addElement(newSign);
		}
		else {
			
			(findAnObject(ParkingLotDataConfiguration, containingElementId)).Areas.addElement(newSign); 
		}	
		newSign.setParent(containingElementId);
	}
	
	void removeSignOrSpot(int Id, int containingElementId) {  
		
		removeStatusElementFromParkingLotDataStatus(Id); 
		(findAnObject(ParkingLotDataConfiguration, containingElementId)).Areas.removeElementAt(findIndexInParkingLotDataConfiguration((findAnObject(ParkingLotDataConfiguration, containingElementId)).Areas,Id));
	}
	
	void addParkingSpot(int spotId, TypeData type, int containingElementId) { 
		
		Spot newSpot = createNewSpot(spotId, type);
		(findAnObject(ParkingLotDataConfiguration, containingElementId)).Areas.addElement(newSpot); 
		newSpot.setParent(containingElementId);
	}
	
	void addArea (int areaId, TypeData type, int containingElementId) {
		
		Area newArea = createNewArea(areaId, type);
		if (containingElementId == 0) {
			ParkingLotDataConfiguration.addElement(newArea);
		}
		else {
			(findAnObject(ParkingLotDataConfiguration, containingElementId)).Areas.addElement(newArea); 
		}
		newArea.setParent(containingElementId);
	}
	
	void removeArea(int Id, int containingElementId) {  
		
		removeRecursivelyElementFromParkingDataStatus((findAnObject(ParkingLotDataConfiguration, containingElementId)),Id);
		(findAnObject(ParkingLotDataConfiguration, containingElementId)).Areas.removeElementAt(findIndexInParkingLotDataConfiguration((findAnObject(ParkingLotDataConfiguration, containingElementId)).Areas,Id));
	}
	
	int typeOfObject(ParkingLotData.ConfigObj obj) { // better use enums
		
		if(obj instanceof Area) {
			return 1;
		}
		else if(obj instanceof Sign) {
			return 2;
		}
		else if(obj instanceof Spot) {
			return 3;
		}
		return 0;
	}

	//ParkingLotData Status Functions
	
	public StatusElementObj createNewStatusElement(int id, TypeData status, Date lastResponse, int counter) {
		
		StatusElementObj newElement = new StatusElementObj(id, status, lastResponse, counter);
		return newElement;
	}
	
	public void addStatusElementToParkingLotDataStatus( int id, TypeData status, Date lastResponse, int counter ) {
		
		StatusElementObj newElement = createNewStatusElement(id, status, lastResponse, counter);
		ParkingLotDataStatus.addElement(newElement);
	}
	
	public void removeStatusElementFromParkingLotDataStatus(int id) {
		
		int index = findTheIndexOfIDInParkingLotDataStatus(id);
		if (index != -1) {	
			ParkingLotDataStatus.removeElementAt(index);
		}
		
	}
	
	public int findTheIndexOfIDInParkingLotDataStatus(int id) {
		
		for (ParkingLotData.StatusElementObj item : ParkingLotDataStatus) {
			if (item.getID() == id) {
				return ParkingLotDataStatus.indexOf(item);
			}
		}
		return -1;
	}
	
	void removeRecursivelyElementFromParkingDataStatus(ParkingLotData.ConfigObj obj, int Id) {
			
		for (ConfigObj item: obj.Areas.get(findIndexInParkingLotDataConfiguration(obj.Areas, Id)).Areas) {
			if ((item instanceof Sign) || (item instanceof Spot) ) {
				if (findTheIndexOfIDInParkingLotDataStatus(item.getID()) != -1) {
					removeStatusElementFromParkingLotDataStatus(item.getID());
				}
			}
			else if(!item.Areas.isEmpty()) {
				removeRecursivelyElementFromParkingDataStatus(item, Id);
			}
		}	
	}
	
	public MessageType messageTypeToTypeData (TypeData type) {
		
		if (type == TypeData.LEFT) {
			return MessageType.SET_SIGN_LEFT;
		}
		else if (type == TypeData.RIGHT) {
			return MessageType.SET_SIGN_RIGHT;
		}
		else if(type == TypeData.FORWARD) {
			return MessageType.SET_SIGN_FORWARD;
		}
		else if(type == TypeData.BACK) {
			return MessageType.SET_SIGN_BACK;
		}
		else {
			return MessageType.SET_SIGN_NO_ENTRY;
		}		
	}
	
}
