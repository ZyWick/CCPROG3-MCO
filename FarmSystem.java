import java.util.ArrayList;

public class FarmSystem {
    private ArrayList<FarmTools> tools = new ArrayList<FarmTools>();
    private ArrayList<FarmSeeds> seeds = new ArrayList<FarmSeeds>();
    private ArrayList<FarmerType> type = new ArrayList<FarmerType>();
    private int day = 1;

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

    public void addDay() {
        this.day += 1;
        System.out.println("Day: " + this.day); 
    }
    
    public void throwToolError(int error) {
        switch (error) {
            case 1: System.out.println("Error: tile is already plowed\n"); break;
            case 2: System.out.println("Error: can't plow tile with rock\n"); break;
            case 3: case 4: System.out.println("Error: tile does not have a crop\n"); break;
            case 5: System.out.println("Error: tile does not have a rock\n"); break;
            case 6: throwInsufficientObjectCoins();
        }
    }

    public void throwInsufficientObjectCoins() {
        System.out.println("Error: not enough objectCoins\n");
    }

    public void throwHarvestError(int error) {
        switch (error) {
            case 1: System.out.print("Error: crop is withered, it is past its harvest time\n"); break;
            case 2: System.out.print("Error: crop is withered, it did not meet fertilizer needs\n"); break;
            case 3: System.out.print("Error: crop is withered, it did not meet water needs\n"); break;
            case 4: System.out.print("Error: crop has not met harvest time\n"); break;
            case 5: System.out.print("Error: tile has no crop\n"); break;
        }
    }

    public void throwMaxFarmerTypeError() {
        System.out.println("Error: you. are. already. legendary.");
    }

    public void throwRegisterError() {
        System.out.println("Error: insufficient level");
    }

    public void throwPlantError(int error) {
        switch (error) {
            case 2: System.out.println("Error: tile is unplowed"); break;
            case 3: System.out.println("Error: tile has a crop already"); break;
        }
    }
    
    public void throwOutOfBoundsError() {
        System.out.println("Error: no such choice");
    }

    public void displayGameMoves () {
        System.out.println("\nWhat do you want to do?");
        System.out.println("| 1 - display farm");
        System.out.println("| 2 - Interact with tile");
        System.out.println("| 3 - advance day");
        System.out.println("| 4 - display player stats");
        System.out.println("| 5 - Register superior farmer type");
        System.out.println("| 6 - display farm legend");
        System.out.println("| ...input any number to return to main");
        System.out.print("Choice: ");
    }

    public void displayInteractionChoices () {
        System.out.println("\nWhat do you want to do?");
        System.out.println("| 1 - Use Tool");
        System.out.println("| 2 - Plant seed");
        System.out.println("| 3 - Harvest Crop");
        System.out.println("| 4 - display tile status");
        System.out.println("| ...input any number to return to main");
        System.out.print("Choice: ");
    }

    public void displayLotLegend () {
        System.out.println("\nLegend:");
        System.out.println("| ( = ) unplowed tile ");
        System.out.println("| ( # ) plowed tile ");
        System.out.println("| ( * ) rock ");
        System.out.println("| ( , )( s )( S ) crop ");
        System.out.println("| ( $ ) Harvestable crop ");
        System.out.println("| ( X ) Withered crop ");
    }

    public ArrayList<FarmTools> getTools() {
        return this.tools;
    }

    public ArrayList<FarmSeeds> getSeeds() {
        return this.seeds;
    }

    public ArrayList<FarmerType> getType() {
        return this.type;
    }

    public int getDay() {
        return this.day;
    }
}
