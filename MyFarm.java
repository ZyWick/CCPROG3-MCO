import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class MyFarm {
    private ArrayList<Tile> lot;

    public MyFarm() {
        int x, y;

        for(x = 1; x <=5; x++)
            for(y = 1; y <= 10; y++) {
                //if indicated
                lot.add(new Tile(false));
                //lot.add(new Tile(true));
            }
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
