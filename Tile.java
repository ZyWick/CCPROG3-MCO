import static java.lang.Math.min;

/**
 * Tile stores information about a particular tile or plot of land.
 */
public class Tile {
    private FarmSeeds seeds = null;    
    private boolean rock = false;
    private boolean plowed = false;
    private int waterTimes = 0;
    private int fertilizerTimes = 0;
    private int age = 0;

    /**
     * Creates a new Tile without a rock
     */
    public Tile() {
    }

    /**
     * Creates a new Tile with or without a rock
     *
     * @param rock true if Tile should have a rock, otherwise false
     */
    public Tile(boolean rock) {
        this.rock = rock;
    }

    /**
     * Prints information about the Tile
     */
    public String displayTileStatus() {
        String status = "<html><pre>";
        status += "\nAbout tile:" + "\n";
        status += "rock: " + this.rock + "\n";
        status += "plowed: " + this.plowed + "\n";
        if (this.seeds != null) {
            status += "crop: " + this.seeds.getName() + "\n";
            status += "Times Watered: " + this.waterTimes + "\n";
            status +=  addWaterStatus() + "\n";
            status += "Times Fertilized: " + this.fertilizerTimes + "\n";
            status += addFertilizerStatus() + "\n";
            status += "Age: " + this.age +" days" + "\n";
            status += "Harvest Time: day " + this.seeds.getHarvestTime() + "\n" ;
        } else
            status += "...no crop planted" + "\n";

        status += "</pre></html>";
        return status;
    }

    /**
     * Reset the Tile's state into the default state (except for the presence/absence of a rock)
     */
    public void reset() {
        // reset all except for rock
        this.seeds = null;
        this.plowed = false;

        this.waterTimes = 0;
        this.fertilizerTimes = 0;
        this.age = 0;
    }

    /**
     * Checks if the tile can be plowed
     *
     * @return int if the tile can be plowed, otherwise false
     */
    public int canPlow() {
        int error = 0;
        if (this.isPlowed())
                error = 1;
        else if(this.isRock())
                error = 2;
        return error;
    }


    /**
     * Checks if the tile can be planted on
     *
     * @return true if the tile can be planted on, otherwise false
     */
    private boolean canPlant() {
        return this.isPlowed() && this.seeds == null;
    }

    /**
     * Checks if the tile can be watered or fertilized.
     *
     * @return true if the tile can be watered/fertilized, otherwise false
     */
    public boolean canWaterOrFertilize() {
        return (this.isPlowed() && this.seeds != null);
    }

    /**
     * Plows the tile if the tile can be plowed
     */
    public void plowTile() {
        // do not plow if plowing is not allowed
        if(canPlow() == 0) {
            this.plowed = true;
            System.out.println("\n...tile successfully plowed");
        }
    }

    /**
     * Waters the tile if the tile can be watered
     */
    public void addWaterTimes() {
        if(this.canWaterOrFertilize()) {
            this.waterTimes += 1;
            System.out.println("\n...success, total Times Watered: " + this.waterTimes);
            System.out.println(addWaterStatus());
        }
    }

    /**
     * Fertilizes the tile if the tile can be fertilized
     */
    public void addFertilizerTimes() {
        if(this.canWaterOrFertilize()) {
            this.fertilizerTimes += 1;
            System.out.println("\n...success, total Times Fertilized: " + this.fertilizerTimes);
            System.out.println(addFertilizerStatus());
        }
    }

    private String addWaterStatus() {
        String result = "";
        if (this.waterTimes >= seeds.getWaterNeeds()) {
            if (this.waterTimes == seeds.getWaterNeeds())
                result = "...crop water needs reached";
            if (this.waterTimes >= seeds.getWaterLimit())
                result = "...crop water bonus limit reached";
        } else
            result = "| water needs: " + seeds.getWaterNeeds();
        return result;
    }

    private String addFertilizerStatus() {
        String result = "";
        if (this.fertilizerTimes >= seeds.getFertilizerNeeds()) {
            if (this.fertilizerTimes == seeds.getFertilizerNeeds())
                result = "...crop fertilizer needs reached";
            if (this.fertilizerTimes >= seeds.getFertilizerLimit())
                result = "...crop fertilizer bonus limit reached";
        } else
            result = "| fertilizer needs: " + seeds.getFertilizerNeeds();
        return result;
    }

