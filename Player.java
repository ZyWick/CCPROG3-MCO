import java.util.Scanner;

public class Player {
    private int objectCoins = 100;
    private Experience experience;
    private MyFarm farm;
    private FarmerType type;

    public Player(MyFarm farm, FarmerType type) {
        this.experience = new Experience();
        this.farm = farm;
        this.type  = type;
    }

    public void displayPlayerStats() {
        System.out.print("\n| " + type.getName());
        System.out.print(" | ObjectCoins: " + this.objectCoins);
        System.out.print(" | exp: " + this.experience.getExp());
        System.out.print(" | level: " + this.experience.getLevel());
        FarmerType zType = farm.canRegisterUp(this.type.getName(), this.experience.getLevel(), this.objectCoins);
        if (zType != null)
            System.out.println(" | can register to " + zType.getName() + " (cost: " + zType.getRegistrationFee() + " ObjectCoins)");
        else 
            System.out.println(" |");
    }

    public void useTool (int tileIndex, Scanner sc) {
        int choice = getToolChoice(sc, tileIndex, this.objectCoins);
        if (farm.useTool(tileIndex, choice, this.objectCoins)) {
            double[] yield = new double[2];

            switch (choice) {
                case 0: yield = farm.plowTile(tileIndex); break;
                case 1: yield = farm.waterTile(tileIndex); break;
                case 2: yield = farm.fertilizeTile(tileIndex); break;
                case 3: yield = farm.removeRock(tileIndex); break;
                case 4: yield = farm.useShovel(tileIndex); break;
            }

            this.objectCoins -= yield[0];
            this.experience.addExp(yield[1]); 
            System.out.println("\n| ObjectCoins expended: " + yield[0]);
            System.out.println("| Experience gained: " + yield[2]); 
        }
    }

    public void plantCrop (int tileIndex, Scanner sc) {
        if(farm.canPlantSeed(tileIndex)) {
            int choice = getSeedChoice(sc, tileIndex, this.objectCoins);

            if (farm.canPlantCrop(tileIndex, this.type.getSeedCostReduction(), choice, this.objectCoins)) {
                int cost = farm.plantCrop(tileIndex, choice);

                this.objectCoins -= cost + this.type.getSeedCostReduction();
                System.out.println("\n| ObjectCoins expended: " + (cost + this.type.getSeedCostReduction()));
            }
        } 
    }

    public void harvestCrop (int tileIndex) {
        if (farm.canHarvest(tileIndex)) {
            double[] yield = farm.harvestCrop(tileIndex, this.type.getName());
            this.objectCoins += (int)yield[0];
            this.experience.addExp(yield[1]);
            System.out.println("| ObjectCoins gained: " + (int)yield[0]);
            System.out.println("| Experience gained: " + yield[1]);
        }
    }

    public void interactTile(Scanner sc) {
        // int tileIndex = getTileIndex(sc);
        int tileIndex = 0;
        farm.display(3);
        int choice = sc.nextInt();
        switch (choice) {
            case 1: useTool(tileIndex, sc);
                    break;
            case 2: plantCrop(tileIndex, sc);
                    break;
            case 3: harvestCrop(tileIndex); 
                    break;
            case 4: farm.displayTileStatus(tileIndex);
                    break;
            default: break;
            }
    }

    public void advanceDay() {
        farm.ageLot();
    }

    public void RegisterUp() {
        FarmerType zType = farm.canRegisterUp(this.type.getName(), this.experience.getLevel(), this.objectCoins);

        if (zType != null) {
            this.type = zType;
            this.objectCoins -= zType.getRegistrationFee();
            System.out.println("\n| ObjectCoins expended: " + zType.getRegistrationFee());
            System.out.println("...you are now a " + this.type.getName());
        }
    }

    public int end(Scanner sc) {
        if(farm.endGame(this.objectCoins, this.type.getSeedCostReduction())) {
            System.out.print("\n| input 1 to play again: "); 
            return sc.nextInt();
        }
            
        return 0;
    }

    public int getSeedChoice(Scanner sc, int tileIndex, int objectCoins) {
        int choice;
        System.out.println("\nWhich seed do you want to plant?");
        farm.displaySeeds(this.objectCoins, this.type.getSeedCostReduction());
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
