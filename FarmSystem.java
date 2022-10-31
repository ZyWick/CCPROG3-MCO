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

    public ArrayList<Integer> displayAvailableTileActions (MyFarm farm, int tileIndex) {
        ArrayList<Integer> index = new ArrayList<Integer>();
        Tile tile = farm.getLot().get(tileIndex);
        int x = 1;

        if (tile.isPlowed()) {
            if (tile.getSeeds() != null) {
                index.add(1);
                index.add(2);
            } else
                index.add(5);
        } else if (tile.isPlowed() == false) {
            index.add(0);
        } else if (tile.isRock()) {
            index.add(3);
        }

        for (int actionIndex: index) {
            if (actionIndex == 5) {
                System.out.println(x + " - " + "plant seeds");
            } else
                System.out.println(x + " - " + tools.get(actionIndex).getName());
            x++;
        }

        return index;
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
