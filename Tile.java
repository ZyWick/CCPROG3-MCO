import java.util.concurrent.ThreadLocalRandom;

public class Tile {
    private FarmSeeds seeds = null;    
    private boolean rock = false;
    private boolean plowed = false;
    private int waterTimes = 0;
    private int fertilizerTimes = 0;
    private int day = 0;

    public Tile() {
    }

    public Tile(boolean rock) {
        this.rock = rock;
    }

    public FarmSeeds getSeeds() {
        return this.seeds;
    }

    public void setSeeds(FarmSeeds seeds) {
        this.seeds = seeds;
    }

    public boolean isRock() {
        return this.rock;
    }

    public void removeRock() {
        this.rock = false;
    }

    public int getWaterTimes() {
        return this.waterTimes;
    }

    public void addWaterTimes() {
        this.waterTimes += 1;
    }

    public int getFertilizerTimes() {
        return this.fertilizerTimes;
    }

    // returns true if operation succeded else false
    public boolean addFertilizerTimes() {
        if(plowed && seeds != null) {
            this.fertilizerTimes += 1;
            return true;
        } else {
            return false;
        }
    }

    public int getDay() {
        return this.day;
    }

    public void addDay() {
        this.day += 1;
    }
    
    public boolean isPlowed() {
        return this.plowed;
    }

    public void setPlowed(boolean plowed) {
        this.plowed = plowed;
    }

    public int isWithered() {
        if(this.seeds != null && this.day > this.seeds.getHarvestTime())
            return 1;

        if(this.seeds != null && this.day == this.seeds.getHarvestTime() && this.getFertilizerTimes() < this.seeds.getFertilizerNeeds())
            return 2;

        if(this.seeds != null && this.day == this.seeds.getHarvestTime() && this.getWaterTimes() < this.seeds.getWaterNeeds())
            return 3;

        return 0;
    }
    
    public boolean canHarvest() {
        
        if (this.day == seeds.getHarvestTime())
            return true;
        
        return false;
    }

    public int getProductsProduced() {
        int productsProduced;
        
        productsProduced = ThreadLocalRandom.current().nextInt(seeds.getMinProductsProduced(), seeds.getMaxProductsProduced() + 1);

        return productsProduced;
    }

    public void removeWithered() {
        // reset all except for rock
        seeds = null;
        plowed = false;

        waterTimes = 0;
        fertilizerTimes = 0;
        day = 0;
    }

}
