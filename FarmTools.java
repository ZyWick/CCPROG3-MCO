public class FarmTools {
    private String name;
    private int usageCost;
    private double expYield;

    public FarmTools(String name, int usageCost, double expYield) {
        this.name = name;
        this.usageCost = usageCost;
        this.expYield = expYield;
    } 
    
    public String getName() {
        return this.name;
    }

    public int getUsageCost() {
        return this.usageCost;
    }

    public double getExpYield() {
        return this.expYield;
    }

}
