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
    
    public boolean canRegisterUp(FarmerType currentType, int level) {
        FarmerType zType = type.get(type.indexOf(currentType) + 1);

        if (level >= zType.getLevelReq()) 
            return true;

        return false;
    }

    public void throwToolError(FarmTools selectedTool, int objectCoins) {
        if (selectedTool.getUsageCost() > objectCoins)
            System.out.println("Error: not enough objectCoins\n");
        else
            switch (tools.indexOf(selectedTool)) {
                case 0: System.out.println("Error: tile is already plowed\n"); break;
                case 1: case 2: System.out.println("Error: tile does not have a crop\n"); break;
                case 3: System.out.println("Error: tile does not have a rock\n"); break;
            }
    }

    public void throwSeedError() {
        System.out.println("Error: not enough objectCoins\n");
    }

    public void throwHarvestError(int errorType) {
        switch (errorType) {
            case 1: System.out.print("Error: crop is withered, it is past its harvest time\n"); break;
            case 2: System.out.print("Error: crop is withered, it did not meet fertilizer needs\n"); break;
            case 3: System.out.print("Error: crop is withered, it did not meet water needs\n"); break;
            case 0: System.out.print("Error: crop has not met harvest time\n"); break;
        }
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

    public void displayGameMoves () {
        System.out.println("\nWhat do you want to do?");
        System.out.println("1 - display farm");
        System.out.println("2 - Interact with tile");
        System.out.println("3 - advance day");
        System.out.println("4 - display player stats");
        System.out.println("5 - Register superior farmer type");
    }

    public void displayInteractionChoices () {
        System.out.println("\nWhat do you want to do?");
        System.out.println("1 - Use Tool");
        System.out.println("2 - Plant seed");
        System.out.println("3 - Harvest Crop");
        System.out.println("4 - display tile status");
        System.out.println("x - input any number to return to main");
        System.out.print("Choice: ");
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

    public void addDay() {
        this.day += 1;
        System.out.println("Day: " + this.day); 
    }
}
