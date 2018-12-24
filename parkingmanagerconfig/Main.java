package parkingmanagerconfig;
	
import java.io.IOException;
import parkingmanagerdata.*;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


//The main class of the application
public class Main extends Application {
	
	private static Stage PrimaryStage; //app main stage
	private static AnchorPane mainLayout; //main layout
	private static Setup ParkingLot; //app data structure
	private static TreeView<ParkingElement> treeList;  //park tree
	private static TreeItem selectedItem =null; //current selected item in the park tree
	private static Stage configWindow; //configuration stage
	private static Stage hierarchyWindow; //hierarchy stage
	private static Stage addChoiceWindow; //add element choice stage
	private static Stage addAreaWindow; //add area stage
	private static Stage addSignWindow; //add sign stage
	private static Stage addSensorWindow; //add sensor stage
	
	//initialize the application
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		PrimaryStage = primaryStage;
		PrimaryStage.setTitle("Setup");
		showChooseView();	
	}
	
	//save data log
	public static void saveData() throws IOException {
		
		ParkingLot.saveDataInterface();
	}
	
	//load data log
	public static void loadData() throws IOException, ClassNotFoundException {
		
		ParkingLot = new Setup(false);
		ParkingLot.loadDataInterface();
		showOldSetup();
	}
	
	//save current selected item in the park tree
	public static void setSelectedItem(TreeItem item ) {
		
		selectedItem = item;
	}
	
	//returns current selected item in the park tree
	public static TreeItem getSelectedItem() {
		
		return selectedItem;
	}
	
	//loads first page of the application
	public static void showChooseView() throws IOException {
			
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("pickAPark.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout,1024,768);
		PrimaryStage.setScene(scene);
		PrimaryStage.show();
	}
	
	//loads new setup of the parking lot
	public static void showNewSetup() throws IOException {
		
		ParkingLot = new Setup(true);
		initTree() ;
		showMainView();
	}
	
	//loads old setup of the parking lot
	public static void showOldSetup() throws IOException {
		
		loadOperation();
		initTree() ;
		loadTree();
		showMainView();
	}
	
	//helper load data method
	public static void loadOperation() {
		
		ParkingLot.initAreasArray();		
		ParkingLot.initSignsArray();		
		ParkingLot.initSensorsArray();			
	}
	
	//loads the park tree of the parking lot
	public static void loadTree() {
		
		AreaId root = new AreaId(0); 
		for (AreaId element:ParkingLot.getAreaIdArray()) {
			if (element.compare(root)) {
				ParkingElement addToTree = ParkingLot.getParkRoot().getData();
				TreeItem<ParkingElement> setRoot = new TreeItem<ParkingElement>(addToTree); 
				treeList.setRoot(setRoot);
			}
			else {
				ParkingElement addToTree = ParkingLot.getParkingElement(element);
				TreeItem<ParkingElement> toAdd = new TreeItem<ParkingElement>(addToTree); 
				toAdd.setExpanded(true);
				searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(element).getParent().getData()).getChildren().add(toAdd);
			}			
		}	
		
		for (SignId element:ParkingLot.getSignIdArray()) {
			
			ParkingElement addToTree = ParkingLot.getParkingElement(element);
			TreeItem<ParkingElement> toAdd = new TreeItem<ParkingElement>(addToTree); 
			searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(element).getParent().getData()).getChildren().add(toAdd);					
		}	
		
		for (SensorId element:ParkingLot.getSensorIdArray()) {
			
			ParkingElement addToTree = ParkingLot.getParkingElement(element);
			TreeItem<ParkingElement> toAdd = new TreeItem<ParkingElement>(addToTree); 
			searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(element).getParent().getData()).getChildren().add(toAdd);				
		}	
	}
	
	//loads main menu page
	public static void showMainView() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("setupMainItems.fxml"));	
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout,1024,768);
		PrimaryStage.setScene(scene);
		PrimaryStage.show();
	}
	
	//configuration button error
	public static void showConfigurtionAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("No Elememt has been Choosed to be Configured ");
		alert.show();
	}

	//new configuration error
	public static void showNewConfigurtionAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("A new Configuration has not been Choosed");
		alert.show();
	}
	
	//remove button error
	public static void showRemoveAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("No Elememt has been Choosed to be Removed");
		alert.show();
	}
	
	//hierarchy button error
	public static void showHierarchyAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("An Elememt must be Choosed in order To change Hierarchy");
		alert.show();
	}
	
	//new hierarchy error - user didnt choose new hierarchy
	public static void showNewHierarchyAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("No New Parent Area has been Choosed");
		alert.show();
	}
	
	//new hierarchy error - area 0 cant be modified
	public static void showArea0HierarchyAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("Area 0 Can't be Modified");
		alert.show();
	}
	
	//new hierarchy error - area cant be moved to a contained area
	public static void showAreaConatinedHierarchyAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("Area Can't be Moved to a Contained Area");
		alert.show();
	}
	
	//new hierarchy error - area cant contain itself
	public static void showAreaItselfHierarchyAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("Area Can't be a Parent Area of Itself");
		alert.show();
	}
	
	//add area error - new id has not been set
	public static void showAddAreaNoIdAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The New Area ID has not been Set");
		alert.show();
	}
	
	//add area error - configuration has not been set
	public static void showAddAreaNoConfigAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The Configuration of the New Area has not been Set");
		alert.show();
	}
	
	//add area error - new id is illegal
	public static void showAddAreaIllegalIdAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The New Area ID is Illegal");
		alert.show();
	}
	
	//add sign error - new id has not been set
	public static void showAddSignNoIdAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The New Sign ID has not been Set");
		alert.show();
	}
	
	//add sign error - new sub id has not been set
	public static void showAddSubSignNoIdAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The New Sub-Sign ID has not been Set");
		alert.show();
	}
	
	//add sign error - configuration has not been set
	public static void showAddSignNoConfigAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The Configuration of the New Sign has not been Set");
		alert.show();
	}
	
	//add sign error - new id is illegal
	public static void showAddSignIllegalIdAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The New Sign ID is Illegal");
		alert.show();
	}
	
	//add sign error - new sub id is illegal
	public static void showAddSubSignIllegalIdAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The New Sub-Sign ID is Illegal");
		alert.show();
	}
	
	//add sensor error - new zone id has not been set
	public static void showAddZoneNoIdAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The New Zone Controller ID has not been Set");
		alert.show();
	}
	
	//add sensor error - new port id has not been set
	public static void showAddPortNoIdAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The New Port ID has not been Set");
		alert.show();
	}
	
	//add sensor error - new sensor id has not been set
	public static void showAddSensorNoIdAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The New Sensor ID has not been Set");
		alert.show();
	}
	
	//add sensor error - configuration has not been set
	public static void showAddSensorNoConfigAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The Configuration of the New Sensor has not been Set");
		alert.show();
	}
	
	//add sensor error - new zone id is illegal
	public static void showAddZoneIllegalIdAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The New Zone Controller ID is Illegal");
		alert.show();
	}
	
	//add sensor error - new port id is illegal
	public static void showAddPortIllegalIdAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The New Port ID is Illegal");
		alert.show();
	}
	
	//add sensor error - new sensor id is illegal
	public static void showAddSensorIllegalIdAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("The New Sensor ID is Illegal");
		alert.show();
	}

	//Add button error
	public static void showAddElementsAlert() throws IOException {
	
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("An Area must be Choosed in order To Add Elements");
		alert.show();
	}
	
	//server settings - a setting has not been filled alert
	public static void showAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("At least one field was not filled");
		alert.show();
	}
	
	//Id is already exist alert
	public static void showExistError(int type) throws IOException { 
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		if (type == 0) {
			alert.setContentText("Area Id is already Exist");
		}
		else if (type == 1) {
			alert.setContentText("Sign Id is already Exist");
		}
		else if (type==2) {
			alert.setContentText("Sensor Id is already Exist");
		}
		
		alert.show();
	}
	
	//server settings port/update interval range alert
	public static void showRangeError() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("Update Interval or Port is not in range (0-65535)");
		alert.show();
	}
	
	//loads data settings page of the application
	public static void showDataSettings() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("setupDataSettings.fxml"));
		AnchorPane dataSettings = loader.load();
		Scene scene = new Scene(dataSettings,1024,768);
		PrimaryStage.setScene(scene);
		PrimaryStage.show();	
	}
	
	//loads server settings page of the application
	public static void showServerSettings() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("setupServerSettings.fxml"));
		AnchorPane serverSettings = loader.load();
		Scene scene = new Scene(serverSettings,1024,768);
		PrimaryStage.setScene(scene);
		PrimaryStage.show();
	}
	
	//loads change configuration windows of the application
	public static void changeConfiguration() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("configurationController.fxml"));
		AnchorPane config = loader.load();
		configWindow = new Stage();
		Scene scene = new Scene(config,300,300);
		configWindow.setScene(scene);
		configWindow.initModality(Modality.APPLICATION_MODAL);
		configWindow.show();	
	}
	
	//loads add elements windows of the application
	public static void addElements() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("addElementsController.fxml"));
		AnchorPane addElements = loader.load();
		addChoiceWindow = new Stage();
		Scene scene = new Scene(addElements,300,300);
		addChoiceWindow.setScene(scene);
		addChoiceWindow.initModality(Modality.APPLICATION_MODAL);
		addChoiceWindow.show();		
	}
	
	//loads add area window of the application
	public static void addArea() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("addArea.fxml"));
		AnchorPane addArea = loader.load();
		addAreaWindow = new Stage();
		Scene scene = new Scene(addArea,300,300);
		addAreaWindow.setScene(scene);
		addAreaWindow.initModality(Modality.APPLICATION_MODAL);
		addAreaWindow.show();
		addChoiceWindow.close();		
	}
	
	//loads add sign window of the application
	public static void addSign() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("addSign.fxml"));
		AnchorPane addSign = loader.load();
		addSignWindow = new Stage();
		Scene scene = new Scene(addSign,300,300);
		addSignWindow.setScene(scene);
		addSignWindow.initModality(Modality.APPLICATION_MODAL);
		addSignWindow.show();
		addChoiceWindow.close();		
	}

	//loads add sensor window of the application
	public static void addSensor() throws IOException {
	
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("addSensor.fxml"));
		AnchorPane addSensor = loader.load();
		addSensorWindow = new Stage();
		Scene scene = new Scene(addSensor,300,300);
		addSensorWindow.setScene(scene);
		addSensorWindow.initModality(Modality.APPLICATION_MODAL);
		addSensorWindow.show();
		addChoiceWindow.close();	
	}
	
	//close window of the application
	public static void closeWindow() throws IOException {
		
		PrimaryStage.close();	
	}
	
	//close configuration window of the application
	public static void closeConfigWindows() {
		
		configWindow.close();
	}
	
	//close add element choice window of the application
	public static void closeAddChoiceWindow() {
		
		addChoiceWindow.close();
	}
	
	//close add area window of the application
	public static void closeAddAreaWindow() {
		
		addAreaWindow.close();
	}

	//close add sign window of the application
	public static void closeAddSignWindow() {
	
		addSignWindow.close();
	}

	//close add sensor window of the application
	public static void closeAddSensorWindow() {
	
		addSensorWindow.close();
	}
	
	//close hierarchy window of the application
	public static void closeHierarchyWindow() {
		
		hierarchyWindow.close();
	}
	
	//loads hierarchy window of the application
	public static void showChangeHierarchy() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("hierarchyController.fxml"));
		AnchorPane changeHierarchy = loader.load();
		hierarchyWindow = new Stage();
		Scene scene = new Scene(changeHierarchy,300,300);
		hierarchyWindow.setScene(scene);
		hierarchyWindow.initModality(Modality.APPLICATION_MODAL);
		hierarchyWindow.show();
	}
	
	//initialize the park tree of the parking lot
	public static void initTree() {
		
		TreeItem<ParkingElement> root =  new TreeItem<ParkingElement>(ParkingLot.getRootParkingElement());
		treeList = new TreeView<ParkingElement>();
		treeList.setRoot(root);
	}
		
	//return the root of the parking lot tree
	public static TreeItem<ParkingElement> getRoot(){
		
		return treeList.getRoot();		
	}
	
	//search an elemnt in the park tree
	public static TreeItem<ParkingElement> searchTreeItem(TreeItem<ParkingElement> item, ParkingElement name) {
        
	    if(item.getValue().equals(name)) { 
	    	return item; 
	    }	
	    TreeItem<ParkingElement> result = null;
	    for(TreeItem<ParkingElement> child : item.getChildren()){
	         result = searchTreeItem(child, name);
	         if(result != null) {
	        	 return result; 
	         }
	    }
	    return null;
	}

	//return an array of the areas id in the parking lot
	public static ArrayList<Integer> getAreaId() {
		
		ArrayList<Integer> ret = new ArrayList<Integer>();
		if (!ParkingLot.getAreaIdArray().isEmpty()) {
			for (AreaId id : ParkingLot.getAreaIdArray()) {
				if (!ret.contains(id.getAreaId())) {
					ret.add(id.getAreaId());
				}	
			}
			return ret;
		}
		else { 
			return null;
		}	
	}
	
	//set server settings of the parking lot
	public static void serverSettingsSaveAfterSubmit(boolean updateFlag, int updateInterval, boolean portFlag, int port, 
														boolean ipAddressFlag, String espIpAddress, boolean workingModeFlag, 
														int workingMode, boolean setupFlag, boolean setup) {
		
		if (setupFlag) {
			ParkingLot.setAutoInit(setup);
		}
		
		if (workingModeFlag) {
			ParkingLot.setWorkingMode(workingMode);
		}
		
		if (updateFlag) {
			ParkingLot.setUpdateInterval(updateInterval);
		}
		
		if (ipAddressFlag) {
			ParkingLot.setIpAddress(espIpAddress);
		}
		
		if (portFlag) {
			ParkingLot.setPort(port);
		}
	}
	
	//add area to the parking lot
	public static boolean addAreaAfterSubmit(int parent, int newAreaId, ConfigurationElement config, StatusElement status) {
		
		boolean flag = true;
		AreaId parentOfNew = new AreaId(parent);
		AreaId checkIfExist = new AreaId(newAreaId);
		for (AreaId check : ParkingLot.getAreaIdArray()) {
			if (check.compare(checkIfExist)) {
				flag = false;
				break;
			}
		}
		if (flag) {
			for (AreaId id : ParkingLot.getAreaIdArray()) {
				if (id.compare(parentOfNew)) {
					ParkingElement addToTree = ParkingLot.addParkingArea(newAreaId, status, config, id);
					TreeItem<ParkingElement> toAdd = new TreeItem<ParkingElement>(addToTree); 
					searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(id).getData()).getChildren().add(toAdd);
					searchTreeItem(treeList.getRoot(), addToTree).setExpanded(true);
					break;
				}	
			}	
		}
		return flag;	
	}
	
	//add sign to the parking lot
	public static boolean addSignAfterSubmit(int parent, int newSignId, int subSignId, ConfigurationElement config, StatusElement status) {
		
		boolean flag = true;
		AreaId parentOfNew = new AreaId(parent);
		SignId checkIfExist = new SignId(newSignId,subSignId);
		for (SignId check : ParkingLot.getSignIdArray()) {
			if (check.compare(checkIfExist)) {
				flag = false;
				break;
			}
		}
		if (flag) {
			for (AreaId id : ParkingLot.getAreaIdArray()) {
				if (id.compare(parentOfNew)) {
					ParkingElement addToTree = ParkingLot.addParkingSign(newSignId, subSignId, status, config, id);
					TreeItem<ParkingElement> toAdd = new TreeItem<ParkingElement>(addToTree); 				
					searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(id).getData()).getChildren().add(toAdd);
					break;
				}	
			}
		}
		return flag;	
	}
	
	//add sensor to the parking lot
	public static boolean addSensorAfterSubmit(int parent, int newSersorZoneId, int newSensorControllerId, int newSensorId, ConfigurationElement config, StatusElement status) {
		
		boolean flag = true;
		AreaId parentOfNew = new AreaId(parent);
		SensorId checkIfExist = new SensorId(newSersorZoneId,newSensorControllerId, newSensorId);
		for (SensorId check : ParkingLot.getSensorIdArray()) {
			if (check.compare(checkIfExist)) {
				flag = false;
				break;
			}
		}
		if (flag) {
			for (AreaId id : ParkingLot.getAreaIdArray()) {
				if (id.compare(parentOfNew)) {
					ParkingElement addToTree = ParkingLot.addParkingSensor(newSersorZoneId, newSensorControllerId, newSensorId, status, config, id);
					TreeItem<ParkingElement> toAdd = new TreeItem<ParkingElement>(addToTree); 
					searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(id).getData()).getChildren().add(toAdd);
					break;
				}	
			}
		}
		return flag;
	}
	
	//remove area from the parking lot
	public static void removeArea(int areaToRemove) {
		
		AreaId areaRemove = new AreaId(areaToRemove);
		for (AreaId id : ParkingLot.getAreaIdArray()) {
			if (id.compare(areaRemove)) {
				ParkingElement removeFromTree = ParkingLot.getParkingElementNode(id).getData();
				Iterator<TreeItem<ParkingElement>> treeIter = searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(id).getParent().getData()).getChildren().iterator(); 			
				while (treeIter.hasNext()) {
					if (treeIter.next().getValue().getId().compare(removeFromTree.getId())) {
						treeIter.remove();
					}
				}	
				ParkingLot.findElementsTorRemove(id);
				break;
			}
		}	
		ParkingLot.removeElements();
	}
	
	//remove sign from the parking lot
	public static void removeSign(int signToRemove, int subSignIdToRemove) {
		
		SignId signRemove = new SignId(signToRemove, subSignIdToRemove);
		for (SignId id : ParkingLot.getSignIdArray()) {
			if (id.compare(signRemove)) {
				ParkingElement removeFromTree = ParkingLot.getParkingElementNode(id).getData();
				Iterator<TreeItem<ParkingElement>> treeIter = searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(id).getParent().getData()).getChildren().iterator(); 			
				while (treeIter.hasNext()) {
					if (treeIter.next().getValue().getId().compare(removeFromTree.getId())) {
						treeIter.remove();
					}
				}	
				ParkingLot.findElementsTorRemove(id);
				break;
			}	
		}
		ParkingLot.removeElements();		
	}
	
	//remove sensor from the parking lot
	public static void removeSensor(int sensorZoneToRemove, int sensorControllerToRemove, int sensorIdToRemove) {
		
		SensorId sensorRemove = new SensorId(sensorZoneToRemove, sensorControllerToRemove, sensorIdToRemove);
		for (SensorId id : ParkingLot.getSensorIdArray()) {
			if (id.compare(sensorRemove)) {
				ParkingElement removeFromTree = ParkingLot.getParkingElementNode(id).getData();
				Iterator<TreeItem<ParkingElement>> treeIter = searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(id).getParent().getData()).getChildren().iterator(); 			
				while (treeIter.hasNext()) {
					if (treeIter.next().getValue().getId().compare(removeFromTree.getId())) {
						treeIter.remove();
					}
				}	
				ParkingLot.findElementsTorRemove(id);
				break;
			}	
		}	
		ParkingLot.removeElements();
	}
	
	//change area configuration
	public static void changeAreaConfigAfterSubmit(int areaToConfig, ConfigurationElement newConfig) {
		
		AreaId areaConfig = new AreaId(areaToConfig);
		for (AreaId id : ParkingLot.getAreaIdArray()) {
			if (id.compare(areaConfig)) {
				ParkingLot.changeElementConfig(id, newConfig);
				break;
			}	
		}
	}
	
	//change sign configuration
	public static void changeSignConfigAfterSubmit(int sign, int subSign, ConfigurationElement newConfig) {
		
		SignId signConfig = new SignId(sign, subSign);
		for (SignId id : ParkingLot.getSignIdArray()) {
			if (id.compare(signConfig)) {
				ParkingLot.changeElementConfig(id, newConfig);
				break;
			}	
		}
	}
	
	//change sensor configuration
	public static void changeSensorConfigAfterSubmit(int sensorZone, int sensorController, int sensor, ConfigurationElement newConfig) {
		
		SensorId sensorConfig = new SensorId(sensorZone, sensorController, sensor);
		for (SensorId id : ParkingLot.getSensorIdArray()) {
			if (id.compare(sensorConfig)) {
				ParkingLot.changeElementConfig(id, newConfig);
				break;
			}	
		}
	}
	
	//change area hierarchy
	public static void changeAreaHierarchyAfterSubmit(int Parent, int area) {
			
		AreaId newParent = new AreaId (Parent);
		AreaId areaToChange = new AreaId (area);
		for (AreaId id : ParkingLot.getAreaIdArray()) {
			if (id.compare(newParent)) {
				for (AreaId idToChange : ParkingLot.getAreaIdArray()) {
					if (idToChange.compare(areaToChange)) {
						ParkingElement removeFromTree = ParkingLot.getParkingElementNode(idToChange).getData();
						TreeItem<ParkingElement> toAdd = searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(idToChange).getData());
						Iterator<TreeItem<ParkingElement>> treeIter = searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(idToChange).getParent().getData()).getChildren().iterator(); 			
						while (treeIter.hasNext()) {
							if (treeIter.next().getValue().getId().compare(removeFromTree.getId())) {
								treeIter.remove();
							}
						}					
						searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(id).getData()).getChildren().add(toAdd);
						ParkingLot.changeParentOfParkingElement(idToChange, id);
						break;
					}
				}
			}	
		}			
	}
	
	//change sign hierarchy
	public static void changeSignHierarchyAfterSubmit(int Parent, int sign, int subSign) {
		
		AreaId newParent = new AreaId (Parent);
		SignId signToChange = new SignId (sign, subSign);
		for (AreaId id : ParkingLot.getAreaIdArray()) {
			if (id.compare(newParent)) {
				for (SignId idToChange : ParkingLot.getSignIdArray()) {
					if (idToChange.compare(signToChange)) {
						ParkingElement removeFromTree = ParkingLot.getParkingElementNode(idToChange).getData(); 
						TreeItem<ParkingElement> toAdd = searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(idToChange).getData());
						Iterator<TreeItem<ParkingElement>> treeIter = searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(idToChange).getParent().getData()).getChildren().iterator(); 			
						while (treeIter.hasNext()) {
							if (treeIter.next().getValue().getId().compare(removeFromTree.getId())) {
								treeIter.remove();
							}
						}					
						searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(id).getData()).getChildren().add(toAdd);
						ParkingLot.changeParentOfParkingElement(idToChange, id);
						break;
					}
				}
			}	
		}			
	}
	
	//change sensor hierarchy
	public static void changeSensorHierarchyAfterSubmit(int Parent, int zone, int controller, int sensor) {
		
		AreaId newParent = new AreaId (Parent);
		SensorId sensorToChange = new SensorId (zone, controller, sensor);
		for (AreaId id : ParkingLot.getAreaIdArray()) {
			if (id.compare(newParent)) {
				for (SensorId idToChange : ParkingLot.getSensorIdArray()) {
					if (idToChange.compare(sensorToChange)) {
						ParkingElement removeFromTree = ParkingLot.getParkingElementNode(idToChange).getData(); 
						TreeItem<ParkingElement> toAdd = searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(idToChange).getData());
						Iterator<TreeItem<ParkingElement>> treeIter = searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(idToChange).getParent().getData()).getChildren().iterator(); 			
						while (treeIter.hasNext()) {
							if (treeIter.next().getValue().getId().compare(removeFromTree.getId())) {
								treeIter.remove();
							}
						}					
						searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(id).getData()).getChildren().add(toAdd);
						ParkingLot.changeParentOfParkingElement(idToChange, id);
						break;
					}
				}
			}	
		}			
	}
	
	//returns parking element node according to id
	public static Node<ParkingElement> getParkingElementNode (IdElement id) {
		
		return ParkingLot.getParkingElementNode(id);
	}
	
	//checks if area id is legal
	public static boolean isAreaNumeric(String strNum) {
		
		return strNum.matches("[1-9]+");
	}
	
	//checks if sign id is legal
	public static boolean isSignNumeric(String strNum) {
		
		return strNum.matches("[0-9]+");
	}
	
	//checks if sub sign id is legal
	public static boolean isSubSignNumeric(String strNum) {
	
		return strNum.matches("[0-3]");
	}

	//checks if sensor zone id is legal
	public static boolean isZoneNumeric(String strNum) {
	
		return strNum.matches("[0-9]+");
	}
	
	//checks if sensor port id is legal
	public static boolean isPortNumeric(String strNum) {
		
		return strNum.matches("[0-9]+");
	}
	
	//checks if sensor id is legal
	public static boolean isSensorNumeric(String strNum) {
		
		return ((strNum.matches("[0-9]+")) && (Integer.parseInt(strNum)<32));
	}
	
	//checks if server port is legal
	public static boolean isEspPortNumeric(String strNum) {
		
		return strNum.matches("[0-9]+");
	}
	
	//returns the server setup mode
	public static String getAutoInit() {

		if(ParkingLot.getAutoInit()) {
			return "Auto";
		}
		else {
			return "Manual";
		}
	}
	
	//returns the server working mode
	public static String getWorkingMode() {
		
		return ""+ParkingLot.getWorkingMode();
	}
	
	//returns the server update interval
	public static String getUpdateInterval() {
	
		return ""+ParkingLot.getUpdateInterval();
	}
	
	//returns the server port
	public static String getPort() {
		
		return ""+ParkingLot.getPort();
	}
	
	//returns the server esp ip address
	public static String getEspIPAddress() {
	
		return ParkingLot.getEspIpAddress();
	}
	
	public static void main(String[] args) {
		launch(args);
	}	   
}
