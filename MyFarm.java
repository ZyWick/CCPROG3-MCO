import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class MyFarm {
    private ArrayList<Tile> lot = new ArrayList<Tile>();
    private FarmSystem game = new FarmSystem();

    public MyFarm() {
        int x;

        for(x = 0; x <1; x++)
                //if indicated
                lot.add(new Tile(false));
                //lot.add(new Tile(true));
            
    }

    public char whatToPrint(int tileIndex) {
        Tile tile = lot.get(tileIndex);

        if (tile.isPlowed()) {
            if (tile.getSeeds() != null) {
                int day = tile.getDay();
                int harvestTime = tile.getSeeds().getHarvestTime();
    
                if (day <= 1)
                    return ',';
                if (day <= harvestTime/3)
                    return 's';
                if (day <= harvestTime/1.5)
                    return 'S'; 
                if (day == harvestTime)
                    return '$'; 
                if (tile.isWithered() || day > harvestTime)
                return 'X';        
            }
            return '#';
        } else if (tile.isRock()) {
            return '*';
        } else {
            return '=';
        }
    }

    public void display () {
        int x, y, row = 1, column = 1, tileIndex = 0;


        for(x = 0; x < row; x++) {
            for(y = 0; y < column; y++) 
                System.out.print("* - - - - - ");
            System.out.println('*');

            for(y = 0; y < column; y++)
                System.out.print("|           ");
            System.out.println('|');
                
            for(y = 0; y < column; y++) {
                System.out.print("|     ");
                System.out.print(whatToPrint(tileIndex));
                System.out.print("     ");
                tileIndex = tileIndex + 1;
            }
            System.out.println('|');

            for(y = 0; y < column; y++)
                System.out.print("|           ");
            System.out.println('|');
        }        
        for(y = 0; y < column; y++) 
            System.out.print("* - - - - - ");
        System.out.println('*');
    }
    
    public int getProductsProduced(FarmSeeds TheSeed) {
        int productsProduced;
        
        productsProduced = ThreadLocalRandom.current().nextInt(TheSeed.getMinProductsProduced(), TheSeed.getMaxProductsProduced() + 1);

        return productsProduced;
    }

    public void ageLot() {    
        game.addDay();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
        for (Tile tile : lot) 
            if(tile.getSeeds() != null) 
                tile.addDay();
    }

    public ArrayList<Tile> getLot() {
        return this.lot;
    }

    public FarmSystem getGame() {
        return this.game;
    }
    
}
