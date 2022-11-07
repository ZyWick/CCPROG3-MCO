import java.util.concurrent.ThreadLocalRandom;
import static java.lang.Math.min;

/**
 * The type Tile.
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
    public void displayTileStatus() {
        System.out.println("\nAbout tile:");
        System.out.println("rock: " + this.rock);
        System.out.println("plowed: " + this.plowed);
        if (this.seeds != null) {
            System.out.println("crop: " + this.seeds.getName());
            System.out.println("Times Watered: " + this.waterTimes);
            System.out.println("Times Fertilized: " + this.fertilizerTimes);
            System.out.println("Age: " + this.age +" days");
        } else
            System.out.println("...no crop planted");
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
     * @return true if the tile can be plowed, otherwise false
     */
    public boolean canPlow() {
        return !this.isPlowed() && !this.isRock();
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
        if(canPlow()) {
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
        }
    }

    /**
     * Fertilizes the tile if the tile can be fertilized
     */
    public void addFertilizerTimes() {
        if(this.canWaterOrFertilize()) {
            this.fertilizerTimes += 1;
            System.out.println("\n...success, total Times Fertilized: " + this.fertilizerTimes);
        }
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
     * @return the amount of products
     */
    public int computeProductsProduced() {
        return ThreadLocalRandom.current().nextInt(seeds.getMinProductsProduced(), seeds.getMaxProductsProduced() + 1);
    }

    /**
     * Compute the amount of times the tile was watered, with respect to the maximum number of times that it can be watered for extra earnings
     *
     * @param typeWaterBonus watering limit increase given by the player's farmer type
     * @return the int
     */
    public int computeWaterTimesCapped(int typeWaterBonus) {
        return min(this.waterTimes, seeds.getWaterLimit() + typeWaterBonus);
    }

    /**
     * Compute the amount of times the tile was fertilized, with respect to the maximum number of times that it can be fertilized for extra earnings
     *
     * @param typeFertilizerBonus fertilizing limit increase given by the player's farmer type
     * @return the int
     */
    public int computeFertilizerTimesCapped(int typeFertilizerBonus) {
        return min(this.fertilizerTimes, seeds.getFertilizerLimit() + typeFertilizerBonus);
    }

    /**
     * Sets the seeds.
     *
     * @param seeds the seed to capped
     */
    public void setSeeds(FarmSeeds seeds) {
        if(this.canPlant()) {
            this.seeds = seeds;

            waterTimes = 0;
            fertilizerTimes = 0;
            age = 0;
            System.out.println("..." + seeds.getName() +" successfully planted");
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
}
