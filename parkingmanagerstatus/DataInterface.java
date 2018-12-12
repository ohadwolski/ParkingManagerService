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

public class DataInterface implements Serializable  {
    /*private Node<ParkingElement> root;
    private boolean auto_init = true;
    private int working_mode = 0; // assume 0: manual by server, 1: on event, 2: every T seconds
    private int update_interval = 0; // in seconds
    private String esp_ip_address = "";
    private int Port = 0;*/
    private ParkingManagerData data=null;
	
    public DataInterface() {
      /* if (isIt) {
    	AreaId id = new AreaId(0);
   		ParkingElement newPark = new ParkingArea(id, StatusElement.OK, ConfigurationElement.REGULAR);
   		data = new ParkingManagerData();
       	data.root = new Node<ParkingElement>(newPark);*/
       
       
    	
    }

    public DataInterface(Node<ParkingElement> r) {
        data.root = r;
    }
     
    public Node<ParkingElement> getRoot() {
    	
    	return data.root;
    }

    public ParkingElement getParkingElement(IdElement id) {
        return FindParkingElement(id, data.root);
    }

    public Node<ParkingElement> getParkingElementNode(IdElement id) {
        return FindParkingElementNode(id, data.root);
    }

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

    public List<ParkingElement> getSignsForParkingElement(ParkingElement s) {
        if (s != null)
            return getSignsForParkingElement(s.getId());
        return null;
    }

    public List<ParkingElement> getSignsForParkingElement(IdElement id) {
        Node<ParkingElement> element = getParkingElementNode(id);
        List<ParkingElement> ListOfSigns = new ArrayList<ParkingElement>();
        if (element == null) return null;
        FindSignsForParkingElement(ListOfSigns, element.getParent());
        return ListOfSigns;
    }

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
    
    public void setAutoInit(boolean mode) {
    	data.auto_init = mode;
    }
    
    public void setwWrkingMode(int mode) {
    	data.working_mode = mode;
    }
    
    public void setUpdateInterval(int interval) {
    	data.update_interval = interval;
    }
    
    public void setEspIpAddress(String address) {
    	data.esp_ip_address = address;
    }
    
    public void setPort(int port) {
    	data.esp_port_number = port;
    }
    
    public boolean getAutoInit() {
    	return data.auto_init;
    }
    
    public int getwWrkingMode() {
    	return data.working_mode;
    }
    
    public int getUpdateInterval() {
    	return data.update_interval;
    }
    
    public String getEspIpAddress() {
    	return data.esp_ip_address;
    }
    
    public int getPort() {
    	return data.esp_port_number;
    }
    
    public List<ParkingElement> getSpotsForParkingElement(ParkingElement s) {
        if (s != null)
            return getSpotsForParkingElement(s.getId());
        return null;
    }

    public List<ParkingElement> getSpotsForParkingElement(IdElement id) {
        Node<ParkingElement> element = getParkingElementNode(id);
        List<ParkingElement> ListOfSpots = new ArrayList<ParkingElement>();
        if (element == null) return null;
        FindSpotsForParkingElement(ListOfSpots, element.getParent());
        return ListOfSpots;
    }

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
  
    public ParkingManagerData getData() {
    	return data;
    }
    
    public void setData(ParkingManagerData d) {
    	
    	
    	data = d;
    }
    
    public void saveDataInterface() {
		
		   try {
		        FileOutputStream fileOut = new FileOutputStream("./ParkingManagerData.ser");
		        ObjectOutputStream out = new ObjectOutputStream(fileOut);
		        //out.writeObject(parkData);
		        out.writeObject(data);
		        out.close();
		        fileOut.close();
		        System.out.printf("Serialized data is saved in ./ParkingManagerData.ser");
		   } 
		   catch (IOException i) {
			   	i.printStackTrace();
		   }
	}
	
	public void loadDataInterface() {
		
			try {
				FileInputStream fileIn = new FileInputStream("./ParkingManagerData.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				//parkData = (DataInterface) in.readObject();
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
  
    



