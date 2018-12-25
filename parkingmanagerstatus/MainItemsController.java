package parkingmanagerstatus;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

////Main menu fxml class
public class MainItemsController {

	private Main main;
	
	//go to the spots fxml page button
	@FXML
	private void goSpotsList() throws IOException {
		
		main.goLoadLog();
		if (main.isThereALog() != null) {
			main.refresh();
			main.showSpotsList();
		}
		/*else {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Log was not Loaded");
			alert.show();
		}*/
	}
	
	//go to the signs fxml page button
	@FXML
	private void goSignStatus() throws IOException {
		
		main.goLoadLog();
		if (main.isThereALog() != null) {
			main.refresh();
			main.showSignData();
		}
		/*else {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Log was not Loaded");
			alert.show();
		}*/	
	}
	
}
