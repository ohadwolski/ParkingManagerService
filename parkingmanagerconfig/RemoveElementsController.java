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


public class RemoveElementsController {

	private Main main;
	
	@FXML
	private ChoiceBox removeAreaIdBox;

	@FXML
	private ChoiceBox removeSignAreaIdBox;

	@FXML
	private ChoiceBox removeSubSignIdBox;

	@FXML
	private ChoiceBox removeZoneControllerIdBox;
	
	@FXML
	private ChoiceBox removePortIdBox;
	
	@FXML
	private ChoiceBox removeSensorIdBox;
	
	@FXML
	private TreeView<ParkingElement> removeAreaParkTree;
	
	@FXML
	private TreeView<ParkingElement> removeSignParkTree;
	
	@FXML
	private TreeView<ParkingElement> removeSensorParkTree;
	
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
			areaNum.remove(0);
			ObservableList<Integer> areaIdList = FXCollections.observableArrayList(areaNum);
			removeAreaIdBox.setItems(areaIdList);
		}
		
		if (main.getSignId()!=null) {
			
			ArrayList<Integer> signNum = main.getSignId();
			Collections.sort(signNum);
			ObservableList<Integer> signIdList = FXCollections.observableArrayList(signNum);
			removeSignAreaIdBox.setItems(signIdList);
		}
		
		if (main.getZoneControllerId()!=null) {
			
			ArrayList<Integer> zoneNum = main.getZoneControllerId();
			ObservableList<Integer> zoneControllerIdList = FXCollections.observableArrayList(zoneNum);
			removeZoneControllerIdBox.setItems(zoneControllerIdList);
		}
		
		removeAreaParkTree.setRoot(main.getRoot());
		removeAreaParkTree.getRoot().setExpanded(true);
		
		removeSignParkTree.setRoot(main.getRoot());
		removeSignParkTree.getRoot().setExpanded(true);
		
		removeSensorParkTree.setRoot(main.getRoot());
		removeSensorParkTree.getRoot().setExpanded(true);		
	}
	
	@FXML
	private void goDataSettings() throws IOException {
		
		main.showDataSettings();
	}
	
	@FXML
	private void setSubSignList() {
	
		if (removeSignAreaIdBox.getValue() != null) {
			if (main.getSubSignId(Integer.parseInt(removeSignAreaIdBox.getValue().toString()))!=null) {		
				ArrayList<Integer> subSignNum = main.getSubSignId(Integer.parseInt(removeSignAreaIdBox.getValue().toString()));
				Collections.sort(subSignNum);
				ObservableList<Integer> subSignIdList = FXCollections.observableArrayList(subSignNum);
				removeSubSignIdBox.setItems(subSignIdList);
			}
		}
	}
	
	@FXML
	private void setPortList() {
	
		if (removeZoneControllerIdBox.getValue() != null) {
			if (main.getPortId(Integer.parseInt(removeZoneControllerIdBox.getValue().toString()))!=null) {		
				ArrayList<Integer> PortNum = main.getPortId(Integer.parseInt(removeZoneControllerIdBox.getValue().toString()));
				Collections.sort(PortNum);
				ObservableList<Integer> zonePortList = FXCollections.observableArrayList(PortNum);
				removePortIdBox.setItems(zonePortList);
			}
		}
	}
	
	@FXML
	private void setSensorList() {
	
		if (removeZoneControllerIdBox.getValue() != null && removePortIdBox.getValue() != null) {
			if (main.getSensorId(Integer.parseInt(removeZoneControllerIdBox.getValue().toString()),Integer.parseInt(removePortIdBox.getValue().toString()))!=null) {		
				ArrayList<Integer> sensorNum = main.getSensorId(Integer.parseInt(removeZoneControllerIdBox.getValue().toString()), Integer.parseInt(removePortIdBox.getValue().toString()));
				Collections.sort(sensorNum);
				ObservableList<Integer> sensorIdList = FXCollections.observableArrayList(sensorNum);
				removeSensorIdBox.setItems(sensorIdList);
			}
		}
	}
	
	@FXML
	private void removeArea() throws IOException {
		
		if (removeAreaIdBox.getValue() == null) {
			main.showAlert();
		}
		else {
			int areaIdToRemove = Integer.parseInt(removeAreaIdBox.getValue().toString());
			main.removeArea(areaIdToRemove);
			main.showRemoveElements();
			main.saveData();
		}
	}	
		
	@FXML
	private void removeSign() throws IOException {
	
		if (removeSignAreaIdBox.getValue() == null || removeSubSignIdBox.getValue() == null) {
			main.showAlert();
		}
		else {
			int signIdToRemove = Integer.parseInt(removeSignAreaIdBox.getValue().toString());
			int subSignIdToRemove = Integer.parseInt(removeSubSignIdBox.getValue().toString());
			main.removeSign(signIdToRemove, subSignIdToRemove);
			main.showRemoveElements();
			main.saveData();
		}
		
	}
	
	@FXML
	private void removeSensor() throws IOException {
		
		if(removeSensorIdBox.getValue() == null || removeZoneControllerIdBox.getValue() == null || removePortIdBox.getValue() == null) {
			main.showAlert();
		}
		else {
			int sensorZoneToRemove = Integer.parseInt(removeZoneControllerIdBox.getValue().toString());
			int sensorPortToRemove = Integer.parseInt(removePortIdBox.getValue().toString());
			int sensorIdToRemove = Integer.parseInt(removeSensorIdBox.getValue().toString());
			main.removeSensor(sensorZoneToRemove, sensorPortToRemove, sensorIdToRemove);
			main.showRemoveElements();
			main.saveData();
		}
		
	}
}
