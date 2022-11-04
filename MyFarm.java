import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class MyFarm {
    private ArrayList<Tile> lot = new ArrayList<Tile>();

    public MyFarm() {
        int x;

        for(x = 0; x <1; x++)
                //if indicated
                lot.add(new Tile(false));
                //lot.add(new Tile(true));
            
    }

    public char whatToPrint(Tile tile) {
        if (tile.isPlowed()) {
            if (tile.getSeeds() != null) {
                int day = tile.getDay();
                int harvestTime = tile.getSeeds().getHarvestTime();

                if (day == 1)
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
                System.out.print(whatToPrint(lot.get(tileIndex)));
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
    
    public void plowTile(Tile TheTile) {
        TheTile.setPlowed(true);
    }

    public void waterTile(Tile TheTile) {
        TheTile.addWaterTimes();
    }

    public void fertilizeTile(Tile TheTile) {
        TheTile.addFertilizerTimes();   
    }

    public void removeRock(Tile TheTile) {
        TheTile.setRock(false);  
    }

    public void removeWithered (Tile TheTile) {

        if (TheTile.isWithered()) {
            TheTile = new Tile();
        } else if (TheTile.isPlowed() && TheTile.getSeeds() != null) {
            TheTile = new Tile();
        } else if (TheTile.isPlowed() == false || TheTile.isRock()) {

        }

    }

    public void plantCrop(Tile TheTile, FarmSeeds selectedSeed) {
        TheTile.setSeeds(selectedSeed);
   } 

    public int getProductsProduced(Tile TheTile) {
        FarmSeeds TheSeed = TheTile.getSeeds();
        int productsProduced;
        
        productsProduced = ThreadLocalRandom.current().nextInt(TheSeed.getMinProductsProduced(), TheSeed.getMaxProductsProduced() + 1);

        TheTile = new Tile();
        return productsProduced;
    }

    public void ageLot() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
        for (Tile tile : lot) 
            if(tile.getSeeds() != null) {
                tile.addDay();
                if (tile.getDay() > tile.getSeeds().getHarvestTime())
                    tile.setWithered(true);
            }
    }

    public ArrayList<Tile> getLot() {
        return this.lot;
    }

    
}
