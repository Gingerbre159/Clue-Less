import java.util.ArrayList;

public class TurnManager {
	
	/***Variables***/
	private int currentTurn = 0;
	private int numPlayers;
	
	/***Methods***/
	TurnManager() {}
	
	void nextTurn() {
		this.currentTurn++;
	}
	
	String getStartHallway(String room, GameManager gm) {
		if(room.contains("Plum")) {
			return gm.getHallwayAt(2).getName();
		}
		
		if(room.contains("Mustard")) {
			return gm.getHallwayAt(4).getName();
		}
		
		if(room.contains("Scarlet")) {
			return gm.getHallwayAt(1).getName();
		}
		
		if(room.contains("White")) {
			return gm.getHallwayAt(11).getName();
		}
		
		if(room.contains("Peacock")) {
			return gm.getHallwayAt(7).getName();
		}
		
		if(room.contains("Green")) {
			return gm.getHallwayAt(10).getName();
		}
		
		return "Could not find start hallway";
	}
	
	ArrayList<String> getAdjacentRooms(String currRoom, GameManager gm) {
		ArrayList<String> options = new ArrayList<>();
		String[] temp = currRoom.split("/");
		options.add(temp[0]);
		options.add(temp[1]);
		
		return options;
	}
	
	ArrayList<String> getMoveOptions(Room currRoom, GameManager gm) {
		ArrayList<String> options = new ArrayList<>();
		
		// if player is in start area, add appropriate hallway to move options
		if(!gm.roomsListContains(currRoom.getName()) && !gm.hallwaysListContains(currRoom.getName())) {
			options.add(getStartHallway(currRoom.getName(), gm));
		}
		
		// if player is in a room or hallway, add adjacent rooms/hallways to move options
		if(gm.roomsListContains(currRoom.getName()) || gm.hallwaysListContains(currRoom.getName())) {
			options.addAll(currRoom.getAdjRoomNames());
		}
		
		return options;
	}
	
	ArrayList<String> getTurnOptions(Room currRoom, GameManager gm) {
		ArrayList<String> options = new ArrayList<>();
		
		if(currentTurn/numPlayers == 0) {
			options.add("Move");
			options.add("Accusation");
			return options;
		}
		
		if(gm.roomsListContains(currRoom.getName()) || gm.hallwaysListContains(currRoom.getName())) {
			options.add("Move");
		}
		
		if(gm.roomsListContains(currRoom.getName())) {
			options.add("Suggestion");
		}
		
		options.add("Accusation");
		
		return options;
	}
	
	// Getters
	int getCurrentTurn() {
		return this.currentTurn;
	}
	
	// Setters
	void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}
	
	void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

}
