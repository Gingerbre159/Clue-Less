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
	
	private ArrayList<String> getAdjacentHallways(String room, GameManager gm) {
		
		ArrayList<String> hallways = new ArrayList<>();
		for(int i = 0; i < gm.getNumRoomsAndHallways(); i++) {
			if(gm.getRoomOrHallwayAt(i).contains(room) && !room.equals(gm.getRoomOrHallwayAt(i))) {
				hallways.add(gm.getRoomOrHallwayAt(i));
			}
		}
		
		return hallways;
	}
	
	String getStartHallway(String room, GameManager gm) {
		if(room.contains("Plum")) {
			return gm.getRoomOrHallwayAt(5);
		}
		
		if(room.contains("Mustard")) {
			return gm.getRoomOrHallwayAt(7);
		}
		
		if(room.contains("Scarlet")) {
			return gm.getRoomOrHallwayAt(3);
		}
		
		if(room.contains("White")) {
			return gm.getRoomOrHallwayAt(19);
		}
		
		if(room.contains("Peacock")) {
			return gm.getRoomOrHallwayAt(13);
		}
		
		if(room.contains("Green")) {
			return gm.getRoomOrHallwayAt(17);
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
	
	ArrayList<String> getMoveOptions(String currRoom, GameManager gm) {
		ArrayList<String> options = new ArrayList<>();
		
		if(!gm.roomsAndHallwaysListContains(currRoom)) {
			options.add(getStartHallway(currRoom, gm));
		}
		
		if(gm.roomsListContains(currRoom)) {
			options.addAll(getAdjacentHallways(currRoom, gm));
		}
		
		if(gm.roomsAndHallwaysListContains(currRoom) && !gm.roomsListContains(currRoom)) {
			options.addAll(getAdjacentRooms(currRoom, gm));
		}
		
		return options;
	}
	
	ArrayList<String> getTurnOptions(String currRoom, GameManager gm) {
		ArrayList<String> options = new ArrayList<>();
		
		if(currentTurn/numPlayers == 0) {
			options.add("Move");
			options.add("Accusation");
			return options;
		}
		
//		if(gm.roomsListContains(currRoom)) {
//			options.add("Move to " + getAdjacentHallways(currRoom, gm) + ". Stay");
//		}
		
		if(gm.roomsAndHallwaysListContains(currRoom)) {
			options.add("Move");
		}
		
		if(gm.roomsListContains(currRoom)) {
			options.add("Stay");
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
