public class Plow extends FarmTools {
    
    public Plow (int usageCost, double expYield) {
        super(usageCost, expYield);
    }

    @Override
    protected double[] useTool(MyFarm farm, int tileIndex) {
        farm.plowTile(tileIndex);
        return getToolCostAndYield();
    }
    
}
