
public class GameBoard {
	
	/***Variables***/
	private String[] abr;
	
	GameManager gm = new GameManager();
	
	/***Methods***/
	GameBoard() {
		abr = new String[gm.getNumRooms()];
		
		for(int i = 0; i < abr.length; i++) {
			String room = gm.getRoomAt(i);
			abr[i] = room.substring(0, 3);
		}
	}
	
	void constructBoardWithStartAreas() {
		System.out.println("                                      +-------+                      ");
		System.out.println("                                      |Scarlet|                      ");
		System.out.println("                                      |Start  |                      ");
		System.out.println("                                      |Area   |                      ");
		System.out.println("                                      +-------+                      ");
		System.out.println("                                        |   |                        ");
		System.out.println("                                        | | |                        ");
		System.out.println("                                        | | |                        ");
		System.out.println("              +-------+       +-------+ | V | +-------+              ");
		System.out.println("              |       |-------|       |-------|       |              ");
		System.out.println("              |  " + abr[0] + "  |       |  " + abr[1] + "  |       |  " + abr[2] + "  |              ");
		System.out.println("              |       |-------|       |-------|       |              ");
		System.out.println("+-------+     +-------+       +-------+       +-------+     +-------+");
		System.out.println("|Plum   |-------|   |           |   |           |   |-------|Mustard|");
		System.out.println("|Start  |   --> |   |           |   |           |   | <--   |Start  |");
		System.out.println("|Area   |-------|   |           |   |           |   |-------|Area   |");
		System.out.println("+-------+     +-------+       +-------+       +-------+     +-------+");
		System.out.println("              |       |-------|       |-------|       |              ");
		System.out.println("              |  " + abr[3] + "  |       |  " + abr[4] + "  |       |  " + abr[5] + "  |              ");
		System.out.println("              |       |-------|       |-------|       |              ");
		System.out.println("+-------+     +-------+       +-------+       +-------+              ");
		System.out.println("|Peacock|-------|   |           |   |           |   |                ");
		System.out.println("|Start  |   --> |   |           |   |           |   |                ");
		System.out.println("|Area   |-------|   |           |   |           |   |                ");
		System.out.println("+-------+     +-------+       +-------+       +-------+              ");
		System.out.println("              |       |-------|       |-------|       |              ");
		System.out.println("              |  " + abr[6] + "  |       |  " + abr[7] + "  |       |  " + abr[8] + "  |              ");
		System.out.println("              |       |-------|       |-------|       |              ");
		System.out.println("              +-------+ | ^ | +-------+ | ^ | +-------+              ");
		System.out.println("                        | | |           | | |                        ");
		System.out.println("                        | | |           | | |                        ");
		System.out.println("                        |   |           |   |                        ");
		System.out.println("                      +-------+       +-------+                      ");
		System.out.println("                      |Green  |       |White  |                      ");
		System.out.println("                      |Start  |       |Start  |                      ");
		System.out.println("                      |Area   |       |Area   |                      ");
		System.out.println("                      +-------+       +-------+                      ");
	}
	
	void constructBoard() {
		System.out.println("              +-------+       +-------+       +-------+");
		System.out.println("              |       |-------|       |-------|       |");
		System.out.println("              |  " + abr[0] + "  |       |  " + abr[1] + "  |       |  " + abr[2] + "  |");
		System.out.println("              |       |-------|       |-------|       |");
		System.out.println("              +-------+       +-------+       +-------+");
		System.out.println("                |   |           |   |           |   |");
		System.out.println("                |   |           |   |           |   |");
		System.out.println("                |   |           |   |           |   |");
		System.out.println("              +-------+       +-------+       +-------+");
		System.out.println("              |       |-------|       |-------|       |");
		System.out.println("              |  " + abr[3] + "  |       |  " + abr[4] + "  |       |  " + abr[5] + "  |");
		System.out.println("              |       |-------|       |-------|       |");
		System.out.println("              +-------+       +-------+       +-------+");
		System.out.println("                |   |           |   |           |   |  ");
		System.out.println("                |   |           |   |           |   |  ");
		System.out.println("                |   |           |   |           |   |  ");
		System.out.println("              +-------+       +-------+       +-------+");
		System.out.println("              |       |-------|       |-------|       |");
		System.out.println("              |  " + abr[6] + "  |       |  " + abr[7] + "  |       |  " + abr[8] + "  |");
		System.out.println("              |       |-------|       |-------|       |");
		System.out.println("              +-------+       +-------+       +-------+");
	}

}
