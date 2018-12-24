package parkingmanagerstatus;

import java.util.Date;
import parkingmanagerdata.*;

import javafx.beans.property.SimpleStringProperty;

//Information class of the Signs status table of the App
public class signInfo {

	private SimpleStringProperty Id; //ID column
	private SimpleStringProperty SubSignId; //Sub ID column
	private SimpleStringProperty Status; //Status column
	private SimpleStringProperty Counter; //Counter column
	private SimpleStringProperty Configuration; //Configuration column
	private SimpleStringProperty TimeStamp; //Timestampe column
	
	//Constructor
	signInfo(int id, int subId, StatusElement statusRec, ConfigurationElement config, Date time, int counter) {
		
		Id = new SimpleStringProperty(Integer.toString(id));
		SubSignId = new SimpleStringProperty(Integer.toString(subId));
		Status = new SimpleStringProperty(statusRec.toString());
		Counter = new SimpleStringProperty(Integer.toString(counter));
		Configuration = new SimpleStringProperty(config.toString());
		TimeStamp = new SimpleStringProperty(time.toString());		
	}
	
	//return ID
	public String getId() {
		
		return Id.get();
	}
	
	//returns sub Id
	public String getSubSignId() {
		
		return SubSignId.get();
	}
	
	//returns status
	public String getStatus() {
		
		return Status.get();
	}

	//returns counter
	public String getCounter() {
		
		return Counter.get();
	}

	//returns configuration
	public String getConfiguration() {
	
		return Configuration.get();
	}

	//returns timestamp
	public String getTimeStamp() {
	
		return TimeStamp.get();
	}
}
