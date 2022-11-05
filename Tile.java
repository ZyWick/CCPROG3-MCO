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

    public boolean canWaterOrFertilize() {
        return (this.isPlowed() && this.seeds != null);
    }

    public int getWaterTimes() {
        return this.waterTimes;
    }

    public void addWaterTimes() {
        if(this.canWaterOrFertilize())
            this.waterTimes += 1;
    }

    public int getFertilizerTimes() {
        return this.fertilizerTimes;
    }

    // returns true if operation succeded else false
    public void addFertilizerTimes() {
        if(this.canWaterOrFertilize())
            this.fertilizerTimes += 1;
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

    public boolean canPlow() {
        return !this.isPlowed() && !this.isRock();
    }

    public void setPlowed(boolean plowed) {
        // do not plow if plowing is not allowed
        if(plowed && !this.canPlow())
            return;

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

    public void reset() {
        // reset all except for rock
        seeds = null;
        plowed = false;

        waterTimes = 0;
        fertilizerTimes = 0;
        day = 0;
    }

    public FarmSeeds getSeed() {
        return this.seeds;
    }

}
