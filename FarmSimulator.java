import java.util.Scanner;
import java.util.ArrayList;

public class FarmSimulator {
 
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        MyFarm farm = new MyFarm();
        Player p1 = new Player();
        int choice, x = 1, tileIndex;
        ArrayList<Integer> actions = new ArrayList<Integer>();
        farm.display();

        while (x != 0) {
            //p1.displayStats();
            System.out.println("What do you want to do?");
            System.out.println("1 - display farm");
            System.out.println("2 - Interact with tile");
            System.out.println("3 - advance day");
            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: farm.display(); break;
                case 2: tileIndex = farm.getTileIndex(sc);
                        actions = p1.getFarmSystem().displayAvailableTileActions(farm, tileIndex);
                        System.out.print("Choice: ");
                        choice = sc.nextInt();
                        switch (actions.get(choice - 1)) {
                            case 5: p1.plantCrop(farm, tileIndex, x); break;
                            default: p1.useTool(farm, actions.get(choice - 1), tileIndex);
                        }
                        break;
                case 3: p1.advanceDay(farm); break;
            }
        }
        sc.close();
    }
}
