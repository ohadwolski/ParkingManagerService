package parkingmanagerstatus;

import java.util.Date;
import parkingmanagerdata.*;

import javafx.beans.property.SimpleStringProperty;


public class signInfo {

	private SimpleStringProperty Id;
	private SimpleStringProperty SubSignId;
	private SimpleStringProperty Status;
	private SimpleStringProperty Counter;
	private SimpleStringProperty Configuration; 
	private SimpleStringProperty TimeStamp;
	
	signInfo(int id, int subId, StatusElement statusRec, ConfigurationElement config, Date time, int counter) {
		
		Id = new SimpleStringProperty(Integer.toString(id));
		SubSignId = new SimpleStringProperty(Integer.toString(subId));
		Status = new SimpleStringProperty(statusRec.toString());
		Counter = new SimpleStringProperty(Integer.toString(counter));
		Configuration = new SimpleStringProperty(config.toString());
		TimeStamp = new SimpleStringProperty(time.toString());		
	}
	
	public String getId() {
		
		return Id.get();
	}
	
	public String getSubSignId() {
		
		return SubSignId.get();
	}
	
	public String getStatus() {
		
		return Status.get();
	}

	public String getCounter() {
		
		return Counter.get();
	}


	public String getConfiguration() {
	
		return Configuration.get();
	}

	public String getTimeStamp() {
	
		return TimeStamp.get();
	}
}
