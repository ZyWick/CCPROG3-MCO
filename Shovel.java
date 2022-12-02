public class Shovel implements FarmTools{
    
    final int usageCost = 7;
    final double expYield = 2;

    public Shovel () {
        
    }

    public double[] useTool(MyFarm farm, int tileIndex) {
        farm.useShovel(tileIndex);
        return getToolCostAndYield();
    }

    private double[] getToolCostAndYield() {
        double[] yield = new double[2]; 
        yield [0] = this.usageCost;
        yield [1] = this.expYield;

        return yield;
    }
}
