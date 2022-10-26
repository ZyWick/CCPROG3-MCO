import java.util.ArrayList;

import java.util.ArrayList;

public class System {
    private ArrayList<FarmTools> tools;
    private ArrayList<FarmSeeds> seeds;
    private ArrayList<FarmerType> type;
    private int day = 1;

    public System (ArrayList<FarmTools> tools, ArrayList<FarmSeeds> seeds, ArrayList<FarmerType> type) {
        this.tools = tools;
        this.seeds = seeds;
        this.type = type;
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
