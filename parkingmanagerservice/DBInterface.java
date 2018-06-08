//package parkingmanagerservice;                  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.util.Date;
import java.util.Vector;
import static java.lang.Math.toIntExact;


enum SensorType {
	TRUE, FALSE, REGULAR, VIP, GUEST, DISABLED, VIRTUAL;
}

enum SensorStatus {
	FREE, OCUUPIED, ERROR;
}

enum SignArrow {
	NO_ENTRY, STOP, LEFT, RIGHT, UP, DOWN;
}

enum SignStatus {
	OK, ERROR;
}

enum IsOpen {
	FALSE, TRUE;
}

enum AdminLogsEvent {
	PARKING_ADDED, PARKING_REMOVED, PARKING_TYPE_MODIFIED ,SIGN_ADDED ,SIGN_REMOVED ,AREA_ADDED ,AREA_REMOVED ,LEVEL_ADDED , LEVEL_REMOVED , ADMIN_LOGIN , ADMIN_LOGOUT, USER_LOGIN , USER_LOGOUT; 	
}

enum ParkingLotLogsEvent{
	PARKING_FREED, PARKING_CAUGHT, PARKING_FULL;
}

enum Results {
	FAILED, SUCCESS;
}


public class DBInterface {
	
	private SystemDB DB;
	
	private DBInterface() {
		DB = new SystemDB();
	}
	
	public static class SystemDB {
		//public ParkingLotSign MainSign;
		public ParkingLot ParkLot;
		public Logs SystemLogs;
		
		private SystemDB( ) {
			
			Date date = new Date (0);             //need to check about Date
			//MainSign = new SystemDB.ParkingLotSign (0, SignArrow.NO_ENTRY, 0, SignStatus.ERROR, date);
			ParkLot = new SystemDB.ParkingLot(0, IsOpen.FALSE, 0, SignArrow.NO_ENTRY, 0, SignStatus.ERROR, date, 0);
			SystemLogs = new Logs();
		}
			
		public static class ParkingLotSign {                  //same sign for everyone
			
			public int ID;
			public SignArrow signArrow;
			public int signCounter;     //if not counter then counter =0
			public SignStatus signStatus;  
			public Date LastResponse;
			
			public ParkingLotSign(int id, SignArrow arrow, int counter, SignStatus status, Date lastResponse) {
				
				ID = id;
				signArrow = arrow;
				signCounter = counter;
				signStatus = status;
				LastResponse = lastResponse; 
			}	
		}	
		
		public static class ParkingLot {
				
			public int ParkID;
			public IsOpen ParkingLotStatus;
			public ParkingLotSign LotSign;
			public int NumOfLevels;
			public int NumOfSpotsInLot;
			public int NumOfFreeSpotsInLot;
			public Vector<Levels> parkingLotLevels;
			
				
			public ParkingLot(int parkId, IsOpen statusOfPark, int signId, SignArrow signArrow, int signCounter, SignStatus statusOfSign, Date signLastResponse, int numOfParkingSpot) {
					
				ParkID = parkId;
				ParkingLotStatus = statusOfPark;
				LotSign = new ParkingLotSign(signId, signArrow, signCounter, statusOfSign, signLastResponse);
				NumOfLevels = 0;
				NumOfSpotsInLot = numOfParkingSpot;
				NumOfFreeSpotsInLot = numOfParkingSpot;
				parkingLotLevels = new Vector<Levels>(0, 1); 
			}
				
				public static class Levels{
					
					public int LevelID;
					public IsOpen LevelStatus;
					public ParkingLotSign LevelSign;
					public int NumOfAreas;
					public int NumOfFreeSpotsInLevel;
					public Vector<Areas> LevelsAreas;
					
				
					public Levels(int levelId, IsOpen statusOfLevel, int signId, SignArrow signArrow, int signCounter, SignStatus statusOfSign, Date signLastResponse,  int numOfAreas, int numOfParkingSpot) {
						
						LevelID = levelId;
						LevelStatus = statusOfLevel;
						LevelSign = new ParkingLotSign(signId, signArrow, signCounter, statusOfSign, signLastResponse);
						NumOfAreas = 0;
						NumOfFreeSpotsInLevel = numOfParkingSpot;
						LevelsAreas = new Vector<Areas>(0, 1); 
					}
					
