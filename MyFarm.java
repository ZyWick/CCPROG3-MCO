import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.min;

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
                if (day < harvestTime)
                    return 'S'; 
                if (day == harvestTime)
                    return '$'; 
                if (TheTile.isWithered() != 0 || day > harvestTime)
                    return 'X';        
            } else
                return '#';
        } else if (TheTile.isRock()) 
            return '*';
      
        return '=';
    }

    public void display () {
        int x, y, row = 1, column = 1, tileIndex = 0;
        System.out.println();

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

    public void displayTools (int tileIndex, int objectCoins) {
        for (FarmTools tool : game.getTools()) {
            if(canUseTool(tool, tileIndex, objectCoins) == 0)
                System.out.print("| / | ");
            else
                System.out.print("| x | ");

            System.out.println(game.getTools().indexOf(tool) + " - " + tool.getName());
        }
    }

    public void displaySeeds (int PlayerObjectCoins, FarmerType farmerType) {
        for (FarmSeeds seed : game.getSeeds()) {
            if(canAffordSeed(seed, PlayerObjectCoins, farmerType))
                System.out.print("| / | ");
            else
                System.out.print("| x | ");

            System.out.println(game.getSeeds().indexOf(seed) + " - " + seed.getName());
        }
    }

    public void displayTileStatus(int tileIndex) {
        lot.get(tileIndex).displayTileStatus();
   }

    public int canUseTool(FarmTools selectedTool, int tileIndex, int objectCoins) {
        Tile TheTile = lot.get(tileIndex);
        int error = 0;

        if (objectCoins >= selectedTool.getUsageCost()) {
            if (selectedTool.getName().equals("Plow"))
                error = TheTile.canPlow();
            else if (selectedTool.getName().equals("Watering Can") && TheTile.canWaterOrFertilize() == false)
                error = 3;
            else if (selectedTool.getName().equals("Fertilizer") && TheTile.canWaterOrFertilize() == false) 
                error = 4;
            else if (selectedTool.getName().equals("Pickaxe") && TheTile.isRock() == false)
                error = 5;
            else if (selectedTool.getName().equals("Shovel"))
                error = 0;
        } else
            error = 6;      
    
        return error;
    }

    public int canPlantSeed (int tileIndex) {
        if (lot.get(tileIndex).isPlowed() == false) 
            return 2;
        if (lot.get(tileIndex).getSeeds() != null)
            return 3;

        return 1;
    }

    public boolean canAffordSeed(FarmSeeds seed, int PlayerObjectCoins, FarmerType farmerType) {
        if (PlayerObjectCoins >= seed.getSeedCost() + farmerType.getSeedCostReduction())
            return true;

        return false;
    }

    public int canHarvest(int tileIndex) {
        int result;
        Tile TheTile = lot.get(tileIndex);

        if (TheTile.getSeeds() == null)
            result = 5;
        else if (TheTile.getSeeds() != null && TheTile.getDay() < TheTile.getSeeds().getHarvestTime())
            result = 4;
        else
            result = TheTile.isWithered();
        
        return result;
    }


    public void plowTile(int tileIndex) {
        lot.get(tileIndex).plowTile();
    }

    public void waterTile(int tileIndex) {
        lot.get(tileIndex).addWaterTimes();
    }

    public void fertilizeTile(int tileIndex) {
        lot.get(tileIndex).addFertilizerTimes();   
    }

    public void removeRock(int tileIndex) {
        lot.get(tileIndex).removeRock();
    }

    public void useShovel (int tileIndex) {
        lot.get(tileIndex).reset();
    }

    public void plantCrop(int tileIndex, int seedIndex) {
        lot.get(tileIndex).setSeeds(game.getSeeds().get(seedIndex));
   } 

   public void ageLot() {    
    game.addDay();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    for (Tile TheTile : lot) 
        if(TheTile.getSeeds() != null) {
            TheTile.addDay();
            if (TheTile.isWithered() != 0)
                System.out.println("..." + TheTile.getSeeds().getName() + "crop has withered");
        }
    }

    public int getProductsProduced(int tileIndex) {
        int productsProduced;
        FarmSeeds seeds = lot.get(tileIndex).getSeeds();
        
        productsProduced = ThreadLocalRandom.current().nextInt(seeds.getMinProductsProduced(), seeds.getMaxProductsProduced() + 1);

        return productsProduced;
    }

   public double[] harvestCrop(int tileIndex, FarmerType farmerType){
        Tile TheTile = lot.get(tileIndex);
        FarmSeeds TheSeed = TheTile.getSeeds();
        int productsProduced = getProductsProduced(tileIndex);
        double harvestTotal, waterBonus, fertilizerBonus;      

        int waterTimesCapped = min(TheTile.getWaterTimes(),
                                   TheSeed.getWaterLimit() + farmerType.getWaterBonusIncrease());

        int fertilizerTimesCapped = min(TheTile.getFertilizerTimes(),
                                        TheSeed.getFertilizerLimit() + farmerType.getFertilizerBonusIncrease());

        harvestTotal = productsProduced * (TheSeed.getSellingPrice() + farmerType.getBonusEarning());
        waterBonus = harvestTotal * 0.2 * (waterTimesCapped - 1);
        fertilizerBonus = harvestTotal * 0.5 * fertilizerTimesCapped;
        harvestTotal = harvestTotal + waterBonus + fertilizerBonus;

        TheTile.reset();
        double[] yield = new double[2];
        yield[0] = harvestTotal;
        yield[1] = TheSeed.getExpYield();
        
        System.out.println("\n" + TheSeed.getName() + " products produced: " + productsProduced);
        System.out.println("ObjectCoins gained: " + harvestTotal);

        return yield;
   }

   public boolean endGame(int objectCoins, FarmerType farmerType) {
        boolean eventA = true;
        boolean eventB = true;

        if (canAffordSeed(game.getSeeds().get(0), objectCoins, farmerType)) {
            for (Tile TheTile : lot) {
                if (TheTile.getSeeds() != null) {
                    eventA = false;
                }
            }
        } else
            eventA = false;

        for (Tile TheTile : lot) {
            if (TheTile.isWithered() == 0)
                eventB = false;
        }

        if (eventA) 
            System.out.println("...you don't have enough objectCoins to continue");
        if (eventB) 
            System.out.println("...all tiles have a withered crop");

        return (eventA || eventB);
   }

    public FarmSystem getGame() {
        return this.game;
    }
    
}
