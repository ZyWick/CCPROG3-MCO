import java.util.Scanner;

public class FarmSimulator {
 
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        MyFarm farm = new MyFarm();
        FarmSystem game = farm.getGame();
        Player p1 = new Player(farm);
        int choice, x = 1;

        game.display(farm.getLot());
        while (x != 0) {

            p1.displayPlayerStats();
            game.displayGameMoves();
            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: game.display(farm.getLot()); 
                        break;
                case 2: p1.interactTile(sc);
                        break;
                case 3: p1.advanceDay(); 
                        break;
                case 4: break;
                case 5: p1.RegisterUp();
                default: break;
            }
        }
        sc.close();
    }
}