					public class Areas {
						
						public int AreaID;
						public IsOpen AreaStatus;
						public ParkingLotSign AreaSign;
						public int NumOfParkingSpots;
						public Vector<ParkingSpots> AreaParkingSpots;
						public int NumOfFreeSpots = NumOfParkingSpots;
						
						public Areas(int areaId, IsOpen statusOfArea, int signId, SignArrow signArrow, int signCounter, SignStatus statusOfSign, Date signLastResponse,  int areaParkSpotNum) {
							
							AreaID = areaId;
							AreaStatus = statusOfArea;
							AreaSign = new ParkingLotSign(signId, signArrow, signCounter, statusOfSign, signLastResponse);
							NumOfParkingSpots = areaParkSpotNum;
							AreaParkingSpots = new Vector<ParkingSpots>(areaParkSpotNum, 1);		
						}
						
						public class ParkingSpots {
							 
							public int sensorID;
							public SensorType Type;
							public SensorStatus sensorStatus;
							public Date LastResponse;
							
							public ParkingSpots(int id, SensorType type, SensorStatus status, Date lastResponse) {
								
								sensorID = id;
								Type = type;
								sensorStatus = status;
								LastResponse = lastResponse;
							}		
						}		
					}		
				}						
			}
		public static class Logs {
			
			public Vector<Logs.AdminLogs> LogsOfAdmin;
			public Vector<Logs.ParkingLogs> LogsOfParking;
			
			public Logs() {
				LogsOfAdmin = new Vector<Logs.AdminLogs>(0,1);
				LogsOfParking = new Vector<Logs.ParkingLogs>(0,1);
			}
			
			public static class AdminLogs {
				
				public int AdminID;
				public int SignID;
				public int ParkingSpotID;
				public Date Date;
				public AdminLogsEvent LogEvent;
				
				public AdminLogs(int adminId, int signId, int spotId, Date date, AdminLogsEvent logEvent) {
					
					AdminID = adminId;
					SignID = signId;
					ParkingSpotID = spotId;
					Date = date;
					LogEvent = logEvent;
				}
			}
			
			public static class ParkingLogs {
				
				public int ParkSpotSensorID;
				public Date Date;
				public ParkingLotLogsEvent ParkingLotLogsEvent;
				
				public ParkingLogs (int sensorId, Date date, ParkingLotLogsEvent logEvent) {
					
					ParkSpotSensorID = sensorId;
					Date = date;
					ParkingLotLogsEvent = logEvent;
				}	
			}
	}	
	
	
		
	}
////////////////////////////////////////////DB initial////////////////////////////////////////////////////////////////////////////////	
	public void initDataBase() {
		
		new DBInterface();
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	

	
/////////////////////////////////////////////Parking Lot////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public Results EditParkingLotSign(int id, SignArrow arrow, int counter, SignStatus status, Date lastResponse) {
		
		DB.ParkLot.LotSign.ID = id;
		DB.ParkLot.LotSign.signArrow = arrow;
		DB.ParkLot.LotSign.signCounter = counter;
		DB.ParkLot.LotSign.signStatus = status;
		DB.ParkLot.LotSign.LastResponse = lastResponse;
		return Results.SUCCESS;
	}
	
	
//////////////////////////Parking Lot ID methods///////////////////////////////////////
	
	public void setParkLotID(int id) {
		
		DB.ParkLot.ParkID = id;
	}
	
	public int getParkLotID() {
		
		return DB.ParkLot.ParkID;
	}

//////////////////////////////Parking Lot Status////////////////////////////////////	
	public void setParkLotStatus(IsOpen status) {
		
		DB.ParkLot.ParkingLotStatus = status;
	}
	
