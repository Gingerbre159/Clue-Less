import java.util.ArrayList;

public class Player {
	
	/***Variables***/
	public int playerNum;
	public String character;
	public Room currRoom = null;
	public Boolean isEliminated = false;
	public ArrayList<String> knownWeapons = new ArrayList<>();
	public ArrayList<String> knownCharacters = new ArrayList<>();
	public ArrayList<String> knownRooms = new ArrayList<>();
	
	/***Methods***/
	Player() {
		
	}
	
	Player(int playerNum, String character){
		this.playerNum = playerNum;
		this.character = character;
	}
	
//	void addKnownWeapon(String weapon) {
//		this.knownWeapons.add(weapon);
//	}
//	
//	void addKnownCharacter(String character) {
//		this.knownCharacters.add(character);
//	}
//	
//	void addKnownRoom(String room) {
//		this.knownRooms.add(room);
//	}
	
	// Getters
//	int getPlayerNum() {
//		return this.playerNum;
//	}
	
//	ArrayList<String> getKnownWeapons() {
//		return this.knownWeapons;
//	}
//	
//	ArrayList<String> getKnownCharacters() {
//		return this.knownCharacters;
//	}
//	
//	ArrayList<String> getKnownRooms() {
//		return this.knownRooms;
//	}
	
	// Setters
//	void setPlayerNum(int playerNum) {
//		this.playerNum = playerNum;
//	}

}
