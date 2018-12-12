package parkingmanagerconfig;
	
import java.io.IOException;
import parkingmanagerdata.*;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import parkingmanagerservice.*;


public class Main extends Application {
	
	private static Stage PrimaryStage;
	private static BorderPane mainLayout;
	private static Setup ParkingLot;
	private static TreeView<ParkingElement> treeList;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		PrimaryStage = primaryStage;
		PrimaryStage.setTitle("Setup");
		showChooseView();	
	}
	
	public static void saveData() {
		
		ParkingLot.saveDataInterface();
	}
	
	public static void loadData() throws IOException {
		
		ParkingLot = new Setup(false);
		ParkingLot.loadDataInterface();
		showOldSetup();
	}
	
	public static void showChooseView() throws IOException {
		
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("choosePark.fxml"));		
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout,800,600);
		PrimaryStage.setScene(scene);
		PrimaryStage.show();
	}
	
	public static void showNewSetup() throws IOException {
		
		ParkingLot = new Setup(true);
		initTree() ;
		showMainView();
		showMainItems();
	}
	
	public static void showOldSetup() throws IOException {
		
		loadOperation();
		initTree() ;
		loadTree();
		showMainView();
		showMainItems();
	}
	
	
	public static void loadOperation() {
		
		ParkingLot.initAreasArray();		
		ParkingLot.initSignsArray();		
		ParkingLot.initSensorsArray();			
	}
	
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
		
	
	
	public static void showMainView() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("setup.fxml"));	
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout,800,600);
		PrimaryStage.setScene(scene);
		PrimaryStage.show();
	}
	
	public static void showMainItems() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("setupItems.fxml"));
		BorderPane mainItems = loader.load();
		mainLayout.setCenter(mainItems);	
	}
	
	public static void showAlert() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("At least one field was not filled");
		alert.show();
	}
	
	public static void showIdError() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("New Id is illegal");
		alert.show();
	}
	
	public static void showExistError() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("Id s already exist");
		alert.show();
	}
	
	public static void showRangeError() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("Update Interval or Port is not in range (0-65535)");
		alert.show();
	}
	
	public static void showHierError() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("Element can't be son of itself");
		alert.show();
	}
	

	public static void showDataSettings() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("dataSettings.fxml"));
		BorderPane dataSettings = loader.load();
		mainLayout.setCenter(dataSettings);		
	}
	
	public static void showServerSettings() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("serverSettings.fxml"));
		BorderPane serverSettings = loader.load();
		mainLayout.setCenter(serverSettings);
	}
	
	public static void closeWindow() throws IOException {
		
		PrimaryStage.close();	
	}
	
	public static void showAddElements() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("addElements.fxml"));
		BorderPane addElements = loader.load();
		mainLayout.setCenter(addElements);
	}
	
	public static void showRemoveElements() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("removeElements.fxml"));
		BorderPane removeElements = loader.load();
		mainLayout.setCenter(removeElements);
	}
	
	public static void showChangeHierarchy() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("changeHierarchy.fxml"));
		BorderPane changeHierarchy = loader.load();
		mainLayout.setCenter(changeHierarchy);
	}
	
	public static void showChangeConfiguration() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("changeConfiguration.fxml"));
		BorderPane changeConfiguration = loader.load();
		mainLayout.setCenter(changeConfiguration);
	}
	
	public static void initTree() {
		
		TreeItem<ParkingElement> root =  new TreeItem<ParkingElement>(ParkingLot.getRootParkingElement());
		treeList = new TreeView<ParkingElement>();
		treeList.setRoot(root);
	}
		
	public static TreeItem<ParkingElement> getRoot(){
		
		return treeList.getRoot();		
	}
	
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
	
	public static ArrayList<Integer> getSignId() {
		
		ArrayList<Integer> ret = new ArrayList<Integer>();
		if (!ParkingLot.getSignIdArray().isEmpty()) {
			for (SignId id : ParkingLot.getSignIdArray()) {
				if (!ret.contains(id.getSignId())) {
					ret.add(id.getSignId());
				}	
			}
			return ret;
		}
		else { 
			return null;
		}
	}
	
	public static ArrayList<Integer> getSubSignId(int signId) {
		
		ArrayList<Integer> ret = new ArrayList<Integer>();
		if (!ParkingLot.getSignIdArray().isEmpty()) {
			for (SignId id : ParkingLot.getSignIdArray()) {
				if (!ret.contains(id.getSubSignId()) && id.getSignId() == signId) {
					ret.add(id.getSubSignId());
				}				
			}
			return ret;
		}
		else { 
			return null;
		}
	}
	
	public static ArrayList<Integer> getZoneControllerId() {
		
		ArrayList<Integer> ret = new ArrayList<Integer>();
		if (!ParkingLot.getSensorIdArray().isEmpty()) {
			for (SensorId id : ParkingLot.getSensorIdArray()) {
				if (!ret.contains(id.getZoneControllerId())) {
					ret.add(id.getZoneControllerId());
				}	
			}
			return ret;
		}
		else { 
			return null;
		}
	}
	
	public static ArrayList<Integer> getPortId(int zoneControllerId) {
		
		ArrayList<Integer> ret = new ArrayList<Integer>();
		if (!ParkingLot.getSensorIdArray().isEmpty()) {
			for (SensorId id : ParkingLot.getSensorIdArray()) {
				if (!ret.contains(id.getControllerId()) && id.getZoneControllerId() == zoneControllerId) {
					ret.add(id.getControllerId());
				}	
			}
			return ret;
		}
		else { 
			return null;
		}
	}

	public static ArrayList<Integer> getSensorId(int zoneControllerId, int portId) {
	
		ArrayList<Integer> ret = new ArrayList<Integer>();
		if (!ParkingLot.getSensorIdArray().isEmpty()) {
			for (SensorId id : ParkingLot.getSensorIdArray()) {
				if (!ret.contains(id.getSensorId()) && id.getZoneControllerId() == zoneControllerId && id.getControllerId() == portId) {
					ret.add(id.getSensorId());
				}
			}
			return ret;
		}
		else { 
			return null;
		}
	}
	
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
	
	public static void changeAreaConfigAfterSubmit(int areaToConfig, ConfigurationElement newConfig) {
		
		AreaId areaConfig = new AreaId(areaToConfig);
		for (AreaId id : ParkingLot.getAreaIdArray()) {
			if (id.compare(areaConfig)) {
				ParkingLot.changeElementConfig(id, newConfig);
				break;
			}	
		}
	}
	
	public static void changeSignConfigAfterSubmit(int sign, int subSign, ConfigurationElement newConfig) {
		
		SignId signConfig = new SignId(sign, subSign);
		for (SignId id : ParkingLot.getSignIdArray()) {
			if (id.compare(signConfig)) {
				ParkingLot.changeElementConfig(id, newConfig);
				break;
			}	
		}
	}
	
	public static void changeSensorConfigAfterSubmit(int sensorZone, int sensorController, int sensor, ConfigurationElement newConfig) {
		
		SensorId sensorConfig = new SensorId(sensorZone, sensorController, sensor);
		for (SensorId id : ParkingLot.getSensorIdArray()) {
			if (id.compare(sensorConfig)) {
				ParkingLot.changeElementConfig(id, newConfig);
				break;
			}	
		}
	}
	
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
	
	public static boolean isAreaNumeric(String strNum) {
		
		return strNum.matches("[1-9]+");
	}
	
	public static boolean isSignNumeric(String strNum) {
		
		return strNum.matches("[0-9]+");
	}

	public static boolean isSubSignNumeric(String strNum) {
	
		return strNum.matches("[0-3]");
	}

	public static boolean isZoneNumeric(String strNum) {
	
		return strNum.matches("[0-9]+");
	}
	
	public static boolean isPortNumeric(String strNum) {
		
		return strNum.matches("[0-9]+");
	}
	
	public static boolean isSensorNumeric(String strNum) {
		
		return strNum.matches("[0-9]+");
	}
	
	public static boolean isEspPortNumeric(String strNum) {
		
		return strNum.matches("[0-9]+");
	}
	
	public static String getAutoInit() {

		if(ParkingLot.getAutoInit()) {
			return "Auto";
		}
		else {
			return "Manual";
		}
	}
	
	public static String getWorkingMode() {
		
		/*if(ParkingLot.getWorkingMode() == 0) {
			return "Manual by Server";
		}
		else if (ParkingLot.getWorkingMode() == 1) {
			return "On Event";
		}
		else {
			return "Every " + (ParkingLot.getWorkingMode()) + " Seconds";
		}*/
		return ""+ParkingLot.getWorkingMode();
	}
	
	public static String getUpdateInterval() {
	
		//return "Every " + (ParkingLot.getUpdateInterval() +" Seconds");
		return ""+ParkingLot.getUpdateInterval();
	}
	
	public static String getPort() {
		
		return ""+ParkingLot.getPort();
	}
	
	public static String getEspIPAddress() {
	
		return ParkingLot.getEspIpAddress();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
