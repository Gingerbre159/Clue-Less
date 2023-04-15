import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Server {
	
	
	
	
	/***Variables***/
	
	// Local variables
	private static int numPlayers = 3;
	private static Player player;
	private static ArrayList<String> chosenCharacters = new ArrayList<>();
	//private static ArrayList<Player> players = new ArrayList<>();
	//private static ArrayList<String> currPlayerLocations = new ArrayList<>();
	
	// Variables to hold other classes
	static GameManager gm = new GameManager();
	static GameBoard gb = new GameBoard();
	static TurnManager tm = new TurnManager();
	
	// Allow use of firebase functions
//	FirebaseFunctions firefunc = FirebaseFunctions.getInstance();
	
	
	
	
	/***Methods***/
	
	// Add a player to the game
	static void addPlayer(int playerNum) {
		player = new Player(playerNum, null);
//		players.add(newPlayer);
//		currPlayerLocations.add("Unknown");
		numPlayers++;
	}
	
	// Eliminate a player from the game
	static void eliminatePlayer() {
		player.isEliminated = true;
	}
	
	// Give out starting knowledge to all players
	static void assignStartKnowledge() {
		int counter = 0;
		
		// Weapons
		for(int i = 0; i < gm.getNumWeapons(); i++) {
			
			// If the weapon is not the correct weapon
			if(!gm.getCorrectWeapon().equals(gm.getWeaponAt(i))) {
				
				// If it is the player's turn to know something
				if(counter % numPlayers == player.playerNum) {
					player.addKnownWeapon(gm.getWeaponAt(i));
				}
				counter++;
			}
		}
		
		// Characters
		for(int i = 0; i < gm.getNumCharacters(); i++) {
			
			// If the character is not the correct character
			if(!gm.getCorrectCharacter().equals(gm.getCharacterAt(i))) {
				
				// If it is the player's turn to know something
				if(counter % numPlayers == player.playerNum) {
					player.addKnownCharacter(gm.getCharacterAt(i));
				}
				counter++;
			}
		}
		
		// Rooms
		for(int i = 0; i < gm.getNumRooms(); i++) {
			
			// If the room is not the correct room
			if(!gm.getCorrectRoom().equals(gm.getRoomAt(i).getName())) {
				
				// If it is the player's turn to know something
				if(counter % numPlayers == player.playerNum) {
					player.addKnownRoom(gm.getRoomAt(i).getName());
				}
				counter++;
			}
		}
	}
	
	// Return the starting area of the character provided
	static String getStartArea(String character) {
		if(gm.getCharacterAt(0).equals(character)) {
			return "Plum Start Area";
		}
		
		else if(gm.getCharacterAt(1).equals(character)) {
			return "Mustard Start Area";
		}
		
		else if(gm.getCharacterAt(2).equals(character)) {
			return "Scarlet Start Area";
		}
		
		else if(gm.getCharacterAt(3).equals(character)) {
			return "White Start Area";
		}
		
		else if(gm.getCharacterAt(4).equals(character)) {
			return "Peacock Start Area";
		}
		
		else if(gm.getCharacterAt(5).equals(character)) {
			return "Green Start Area";
		}
		
		return "No Available Start Area";
	}
	
	// Return the starting area of the provided Player
	static String getStartArea(Player player) {
		return getStartArea(player.character);
	}
	
	// Assign a character to a player
	static void chooseCharacter(String character) {
		player.character = character;
		player.currRoom = new Room(getStartArea(player.character));
		
//		player.currRoom = gm.getRoom(getStartArea(player.character));
	}
	
	// Output available characters for player to choose from
	static void printAvailableCharacters() {
		int numCharacters = gm.getNumCharacters();
		
		for(int i = 0; i < numCharacters; i++) {
			String currCharacter = gm.getCharacterAt(i);
			Boolean characterTaken = false;
			
			// Check if the character has already been chosen
			if(chosenCharacters.contains(currCharacter)) {
				characterTaken = true;
			}
			
			// If character has not already been chosen, output character
			if(!characterTaken) {
				System.out.println(" - " + currCharacter);
			}
		}
	}
	
	// Assign the correct answers that will end the game to the GameManager class
	static void chooseCorrectAnswers(int randWeaponNum, int randCharacterNum, int randRoomNum) {
		gm.setCorrectWeapon(gm.getWeaponAt(randWeaponNum));
		gm.setCorrectCharacter(gm.getCharacterAt(randCharacterNum));
		gm.setCorrectRoom(gm.getRoomAt(randRoomNum).getName());
	}
	
	// Output the current turn options
	static void printTurnOptions(ArrayList<String> options) {
		for(int i = 0; i < options.size(); i++) {
			System.out.println(" - " + options.get(i));
		}
	}
	
	// Output the current move options
	static void printMoveOptions(ArrayList<String> options) {
		for(int i = 0; i < options.size(); i++) {
			System.out.println(" - " + options.get(i));
		}
	}
	
	// Move the provided player to the provided location
	static void move(String location) {
		Room room = gm.getRoom(location);
		player.currRoom = room;
	}
	
	// Display current knowledge
	static void printKnowns() {
		System.out.println("You know the following cannot be guilty:");
		System.out.println("	Weapons:" + player.knownWeapons);
		System.out.println("	Characters:" + player.knownCharacters);
		System.out.println("	Rooms:" + player.knownRooms);
	}
	
	
	
	
	/***Main***/
	public static void main(String[] args) {
		
		
		
		/**Variables**/
		
		// Local variables to not be static
		int acceptableNumFailedReplies = 2;
		int failedReplyCounter = 0;
		String answer = "";
		String divider = "--------------------------------";
		
		// Variables to hold other classes to not be static
		Scanner sc = new Scanner(System.in);
		Random random = new Random();
		
		// Randoms to be rerolled every new game
        int randWeaponNum = random.nextInt(6);
        int randCharacterNum = random.nextInt(6);
        int randRoomNum = random.nextInt(9);
        
        // Data to be sent to firebase funtions
//        Map<String, Object> data = new HashMap<>();
        
        
        
        /**PreGame**/
        
        
        /*Ask if the user would like to play*/
        System.out.println("Would you like to play Clue-less? (Y/N)");
        answer = sc.nextLine();
        
        // Handle any answer that is not 'Y' or 'N'
        while(!answer.equals("N") && !answer.equals("Y") && failedReplyCounter < acceptableNumFailedReplies) {
        	System.out.println("Sorry, that was not one of the acceptable responses. Please only write 'Y' or 'N'");
        	failedReplyCounter++;
        	System.out.println("Would you like to play Clue-less? (Y/N)");
            answer = sc.nextLine();
        }
        
        // Handle 'N' and too many incorrect answers
        if(answer.equals("N") || failedReplyCounter >= acceptableNumFailedReplies) {
        	System.out.println("Goodbye!");
        	System.exit(0);
        }
        else {
        	failedReplyCounter = 0;
        }
        
        // Assign correct answers for the current game if player wants to play
        chooseCorrectAnswers(randWeaponNum, randCharacterNum, randRoomNum);
        
        // Add player to game
        addPlayer(0);  // Once we can access the server, change this to get the current number of players and assign that as the variable rather than 0
        System.out.println("Great! You are player: " + numPlayers);
        
        
        /*Character selection*/
        System.out.println("Which character would you like to be? Below are the available characters:");
        printAvailableCharacters();
        answer = sc.nextLine();
        
        // Handle unavailable answers
        while(!gm.charactersListContains(answer) && failedReplyCounter < acceptableNumFailedReplies) {
        	System.out.println("Sorry, that character was not on the list or available characters, please try again.");
        	failedReplyCounter++;
        	System.out.println("Which character would you like to be? Below are the available characters:");
            printAvailableCharacters();
            answer = sc.nextLine();
        }
        
        // Handle too many unavailable answers
        if(failedReplyCounter >= acceptableNumFailedReplies) {
        	System.out.println("Goodbye!");
        	System.exit(0);
        }
        else {
        	failedReplyCounter = 0;
        }
        
        // Output data to player and assign their choice to the player
        System.out.println("You have chosen: " + answer);
        chooseCharacter(answer);
        
        
        
        /**Start Game**/
        
        // Set required starting variables
        tm.setNumPlayers(numPlayers);
        assignStartKnowledge();
        
        
        /*Begin while loop, this breaks when game ends*/
        while(tm.getCurrentTurn()<5) {
        	
        	
        	/*Display beginning of turn info*/
        	System.out.println(divider);
        	System.out.println("Current turn: " + (tm.getCurrentTurn()+1));
        	System.out.println();
        	printKnowns();
        	System.out.println();
        	
        	
        	/*Construct current game board*/
        	if(tm.getCurrentTurn()/numPlayers == 0) {
        		gb.constructBoardWithStartAreas();
        	}
        	else {
        		gb.constructBoard();
        	}
            
        	
        	/*Display turn options*/
            System.out.println("You are in: " + player.currRoom.getName());
            System.out.println(divider);
            System.out.println("Your options this turn are:");
            printTurnOptions(tm.getTurnOptions(player.currRoom, gm));
            System.out.println("What would you like to do?");
            answer = sc.nextLine();
            System.out.println(divider);
            
            
            /*Handle Move*/
            if(answer.equals("Move")) {
            	System.out.println("Where would you like to move?");
            	printMoveOptions(tm.getMoveOptions(player.currRoom, gm));
            	answer = sc.nextLine();
                System.out.println(divider);
                move(answer);
            }
            
            
            /*Handle Accusation*/
            if(answer.equals("Accusation")) {
                System.out.println("The accused suspect: ");
                String character = sc.nextLine();
               
                System.out.println("The room where the crime occured: ");
                String room = sc.nextLine();
               
                System.out.println("The weapon used during the crime: ");
                String weapon = sc.nextLine();
            
                System.out.println("The accusation: " + character + " committed the crime in the " + room + " with the " + weapon);
		    
                 
                
                if (gm.accusation(weapon, character, room)) {
                	System.out.println("Congrats, you win!");
                }
                
                else {
                	System.out.println("I'm sorry, that is incorrect. You have been eliminated. You will still participate in suggestions, if chosen");
                	eliminatePlayer();
                }
            }
            
            
            /*Handle Suggestion*/
            if(answer.equals("Suggestion")) {
                System.out.println("Choose a suspect: ");
                String suspect = sc.nextLine();
               
                System.out.println("Choose a weapon: ");
                String weapon = sc.nextLine();
               
                System.out.println("The suggestion: Crime was committed in the " + player.currRoom.getName() + " by " + suspect + " with the " + weapon);
		     
		//assume that player number X made the suggestion
                //ask player #X+1 if they have the suspect or weapon or room that was suggested
                //if(player #X+1 has either the suspect or weapon or room suggested)
                //   player #X+1 reveals one of those cards to player X
                //   game continues as usual. It is the next player's turn to make a play
                //else
                //   ask player #X+2 if they have the suspect or weapon or room that was suggested
                //   if(player #X+2 has either the suspect or weapon or room suggested)
                //       player #X+2 reveals one of those cards to player X
                //       game continues as usual. It is the next player's turn to make a play
                //until someone shows a card, keep repeating the logic in the else statement by asking the other players if they have any of the cards
                //if no player has any of the suggested cards, the turn is over for player X. game continues as usual. It is the next player's turn to make a play
		     
            }
            
            
            /*Next Turn*/
            tm.nextTurn();
            System.out.println();
        }
        
        //Close Scanner
        sc.close();

	}

}