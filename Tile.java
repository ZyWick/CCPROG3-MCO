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

    public void reset() {
        // reset all except for rock
        this.seeds = null;
        this.plowed = false;

        this.waterTimes = 0;
        this.fertilizerTimes = 0;
        this.day = 0;
    }

    public boolean canPlow() {
        return !this.isPlowed() && !this.isRock();
    }

    private boolean canPlant() {
        return this.isPlowed() && this.seeds == null;
    }

    public boolean canWaterOrFertilize() {
        return (this.isPlowed() && this.seeds != null);
    }
    
    public void addWaterTimes() {
        if(this.canWaterOrFertilize())
            this.waterTimes += 1;
    }

    public void addFertilizerTimes() {
        if(this.canWaterOrFertilize())
            this.fertilizerTimes += 1;
    }

    public void addDay() {
        this.day += 1;
    }

    public void setPlowed(boolean plowed) {
        // do not plow if plowing is not allowed
        if(plowed && !this.canPlow())
            return;

        this.plowed = plowed;
    }

    public void setSeeds(FarmSeeds seeds) {
        if(this.canPlant()) {
            this.seeds = seeds;

            waterTimes = 0;
            fertilizerTimes = 0;
            day = 0;
        }
    }

    public void removeRock() {
        this.rock = false;
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

    public boolean isRock() {
        return this.rock;
    }

    public boolean isPlowed() {
        return this.plowed;
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

    public int getDay() {
        return this.day;
    }

}
