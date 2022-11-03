public class Tile {
    private FarmSeeds seeds = null;    
    private boolean rock = false;
    private boolean plowed = false;
    private boolean withered = false;
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

    public boolean setPlowed(boolean plowed) {
        this.plowed = plowed;
    }

    public boolean isWithered() {
        return this.withered;
    }

    public void setWithered(boolean withered) {
        this.withered = withered;
    }

    //
    public boolean plow() {
        if(!plowed){
            plowed = true;
            return true;
        } else {
            return false;
        }
    }

    // always returns true (just to be consistent)
    public boolean shovel() {
        // resets fields (except rock and day) of object
        seeds = null;
        plowed = false;
        withered = false;

        waterTimes = 0;
        fertilizerTimes = 0;
        
        return true;
    }
}
