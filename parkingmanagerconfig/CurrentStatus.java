package parkingmanagerconfig;

import java.util.List;
import parkingmanagerdata.*;

import parkingmanagerservice.*;



public class CurrentStatus {

	private DataInterface parkData;
	
	public CurrentStatus() {
		
	}
	
	public void showDataID(IdElement Id) {
		
		parkData.getParkingElement(Id).getId().print();
	}
	
	public void showDataStatusElement(IdElement Id) {
			
		parkData.getParkingElement(Id);
	}
	
	public void showDataConfiurationElement(IdElement Id) {
		
		parkData.getParkingElement(Id);
	}
	
	public void showDataTimeStamp(IdElement Id) {
		
		parkData.getParkingElement(Id);
	}
	
	public int getNumOfFreeSpots() {
 
		int numOfFreeSpots = 0;
		List<ParkingElement> spots = parkData.getSpotsForParkingElement(parkData.getRoot().getData());
		for(ParkingElement item: spots) {
			
			if (item.getStatus() == StatusElement.FREE) {
				numOfFreeSpots++;
			}
		}		
		return numOfFreeSpots;
	}
}