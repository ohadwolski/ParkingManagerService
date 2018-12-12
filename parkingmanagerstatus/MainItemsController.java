package parkingmanagerstatus;

import java.io.IOException;
import parkingmanagerdata.*;

import javafx.fxml.FXML;

public class MainItemsController {

	
	private Main main;
	
	@FXML
	private void goSpotsList() throws IOException {
		
		main.refresh();
		main.showSpotsList();
	}
		
	@FXML
	private void goSignStatus() throws IOException {
		
		main.refresh();
		main.showSignData();
	}
	
	@FXML
	private void goSensorStatus() throws IOException {
		
		main.refresh();
		main.showSensorData();
	}
	
	/*@FXML
	private void closeWindows() throws IOException {
		
		main.closeWindow();
	}*/
}
