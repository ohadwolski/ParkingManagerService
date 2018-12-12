package parkingmanagerstatus;

import java.io.IOException;
import parkingmanagerdata.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;


public class spotsData {

private Main main;
	
	@FXML
	private TextArea totalSpots;
	
	@FXML
	private TextArea freeSpots;
	
	@FXML
	private TextArea takenSpots;
	
	@FXML
	private ListView<ParkingElement> SpotsList;
	
	@FXML
	private ChoiceBox area;
	
	@FXML
	private TreeView<ParkingElement> ParkTree;
	
	/*@FXML
	private TreeElement ParkTree;*/
	
	@FXML
	private void initialize() {
		
		/*int totalParkSpots = 0;
		int totalTakenSpots = 0;
		int totalFreeSpots = 0;
		if (main.getSensorId()!=null) {
		
			ArrayList<Integer> areaNum = main.getAreaIdList();
			Collections.sort(areaNum);
			ObservableList<Integer> areaIdList = FXCollections.observableArrayList(areaNum);
			area.setItems(areaIdList);
		}
		
		if (main.getSensorId()!=null) {
			
			ArrayList<SensorId> sensorList = main.getSensorId();
			for (SensorId sensor : sensorList) {
				totalParkSpots++;
				if(main.getElement(sensor).getStatus() == StatusElement.TAKEN) {
					totalTakenSpots++;
				}
				if(main.getElement(sensor).getStatus() == StatusElement.FREE) {
					totalFreeSpots++;
				}
			}
		}
		totalSpots.appendText(""+totalParkSpots);
		takenSpots.appendText(""+totalTakenSpots);
		freeSpots.appendText(""+totalFreeSpots);
		
		ParkTree.setRoot(main.getRoot());
		ParkTree.getRoot().setExpanded(true);*/
		
	/*-----------------*/
		Timer timer = new Timer();
		 timer.schedule(new TimerTask() {
		    @Override
		    public void run() { // Function runs every MINUTES minutes.
		    	main.refresh();
		    	ParkTree.setRoot(main.getRoot());
				ParkTree.getRoot().setExpanded(true);
				
				ParkTree.refresh();
		    	int totalParkSpots = 0;
				int totalTakenSpots = 0;
				int totalFreeSpots = 0;
				ParkTree.refresh();
				if (main.getSensorId()!=null) {
				
					ArrayList<Integer> areaNum = main.getAreaIdList();
					Collections.sort(areaNum);
					ObservableList<Integer> areaIdList = FXCollections.observableArrayList(areaNum);
					area.setItems(areaIdList);
				}
				ParkTree.refresh();
				if (main.getSensorId()!=null) {
					
					ArrayList<SensorId> sensorList = main.getSensorId();
					for (SensorId sensor : sensorList) {
						totalParkSpots++;
						if(main.getElement(sensor).getStatus() == StatusElement.TAKEN) {
							totalTakenSpots++;
						}
						if(main.getElement(sensor).getStatus() == StatusElement.FREE) {
							totalFreeSpots++;
						}
					}
				}
				ParkTree.refresh();
				totalSpots.setText("Total spots: "+totalParkSpots);
				takenSpots.setText("Taken spots: "+totalTakenSpots);
				freeSpots.setText("Free spots: "+totalFreeSpots);
				ParkTree.refresh();
				//ParkTree.setRoot(main.getRoot());
				//ParkTree.getRoot().setExpanded(true);
				ParkTree.refresh();
		    }
		 }, 0, 1000*15  );
	
	}	

	/*@FXML
	private void refreshing() throws IOException{
		  main.refresh();    	
		 // main.showSpotsList();
		
	}*/
	
	
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
		main.showSpotsList();
	}
	
	@FXML
	private void showSpotsList() throws IOException {
		
		SpotsList.getItems().clear();
		if (area.getValue()==null) {
			main.showAreaError();
		}
		else {
			
			ArrayList<SensorId> sensorList = main.getSensorId();
			for (SensorId sensor : sensorList) {
				
				if(main.getElementNode(sensor).getParent().getData().getId().get() == Integer.parseInt(area.getValue().toString())){				
					
					SpotsList.getItems().add(main.getElement(sensor));
					
					
				}
				else {
					
					Node<ParkingElement> sensorOrigin = main.getElementNode(sensor);
					while (sensorOrigin!=null) {						
						if (sensorOrigin.getData().getId().get() ==Integer.parseInt(area.getValue().toString())) {
							
							SpotsList.getItems().add(main.getElement(sensor));	
						}					
						sensorOrigin = sensorOrigin.getParent();					
					}
						
				}
					
			}		
		}
		SpotsList.refresh();
	
	}
}
