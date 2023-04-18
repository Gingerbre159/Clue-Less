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

}
