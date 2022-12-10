/**
 * FarmTools stores information about a particular tool
 */
public interface FarmTools {

    // /**
    //  * Creates a farm tool
    //  * @param name name of the tool
    //  * @param usageCost cost of using the tool
    //  * @param expYield amount of experience gained from using the tool
    //  */
    // public FarmTools(String name, int usageCost, double expYield) {
    //     this.name = name;
    //     this.usageCost = usageCost;
    //     this.expYield = expYield;
    // }
    public int canUseTool(MyFarm farm, Coordinates coordinates);
    // /**
    //  * Returns the name of the tool
    //  * @return name of the tool
    //  */
    public String getName();

    // /**
    //  * Returns the cost of using the tool
    //  * @return cost of using the tool
    //  */
    public int getUsageCost();

    // /**
    //  * Returns the amount of experience gained from using the tool
    //  * @return amount of experience gained
    //  */
    // public double getExpYield();

    // /**
    //  * Returns the cost of using the tool and amount of experience gained from using the tool
    //  * @return an array containing {cost of using the tool, amount of experience gained}
    //  */
    public double[] getToolCostAndYield();

        public double[] useTool(MyFarm farm, Coordinates tileIndex);
}
