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

    public char whatToPrint(Tile TheTile) {

        if (TheTile.isPlowed()) {
            if (TheTile.getSeeds() != null) {
                int day = TheTile.getDay();
                int harvestTime = TheTile.getSeeds().getHarvestTime();
    
                if (day <= 1)
                    return ',';
                if (day <= harvestTime/3)
                    return 's';
                if (day <= harvestTime/1.5)
                    return 'S'; 
                if (day == harvestTime)
                    return '$'; 
                if (TheTile.isWithered() || day > harvestTime)
                return 'X';        
            }
            return '#';
        } else if (TheTile.isRock()) {
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

   public ArrayList<Tile> getLot() {
        return this.lot;
   }

    public FarmSystem getGame() {
        return this.game;
    }
    
}
