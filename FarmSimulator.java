import java.util.Scanner;

public class FarmSimulator {
 
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        MyFarm farm = new MyFarm();
        Player p1 = new Player(farm, );
        int choice;

        farm.display();
        while (true) {

            p1.displayPlayerStats();
            farm.display(0);
            choice = sc.nextInt();

            switch (choice) {
                case 1: farm.display(); 
                        break;
                case 2: p1.interactTile(sc);
                        break;
                case 3: p1.advanceDay(); 
                        break;
                case 4: break;
                case 5: p1.RegisterUp(); break;
                case 6: farm.display(1);; break;
                default: break;
            }

            switch (p1.end(sc)) {
                case 0: break;
                case 1: //play again
                        break;
                default: sc.close();
                         System.exit(0);
                         break;
            }
        }
    }
}
