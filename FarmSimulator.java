import java.util.Scanner;

public class FarmSimulator {
 
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        MyFarm farm = new MyFarm();
        Player p1 = new Player(farm.getGame().getType().get(0));
        FarmSystem game = farm.getGame();
        int choice, tileIndex, x = 1;
        Tile TheTile = null;
        farm.display();

        while (x != 0) {

            p1.displayPlayerStats(farm);
            game.displayGameMoves();
            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: farm.display(); 
                        break;
                case 2: tileIndex = game.getTileIndex(sc);
                        TheTile = farm.getLot().get(tileIndex);
                        game.displayInteractionChoices ();
                        choice = sc.nextInt();
                        switch (choice) {
                            case 1: choice = game.getToolChoice(sc, TheTile, p1.getObjectCoins());
                                    FarmTools selectedTool = game.getTools().get(choice);
                                    p1.useTool(farm, selectedTool, TheTile);
                                    break;
                            case 2: p1.plantCrop(farm, TheTile, sc);
                                    break;
                            case 3: p1.harvestCrop(farm, TheTile); 
                                    break;
                            default: break;
                        }
                        break;
                case 3: p1.advanceDay(farm); 
                        System.out.println("Day: " + game.getDay()); 
                        break;
                case 4: break;
                case 5: p1.RegisterUp(farm);
                default: break;
            }
        }
        sc.close();
    }
}
