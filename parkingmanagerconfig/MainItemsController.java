package parkingmanagerconfig;

import java.io.IOException;

import javafx.fxml.FXML;

//main menu fxml class
public class MainItemsController {

	private Main main;
	
	//data settings button
	@FXML
	private void goDataSettings() throws IOException {
		
		main.showDataSettings();
	}
	
	//server settings button
	@FXML
	private void goServerSettings() throws IOException {
		
		main.showServerSettings();
	}
	
	//back button
	@FXML
	private void goChooseView() throws IOException {
		
		main.showChooseView();
	}
}
