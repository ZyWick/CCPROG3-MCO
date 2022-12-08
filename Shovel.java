public class Shovel implements FarmTools{
    
    final int usageCost = 7;
    final double expYield = 2;

    public Shovel () {
        
    }

    public double[] useTool(MyFarm farm, int tileIndex) {
        farm.useShovel(tileIndex);
        return getToolCostAndYield();
    }

    public int getUsageCost() {
        return this.usageCost;
    }

    private double[] getToolCostAndYield() {
        double[] yield = new double[2]; 
        yield [0] = getUsageCost();
        yield [1] = this.expYield;

        return yield;
    }
}
