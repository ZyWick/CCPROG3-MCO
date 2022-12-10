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

    /** gets stats of the player
     * @return PlayerStats of the player
     */
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

    /**Adds to objectCoins of the player.
     * @param change amount of objectCoins to add
     */
    private void addObjectCoins (double change) {
        this.objectCoins += change;
    }

 
    /** lets the player use tools on the tiles of MyFarm and adds the yield of the tool to player stats
     * @param coordinates the coordinates of the tile
     * @param tool the chosen tool object 
     */
    public void useTool (Coordinates coordinates, FarmTools tool) {
        double[] yield = tool.useTool(farm, coordinates);
            
        addObjectCoins(yield[0] * -1);
        addExp(yield[1]);
        System.out.println("| ObjectCoins expended: " + yield[0]);
        System.out.println("| Experience gained: " + yield[1]);
    }
  
    /** lets the player plant crops through MyFarm and deducts objectCoins from the chosen seed cost amount 
     * @param coordinates the coordinates of the tile 
     * @param seedIndex the index of the chosen seed 
     */
    public void plantCrop (Coordinates coordinates, int seedIndex) {
        int cost = farm.plantCrop(coordinates, seedIndex);
        addObjectCoins((cost + this.type.getSeedCostReduction()) * -1); 
        System.out.println("| ObjectCoins expended: " + (cost + this.getType().getSeedCostReduction()));
    }

    /** harversts the crop on the tile
     * @param coordinates the coordinates of the tile
     */
    public String harvestCrop (Coordinates coordinates) {
            double[] yield = farm.harvestCrop(coordinates, farm.getGame().getType().indexOf(this.getType()));
            addObjectCoins(yield[0]);
            addExp(yield[1]);

            String result = "";
            result +=    "ObjectCoins gained: " + yield[0];
            result += " | Experience gained: "  + yield[1];
            result += " | Produce harvested: " + (int)yield[2];

            return result;
    }

    /**
     * Advance day.
     */
    public void advanceDay() {
        farm.ageLot();
    }

    /** checks if the player can afford a seed
     * @param seedCost the cost of a seed
     * @return true if the player can afford the seed, otherwise, false
     */
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

    public boolean checkLevelReq(FarmerType zType) {
        boolean result = false;

        if (experience.getLevel() >= zType.getLevelReq())
            result = true;

        return result;
    }

    /** 
     * @return string feedback
     */
    public void registerUp() {
        this.type = farm.getGame().getNextFarmerType(this.type);
        this.objectCoins -= this.type.getRegistrationFee();
    }

    /**
     * Checks if the game is over.
     *
     * @return false if the game has not ended. Otherwise, true
     */
    public boolean endGame() {
        return farm.endGame(this.objectCoins, this.type.getSeedCostReduction());
    }

    /** gets the current farmer type of the player
     * @return the current farmer type of the player
     */
    public FarmerType getType() {
        return type;
    }


    /** gets the amount of objectCoins the player has
     * @return the amount of objectCoins the player has
     */
    public double getObjectCoins(){
        return this.objectCoins;
    }

    /** gets the tools of the player
     * @return the list of tool of the player
     */
    public ArrayList<FarmTools> getTools() {
        return this.tools;
    }

}