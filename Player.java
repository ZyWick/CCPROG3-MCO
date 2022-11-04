public class Player {
    private int seeds = 0;
    private int objectCoins = 100;
    private double exp = 0.0;
    private int level = 1;
    private FarmSystem game = new FarmSystem();
    private FarmerType type = game.getType().get(0);

    public Player() {
    }
    
    public void levelUp(double exp) {
        if (this.level < exp / 100)
            this.level = (int)(exp / 100);
    }

    public void displayPlayerStats() {
        System.out.print("ObjectCoins: " + this.objectCoins);
        System.out.print(" exp: " + this.exp);
        System.out.print(" level: " + this.level);
        if (game.canRegisterUp(this.type, this.level))
            System.out.println("can register superior farmer type");
        else 
            System.out.println();
    }

    public void useTool (MyFarm farm, FarmTools selectedTool, Tile TheTile) {
        if (game.canUseTool(selectedTool, TheTile, this.objectCoins)) {
            
            switch (game.getTools().indexOf(selectedTool)) {
                case 0: farm.plowTile(TheTile); break;
                case 1: farm.waterTile(TheTile); break;
                case 2: farm.fertilizeTile(TheTile); break;
                case 3: farm.removeRock(TheTile); break;
                case 4: farm.removeWithered(TheTile); break;
            }
            
            objectCoins -= selectedTool.getUsageCost();
            exp += selectedTool.getExpYield();
            levelUp(exp);            
        }
        else
            game.throwToolError(selectedTool);
    }

    public void plantCrop (MyFarm farm, Tile TheTile, FarmSeeds selectedSeed) {
        if (game.canUseSeed(selectedSeed, this.objectCoins))        
            farm.plantCrop(TheTile, selectedSeed);
        else
            game.throwSeedError();
    }

    public void harvestCrop (MyFarm farm, Tile TheTile) {
        if (game.canHarvest(TheTile)) {
            FarmSeeds TheSeed = TheTile.getSeeds();
            int productsProduced = farm.getProductsProduced(TheTile);
            double harvestTotal, waterBonus, fertilizerBonus;      
            
            harvestTotal = productsProduced * (TheSeed.getSellingPrice() + type.getBonusEarning());
            waterBonus = harvestTotal * 0.2 * (TheTile.getWaterTimes() - 1);
            fertilizerBonus = harvestTotal * 0.5 * TheTile.getFertilizerTimes();
            harvestTotal = harvestTotal + waterBonus + fertilizerBonus;

            this.objectCoins += harvestTotal;
            this.exp += TheSeed.getExpYield();
            levelUp(exp); 
        }
        else
            game.throwHarvestError(TheTile);
    }

    public void RegisterUp() {
        if (game.canRegisterUp(this.type, this.level))
            this.type = game.getType().get(game.getType().indexOf(this.type) + 1);
        else
            game.throwRegisterError();
    }

    public FarmSystem getFarmSystem() {
        return this.game;
    }

    public void advanceDay(MyFarm farm) {
        game.addDay();
        farm.ageLot();
    }

    public int getObjectCoins() {
        return this.objectCoins;
    }

    public FarmerType getType() {
        return this.type;
    }

    public int getLevel() {
        return this.level;
    }
}
