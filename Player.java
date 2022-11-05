import java.util.Scanner;

public class Player {
    private int objectCoins = 100;
    private Experience experience;
    private MyFarm farm;
    private FarmerType type;

    public Player(MyFarm farm) {
        this.experience = new Experience();
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
        int choice = farm.getToolChoice(sc, tileIndex, this.objectCoins);
        FarmTools selectedTool = farm.getGame().getTools().get(choice);

        if (farm.canUseTool(selectedTool, tileIndex, this.objectCoins)) {
            
            switch (choice) {
                case 0: farm.plowTile(tileIndex); break;
                case 1: farm.waterTile(tileIndex); break;
                case 2: farm.fertilizeTile(tileIndex); break;
                case 3: farm.removeRock(tileIndex); break;
                case 4: farm.removeWithered(tileIndex); break;
            }
            
            objectCoins -= selectedTool.getUsageCost();
            experience.addExp(selectedTool.getExpYield());         
        }
        else
            farm.getGame().throwToolError(selectedTool, this.objectCoins);
    }

    public void plantCrop (int tileIndex, Scanner sc) {
        int canPlant = farm.canPlantSeed(tileIndex);
        if(canPlant == 1) {
            int choice = farm.getSeedChoice(sc, tileIndex, this.objectCoins); 
            FarmSeeds selectedSeed = farm.getGame().getSeeds().get(choice);

            if (farm.canUseSeed(selectedSeed, this.objectCoins)) {
                farm.plantCrop(tileIndex, choice);
                this.objectCoins -= selectedSeed.getSeedCost();
            } else
                farm.getGame().throwSeedError();
        } else
            farm.getGame().throwPlantError(canPlant);
    }

    public void harvestCrop (int tileIndex) {
        if (farm.canHarvest(tileIndex)) {
            double[] yield = farm.harvestCrop(tileIndex, farm.getGame().getType().indexOf(type));
            this.objectCoins += yield[0];
            this.experience.addExp(yield[1]);
        }
        else {
            int error = farm.identifyHarvestError(tileIndex);
            farm.getGame().throwHarvestError(error);
        }
    }

    public void interactTile(Scanner sc) {
        int tileIndex = farm.getTileIndex(sc);
        farm.getGame().displayInteractionChoices ();
        int choice = sc.nextInt();
        switch (choice) {
            case 1: useTool(tileIndex, sc);
                    break;
            case 2: plantCrop(tileIndex, sc);
                    break;
            case 3: harvestCrop(tileIndex); 
                    break;
            case 4: farm.dispayTileStatus(tileIndex);
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
}
