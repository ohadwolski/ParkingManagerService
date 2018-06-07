package parkingmanagerservice;

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

enum ParkingLotEvent{
	PARKING_FREED, PARKING_CAUGHT, PARKING_FULL;
}


public class DBInterface {
	
	private SystemDB DB;
	
	class SystemDB {
			
			
		class ParkingLotSign {                  //same sign for everyone
			
			private int ID;
			private SignArrow signArrow;
			private int signCounter;     //if not counter then counter =0
			private SignStatus signStatus;  
			private int LastResponse;
			
			
			public ParkingLotSign(int id, SignArrow arrow, int counter, SignStatus status, int lastResponse) {
				
				ID = id;
				signArrow = arrow;
				signCounter = counter;
				signStatus = status;
				LastResponse = lastResponse; 
			}	
		}	
		
		
		class ParkingLot {
				
				int ParkID;
				private IsOpen ParkingLotStatus;
				private ParkingLotSign LotSign;
				int NumOfLevels;
				private Vector<Levels> parkingLotLevels;
				int NumOfFreeSpotsInLot;
				
				public ParkingLot(int parkId, IsOpen statusOfPark, int signId, SignArrow signArrow, int signCounter, SignStatus statusOfSign, int signLastResponse,  int numOfLevels, int numOfParkingSpot) {
					
					ParkID = parkId;
					ParkingLotStatus = statusOfPark;
					LotSign = new ParkingLotSign(signId, signArrow, signCounter, statusOfSign, signLastResponse);
					NumOfLevels = numOfLevels;
					parkingLotLevels = new Vector<Levels>(numOfLevels, 1); 
					NumOfFreeSpotsInLot = numOfParkingSpot;
				}
				
				public class Levels{
					
					int LevelID;
					private IsOpen LevelStatus;
					private ParkingLotSign LevelSign;
					int NumOfAreas;
					private Vector<Areas> LevelsAreas;
					int NumOfFreeSpotsInLevel;
				
					
					public Levels(int levelId, IsOpen statusOfLevel, int signId, SignArrow signArrow, int signCounter, SignStatus statusOfSign, int signLastResponse,  int numOfAreas, int numOfParkingSpot) {
						
						LevelID = levelId;
						LevelStatus = statusOfLevel;
						LevelSign = new ParkingLotSign(signId, signArrow, signCounter, statusOfSign, signLastResponse);
						NumOfAreas = numOfAreas;
						LevelsAreas = new Vector<Areas>(numOfAreas, 1); 
						NumOfFreeSpotsInLevel = numOfParkingSpot;
					}
					
					public class Areas {
						
						int AreaID;
						private IsOpen AreaStatus;
						private ParkingLotSign AreaSign;
						int NumOfParkingSpots;
						private Vector<ParkingSpots> AreaParkingSpots;
						int NumOfFreeSpots = NumOfParkingSpots;
						
						
						public Areas(int areaId, IsOpen statusOfArea, int signId, SignArrow signArrow, int signCounter, SignStatus statusOfSign, int signLastResponse,  int areaParkSpotNum) {
							
							AreaID = areaId;
							AreaStatus = statusOfArea;
							AreaSign = new ParkingLotSign(signId, signArrow, signCounter, statusOfSign, signLastResponse);
							NumOfParkingSpots = areaParkSpotNum;
							AreaParkingSpots = new Vector<ParkingSpots>(areaParkSpotNum, 1);		
						}
						
						public class ParkingSpots {
							 
							int sensorID;
							private SensorType Type;
							private SensorStatus sensorStatus;
							private int LastResponse;
							
							public ParkingSpots(int id, SensorType type, SensorStatus status, int lastResponse) {
								
								sensorID = id;
								Type = type;
								sensorStatus = status;
								LastResponse = lastResponse;
							}		
						}		
					}		
				}						
		}			
	}		
}	
		
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	public class Layout {
	
		public class Sensor {
			private int sensorId;
			private SensorType Type; 
			private SensorStatus Status;
			private Date LastResponse;
		
			public Sensor(int id, SensorType type, SensorStatus status,Date lastResponse) {
				sensorId = id; 
				Type = type;
				Status = status;
				LastResponse = lastResponse;
			}	
			
			public int getsensorId() {
		        return sensorId;
			}

			public void setsensorId(int id) {
		        sensorId = id;
			}
			
			public SensorType getSensorType() {
		        return Type;
			}

			public void setSensorType(SensorType type) {
		        Type = type;
			} 
			
			public SensorStatus getSensorStatus() {
		        return Status;
			}

			public void setSensorStatus(SensorStatus status) {
		        Status = status;
			} 
			
			public Date getLastResponse() {
			        return LastResponse;
			}

			public void setLastResponse(Date date) {
				LastResponse = date;
			}
			
		}
		
		public class Level {
			
			private int LevelId;
			private IsOpen LevelStatus;
			
			
			public Level(int levelId, IsOpen status) {
				LevelId = levelId;
				LevelStatus = status;
			}	
			
			public int getLevelId() {
			        return LevelId;
			}

			public void setId(int id) {
			        LevelId = id;
			}
			
			public IsOpen isLevelOpen() {
				return LevelStatus;
			}
			
			public void setLevelStatus(IsOpen status) {
				LevelStatus = status;
			} 	
		}
		
		public class Area {
		
		
	}
	

		
		
	
		
	
		

	
	public class currentStatus{
		
	}
	
	public class Logs{
	
	}	
	
	*/
	
//}	






























