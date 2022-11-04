import java.util.Scanner;

public class Player {
    private int seeds = 0;
    private int objectCoins = 100;
    private double exp = 0.0;
    private int level = 1;
    private MyFarm farm = new MyFarm();
    private FarmerType type = farm.getGame().getType().get(0);

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
        if (farm.getGame().canRegisterUp(this.type, this.level))
            System.out.println("can register superior farmer type");
        else 
            System.out.println();
    }

    public void useTool (int toolIndex, int tileIndex) {
        Tile TheTile = farm.getLot().get(tileIndex);
        FarmTools selectedTool = farm.getGame().getTools().get(toolIndex);

        if (farm.getGame().canUseTool(selectedTool, TheTile, this.objectCoins)) {
            
            switch (toolIndex) {
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
            farm.getGame().throwToolError(selectedTool);
    }

    public void plantCrop (int tileIndex, Scanner sc) {
        Tile TheTile = farm.getLot().get(tileIndex);
        if(TheTile.isPlowed()) {
            int choice = farm.getGame().getSeedChoice(sc, TheTile, this.objectCoins); 
            FarmSeeds selectedSeed = farm.getGame().getSeeds().get(choice);

            if (farm.getGame().canUseSeed(selectedSeed, choice)) {
                farm.plantCrop(TheTile, choice);
            } else
            farm.getGame().throwSeedError();
        } else
            farm.getGame().throwPlantError();
    }

    public void harvestCrop (int tileIndex) {
        Tile TheTile = farm.getLot().get(tileIndex);
        if (TheTile.canHarvest()) {
            FarmSeeds TheSeed = TheTile.getSeeds();
            int productsProduced = TheTile.getProductsProduced();
            double harvestTotal, waterBonus, fertilizerBonus;      
            
            harvestTotal = productsProduced * (TheSeed.getSellingPrice() + type.getBonusEarning());
            waterBonus = harvestTotal * 0.2 * (TheTile.getWaterTimes() - 1);
            fertilizerBonus = harvestTotal * 0.5 * TheTile.getFertilizerTimes();
            harvestTotal = harvestTotal + waterBonus + fertilizerBonus;

            TheTile = new Tile();
            this.objectCoins += harvestTotal;
            this.exp += TheSeed.getExpYield();
            levelUp(exp); 
        }
        else {
            int error = farm.identifyHarvestError(TheTile);
            farm.getGame().throwHarvestError(error);
        }
    }

    public void RegisterUp() {
        if (farm.getGame().canRegisterUp(this.type, this.level))
            this.type = farm.getGame().getType().get(farm.getGame().getType().indexOf(this.type) + 1);
        else
            farm.getGame().throwRegisterError();
    }

    public void advanceDay() {
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
