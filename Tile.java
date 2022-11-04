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

    public void setRock(boolean rock) {
        this.rock = rock;
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

    public boolean isWithered() {
        if(this.seeds != null && this.day > this.seeds.getHarvestTime())
            return true;

        if(this.seeds != null && this.day == this.seeds.getHarvestTime() && this.getFertilizerTimes() < this.seeds.getFertilizerNeeds())
            return true;

        if(this.seeds != null && this.day == this.seeds.getHarvestTime() && this.getWaterTimes() < this.seeds.getWaterNeeds())
            return true;

        return false;
    }
    
    public boolean canHarvest() {
        
        if (this.day == seeds.getHarvestTime())
            return true;
        
        return false;
    }

}
