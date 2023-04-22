import java.util.ArrayList;
import java.util.Arrays;

public class GameManager {
	
	/***FireStore Service Account***/
	public String fs_service_account = "C:\\Users\\riley\\OneDrive\\Documents\\ClueLessStuff\\clue-less-fa80b-firebase-adminsdk-x4xvz-a05c14518c.json";

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
	private ArrayList<String> roomNames = new ArrayList<>(Arrays.asList(roomNamesArray));
	
	private static String correctWeapon;
	private static String correctCharacter;
	private static String correctRoom;
	
	public Boolean winCondition = false;
	
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
		
		
		/*Attach adjacent rooms*/
		
		// Attach to rooms
		for(int i = 0; i < this.rooms.size(); i++) {
			Room room = this.rooms.get(i);
			String roomName = room.getName();
			
			// Attach hallways to rooms
			for(int j = 0; j < this.hallways.size(); j++) {
				
				Room hallway = this.hallways.get(j);
				String hallwayName = hallway.getName();
				
				if(hallwayName.contains(roomName)) {
					
					room.appendAdjRoom(hallway);
					
				}
				
			}
			
			// Attach secret passage rooms to rooms
			if(room.hasSecretPassage()) {
				if(i == 0) {
					room.appendAdjRoom(rooms.get(8));
				}
				else if(i == 2) {
					room.appendAdjRoom(rooms.get(6));
				}
				else if(i == 6) {
					room.appendAdjRoom(rooms.get(2));
				}
				else if(i == 8) {
					room.appendAdjRoom(rooms.get(0));
				}
			}
			
			this.rooms.set(i, room);
		}
		
		// Attach to hallways
		for(int i = 0; i < this.hallways.size(); i++) {
			Room hallway = this.hallways.get(i);
			
			String[] tempRooms = hallway.getName().split("/");
			Room temp = new Room(tempRooms[0]);
			hallway.appendAdjRoom(temp);
			temp = new Room(tempRooms[1]);
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
	
	void endScreen(int turnNum, int numPlayers, Player winner, int playerNum) {
		// Display win screen for winner
        if(turnNum % numPlayers == playerNum) {
        	System.out.println("Congrats, you win!");
        }
        
        // Show who won and the win condition for losers
        else {
        	System.out.println("Sorry, better luck next time");
        	System.out.println("Winner: " + winner.character);
        	System.out.println("Correct Weapon: " + correctWeapon);
        	System.out.println("Correct Character: " + correctCharacter);
        	System.out.println("Correct Room: " + correctRoom);
        }
	}
	
	// Output block of text for player knowns
	void printKnowns(Player player) {
		System.out.println("You know the following cannot be guilty:");
		System.out.println("	Weapons:" + player.knownWeapons);
		System.out.println("	Characters:" + player.knownCharacters);
		System.out.println("	Rooms:" + player.knownRooms);
	}
	
	// Output block of text for player unknowns
	void printUnknowns(Player player) {
		System.out.println("You do not know if the following are guilty:");
		printWeaponsUnknown(player.knownWeapons);
		printCharactersUnknown(player.knownCharacters);
		printRoomsUnknown(player.knownRooms);
	}
	
	// Output unknown weapons to the console
	private void printWeaponsUnknown(ArrayList<String> knownWeapons) {
		ArrayList<String> output = new ArrayList<>();
		
		for(int i = 0; i < weapons.size(); i++) {
			if(!knownWeapons.contains(weapons.get(i))) {
				output.add(weapons.get(i));
			}
		}
		
		System.out.println("	Weapons:" + output);
	}
	
	// Output unknown characters to the console
	private void printCharactersUnknown(ArrayList<String> knownCharacters) {
		ArrayList<String> output = new ArrayList<>();
		
		for(int i = 0; i < characters.size(); i++) {
			if(!knownCharacters.contains(characters.get(i))) {
				output.add(characters.get(i));
			}
		}
		
		System.out.println("	Characters:" + output);
	}
	
	// Output unknown rooms to the console
	private void printRoomsUnknown(ArrayList<String> knownRooms) {
		ArrayList<String> output = new ArrayList<>();
		
		for(int i = 0; i < rooms.size(); i++) {
			if(!knownRooms.contains(rooms.get(i).getName())) {
				output.add(rooms.get(i).getName());
			}
		}
		
		System.out.println("	Rooms:" + output);
	}
	
	
	
	/**Getters**/
	ArrayList<String> getWeapons() {
		return weapons;
	}
	
	ArrayList<String> getCharacters() {
		return characters;
	}
	
	ArrayList<Room> getRooms() {
		return rooms;
	}
	
	ArrayList<String> getRoomNames(){
		return roomNames;
	}
	
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
	
	
	
	/**Setters**/
	static void setCorrectWeapon(String weapon) {
		correctWeapon = weapon;
	}
	
	static void setCorrectCharacter(String character) {
		correctCharacter = character;
	}
	
	static void setCorrectRoom(String room) {
		correctRoom = room;
	}

}