    /**
     * Remove rock from tile
     */
    public void removeRock() {
        this.rock = false;
        System.out.println("\n...rock removed from tile");
    }

    /**
     * Add a day to the plant growing process
     */
    public void addDay() {
        this.age += 1;
    }

    /**
     * Computes the amount of products produced by tile
     *
     * @return the amount of products the crop produced
     */
    public int computeProductsProduced() {
        return seeds.computeProductsProduced();
    }

    /**
     * Compute the amount of times the tile was watered, with respect to the maximum number of times that it can be watered for extra earnings
     *
     * @param typeWaterBonus watering limit increase given by the player's farmer type
     * @return the result
     */
    public int computeWaterTimesCapped(int typeWaterBonus) {
        return min(this.waterTimes, seeds.getWaterLimit() + typeWaterBonus);
    }

    /**
     * Compute the amount of times the tile was fertilized, with respect to the maximum number of times that it can be fertilized for extra earnings
     *
     * @param typeFertilizerBonus fertilizing limit increase given by the player's farmer type
     * @return the result
     */
    public int computeFertilizerTimesCapped(int typeFertilizerBonus) {
        return min(this.fertilizerTimes, seeds.getFertilizerLimit() + typeFertilizerBonus);
    }

    /**
     * Sets the seeds.
     *
     * @param seeds the seed to plant
     */
    public void setSeeds(FarmSeeds seeds) {
        if(this.canPlant()) {
            this.seeds = seeds;

            waterTimes = 0;
            fertilizerTimes = 0;
            age = 0;
            System.out.println("\n..." + seeds.getName() +" successfully planted");
        }
    }

    /**
     * Checks if the tile is a rock
     *
     * @return true if the tile is a rock, otherwise false
     */
    public boolean isRock() {
        return this.rock;
    }

    /**
     * Checks if the tile is plowed
     *
     * @return true if the tile is plowed, otherwise false
     */
    public boolean isPlowed() {
        return this.plowed;
    }

    /**
     * Checks if the tile is planted
     *
     * @return true if the tile is planted, otherwise false
     */
    public boolean isPlanted() {
        return this.seeds != null;
    }

    /**
     * Checks if the tile is withered
     *
     * @return a positive number if the tile is withered, otherwise 0
     */
    public int isWithered() {
        if(this.seeds != null && this.age > this.seeds.getHarvestTime())
            return 1;

        if(this.seeds != null && this.age == this.seeds.getHarvestTime() && this.getFertilizerTimes() < this.seeds.getFertilizerNeeds())
            return 2;

        if(this.seeds != null && this.age == this.seeds.getHarvestTime() && this.getWaterTimes() < this.seeds.getWaterNeeds())
            return 3;

        return 0;
    }

    /**
     * Gets the seed currently planted in the tile
     *
     * @return the seed
     */
    public FarmSeeds getSeeds() {
        return this.seeds;
    }

    /**
     * Returns the number of times the tile was watered (without any limits)
     *
     * @return the number of times the tile was watered
     */
    public int getWaterTimes() {
        return this.waterTimes;
    }

    /**
     * Returns the number of times the tile was fertilized (without any limits)
     *
     * @return the number of times the tile was fertilized
     */
    public int getFertilizerTimes() {
        return this.fertilizerTimes;
    }

    /**
     * Returns the age of the tile in days
     *
     * @return the age of the tile
     */
    public int getAge() {
        return this.age;
    }

    /** gets the state of the tile
     * @return the TileState of the tile
     */
    public TileState getTileState() {
        String seedName = "";
        if(seeds != null)
            seedName = seeds.getName();

        int state = TileState.ROCK;
        if(!isRock())
            state = TileState.UNPLOWED;

        if(isPlowed())
            state = TileState.PLOWED;

        if(seeds != null)
            state = TileState.PLANTED;

        if(seeds != null && age == seeds.getHarvestTime())
            state = TileState.READY;

        if(isWithered() != 0)
            state = TileState.WITHERED;

        return new TileState(seedName, state);
    }
}
