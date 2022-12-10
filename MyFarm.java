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

   
    /** Creates a new MyFarm
     * 
     * @param farmSize the size of the farm
     */
    public MyFarm(Coordinates farmSize) {
        // load rock cfg from file
        // format '0' if no rock, '1' if rock
        String rockCfg = "0"; // failsafe 0
        try {
            Path rockCfgPath = Path.of("rocks.txt");
            rockCfg = Files.readString(rockCfgPath);
        } catch (IOException e) {
            System.out.println("Failed to read rocks.txt");
        }

        this.farmSize = farmSize;
        for(int y = 0; y < farmSize.getRow(); y++) {
            for(int x = 0; x < farmSize.getCol(); x++) {
                // add rocks if and only if the character at index mod string length is 1
                lot.put(new Coordinates(y, x), new Tile(
                        rockCfg.charAt((y * farmSize.getRow() + x) % rockCfg.length()) == '1'));
            }
        }
    }

    /** Display tile status.
     *
     * @param coordinates the coordinates of a tile
     * @return string of the status of a tile
     */
    public String displayTileStatus(Coordinates coordinates) {
        return lot.get(coordinates).displayTileStatus();
   }

    /** checks if a tile has a rock
     * @param coordinates the coordinates of a tile
     * @return true if the tile has a rock. Else, return false.
     */
    public boolean doesTileRock(Coordinates coordinates) {
        boolean result = false;
        if(lot.get(coordinates).isRock())
            result = true;
        return result;
    }

    /** checks if a tile can be plowed
     * @param coordinates the coordinates of a tile
     * @return int determining the error that occurred or none
     */
    public int doesTilePlow(Coordinates coordinates) {
        int error = lot.get(coordinates).canPlow();
        return error;
    }

    /** checks if a tile can be watered or fertilized
     * @param coordinates the coordinates of a tile
     * @return true if the tile can be watered or fertilized, otherwise, false
     */
    public boolean doesTileWorF(Coordinates coordinates) {
        boolean result = false;
        if(lot.get(coordinates).canWaterOrFertilize())
            result = true;
        return result;
    }

    /**
     * Checks if the seed can be purchased by the player
     * @param objectCoins          the number of ObjectCoins the player has
     * @param seedCost             cost of the seed in ObjectCoins
     * @param farmerSeedCostReduction  discount on seed cost given by the player's farmer type
     * @return true if the player can purchase the seed, otherwise, false
     */
    private boolean canAffordSeed(double objectCoins, int seedCost, int farmerSeedCostReduction) {
        if (objectCoins >= seedCost + farmerSeedCostReduction)
            return true;

        return false;
    }

    /** checks if the adjacent tiles of a tile is free
     * @param coordinates the coordinates of a tile to which the adjacent tiles will be relatively checked
     * @return true if the adjacent tiles of a tile is free, otherwise, false
     */
    private boolean checkFreeAdjacentTile(Coordinates coordinates) {
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

    /** checks if a seed can be planted in a tile
     * @param coordinates the coordinates of a tile
     * @param seed the seed to be planted
     * @return int determining the error that occurred or none
     */
    public int checkPlantInTile (Coordinates coordinates, FarmSeeds seed) {
        int result = 0;

        if (seed instanceof FruitTree && !checkFreeAdjacentTile(coordinates))
            result = 5;
        if (lot.get(coordinates).isPlowed() == false) 
            result = 1;
        if (lot.get(coordinates).getSeeds() != null) 
            result = 2;
        
        return result;
    }

    /**
     * Check if the crop on a tile can be harvested
     *
     * @param coordinates the coordinates of a tile
     * @return int determining the error that occurred or none
     */
    public int checkHarvest(Coordinates coordinates) {
        int error;
        Tile TheTile = lot.get(coordinates);

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
     * @param coordinates the coordinates of the tile
     */
    public void plowTile(Coordinates coordinates) {
        lot.get(coordinates).plowTile();
    }

    /**
     * Waters a tile
     *
     * @param coordinates the coordinates of the tile
     */
    public void waterTile(Coordinates coordinates) {
        lot.get(coordinates).addWaterTimes();
    }

    /**
     * Fertilizes a tile
     *
     * @param coordinates the index of the tile
     */
    public void fertilizeTile(Coordinates coordinates) {
        lot.get(coordinates).addFertilizerTimes();   
    }

    /**
     * Removes a rock from a tile
     *
     * @param coordinates the index of the tile
     */
    public void removeRock(Coordinates coordinates) {
        lot.get(coordinates).removeRock();
    }

    /**
     * Shovels a tile
     *
     * @param coordinates the index of the tile
     */
    public void useShovel (Coordinates coordinates) {
        Tile TheTile = lot.get(coordinates);
        
        if (TheTile.isPlowed() == false || TheTile.isRock() == true)
            System.out.println("\n...nothing happened");
        else
            System.out.println("\n...tile has been reset");
        
        TheTile.reset();
    }

    /**
     * Plants a crop on a tile
     *
     * @param coordinates the coordinates of a tile
     * @param seed the seed to be planted
     * @return amount of the cost of the seed or 0 if plant failed
     */
    public int plantCrop(Coordinates coordinates, FarmSeeds seed) {
        if(checkPlantInTile(coordinates, seed) == 0) {
            lot.get(coordinates).setSeeds(seed);
            return seed.getSeedCost();
        }

        // planting failure
        return 0;
   }

    /**
     * Harvests a crop from the tile
     *
     * @param coordinates the coordinates of a tile
     * @param farmerTypeIndex the index of the player's farmer type
     * @return an array containing the ObjectCoin yield, exp yield, and products produced of the operation
     */
    public double[] harvestCrop(Coordinates coordinates, int farmerTypeIndex){
        Tile TheTile = lot.get(coordinates);
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
            harvestTotal *= ((Flower) TheSeed).getPremiumFactor();

        TheTile.reset();
        double[] yield = new double[3];
        yield[0] = harvestTotal;
        yield[1] = TheSeed.getExpYield();
        yield[2] = productsProduced;
        
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
     * @param objectCoins             the amount of ObjectCoins the player has
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

    /** gets the state of a tile
     * @param coordinates the coordinates of a tile
     * @return the TileState of a tile
     */
    public TileState getTileState(Coordinates coordinates) {
        return lot.get(coordinates).getTileState();
    }
}
