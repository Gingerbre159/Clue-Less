import java.util.ArrayList;
import java.util.Arrays;

public class GameManager {

	/***Variables***/
	private String[] weaponsArray = {
		"Revolver", 
		"Dagger", 
		"Lead Pipe", 
		"Rope", 
		"Candlestick", 
		"Wrench"
	};
	private String[] charactersArray = {
		"Professor Plum", 
		"Colonol Mustard", 
		"Miss Scarlet", 
		"Mrs. White", 
		"Mrs. Peacock", 
		"Mr. Green"
	};
	private String[] roomsArray = {
		"Study", 
		"Hall", 
		"Lounge", 
		"Library", 
		"Billiard Room", 
		"Dining Room",
		"Conservatory", 
		"Ballroom", 
		"Kitchen"
	};
	private String[] roomsAndHallwaysArray = {
		"Study", 
		"Study/Hall", 
		"Hall", 
		"Hall/Lounge", 
		"Lounge", 
		"Study/Library", 
		"Hall/Billiard Room", 
		"Lounge/Dining Room", 
		"Library", 
		"Library/Billiard Room", 
		"Billiard Room", 
		"Billiard Room/Dining Room", 
		"Dining Room",
		"Library/Conservatory", 
		"Billiard Room/Ballroom", 
		"Dining Room/Kitchen", 
		"Conservatory", 
		"Conservatory/Ballroom", 
		"Ballroom", 
		"Ballroom/Kitchen", 
		"Kitchen"
	};
	
	private ArrayList<String> weapons = new ArrayList<>(Arrays.asList(weaponsArray));
	private ArrayList<String> characters = new ArrayList<>(Arrays.asList(charactersArray));
	private ArrayList<String> rooms = new ArrayList<>(Arrays.asList(roomsArray));
	private ArrayList<String> roomsAndHallways = new ArrayList<>(Arrays.asList(roomsAndHallwaysArray));
	
	private static String correctWeapon;
	private static String correctCharacter;
	private static String correctRoom;
	
	/***Methods***/
	GameManager() {}
	
	Boolean accusation(String weapon, String character, String room) {
		if(weapon.equals(correctWeapon) && character.equals(correctCharacter) && room.equals(correctRoom)) {
			return true;
		}
		return false;
	}
	
	Boolean weaponsListContains(String s) {
		return weapons.contains(s);
	}
	
	Boolean charactersListContains(String s) {
		return characters.contains(s);
	}
	
	Boolean roomsListContains(String s) {
		return rooms.contains(s);
	}
	
	Boolean roomsAndHallwaysListContains(String s) {
		return roomsAndHallways.contains(s);
	}
	
	// Getters
	String getWeaponAt(int weaponNum) {
		return weapons.get(weaponNum);
	}
	
	String getCharacterAt(int characterNum) {
		return characters.get(characterNum);
	}
	
	String getRoomAt(int roomNum) {
		return rooms.get(roomNum);
	}
	
	String getRoomOrHallwayAt(int roomNum) {
		return roomsAndHallways.get(roomNum);
	}
	
	int getNumWeapons() {
		return weapons.size();
	}
	
	int getNumCharacters() {
		return characters.size();
	}
	
	int getNumRooms() {
		return rooms.size();
	}
	
	int getNumRoomsAndHallways() {
		return roomsAndHallways.size();
	}
	
	// Setters
	void setCorrectWeapon(String weapon) {
		correctWeapon = weapon;
	}
	
	void setCorrectCharacter(String character) {
		correctCharacter = character;
	}
	
	void setCorrectRoom(String room) {
		correctRoom = room;
	}

}
