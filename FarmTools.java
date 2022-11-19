/**
 * FarmTools stores information about a particular tool
 */
public abstract class FarmTools {
    private int usageCost;
    private double expYield;

    /**
     * Creates a farm tool
     * @param name name of the tool
     * @param usageCost cost of using the tool
     * @param expYield amount of experience gained from using the tool
     */
    public FarmTools(int usageCost, double expYield) {
        this.usageCost = usageCost;
        this.expYield = expYield;
    }

    protected abstract double[] useTool (MyFarm farm, int tileIndex);

    /**
     * Returns the cost of using the tool
     * @return cost of using the tool
     */
    public int getUsageCost() {
        return this.usageCost;
    }

    /**
     * Returns the amount of experience gained from using the tool
     * @return amount of experience gained
     */
    public double getExpYield() {
        return this.expYield;
    }

    /**
     * Returns the cost of using the tool and amount of experience gained from using the tool
     * @return an array containing {cost of using the tool, amount of experience gained}
     */
    protected double[] getToolCostAndYield() {
        double[] yield = new double[2]; 
        yield [0] = this.getUsageCost();
        yield [1] = this.getExpYield();

        return yield;
    }

}
