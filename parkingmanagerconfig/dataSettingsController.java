package parkingmanagerconfig;

import java.io.IOException;

import javafx.fxml.FXML;
import parkingmanagerdata.*;

public class dataSettingsController {

	private Main main;
	
	@FXML
	private void goAddElements() throws IOException {
		
		main.showAddElements();
	}
	
	@FXML
	private void goRemoveElements() throws IOException {
		
		main.showRemoveElements();
	}
	
	@FXML
	private void goChangeHierarchy() throws IOException {
		
		main.showChangeHierarchy();
	}
	
	@FXML
	private void goChangeConfiguratio() throws IOException {
		
		main.showChangeConfiguration();
	}
	
	@FXML
	private void goMainView() throws IOException {
		
		main.showMainView();
		main.showMainItems();
	}
	
}

