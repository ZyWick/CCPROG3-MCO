import java.util.ArrayList;
import java.util.Scanner;

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

    public boolean canUseTool(FarmTools selectedTool, Tile TheTile, int objectCoins) {
        if (objectCoins >= selectedTool.getUsageCost()) {
            if (selectedTool.getName().equals("Plow") && TheTile.isPlowed() == false && TheTile.isRock() == false) 
                return true;
            else if ((selectedTool.getName().equals("Watering Can") || selectedTool.getName().equals("Fertilizer"))
                     && TheTile.isPlowed() == true && TheTile.getSeeds() != null)
                return true;      
            else if (selectedTool.getName().equals("Pickaxe") && TheTile.isRock())
                return true;
            else if (selectedTool.getName().equals("Shovel"))
                return true;
        }       

        return false;
    }
    
    public boolean canUseSeed(FarmSeeds seed, int PlayerObjectCoins) {
        if (seed.getSeedCost() <= PlayerObjectCoins)
            return true;

        return false;
    }

    public boolean canRegisterUp(FarmerType currentType, int level) {
        FarmerType zType = type.get(type.indexOf(currentType) + 1);

        if (level >= zType.getLevelReq()) 
            return true;

        return false;
    }

    public boolean canHarvest(Tile TheTile) {
        
        if (TheTile.getDay() == TheTile.getSeeds().getHarvestTime())
            return true;
        
        return false;
    }

    public void throwToolError(FarmTools selectedTool) {
        switch (tools.indexOf(selectedTool)) {
            case 0: System.out.println("tile is already plowed\n"); break;
            case 1: case 2: System.out.println("tile does not have a crop\n"); break;
            case 3: System.out.println("tiles does not have a rock\n"); break;
        }
    }

    public void throwSeedError() {
        System.out.println("Error: not enough objectCoins\n");
    }

    public void throwHarvestError(Tile TheTile) {
        if (TheTile.isWithered())
            System.out.print("Error: crop is withered\n");
        else
            System.out.print("Error: crop has not met harvest time\n");
    }

    public void throwRegisterError() {
        System.out.println("Error: insufficient level");
    }

    public void throwPlantError() {
        System.out.println("Error: tile is unplowed");
    }

    public void displayTools (Tile TheTile, int objectCoins) {
        for (FarmTools tool : tools) {
            if(canUseTool(tool, TheTile, objectCoins))
                System.out.print("| / | ");
            else
                System.out.print("| x | ");

            System.out.println(tools.indexOf(tool) + " - " + tool.getName());
        }
    }

    public void displaySeeds (int PlayerObjectCoins) {
        for (FarmSeeds seed : seeds) {
            if(canUseSeed(seed, PlayerObjectCoins))
                System.out.print("| / | ");
            else
                System.out.print("| x | ");

            System.out.println(seeds.indexOf(seed) + " - " + seed.getName());
        }
    }

    public void displayGameMoves () {
        System.out.println("What do you want to do?");
        System.out.println("1 - display farm");
        System.out.println("2 - Interact with tile");
        System.out.println("3 - advance day");
        System.out.println("4 - display player stats");
        System.out.println("5 - Register superior farmer type");
    }

    public void displayInteractionChoices () {
        System.out.println("What do you want to do?");
        System.out.println("1 - Use Tool");
        System.out.println("2 - Plant seed");
        System.out.println("3 - Harvest Crop");
        System.out.println("x - input any number to return to main");
        System.out.print("Choice: ");
    }

    public int getTileIndex (Scanner sc) {
        int x, y, tileIndex;

        System.out.print("input tile coordinates: ");
        x = sc.nextInt();
        y = sc.nextInt();
        tileIndex = (x - 1) * 10 + (y - 1) ;

        return tileIndex;
    }

    public int getToolChoice(Scanner sc, Tile TheTile, int objectCoins) {
        int choice;
        System.out.println("Which tool do you want to use?");
        displayTools(TheTile, objectCoins);
        System.out.print("Choice: ");
        choice = sc.nextInt();

        return choice;
    }

    public int getSeedChoice(Scanner sc, Tile TheTile, int objectCoins) {
        int choice;
        if (TheTile.isPlowed()) {
            System.out.println("Which seed do you want to plant?");
            displaySeeds(objectCoins);
            System.out.print("Choice: ");
            choice = sc.nextInt();
        } else 
            choice = -1;

        return choice;
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
    }

}