	public IsOpen getParkLotStatus() {
		
		return DB.ParkLot.ParkingLotStatus;
	}

//////////////////////////////////////Parking Lot num of levels///////////////////
	public void setParkLotNumOfLevels(int num) {
		
		DB.ParkLot.NumOfLevels = num;
	}
	
	public int getParkLotNumOfLevels() {
		
		return DB.ParkLot.NumOfLevels;
	}
	
////////////////////////////////////Parking Lot num of spots//////////////////////
	
	public void setParkLotNumOfSpots(int num) {
		
		DB.ParkLot.NumOfSpotsInLot = num;
	}
	
	public int getParkLotNumOfSpots() {
		
		return DB.ParkLot.NumOfSpotsInLot;
	}	
	
	public void setParkLotNumOfFreeSpots(int num) {
		
		DB.ParkLot.NumOfFreeSpotsInLot = num;
	}
	
	public int getParkLotNumOfFreeSpots() {
		
		return DB.ParkLot.NumOfFreeSpotsInLot;
	}	
	
	public void incParkLotNumOfFreeSpots(int num) {
		
		DB.ParkLot.NumOfFreeSpotsInLot++;
	}
	
	public void decParkLotNumOfFreeSpots() {
		
		DB.ParkLot.NumOfFreeSpotsInLot--;
	}	

/////////////////////////////////////////////////Parkinglot's main sign methods///////////////////////////////////////////////////////
	
	public void setParkLotSignID(int id) {
		
		DB.ParkLot.LotSign.ID = id;
	}
	
	public int getParkLotSignID() {
		
		return DB.ParkLot.LotSign.ID;
	}
	
	
	public void setParkLotSignArrow(SignArrow arrow) {
		
		DB.ParkLot.LotSign.signArrow = arrow;
	}
	
	public SignArrow getParkLotSignArrow() {
		
		return DB.ParkLot.LotSign.signArrow;
	}
	
	public void setParkLotSignCounter(int counter) {
		
		DB.ParkLot.LotSign.signCounter = counter;
	}
	
	public int getParkLotSignCounter() {
		
		return DB.ParkLot.LotSign.signCounter;
	}
	
	public void setParkLotSignStatus(SignStatus status) {
		
		DB.ParkLot.LotSign.signStatus = status;
	}

	public SignStatus getParkLotSignStatus() {
	
		return DB.ParkLot.LotSign.signStatus;
	}
	
	public void setParkLotSignLastResponse(Date lastResponse) {
		
		DB.ParkLot.LotSign.LastResponse = lastResponse;	
	}
	
