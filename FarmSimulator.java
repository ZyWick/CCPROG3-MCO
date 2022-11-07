import java.util.Scanner;

/**
 * FarmSimulator is the driver class which contains the main method
 */
public class FarmSimulator {
 
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        MyFarm farm = new MyFarm();
        Player p1 = new Player(farm);
        int choice;

        farm.display();
        while (true) {

            p1.displayPlayerStats();
            choice = p1.whatCanIDo(sc);

            switch (choice) {
                case 1: p1.thisIsMyFarm(); 
                        break;
                case 2: p1.interactTile(sc);
                        break;
                case 3: p1.advanceDay(); 
                        break;
                case 4: p1.registerUp(); break;
                case 5: p1.whatIsThat(); break;
                default: break;
            }

            switch (p1.end(sc)) {
                case 0: break;
                case 1: // reinitialize everything to play again
                        farm = new MyFarm();
                        p1 = new Player(farm);
                        break;
                default: sc.close();
                         System.exit(0);
                         break;
            }
        }
    }
}
