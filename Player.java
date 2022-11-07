import java.util.Scanner;

public class Player {
    private double objectCoins = 100.0;
    private Experience experience;
    private MyFarm farm;
    private FarmerType type;

    /**
     * Instantiates the Player.
     *
     * @param farm an existing MyFarm
     */
    public Player(MyFarm farm) {
        this.experience = new Experience();
        this.farm = farm;
        this.type = farm.getGame().getType().get(0);
    }

    /**
     * Prints the information about the player
     */
    public void displayPlayerStats() {
        System.out.print("\n| " + type.getName());
        System.out.print(" | ObjectCoins: " + this.objectCoins);
        System.out.print(" | exp: " + this.experience.getExp());
        System.out.print(" | level: " + this.experience.getLevel());
        System.out.print(" | day: " + this.farm.getGame().getDay());

        switch (canRegisterUp()) {
            case REGISTER_UP_OK:
            case REGISTER_UP_ERR_INSUFFICIENT_OBJECTCOINS:
                FarmerType zType = farm.getGame().getNextFarmerType(type);
                System.out.println(" | can register to " + zType.getName() + " (cost: " + zType.getRegistrationFee() + " ObjectCoins)");
                break;
            default:
                System.out.println(" |");
        }
    }

    public int whatCanIDo(Scanner sc) {
        farm.getGame().displayGameMoves();
        return sc.nextInt();
    }

    public void thisIsMyFarm () {
        farm.display();
    }

    public void whatIsThat () {
        farm.getGame().displayLotLegend();
    }

    /**
     * Displays the list of eligible tools and asks the player to pick a tool
     * @param sc the Scanner to get input from
     * @param tileIndex the tile index
     * @return
     */
    private int getToolChoice(Scanner sc, int tileIndex) {
        int choice;
        System.out.println("\nWhich tool do you want to use?");
        farm.displayTools(tileIndex, this.objectCoins);
        System.out.print("Choice: ");
        choice = sc.nextInt();

        return choice;
    }

    /**
     * Displays a list of seeds that are available and asks the player to pick from one of them
     * @param sc the Scanner to get input from
     * @return
     */
    private int getSeedChoice(Scanner sc) {
        int choice;
        System.out.println("\nWhich seed do you want to plant?");
        farm.displaySeeds(this.objectCoins, this.type.getSeedCostReduction());
        System.out.print("Choice: ");
        choice = sc.nextInt();

        return choice;
    }

    /**
     * Asks for tile coordinates from the player and returns it as an index
     *
     * @param sc the Scanner to get input from
     * @return the tile index
     */
    public int getTileIndex (Scanner sc) {
        int x, y, tileIndex;

        System.out.print("\ninput tile coordinates: ");
        x = sc.nextInt();
        y = sc.nextInt();
        tileIndex = (x - 1) * 10 + (y - 1) ;

        return tileIndex;
    }

    /**
     * Asks player to select a tile action
     *
     * @param sc the Scanner to get input from
     */
    public void interactTile(Scanner sc) {
        // int tileIndex = getTileIndex(sc);
        int tileIndex = 0;
        farm.getGame().displayInteractionChoices();;
        int choice = sc.nextInt();
        switch (choice) {
            case 1: useTool(sc, tileIndex);
                    break;
            case 2: plantCrop(sc, tileIndex);
                    break;
            case 3: harvestCrop(tileIndex); 
                    break;
            case 4: farm.displayTileStatus(tileIndex);
                    break;
            default: break;
            }
    }

    /**
     * Adds experience to the experience counter, then informs the player if the level changes
     * @param expToAdd amount of experience points to add
     */
    private void addExp(double expToAdd) {
        int prev = this.experience.getLevel();

        this.experience.addExp(expToAdd);
        if (prev < this.experience.getLevel())
            System.out.println("\n...player leveled up!");
    }

    /**
     * Asks player to choose a tool, then uses it on the specific tile index.
     * @param sc the Scanner to get input from
     * @param tileIndex the tile index
     */
    private void useTool (Scanner sc, int tileIndex) {
        int choice = getToolChoice(sc, tileIndex);
        if (farm.checkUseTool(tileIndex, choice, this.objectCoins)) {
            double[] yield = new double[2];

            switch (choice) {
                case 0: yield = farm.plowTile(tileIndex); break;
                case 1: yield = farm.waterTile(tileIndex); break;
                case 2: yield = farm.fertilizeTile(tileIndex); break;
                case 3: yield = farm.removeRock(tileIndex); break;
                case 4: yield = farm.useShovel(tileIndex); break;
            }

            this.objectCoins -= yield[0];
            this.addExp(yield[1]);
            System.out.println("| ObjectCoins expended: " + yield[0]);
            System.out.println("| Experience gained: " + yield[1]);
        }
    }

