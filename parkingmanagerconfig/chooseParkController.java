package parkingmanagerconfig;

import java.io.IOException;


import javafx.fxml.FXML;

//first fxml page class
public class chooseParkController {

	private Main main;	
	
	//new Parking lot setup button
	@FXML
	private void goShowNewSetup() throws IOException {
		
		main.showNewSetup();
	}
	
	//edit an exist parking lot setup button
	@FXML
	private void goShowOldSetup() throws IOException, ClassNotFoundException {
		
		main.loadData();
	}
}
