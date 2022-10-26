import java.util.ArrayList;

import java.util.ArrayList;

public class System {
    private ArrayList<FarmTools> tools;
    private ArrayList<FarmSeeds> seeds;
    private ArrayList<FarmerType> type;
    private int day = 1;

    public System () {
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
