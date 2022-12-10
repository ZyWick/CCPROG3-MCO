public class WateringCan implements FarmTools{
    
    final int usageCost = 0;
    final double expYield = 0.5;

    public WateringCan () {
        
    }

    public String getName() {
        return "Watering Can";
    }

    public int canUseTool(MyFarm farm, Coordinates coordinates, double objectCoins) {
        int error = 3;
        if (farm.doesTileWorF(coordinates))
            error = 0;
        return error;
    }

    public double[] useTool(MyFarm farm, Coordinates tileIndex) {
        farm.waterTile(tileIndex);
        return getToolCostAndYield();
    }

    public int getUsageCost() {
        return this.usageCost;
    }

    public double[] getToolCostAndYield() {
        double[] yield = new double[2]; 
        yield [0] = getUsageCost();
        yield [1] = this.expYield;

        return yield;
    }
}
