package parkingmanagerstatus;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import parkingmanagerservice.*;
import parkingmanagerdata.*;

//This class is the interface between The Status Application and the data structure of the parking lot
public class DataInterface implements Serializable  {
   
    private ParkingManagerData data=null;    //Parking Lot Data structure
	
    //Constructor
    public DataInterface() {
    	
    }

    //Constructor
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
    
    //save file method
    public void saveDataInterface() {
		
    	try {
    		FileOutputStream fileOut = new FileOutputStream("./ParkingManagerData.ser");
		    ObjectOutputStream out = new ObjectOutputStream(fileOut);
		    out.writeObject(data);
		    out.close();
		    fileOut.close();
		    System.out.printf("Serialized data is saved in ./ParkingManagerData.ser");
		} 
		catch (IOException i) {
			i.printStackTrace();
		}
	}
	
    //load file method
	public void loadDataInterface() {
		
		try {
			FileInputStream fileIn = new FileInputStream("./ParkingManagerData.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			data = (ParkingManagerData) in.readObject();
			in.close();
			fileIn.close();
		} 
		catch (IOException i) {
			i.printStackTrace();
			return;
		} 
		catch (ClassNotFoundException c) {
			System.out.println("Data class not found");
	        c.printStackTrace();
	        return;
		}
	}
     
}   
  
    



