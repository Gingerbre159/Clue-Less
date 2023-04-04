
public class Player {
	
	/***Variables***/
	private int playerNum;
	private String character;
	private Room currRoom = null;
	
	/***Methods***/
	Player(int playerNum, String character){
		this.playerNum = playerNum;
		this.character = character;
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
