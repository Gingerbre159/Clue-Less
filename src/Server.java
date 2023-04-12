import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Server {
	
	/***Variables***/
	private static int numPlayers = 0;
	private static ArrayList<Player> players = new ArrayList<>();
	private static ArrayList<String> currPlayerLocations = new ArrayList<>();
	
	static GameManager gm = new GameManager();
	static GameBoard gb = new GameBoard();
	static TurnManager tm = new TurnManager();
	
	/***Methods***/
	static void addPlayer() {
		Player newPlayer = new Player(numPlayers, null);
		players.add(newPlayer);
		currPlayerLocations.add("Unknown");
		numPlayers++;
	}
	
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
	
	static void chooseCharacter(int playerNum, String character) {
		Player tempPlayerObject = new Player(playerNum, character);
		Room tempRoomObject = new Room(getStartArea(character));
		tempPlayerObject.setCurrRoom(tempRoomObject);
		currPlayerLocations.set(playerNum, tempPlayerObject.getCurrRoom().getName());
		players.set(playerNum, tempPlayerObject);
	}
	
	static void printAvailableCharacters() {
		int numCharacters = gm.getNumCharacters();
		
		for(int i = 0; i < numCharacters; i++) {
			String currCharacter = gm.getCharacterAt(i);
			Boolean characterTaken = false;
			
			for(int j = 0; j < numPlayers; j++) {
				Player currPlayer = players.get(j);
				String currPlayerCharacter = currPlayer.getCharacter();
				if(currCharacter.equals(currPlayerCharacter)) {
					characterTaken = true;
				}
			}
			
			if(!characterTaken) {
				System.out.println(" - " + currCharacter);
			}
		}
	}
	
	static void chooseCorrectAnswers(int randWeaponNum, int randCharacterNum, int randRoomNum) {
		gm.setCorrectWeapon(gm.getWeaponAt(randWeaponNum));
		gm.setCorrectCharacter(gm.getCharacterAt(randCharacterNum));
		gm.setCorrectRoom(gm.getRoomAt(randRoomNum).getName());
	}
	
	static void printTurnOptions(ArrayList<String> options) {
		for(int i = 0; i < options.size(); i++) {
			System.out.println(" - " + options.get(i));
		}
	}
	
	static void printMoveOptions(ArrayList<String> options) {
		for(int i = 0; i < options.size(); i++) {
			System.out.println(" - " + options.get(i));
		}
	}
	
	static void move(int playerNum, String location) {
		Player temp = players.get(playerNum);
		Room room = gm.getRoom(location);
		temp.setCurrRoom(room);
		players.set(playerNum, temp);
	}
	
	/***Main***/
	public static void main(String[] args) {
		
		/*Variables*/
		int acceptableNumFailedReplies = 2;
		int failedReplyCounter = 0;
		String answer;
		String divider = "--------------------------------";
		
		Scanner sc = new Scanner(System.in);
		Random random = new Random();
		
        int randWeaponNum = random.nextInt(6);
        int randCharacterNum = random.nextInt(6);
        int randRoomNum = random.nextInt(9);
        
        chooseCorrectAnswers(randWeaponNum, randCharacterNum, randRoomNum);
        
        /*PreGame*/
        
        //Ask if the user would like to play
        System.out.println("Would you like to play Clue-less? (Y/N)");
        answer = sc.nextLine();
        //answer = "Y";
        
        while(!answer.equals("N") && !answer.equals("Y") && failedReplyCounter < acceptableNumFailedReplies) {
        	System.out.println("Sorry, that was not one of the acceptable responses. Please only write 'Y' or 'N'");
        	failedReplyCounter++;
        	System.out.println("Would you like to play Clue-less? (Y/N)");
            answer = sc.nextLine();
        }
        
        if(answer.equals("N") || failedReplyCounter >= acceptableNumFailedReplies) {
        	System.out.println("Goodbye!");
        	System.exit(0);
        }
        else {
        	failedReplyCounter = 0;
        }
        
        //Character selection
        addPlayer();
        System.out.println("Great! You are player: " + numPlayers);
        System.out.println("Which character would you like to be? Below are the available characters:");
        printAvailableCharacters();
        answer = sc.nextLine();
        //answer = "Miss Scarlet";
        
        while(!gm.charactersListContains(answer) && failedReplyCounter < acceptableNumFailedReplies) {
        	System.out.println("Sorry, that character was not on the list or available characters, please try again.");
        	failedReplyCounter++;
        	System.out.println("Which character would you like to be? Below are the available characters:");
            printAvailableCharacters();
            answer = sc.nextLine();
        }
        
        if(failedReplyCounter >= acceptableNumFailedReplies) {
        	System.out.println("Goodbye!");
        	System.exit(0);
        }
        else {
        	failedReplyCounter = 0;
        }
        
        System.out.println("You have chosen: " + answer);
        chooseCharacter(numPlayers-1, answer);
        
        /*Start Game*/
        
        tm.setNumPlayers(numPlayers);
        
        while(tm.getCurrentTurn()<5) {
        	System.out.println();
        	
        	if(tm.getCurrentTurn()/numPlayers == 0) {
        		gb.constructBoardWithStartAreas();
        	}
        	else {
        		gb.constructBoard();
        	}
            
            System.out.println("You are in: " + players.get(numPlayers-1).getCurrRoom().getName());
            System.out.println(divider);
            System.out.println("Your options this turn are:");
            printTurnOptions(tm.getTurnOptions(players.get(numPlayers-1).getCurrRoom(), gm));
            System.out.println("What would you like to do?");
            answer = sc.nextLine();
            System.out.println(divider);
            
            if(answer.equals("Move")) {
            	System.out.println("Where would you like to move?");
            	printMoveOptions(tm.getMoveOptions(players.get(numPlayers-1).getCurrRoom(), gm));
            	answer = sc.nextLine();
                System.out.println(divider);
                move(numPlayers-1, answer);
            }
		
	    if(answer.equals("Accusation")) {
                System.out.println("The accused suspect: ");
                String suspect = sc.nextLine();
               
                System.out.println("The room where the crime occured: ");
                String room = sc.nextLine();
               
                System.out.println("The weapon used during the crime: ");
                String weapon = sc.nextLine();
            
                System.out.println("The accusation: " + suspect + " committed the crime in the " + room + " with the " + weapon);
             }
            
             if(answer.equals("Suggestion")) {
                System.out.println("Choose a suspect: ");
                String suspect = sc.nextLine();
               
                System.out.println("Choose a weapon: ");
                String weapon = sc.nextLine();
               
                System.out.println("The suggestion: Crime was committed in the " + players.get(numPlayers-1).getCurrRoom().getName() + " by " + suspect + " with the " + weapon);
             }
            
            //Next Turn
            tm.nextTurn();
        }
        
        
        //Close Scanner
        sc.close();

	}

}
