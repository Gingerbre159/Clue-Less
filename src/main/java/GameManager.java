import java.util.ArrayList;
import java.util.Arrays;

public class GameManager {
	
	/***FireStore Service Account***/
<<<<<<< HEAD
	public String fs_service_account = "Your service account file goes here";
=======
	public String fs_service_account = "/Users/amberhamelin/Documents/clue-less-fa80b-firebase-adminsdk-x4xvz-5a7a55e5c1.json";
>>>>>>> 139b2f698d5506ddd3fb541c4370b1173033cd27

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
	
//	void printWeaponsUnknown(ArrayList<String> knownWeapons) {
//		ArrayList<String> output = new ArrayList<>();
//		
//		for(int i = 0; i < )
//		
//		System.out.println();
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