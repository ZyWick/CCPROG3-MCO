public class Shovel implements FarmTools{
    
    final int usageCost = 7;
    final double expYield = 2;

    /** Creates a Shovel
     * 
     */
    public Shovel () {
        
    }

    public int canUseTool(MyFarm farm, Coordinates coordinates) {
        int error = 0;
        return error;
    }

    public double[] useTool(MyFarm farm, Coordinates tileIndex) {
        farm.useShovel(tileIndex);
        return getToolCostAndYield();
    }

    public double[] getToolCostAndYield() {
        double[] yield = new double[2]; 
        yield [0] = getUsageCost();
        yield [1] = this.expYield;

        return yield;
    }
    
    public int getUsageCost() {
        return this.usageCost;
    }

    public String getName() {
        return "Shovel";
    }
}
