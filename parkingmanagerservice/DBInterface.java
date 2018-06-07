//package parkingmanagerservice;                  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.util.Date;
import java.util.Vector;



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
	
	public class SystemDB {
		public ParkingLotSign MainSign;
		public ParkingLot ParkLot;
		public AdminLogs LogsOfAdmin;
		public ParkingLogs LogsOfParking;
		
		
		private SystemDB( ) {
			
			Date date = new Date (0);             //need to check about Date
			MainSign = new SystemDB.ParkingLotSign (0, SignArrow.NO_ENTRY, 0, SignStatus.ERROR, date);
			ParkLot = new SystemDB.ParkingLot(0, IsOpen.FALSE, 0, SignArrow.NO_ENTRY, 0, SignStatus.ERROR, date, 0, 0);
			LogsOfAdmin = new SystemDB.AdminLogs(0, 0, 0, date, AdminLogsEvent.ADMIN_LOGIN);
			LogsOfParking = new SystemDB.ParkingLogs(0, date, ParkingLotLogsEvent.PARKING_FULL);
			
		}
			
		public class ParkingLotSign {                  //same sign for everyone
			
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
		
		public class ParkingLot {
				
			public int ParkID;
			public IsOpen ParkingLotStatus;
			public ParkingLotSign LotSign;
			public int NumOfLevels;
			public Vector<Levels> parkingLotLevels;
			public int NumOfFreeSpotsInLot;
				
			public ParkingLot(int parkId, IsOpen statusOfPark, int signId, SignArrow signArrow, int signCounter, SignStatus statusOfSign, Date signLastResponse,  int numOfLevels, int numOfParkingSpot) {
					
				ParkID = parkId;
				ParkingLotStatus = statusOfPark;
				LotSign = new ParkingLotSign(signId, signArrow, signCounter, statusOfSign, signLastResponse);
				NumOfLevels = numOfLevels;
				parkingLotLevels = new Vector<Levels>(numOfLevels, 1); 
				NumOfFreeSpotsInLot = numOfParkingSpot;
			}
				
				public class Levels{
					
					public int LevelID;
					public IsOpen LevelStatus;
					public ParkingLotSign LevelSign;
					public int NumOfAreas;
					public Vector<Areas> LevelsAreas;
					public int NumOfFreeSpotsInLevel;
				
					public Levels(int levelId, IsOpen statusOfLevel, int signId, SignArrow signArrow, int signCounter, SignStatus statusOfSign, Date signLastResponse,  int numOfAreas, int numOfParkingSpot) {
						
						LevelID = levelId;
						LevelStatus = statusOfLevel;
						LevelSign = new ParkingLotSign(signId, signArrow, signCounter, statusOfSign, signLastResponse);
						NumOfAreas = numOfAreas;
						LevelsAreas = new Vector<Areas>(numOfAreas, 1); 
						NumOfFreeSpotsInLevel = numOfParkingSpot;
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
		public class AdminLogs {
				
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
			
		public class ParkingLogs {
				
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
////////////////////////////////////////////DB initial////////////////////////////////////////////////////////////////////////////////	
	public void initDataBase() {
		
		new DBInterface();
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/////////////////////////////////////////////////Parkinglot's main sign methods///////////////////////////////////////////////////////
	
	public Results EditParkingLotSign(int id, SignArrow arrow, int counter, SignStatus status, Date lastResponse) {
		
		DB.MainSign.ID = id;
		DB.MainSign.signArrow = arrow;
		DB.MainSign.signCounter = counter;
		DB.MainSign.signStatus = status;
		DB.MainSign.LastResponse = lastResponse;
		return Results.SUCCESS;
	}
	
	
	public void setMainSignID(int id) {
		
		DB.MainSign.ID = id;
	}
	
	public int getMainSignID() {
		
		return DB.MainSign.ID;
	}
	
	public void setMainSignArrow(SignArrow arrow) {
		
		DB.MainSign.signArrow = arrow;
	}
	
	public SignArrow getMainSignArrow() {
		
		return DB.MainSign.signArrow;
	}
	
	public void setMainSignCounter(int counter) {
		
		DB.MainSign.signCounter = counter;
	}
	
	public int getMainSignCounter() {
		
		return DB.MainSign.signCounter;
	}
	
	public void setMainSignStatus(SignStatus status) {
		
		DB.MainSign.signStatus = status;
	}

	public SignStatus getMainSignStatus() {
	
		return DB.MainSign.signStatus;
	}
	
	public void setMainSignLastResponse(Date lastResponse) {
		
		DB.MainSign.LastResponse = lastResponse;	
	}
	
	public Date LastResponse() {
		
		return DB.MainSign.LastResponse;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
///////////////////////////////////////////////////Admin's logs methods///////////////////////////////////////////////////
	
	
	public Results AddNewAdminLog(int adminId, int signId, int spotId, Date date, AdminLogsEvent logEvent) {
		
		DB.LogsOfAdmin.AdminID = adminId;
		DB.LogsOfAdmin.SignID = signId;
		DB.LogsOfAdmin.ParkingSpotID = spotId;
		DB.LogsOfAdmin.Date = date;
		DB.LogsOfAdmin.LogEvent = logEvent;
		return Results.SUCCESS;
	}
	
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
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
///////////////////////////////////////////////////Parking logs methods///////////////////////////////////////////////////	
	
public Results AddNewParkingLog(int sensorId, Date date, ParkingLotLogsEvent logEvent) {
		
		DB.LogsOfParking.ParkSpotSensorID = sensorId;
		DB.LogsOfParking.Date = date;
		DB.LogsOfParking.ParkingLotLogsEvent = logEvent;
		return Results.SUCCESS;
	}
	
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
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
				
}	
		
		
		
