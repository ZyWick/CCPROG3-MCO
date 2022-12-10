import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * MyFarm is the player's farm
 */
public class MyFarm {
    private HashMap<Coordinates, Tile> lot = new HashMap<>();
    private FarmSystem game = new FarmSystem();
    private Coordinates farmSize;

    /**
     * Creates a new MyFarm
     */
    public MyFarm() {
        // load rock cfg from file
        // format '0' if no rock, '1' if rock
        String rockCfg = "0"; // failsafe 0
        try {
            Path rockCfgPath = Path.of("rocks.txt");
            rockCfg = Files.readString(rockCfgPath);
        } catch (IOException e) {
            System.out.println("Failed to read rocks.txt");
        }

        farmSize = new Coordinates(5, 10);
        for(int y = 0; y < farmSize.getRow(); y++) {
            for(int x = 0; x < farmSize.getCol(); x++) {
                //if indicated
                System.out.println(new Coordinates(y, x));
                lot.put(new Coordinates(y, x), new Tile(rockCfg.charAt(y * farmSize.getRow() + x) == '1'));
                //lot.add(new Tile(true));
            }
        }

            
    }

    /**
     * Displays the farm
     */
    public void display () {
    }

    /**
     * Display tile status.
     *
     * @param tileIndex the index of the tile
     */
    public String displayTileStatus(Coordinates tileIndex) {
        return lot.get(tileIndex).displayTileStatus();
   }

    /**
     * Checks if the tool can be used by the player on the specified tile
     * @param tileIndex   the index of the tool
     * @param toolIndex   the index of the tile
     * @param objectCoins the number of ObjectCoins the player has
     * @return true if the player can use the tool on the tile, otherwise false
     */
    public int canUseTool(Coordinates tileIndex, String toolName, int usageCost, double objectCoins) {
        Tile TheTile = lot.get(tileIndex);
        int error = 0;

        if (objectCoins >= usageCost) {
            if (toolName.equals("Plow") && TheTile.canPlow() == false)
                if(TheTile.isRock())
                    error = 2;
                else
                    error = 1;
            else if (toolName.equals("Watering Can") && TheTile.canWaterOrFertilize() == false)
                error = 3;
            else if (toolName.equals("Fertilizer") && TheTile.canWaterOrFertilize() == false) 
                error = 4;
            else if (toolName.equals("Pickaxe") && TheTile.isRock() == false)
                error = 5;
            else if (toolName.equals("Shovel"))
                error = 0;
        } else
            error = 6;      
    
        return error;
    }

    /**
     * Checks if the seed can be purchased by the player
     * @param objectCoins          the number of ObjectCoins the player has
     * @param seedCost             cost of the seed in ObjectCoins
     * @param farmerSeedReduction  discount on seed cost given by the player's farmer type
     * @return true if the player can purchase the seed, otherwise false
     */
    private boolean canAffordSeed(double objectCoins, int seedCost, int farmerSeedReduction) {
        if (objectCoins >= seedCost + farmerSeedReduction)
            return true;

        return false;
    }

    /**
     * Checks if a new seed can be planted on the tile
     *
     * @param tileIndex   the index of the tile
     * @return true if a new seed can be planted, otherwise false
     */
    public int checkPlantInTile (Coordinates tileIndex) {
        int result = 0;

        if (lot.get(tileIndex).isPlowed() == false) 
            result = 1;
        if (lot.get(tileIndex).getSeeds() != null) 
            result = 2;
        
        return result;
    }

    /**
     * Check if the tile can be harvested
     *
     * @param tileIndex the index of the tile
     * @return true if the tile can be harvested, otherwise false
     */
    public int checkHarvest(Coordinates tileIndex) {
        int error;
        Tile TheTile = lot.get(tileIndex);

        if (TheTile.getSeeds() == null) 
            error = 5;
        else if (TheTile.getSeeds() != null && TheTile.getAge() < TheTile.getSeeds().getHarvestTime())
            error = 4;
        else
            error = TheTile.isWithered();

        return error;
    }

