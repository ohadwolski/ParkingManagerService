package parkingmanagerstatus;

import java.io.IOException;
import parkingmanagerdata.*;

import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;


public class signData {

	private Main main;
	
	/*@FXML
	private ListView signsData;*/
	
	@FXML
	private TableView signsData;
	

	@FXML
	private TableColumn signID;
	
	@FXML
	private TableColumn subSignID;
	
	@FXML
	private TableColumn status;
	
	@FXML
	private TableColumn counter;
	
	@FXML
	private TableColumn configuration;
	
	@FXML
	private TableColumn date;
	
	@FXML
	private TreeView<ParkingElement> ParkTree;
	
	
	@FXML
	private void goMainView() throws IOException {
		
		main.showMainView();
		//main.showMainItems();
		main.refresh();
	}

	@FXML
	private void refreshWindow() throws IOException {
		
		main.refresh();
		//main.showMainItems();
		main.showSignData();
	}
	
	@FXML
	private void initialize() {
		
		if (main.getSignId()!=null) {
			ArrayList<SignId> signArray = main.getSignId();
			ObservableList<signInfo> data = FXCollections.observableArrayList();
			for (SignId item : signArray) {
				ParkingSign signElement =(ParkingSign) main.getElement(item);
				int id = item.getSignId();
				int subId = item.getSubSignId();
				int counterOfSign = signElement.getCounter();
				StatusElement newStatus = main.getElement(item).getStatus();
				Date newDate = main.getElement(item).getTimeStamp();
				ConfigurationElement config = main.getElement(item).getConfiguration();
				signInfo newInfo = new signInfo(id, subId, newStatus, config, newDate, counterOfSign);
				signID.setCellValueFactory(new PropertyValueFactory<>("Id"));
				subSignID.setCellValueFactory(new PropertyValueFactory<>("SubSignId"));
				status.setCellValueFactory(new PropertyValueFactory<>("Status"));
				counter.setCellValueFactory(new PropertyValueFactory<>("Counter"));
				configuration.setCellValueFactory(new PropertyValueFactory<>("Configuration"));
				date.setCellValueFactory(new PropertyValueFactory<>("TimeStamp"));
				signsData.getItems().add(newInfo);
			 }
		}
		
		ParkTree.setRoot(main.getRoot());
		ParkTree.getRoot().setExpanded(true);
			
	}
		
}
