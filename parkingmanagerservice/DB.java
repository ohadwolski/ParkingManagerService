

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


public class DB {
	
	public Vector<ConfigObj> DBConfiguration;
	public Vector<StatusElementObj> DBStatus;
	
	public DB() {
		
		DBConfiguration = new Vector<ConfigObj>(0,1);
		DBStatus = new Vector<StatusElementObj>(0,1);	
	}
	
	public static abstract class ConfigObj {								////////static
		
		protected int ID;
		protected Type GeneralType;
		protected Vector<ConfigObj> Areas = null;
		
		public ConfigObj() {                                  //probably not necessary
			
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
		
	}	

	public static class Area extends ConfigObj {
			
		public Area (int id, Type genT) {
			
			Areas = new Vector<ConfigObj> (0,1);
			setID(id);
			setGeneralType(genT);
		}
		

			
	}
		
	public static class Sign extends ConfigObj {                        /////////////////////////static /////////////////////////////////////
			
		public Sign(int id, Type genT) {
			setID(id);
			setGeneralType(genT);	
		}
		/*
		public Sign createNewSign(int signId, Type type) {
			
			Sign newSign = new DB.Sign(signId, type);
			return newSign;		
		}*/
	}
		
	public static class Spot extends ConfigObj {
			
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
		
		void setID(int id) {
			
			ID = id;
		}
		
		int getID() {
			
			return ID;
		}
		
		void setStatus(Type status) {
			
			Status = status;
		}
		
		Type getStatus() {
			
			return Status;
		}
		
		void setLastResponse(Date lastResponse) {
			
			LastResponse = lastResponse;
		}
		
		Date getLastResponse() {
			
			return LastResponse;
		}
		
		void setCounter(int counter) {
			
			Counter = counter;
			
		}
		
		int getCounter() {
			
			return Counter;
		}

		void incCounter() {
			
			Counter++;
	
		}

		void decCounter() {
	
			Counter--;
		}

	}		
	/*
	public static class SpotStatus extends StatusElementObj{
		
		public SpotStatus(int id, Type status, Date lastResponse) {                                  
			
			ID = id;
			Status = status;
			LastResponse = lastResponse;
			
		}
		
	}
	/*
	public static class SignStatus extends StatusElementObj{
		
		private int counter=0;
		
		public SignStatus(int id, Type status, Date lastResponse) {                             
			
			ID = id;
			Status = status;
			LastResponse = lastResponse;
		}
		
		public void setCounter(int num) {
			
			counter = num;
		}
		
		public void incCounter() {
			
			counter++;
		}
			
		public void decCounter() {
			
			counter--;
		}
	}
	
	/*
	public static void main(String args[]) {
		
		Sign A = new Sign(3, Type.ADMIN_LOGIN);
		Sign B = new Sign(4, Type.AREA_ADDED);
		
		System.out.println(A.getID());
		System.out.println(A.getGeneralType());
		System.out.println(B.getID());
		System.out.println(B.getGeneralType());
		A.setID(5);
		A.setGeneralType(Type.ADMIN_LOGOUT);
		System.out.println(A.getID());
		System.out.println(A.getGeneralType());
		System.out.println(B.getID());
		System.out.println(B.getGeneralType());
	}*/

}