    /**
     * Plows a tile
     *
     * @param tileIndex the index of the tile
     * @return an array containing the cost and exp yield of the operation
     */
    public void plowTile(Coordinates tileIndex) {
        lot.get(tileIndex).plowTile();
    }

    /**
     * Waters a tile
     *
     * @param tileIndex the index of the tile
     * @return an array containing the cost and exp yield of the operation
     */
    public void waterTile(Coordinates tileIndex) {
        lot.get(tileIndex).addWaterTimes();
    }

    /**
     * Fertilizes a tile
     *
     * @param tileIndex the index of the tile
     * @return an array containing the cost and exp yield of the operation
     */
    public void fertilizeTile(Coordinates tileIndex) {
        lot.get(tileIndex).addFertilizerTimes();   
    }

    /**
     * Removes a rock from a tile
     *
     * @param tileIndex the index of the tile
     * @return an array containing the cost and exp yield of the operation
     */
    public void removeRock(Coordinates tileIndex) {
        lot.get(tileIndex).removeRock();
    }

    /**
     * Shovels a tile
     *
     * @param tileIndex the index of the tile
     * @return an array containing the cost and exp yield of the operation
     */
    public void useShovel (Coordinates tileIndex) {
        Tile TheTile = lot.get(tileIndex);
        
        if (TheTile.isPlowed() == false || TheTile.isRock() == true)
            System.out.println("\n...nothing happened");
        else
            System.out.println("\n...tile has been reset");
        
        TheTile.reset();
    }

    /**
     * Plants a crop on a tile
     *
     * @param tileIndex the index of the tile
     * @param seedIndex the index of the seed
     * @return the cost of the operation
     */
    public int plantCrop(Coordinates tileIndex, int seedIndex) {
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
    public double[] harvestCrop(Coordinates tileIndex, int farmerTypeIndex){
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

        if(TheSeed instanceof Flower)
            harvestTotal *= 1.1;   

        TheTile.reset();
        double[] yield = new double[2];
        yield[0] = harvestTotal;
        yield[1] = TheSeed.getExpYield();
        
        System.out.println("\n...crop harvested + tile has been reset");
        System.out.println("| " + TheSeed.getName() + " products produced: " + productsProduced);

        return yield;
   }

    /**
     * Add a day to the plant growing process, and notifies if a plant has withered as a result
     */
    public void ageLot() {
    game.addDay();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    for (Tile TheTile : lot.values())
        if(TheTile.getSeeds() != null) {
            TheTile.addDay();
            if (TheTile.isWithered() != 0)
                System.out.print("\n..." + TheTile.getSeeds().getName() + " has withered");
        }
    }

    /**
     * Checks if the game is over
     *
     * @param objectCoins             the number of ObjectCoins the player has
     * @param farmerSeedCostReduction discount on seed cost given by the player's farmer type
     * @return true if the game is over, otherwise false
     */
    public boolean endGame(double objectCoins, int farmerSeedCostReduction) {
        boolean eventA = true;
        boolean eventB = true;

        if (canAffordSeed(objectCoins, game.getSeeds().get(0).getSeedCost(), farmerSeedCostReduction) == false) {
            for (Tile TheTile : lot.values()) {
                if (TheTile.getSeeds() != null) {
                    eventA = false;
                }
            }
        } else
            eventA = false;

        for (Tile TheTile : lot.values()) {
            if (TheTile.isWithered() == 0)
                eventB = false;
        }

        if (eventA) 
            System.out.println("\n...you don't have enough objectCoins to continue");
        if (eventB) 
            System.out.println("\n...all tiles have a withered crop");

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

    public boolean checkFreeAdjacentTile(Coordinates coordinates) {
        int row = coordinates.getRow();
        int col = coordinates.getCol();
        for(int r = row - 1; r <= row + 1; r++) {
            for(int c = col - 1; c <= col + 1; c++) {
                System.out.println(new Coordinates(r, c));
                Tile checkTile = lot.get(new Coordinates(r, c));

                // if out of bounds or contains something, return false
                if(checkTile == null || checkTile.isRock() || checkTile.isPlanted()) {
                    return false;
                }
            }
        }
        return true;
    }
}
