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
     * @param levelReq                the level requirement
     * @param bonusEarning            the amount of bonus earnings per produce
     * @param seedCostReduction       the amount of seed cost reduction bonus
     * @param waterBonusIncrease      the amount of water bonus limit increase
     * @param fertilizerBonusIncrease the amount of fertilizer bonus limit increase
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
     * Returns the name of the Farmer Type
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the level requirement for the Farmer Type
     *
     * @return the level requirement
     */
    public int getLevelReq() {
        return this.levelReq;
    }

    /**
     * Returns the amount of bonus earnings per produce for the Farmer Type
     *
     * @return the amount of bonus earnings
     */
    public int getBonusEarning() {
        return this.bonusEarning;
    }

    /**
     * Returns the amount of seed cost reduction bonus for the Farmer Type
     *
     * @return the amount of seed cost reduction bonus
     */
    public int getSeedCostReduction() {
        return this.seedCostReduction;
    }

    /**
     * Returns the amount of water bonus limit increase for the Farmer Type
     *
     * @return the amount of water bonus limit increase
     */
    public int getWaterBonusIncrease() {
        return this.waterBonusIncrease;
    }

    /**
     * Returns the amount of fertilizer bonus limit increase for the Farmer Type
     *
     * @return the amount of fertilizer bonus limit increase
     */
    public int getFertilizerBonusIncrease() {
        return this.fertilizerBonusIncrease;
    }

    /**
     * Returns the registration fee for the Farmer Type
     *
     * @return the registration fee
     */
    public int getRegistrationFee() {
        return this.registrationFee;
    }
   
}
