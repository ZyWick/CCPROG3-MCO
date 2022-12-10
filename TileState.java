public class TileState {
    public static final int ROCK = 0;
    public static final int UNPLOWED = 1;
    public static final int PLOWED = 2;
    public static final int PLANTED = 3;
    public static final int READY = 4;
    public static final int WITHERED = 5;

    private String seedName;
    private int state;

    /**
     * Creates a read-only tile state
     * @param seedName name of the seed in the tile, if any
     * @param state state of the tile (can be ROCK, UNPLOWED, PLOWED, PLANTED, READY, WITHERED)
     */
    public TileState(String seedName, int state) {
        this.seedName = seedName;
        this.state = state;
    }

    /**
     * Gets the name of the seed, if there is one.
     * The seed name is only guaranteed to be valid if the state is PLANTED, READY, or WITHERED
     * @return the name of the seed
     */
    public String getSeedName() {
        return seedName;
    }

    /**
     * Get the state of the tile (can be ROCK, UNPLOWED, PLOWED, PLANTED, READY, WITHERED)
     * @return the state of the tile
     */
    public int getState() {
        return state;
    }
}
