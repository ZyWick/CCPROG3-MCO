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

    public void displayTileStatus() {
        System.out.println("\nAbout tile:");
        System.out.println("rock: " + this.rock);
        System.out.println("plowed: " + this.plowed);
        System.out.println("crop: " + this.seeds.getName());
        if (this.seeds != null) {
            System.out.println("Times Watered: " + this.waterTimes);
            System.out.println("Times Fertilized: " + this.fertilizerTimes);
            System.out.println("Age: " + this.day +" days");
        }
    }

    public void reset() {
        // reset all except for rock
        this.seeds = null;
        this.plowed = false;

        this.waterTimes = 0;
        this.fertilizerTimes = 0;
        this.day = 0;
        displayTileStatus();
    }

    public boolean canPlow() {
        return (this.isPlowed() == false && this.isRock() == false);
    }

    private boolean canPlant() {
        return this.isPlowed() && this.seeds == null;
    }

    public boolean canWaterOrFertilize() {
        return (this.isPlowed() && this.seeds != null);
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

    public void addDay() {
        this.day += 1;
    }

    public void plowTile() {
        // do not plow if plowing is not allowed
        if(canPlow()) {
            this.plowed = true;
            System.out.println("...tile successfully plowed");
        }
    }

    public void setSeeds(FarmSeeds seeds) {
        if(this.canPlant()) {
            this.seeds = seeds;

            waterTimes = 0;
            fertilizerTimes = 0;
            day = 0;
            System.out.println("..." + seeds.getName() +" successfully planted");
        }
    }

    public void removeRock() {
        this.rock = false;
        System.out.println("...rock removed from tile");
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
