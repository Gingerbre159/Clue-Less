import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.util.concurrent.MoreExecutors;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;

public class Server {
	
	
	
	
	/***Variables***/
	
	// Local variables
	private static int numPlayers = 0;
	private static Player player;
	private static ArrayList<String> chosenCharacters = new ArrayList<>();
	
	// Variables to hold other classes
	static GameManager gm = new GameManager();
	static GameBoard gb = new GameBoard();
	static TurnManager tm = new TurnManager();
	
	// Allow use of FireStore database
	private static Firestore db;
	
	
	
	
	/***Methods***/
	
	// Add a player to the game
	static void initializePlayer(int playerNum) {
		player = new Player(playerNum, null);
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
					player.knownWeapons.add(gm.getWeaponAt(i));
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
					player.knownCharacters.add(gm.getCharacterAt(i));
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
					player.knownRooms.add(gm.getRoomAt(i).getName());
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
		chosenCharacters.add(character);
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
	
	// Clear the console so it is not so cluttered
	public static void clearScreen() {
		for (int i = 0; i < 50; i++) {
	        System.out.println();
	    }
    }
	
	
	
	
	/***FireStore Operations***/
	
	
	/*Player Functions*/
	
	// Add player to FireStore
	public static void addPlayer(Player player) {
		DocumentReference docRef = db.collection("players").document(Integer.toString(player.playerNum));
	    docRef.set(player);
	}
	
	// Update player's data in FireStore
	public static void updatePlayer(Player player) {
		DocumentReference docRef = db.collection("players").document(Integer.toString(player.playerNum));
	    docRef.update(
	        "character", player.character,
	        "currRoom", player.currRoom.getName(),
	        "isEliminated", player.isEliminated,
	        "knownWeapons", player.knownWeapons,
	        "knownCharacters", player.knownCharacters,
	        "knownRooms", player.knownRooms
	    );
	}
	
	// Retrieve player data from FireStore
	public static Player getPlayer(int playerNum) {
	    DocumentReference docRef = db.collection("players").document(Integer.toString(playerNum));
	    ApiFuture<DocumentSnapshot> future = docRef.get();
	    Player player = null;

	    try {
	        DocumentSnapshot document = future.get();
	        if (document.exists()) {
	            int playerNumber = document.getLong("playerNum").intValue();
	            String character = document.getString("character");
	            String currRoomName = document.getString("currRoom");
	            boolean isEliminated = document.getBoolean("isEliminated");
	            ArrayList<String> knownWeapons = (ArrayList<String>) document.get("knownWeapons");
	            ArrayList<String> knownCharacters = (ArrayList<String>) document.get("knownCharacters");
	            ArrayList<String> knownRooms = (ArrayList<String>) document.get("knownRooms");

	            player = new Player(playerNumber, character);
	            player.currRoom = new Room(currRoomName);
	            player.isEliminated = isEliminated;
	            player.knownWeapons = knownWeapons;
	            player.knownCharacters = knownCharacters;
	            player.knownRooms = knownRooms;
	        } else {
	            System.out.println("No such document!");
	        }
	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    }

	    return player;
	}
	
	
	/*Game Functions*/
	
	// Get the current turn from FireStore
	public static int getCurrentTurn() {
	    DocumentReference docRef = db.collection("game").document("currentTurn");
	    ApiFuture<DocumentSnapshot> future = docRef.get();
	    int turnNumber = 0;

	    try {
	        DocumentSnapshot document = future.get();
	        if (document.exists()) {
	            turnNumber = document.getLong("turnNumber").intValue();
	        } else {
	            System.out.println("No current turn document!");
	        }
	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    }

	    return turnNumber;
	}
	
	// Set the current turn in FireStore
	public static void setCurrentTurn(int turnNumber) {
	    DocumentReference docRef = db.collection("game").document("currentTurn");
	    docRef.set(Collections.singletonMap("turnNumber", turnNumber));
	}
	
	// Delete all player and turn data from the server to reset the game state
	public static void resetGame() {
	    // Get a reference to the game collection
	    CollectionReference gameRef = db.collection("game");
	    ApiFuture<QuerySnapshot> gameFuture = gameRef.get();

	    // Reset the players data
	    CollectionReference playersRef = db.collection("players");
	    ApiFuture<QuerySnapshot> playersFuture = playersRef.get();

	    try {
	        // Delete all documents in the game collection
	        List<QueryDocumentSnapshot> gameDocuments = gameFuture.get().getDocuments();
	        for (QueryDocumentSnapshot gameDocument : gameDocuments) {
	            gameRef.document(gameDocument.getId()).delete();
	        }

	        // Delete all documents in the players collection
	        List<QueryDocumentSnapshot> playerDocuments = playersFuture.get().getDocuments();
	        for (QueryDocumentSnapshot playerDocument : playerDocuments) {
	            playersRef.document(playerDocument.getId()).delete();
	        }
	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    }
	}
	
	// Get the total number of players
	public static int getTotalPlayers() {
	    ApiFuture<QuerySnapshot> futurePlayers = db.collection("players").get();
	    int totalPlayers = 0;

	    try {
	        List<QueryDocumentSnapshot> playerDocuments = futurePlayers.get().getDocuments();
	        totalPlayers = playerDocuments.size();
	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    }

	    return totalPlayers;
	}
	
	// Set the current player in FireStore
	public static void setCurrentPlayer(Player currentPlayer) {
	    DocumentReference docRef = db.collection("game").document("currentPlayer");
	    docRef.set(Collections.singletonMap("playerNum", currentPlayer.playerNum));
	}
	
	// New version of getCurrentPlayer()
	public static Player getCurrentPlayer() {
	    try {
	        // Get the current player number from the server
	        DocumentReference currentPlayerNumDocRef = db.collection("game").document("currentPlayer");
	        ApiFuture<DocumentSnapshot> currentPlayerNumFuture = currentPlayerNumDocRef.get();
	        DocumentSnapshot currentPlayerNumSnapshot = currentPlayerNumFuture.get();
	        int currentPlayerNum = ((Number) currentPlayerNumSnapshot.get("playerNum")).intValue();

	        // Get the player data for the current player number
	        DocumentReference playerDocRef = db.collection("players").document(Integer.toString(currentPlayerNum));
	        ApiFuture<DocumentSnapshot> playerFuture = playerDocRef.get();
	        DocumentSnapshot playerSnapshot = playerFuture.get();

	        if (playerSnapshot.exists()) {
	            Player player = new Player();
	            player.playerNum = currentPlayerNum;
	            player.character = playerSnapshot.getString("character");
	            player.isEliminated = playerSnapshot.getBoolean("isEliminated");
	            player.knownWeapons = (ArrayList<String>) playerSnapshot.get("knownWeapons");
	            player.knownCharacters = (ArrayList<String>) playerSnapshot.get("knownCharacters");
	            player.knownRooms = (ArrayList<String>) playerSnapshot.get("knownRooms");

	            String roomName = playerSnapshot.getString("currRoom");
	            if (roomName != null) {
	                player.currRoom = new Room(roomName);
	            }

	            return player;
	        } else {
	            return null;
	        }
	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	// Start the game for everyone, only to be called by player 0
	public static void setGameStarted(boolean started) {
	    DocumentReference docRef = db.collection("game").document("gameStatus");
	    docRef.set(Collections.singletonMap("gameStarted", started));
	}
	
	// Get status of game starting
	public static boolean isGameStarted() {
	    DocumentReference docRef = db.collection("game").document("gameStatus");
	    ApiFuture<DocumentSnapshot> future = docRef.get();
	    boolean gameStarted = false;

	    try {
	        DocumentSnapshot document = future.get();
	        if (document.exists()) {
	            gameStarted = document.getBoolean("gameStarted");
	        } else {
	            System.out.println("No gameStatus document!");
	        }
	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    }

	    return gameStarted;
	}
	
	// Get a list of all player locations
	public static ArrayList<String> getAllPlayerLocations() {
	    CollectionReference playersRef = db.collection("players");
	    ApiFuture<QuerySnapshot> future = playersRef.get();
	    ArrayList<String> playerLocations = new ArrayList<>();

	    try {
	        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
	        for (QueryDocumentSnapshot document : documents) {
	            Map<String, Object> data = document.getData();
	            String location = data.get("currRoom").toString();
	            playerLocations.add(location);
	        }
	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    }

	    return playerLocations;
	}
	
	// Get a list of all chosen characters
	public static ArrayList<String> getAllChosenCharacters() {
	    CollectionReference playersRef = db.collection("players");
	    ApiFuture<QuerySnapshot> future = playersRef.get();
	    ArrayList<String> chosenCharacters = new ArrayList<>();

	    try {
	        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
	        for (QueryDocumentSnapshot document : documents) {
	            Map<String, Object> data = document.getData();
	            String character = data.get("character").toString();
	            chosenCharacters.add(character);
	        }
	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    }

	    return chosenCharacters;
	}
	
	// Set the correct weapon, character, and room
	public static void setSolution(String correctWeapon, String correctCharacter, String correctRoom) {
	    DocumentReference docRef = db.collection("game").document("correct");
	    ArrayList<String> solutionData = new ArrayList<>();
	    solutionData.add(correctWeapon);
	    solutionData.add(correctCharacter);
	    solutionData.add(correctRoom);
	    Map<String, Object> data = new HashMap<>();
	    data.put("solutionData", solutionData);
	    docRef.set(data);
	}
	
	// Get the correct weapon, character, and room
	public static ArrayList<String> getSolution() {
	    DocumentReference docRef = db.collection("game").document("correct");
	    CompletableFuture<ArrayList<String>> futureResult = new CompletableFuture<>();
	    ApiFuture<DocumentSnapshot> future = docRef.get();
	
	    future.addListener(() -> {
	        try {
	            DocumentSnapshot document = future.get();
	            if (document.exists()) {
	                ArrayList<String> solutionData = (ArrayList<String>) document.get("solutionData");
	                futureResult.complete(solutionData);
	            } else {
	                System.out.println("No solution data found");
	                futureResult.complete(null);
	            }
	        } catch (InterruptedException | ExecutionException e) {
	            e.printStackTrace();
	            futureResult.completeExceptionally(e);
	        }
	    }, MoreExecutors.directExecutor());
	
	    ArrayList<String> solutionData = null;
	    try {
	        solutionData = futureResult.get();
	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    }
	    return solutionData;
	}
	
	// Goes through the players knowns are returns the matching string or the string "none" if there are no matches
	public static String processSuggestion(int suggestingPlayerNum, String suggestedWeapon, String suggestedCharacter, String suggestedRoom) {
	    int totalPlayers = getTotalPlayers();
	    String matchingString = "None";
	    
	    for (int i = 0; i < totalPlayers; i++) {
	        if (i != suggestingPlayerNum) { // Skip the suggesting player
	            Player currentPlayer = getPlayer(i);
	            
	            if (currentPlayer.knownWeapons.contains(suggestedWeapon)) {
	                matchingString = suggestedWeapon;
	            } else if (currentPlayer.knownCharacters.contains(suggestedCharacter)) {
	                matchingString = suggestedCharacter;
	            } else if (currentPlayer.knownRooms.contains(suggestedRoom)) {
	                matchingString = suggestedRoom;
	            }
	            
	            if (!matchingString.equals("None")) {
	                System.out.println("Player " + currentPlayer.character + " knows it cannot be " + matchingString);
	                break;
	            }
	        }
	    }
	    
	    if (matchingString.equals("None")) {
	        System.out.println("No matches found.");
	    }
	    
	    return matchingString;
	}
	
	// Set win condition status
	public static void setWinCondition(Boolean winCondition) {
	    DocumentReference docRef = db.collection("game").document("winCondition");
	    Map<String, Object> data = new HashMap<>();
	    data.put("value", winCondition);
	    docRef.set(data);
	}
	
	// Get win condition status
	public static Boolean getWinCondition() {
	    DocumentReference docRef = db.collection("game").document("winCondition");
	    ApiFuture<DocumentSnapshot> future = docRef.get();
	    Boolean winCondition = null;

	    try {
	        DocumentSnapshot document = future.get();
	        if (document.exists()) {
	        	winCondition = document.getBoolean("value");
	        } else {
	            System.out.println("No win condition status document");
	        }
	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    }

	    return winCondition;
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
        
        
        
        /**Initialize FireBase Admin SDK**/
        try {
            FileInputStream serviceAccount = new FileInputStream(gm.fs_service_account);
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://clue-less-fa80b.firebaseio.com")
                .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            System.out.println("Error initializing Firebase Admin SDK");
            e.printStackTrace();
        }
        
        // Assign to FireStore database
        db = FirestoreClient.getFirestore();
        

        resetGame();
        
		
		
        /**PreGame**/
        
        
        /*Ask if the user would like to play*/
        
        // Check if there are already 6 players
        if(getTotalPlayers() < 6) {
        	System.out.println("Would you like to play Clue-less? (Y/N)");
            answer = sc.nextLine();
        }
        else {
        	answer = "N";
        }
        
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
        	numPlayers = getTotalPlayers();
        	clearScreen();
        }
        
//        // Assign correct answers for the current game if player wants to play
//        chooseCorrectAnswers(randWeaponNum, randCharacterNum, randRoomNum);
        
        // Add player to game
        initializePlayer(numPlayers);
        addPlayer(player);
        System.out.println("Great! You are player: " + (player.playerNum + 1));
        
        // Handle start game condition
        if(player.playerNum == 0) {
        	setGameStarted(false);
        	setCurrentTurn(0);
        	setWinCondition(false);
        	
        	setSolution(gm.getWeaponAt(randWeaponNum), gm.getCharacterAt(randCharacterNum), gm.getRoomAt(randRoomNum).getName());
        }
        
        
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
        	clearScreen();
        }
        
        // Output data to player and assign their choice to the player
        System.out.println("You are player: " + (player.playerNum + 1));
        System.out.println("You have chosen: " + answer);
        chooseCharacter(answer);
        player.currRoom = new Room(getStartArea(player));
        updatePlayer(player);
        
        /*Wait to start the game*/
        
        // If the player is player 0 they decide when to start the game
        if (player.playerNum == 0) {
            // Wait for input from player 0 to start the game, then call setGameStarted(true)
            // ...
        	System.out.println("When everyone has joined, type 'Start'");
            answer = sc.nextLine();
            
            // Handle incorrect response
            while(!answer.equals("Start") && failedReplyCounter < acceptableNumFailedReplies) {
            	System.out.println("Sorry, that was not an available response.");
            	failedReplyCounter++;
            	System.out.println("When everyone has joined, type 'Start'");
                answer = sc.nextLine();
            }
            
            setGameStarted(true);
        } 
        
        // Other players wait until player 0 starts the game
        else {
        	System.out.println("Waiting for Player 1 to start the game");
            while (!isGameStarted()) {
                try {
                    // Sleep for a short time to avoid busy waiting
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        clearScreen();
        
        
        
        /**Start Game**/
        
        // Set required starting variables
        numPlayers = getTotalPlayers();
        tm.setNumPlayers(numPlayers);
        ArrayList<String> correct = getSolution();
        gm.setCorrectWeapon(correct.get(0));
        gm.setCorrectCharacter(correct.get(1));
        gm.setCorrectRoom(correct.get(2));
        gm.winCondition = getWinCondition();
        assignStartKnowledge();
        updatePlayer(player);
        chosenCharacters = getAllChosenCharacters();
        
        
        /*Begin while loop, this breaks when game ends*/
        while(!gm.winCondition) {
        	
        	
        	/*Check if it is the player's turn and they are not eliminated*/
        	if(tm.getCurrentTurn() % numPlayers == player.playerNum && !player.isEliminated) {
        		
        		// Set new current player in server
        		setCurrentPlayer(player);
            	
            	
            	/*Construct current game board*/
            	if(tm.getCurrentTurn()/numPlayers == 0) {
            		gb.constructBoardWithStartAreas();
            	}
            	else {
					gb.contructBoardWithPlayers(getAllPlayerLocations(), chosenCharacters);
            		//gb.constructBoard();
            	}
            	
            	
            	/*Display beginning of turn info*/
            	System.out.println();
            	System.out.println(divider);
            	System.out.println("Current turn: " + (tm.getCurrentTurn()+1));
            	System.out.println();
            	gm.printKnowns(player);
            	System.out.println();
            	gm.printUnknowns(player);
            	System.out.println();
            	System.out.println("You are in: " + player.currRoom.getName());
                System.out.println(divider);
                System.out.println();
                
            	
            	/*Display turn options*/
                System.out.println("Your options this turn are:");
                printTurnOptions(tm.getTurnOptions(player.currRoom, gm));
                System.out.println("What would you like to do?");
                answer = sc.nextLine();
                System.out.println(divider);
                System.out.println();
                
                
                /*Handle Move*/
                if(answer.equals("Move")) {
                	System.out.println("Where would you like to move?");
                	
                	// Gather turn options and output to player
                	ArrayList<String> moveOptions = tm.getMoveOptions(player.currRoom, gm, getAllPlayerLocations());
                	printMoveOptions(moveOptions);
                	answer = sc.nextLine();
                	
                	// Handle incorrect responses
                	while(!moveOptions.contains(answer)) {
                		System.out.println("Sorry, that was not one of the possible choices");
                		printMoveOptions(moveOptions);
                		answer = sc.nextLine();
                	}
                	
                	// Output result and update
                    System.out.println(divider);
                    move(answer);
                    updatePlayer(player);
                    setCurrentTurn(getCurrentTurn()+1);
                }
                
                
                /*Handle Accusation*/
                else if(answer.equals("Accusation")) {
                	System.out.println(gm.getCharacters());
                    System.out.println("The accused suspect: ");
                    String character = sc.nextLine();
                    
                    System.out.println(gm.getRoomNames());
                    System.out.println("The room where the crime occured: ");
                    String room = sc.nextLine();
                    
                    System.out.println(gm.getWeapons());
                    System.out.println("The weapon used during the crime: ");
                    String weapon = sc.nextLine();
                
                    System.out.println("The accusation: " + character + " committed the crime in the " + room + " with the " + weapon);
    		    
                     
                    // If player accuses correctly
                    if (gm.accusation(weapon, character, room)) {
                    	gm.winCondition = true;
                    }
                    
                    // If player is wrong
                    else {
                    	System.out.println("I'm sorry, that is incorrect. You have been eliminated. You will still participate in suggestions, if chosen");
                    	eliminatePlayer();
                    	updatePlayer(player);
                    	setCurrentTurn(getCurrentTurn()+1);
                    }
                }
                
                
                /*Handle Suggestion*/
                else if(answer.equals("Suggestion")) {
                	
                	/*Weapon*/
                	System.out.println("Choose a weapon: ");
                    String weapon = sc.nextLine();
                    
                    // Handle incorrect responses
                	while(!gm.getWeapons().contains(weapon)) {
                		System.out.println("Sorry, that was not one of the possible choices, please try again");
                		weapon = sc.nextLine();
                	}
                    
                	/*Character*/
                    System.out.println("Choose a character: ");
                    String character = sc.nextLine();
                    
                    // Handle incorrect responses
                	while(!gm.getCharacters().contains(character)) {
                		System.out.println("Sorry, that was not one of the possible choices, please try again");
                		character = sc.nextLine();
                	}
                    
                    System.out.println("The suggestion: Crime was committed in the " + player.currRoom.getName() + " by " + character + " with the " + weapon);
                    
                    // Check suggestion
                    String matchingString = processSuggestion(player.playerNum, weapon, character, player.currRoom.getName());
                    
                    // Assign new knowledge
                    if(gm.weaponsListContains(matchingString)) {
                    	player.knownWeapons.add(matchingString);
                    }
                    else if(gm.charactersListContains(matchingString)) {
                    	player.knownCharacters.add(matchingString);
                    }
                    else if(gm.roomsListContains(matchingString)) {
                    	player.knownRooms.add(matchingString);
                    }
                    
                    // Update new info to server and move to next turn
                    updatePlayer(player);
                	setCurrentTurn(getCurrentTurn()+1);
                }
                    
                /*Next Turn*/                
                try {
                    // Sleep for a short time to allow turn to update
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                tm.setCurrentTurn(getCurrentTurn());
                
                clearScreen();
            }
        	
        	
        	/*If it is player's turn but they are eliminated*/
        	else if(tm.getCurrentTurn() % numPlayers == player.playerNum && player.isEliminated) {
        		
        		setCurrentTurn(getCurrentTurn()+1);
        		try {
                    // Sleep for a short time to allow turn to update
                    Thread.sleep(500);
                    tm.setCurrentTurn(getCurrentTurn());
                    gm.winCondition = getWinCondition();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        		
        	}
        	
        	
        	/*If it is not the player's turn*/
        	else {
        		try {
                    // Sleep for a short time to avoid busy waiting
                    Thread.sleep(500);
                    tm.setCurrentTurn(getCurrentTurn());
                    gm.winCondition = getWinCondition();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        	}
        		
        }
        
        
        
        /**End Game**/
        int turnNum = getCurrentTurn();
        Player winner = getCurrentPlayer();
        
        // Display win screen for winner
        gm.endScreen(turnNum, numPlayers, winner, player.playerNum);
        
        // Delete data to reset game when player 0 finishes game
        if(player.playerNum == 0) {
        	System.out.println("End Game?");
            answer = sc.nextLine();
        	
        	resetGame();
        }
        
        //Close Scanner
        sc.close();

	}

}