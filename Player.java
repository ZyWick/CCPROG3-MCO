import java.util.Scanner;

public class Player {
    private int objectCoins = 100;
    private Experience experience;
    private MyFarm farm;
    private FarmerType type;

    public Player(MyFarm farm) {
        this.farm = farm;
        this.type  = farm.getGame().getType().get(0);
    }

    public void displayPlayerStats() {
        System.out.print("ObjectCoins: " + this.objectCoins);
        System.out.print(" exp: " + this.experience.getExp());
        System.out.print(" level: " + this.experience.getLevel());
        if (farm.getGame().canRegisterUp(this.type, this.experience.getLevel()))
            System.out.println("can register superior farmer type");
        else 
            System.out.println();
    }

    public void useTool (int tileIndex, Scanner sc) {
        Tile TheTile = farm.getLot().get(tileIndex);
        int choice = farm.getGame().getToolChoice(sc, TheTile, this.objectCoins);
        FarmTools selectedTool = farm.getGame().getTools().get(choice);

        if (farm.getGame().canUseTool(selectedTool, TheTile, this.objectCoins)) {
            
            switch (choice) {
                case 0: farm.plowTile(TheTile); break;
                case 1: farm.waterTile(TheTile); break;
                case 2: farm.fertilizeTile(TheTile); break;
                case 3: farm.removeRock(TheTile); break;
                case 4: farm.removeWithered(TheTile); break;
            }
            
            objectCoins -= selectedTool.getUsageCost();
            experience.addExp(selectedTool.getExpYield());         
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
                this.objectCoins -= selectedSeed.getSeedCost();
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
            experience.addExp(TheSeed.getExpYield()); 
        }
        else {
            int error = farm.identifyHarvestError(TheTile);
            farm.getGame().throwHarvestError(error);
        }
    }

    public void interactTile(Scanner sc) {
        int tileIndex = farm.getGame().getTileIndex(sc);
        farm.getGame().displayInteractionChoices ();
        int choice = sc.nextInt();
        switch (choice) {
            case 1: useTool(tileIndex, sc);
                    break;
            case 2: plantCrop(tileIndex, sc);
                    break;
            case 3: harvestCrop(tileIndex); 
                    break;
            default: break;
            }
    }

    public void RegisterUp() {
        if (farm.getGame().canRegisterUp(this.type, this.experience.getLevel()))
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
        return this.experience.getLevel();
    }

    public MyFarm getFarm() {
        return this.farm;
    }
}
