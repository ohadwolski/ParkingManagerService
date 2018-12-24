package parkingmanagerstatus;

import java.util.Date;
import parkingmanagerdata.*;

import javafx.beans.property.SimpleStringProperty;

//Information class of the Sensors status table of the App
public class sensorInfo {

	private SimpleStringProperty SensorId; //sensor ID column
	private SimpleStringProperty ZoneId; //zone ID column
	private SimpleStringProperty ControllerId; //port ID column
	private SimpleStringProperty Status; //status column
	private SimpleStringProperty Configuration; //configuration column
	private SimpleStringProperty TimeStamp; //time stamp column
	
	//constructor
	sensorInfo(int senId, int zoneId, int contId, StatusElement statusRec, ConfigurationElement config, Date time) {
		
		SensorId = new SimpleStringProperty(Integer.toString(senId));
		ZoneId = new SimpleStringProperty(Integer.toString(zoneId));
		ControllerId = new SimpleStringProperty(Integer.toString(contId));
		Status = new SimpleStringProperty(statusRec.toString());
		Configuration = new SimpleStringProperty(config.toString());
		TimeStamp = new SimpleStringProperty(time.toString());		
	}
	
	//return sensor Id
	public String getSensorId() {
		
		return SensorId.get();
	}
	
	//return zone Id
	public String getZoneId() {
		
		return ZoneId.get();
	}
	
	//return port Id
	public String getControllerId() {
		
		return ControllerId.get();
	}
	
	//return status
	public String getStatus() {
		
		return Status.get();
	}

	//return configuration
	public String getConfiguration() {
	
		return Configuration.get();
	}

	//return time stamp
	public String getTimeStamp() {
	
		return TimeStamp.get();
	}
}
