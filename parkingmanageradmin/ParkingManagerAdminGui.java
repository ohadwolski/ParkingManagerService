package parkingmanageradmin;



import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ParkingManagerAdminGui extends Application {

	
	public static void main(String[] args) {
        launch(args);
    
	}

	@Override
	public void start(Stage primaryStage) {

	    primaryStage.setTitle("Admin Manager");
	    Pane root = new Pane();
	    
	    Button btn = new Button("Say 'Hello World'");
	    Button setupBtn = new Button("Setup");
	    Button currentStatusBtn = new Button("Current Status");
	    Button logsBtn = new Button("Logs");
	     
	    setupBtn.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
	    currentStatusBtn.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
	    logsBtn.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
	   
	    setupBtn.setLayoutX(350);
	    setupBtn.setLayoutY(50);
	    setupBtn.setPrefSize(200, 200);
	    currentStatusBtn.setLayoutX(350);
	    currentStatusBtn.setLayoutY(350);
	    currentStatusBtn.setPrefSize(200, 200);
	    logsBtn.setLayoutX(350);
	    logsBtn.setLayoutY(650);
	    logsBtn.setPrefSize(200, 200);
	     
	    root.getChildren().add(btn);
	    root.getChildren().add(setupBtn);
	    root.getChildren().add(currentStatusBtn);
	    root.getChildren().add(logsBtn);
	    
	    primaryStage.setScene(new Scene(root, 900, 900));
	    primaryStage.show();
	      
	    btn.setOnAction(new EventHandler<ActionEvent>() {
	    		
	        @Override
	        public void handle(ActionEvent event) {
	            System.out.println("Hello World!");
	            }
	      });
	     
	    setupBtn.setOnAction(new EventHandler<ActionEvent>() {
	    	
	        @Override
	        public void handle(ActionEvent event) {
	        	
	        	Stage setupStage = new Stage();
	     	    setupStage.setTitle("Setup");
	        	Pane root2 = new Pane();
	        	   
	        	Button parkStructBtn = new Button("Park Structure");
	        	Button addAreaBtn = new Button("Add Level or Area");
	        	Button addSensorBtn = new Button("Add Sensor");
	        	Button removeAreaBtn = new Button("Remove Level or Area");
	        	Button removeSensorBtn = new Button("Remove Sensor");
	        	Button areaConfigBtn = new Button("Change Configuration of Level or Area");
	        	Button sesnsorConfigBtn = new Button("Change Configuration of Sensor");
	        	Button areaStatusBtn = new Button("Change Status of Area");
	        	Button sensorStatusBtn = new Button("Change Status of Sensor");
	        	Button setupBackBtn = new Button("Back");
	        	  
	        	parkStructBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	addAreaBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	addSensorBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	removeAreaBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	removeSensorBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	areaConfigBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	sesnsorConfigBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	areaStatusBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	sensorStatusBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	setupBackBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	   
	        	parkStructBtn.setLayoutX(300);
	        	parkStructBtn.setLayoutY(36);
	        	parkStructBtn.setPrefSize(300,60);       
	        	addAreaBtn.setLayoutX(300);
	        	addAreaBtn.setLayoutY(132);
	        	addAreaBtn.setPrefSize(300,60);   
	        	addSensorBtn.setLayoutX(300);
	        	addSensorBtn.setLayoutY(228);
	        	addSensorBtn.setPrefSize(300,60);   
	        	removeAreaBtn.setLayoutX(300);
	        	removeAreaBtn.setLayoutY(324);
	        	removeAreaBtn.setPrefSize(300,60);   
	        	removeSensorBtn.setLayoutX(300);
	        	removeSensorBtn.setLayoutY(420);
	        	removeSensorBtn.setPrefSize(300,60);	   
	        	areaConfigBtn.setLayoutX(300);
	        	areaConfigBtn.setLayoutY(516);
	        	areaConfigBtn.setPrefSize(300,60);        	   
	        	sesnsorConfigBtn.setLayoutX(300);
	        	sesnsorConfigBtn.setLayoutY(612);
	        	sesnsorConfigBtn.setPrefSize(300,60);   
	        	areaStatusBtn.setLayoutX(300);
	        	areaStatusBtn.setLayoutY(708);
	        	areaStatusBtn.setPrefSize(300,60);   
	        	sensorStatusBtn.setLayoutX(300);
	        	sensorStatusBtn.setLayoutY(804);
	        	sensorStatusBtn.setPrefSize(300,60);
	        	          
	        	root2.getChildren().add(parkStructBtn);
	     	    root2.getChildren().add(addAreaBtn);
	     	    root2.getChildren().add(addSensorBtn);
	     	    root2.getChildren().add(removeAreaBtn);
	     	    root2.getChildren().add(removeSensorBtn);
	     	    root2.getChildren().add(areaConfigBtn);
	     	    root2.getChildren().add(sesnsorConfigBtn);
	     	    root2.getChildren().add(areaStatusBtn);
	     	    root2.getChildren().add(sensorStatusBtn);	     	    
	     	    root2.getChildren().add(setupBackBtn);
	     	    
	     	    setupStage.setScene(new Scene(root2, 900, 900)); 
	     	    setupStage.show();
	     	    primaryStage.close();
	     	       
	     	    setupBackBtn.setOnAction(new EventHandler<ActionEvent>() {
	     	    	 
	   	            @Override
	   	            public void handle(ActionEvent event) {
	   	            	setupStage.close();
	   	            	primaryStage.show();
	   	            }
	   	      	});
	        }
	    });
	   
	    currentStatusBtn.setOnAction(new EventHandler<ActionEvent>() {
	    	
	    	@Override
	        public void handle(ActionEvent event) {
	    		
	    		Stage statusStage = new Stage();
	     	    statusStage.setTitle("Status");
	    		Pane root3 = new Pane();
	        	
	     	    Button freeSpotsBtn = new Button("Free Spots");
	        	Button TakenSpotBtn = new Button("Taken Spots");
	        	Button sensorDataBtn = new Button("Sesnsor's Data");
	        	Button signStatusBtn = new Button("Sign's Status");
	        	Button currentStatusBackBtn = new Button("Back");
	        	 
	        	freeSpotsBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	TakenSpotBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	sensorDataBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	signStatusBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	currentStatusBackBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	 
	        	freeSpotsBtn.setLayoutX(300);
	        	freeSpotsBtn.setLayoutY(132);
	        	freeSpotsBtn.setPrefSize(300,60);   
	        	TakenSpotBtn.setLayoutX(300);
	        	TakenSpotBtn.setLayoutY(324);
	        	TakenSpotBtn.setPrefSize(300,60);   
	        	sensorDataBtn.setLayoutX(300);
	        	sensorDataBtn.setLayoutY(516);
	        	sensorDataBtn.setPrefSize(300,60);   
	        	signStatusBtn.setLayoutX(300);
	        	signStatusBtn.setLayoutY(708);
	        	signStatusBtn.setPrefSize(300,60);
	   
	        	root3.getChildren().add(freeSpotsBtn);
	     	    root3.getChildren().add(TakenSpotBtn);
	     	    root3.getChildren().add(sensorDataBtn);
	     	    root3.getChildren().add(signStatusBtn);
	     	    root3.getChildren().add(currentStatusBackBtn);
	     	 
	     	    statusStage.setScene(new Scene(root3, 900, 900)); 
	     	    statusStage.show();
	     	    primaryStage.close();
	     	       
	     	    currentStatusBackBtn.setOnAction(new EventHandler<ActionEvent>() {
	     	    	
	     	    	@Override
		   	        public void handle(ActionEvent event) {	
		   	        	statusStage.close();
			     	    primaryStage.show();
		   	        }
		   	    });
	     	    
	     	   sensorDataBtn.setOnAction(new EventHandler<ActionEvent>() {
	     	    	
	     	    	@Override
		   	        public void handle(ActionEvent event) {	
		   	        	
	     	    		Stage sensorDataStage = new Stage();
	    	     	    statusStage.setTitle("Sensor's Data");
	    	    		Pane root5 = new Pane();
	    	        	
	    	     	    Button sensorStatusBtn = new Button("Sensor's Status");
	    	        	Button sensorConfigBtn = new Button("Sensor's Configuration");
	    	        	Button sensorTimestampBtn = new Button("Sensor's Timestamp");
	    	        	Button sensorsDataBackBtn = new Button("Back");
	    	        	
	    	        	sensorStatusBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	    	        	sensorConfigBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	    	        	sensorTimestampBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	    	        	sensorsDataBackBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	    	        	
	    	        	sensorStatusBtn.setLayoutX(300);
	    	        	sensorStatusBtn.setLayoutY(180);
	    	        	sensorStatusBtn.setPrefSize(300,60);   
	    	        	sensorConfigBtn.setLayoutX(300);
	    	        	sensorConfigBtn.setLayoutY(420);
	    	        	sensorConfigBtn.setPrefSize(300,60);   
	    	        	sensorTimestampBtn.setLayoutX(300);
	    	        	sensorTimestampBtn.setLayoutY(660);
	    	        	sensorTimestampBtn.setPrefSize(300,60);  
	    	        	
	    	        	root5.getChildren().add(sensorStatusBtn);
	    	        	root5.getChildren().add(sensorConfigBtn);
	    	        	root5.getChildren().add(sensorTimestampBtn);
	    	        	root5.getChildren().add(sensorsDataBackBtn);
	     	    		
	    	        	sensorDataStage.setScene(new Scene(root5, 900, 900));
	     	    		statusStage.close();
	     	    		sensorDataStage.show();
	     	    		
	     	    		sensorsDataBackBtn.setOnAction(new EventHandler<ActionEvent>() {
	    	     	    	
	    	     	    	@Override
	    		   	        public void handle(ActionEvent event) {	
	    	     	    		sensorDataStage.close();
	    		   	        	statusStage.show();
	    		   	        }
	    		   	    });
		   	        }
		   	    });
	     	    
	     	  signStatusBtn.setOnAction(new EventHandler<ActionEvent>() {
	     	    	
	     	    	@Override
		   	        public void handle(ActionEvent event) {	
		   	        	
	     	    		Stage signStatusBtnStage = new Stage();
	     	    		signStatusBtnStage.setTitle("Sign's Data");
	    	    		Pane root6 = new Pane();
	    	        	
	    	     	    Button signStatusBtn = new Button("Sign's Status");
	    	        	Button signConfigBtn = new Button("Sign's Configuration");
	    	        	Button signTimestampBtn = new Button("Sign's Timestamp");
	    	        	Button signDataBackBtn = new Button("Back");
	    	        	
	    	        	signStatusBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	    	        	signConfigBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	    	        	signTimestampBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	    	        	signDataBackBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	    	        	
	    	        	signStatusBtn.setLayoutX(300);
	    	        	signStatusBtn.setLayoutY(180);
	    	        	signStatusBtn.setPrefSize(300,60);   
	    	        	signConfigBtn.setLayoutX(300);
	    	        	signConfigBtn.setLayoutY(420);
	    	        	signConfigBtn.setPrefSize(300,60);   
	    	        	signTimestampBtn.setLayoutX(300);
	    	        	signTimestampBtn.setLayoutY(660);
	    	        	signTimestampBtn.setPrefSize(300,60);  
	    	        	
	    	        	root6.getChildren().add(signStatusBtn);
	    	        	root6.getChildren().add(signConfigBtn);
	    	        	root6.getChildren().add(signTimestampBtn);
	    	        	root6.getChildren().add(signDataBackBtn);
	     	    		
	    	        	signStatusBtnStage.setScene(new Scene(root6, 900, 900));
	     	    		statusStage.close();
	     	    		signStatusBtnStage.show();
	     	    		
	     	    		signDataBackBtn.setOnAction(new EventHandler<ActionEvent>() {
	    	     	    	
	    	     	    	@Override
	    		   	        public void handle(ActionEvent event) {	
	    	     	    		signStatusBtnStage.close();
	    		   	        	statusStage.show();
	    		   	        }
	    		   	    });
		   	        }
		   	    });
	     	    
	        }
	    });
	    
	    logsBtn.setOnAction(new EventHandler<ActionEvent>() {
	    	
	    	@Override
	        public void handle(ActionEvent event) {
	    		
		    	Stage logsStage = new Stage();
	    		logsStage.setTitle("Logs");
	    		Pane root4 = new Pane();
	        	
	        	Button logsBackBtn = new Button("Back");
	        	 
	        	logsBackBtn.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
	        	 
	        	root4.getChildren().add(logsBackBtn);
	     	    
	        	logsStage.setScene(new Scene(root4, 900, 900)); 
	        	logsStage.show();
	     	    primaryStage.close();
	     	       
	     	    logsBackBtn.setOnAction(new EventHandler<ActionEvent>() {
	     	  
	     	    	@Override
		   	        public void handle(ActionEvent event) {	
	     	    		logsStage.close();
			     	    primaryStage.show();
		   	        }
		   	    });
	        }
	    });
	        
	      
	}
	
}