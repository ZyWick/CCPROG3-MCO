import java.util.Scanner;

public class FarmSimulator {
 
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        MyFarm farm = new MyFarm();
        FarmSystem game = farm.getGame();
        Player p1 = new Player(farm);
        int choice;

        farm.display();
        while (true) {

            p1.displayPlayerStats();
            game.displayGameMoves();
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
                case 6: game.displayLotLegend(); break;
                default: break;
            }

            if (farm.endGame(p1.getObjectCoins(), p1.getType())) {
                System.out.print("\n| input 1 to play again: ");
                if(sc.nextInt() == 1) {

                } else
                    break;
            }
        }
        sc.close();
    }
}
