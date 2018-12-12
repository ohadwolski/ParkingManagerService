package parkingmanagerstatus;

import java.io.IOException;
import parkingmanagerdata.*;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;


public class TakenSpotsData {

	private Main main;
	
	@FXML
	private TextArea totalSpots;
	
	@FXML
	private TextArea takenSpots;
	
	@FXML
	private ListView takenSpotsList;
	
	@FXML
	private ChoiceBox area;
	

	@FXML
	private void initialize() {
		
		int totalParkSpots = 0;
		int totalTakenSpots = 0;
		if (main.getSensorId()!=null) {
		
			ArrayList<Integer> areaNum = main.getAreaIdList();
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
			}
		}
		totalSpots.appendText(""+totalParkSpots);
		takenSpots.appendText(""+totalTakenSpots);		
	}	
	
	@FXML
	private void goMainView() throws IOException {
		
		main.showMainView();
		//main.showMainItems();
	}
	
	@FXML
	private void showTakenSpotsList() throws IOException {
		
		takenSpotsList.getItems().clear();
		if (area.getValue()==null) {
			main.showAreaError();
		}
		else {
			ArrayList<SensorId> sensorList = main.getSensorId();
			for (SensorId sensor : sensorList) {
				
				if(main.getElementNode(sensor).getParent().getData().getId().get() == Integer.parseInt(area.getValue().toString())){
					
					if (main.getElement(sensor).getStatus() == StatusElement.TAKEN) {
						takenSpotsList.getItems().add(main.getElement(sensor));
						
					}
				}
				else {
					
					Node<ParkingElement> sensorOrigin = main.getElementNode(sensor);
					while (sensorOrigin!=null) {
						if (sensorOrigin.getData().getId().get() ==Integer.parseInt(area.getValue().toString())) {
							if (main.getElement(sensor).getStatus() == StatusElement.TAKEN) {
								takenSpotsList.getItems().add(main.getElement(sensor));
								
							}
						}
						sensorOrigin = sensorOrigin.getParent();					
					}
						
				}
					
			}		
		}
		//main.showTakenSpots();
	}

}
