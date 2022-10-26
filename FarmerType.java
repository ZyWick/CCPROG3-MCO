public class FarmerType {
    private String name;
    private int levelReq;
    private int bonusEarning;
    private int seedCostReduction;
    private int waterBonusIncrease;
    private int fertilizerBonusIncrease;
    private int registrationFee;

    public FarmerType(String name, int levelReq, int bonusEarning, int seedCostReduction, int waterBonusIncrease, int fertilizerBonusIncrease, int registrationFee) {
        this.name = name;
        this.levelReq = levelReq;
        this.bonusEarning = bonusEarning;
        this.seedCostReduction = seedCostReduction;
        this.waterBonusIncrease = waterBonusIncrease;
        this.fertilizerBonusIncrease = fertilizerBonusIncrease;
        this.registrationFee = registrationFee;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getLevelReq() {
        return this.levelReq;
    }

    public int getBonusEarning() {
        return this.bonusEarning;
    }

    public int getSeedCostReduction() {
        return this.seedCostReduction;
    }

    public int getWaterBonusIncrease() {
        return this.waterBonusIncrease;
    }

    public int getFertilizerBonusIncrease() {
        return this.fertilizerBonusIncrease;
    }

    public int getRegistrationFee() {
        return this.registrationFee;
    }
   
}
