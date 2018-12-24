package parkingmanagerconfig;


import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.io.FileInputStream;
import parkingmanagerdata.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//This class is the interface between The Setup Application and the data structure of the parking lot
public class DataInterface implements Serializable  {
    
    private ParkingManagerData data=null; //Parking Lot Data structure
	
    //constructor
    public DataInterface(boolean isIt) {
      
    	if (isIt) {
    		AreaId id = new AreaId(0);
    		ParkingElement newPark = new ParkingArea(id, StatusElement.OK, ConfigurationElement.REGULAR);
    		data = new ParkingManagerData();
    		data.root = new Node<ParkingElement>(newPark);
    	}  	
    }

    //constructor
    public DataInterface(Node<ParkingElement> r) {
        
    	data.root = r;
    }
  
    //return the root of the data structure 
    public Node<ParkingElement> getRoot() {
    	
    	return data.root;
    }
 
    //return Parking Element according to Id
    public ParkingElement getParkingElement(IdElement id) {
       
    	return FindParkingElement(id, data.root);
    }
  
    //return Parking Element Node according to Id
    public Node<ParkingElement> getParkingElementNode(IdElement id) {
        
    	return FindParkingElementNode(id, data.root);
    }
    
    //helper method to find parking element
    private ParkingElement FindParkingElement(IdElement id, Node<ParkingElement> node) {
       
    	if (node == null || id == null)
            return null;
        if (node.getData().getId().compare(id))
            return node.getData();
        List<Node<ParkingElement>> childrenNodes = node.getChildren();
        for (Node<ParkingElement> child : childrenNodes) {
            ParkingElement result = FindParkingElement(id, child);
            if (result != null) return result;
        }
        return null;
    }

    //helper method to find parking element node
    private Node<ParkingElement> FindParkingElementNode(IdElement id, Node<ParkingElement> node) {
       
    	if (node == null || id == null)
            return null;
        if (node.getData().getId().compare(id))
            return node;
        List<Node<ParkingElement>> childrenNodes = node.getChildren();
        for (Node<ParkingElement> child : childrenNodes) {
            Node<ParkingElement> result = FindParkingElementNode(id, child);
            if (result != null) return result;
        }
        return null;
    }
    
    //returns list of all the Id of areas in the parking lot
    public ArrayList<AreaId> getAreasOfParkingLot(ArrayList<AreaId> areasList, Node<ParkingElement> parkingLot) {
    	
    	if (parkingLot == null) {
    		return null;
    	}
    	if (parkingLot.getData() instanceof ParkingArea) {
    		areasList.add((AreaId)parkingLot.getData().getId());
    	}
    	if(parkingLot.getChildren()!=null) {
    		for (Node<ParkingElement> element: parkingLot.getChildren()) {
    			ArrayList<AreaId> result = getAreasOfParkingLot(areasList, element);
    			if (result!=null) {
    				return result;
    			}
    		}   		
    	}
    	return null;    	
    }
 
    //returns list of all the Id of signs in the parking lot
    public ArrayList<SignId> getSignsOfParkingLot(ArrayList<SignId> signsList, Node<ParkingElement> parkingLot) {
    	
    	if (parkingLot == null) {
    		return null;
    	}
    	if (parkingLot.getData() instanceof ParkingSign) {
    		signsList.add((SignId)parkingLot.getData().getId());
    	}
    	if(parkingLot.getChildren()!=null) {
    		for (Node<ParkingElement> element: parkingLot.getChildren()) {
    			ArrayList<SignId> result = getSignsOfParkingLot(signsList, element);
    			if (result!=null) {
    				return result;
    			}
    		}   		
    	}
    	return null;    	
    }
    
    //returns list of all the Id of sensors in the parking lot
    public ArrayList<SensorId> getSensorsOfParkingLot(ArrayList<SensorId> sensorsList, Node<ParkingElement> parkingLot) {
    	
    	if (parkingLot == null) {
    		return null;
    	}
    	if (parkingLot.getData() instanceof ParkingSensor) {
    		sensorsList.add((SensorId)parkingLot.getData().getId());
    	}
    	if(parkingLot.getChildren()!=null) {
    		for (Node<ParkingElement> element: parkingLot.getChildren()) {
    			ArrayList<SensorId> result = getSensorsOfParkingLot(sensorsList, element);
    			if (result!=null) {
    				return result;
    			}
    		}   		
    	}
    	return null;    	
    }

    //return list of all the signs in the parking lot
    public List<ParkingElement> getSignsForParkingElement(ParkingElement s) {
       
    	if (s != null)
            return getSignsForParkingElement(s.getId());
        return null;
    }

