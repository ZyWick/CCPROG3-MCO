public class WateringCan implements FarmTools{
    
    private final int usageCost = 0;
    private final double expYield = 0.5;

    /** Creates a Watering can
     * 
     */
    public WateringCan () {
        
    }

    public int canUseTool(MyFarm farm, Coordinates coordinates) {
        int error = 0;
        if (farm.doesTileWorF(coordinates) == false)
            error = 3;
        return error;
    }

    public double[] useTool(MyFarm farm, Coordinates tileIndex) {
        farm.waterTile(tileIndex);
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
        return "Watering Can";
    }
}
