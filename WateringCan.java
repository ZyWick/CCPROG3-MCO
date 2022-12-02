public class WateringCan implements FarmTools{
    
    final int usageCost = 0;
    final double expYield = 0.5;

    public WateringCan () {
        
    }

    public double[] useTool(MyFarm farm, int tileIndex) {
        farm.waterTile(tileIndex);
        return getToolCostAndYield();
    }

    public int int getUsageCost() {
        return this.usageCost;
    }

    private double[] getToolCostAndYield() {
        double[] yield = new double[2]; 
        yield [0] = getUsageCost();
        yield [1] = this.expYield;

        return yield;
    }
}
