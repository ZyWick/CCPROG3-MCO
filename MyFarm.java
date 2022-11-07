import java.util.ArrayList;

/**
 * The type My farm.
 */
public class MyFarm {
    private ArrayList<Tile> lot = new ArrayList<Tile>();
    private FarmSystem game = new FarmSystem();

    /**
     * Creates a new MyFarm
     */
    public MyFarm() {
        int x;

        for(x = 0; x <1; x++)
                //if indicated
                lot.add(new Tile(false));
                //lot.add(new Tile(true));
            
    }

    /**
     * Checks the state of a specific tile and uses a character to represent it
     * @param tileIndex the index of the tile
     * @return a character representing the tile's state
     */
    private char whatToPrint(int tileIndex) {
        Tile TheTile = lot.get(tileIndex);
        if (TheTile.isPlowed()) {
            if (TheTile.getSeeds() != null) {
                int age = TheTile.getAge();
                int harvestTime = TheTile.getSeeds().getHarvestTime();
    
                if (age <= 1)
                    return ',';
                if (age <= harvestTime/3)
                    return 's';
                if (age < harvestTime)
                    return 'S'; 
                if (age == harvestTime)
                    return '$'; 
                if (TheTile.isWithered() != 0 || age > harvestTime)
                    return 'X';        
            } else
                return '#';
        } else if (TheTile.isRock()) 
            return '*';
      
        return '=';
    }

    /**
     * Displays the farm
     */
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

    /**
     * Displays the tools that can and cannot be used on a specific tile
     *
     * @param tileIndex   the index of the tile
     * @param objectCoins the number of ObjectCoins the player has
     */
    public void displayTools (int tileIndex, double objectCoins) {
        for (FarmTools tool : game.getTools()) {
            int toolIndex = game.getTools().indexOf(tool);

            if(canUseTool(tileIndex, toolIndex, objectCoins) == 0)
                System.out.print("| / | ");
            else
                System.out.print("| x | ");

            System.out.println(toolIndex + " - " + tool.getName());
        }
    }

    /**
     * Displays the seeds that can and cannot be planted by the player
     *
     * @param objectCoins the number of ObjectCoins the player has
     * @param farmerSeedCostReduction discount on seed cost given by the player's farmer type
     */
    public void displaySeeds (double objectCoins, int farmerSeedCostReduction) {
        for (FarmSeeds seed : game.getSeeds()) {
            if(canAffordSeed(objectCoins, seed.getSeedCost(), farmerSeedCostReduction))
                System.out.print("| / | ");
            else
                System.out.print("| x | ");

            System.out.println(game.getSeeds().indexOf(seed) + " - " + seed.getName());
        }
    }

    /**
     * Display tile status.
     *
     * @param tileIndex the index of the tile
     */
    public void displayTileStatus(int tileIndex) {
        lot.get(tileIndex).displayTileStatus();
   }

