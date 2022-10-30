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

    public void addFertilizerTimes() {
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

    public void setPlowed(boolean plowed) {
        this.plowed = plowed;
    }

    public boolean isWithered() {
        return this.withered;
    }

    public void setWithered(boolean withered) {
        this.withered = withered;
    }

}
