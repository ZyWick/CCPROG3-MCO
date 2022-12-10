public class PlayerStats {
    private String type;
    private double objectCoins;
    private double exp;
    private int level;
    private int day;
    private FarmerType eligibleForPlayerType;

    /** creates a PlayerStats instance
     * @param type          the current farmer type of the player
     * @param objectCoins   the amount of objectCoins the player has
     * @param exp           the amount of experience the player has
     * @param level         the level of the player
     * @param day           the current day of the system
     * @param eligibleForPlayerType the farmer type to which the player is eligible to register up
     */
    public PlayerStats(String type, double objectCoins, double exp, int level, int day, FarmerType eligibleForPlayerType) {
        this.type = type;
        this.objectCoins = objectCoins;
        this.exp = exp;
        this.level = level;
        this.day = day;
        this.eligibleForPlayerType = eligibleForPlayerType;
    }

    /** gets the farmer type of the player
     * @return the farmer type of the player
     */
    public String getType() {
        return type;
    }

    /** gets the amount of objectCoins the player has
     * @return the amount of objectCoins the player has
     */
    public double getObjectCoins() {
        return objectCoins;
    }

    /** gets the amount of experience the player has
     * @return the amount of experience the player has
     */
    public double getExp() {
        return exp;
    }

    /** gets the level of the player
     * @return the level of the player
     */
    public int getLevel() {
        return level;
    }

    /** gets the current day of the system
     * @return the current day of the system
     */
    public int getDay() {
        return day;
    }

    /** gets the farmer type to which the player is eligible to register up
     * @return the farmer type to which the player is eligible to register up
     */
    public FarmerType getEligibleForPlayerType() {
        return eligibleForPlayerType;
    }
}
