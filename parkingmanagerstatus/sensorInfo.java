package parkingmanagerstatus;

import java.util.Date;
import parkingmanagerdata.*;

import javafx.beans.property.SimpleStringProperty;


public class sensorInfo {

	private SimpleStringProperty SensorId;
	private SimpleStringProperty ZoneId;
	private SimpleStringProperty ControllerId;
	private SimpleStringProperty Status;
	private SimpleStringProperty Configuration; 
	private SimpleStringProperty TimeStamp;
	
	sensorInfo(int senId, int zoneId, int contId, StatusElement statusRec, ConfigurationElement config, Date time) {
		
		SensorId = new SimpleStringProperty(Integer.toString(senId));
		ZoneId = new SimpleStringProperty(Integer.toString(zoneId));
		ControllerId = new SimpleStringProperty(Integer.toString(contId));
		Status = new SimpleStringProperty(statusRec.toString());
		Configuration = new SimpleStringProperty(config.toString());
		TimeStamp = new SimpleStringProperty(time.toString());		
	}
	
	public String getSensorId() {
		
		return SensorId.get();
	}
	
	public String getZoneId() {
		
		return ZoneId.get();
	}
	
	public String getControllerId() {
		
		return ControllerId.get();
	}
	
	
	public String getStatus() {
		
		return Status.get();
	}

	public String getConfiguration() {
	
		return Configuration.get();
	}

	public String getTimeStamp() {
	
		return TimeStamp.get();
	}
}
