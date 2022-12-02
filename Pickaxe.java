public class Pickaxe implements FarmTools{
    
    final int usageCost = 50;
    final double expYield = 15;

    public Pickaxe () {
        
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
