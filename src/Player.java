import java.util.ArrayList;

public class Player {
	
	/***Variables***/
	private int playerNum;
	private String character;
	private Room currRoom = null;
	private ArrayList<String> knownWeapons = new ArrayList<>();
	private ArrayList<String> knownCharacters = new ArrayList<>();
	private ArrayList<String> knownRooms = new ArrayList<>();
	
	/***Methods***/
	Player(int playerNum, String character){
		this.playerNum = playerNum;
		this.character = character;
	}
	
	void addKnownWeapon(String weapon) {
		this.knownWeapons.add(weapon);
	}
	
	void addKnownCharacter(String character) {
		this.knownCharacters.add(character);
	}
	
	void addKnownRoom(String room) {
		this.knownRooms.add(room);
	}
	
	void printData() {
		System.out.println("Player Number: " + this.playerNum);
		System.out.println("Character: " + this.character);
		System.out.println("Current Room: " + this.currRoom.getName());
		System.out.println("Known Weapons: " + this.knownWeapons);
		System.out.println("Known Characters: " + this.knownCharacters);
		System.out.println("Known Rooms: " + this.knownRooms);
	}
	
	// Getters
	int getPlayerNum() {
		return this.playerNum;
	}
	
	String getCharacter() {
		return this.character;
	}
	
	Room getCurrRoom() {
		return this.currRoom;
	}
	
	ArrayList<String> getKnownWeapons() {
		return this.knownWeapons;
	}
	
	ArrayList<String> getKnownCharacters() {
		return this.knownCharacters;
	}
	
	ArrayList<String> getKnownRooms() {
		return this.knownRooms;
	}
	
	// Setters
	void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}
	
	void setCharacter(String character) {
		this.character = character;
	}
	
	void setCurrRoom(Room newRoom) {
		this.currRoom = newRoom;
	}

}
