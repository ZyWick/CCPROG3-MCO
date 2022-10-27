public class Tile {
    private FarmSeeds seeds = null;    
    private boolean rock = false;
    private boolean plowed = false;
    private coordinates coords;

    public Tile(coordinates coords) {
        this.coords = coords;
    }

    public Tile(boolean rock, coordinates coords) {
        this.rock = rock;
        this.coords = coords;
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

    public boolean getRock() {
        return this.rock;
    }

    public void setRock(boolean rock) {
        this.rock = rock;
    }

    public boolean isPlowed() {
        return this.plowed;
    }

    public boolean getPlowed() {
        return this.plowed;
    }

    public void setPlowed(boolean plowed) {
        this.plowed = plowed;
    }

    public coordinates getCoords() {
        return this.coords;
    }
}