	public Date getParkLotSignLastResponse() {
		
		return DB.ParkLot.LotSign.LastResponse;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////Parkinglot's Level methods////////////////////////////////////////////////////////////////
	
	public Results AddNewLevelToParkingLot(int levelId, IsOpen statusOfLevel, int signId, SignArrow signArrow, int signCounter, SignStatus statusOfSign, Date signLastResponse,  int numOfAreas, int numOfParkingSpot) {
		
		
		SystemDB.ParkingLot.Levels LevelToAdd = new SystemDB.ParkingLot.Levels(levelId, statusOfLevel, signId, signArrow, signCounter, statusOfSign, signLastResponse, numOfAreas, numOfParkingSpot);
		DB.ParkLot.parkingLotLevels.insertElementAt(LevelToAdd, levelId);
		DB.ParkLot.NumOfLevels++;
		
		return Results.SUCCESS;
	}	

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	
	
///////////////////////////////////////////////////Admin's logs methods///////////////////////////////////////////////////
	
	
	public Results AddNewAdminLog(int adminId, int signId, int spotId, Date date, AdminLogsEvent logEvent) {
		
		
		SystemDB.Logs.AdminLogs LogToAdd = new SystemDB.Logs.AdminLogs(adminId, signId, spotId, date, logEvent);
		int index = Math.toIntExact(date.getTime());  										 //long to int conversion
		DB.SystemLogs.LogsOfAdmin.insertElementAt(LogToAdd, index);
		return Results.SUCCESS;
	}
	
	public void printAdminLogs() {
		
		for (int i=1; i<DB.SystemLogs.LogsOfAdmin.capacity()+1; i++) {
			
			System.out.println("Admin Id is" + DB.SystemLogs.LogsOfAdmin.get(i).AdminID + ", The sign Id is" + DB.SystemLogs.LogsOfAdmin.get(i).SignID + ", The spot Id is" + DB.SystemLogs.LogsOfAdmin.get(i).ParkingSpotID + ",Time is" + DB.SystemLogs.LogsOfAdmin.get(i).Date + ",The Event is" + DB.SystemLogs.LogsOfAdmin.get(i).LogEvent);
		}
	}
	/*
	public void setLogsOfAdminAdminID(int adminId) {
		
		DB.LogsOfAdmin.AdminID = adminId;
	}
	
	public int getLogsOfAdminAdminID() {
		
		return DB.LogsOfAdmin.AdminID;
	}
	
	public void setLogsOfAdminSignID(int signId) {
		
		DB.LogsOfAdmin.SignID= signId;
	}
	
	public int getLogsOfAdminSignID() {
		
		return DB.LogsOfAdmin.SignID;
	}
	
	public void setLogsOfAdminParkingSpotID(int spotId) {
		
		DB.LogsOfAdmin.ParkingSpotID = spotId;
	}
	
	public int getLogsOfAdminParkingSpotID() {
		
		return DB.LogsOfAdmin.ParkingSpotID;
	}
	
	public void setLogsOfAdminDate(Date date) {
		
		DB.LogsOfAdmin.Date = date;
	}
	
	public Date getLogsOfAdminDate() {
		
		return DB.LogsOfAdmin.Date;
	}
	
	public void setLogsOfAdminLogEvent(AdminLogsEvent logEvent) {
		
		DB.LogsOfAdmin.LogEvent = logEvent;
	}
	
	public AdminLogsEvent getLogsOfAdminLogEvent() {
		
		return DB.LogsOfAdmin.LogEvent;
	}
	*/
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
///////////////////////////////////////////////////Parking logs methods///////////////////////////////////////////////////	
	
	public Results AddNewParkingLog(int sensorId, Date date, ParkingLotLogsEvent logEvent) {
		
		SystemDB.Logs.ParkingLogs LogToAdd = new SystemDB.Logs.ParkingLogs(sensorId, date, logEvent);
		int index = Math.toIntExact(date.getTime());  										 //long to int conversion
		DB.SystemLogs.LogsOfParking.insertElementAt(LogToAdd, index);
		return Results.SUCCESS;
	}
	
	public void printParkingLogs() {
		
		for (int i=1; i<DB.SystemLogs.LogsOfParking.capacity()+1; i++) {
			
			System.out.println("Sensor Id is" + DB.SystemLogs.LogsOfParking.get(i).ParkSpotSensorID + ",Time is" + DB.SystemLogs.LogsOfParking.get(i).Date + ",The Event is" + DB.SystemLogs.LogsOfParking.get(i).ParkingLotLogsEvent);
		}
	}
	/*
	public void setLogsOfParkingParkSpotSensorID(int sensorId) {
		
		DB.LogsOfParking.ParkSpotSensorID = sensorId;
	}
	
	public int getLogsOfParkingParkSpotSensorID() {
		
		return DB.LogsOfParking.ParkSpotSensorID;
	}
	
	public void setLogsOfParkingDate(Date date) {
		
		DB.LogsOfParking.Date= date;
	}
	
	public Date getLogsOfParkingDate() {
		
		return DB.LogsOfParking.Date;
	}
	
	public void setLogsOfParkingLogsEvent(ParkingLotLogsEvent logEvent) {
		
		DB.LogsOfParking.ParkingLotLogsEvent = logEvent;
	}
	
	public ParkingLotLogsEvent getLogsOfParkingLogsEvent() {
		
		return DB.LogsOfParking.ParkingLotLogsEvent;
	}*/
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}	
		
		
		
