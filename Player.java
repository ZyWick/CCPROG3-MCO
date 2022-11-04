import java.util.Scanner;

public class Player {
    private int seeds = 0;
    private int objectCoins = 100;
    private Experience experience;
    private FarmerType type;

    public Player(FarmerType type) {
        this.type = type;
    }

    public void displayPlayerStats(MyFarm farm) {
        System.out.print("ObjectCoins: " + this.objectCoins);
        System.out.print(" exp: " + this.experience.getExp());
        System.out.print(" level: " + this.experience.getLevel());
        if (farm.getGame().canRegisterUp(this.type, this.experience.getLevel()))
            System.out.println("can register superior farmer type");
        else 
            System.out.println();
    }

    public void useTool (MyFarm farm, FarmTools selectedTool, Tile TheTile) {
        if (farm.getGame().canUseTool(selectedTool, TheTile, this.objectCoins)) {
            
            switch (farm.getGame().getTools().indexOf(selectedTool)) {
                case 0: TheTile.setPlowed(true); break;
                case 1: TheTile.addWaterTimes(); break;
                case 2: TheTile.addFertilizerTimes(); break;
                case 3: TheTile.setRock(false);  break;
                case 4: if (TheTile.isWithered()) {
                            TheTile = new Tile();
                        } else if (TheTile.isPlowed() && TheTile.getSeeds() != null) {
                            TheTile = new Tile();
                        } else if (TheTile.isPlowed() == false || TheTile.isRock()) {
        
                        }
                        break;
            }
            
            objectCoins -= selectedTool.getUsageCost();
            experience.addExp(selectedTool.getExpYield()); 
        }
        else
            farm.getGame().throwToolError(selectedTool);
    }

    public void plantCrop (MyFarm farm, Tile TheTile, Scanner sc) {
        if(TheTile.isPlowed()) {
            int choice = farm.getGame().getSeedChoice(sc, TheTile, this.objectCoins); 
            FarmSeeds selectedSeed = farm.getGame().getSeeds().get(choice);

            if (farm.getGame().canUseSeed(selectedSeed, choice)) {
                TheTile.setSeeds(selectedSeed);
            } else
            farm.getGame().throwSeedError();
        } else
            farm.getGame().throwPlantError();
    }

    public void harvestCrop (MyFarm farm, Tile TheTile) {
        if (farm.getGame().canHarvest(TheTile)) {
            FarmSeeds TheSeed = TheTile.getSeeds();
            int productsProduced = farm.getProductsProduced(TheSeed);
            double harvestTotal, waterBonus, fertilizerBonus;      
            
            harvestTotal = productsProduced * (TheSeed.getSellingPrice() + type.getBonusEarning());
            waterBonus = harvestTotal * 0.2 * (TheTile.getWaterTimes() - 1);
            fertilizerBonus = harvestTotal * 0.5 * TheTile.getFertilizerTimes();
            harvestTotal = harvestTotal + waterBonus + fertilizerBonus;

            TheTile = new Tile();
            this.objectCoins += harvestTotal;
            experience.addExp(TheSeed.getExpYield());
        }
        else
            farm.getGame().throwHarvestError(TheTile);
    }

    public void RegisterUp(MyFarm farm) {
        if (farm.getGame().canRegisterUp(this.type, this.experience.getLevel()))
            this.type = farm.getGame().getType().get(farm.getGame().getType().indexOf(this.type) + 1);
        else
            farm.getGame().throwRegisterError();
    }


    public void advanceDay(MyFarm farm) {
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
}
