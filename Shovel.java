public class Shovel implements FarmTools{
    
    final int usageCost = 7;
    final double expYield = 2;

    public Shovel () {
        
    }

    public String getName() {
        return "Shovel";
    }

    public double[] useTool(MyFarm farm, Coordinates tileIndex) {
        farm.useShovel(tileIndex);
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
