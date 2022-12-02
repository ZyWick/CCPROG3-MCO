public class Plow implements FarmTools{
    
    final int usageCost = 0;
    final double expYield = 0.5;

    public Plow () {
        
    }

    public double[] useTool(MyFarm farm, int tileIndex) {
        farm.plowTile(tileIndex);
        return getToolCostAndYield();
    }

    private double[] getToolCostAndYield() {
        double[] yield = new double[2]; 
        yield [0] = this.usageCost;
        yield [1] = this.expYield;

        return yield;
    }
}
