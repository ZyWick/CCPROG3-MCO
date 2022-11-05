import java.util.ArrayList;

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
    
    public void ageLot() {    
        game.addDay();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
        for (Tile TheTile : lot) 
            if(TheTile.getSeeds() != null) 
                TheTile.addDay();
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
        TheTile.removeWithered();
    }

    public void plantCrop(Tile TheTile, int seedIndex) {
        TheTile.setSeeds(game.getSeeds().get(seedIndex));
   } 

   public int identifyHarvestError(Tile TheTile) {
        return TheTile.isWithered();
   }

   public ArrayList<Tile> getLot() {
        return this.lot;
   }

    public FarmSystem getGame() {
        return this.game;
    }
    
}
