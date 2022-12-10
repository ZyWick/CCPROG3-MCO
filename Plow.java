public class Plow implements FarmTools{
    
    final int usageCost = 0;
    final double expYield = 0.5;

    public Plow () {
        
    }

    public String getName() {
        return "Plow";
    }

    public double[] useTool(MyFarm farm, Coordinates tileIndex) {
        farm.plowTile(tileIndex);
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
