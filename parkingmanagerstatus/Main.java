package parkingmanagerstatus;
	
import java.io.IOException;
import parkingmanagerdata.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

//The main class of the application
public class Main extends Application {
	
	private static Stage PrimaryStage; //app stage
	private static AnchorPane mainLayout; //app main layout
	private static Status ParkingLot; //app data structure
	private static TreeView<ParkingElement> treeList;  //park tree
	
	//initialize the application
	@Override
	public void start(Stage primaryStage) throws IOException {

		ParkingLot = new Status();
		PrimaryStage = primaryStage;
		PrimaryStage.setTitle("Status");		
		showMainView();	
	}
	
	//refresh of  the application method
	public static void refresh() {
		
		treeList = null;
		ParkingLot.areaIDList =  new ArrayList<AreaId>();
		ParkingLot.signIDList =  new ArrayList<SignId>();
		ParkingLot.sensorIDList =  new ArrayList<SensorId>();
		ParkingLot.loadDataInterface(); 
		initTree();
		loadOperation();
		loadTree();	
	}
	
	//load the data log method
	public static void goLoadLog() {
		
		ParkingLot.loadDataInterface();
		if (ParkingLot.getData() != null) {
			Alert alert = new Alert (AlertType.INFORMATION);
			alert.setTitle("Log Loading");
			alert.setContentText("Loaded Successfully");
			alert.show();
			initTree();
			loadOperation();
			loadTree();
		}
		else {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Error in the Loading Procedue");
			alert.show();
		}
	}
	
	//check if a data log exist
	public static ParkingManagerData isThereALog() {
		
		return ParkingLot.getData();
	}
	
	//initialize the park tree data structure
	public static void initTree() {
		
		treeList = new TreeView<ParkingElement>();
		TreeItem<ParkingElement> root =  new TreeItem<ParkingElement>(ParkingLot.getRootParkingElement());	
		treeList.setRoot(root);	
	}

	//get the root of the park tree
	public static TreeItem<ParkingElement> getRoot(){
		
		return treeList.getRoot();		
	}
	
	//handle the data log load operation
	public static void loadOperation() {
		
		ParkingLot.initAreasArray();
		ParkingLot.initSignsArray();
		ParkingLot.initSensorsArray();	
	}
	
	//load the park tree of the parking lot
	public static void loadTree() {
		
		AreaId root = new AreaId(0); 
		for (AreaId element:ParkingLot.getAreaIdArray()) {
			if (element.compare(root)) {
				ParkingElement addToTree = ParkingLot.getParkRoot().getData();
				TreeItem<ParkingElement> setRoot = new TreeItem<ParkingElement>(addToTree);
				treeList.setRoot(setRoot);
				treeList.getRoot().setExpanded(true);
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
	
	//search an element in the park tree data structure
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
	
	//show the fxml page of the main menu
	public static void showMainView() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();	
		loader.setLocation(Main.class.getResource("statusMainItems.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout,1024,768);
		PrimaryStage.setScene(scene);	    
		PrimaryStage.show();
	}
	
	//show the fxml page of the spots/sensors 
	public static void showSpotsList() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("spotsInfo.fxml"));
		AnchorPane freeSpots = loader.load();
		Scene scene = new Scene(freeSpots,1024,768);
		PrimaryStage.setScene(scene);	    
		PrimaryStage.show();
	}
	
	//show the fxml page of the signs
	public static void showSignData() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("signsInfo.fxml"));
		AnchorPane data = loader.load();
		Scene scene = new Scene(data,1024,768);
		PrimaryStage.setScene(scene);	    
		PrimaryStage.show();		
	}
	
	//return array of the Id of sensors in the parking lot
	public static ArrayList<SensorId> getSensorId() {
		
		return ParkingLot.getSensorIdArray();
	}
	
	//return array of the Id of signs in the parking lot
	public static ArrayList<SignId> getSignId() {
		
		return ParkingLot.getSignIdArray();
	}
	
	//returns element of the parking lot according to Id
	public static ParkingElement getElement(IdElement id) {
		
		return ParkingLot.getParkingElement(id);
	}
		
	public static void main(String[] args) {
		launch(args);
	}
}
