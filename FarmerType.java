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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets level req.
     *
     * @return the level req
     */
    public int getLevelReq() {
        return this.levelReq;
    }

    /**
     * Gets bonus earning.
     *
     * @return the bonus earning
     */
    public int getBonusEarning() {
        return this.bonusEarning;
    }

    /**
     * Gets seed cost reduction bonus.
     *
     * @return the seed cost reduction bonus
     */
    public int getSeedCostReduction() {
        return this.seedCostReduction;
    }

    /**
     * Gets water bonus increase.
     *
     * @return the water bonus increase
     */
    public int getWaterBonusIncrease() {
        return this.waterBonusIncrease;
    }

    /**
     * Gets fertilizer bonus increase.
     *
     * @return the fertilizer bonus increase
     */
    public int getFertilizerBonusIncrease() {
        return this.fertilizerBonusIncrease;
    }

    /**
     * Gets registration fee.
     *
     * @return the registration fee
     */
    public int getRegistrationFee() {
        return this.registrationFee;
    }
   
}