    private int canUseTool(int tileIndex, int toolIndex, double objectCoins) {
        Tile TheTile = lot.get(tileIndex);
        FarmTools selectedTool = game.getTools().get(toolIndex);
        int error = 0;

        if (objectCoins >= selectedTool.getUsageCost()) {
            if (selectedTool.getName().equals("Plow") && TheTile.canPlow() == false)
                if(TheTile.isRock())
                    error = 2;
                else
                    error = 1;
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

    private boolean canAffordSeed(double objectCoins, int seedCost, int farmerSeedReduction) {
        if (objectCoins >= seedCost + farmerSeedReduction)
            return true;

        return false;
    }

    /**
     * Checks if a tool can be used on the tile
     *
     * @param tileIndex   the index of the tile
     * @param toolIndex   the index of the tool
     * @param objectCoins the number of ObjectCoins the player has
     * @return true if the tool can be used, otherwise false
     */
    public boolean checkUseTool (int tileIndex, int toolIndex, double objectCoins) {
        boolean result = false;

        if (toolIndex >= 0 && toolIndex < game.getTools().size()) {
            int error = canUseTool(tileIndex, toolIndex, objectCoins);

            if (error == 0) {
                result = true;   
            } else 
                game.throwToolError(error);
        } else
            game.throwOutOfBoundsError();
        
        return result;
    }

    /**
     * Checks if a new seed can be planted on the tile
     *
     * @param tileIndex   the index of the tile
     * @return true if a new seed can be planted, otherwise false
     */
    public boolean checkPlantInTile (int tileIndex) {
        boolean result = true;

        if (lot.get(tileIndex).isPlowed() == false) {
            result = false;
            game.throwPlantError(1);
        }

        if (lot.get(tileIndex).getSeeds() != null) {
            result = false;
            game.throwPlantError(2);
        }

        return result;
    }

    /**
     * Checks if a specific seed can be planted on the tile
     *
     * @param tileIndex               the index of the tile
     * @param farmerSeedCostReduction discount on seed cost given by the player's farmer type
     * @param seedIndex               the index of the seed
     * @param objectCoins             the number of ObjectCoins the player has
     * @return true if the specific seed can be planted, otherwise false
     */
    public boolean checkPlantCrop (int tileIndex, int farmerSeedCostReduction, int seedIndex, double objectCoins) {
        boolean result = false;

        if (seedIndex >= 0 && seedIndex < game.getSeeds().size()) {

            if (canAffordSeed(objectCoins, game.getSeeds().get(seedIndex).getSeedCost(), farmerSeedCostReduction)) {
                result = true;
            } else
                game.throwInsufficientObjectCoins();
        } else
            game.throwOutOfBoundsError();

        return result;
    }

    /**
     * Check if the tile can be harvested
     *
     * @param tileIndex the index of the tile
     * @return true if the tile can be harvested, otherwise false
     */
    public boolean checkHarvest(int tileIndex) {
        boolean result = true;
        int error;
        Tile TheTile = lot.get(tileIndex);

        if (TheTile.getSeeds() == null) 
            error = 5;
        else if (TheTile.getSeeds() != null && TheTile.getAge() < TheTile.getSeeds().getHarvestTime())
            error = 4;
        else
            error = TheTile.isWithered();
        
        if (error != 0) {
            game.throwHarvestError(error);
            result = false;
        }

        return result;
    }

    /**
     * Plows a tile
     *
     * @param tileIndex the index of the tile
     * @return an array containing the cost and exp yield of the operation
     */
    public double[] plowTile(int tileIndex) {
        lot.get(tileIndex).plowTile();
        return game.getTools().get(0).getToolCostAndYield();
    }

    /**
     * Waters a tile
     *
     * @param tileIndex the index of the tile
     * @return an array containing the cost and exp yield of the operation
     */
    public double[] waterTile(int tileIndex) {
        lot.get(tileIndex).addWaterTimes();
        return game.getTools().get(1).getToolCostAndYield();
    }

    /**
     * Fertilizes a tile
     *
     * @param tileIndex the index of the tile
     * @return an array containing the cost and exp yield of the operation
     */
    public double[] fertilizeTile(int tileIndex) {
        lot.get(tileIndex).addFertilizerTimes();   
        return game.getTools().get(2).getToolCostAndYield();
    }

    /**
     * Removes a rock from a tile
     *
     * @param tileIndex the index of the tile
     * @return an array containing the cost and exp yield of the operation
     */
    public double[] removeRock(int tileIndex) {
        lot.get(tileIndex).removeRock();
        return game.getTools().get(3).getToolCostAndYield();
    }

    /**
     * Shovels a tile
     *
     * @param tileIndex the index of the tile
     * @return an array containing the cost and exp yield of the operation
     */
    public double[] useShovel (int tileIndex) {
        Tile TheTile = lot.get(tileIndex);
        
        if (TheTile.isPlowed() == false || TheTile.isRock() == true)
            System.out.println("\n...nothing happened");
        else
            System.out.println("\n...tile has been reset");
        
        TheTile.reset();
        return game.getTools().get(4).getToolCostAndYield();
    }

    /**
     * Plants a crop on a tile
     *
     * @param tileIndex the index of the tile
     * @param seedIndex the index of the seed
     * @return the cost of the operation
     */
    public int plantCrop(int tileIndex, int seedIndex) {
        lot.get(tileIndex).setSeeds(game.getSeeds().get(seedIndex));
        return game.getSeeds().get(seedIndex).getSeedCost();
   }

    /**
     * Harvests a crop from the tile
     *
     * @param tileIndex       the index of the tile
     * @param farmerTypeIndex the index of the player's farmer type
     * @return an array containing the ObjectCoin yield and exp yield of the operation
     */
    public double[] harvestCrop(int tileIndex, int farmerTypeIndex){
        Tile TheTile = lot.get(tileIndex);
        FarmSeeds TheSeed = TheTile.getSeeds();
        double harvestTotal, waterBonus, fertilizerBonus;      
        FarmerType TheType = game.getType().get(farmerTypeIndex);

        int productsProduced = TheTile.computeProductsProduced();
        int waterTimesCapped = TheTile.computeWaterTimesCapped(TheType.getWaterBonusIncrease());
        int fertilizerTimesCapped = TheTile.computeFertilizerTimesCapped(TheType.getFertilizerBonusIncrease());

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

    /**
     * Add a day to the plant growing process, and notifies if a plant has withered as a result
     */
    public void ageLot() {
    game.addDay();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    for (Tile TheTile : lot) 
        if(TheTile.getSeeds() != null) {
            TheTile.addDay();
            if (TheTile.isWithered() != 0)
                System.out.println("..." + TheTile.getSeeds().getName() + "crop has withered");
        }
    }

    /**
     * Checks if the game is over
     *
     * @param objectCoins             the number of ObjectCoins the player has
     * @param farmerSeedCostReduction discount on seed cost given by the player's farmer type
     * @return the boolean
     */
    public boolean endGame(double objectCoins, int farmerSeedCostReduction) {
        boolean eventA = true;
        boolean eventB = true;

        if (canAffordSeed(objectCoins, game.getSeeds().get(0).getSeedCost(), farmerSeedCostReduction) == false) {
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

    /**
     * Returns the FarmSystem that the farm has
     *
     * @return a FarmSystem
     */
    public FarmSystem getGame () {
        return this.game;
   }
}
