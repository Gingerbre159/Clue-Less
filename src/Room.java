import java.util.ArrayList;

public class Room {
	
	/***Variables***/
	private String name;
	private Boolean isOccupied = false;
	private Boolean hasSecretPassage = false;
	private Boolean isHallway = false;
	private ArrayList<Room> adjRooms = new ArrayList<>();
	private ArrayList<String> adjRoomNames = new ArrayList<>();
	
	/***Methods***/
	Room(String name) {
		this.name = name;
	}
	
	void appendAdjRoom(Room adjRoom) {
		this.adjRooms.add(adjRoom);
		this.adjRoomNames.add(adjRoom.getName());
	}
	
	// Getters
	String getName() {
		return this.name;
	}
	
	Boolean isOccupied() {
		return this.isOccupied;
	}
	
	Boolean hasSecretPassage() {
		return this.hasSecretPassage;
	}
	
	Boolean isHallway() {
		return this.isHallway;
	}
	
	ArrayList<Room> getAdjRooms() {
		return this.adjRooms;
	}
	
	ArrayList<String> getAdjRoomNames() {
		return this.adjRoomNames;
	}
	
	// Setters
	void setName(String name) {
		this.name = name;
	}
	
	void setIsOccupied(Boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	void setHasSecretPassage(Boolean hasSecretPassage) {
		this.hasSecretPassage = hasSecretPassage;
	}
	
	void setIsHallway(Boolean isHallway) {
		this.isHallway = isHallway;
	}

}
