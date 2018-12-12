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


public class ChangeHierarchyController {

	private Main main;
	
	@FXML
	private ChoiceBox newParentArea;

	@FXML
	private ChoiceBox newParentSign;
	
	@FXML
	private ChoiceBox newParentSensor;
	
	@FXML
	private ChoiceBox changeAreaIdBox;

	@FXML
	private ChoiceBox changeSignIdBox;

	@FXML
	private ChoiceBox changeSubSignIdBox;

	@FXML
	private ChoiceBox changeZoneControllerIdBox;
	
	@FXML
	private ChoiceBox changePortIdBox;
	
	@FXML
	private ChoiceBox changeSensorIdBox;
	
	@FXML
	private TreeView<ParkingElement> changeHierAreaParkTree;
	
	@FXML
	private TreeView<ParkingElement> changeHierSignParkTree;
	
	@FXML
	private TreeView<ParkingElement> changeHierSensorParkTree;
	
	@FXML
	private Button signSet;
	
	@FXML
	private Button sensorSet;
	
	@FXML
	private Button zoneControllerSet;
	
	@FXML
	private void initialize() {
	
		if (main.getAreaId()!=null) {
		
			ArrayList<Integer> areaNum = main.getAreaId();
			Collections.sort(areaNum);
			ObservableList<Integer> areaIdList = FXCollections.observableArrayList(areaNum);			
			newParentArea.setItems(areaIdList);
			newParentSign.setItems(areaIdList);
			newParentSensor.setItems(areaIdList);
			areaNum.remove(0);
			areaIdList = FXCollections.observableArrayList(areaNum);
			changeAreaIdBox.setItems(areaIdList);
		}
		
		if (main.getSignId()!=null) {
			
			ArrayList<Integer> signNum = main.getSignId();
			Collections.sort(signNum);
			ObservableList<Integer> signIdList = FXCollections.observableArrayList(signNum);
			changeSignIdBox.setItems(signIdList);
		}
		
		if (main.getZoneControllerId()!=null) {
			
			ArrayList<Integer> zoneNum = main.getZoneControllerId();
			ObservableList<Integer> zoneControllerIdList = FXCollections.observableArrayList(zoneNum);
			changeZoneControllerIdBox.setItems(zoneControllerIdList);
		}

		changeHierAreaParkTree.setRoot(main.getRoot());
		changeHierAreaParkTree.getRoot().setExpanded(true);
		
		changeHierSignParkTree.setRoot(main.getRoot());
		changeHierSignParkTree.getRoot().setExpanded(true);
		
		changeHierSensorParkTree.setRoot(main.getRoot());
		changeHierSensorParkTree.getRoot().setExpanded(true);
	}	
	
	@FXML
	private void goDataSettings() throws IOException {
		
		main.showDataSettings();
	}
	
	@FXML
	private void changeAreaHierarchy() throws IOException {

		if (newParentArea.getValue() == null || changeAreaIdBox.getValue() == null) {
			main.showAlert();
		}
		else if(newParentArea.getValue().toString().equals(changeAreaIdBox.getValue().toString())) {
			main.showHierError();
		}
		else {			
			int parentId = Integer.parseInt(newParentArea.getValue().toString());
			int areaId = Integer.parseInt(changeAreaIdBox.getValue().toString());
			main.changeAreaHierarchyAfterSubmit(parentId, areaId);
			main.showChangeHierarchy();
			main.saveData();
		}
	}
	
	@FXML
	private void changeSignHierarchy() throws IOException {

		if (newParentSign.getValue() == null || changeSignIdBox.getValue() == null || changeSubSignIdBox.getValue() == null) {
			main.showAlert();
		}
		else {
			
			int parentId = Integer.parseInt(newParentSign.getValue().toString());
			int signId = Integer.parseInt(changeSignIdBox.getValue().toString());
			int subSignId = Integer.parseInt(changeSubSignIdBox.getValue().toString()); 
			main.changeSignHierarchyAfterSubmit(parentId, signId, subSignId);
			main.showChangeHierarchy();
			main.saveData();
		}
	}
	
	@FXML
	private void changeSensorHierarchy() throws IOException {

		if (newParentSensor.getValue() == null || changeSensorIdBox.getValue() == null || changeZoneControllerIdBox.getValue() == null || changePortIdBox.getValue() == null) {
			main.showAlert();
		}
		else {			
			int parentId = Integer.parseInt(newParentSensor.getValue().toString());
			int sensorId = Integer.parseInt(changeSensorIdBox.getValue().toString());
			int zoneId = Integer.parseInt(changeZoneControllerIdBox.getValue().toString()); 
			int portId = Integer.parseInt(changePortIdBox.getValue().toString()); 		
			main.changeSensorHierarchyAfterSubmit(parentId, zoneId, portId, sensorId);
			main.showChangeHierarchy();
			main.saveData();
		}
	}	
	
	@FXML
	private void setSubSignList() {
	
		if (changeSignIdBox.getValue() != null) {
			if (main.getSubSignId(Integer.parseInt(changeSignIdBox.getValue().toString()))!=null) {		
				ArrayList<Integer> subSignNum = main.getSubSignId(Integer.parseInt(changeSignIdBox.getValue().toString()));
				Collections.sort(subSignNum);
				ObservableList<Integer> subSignIdList = FXCollections.observableArrayList(subSignNum);
				changeSubSignIdBox.setItems(subSignIdList);
			}
		}
	}
	
	@FXML
	private void setPortList() {
	
		if (changeZoneControllerIdBox.getValue() != null) {
			if (main.getPortId(Integer.parseInt(changeZoneControllerIdBox.getValue().toString()))!=null) {		
				ArrayList<Integer> PortNum = main.getPortId(Integer.parseInt(changeZoneControllerIdBox.getValue().toString()));
				Collections.sort(PortNum);
				ObservableList<Integer> zonePortList = FXCollections.observableArrayList(PortNum);
				changePortIdBox.setItems(zonePortList);
			}
		}
	}
	
	@FXML
	private void setSensorList() {
	
		if (changeZoneControllerIdBox.getValue() != null && changePortIdBox.getValue() != null) {
			if (main.getSensorId(Integer.parseInt(changeZoneControllerIdBox.getValue().toString()),Integer.parseInt(changePortIdBox.getValue().toString()))!=null) {		
				ArrayList<Integer> sensorNum = main.getSensorId(Integer.parseInt(changeZoneControllerIdBox.getValue().toString()), Integer.parseInt(changePortIdBox.getValue().toString()));
				Collections.sort(sensorNum);
				ObservableList<Integer> sensorIdList = FXCollections.observableArrayList(sensorNum);
				changeSensorIdBox.setItems(sensorIdList);
			}
		}
	}
	
}
