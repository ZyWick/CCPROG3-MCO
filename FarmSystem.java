import java.util.ArrayList;

/**
 * FarmSystem contains the available items and helper methods
 */
public class FarmSystem {
    // private ArrayList<FarmTools> tools = new ArrayList<FarmTools>();
    private ArrayList<FarmSeeds> seeds = new ArrayList<FarmSeeds>();
    private ArrayList<FarmerType> type = new ArrayList<FarmerType>();
    private int day = 1;

    /**
     * Creates a new farm system
     */
    public FarmSystem () {
        seeds.add(new FarmSeeds("Turnip", "Root Crop", 2, 1, 2, 0, 1, 1, 2, 5, 6, 5));
        seeds.add(new FarmSeeds("Carrot", "Root Crop", 3, 1, 2, 0, 1, 1, 2, 10, 9, 7.5));
        seeds.add(new FarmSeeds("Potato", "Root Crop", 5, 3, 4, 1, 2, 1, 10, 20, 3, 12.5));
        seeds.add(new FarmSeeds("Rose", "Flower", 1, 1, 2, 0, 1, 1, 1, 5, 5, 2.5));
        seeds.add(new FarmSeeds("Tulips", "Flower", 2, 2, 3, 0, 1, 1, 1, 10, 9, 5));
        seeds.add(new FarmSeeds("Sunflower", "Flower", 3, 2, 3, 1, 2, 1, 1, 20, 19, 7.5));
        seeds.add(new FarmSeeds("Mango", "Fruit Tree", 10, 7, 7, 4, 4, 5, 15, 100, 8, 25));
        seeds.add(new FarmSeeds("Apple", "Fruit Tree", 10, 7, 7, 5, 5, 10, 15, 200, 5, 25));
        type.add(new FarmerType("Farmer", 0, 0, 0, 0, 0, 0));
        type.add(new FarmerType("Registered Farmer", 5, 1, -1, 0, 0, 200));
        type.add(new FarmerType("Distinguished Farmer", 10, 2, -2, 1, 0, 300));
        type.add(new FarmerType("Legendary Farmer", 15, 4, -3, 2, 1, 400));
    }

    /**
     * Increase the current day by 1
     */
    public void addDay() {
        this.day += 1;
        System.out.print("\n...success, a day has passed");
    }

    /**
     * Prints a prompt with the list of available game actions
     */
    public void displayGameMoves () {

    }

    /**
     * Prints a prompt with the list of available tile actions
     */
    public void displayInteractionChoices () {

    }

    /**
     * Prints a lot status legend
     */
    public void displayLotLegend () {

    }

    /**
     * Prints an error indicating that the player does not have enough ObjectCoins
     */
    public String throwInsufficientObjectCoins() {
        return "Error: not enough objectCoins";
    }

    /**
     * Prints an error indicating that the player typed a number not among the choices
     */
    public String throwOutOfBoundsError() {
        return "Error: no such choice";
    }

    /**
     * Prints an error indicating that the tool cannot be used due to a specific reason
     *
     * @param error an error code identifying the specific error
     */
    public String throwToolError(int error) {
        switch (error) {
            case 1: return "Error: tile is already plowed";
            case 2: return "Error: can't plow tile with rock";
            case 3: case 4: return "Error: tile does not have a crop";
            case 5: return "Error: tile does not have a rock";
            case 6: return throwInsufficientObjectCoins();
        }
        return "Unknown error";
    }

    /**
     * Prints an error indicating that the tile cannot be planted on due to a specific reason
     *
     * @param error an error code identifying the specific error
     */
    public String throwPlantError(int error) {
        switch (error) {
            case 1: return "Error: tile is unplowed";
            case 2: return "Error: tile has a crop already";
            case 3: return throwInsufficientObjectCoins();
            case 4: return throwOutOfBoundsError();
            case 5: return "Error: fruit tree cannot be planted here";
        }
        return "Unknown Error";
    }

    /**
     * Prints an error indicating that the tile cannot be harvested due to a specific reason
     *
     * @param error an error code identifying the specific error
     */
    public String throwHarvestError(int error) {
        switch (error) {
            case 1: return "Error: crop is withered, it is past its harvest time";
            case 2: return "Error: crop is withered, it did not meet fertilizer needs";
            case 3: return "Error: crop is withered, it did not meet water needs";
            case 4: return "Error: crop has not met harvest time";
            case 5: return "Error: tile has no crop";
        }
        return "Unknown Error";
    }

    /**
     * Prints an error indicating that the player cannot register to higher type because they are already on the highest type
     */
    public String throwMaxFarmerTypeError() {
        return "Error: you. are. already. legendary.";
    }

    /**
     * Prints an error indicating that the player cannot register to higher type because they do not meet the level requirement for it
     */
    public String throwRegisterError() {
        return "Error: insufficient level";
    }

    // /**
    //  * Returns the list of tools that are available in the game
    //  *
    //  * @return the list of available tools
    //  */
    // public ArrayList<FarmTools> getTools() {
    //     return this.tools;
    // }

    /**
     * Returns the list of seeds that are available in the game
     *
     * @return the list of available seeds
     */
    public ArrayList<FarmSeeds> getSeeds() {
        return this.seeds;
    }

    /**
     * Returns the list of farmer types that are available in the game
     *
     * @return the list of available farmer types
     */
    public ArrayList<FarmerType> getType() {
        return this.type;
    }

    /**
     * Returns the next available farmer type. Returns null if there is no such farmer type
     *
     * @param TheType the current farmer type
     * @return the next farmer type, can be null
     */
    public FarmerType getNextFarmerType(FarmerType TheType) {
        int nextLevelIndex = type.indexOf(TheType) + 1;

        if(nextLevelIndex < type.size())
            return type.get(nextLevelIndex);
        else
            return null;
    }

    /**
     * Returns the current day
     *
     * @return the day
     */
    public int getDay() {
        return this.day;
    }

}
