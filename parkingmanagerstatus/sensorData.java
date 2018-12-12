package parkingmanagerstatus;

import java.io.IOException;
import parkingmanagerdata.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;


public class sensorData {

private Main main;
	
	@FXML
	private TableView sensorData;
	
	@FXML
	private TableColumn sensorID;
	
	@FXML
	private TableColumn zoneID;
	
	@FXML
	private TableColumn contID;
	
	@FXML
	private TableColumn status;
	
	@FXML
	private TableColumn configuration;
	
	@FXML
	private TableColumn date;
	
	@FXML
	private TreeView<ParkingElement> ParkTree;
	
	@FXML
	private void goMainView() throws IOException {
		
		main.showMainView();
		//main.showMainItems();
		main.refresh();
	}
	
	@FXML
	private void refreshWindow() throws IOException {
		
		main.refresh();
		//main.showMainItems();
		main.showSensorData();
	}

	@FXML
	private void initialize() {
		Timer timer = new Timer();
		 timer.schedule(new TimerTask() {
		    @Override
		    public void run() { // Function runs every MINUTES minutes.
		    	main.refresh();
		    	sensorData.getItems().clear();
		    	ParkTree.setRoot(main.getRoot());
				ParkTree.getRoot().setExpanded(true);
				ParkTree.refresh();
		    	if (main.getSensorId()!=null) {
					ArrayList<SensorId> sensorArray = main.getSensorId();
					ParkTree.refresh();
					ObservableList<sensorInfo> data = FXCollections.observableArrayList();
					for (SensorId item : sensorArray) {
						ParkingElement signElement = main.getElement(item);
						int senId = item.getSensorId();
						int zId = item.getZoneControllerId();
						int cId = item.getControllerId();
						StatusElement newStatus = main.getElement(item).getStatus();
						ParkTree.refresh();
						Date newDate = main.getElement(item).getTimeStamp();
						ConfigurationElement config = main.getElement(item).getConfiguration();
						sensorInfo newInfo = new sensorInfo(senId, zId, cId, newStatus, config, newDate);
						sensorID.setCellValueFactory(new PropertyValueFactory<>("SensorId"));
						zoneID.setCellValueFactory(new PropertyValueFactory<>("ZoneId"));
						contID.setCellValueFactory(new PropertyValueFactory<>("ControllerId"));
						status.setCellValueFactory(new PropertyValueFactory<>("Status"));
						configuration.setCellValueFactory(new PropertyValueFactory<>("Configuration"));
						date.setCellValueFactory(new PropertyValueFactory<>("TimeStamp"));
						sensorData.getItems().add(newInfo);
					 }
				}
		    	ParkTree.refresh();
		    	ParkTree.refresh();
				
		    }
		 }, 0, 1000*15  );
		/*if (main.getSensorId()!=null) {
			ArrayList<SensorId> sensorArray = main.getSensorId();
			ObservableList<sensorInfo> data = FXCollections.observableArrayList();
			for (SensorId item : sensorArray) {
				ParkingElement signElement = main.getElement(item);
				int senId = item.getSensorId();
				int zId = item.getZoneControllerId();
				int cId = item.getControllerId();
				StatusElement newStatus = main.getElement(item).getStatus();
				Date newDate = main.getElement(item).getTimeStamp();
				ConfigurationElement config = main.getElement(item).getConfiguration();
				sensorInfo newInfo = new sensorInfo(senId, zId, cId, newStatus, config, newDate);
				sensorID.setCellValueFactory(new PropertyValueFactory<>("SensorId"));
				zoneID.setCellValueFactory(new PropertyValueFactory<>("ZoneId"));
				contID.setCellValueFactory(new PropertyValueFactory<>("ControllerId"));
				status.setCellValueFactory(new PropertyValueFactory<>("Status"));
				configuration.setCellValueFactory(new PropertyValueFactory<>("Configuration"));
				date.setCellValueFactory(new PropertyValueFactory<>("TimeStamp"));
				sensorData.getItems().add(newInfo);
			 }
		}
		
		ParkTree.setRoot(main.getRoot());
		ParkTree.getRoot().setExpanded(true);*/
		
	       
			
	}
		
	
}
