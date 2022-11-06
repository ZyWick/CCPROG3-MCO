public class FarmTools {
    private String name;
    private int usageCost;
    private double expYield;

    /**
     * Creates a farm tool
     * @param name name of the tool
     * @param usageCost cost of using the tool
     * @param expYield amount of experience gained from using the tool
     */
    public FarmTools(String name, int usageCost, double expYield) {
        this.name = name;
        this.usageCost = usageCost;
        this.expYield = expYield;
    }

    /**
     * Returns the name of the tool
     * @return name of the tool
     */
    public String getName() {
        return this.name;
    }

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

}
