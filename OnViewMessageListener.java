import java.util.ArrayList;

public interface OnViewMessageListener {
    /**
     * Tells controller to attempt to plant a seed on the tile
     * @param coordinates coordinates of the tile
     * @param seedName name of the seed from FarmSeeds.getName()
     */
    public void onMessagePlant(Coordinates coordinates, String seedName);

    /**
     * Tells controller to attempt to use a tool on the tile
     * @param coordinates coordinates of the tile
     * @param toolName name of the seed from FarmTools.getName()
     */
    public void onMessageUseTool(Coordinates coordinates, String toolName);

    /**
     * Tells controller to attempt to harvest the tile
     * @param coordinates coordinates of the tile
     */
    public void onMessageHarvestCrop(Coordinates coordinates);

    /**
     * Tells controller to attempt to register the farmer
     */
    public void onMessageRegisterFarmer();

    /**
     * Asks controller for a list of seeds with bonuses applied
     * @return a list of seeds with bonuses applied
     */
    public ArrayList<FarmSeeds> requestFarmSeedsWithBonuses();

    /**
     * Asks controller for the status of a tile
     * @param coordinates coordinates of the tile
     * @return
     */
    public String requestTileStatus(Coordinates coordinates);

    /**
     * Tells controller to advance day
     */
    public void onMessageAdvanceDay();

    /**
     * Tells controller to force-update the contents of the view
     */
    public void requestViewUpdate();
}