    /**
     * Prompts the player to pick a seed, then attempts to plant a crop at a specific tile index
     * @param sc the Scanner to get input from
     * @param tileIndex the tile index
     */
    private void plantCrop (Scanner sc, int tileIndex) {
        if(farm.checkPlantInTile(tileIndex)) {
            int choice = getSeedChoice(sc);

            if (farm.checkPlantCrop(tileIndex, this.type.getSeedCostReduction(), choice, this.objectCoins)) {
                int cost = farm.plantCrop(tileIndex, choice);

                this.objectCoins -= cost + this.type.getSeedCostReduction();
                System.out.println("| ObjectCoins expended: " + (cost + this.type.getSeedCostReduction()));
            }
        } 
    }

    /**
     * Attempts to harvest a crop at a specific tile index
     * @param tileIndex the tile index
     */
    private void harvestCrop (int tileIndex) {
        if (farm.checkHarvest(tileIndex)) {
            double[] yield = farm.harvestCrop(tileIndex, farm.getGame().getType().indexOf(this.type));
            this.objectCoins += yield[0];
            this.addExp(yield[1]);
            System.out.println("| ObjectCoins gained: " + yield[0]);
            System.out.println("| Experience gained: " + yield[1]);
        }
    }

    /**
     * Advance day.
     */
    public void advanceDay() {
        farm.ageLot();
    }

    private static final int REGISTER_UP_OK = 0;
    private static final int REGISTER_UP_ERR_INSUFFICIENT_OBJECTCOINS = 1;
    private static final int REGISTER_UP_ERR_INSUFFICIENT_LEVEL = 2;
    private static final int REGISTER_UP_ERR_MAX_LEVEL = 3;

    /**
     * Checks if the player can register to a higher farmer type
     *
     * @return 0 if the player can register. Otherwise, a positive number representing an error code
     */
    private int canRegisterUp() {
        FarmerType zType = this.farm.getGame().getNextFarmerType(this.type);

        if(zType != null)
            if (experience.getLevel() >= zType.getLevelReq())
                if(objectCoins >= zType.getRegistrationFee())
                    return REGISTER_UP_OK;
                else
                    return REGISTER_UP_ERR_INSUFFICIENT_OBJECTCOINS;
            else
                return REGISTER_UP_ERR_INSUFFICIENT_LEVEL;
        else
            return REGISTER_UP_ERR_MAX_LEVEL;
    }

    /**
     * Attempts to register up.
     */
    public void registerUp() {
        switch (canRegisterUp()) {
            case REGISTER_UP_OK:
                this.type = farm.getGame().getNextFarmerType(this.type);
                this.objectCoins -= this.type.getRegistrationFee();
                System.out.println("\n| ObjectCoins expended: " + this.type.getRegistrationFee());
                System.out.println("...you are now a " + this.type.getName());
                break;
            case REGISTER_UP_ERR_INSUFFICIENT_OBJECTCOINS:
                farm.getGame().throwInsufficientObjectCoins();
                break;
            case REGISTER_UP_ERR_INSUFFICIENT_LEVEL:
                farm.getGame().throwRegisterError();
                break;
            case REGISTER_UP_ERR_MAX_LEVEL:
                farm.getGame().throwMaxFarmerTypeError();
                break;
        }
    }

    /**
     * Checks if the game is over. If the game is over, the player is asked if it wants to play again
     *
     * @param sc the Scanner to get input from
     * @return 0 if the game has not ended. Otherwise, a non-zero number from the player containing their response
     */
    public int end(Scanner sc) {
        if(farm.endGame(this.objectCoins, this.type.getSeedCostReduction())) {
            int ENDD = 0;

            while (ENDD == 0) {
                System.out.print("\n| input 1 to play again: ");
                ENDD = sc.nextInt();
            }
            return ENDD;
        }
            
        return 0;
    }

}