package parkingmanagerconfig;

import java.io.IOException;
import parkingmanagerdata.*;

import javafx.fxml.FXML;

public class MainItemsController {

	private Main main;
	
	@FXML
	private void goDataSettings() throws IOException {
		
		main.showDataSettings();
	}
	
	@FXML
	private void goServerSettings() throws IOException {
		
		main.showServerSettings();
	}
	
	@FXML
	private void goChooseView() throws IOException {
		
		main.showChooseView();
	}
	
}
