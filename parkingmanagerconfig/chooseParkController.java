package parkingmanagerconfig;

import java.io.IOException;
import parkingmanagerdata.*;

import javafx.fxml.FXML;

public class chooseParkController {

	private Main main;	
	
	
	@FXML
	private void goShowNewSetup() throws IOException {
		
		main.showNewSetup();
	}
	
	@FXML
	private void goShowOldSetup() throws IOException {
		
		main.loadData();
	}
	
	
	
}
