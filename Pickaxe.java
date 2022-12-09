public class Pickaxe implements FarmTools{
    
    final int usageCost = 50;
    final double expYield = 15;

    public Pickaxe () {
        
    }

    public double[] useTool(MyFarm farm, Coordinates tileIndex) {
        farm.removeRock(tileIndex);
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