    //helper method to find all the signs in the parking lot
    public List<ParkingElement> getSignsForParkingElement(IdElement id) {
        
    	Node<ParkingElement> element = getParkingElementNode(id);
        List<ParkingElement> ListOfSigns = new ArrayList<ParkingElement>();
        if (element == null) return null;
        FindSignsForParkingElement(ListOfSigns, element.getParent());
        return ListOfSigns;
    }

    //helper method to find all the signs in the parking lot
    private void FindSignsForParkingElement(List<ParkingElement> ListOfSigns, Node<ParkingElement> node) {
       
    	if (node == null) return;
        List<Node<ParkingElement>> childrenNodes = node.getChildren();
        for (Node<ParkingElement> child : childrenNodes) {
            if (child.getData() instanceof ParkingSign) {
                ListOfSigns.add(child.getData());
            }
        }
        if (node.getParent() == null) return;
        FindSignsForParkingElement(ListOfSigns, node.getParent());
    }
    
    //set server setup initial mode
    public void setAutoInit(boolean mode) {
    	
    	data.auto_init = mode;
    }
    
    //set server working mode
    public void setwWrkingMode(int mode) {
    	
    	data.working_mode = mode;
    }
    
    //set server update interval
    public void setUpdateInterval(int interval) {
    	
    	data.update_interval = interval;
    }
    
    //set server esp ip address
    public void setEspIpAddress(String address) {
    	
    	data.esp_ip_address = address;
    }
    
    //set server port
    public void setPort(int port) {
    	
    	data.esp_port_number = port;
    }
    
    //get server setup initial mode
    public boolean getAutoInit() {
    	
    	return data.auto_init;
    }
    
    //get server working mode
    public int getwWrkingMode() {
    	
    	return data.working_mode;
    }
    
    //get server update interval
    public int getUpdateInterval() {
    	
    	return data.update_interval;
    }
    
    //get server esp ip address
    public String getEspIpAddress() {
    	
    	return data.esp_ip_address;
    }
    
    //get server port
    public int getPort() {
    	
    	return data.esp_port_number;
    }
    
    //return list of all the spots in the parking lot
    public List<ParkingElement> getSpotsForParkingElement(ParkingElement s) {
        
    	if (s != null)
            return getSpotsForParkingElement(s.getId());
        return null;
    }
  
    //helper method to find all the sensors in the parking lot
    public List<ParkingElement> getSpotsForParkingElement(IdElement id) {
        
    	Node<ParkingElement> element = getParkingElementNode(id);
        List<ParkingElement> ListOfSpots = new ArrayList<ParkingElement>();
        if (element == null) return null;
        FindSpotsForParkingElement(ListOfSpots, element.getParent());
        return ListOfSpots;
    }

    //helper method to find all the sensors in the parking lot
    private void FindSpotsForParkingElement(List<ParkingElement> ListOfSpots, Node<ParkingElement> node) {
       
    	if (node == null) return;
        List<Node<ParkingElement>> childrenNodes = node.getChildren();
        for (Node<ParkingElement> child : childrenNodes) {
            if (child.getData() instanceof ParkingSensor) {
                ListOfSpots.add(child.getData());
            }
        }
        if (node.getParent() == null) return;
        FindSpotsForParkingElement(ListOfSpots, node.getParent());
    }
 
    //returns the data of the data structure
    public ParkingManagerData getData() {
    	
    	return data;
    }
    
    //sets the data of the data structure
    public void setData(ParkingManagerData d) {
    	
    	data = d;
    }
    
    //save as method
    public void saveDataInterface() throws IOException {
		
    	JFrame parentFrame = new JFrame();
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setDialogTitle("Specify a file to save");   
    	File fileToSave;
    	int userSelection = fileChooser.showSaveDialog(parentFrame);	 
    	if (userSelection == JFileChooser.APPROVE_OPTION) {
    		fileToSave = fileChooser.getSelectedFile();
    		FileOutputStream fw = new FileOutputStream (fileToSave);
        	ObjectOutputStream bw = new ObjectOutputStream(fw);
            bw.writeObject(data);
            bw.close();
    	    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
    	}  
	}
	
    //load as method
	public void loadDataInterface() throws IOException, ClassNotFoundException {
		
		JFrame parentFrame = new JFrame();
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setDialogTitle("Specify a file to load");   
    	File fileToSave;
    	int userSelection = fileChooser.showOpenDialog(parentFrame);	 
    	if (userSelection == JFileChooser.APPROVE_OPTION) {
    		fileToSave = fileChooser.getSelectedFile();
    		FileInputStream fw = new FileInputStream (fileToSave);
    		ObjectInputStream bw = new ObjectInputStream(fw);
            data = (ParkingManagerData)bw.readObject();
            bw.close();
    	    System.out.println("Load file: " + fileToSave.getAbsolutePath());
    	}  
	}
}   
  
    



