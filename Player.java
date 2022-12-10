import java.util.Scanner;
import java.util.ArrayList;

/**
 * The Player is the user that controls the farm
 */
public class Player {
    private double objectCoins = 100.0;
    private Experience experience;
    private MyFarm farm;
    private FarmerType type;
    private ArrayList<FarmTools> tools = new ArrayList<FarmTools>();
    /**
     * Instantiates the Player.
     *
     * @param farm an existing MyFarm
     */
    public Player(MyFarm farm) {
        this.experience = new Experience();
        this.farm = farm;
        this.type = farm.getGame().getType().get(0);
        tools.add(new Plow());
        tools.add(new WateringCan());
        tools.add(new Fertilizer());
        tools.add(new Pickaxe());
        tools.add(new Shovel());
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
    public PlayerStats getPlayerStats() {
        FarmerType zType = null;
        switch (canRegisterUp()) {
            case REGISTER_UP_OK:
            case REGISTER_UP_ERR_INSUFFICIENT_OBJECTCOINS:
                zType = farm.getGame().getNextFarmerType(type);
        }
        return new PlayerStats(type.getName(), this.objectCoins,
                this.experience.getExp(), this.experience.getLevel(), this.farm.getGame().getDay(), zType);
    }

    /**
     * Displays the list of available game tasks and obtains the player's choice
     * @param sc the Scanner to get input from
     * @return an int representing the player's choice
     */
    public int whatCanIDo(Scanner sc) {
        farm.getGame().displayGameMoves();
        return sc.nextInt();
    }

    /**
     * Displays the player's farm
     */
    public void thisIsMyFarm () {
        farm.display();
    }

    /**
     * Displays a legend to explain the symbols shown in the player's farm by {@link #thisIsMyFarm()}
     */
    public void whatIsThat () {
        farm.getGame().displayLotLegend();
    }

    // /**
    //  * Displays the list of eligible tools and asks the player to pick a tool
    //  * @param sc the Scanner to get input from
    //  * @param tileIndex the tile index
    //  * @return an int representing the player's choice
    //  */
    // private int getToolChoice(Scanner sc, Coordinates tileIndex) {
    //     int choice;
    //     System.out.println("\nWhich tool do you want to use?");
    //     farm.displayTools(tileIndex, this.objectCoins);
    //     System.out.print("Choice: ");
    //     choice = sc.nextInt();

    //     return choice;
    // }

    // /**
    //  * Displays a list of seeds that are available and asks the player to pick from one of them
    //  * @param sc the Scanner to get input from
    //  * @return an int representing the player's choice
    //  */
    // private int getSeedChoice(Scanner sc) {
    //     int choice;
    //     System.out.println("\nWhich seed do you want to plant?");
    //     farm.displaySeeds(this.objectCoins, this.type.getSeedCostReduction());
    //     System.out.print("Choice: ");
    //     choice = sc.nextInt();

    //     return choice;
    // }

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
        // Coordinates tileIndex = getTileIndex(sc);
        /*Coordinates tileIndex = 0;
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
            }*/
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


    private void addObjectCoins (double change) {
        this.objectCoins += change;
    }


    /**
     * Asks player to choose a tool, then uses it on the specific tile index.
     * @param sc the Scanner to get input from
     * @param tileIndex the tile index
     */
    public void useTool (Coordinates coordinates, FarmTools tool) {
        double[] yield = tool.useTool(farm, coordinates);
            
        addObjectCoins(yield[0] * -1);
        addExp(yield[1]);
        System.out.println("| ObjectCoins expended: " + yield[0]);
        System.out.println("| Experience gained: " + yield[1]);
    }

    /**
     * Prompts the player to pick a seed, then attempts to plant a crop at a specific tile index
     * @param sc the Scanner to get input from
     * @param tileIndex the tile index
     */
    public void plantCrop (Coordinates coordinates, int seedIndex) {
        int cost = farm.plantCrop(coordinates, seedIndex);
        addObjectCoins((cost + this.type.getSeedCostReduction()) * -1); 
        System.out.println("| ObjectCoins expended: " + (cost + this.getType().getSeedCostReduction()));
    }

    // /**
    //  * Attempts to harvest a crop at a specific tile index
    //  * @param tileIndex the tile index
    //  */
    public void harvestCrop (Coordinates coordinates) {
            double[] yield = farm.harvestCrop(coordinates, farm.getGame().getType().indexOf(this.getType()));
            addObjectCoins(yield[0]);
            addExp(yield[1]);
            System.out.println("| ObjectCoins gained: " + yield[0]);
            System.out.println("| Experience gained: " + yield[1]);
    }

    /**
     * Advance day.
     */
    public void advanceDay() {
        farm.ageLot();
    }

    public boolean canAffordSeed(int seedCost) {
        if (this.objectCoins >= seedCost + this.type.getSeedCostReduction())
            return true;

        return false;
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
    public String registerUp() {
        String result = "";
        switch (canRegisterUp()) {
            case REGISTER_UP_OK:
                this.type = farm.getGame().getNextFarmerType(this.type);
                this.objectCoins -= this.type.getRegistrationFee();
                result = "\n...you are now a " + this.type.getName();
                result += "| ObjectCoins expended: " + this.type.getRegistrationFee();
                break;
            case REGISTER_UP_ERR_INSUFFICIENT_OBJECTCOINS:
                result = farm.getGame().throwInsufficientObjectCoins();
                break;
            case REGISTER_UP_ERR_INSUFFICIENT_LEVEL:
                result = farm.getGame().throwRegisterError();
                break;
            case REGISTER_UP_ERR_MAX_LEVEL:
                result = farm.getGame().throwMaxFarmerTypeError();
                break;
        }

        return result;
    }

    /**
     * Checks if the game is over.
     *
     * @return false if the game has not ended. Otherwise, true
     */
    public boolean endGame() {
        return farm.endGame(this.objectCoins, this.type.getSeedCostReduction());
    }

    public FarmerType getType() {
        return type;
    }

    public double getObjectCoins(){
        return this.objectCoins;
    }

    public ArrayList<FarmTools> getTools() {
        return this.tools;
    }

}