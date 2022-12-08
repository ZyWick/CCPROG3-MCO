public class Fertilizer implements FarmTools{
    
    final int usageCost = 10;
    final double expYield = 4;

    public Fertilizer () {
        
    }

    public double[] useTool(MyFarm farm, int tileIndex) {
        farm.fertilizeTile(tileIndex);
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
