import java.util.ArrayList;

/**
 * FarmSystem contains the available items and helper methods
 */
public class FarmSystem {
    private ArrayList<FarmTools> tools = new ArrayList<FarmTools>();
    private ArrayList<FarmSeeds> seeds = new ArrayList<FarmSeeds>();
    private ArrayList<FarmerType> type = new ArrayList<FarmerType>();
    private int day = 1;

    /**
     * Creates a new farm system
     */
    public FarmSystem () {
        tools.add(new FarmTools("Plow", 0, 0.5));
        tools.add(new FarmTools("Watering Can", 0, 0.5));
        tools.add(new FarmTools("Fertilizer", 10, 4));
        tools.add(new FarmTools("Pickaxe", 50, 15));
        tools.add(new FarmTools("Shovel", 7, 2));
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
        System.out.println("\nWhat do you want to do?");
        System.out.println("| 1 - display farm");
        System.out.println("| 2 - Interact with tile");
        System.out.println("| 3 - advance day");
        System.out.println("| 4 - Register superior farmer type");
        System.out.println("| 5 - display farm legend");
        System.out.println("| ...input any number to return to main");
        System.out.print("Choice: ");
    }

    /**
     * Prints a prompt with the list of available tile actions
     */
    public void displayInteractionChoices () {
        System.out.println("\nWhat do you want to do?");
        System.out.println("| 1 - Use Tool");
        System.out.println("| 2 - Plant seed");
        System.out.println("| 3 - Harvest Crop");
        System.out.println("| 4 - display tile status");
        System.out.println("| ...input any number to return to main");
        System.out.print("Choice: ");
    }

    /**
     * Prints a lot status legend
     */
    public void displayLotLegend () {
        System.out.println("\nLegend:");
        System.out.println("| ( = ) unplowed tile ");
        System.out.println("| ( # ) plowed tile ");
        System.out.println("| ( * ) rock ");
        System.out.println("| ( , )( s )( S ) crop ");
        System.out.println("| ( $ ) Harvestable crop ");
        System.out.println("| ( X ) Withered crop ");
    }

    /**
     * Prints an error indicating that the player does not have enough ObjectCoins
     */
    public void throwInsufficientObjectCoins() {
        System.out.println("\n| Error: not enough objectCoins");
    }

    /**
     * Prints an error indicating that the player typed a number not among the choices
     */
    public void throwOutOfBoundsError() {
        System.out.println("\n| Error: no such choice");
    }

    /**
     * Prints an error indicating that the tool cannot be used due to a specific reason
     *
     * @param error an error code identifying the specific error
     */
    public void throwToolError(int error) {
        switch (error) {
            case 1: System.out.println("\n| Error: tile is already plowed"); break;
            case 2: System.out.println("\n| Error: can't plow tile with rock"); break;
            case 3: case 4: System.out.println("\n| Error: tile does not have a crop"); break;
            case 5: System.out.println("\n| Error: tile does not have a rock"); break;
            case 6: throwInsufficientObjectCoins();
        }
    }

    /**
     * Prints an error indicating that the tile cannot be planted on due to a specific reason
     *
     * @param error an error code identifying the specific error
     */
    public void throwPlantError(int error) {
        switch (error) {
            case 1: System.out.println("\n| Error: tile is unplowed"); break;
            case 2: System.out.println("\n| Error: tile has a crop already"); break;
        }
    }

    /**
     * Prints an error indicating that the tile cannot be harvested due to a specific reason
     *
     * @param error an error code identifying the specific error
     */
    public void throwHarvestError(int error) {
        switch (error) {
            case 1: System.out.println("\n| Error: crop is withered, it is past its harvest time"); break;
            case 2: System.out.println("\n| Error: crop is withered, it did not meet fertilizer needs"); break;
            case 3: System.out.println("\n| Error: crop is withered, it did not meet water needs"); break;
            case 4: System.out.println("\n| Error: crop has not met harvest time"); break;
            case 5: System.out.println("\n| Error: tile has no crop"); break;
        }
    }

    /**
     * Prints an error indicating that the player cannot register to higher type because they are already on the highest type
     */
    public void throwMaxFarmerTypeError() {
        System.out.println("\n| Error: you. are. already. legendary.");
    }

    /**
     * Prints an error indicating that the player cannot register to higher type because they do not meet the level requirement for it
     */
    public void throwRegisterError() {
        System.out.println("\n| Error: insufficient level");
    }

    /**
     * Returns the list of tools that are available in the game
     *
     * @return the list of available tools
     */
    public ArrayList<FarmTools> getTools() {
        return this.tools;
    }

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
