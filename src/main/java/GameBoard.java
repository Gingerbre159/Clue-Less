import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.*;

public class GameBoard {

    
    /***Variables***/
    private String[] abr;
	private String[] names;
	private String[] locs;
    private String[] weapArr = {
        "Rev", 
		"Dag", 
		"Lea", 
		"Rop", 
		"Can", 
		"Wre",
        "   ",
        "   ",
        "   "
    };

    private String[] weapArrPop = {
        "Rev", 
		"Dag", 
		"Lea", 
		"Rop", 
		"Can", 
		"Wre",
        "---",
        "---",
        "---"
    };
    ArrayList<String> weaps = new ArrayList<>(Arrays.asList(weapArr));
    ArrayList<String> weapsPop = new ArrayList<>(Arrays.asList(weapArrPop));

    GameManager gm = new GameManager();
    
    /***Methods***/
    GameBoard() {
        abr = new String[gm.getNumRooms()];
        
        for(int i = 0; i < abr.length; i++) {
            String room = gm.getRoomAt(i).getName();
            //abr[i] = room.substring(0, 3);
            abr[i] = room;
        }

        Collections.shuffle(weaps);
        Collections.shuffle(weapsPop);
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

    String printPlayerUpdated(int i) {
        String player1 = "";
        boolean playerInRoom = false;
        for(int j = 0; j < names.length; j++) {
            if (gm.getRoomAt(i).getName().equals(locs[j])) {
                player1 = (names[j].substring(0,9));
                playerInRoom = true;
            }
        }
        if (playerInRoom == false) {
            player1 = "    ---     ";
        }
        return player1;
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
        System.out.println("              |  " + weaps.get(0) + "  |-------|  " + weaps.get(1) + "  |-------|  " + weaps.get(2) + "  |              ");
        System.out.println("              |  " + abr[0] + "  |       |  " + abr[1] + "  |       |  " + abr[2] + "  |              ");
        System.out.println("              |       |-------|       |-------|       |              ");
        System.out.println("+-------+     +-------+       +-------+       +-------+     +-------+");
        System.out.println("|Plum   |-------|   |           |   |           |   |-------|Mustard|");
        System.out.println("|Start  |   --> |   |           |   |           |   | <--   |Start  |");
        System.out.println("|Area   |-------|   |           |   |           |   |-------|Area   |");
        System.out.println("+-------+     +-------+       +-------+       +-------+     +-------+");
        System.out.println("              |  " + weaps.get(3) + "  |-------|  " + weaps.get(4) + "  |-------|  " + weaps.get(5) + "  |              ");
        System.out.println("              |  " + abr[3] + "  |       |  " + abr[4] + "  |       |  " + abr[5] + "  |              ");
        System.out.println("              |       |-------|       |-------|       |              ");
        System.out.println("+-------+     +-------+       +-------+       +-------+              ");
        System.out.println("|Peacock|-------|   |           |   |           |   |                ");
        System.out.println("|Start  |   --> |   |           |   |           |   |                ");
        System.out.println("|Area   |-------|   |           |   |           |   |                ");
        System.out.println("+-------+     +-------+       +-------+       +-------+              ");
        System.out.println("              |  " + weaps.get(6) + "  |-------|  " + weaps.get(7) + "  |-------|  " + weaps.get(8) + "  |              ");
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
        System.out.println("              |  " + weaps.get(0) + "  |-------|  " + weaps.get(1) + "  |-------|  " + weaps.get(2) + "  |");
        System.out.println("              |  " + abr[0] + "  |       |  " + abr[1] + "  |       |  " + abr[2] + "  |");
        System.out.println("              |       |-------|       |-------|       |");
        System.out.println("              +-------+       +-------+       +-------+");
        System.out.println("                |   |           |   |           |   |");
        System.out.println("                |   |           |   |           |   |");
        System.out.println("                |   |           |   |           |   |");
        System.out.println("              +-------+       +-------+       +-------+");
        System.out.println("              |  " + weaps.get(3) + "  |-------|  " + weaps.get(4) + "  |-------|  " + weaps.get(5) + "  |");
        System.out.println("              |  " + abr[3] + "  |       |  " + abr[4] + "  |       |  " + abr[5] + "  |");
        System.out.println("              |       |-------|       |-------|       |");
        System.out.println("              +-------+       +-------+       +-------+");
        System.out.println("                |   |           |   |           |   |  ");
        System.out.println("                |   |           |   |           |   |  ");
        System.out.println("                |   |           |   |           |   |  ");
        System.out.println("              +-------+       +-------+       +-------+");
        System.out.println("              |  " + weaps.get(6) + "  |-------|  " + weaps.get(7) + "  |-------|  " + weaps.get(8) + "  |");
        System.out.println("              |  " + abr[6] + "  |       |  " + abr[7] + "  |       |  " + abr[8] + "  |");
        System.out.println("              |       |-------|       |-------|       |");
        System.out.println("              +-------+       +-------+       +-------+");
    }

    void contructBoardWithPlayers(ArrayList<String> pLocations, ArrayList<String> pNames) {
		formatLocs(pLocations, pNames);
        System.out.println("              +----------+       +----------+       +----------+");
        System.out.println("              |   " + weaps.get(0) + "    |-------|   " + weaps.get(1) + "    |-------|   " + weaps.get(2) + "    |");
        System.out.println("              |   " + abr[0] +   "    |       |   " + abr[1] +   "    |       |   " + abr[2] +   "    |");
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
        System.out.println("              |   " + weaps.get(3) + "    |-------|   " + weaps.get(4) + "    |-------|   " + weaps.get(5) + "    |");
        System.out.println("              |   " + abr[3]   + "    |       |   " + abr[4] +   "    |       |   " + abr[5] +   "    |"); 
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
        System.out.println("              |   " + weaps.get(6) + "    |-------|   " + weaps.get(7) + "    |-------|   " + weaps.get(8) + "    |");
        System.out.println("              |   " + abr[6] +   "    |       |   " + abr[7] +   "    |       |   " + abr[8] +   "    |");
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
    void popOut(ArrayList<String> pLocations, ArrayList<String> pNames) {
        formatLocs(pLocations, pNames);
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "             +-------------+       +-------------+       +-------------+\n              |                           |----|                            |----|                           |\n              |        " + abr[0] +   "          |        |          " + abr[1] +   "            |        |        " + abr[2] +     "       |\n              |          " + weapsPop.get(0) +   "           |        |           " + weapsPop.get(1) +   "           |        |           " + weapsPop.get(2) +     "          |\n              |      " + printPlayerUpdated(0) +   "      |        |       " + printPlayerUpdated(1) +   "      |        |       " + printPlayerUpdated(2) +     "     |\n              |                           |----|                            |----|                           |\n             +-------------+       +-------------+       +-------------+\n                     |            |                        |            |                        |            |\n                     |            |                        |            |                        |            |\n                     |            |                        |            |                        |            |\n             +-------------+       +-------------+       +-------------+\n              |                           |----|                            |----|                           |\n              |       " + abr[3] +   "         |        |    " + abr[4] +   "   |        |    " + abr[5] +     "   |\n              |          " + weapsPop.get(3) +   "           |        |           " + weapsPop.get(4) +   "           |        |           " + weapsPop.get(5) +     "           |\n              |      " + printPlayerUpdated(3) +     "      |        |       " + printPlayerUpdated(4) +     "      |        |       " + printPlayerUpdated(5) +     "     |\n              |                           |----|                            |----|                           |\n             +-------------+       +-------------+       +-------------+\n                     |            |                        |            |                        |            |\n                     |            |                        |            |                        |            |\n                     |            |                        |            |                        |            |\n             +-------------+       +-------------+       +-------------+\n              |                           |----|                            |----|                           |\n              |  " + abr[6] +   "    |        |      " + abr[7] +   "        |        |        " + abr[8] +     "        |\n              |          " + weapsPop.get(6) +   "           |        |           " + weapsPop.get(7) +   "           |        |           " + weapsPop.get(8) +     "           |\n              |      " + printPlayerUpdated(6) +     "      |        |       " + printPlayerUpdated(7) +     "      |        |       " + printPlayerUpdated(8) +     "     |\n              |                           |----|                            |----|                           |\n             +-------------+       +-------------+       +-------------+\n"
        ,"Current GameBoard", JOptionPane.PLAIN_MESSAGE);
  
        frame.setSize(350,350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
    }

    void popOutWithStartAreas() {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "                                                                                                            +-------------+        \n                                                                                                             |    Miss Scarlet    |        \n                                                                                                             |      Start Area      |        \n                                                                                                             |                           |        \n                                                                                                            +-------------+        \n                                                                                                                       |        |        \n                                                                                                                       |        |        \n                                                    +-------------+       +-------------+       +-------------+\n                                                     |                           |----|                            |----|                           |\n                                                     |        " + abr[0] +   "          |        |          " + abr[1] +   "            |        |        " + abr[2] +     "       |\n                                                     |                           |----|                            |----|                           |\n              +-------------+       +-------------+       +-------------+       +-------------+       +-------------+\n               |          Plum         |--------|            |                        |            |                         |            |-------|        Mustard      |\n               |      Start Area      |                |            |                        |            |                         |            |              |      Start Area     |\n               |                           |--------|            |                        |            |                         |            |-------|                           |\n              +-------------+       +-------------+       +-------------+       +-------------+       +-------------+\n                                                     |                           |----|                            |----|                           |\n                                                     |        " + abr[3] +   "        |        |    " + abr[4] +   "    |        |   " + abr[5] +     "   |\n                                                     |                           |----|                            |----|                           |\n              +-------------+       +-------------+       +-------------+       +-------------+\n               |       Peacock       |--------|            |                        |            |                         |            |\n               |      Start Area      |                |            |                        |            |                         |            |\n               |                           |--------|            |                        |            |                         |            |\n              +-------------+       +-------------+       +-------------+       +-------------+\n                                                     |                           |----|                            |----|                           |\n                                                     |   " + abr[6] +   "   |        |       " + abr[7] +   "       |        |        " + abr[8] +     "        |\n                                                     |                           |----|                            |----|                           |\n                                                    +-------------+       +-------------+       +-------------+\n                                                                                 |        |                            |        |        \n                                                                                 |        |                            |        |        \n                                                                       +-------------+        +-------------+        \n                                                                       |         Green          |         |          White         |        \n                                                                       |       Start Area      |         |      Start Area      |        \n                                                                       |                            |         |                            |        \n                                                                       +-------------+        +-------------+        \n"
        ,"GameBoard Start", JOptionPane.PLAIN_MESSAGE);
  
        frame.setSize(350,350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
    }




}