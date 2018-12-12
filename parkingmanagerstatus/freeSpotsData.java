package parkingmanagerstatus;

import java.io.IOException;
import parkingmanagerdata.*;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;


public class freeSpotsData {
	
	private Main main;
	
	@FXML
	private TextArea totalSpots;
	
	@FXML
	private TextArea freeSpots;
	
	@FXML
	private ListView<ParkingElement> freeSpotsList;
	
	@FXML
	private ChoiceBox area;
	

	@FXML
	private void initialize() {
		
		int totalParkSpots = 0;
		int totalFreeSpots = 0;
		if (main.getSensorId()!=null) {
		
			ArrayList<Integer> areaNum = main.getAreaIdList();
			ObservableList<Integer> areaIdList = FXCollections.observableArrayList(areaNum);
			area.setItems(areaIdList);
		}
		
		if (main.getSensorId()!=null) {
			
			ArrayList<SensorId> sensorList = main.getSensorId();
			for (SensorId sensor : sensorList) {
				totalParkSpots++;
				if(main.getElement(sensor).getStatus() == StatusElement.FREE) {
					totalFreeSpots++;
				}
			}
		}
		totalSpots.appendText(""+totalParkSpots);
		freeSpots.appendText(""+totalFreeSpots);		
		//****************************************************************/
		ArrayList<SensorId> sensorList = main.getSensorId();
		for (SensorId sensor : sensorList) {
				
				
					freeSpotsList.getItems().add(main.getElement(sensor));
		}
		freeSpotsList.refresh();
		/////////////////////***///
		freeSpotsList.setCellFactory(lv -> new ListCell<ParkingElement>() {
	        @Override
	        protected void updateItem(ParkingElement item, boolean empty) {
	             super.updateItem(item, empty);
	               if (item.getStatus() == StatusElement.FREE) {
	                   
	                   setTextFill(Color.GREEN);
	                   setStyle("-fx-background-color: green;");
	               } 
	               else if(item.getStatus() == StatusElement.TAKEN) {
	                   
	            	   setTextFill(Color.RED);
	            	   setStyle("-fx-background-color: red;");
	               }
	               else if (item.getStatus() == StatusElement.ERROR) {
	            	   
	            	   setTextFill(Color.YELLOW);
	            	   setStyle("-fx-background-color: black;");
	               }
	            }
	        });
	}	
	
	@FXML
	private void goMainView() throws IOException {
		
		main.showMainView();
		//main.showMainItems();
	}
	
	@FXML
	private void showFreeSpotsList() throws IOException {
		
		freeSpotsList.getItems().clear();
		if (area.getValue()==null) {
			main.showAreaError();
		}
		else {
			
			ArrayList<SensorId> sensorList = main.getSensorId();
			for (SensorId sensor : sensorList) {
				
				if(main.getElementNode(sensor).getParent().getData().getId().get() == Integer.parseInt(area.getValue().toString())){
					
					if (main.getElement(sensor).getStatus() == StatusElement.FREE) {
						freeSpotsList.getItems().add(main.getElement(sensor));
					}
				}
				else {
					
					Node<ParkingElement> sensorOrigin = main.getElementNode(sensor);
					while (sensorOrigin!=null) {						
						if (sensorOrigin.getData().getId().get() ==Integer.parseInt(area.getValue().toString())) {
							if (main.getElement(sensor).getStatus() == StatusElement.FREE) {
								freeSpotsList.getItems().add(main.getElement(sensor));
								
							}
						}					
						sensorOrigin = sensorOrigin.getParent();					
					}
						
				}
					
			}		
		}
		freeSpotsList.refresh();
		
	}
}
