/**
 * The type Farmer type.
 */
public class FarmerType {
    private String name;
    private int levelReq;
    private int bonusEarning;
    private int seedCostReduction;
    private int waterBonusIncrease;
    private int fertilizerBonusIncrease;
    private int registrationFee;

    /**
     * Creates a new Farmer type.
     *
     * @param name                    the name
     * @param levelReq                the level req
     * @param bonusEarning            the bonus earning
     * @param seedCostReduction       the seed cost reduction
     * @param waterBonusIncrease      the water bonus increase
     * @param fertilizerBonusIncrease the fertilizer bonus increase
     * @param registrationFee         the registration fee
     */
    public FarmerType(String name, int levelReq, int bonusEarning, int seedCostReduction, int waterBonusIncrease, int fertilizerBonusIncrease, int registrationFee) {
        this.name = name;
        this.levelReq = levelReq;
        this.bonusEarning = bonusEarning;
        this.seedCostReduction = seedCostReduction;
        this.waterBonusIncrease = waterBonusIncrease;
        this.fertilizerBonusIncrease = fertilizerBonusIncrease;
        this.registrationFee = registrationFee;
    }

    /**
     * Gets the name of the Farmer Type
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the level requirement for the Farmer Type
     *
     * @return the level requirement
     */
    public int getLevelReq() {
        return this.levelReq;
    }

    /**
     * Gets the amount of bonus earnings per produce for the Farmer Type
     *
     * @return the amount of bonus earnings
     */
    public int getBonusEarning() {
        return this.bonusEarning;
    }

    /**
     * Gets the amount of seed cost reduction bonus for the Farmer Type
     *
     * @return the amount of seed cost reduction bonus
     */
    public int getSeedCostReduction() {
        return this.seedCostReduction;
    }

    /**
     * Gets the amount of water bonus limit increase for the Farmer Type
     *
     * @return the amount of water bonus limit increase
     */
    public int getWaterBonusIncrease() {
        return this.waterBonusIncrease;
    }

    /**
     * Gets the amount of fertilizer bonus limit increase for the Farmer Type
     *
     * @return the amount of fertilizer bonus limit increase
     */
    public int getFertilizerBonusIncrease() {
        return this.fertilizerBonusIncrease;
    }

    /**
     * Gets the registration fee for the Farmer Type
     *
     * @return the registration fee
     */
    public int getRegistrationFee() {
        return this.registrationFee;
    }
   
}
