import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class MyFarm {
    private ArrayList<Tile> lot = new ArrayList<Tile>();

    public MyFarm() {
        int x;

        for(x = 0; x <50; x++)
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
            }
            return '#';
        } else if (tile.isRock()) {
            return '*';
        } else if (tile.isWithered()) {
            return 'X';
        } else {
            return '=';
        }
    }

    public void display () {
        int x, y, tileIndex = 0;

        for(x = 0; x < 5; x++) {
            for(y = 0; y < 10; y++) 
                System.out.print("* - - - - - ");
            System.out.println('*');

            for(y = 0; y< 10; y++)
                System.out.print("|           ");
            System.out.println('|');
                
            for(y = 0; y < 10; y++) {
                System.out.print("|     ");
                System.out.print(whatToPrint(lot.get(tileIndex)));
                System.out.print("     ");
                tileIndex = tileIndex + 1;
            }
            System.out.println('|');

            for(y = 0; y< 10; y++)
                System.out.print("|           ");
            System.out.println('|');
        }        
        for(y = 0; y < 10; y++) 
            System.out.print("* - - - - - ");
        System.out.println('*');
    }
    
    public int getTileIndex(Scanner sc) {
        int x, y, tileIndex;

        System.out.print("input tile coordinates: ");
        x = sc.nextInt();
        y = sc.nextInt();
        tileIndex = (x - 1) * 10 + (y - 1) ;
        return tileIndex;
    }

    public void plowTile(int index) {
        lot.get(index).setPlowed(true);
    }

    public void waterTile(int index) {
        lot.get(index).addWaterTimes();
    }

    public void fertilizeTile(int index) {
        lot.get(index).addFertilizerTimes();   
    }

    public void removeRock(int index) {
        lot.get(index).setRock(false);  
    }

    public void removeWithered (int index) {
        Tile tile = lot.get(index);

        if (tile.isWithered()) {
            tile = new Tile();
        } else if (tile.isPlowed() && tile.getSeeds() != null) {
            tile = new Tile();
        } else if (tile.isPlowed() == false || tile.isRock()) {

        }

    }

    public void plantCrop(int index, FarmSeeds seed) {
        lot.get(index).setSeeds(seed);
   } 

    public int getProductsProduced(int tileIndex) {
        Tile TheTile = lot.get(tileIndex);
        FarmSeeds TheSeed = TheTile.getSeeds();
        int productsProduced;
        
        productsProduced = ThreadLocalRandom.current().nextInt(TheSeed.getMinProductsProduced(), TheSeed.getMaxProductsProduced() + 1);

        TheTile = new Tile();
        return productsProduced;
    }

    public void addDay() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
        for (Tile tile : lot)
            if(tile.getSeeds() != null)
                tile.addDay();
    }

    public ArrayList<Tile> getLot() {
        return this.lot;
    }
}
