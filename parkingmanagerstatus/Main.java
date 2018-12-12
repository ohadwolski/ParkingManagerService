package parkingmanagerstatus;
	
import java.io.IOException;
import parkingmanagerdata.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.javafx.css.Style;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	private static Stage PrimaryStage;
	private static BorderPane mainLayout;
	private static Status ParkingLot;
	private static TreeView<ParkingElement> treeList;
	
	
	@Override
	public void start(Stage primaryStage) throws IOException {

		ParkingLot = new Status();
		PrimaryStage = primaryStage;
		PrimaryStage.setTitle("Status");
		initTree();
		loadOperation();
		loadTree();
		showMainView();
		
	}
	
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
	
	public static void initTree() {
		
		treeList = new TreeView<ParkingElement>();
		TreeItem<ParkingElement> root =  new TreeItem<ParkingElement>(ParkingLot.getRootParkingElement());	
		treeList.setRoot(root);	
	}

	public static TreeItem<ParkingElement> getRoot(){
		
		return treeList.getRoot();		
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
			//TreeItem<ParkingElement> toAdd = null;
			TreeItem<ParkingElement> toAdd = new TreeItem<ParkingElement>(addToTree); 	
			/*if (addToTree.getStatus() == StatusElement.FREE) {
				
				toAdd = new TreeItem<ParkingElement>(addToTree); 		
						
			}
			else if (addToTree.getStatus() == StatusElement.TAKEN) {
				
				toAdd = new TreeItem<ParkingElement>(addToTree); 
			}
			else if (addToTree.getStatus() == StatusElement.ERROR){
				
				
				toAdd = new TreeItem<ParkingElement>(addToTree); 
			}
			else {
				toAdd = new TreeItem<ParkingElement>(addToTree); 
			}	*/
			searchTreeItem(treeList.getRoot(), ParkingLot.getParkingElementNode(element).getParent().getData()).getChildren().add(toAdd);			
		}
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
	public static void showMainView() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("statusItems.fxml"));	
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout,800,600);
		PrimaryStage.setScene(scene);
		PrimaryStage.show();
	}
	
	public static void showSpotsList() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("spotsData.fxml"));
		BorderPane freeSpots = loader.load();
		mainLayout.setCenter(freeSpots);		
	}
	
	
	public static void showSignData() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("signData.fxml"));
		BorderPane data = loader.load();
		mainLayout.setCenter(data);		
	}
	
	public static void showSensorData() throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("sensorData.fxml"));
		BorderPane data = loader.load();
		mainLayout.setCenter(data);		
	}

	public static ArrayList<Integer> getAreaIdList() {
		
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
	
	public static ArrayList<Integer> getSensorIdList() {
		
		ArrayList<Integer> ret = new ArrayList<Integer>();
		if (!ParkingLot.getSensorIdArray().isEmpty()) {
			for (SensorId id : ParkingLot.getSensorIdArray()) {
				if (!ret.contains(id.getSensorId())) {
					ret.add(id.getSensorId());
				}
			}
			return ret;
		}
		else { 
			return null;
		}
	}
	
	public static ArrayList<SensorId> getSensorId() {
		
		return ParkingLot.getSensorIdArray();
	}
	
	
	public static ArrayList<SignId> getSignId() {
		
		return ParkingLot.getSignIdArray();
	}
	
	public static Node<ParkingElement> getElementNode(IdElement id) {
		
		return ParkingLot.getParkingElementNode(id);
	}
	
	
	public static ParkingElement getElement(IdElement id) {
		
		return ParkingLot.getParkingElement(id);
	}
		
	
	public static void showAreaError() throws IOException {
		
		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText("Area was not choosed");
		alert.show();
	}

	public static void main(String[] args) {
		launch(args);
		
		
	}
}
