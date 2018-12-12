package parkingmanagerconfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import parkingmanagerservice.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import parkingmanagerdata.*;


public class AddElementsController {

	private Main main;
	ObservableList<ConfigurationElement> configurationListArea = FXCollections.observableArrayList(ConfigurationElement.CLOSED,ConfigurationElement.REGULAR);
	ObservableList<ConfigurationElement> configurationListSign = FXCollections.observableArrayList(ConfigurationElement.CLOSED,ConfigurationElement.LEFT, ConfigurationElement.RIGHT, ConfigurationElement.UP, ConfigurationElement.DOWN,ConfigurationElement.NO_ENTRY);
	ObservableList<ConfigurationElement> configurationListSensor = FXCollections.observableArrayList(ConfigurationElement.REGULAR,ConfigurationElement.DISABLED);
	
	@FXML
	private TextField areaID;
	
	@FXML
	private TextField signID;
	
	@FXML
	private TextField subSignID;
	
	@FXML
	private TextField sensorZoneControllerID;
	
	@FXML
	private TextField sensorPortID;
	
	@FXML
	private TextField sensorID;
	
	@FXML
	private ChoiceBox configurationElementAreaBox;
	
	@FXML
	private ChoiceBox configurationElementSignBox;
	
	@FXML
	private ChoiceBox configurationElementSensorBox;
		
	@FXML
	private ChoiceBox addAreaAreaIdBox;
	
	@FXML
	private ChoiceBox addSignAreaIdBox;
	
	@FXML
	private ChoiceBox addSensorAreaIdBox;
	
	@FXML
	private TreeView<ParkingElement> addAreaParkTree;
	
	@FXML
	private TreeView<ParkingElement> addSignParkTree;
	
	@FXML
	private TreeView<ParkingElement> addSensorParkTree;
	
	
	@FXML
	private void initialize() {
		configurationElementAreaBox.setItems(configurationListArea);
		configurationElementSignBox.setItems(configurationListSign);
		configurationElementSensorBox.setItems(configurationListSensor);
	
		if (main.getAreaId()!=null) {
		
			ArrayList<Integer> areaNum = main.getAreaId();
			Collections.sort(areaNum);
			ObservableList<Integer> areaIdList = FXCollections.observableArrayList(areaNum);
			addAreaAreaIdBox.setItems(areaIdList);
			addSignAreaIdBox.setItems(areaIdList);
			addSensorAreaIdBox.setItems(areaIdList);
		}
		
		addAreaParkTree.setRoot(main.getRoot());
		addAreaParkTree.getRoot().setExpanded(true);
		
		addSignParkTree.setRoot(main.getRoot());
		addSignParkTree.getRoot().setExpanded(true);
		
		addSensorParkTree.setRoot(main.getRoot());
		addSensorParkTree.getRoot().setExpanded(true);	
	}
	
	@FXML
	private void goDataSettings() throws IOException {
		
		main.showDataSettings();		
	}
	
	@FXML
	private void addAreaAfterSubmit() throws IOException { 
		
		if (addAreaAreaIdBox.getValue() == null || areaID.getText().trim().isEmpty() ||  configurationElementAreaBox.getValue()==null) {
			main.showAlert();
		}		
		else if(!main.isAreaNumeric(areaID.getText())) {
			main.showIdError();			
		}
		else {
			int parentId = Integer.parseInt(addAreaAreaIdBox.getValue().toString());
			int newAreaId = Integer.parseInt(areaID.getText());
			ConfigurationElement config = (ConfigurationElement) configurationElementAreaBox.getValue();
			if (main.addAreaAfterSubmit(parentId, newAreaId, config, StatusElement.OK)) {
				main.showAddElements();
				main.saveData();
			
			}
			else {
				main.showExistError();
			}
		}
	}
	
	@FXML
	private void addSignAfterSubmit() throws IOException {
		
		if (addSignAreaIdBox.getValue() == null || signID.getText().trim().isEmpty() || subSignID.getText().trim().isEmpty() || configurationElementSignBox.getValue()==null) {
			main.showAlert();
		}
		else if(!(main.isSignNumeric(signID.getText()) && main.isSubSignNumeric(subSignID.getText())) ) {
			main.showIdError();	
		}
		else {
			int parentId = Integer.parseInt(addSignAreaIdBox.getValue().toString());
			int newSignId = Integer.parseInt(signID.getText());
			int newSubSignId = Integer.parseInt(subSignID.getText());
			ConfigurationElement config = (ConfigurationElement) configurationElementSignBox.getValue();
			if (main.addSignAfterSubmit(parentId, newSignId, newSubSignId, config, StatusElement.OK)) {
				main.showAddElements();
				main.saveData();
			}
			else {
				main.showExistError();
			}			
		}
	}
	
	@FXML
	private void addSensorAfterSubmit() throws IOException {
	
		if (addSensorAreaIdBox.getValue() == null || sensorID.getText().trim().isEmpty() || sensorPortID.getText().trim().isEmpty() || sensorZoneControllerID.getText().trim().isEmpty() || configurationElementSensorBox.getValue()==null) {
			main.showAlert();
		}
		else if(!(main.isSensorNumeric(sensorID.getText()) && main.isPortNumeric(sensorPortID.getText()) && main.isZoneNumeric(sensorZoneControllerID.getText()))) {
			main.showIdError();		
		}
		else {
			int parentId = Integer.parseInt(addSensorAreaIdBox.getValue().toString());
			int newSersorZoneId = Integer.parseInt(sensorZoneControllerID.getText());
			int newSensorPortId = Integer.parseInt(sensorPortID.getText());
			int newSensorId = Integer.parseInt(sensorID.getText());
			ConfigurationElement config = (ConfigurationElement) configurationElementSensorBox.getValue();
			if (main.addSensorAfterSubmit(parentId, newSersorZoneId, newSensorPortId, newSensorId, config, StatusElement.FREE)) {
				main.showAddElements();
				main.saveData();
			}
			else {
				main.showExistError();
			}	
		}
	}	
}
