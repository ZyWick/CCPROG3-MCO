public class TileState {
    public static final int ROCK = 0;
    public static final int UNPLOWED = 1;
    public static final int PLOWED = 2;
    public static final int PLANTED = 3;
    public static final int READY = 4;
    public static final int WITHERED = 5;

    private String seedName;
    private int state;

    public TileState(String seedName, int state) {
        this.seedName = seedName;
        this.state = state;
    }

    public String getSeedName() {
        return seedName;
    }

    public int getState() {
        return state;
    }
}
