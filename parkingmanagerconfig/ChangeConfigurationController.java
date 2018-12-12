package parkingmanagerconfig;

import java.io.IOException;
import parkingmanagerdata.*;
import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeView;


public class ChangeConfigurationController {

		private Main main;	
		ObservableList<ConfigurationElement> configurationListArea = FXCollections.observableArrayList(ConfigurationElement.CLOSED, ConfigurationElement.REGULAR);
		ObservableList<ConfigurationElement> configurationListSign = FXCollections.observableArrayList(ConfigurationElement.CLOSED,ConfigurationElement.LEFT, ConfigurationElement.RIGHT, ConfigurationElement.UP, ConfigurationElement.DOWN,ConfigurationElement.NO_ENTRY);
		ObservableList<ConfigurationElement> configurationListSensor = FXCollections.observableArrayList(ConfigurationElement.REGULAR,ConfigurationElement.DISABLED);
		
		@FXML
		private ChoiceBox configurationElementAreaBox;
		
		@FXML
		private ChoiceBox configurationElementSignBox;
		
		@FXML
		private ChoiceBox configurationElementSensorBox;
		
		@FXML
		private ChoiceBox configAreaIdBox;

		@FXML
		private ChoiceBox configSignIdBox;

		@FXML
		private ChoiceBox configSubSignIdBox;

		@FXML
		private ChoiceBox configZoneControllerIdBox;
		
		@FXML
		private ChoiceBox configPortIdBox;
		
		@FXML
		private ChoiceBox configSensorIdBox;
		
		@FXML
		private TreeView<ParkingElement> changeConfigAreaParkTree;
		
		@FXML
		private TreeView<ParkingElement> changeConfigSignParkTree;
		
		@FXML
		private TreeView<ParkingElement> changeConfigSensorParkTree;
		
		@FXML
		private Button signSet;
		
		@FXML
		private Button sensorSet;
		
		@FXML
		private Button zoneControllerSet;
		
		
		@FXML
		private void initialize() {
		
			configurationElementAreaBox.setItems(configurationListArea);
			configurationElementSignBox.setItems(configurationListSign);
			configurationElementSensorBox.setItems(configurationListSensor);
			if (main.getAreaId()!=null) {
			
				ArrayList<Integer> areaNum = main.getAreaId();
				Collections.sort(areaNum);
				ObservableList<Integer> areaIdList = FXCollections.observableArrayList(areaNum);
				configAreaIdBox.setItems(areaIdList);
			}
			
			if (main.getSignId()!=null) {
				
				ArrayList<Integer> signNum = main.getSignId();
				Collections.sort(signNum);
				ObservableList<Integer> signIdList = FXCollections.observableArrayList(signNum);
				configSignIdBox.setItems(signIdList);
			}
			
			if (main.getZoneControllerId()!=null) {
				
				ArrayList<Integer> zoneNum = main.getZoneControllerId();
				ObservableList<Integer> zoneControllerIdList = FXCollections.observableArrayList(zoneNum);
				configZoneControllerIdBox.setItems(zoneControllerIdList);
			}
			
			changeConfigAreaParkTree.setRoot(main.getRoot());
			changeConfigAreaParkTree.getRoot().setExpanded(true);
			
			changeConfigSignParkTree.setRoot(main.getRoot());
			changeConfigSignParkTree.getRoot().setExpanded(true);
			
			changeConfigSensorParkTree.setRoot(main.getRoot());
			changeConfigSensorParkTree.getRoot().setExpanded(true);
		}	
	
		
		@FXML
		private void goDataSettings() throws IOException {
		
			main.showDataSettings();
		}
		
		@FXML
		private void changeAreaConfigAfterSubmit() throws IOException {
			
			if (configAreaIdBox.getValue() == null || configurationElementAreaBox.getValue() == null) {
				main.showAlert();
			}
			else {
				int Area = Integer.parseInt(configAreaIdBox.getValue().toString());
				ConfigurationElement config = (ConfigurationElement) configurationElementAreaBox.getValue();
				main.changeAreaConfigAfterSubmit(Area, config);
				main.saveData();
			}	
		}
		
		@FXML
		private void changeSignConfigAfterSubmit() throws IOException {
			
			if (configSignIdBox.getValue() == null || configSubSignIdBox.getValue() == null || configurationElementSignBox.getValue() == null) {
				main.showAlert();
			}
			else {
				int Sign = Integer.parseInt(configSignIdBox.getValue().toString());
				int SubSignId = Integer.parseInt(configSubSignIdBox.getValue().toString());
				ConfigurationElement config = (ConfigurationElement) configurationElementSignBox.getValue();
				main.changeSignConfigAfterSubmit(Sign, SubSignId, config);
				main.saveData();
			}
		}	
		
		@FXML
		private void changeSensorConfigAfterSubmit() throws IOException {
			
			if (configSensorIdBox.getValue() == null || configZoneControllerIdBox.getValue() == null || configPortIdBox.getValue() == null || configurationElementSensorBox.getValue() == null) {
				main.showAlert();
			}
			else {
				int newSersorZoneId = Integer.parseInt(configZoneControllerIdBox.getValue().toString());
				int newPortId = Integer.parseInt(configPortIdBox.getValue().toString());
				int newSensorId = Integer.parseInt(configSensorIdBox.getValue().toString());
				ConfigurationElement config = (ConfigurationElement) configurationElementSensorBox.getValue();
				main.changeSensorConfigAfterSubmit(newSersorZoneId, newPortId, newSensorId, config);
				main.saveData();
			}	
		}	
		
		@FXML
		private void setSubSignList() {
		
			if (configSignIdBox.getValue() != null) {
				if (main.getSubSignId(Integer.parseInt(configSignIdBox.getValue().toString()))!=null) {		
					ArrayList<Integer> subSignNum = main.getSubSignId(Integer.parseInt(configSignIdBox.getValue().toString()));
					Collections.sort(subSignNum);
					ObservableList<Integer> subSignIdList = FXCollections.observableArrayList(subSignNum);
					configSubSignIdBox.setItems(subSignIdList);
				}
			}
		}
		
		@FXML
		private void setPortList() {
		
			if (configZoneControllerIdBox.getValue() != null) {
				if (main.getPortId(Integer.parseInt(configZoneControllerIdBox.getValue().toString()))!=null) {		
					ArrayList<Integer> PortNum = main.getPortId(Integer.parseInt(configZoneControllerIdBox.getValue().toString()));
					Collections.sort(PortNum);
					ObservableList<Integer> zonePortList = FXCollections.observableArrayList(PortNum);
					configPortIdBox.setItems(zonePortList);
				}
			}
		}
		
		@FXML
		private void setSensorList() {
		
			if (configZoneControllerIdBox.getValue() != null && configPortIdBox.getValue() != null) {
				if (main.getSensorId(Integer.parseInt(configZoneControllerIdBox.getValue().toString()),Integer.parseInt(configPortIdBox.getValue().toString()))!=null) {		
					ArrayList<Integer> sensorNum = main.getSensorId(Integer.parseInt(configZoneControllerIdBox.getValue().toString()), Integer.parseInt(configPortIdBox.getValue().toString()));
					Collections.sort(sensorNum);
					ObservableList<Integer> sensorIdList = FXCollections.observableArrayList(sensorNum);
					configSensorIdBox.setItems(sensorIdList);
				}
			}
		}
}
