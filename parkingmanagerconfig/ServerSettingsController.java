package parkingmanagerconfig;

import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

//server settings fxml class
public class ServerSettingsController {

	private Main main;
	
	@FXML
	private TextField updateInterval; //update interval field
	
	@FXML
	private TextField port; //port field
	
	@FXML
	private TextField espIPAddress; //esp ip address field
	
	@FXML
	private RadioButton auto; //auto setup option
	
	@FXML
	private RadioButton manual; //manual setup option
	
	@FXML
	private RadioButton manualByServer; //manual by server working mode option
	
	@FXML
	private RadioButton onEvent; //one-event working mode option
	
	@FXML
	private RadioButton everyTSeconds; //every-t-seconds working mode option
	
	//back button
	@FXML
	private void goMainView() throws IOException {
		
		main.showMainView();
	}
	
	//initialize the fxml page
	@FXML
	private void initialize() {
		
		port.setText(main.getPort());
		updateInterval.setText(main.getUpdateInterval());		
		espIPAddress.setText(main.getEspIPAddress());
		
		if (main.getAutoInit().equals("Auto")) {
			auto.setSelected(true);
		}
		else {
			auto.setSelected(false);
		}
		if (main.getAutoInit().equals("Manual")) {
			manual.setSelected(true);
		}
		else {
			manual.setSelected(false);
		}
		if (main.getWorkingMode().equals("0")) {
			manualByServer.setSelected(true);
			onEvent.setSelected(false);
			everyTSeconds.setSelected(false);
		}
		else if (main.getWorkingMode().equals("1")) {
			manualByServer.setSelected(false);
			onEvent.setSelected(true);
			everyTSeconds.setSelected(false);
		}
		else if (main.getWorkingMode().equals("2")) {
			manualByServer.setSelected(false);
			onEvent.setSelected(false);
			everyTSeconds.setSelected(true);
		}
	}
	
	//save as button
	@FXML
	private void saveData() throws IOException {
		
		main.saveData();
	}
	
	//submit button
	@FXML
	private void serverSettingsSaveAfterSubmit() throws IOException {
		
		boolean isIntervalInRange = true;
		boolean isPortInRange = true;
		
		boolean systemSetup = true;
		boolean setupFlag = false;
		
		int systemWorkingMode = -1;
		boolean  systemWorkingModeFlag= false;
		
		int systemUpdateInterval = -1;
		boolean updateIntervalFlag = false;
		
		String systemEspIpAddress = "";
		boolean  systemEspIpAddressFlag= false;
		
		int systemPort = -1;
		boolean systemPortFlag = false;
		
		if (auto.isSelected()) {
			systemSetup = true;
			setupFlag = true;
		}
		else if (manual.isSelected()){
			systemSetup = false;
			setupFlag = true;
		}
		
		if (manualByServer.isSelected()) {
			systemWorkingMode = 0;
			systemWorkingModeFlag = true;
		}
		else if (onEvent.isSelected()) {
			systemWorkingMode = 1;
			systemWorkingModeFlag = true;		
		}
		else if (everyTSeconds.isSelected()){
			systemWorkingMode = 2;
			systemWorkingModeFlag = true;		
		}
		
		if (!updateInterval.getText().trim().isEmpty()) {
			if (!updateInterval.getText().matches("[0-9]+")) {
				isIntervalInRange = false;
			}
			else {
				systemUpdateInterval = Integer.parseInt(updateInterval.getText());
				updateIntervalFlag = true;
			}	
		}
		
		if (!espIPAddress.getText().trim().isEmpty()) {
			
				systemEspIpAddress = espIPAddress.getText();
				systemEspIpAddressFlag= true;	
		}
		
		if (!port.getText().trim().isEmpty()) {
			if (!main.isEspPortNumeric(port.getText())) {			
				isPortInRange = false;
			}
			else {
				systemPort = Integer.parseInt(port.getText());
				systemPortFlag = true;		
			}		
		}
		if (setupFlag && systemWorkingModeFlag && updateIntervalFlag && systemEspIpAddressFlag && systemPortFlag) {
			
			main.serverSettingsSaveAfterSubmit(updateIntervalFlag, systemUpdateInterval, systemPortFlag, systemPort,
					systemEspIpAddressFlag, systemEspIpAddress, systemWorkingModeFlag, systemWorkingMode,
					setupFlag, systemSetup);
			main.showServerSettings();
		}
		else {
			if (!isIntervalInRange || !isPortInRange) {
				main.showRangeError();
			}
			else {
				main.showAlert();
			}	
		}	
	}
}
