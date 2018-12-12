package parkingmanagerconfig;

import javafx.scene.control.TextField;
import parkingmanagerdata.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;


public class ServerSettingsController {

	private Main main;
	

	@FXML
	private TextField updateInterval;
	
	@FXML
	private TextField port;
	
	@FXML
	private TextField espIPAddress;
	
	@FXML
	private RadioButton auto;
	
	@FXML
	private RadioButton manual;
	
	@FXML
	private RadioButton manualByServer;
	
	@FXML
	private RadioButton onEvent;
	
	@FXML
	private RadioButton everyTSeconds;
	
	@FXML
	private TextArea currentSetup;
	
	@FXML
	private TextArea currentWorkingMode;
	
	@FXML
	private TextArea currentUpInt;
	
	@FXML
	private TextArea currentPort;
	
	@FXML
	private TextArea currentIP;
	
	@FXML
	private void goMainView() throws IOException {
		
		main.showMainView();
		main.showMainItems();
	}
	
	@FXML
	private void initialize() {
		
		//currentSetup.appendText(main.getAutoInit());
		//currentWorkingMode.appendText(main.getWorkingMode());
		//currentUpInt.appendText(main.getUpdateInterval());
		//currentPort.appendText(main.getPort());
		//currentIP.appendText(main.getEspIPAddress());
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
			main.saveData();
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
