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
	private String[] roomNamesArray = {
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
//	private String[] roomAndHallwayNamesArray = {
//		"Study", 
//		"Study/Hall", 
//		"Hall", 
//		"Hall/Lounge", 
//		"Lounge", 
//		"Study/Library", 
//		"Hall/Billiard Room", 
//		"Lounge/Dining Room", 
//		"Library", 
//		"Library/Billiard Room", 
//		"Billiard Room", 
//		"Billiard Room/Dining Room", 
//		"Dining Room",
//		"Library/Conservatory", 
//		"Billiard Room/Ballroom", 
//		"Dining Room/Kitchen", 
//		"Conservatory", 
//		"Conservatory/Ballroom", 
//		"Ballroom", 
//		"Ballroom/Kitchen", 
//		"Kitchen"
//	};
	private String[] hallwayNamesArray = {
		"Study/Hall", 
		"Hall/Lounge", 
		"Study/Library", 
		"Hall/Billiard Room", 
		"Lounge/Dining Room", 
		"Library/Billiard Room", 
		"Billiard Room/Dining Room", 
		"Library/Conservatory", 
		"Billiard Room/Ballroom", 
		"Dining Room/Kitchen", 
		"Conservatory/Ballroom", 
		"Ballroom/Kitchen", 
	};
	private ArrayList<Room> rooms = new ArrayList<>();
	private ArrayList<Room> hallways = new ArrayList<>();
	
	private ArrayList<String> weapons = new ArrayList<>(Arrays.asList(weaponsArray));
	private ArrayList<String> characters = new ArrayList<>(Arrays.asList(charactersArray));
	
	private static String correctWeapon;
	private static String correctCharacter;
	private static String correctRoom;
	
	/***Methods***/
	GameManager() {
		
		
		// Create list of rooms and hallways
		for(int i = 0; i < this.roomNamesArray.length; i++) {
			Room temp = new Room(this.roomNamesArray[i]);
			
			if(i == 0 || i == 2 || i == 6 || i == 8) {
				temp.setHasSecretPassage(true);
			}
			
			this.rooms.add(temp);
		}
		
		for(int i = 0; i < this.hallwayNamesArray.length; i++) {
			Room temp = new Room(this.hallwayNamesArray[i]);
			
			temp.setIsHallway(true);
			
			this.hallways.add(temp);
		}
		
		
		// Attach adjacent rooms
		for(int i = 0; i < this.rooms.size(); i++) {
			Room room = this.rooms.get(i);
			String roomName = room.getName();
			
			for(int j = 0; j < this.hallways.size(); j++) {
				
				Room hallway = this.hallways.get(j);
				String hallwayName = hallway.getName();
				
				if(hallwayName.contains(roomName)) {
					
					room.appendAdjRoom(hallway);
					
				}
				
			}
			
			this.rooms.set(i, room);
		}
		
		for(int i = 0; i < this.hallways.size(); i++) {
			Room hallway = this.hallways.get(i);
			
			String[] rooms = hallway.getName().split("/");
			Room temp = new Room(rooms[0]);
			hallway.appendAdjRoom(temp);
			temp = new Room(rooms[1]);
			hallway.appendAdjRoom(temp);
			
			this.hallways.set(i, hallway);
		}
		
	}
	
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
		for(int i = 0; i < rooms.size(); i++) {
			if(rooms.get(i).getName().equals(s)) {
				return true;
			}
		}
		
		return false;
	}
	
	Boolean hallwaysListContains(String s) {
		for(int i = 0; i < hallways.size(); i++) {
			if(hallways.get(i).getName().equals(s)) {
				return true;
			}
		}
		
		return false;
	}
	
//	Boolean roomsAndHallwaysListContains(String s) {
//		return roomsAndHallways.contains(s);
//	}
	
	// Getters
	String getWeaponAt(int weaponNum) {
		return weapons.get(weaponNum);
	}
	
	String getCharacterAt(int characterNum) {
		return characters.get(characterNum);
	}
	
	Room getRoomAt(int roomNum) {
		return rooms.get(roomNum);
	}
	
	Room getRoom(String name) {
		for(int i = 0; i < rooms.size(); i++) {
			if(rooms.get(i).getName().equals(name)) {
				return rooms.get(i);
			}
		}
		for(int i = 0; i < hallways.size(); i++) {
			if(hallways.get(i).getName().equals(name)) {
				return hallways.get(i);
			}
		}
		return null;
	}
	
	Room getHallwayAt(int hallwayNum) {
		return hallways.get(hallwayNum);
	}
	
//	String getRoomNameAt(int roomNum) {
//		return rooms.get(roomNum).getName();
//	}
//	
//	String getHallwayNameAt(int hallwayNum) {
//		return hallways.get(hallwayNum).getName();
//	}
	
//	String getRoomOrHallwayAt(int roomNum) {
//		return roomsAndHallways.get(roomNum);
//	}
	
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
		return (rooms.size() + hallways.size());
	}
	
	String getCorrectWeapon() {
		return correctWeapon;
	}
	
	String getCorrectCharacter() {
		return correctCharacter;
	}
	
	String getCorrectRoom() {
		return correctRoom;
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
