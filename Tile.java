import java.util.concurrent.ThreadLocalRandom;
import static java.lang.Math.min;

public class Tile {
    private FarmSeeds seeds = null;    
    private boolean rock = false;
    private boolean plowed = false;
    private int waterTimes = 0;
    private int fertilizerTimes = 0;
    private int age = 0;

    public Tile() {
    }

    public Tile(boolean rock) {
        this.rock = rock;
    }

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

    public void reset() {
        // reset all except for rock
        this.seeds = null;
        this.plowed = false;

        this.waterTimes = 0;
        this.fertilizerTimes = 0;
        this.age = 0;
    }

    public int canPlow() {
        if  (this.isPlowed() == true)
            return 1;
        if (this.isRock() == true)
            return 2;
        
        return 0;
    }

    private boolean canPlant() {
        return this.isPlowed() && this.seeds == null;
    }

    public boolean canWaterOrFertilize() {
        return (this.isPlowed() && this.seeds != null);
    }

    public void plowTile() {
        // do not plow if plowing is not allowed
        if(canPlow() == 0) {
            this.plowed = true;
            System.out.println("...tile successfully plowed");
        }
    }
    
    public void addWaterTimes() {
        if(this.canWaterOrFertilize()) {
            this.waterTimes += 1;
            System.out.println("Times Watered: " + this.waterTimes);
        }
    }

    public void addFertilizerTimes() {
        if(this.canWaterOrFertilize()) {
            this.fertilizerTimes += 1;
            System.out.println("Times Fertilized: " + this.fertilizerTimes);
        }
    }

    public void removeRock() {
        this.rock = false;
        System.out.println("...rock removed from tile");
    }

    public void addDay() {
        this.age += 1;
    }

    public int computeProductsProduced() {
        return ThreadLocalRandom.current().nextInt(seeds.getMinProductsProduced(), seeds.getMaxProductsProduced() + 1);
    }

    public int computeWaterTimesCapped(int typeWaterBonus) {
        return min(this.waterTimes, seeds.getWaterLimit() + typeWaterBonus);
    }

    public int computeFertilizerTimesCapped(int typeFertilizerBonus) {
        return min(this.fertilizerTimes, seeds.getFertilizerLimit() + typeFertilizerBonus);
    }

    public void setSeeds(FarmSeeds seeds) {
        if(this.canPlant()) {
            this.seeds = seeds;

            waterTimes = 0;
            fertilizerTimes = 0;
            age = 0;
            System.out.println("..." + seeds.getName() +" successfully planted");
        }
    }

    public boolean isRock() {
        return this.rock;
    }

    public boolean isPlowed() {
        return this.plowed;
    }

    public int isWithered() {
        if(this.seeds != null && this.age > this.seeds.getHarvestTime())
            return 1;

        if(this.seeds != null && this.age == this.seeds.getHarvestTime() && this.getFertilizerTimes() < this.seeds.getFertilizerNeeds())
            return 2;

        if(this.seeds != null && this.age == this.seeds.getHarvestTime() && this.getWaterTimes() < this.seeds.getWaterNeeds())
            return 3;

        return 0;
    }

    public FarmSeeds getSeeds() {
        return this.seeds;
    }

    public int getWaterTimes() {
        return this.waterTimes;
    }

    public int getFertilizerTimes() {
        return this.fertilizerTimes;
    }

    public int getAge() {
        return this.age;
    }
}
