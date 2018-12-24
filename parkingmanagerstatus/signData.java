package parkingmanagerstatus;

import java.io.IOException;
import parkingmanagerdata.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

//Signs fxml class
public class signData {

	private Main main;
		
	@FXML
	private TableView signsData; //table of the signs
	
	@FXML
	private TableColumn signID; //sign id column
	
	@FXML
	private TableColumn subSignID; //sub-sign id column
	
	@FXML
	private TableColumn status; //status column
	
	@FXML
	private TableColumn counter; //counter column
	
	@FXML
	private TableColumn configuration; //configuration column
	
	@FXML
	private TableColumn date; //date column
	
	@FXML
	private TreeView<ParkingElement> ParkTree; //parking lot tree
	
	//back button
	@FXML
	private void goMainView() throws IOException {
		
		main.showMainView();
		main.refresh();
	}
	
	//initialize the signs page
	@FXML
	private void initialize() {
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {		
			main.refresh();
		    ParkTree.setRoot(main.getRoot());
		    ParkTree.getRoot().setExpanded(true);
		    ParkTree.refresh();
		    signsData.getItems().clear();
		    if (main.getSignId()!=null) {
		    	ArrayList<SignId> signArray = main.getSignId();
		    	ObservableList<signInfo> data = FXCollections.observableArrayList();
		    	ParkTree.refresh();
				ParkTree.refresh();
				for (SignId item : signArray) {
					ParkingSign signElement =(ParkingSign) main.getElement(item);
					int id = item.getSignId();
					int subId = item.getSubSignId();
					int counterOfSign = signElement.getCounter();
					ParkTree.refresh();
					ParkTree.refresh();
					StatusElement newStatus = main.getElement(item).getStatus();
					Date newDate = main.getElement(item).getTimeStamp();
					ConfigurationElement config = main.getElement(item).getConfiguration();
					ParkTree.refresh();
					ParkTree.refresh();
					signInfo newInfo = new signInfo(id, subId, newStatus, config, newDate, counterOfSign);
					signID.setCellValueFactory(new PropertyValueFactory<>("Id"));
					subSignID.setCellValueFactory(new PropertyValueFactory<>("SubSignId"));
					status.setCellValueFactory(new PropertyValueFactory<>("Status"));
					counter.setCellValueFactory(new PropertyValueFactory<>("Counter"));
					configuration.setCellValueFactory(new PropertyValueFactory<>("Configuration"));
					date.setCellValueFactory(new PropertyValueFactory<>("TimeStamp"));
					signsData.getItems().add(newInfo);
					
					status.setCellFactory(column -> {
				        return new TableCell<sensorInfo,String>() {
				            @Override
				            protected void updateItem(String item, boolean empty) {
				                super.updateItem(item, empty);

				                setText(empty ? "" : getItem().toString());
				                setGraphic(null);

				                TableRow<sensorInfo> currentRow = getTableRow();

				                if (!isEmpty()) {

				                   if(item.equals("ERROR")){
				                        currentRow.setStyle("-fx-background-color:ORANGE");
				                   }
				                }
				            }
				        };
				    });
				}
				ParkTree.setCellFactory(tv -> new TreeCell<ParkingElement>()
		        {
		            @Override
		            protected void updateItem(ParkingElement item, boolean empty)
		            {
		                super.updateItem(item, empty);

		                if (item != null)
		                {
		                    setText(item.toString());

		                    if (item.getStatus().equals(StatusElement.FREE))
		                    {
		                        setStyle("-fx-text-fill: green");
		                    }
		                    if (item.getStatus().equals(StatusElement.TAKEN))
		                    {
		                        setStyle("-fx-text-fill: red");
		                    }
		                    if (item.getStatus().equals(StatusElement.ERROR))
		                    {
		                        setStyle("-fx-text-fill: orange");
		                    }

		                }
		            }
		        });
				ParkTree.refresh();
				ParkTree.refresh();
		   }
		   ParkTree.refresh();
		   ParkTree.refresh();	
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
}	
