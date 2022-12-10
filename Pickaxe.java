public class Pickaxe implements FarmTools{
    
    private final int usageCost = 50;
    private final double expYield = 15;

    /** Creates a Pickaxe
     * 
     */
    public Pickaxe () {
        
    }

    public int canUseTool(MyFarm farm, Coordinates coordinates) {
        int error = 0;
        if (farm.doesTileRock(coordinates) == false)
            error = 5;
        return error;
    }

    public double[] useTool(MyFarm farm, Coordinates tileIndex) {
        farm.removeRock(tileIndex);
        return getToolCostAndYield();
    }

    public double[] getToolCostAndYield() {
        double[] yield = new double[2]; 
        yield [0] = getUsageCost();
        yield [1] = this.expYield;

        return yield;
    }
        
    public int getUsageCost() {
        return this.usageCost;
    }
    
    public String getName() {
        return "Pickaxe";
    }
}
