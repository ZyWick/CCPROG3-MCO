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

    public char whatToPrint(int tileIndex) {
        Tile TheTile = lot.get(tileIndex);
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

    public void displayTools (int tileIndex, int objectCoins) {
        for (FarmTools tool : game.getTools()) {
            int toolIndex = game.getTools().indexOf(tool);

            if(canUseTool(toolIndex, tileIndex, objectCoins) == 0)
                System.out.print("| / | ");
            else
                System.out.print("| x | ");

            System.out.println(toolIndex + " - " + tool.getName());
        }
    }

    public void displaySeeds (int PlayerObjectCoins, int farmerTypeSeedCostReduction) {
        for (FarmSeeds seed : game.getSeeds()) {
            if(canAffordSeed(PlayerObjectCoins, seed.getSeedCost(), farmerTypeSeedCostReduction))
                System.out.print("| / | ");
            else
                System.out.print("| x | ");

            System.out.println(game.getSeeds().indexOf(seed) + " - " + seed.getName());
        }
    }

    public void displayTileStatus(int tileIndex) {
        lot.get(tileIndex).displayTileStatus();
   }

    public int canUseTool(int toolIndex, int tileIndex, int objectCoins) {
        Tile TheTile = lot.get(tileIndex);
        FarmTools selectedTool = game.getTools().get(toolIndex);
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
            return 1;
        if (lot.get(tileIndex).getSeeds() != null)
            return 2;

        return 0;
    }

    public boolean canAffordSeed(int PlayerObjectCoins, int seedCost, int farmerTypeSeedReduction) {
        if (PlayerObjectCoins >= seedCost + farmerTypeSeedReduction)
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
        Tile TheTile = lot.get(tileIndex);
        if (TheTile.isPlowed() == false || TheTile.isRock() == true)
            System.out.println("\n...nothing happened");
        else
            System.out.println("\n...tile has been reset");
        
        TheTile.reset();        
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

   public double[] harvestCrop(int tileIndex, int farmerTypeIndex){
        Tile TheTile = lot.get(tileIndex);
        FarmSeeds TheSeed = TheTile.getSeeds();
        FarmerType TheType = game.getType().get(farmerTypeIndex);
        double harvestTotal, waterBonus, fertilizerBonus;      

        int productsProduced = TheTile.getProductsProduced();
        int waterTimesCapped = TheTile.getWaterTimesCapped(TheType.getWaterBonusIncrease());
        int fertilizerTimesCapped = TheTile.getFertilizerTimesCapped(TheType.getFertilizerBonusIncrease());

        harvestTotal = productsProduced * (TheSeed.getSellingPrice() + TheType.getBonusEarning());
        waterBonus = harvestTotal * 0.2 * (waterTimesCapped - 1);
        fertilizerBonus = harvestTotal * 0.5 * fertilizerTimesCapped;
        harvestTotal = harvestTotal + waterBonus + fertilizerBonus;

        if(TheSeed.getCropType().equals("Flower"))
            harvestTotal *= 1.1;

        TheTile.reset();
        double[] yield = new double[2];
        yield[0] = harvestTotal;
        yield[1] = TheSeed.getExpYield();
        
        System.out.println("\n...tile has been reset");
        System.out.println("| " + TheSeed.getName() + " products produced: " + productsProduced);

        return yield;
   }

   public boolean endGame(int objectCoins, int farmerTypeSeedReduction) {
        boolean eventA = true;
        boolean eventB = true;

        if (canAffordSeed(objectCoins, game.getSeeds().get(0).getSeedCost(), farmerTypeSeedReduction) == false) {
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
