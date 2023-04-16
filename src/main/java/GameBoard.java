import java.util.ArrayList;

public class GameBoard {

    
    /***Variables***/
    private String[] abr;
	private String[] names;
	private String[] locs;

    GameManager gm = new GameManager();
    
    /***Methods***/
    GameBoard() {
        abr = new String[gm.getNumRooms()];
        
        for(int i = 0; i < abr.length; i++) {
            String room = gm.getRoomAt(i).getName();
            abr[i] = room.substring(0, 4);
        }
    }
    /* method to print out a player's first two letters of their name in the room corresponding to abr[i] 
     * i is passd as the parameter 
     */
    void printPlayer(int i) {
        boolean playerInRoom = false;
        for(int j = 0; j < names.length; j++) {
            if (gm.getRoomAt(i).getName().equals(locs[j])) {
                System.out.print(names[j].substring(0,6));
                playerInRoom = true;
            }
        }
        if (playerInRoom == false) {
            System.out.print("  --  ");
        }
    }

	void formatLocs(ArrayList <String> pLocations, ArrayList <String> pNames) {
		names = new String[pLocations.size()];
		locs = new String[pLocations.size()];

		for (int j = 0; j < pLocations.size(); j++) {
			names[j] =  pNames.get(j);
			locs[j] = pLocations.get(j);
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

    void contructBoardWithPlayers(ArrayList<String> pLocations, ArrayList<String> pNames) {
		formatLocs(pLocations, pNames);
        System.out.println("              +----------+       +----------+       +----------+");
        System.out.println("              |          |-------|          |-------|          |");
        System.out.println("              |  " + abr[0] +   "    |       |  " + abr[1] +   "    |       |  " + abr[2] +   "    |");
        System.out.print("              |  ");
        printPlayer(0);
        System.out.print("  |       |  ");
        printPlayer(1);
        System.out.print("  |       |  ");
        printPlayer(2);
        System.out.println("  |");                                                                    
        System.out.println("              |          |-------|          |-------|          |");
        System.out.println("              +----------+       +----------+       +----------+");
        System.out.println("                  |   |              |   |              |   |");
        System.out.println("                  |   |              |   |              |   |");
        System.out.println("                  |   |              |   |              |   |");
        System.out.println("              +----------+       +----------+       +----------+");
        System.out.println("              |          |-------|          |-------|          |");
        System.out.println("              |  " + abr[3]   + "    |       |  " + abr[4] +   "    |       |  " + abr[5] +   "    |"); 
        System.out.print("              |  ");
        printPlayer(3);
        System.out.print("  |       |  ");
        printPlayer(4);
        System.out.print("  |       |  ");
        printPlayer(5);
        System.out.println("  |");    
        System.out.println("              |          |-------|          |-------|          |");
        System.out.println("              +----------+       +----------+       +----------+");
        System.out.println("                  |   |              |   |              |   |  ");
        System.out.println("                  |   |              |   |              |   |  ");
        System.out.println("                  |   |              |   |              |   |  ");
        System.out.println("              +----------+       +----------+       +----------+");
        System.out.println("              |          |-------|          |-------|          |");
        System.out.println("              |  " + abr[6] +   "    |       |  " + abr[7] +   "    |       |  " + abr[8] +   "    |");
        System.out.print("              |  ");
        printPlayer(6);
        System.out.print("  |       |  ");
        printPlayer(7);
        System.out.print("  |       |  ");
        printPlayer(8);
        System.out.println("  |");        
        System.out.println("              |          |-------|          |-------|          |");
        System.out.println("              +----------+       +----------+       +----------+");

    }
}