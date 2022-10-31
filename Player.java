public class Player {
    private int seeds = 0;
    private int objectCoins = 100;
    private double exp = 0.0;
    private int level = 1;
    private System god = new System();
    private FarmerType type = god.getType().get(0);

    public Player() {
    }

    public void useTool (MyFarm farm, int toolIndex, int tileIndex) {
        FarmTools tool = god.getTools().get(toolIndex);
        if (objectCoins >= tool.getUsageCost()) {
            
            switch (toolIndex) {
                case 0: farm.plowTile(tileIndex); break;
                case 1: farm.waterTile(tileIndex); break;
                case 2: farm.fertilizeTile(tileIndex); break;
                case 3: farm.removeRock(tileIndex); break;
                case 4: farm.removeWithered(tileIndex); break;
            }
            
            objectCoins -= tool.getUsageCost();
            exp += tool.getExpYield();            
        }
    }

    public void plantCrop (MyFarm farm, int tileIndex, int seedIndex) {
        farm.plantCrop(tileIndex, god.getSeeds().get(seedIndex));
    }

    public void harvestCrop (MyFarm farm, int tileIndex) {

        Tile TheTile = farm.getLot().get(tileIndex);
        FarmSeeds TheSeed = TheTile.getSeeds();
        int productsProduced = farm.getProductsProduced(tileIndex);
        double harvestTotal, waterBonus, fertilizerBonus;      
        
        harvestTotal = productsProduced * (TheSeed.getSellingPrice() + type.getBonusEarning());
        waterBonus = harvestTotal * 0.2 * (TheTile.getWaterTimes() - 1);
        fertilizerBonus = harvestTotal * 0.5 * TheTile.getFertilizerTimes();
        harvestTotal = harvestTotal + waterBonus + fertilizerBonus;

        objectCoins += harvestTotal;
    }

}