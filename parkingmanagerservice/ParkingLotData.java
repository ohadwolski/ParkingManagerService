import java.util.Date;
import java.util.Vector;


enum Type {
	OPEN, CLOSED,											//open-closed status 
	REGULAR, VIP, GUEST, DISABLED, VIRTUAL,               //Parking spot type
	OK, ERROR,											//generic status				
	FREE, TAKEN,  								//sensor status
	NO_ENTRY, STOP, LEFT, RIGHT, UP, DOWN,				//sign arrow
	PARKING_ADDED, PARKING_REMOVED, PARKING_TYPE_MODIFIED ,SIGN_ADDED ,SIGN_REMOVED ,AREA_ADDED ,AREA_REMOVED ,LEVEL_ADDED , LEVEL_REMOVED , ADMIN_LOGIN , ADMIN_LOGOUT, USER_LOGIN , USER_LOGOUT, //admins logs event
	PARKING_FREED, PARKING_CAUGHT, PARKING_FULL;			//Parking logs event
}


public class ParkingLotData {
	
	public Vector<ConfigObj> ParkingLotDataConfiguration;
	public Vector<StatusElementObj> ParkingLotDataStatus;
	
	public ParkingLotData() {
		
		ParkingLotDataConfiguration = new Vector<ConfigObj>(0,1);
		ParkingLotDataStatus = new Vector<StatusElementObj>(0,1);	
	}
	
	public abstract class ConfigObj {								
		
		protected int ID;
		protected Type GeneralType;
		protected ConfigObj Parent;
		protected Vector<ConfigObj> Areas = new Vector<ConfigObj>(0,1);
		
		public ConfigObj() {                                  
			
		}
		void setID(int id) {
			
			ID = id;
		}
		
		int getID() {
			
			return ID;
		}
		
		void setGeneralType(Type genType) {
			
			GeneralType = genType;
		}
		
		Type getGeneralType() {
			
			return GeneralType;
		}
		
		void setParent (int parent) {
			
			Parent = findAnObject(ParkingLotDataConfiguration, parent);
		}
		
		ConfigObj getParent () {
			
			return Parent;	
		}
	}	

	public class Area extends ConfigObj {
			
		public Area (int id, Type genT) {
			
			Areas = new Vector<ConfigObj> (0,1);
			setID(id);
			setGeneralType(genT);
		}	
	}
		
	public class Sign extends ConfigObj {                       
			
		public Sign(int id, Type genT) {
			setID(id);
			setGeneralType(genT);	
		}
	}
		
	public class Spot extends ConfigObj {
			
		public Spot(int id, Type genT) {
			setID(id);
			setGeneralType(genT);	
		}
	}
		
	public class StatusElementObj {
		
		protected int ID;
		protected Type Status;
		protected Date LastResponse;
		protected int Counter;
		
		
		public StatusElementObj(int id, Type status, Date lastResponse, int counter) {
			    	
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
		
		public void setStatus(Type status) {
			
			Status = status;
		}
		
		public Type getStatus() {
			
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
		
		public void setStatusElement(int id, Type status) {
			
			ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(id)).setStatus(status);
			
		}
		
		public Type getStatusElement(int id) {
			
			return ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(id)).getStatus();
		}
		
		
	}	
	
	//DB Configuration functions
	
	public Area createNewArea(int id, Type genT) {
		
		Area newArea = new Area(id, genT);
		return newArea;
	}
	
	public Sign createNewSign(int id, Type genT) {
		
		Sign newSign = new Sign(id, genT);
		return newSign;
	}
	
	public Spot createNewSpot(int id, Type genT) {
		
		Spot newSpot = new Spot(id, genT);
		return newSpot;
	}
	
	void addObject(ParkingLotData.ConfigObj obj, int containingElementId) {
		
		if (containingElementId == 0) {
			ParkingLotDataConfiguration.addElement(obj);
		}
		else {
			(findAnObject(ParkingLotDataConfiguration, containingElementId)).Areas.addElement(obj); 
	
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
	
	void increaseSignCounters(int id, int flag) {
		
		ParkingLotData.ConfigObj temp = findAnObject(ParkingLotDataConfiguration, id);
		ParkingLotData.ConfigObj parent = temp.Parent;
		while (parent!= null) {
			
			for (ParkingLotData.ConfigObj item : parent.Areas) {
				
				if (item instanceof Sign) {
					
					if (flag == 0) {
						
						ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(item.getID())).incCounter();
					}
					else {
						ParkingLotDataStatus.get(findTheIndexOfIDInParkingLotDataStatus(item.getID())).decCounter();
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
	
	void setType(int id, Type type) {    
		(findAnObject(ParkingLotDataConfiguration, id)).setGeneralType(type);		
	}
	
	void removeObject(ParkingLotData.ConfigObj obj, int containingElementId) {
		
		removeRecursivelyElementFromParkingDataStatus((findAnObject(ParkingLotDataConfiguration, containingElementId)) ,obj.getID());
		(findAnObject(ParkingLotDataConfiguration, containingElementId)).Areas.removeElementAt(findIndexInParkingLotDataConfiguration((findAnObject(ParkingLotDataConfiguration, containingElementId)).Areas,obj.getID())); 
	}
	
	void addSign(int signId, Type type, int containingElementId) { 
		
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
	
	void addParkingSpot(int spotId, Type type, int containingElementId) { 
		
		Spot newSpot = createNewSpot(spotId, type);
		(findAnObject(ParkingLotDataConfiguration, containingElementId)).Areas.addElement(newSpot); 
		newSpot.setParent(containingElementId);
	}
	
	void addArea (int areaId, Type type, int containingElementId) {
		
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
	
	int typeOfObject(ParkingLotData.ConfigObj obj) {
		
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
	
	public StatusElementObj createNewStatusElement(int id, Type status, Date lastResponse, int counter) {
		
		StatusElementObj newElement = new StatusElementObj(id, status, lastResponse, counter);
		return newElement;
	}
	
	public void addStatusElementToParkingLotDataStatus( int id, Type status, Date lastResponse, int counter ) {
		
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
	
}
