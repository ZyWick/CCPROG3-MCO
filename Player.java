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
        System.out.print("\n| " + type.getName());
        System.out.print(" | ObjectCoins: " + this.objectCoins);
        System.out.print(" | exp: " + this.experience.getExp());
        System.out.print(" | level: " + this.experience.getLevel());
        FarmerType zType = canRegisterUp();
        if (zType != null)
            System.out.println(" | can register to " + zType.getName() + " (cost: " + zType.getRegistrationFee() + " ObjectCoins)");
        else 
            System.out.println(" |");
    }

    public void useTool (int tileIndex, Scanner sc) {
        int choice = getToolChoice(sc, tileIndex, this.objectCoins);
        if (choice >= 0 && choice < farm.getGame().getTools().size()) {
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
            } else
                farm.getGame().throwToolError(selectedTool, this.objectCoins);
        } else
            farm.getGame().throwOutOfBoundsError();
    }

    public void plantCrop (int tileIndex, Scanner sc) {
        int canPlant = farm.canPlantSeed(tileIndex);

        if(canPlant == 1) {
            int choice = getSeedChoice(sc, tileIndex, this.objectCoins);

            if (choice >= 0 && choice < farm.getGame().getSeeds().size()) {
                FarmSeeds selectedSeed = farm.getGame().getSeeds().get(choice);

                if (farm.canUseSeed(selectedSeed, this.objectCoins, this.type)) {
                    farm.plantCrop(tileIndex, choice);
                    this.objectCoins -= selectedSeed.getSeedCost() + this.type.getSeedCostReduction();
                } else
                    farm.getGame().throwSeedError();
            } else
                farm.getGame().throwOutOfBoundsError();
        } else
            farm.getGame().throwPlantError(canPlant);
    }

    public void harvestCrop (int tileIndex) {
        int result = farm.canHarvest(tileIndex);
        if (result == 0) {
            double[] yield = farm.harvestCrop(tileIndex, this.type);
            this.objectCoins += yield[0];
            this.experience.addExp(yield[1]);
        }
        else 
            farm.getGame().throwHarvestError(result);
    }

    public void interactTile(Scanner sc) {
        // int tileIndex = getTileIndex(sc);
        int tileIndex = 0;
        farm.getGame().displayInteractionChoices ();
        int choice = sc.nextInt();
        switch (choice) {
            case 1: useTool(tileIndex, sc);
                    break;
            case 2: plantCrop(tileIndex, sc);
                    break;
            case 3: harvestCrop(tileIndex); 
                    break;
            case 4: farm.displayTileStatus(tileIndex);
            default: break;
            }
    }

    public void advanceDay() {
        farm.ageLot();
    }

    public FarmerType canRegisterUp() {
        int nextLevelIndex = farm.getGame().getType().indexOf(this.type) + 1;

        if (nextLevelIndex < farm.getGame().getType().size()) {
            FarmerType zType = farm.getGame().getType().get(nextLevelIndex);

            if (this.experience.getLevel() >= zType.getLevelReq())
                return zType;
        }

        return null;
    }

    public void RegisterUp() {
        FarmerType zType = canRegisterUp();

        if (zType != null) {
            if (this.objectCoins >= zType.getRegistrationFee()) {
                this.type = zType;
                this.objectCoins -= zType.getRegistrationFee();
            } else {
                // maybe rename to more generic like notenoughobjectcoinserror
                farm.getGame().throwSeedError();
            }
        }
        else if (farm.getGame().getType().indexOf(this.type) + 1 >=  farm.getGame().getType().size())
            farm.getGame().throwMaxFarmerTypeError();
        else
            farm.getGame().throwRegisterError();
    }

    public int getSeedChoice(Scanner sc, int tileIndex, int objectCoins) {
        int choice;
        System.out.println("\nWhich seed do you want to plant?");
        farm.displaySeeds(this.objectCoins, this.type);
        System.out.print("Choice: ");
        choice = sc.nextInt();

        return choice;
    }

    public int getToolChoice(Scanner sc, int tileIndex, int objectCoins) {
        int choice;
        System.out.println("\nWhich tool do you want to use?");
        farm.displayTools(tileIndex, this.objectCoins);
        System.out.print("Choice: ");
        choice = sc.nextInt();

        return choice;
    }    

    public int getTileIndex (Scanner sc) {
        int x, y, tileIndex;

        System.out.print("\ninput tile coordinates: ");
        x = sc.nextInt();
        y = sc.nextInt();
        tileIndex = (x - 1) * 10 + (y - 1) ;

        return tileIndex;
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
