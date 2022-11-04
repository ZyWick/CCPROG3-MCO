public class FarmSeeds {
    private String name;
    private String cropType;
    private int harvestTime;
    private int waterNeeds;
    private int waterLimit;
    private int fertilizerNeeds;
    private int fertilizerLimit;
    private int minProductsProduced;
    private int maxProductsProduced;
    private int seedCost;
    private int sellingPrice;
    private double expYield;

    public FarmSeeds(String name, String cropType, int harvestTime, int waterNeeds, int waterLimit, int fertilizerNeeds, int fertilizerLimit, int minProductsProduced, int maxProductsProduced, int seedCost, int sellingPrice, double expYield) {
        this.name = name;
        this.cropType = cropType;
        this.harvestTime = harvestTime;
        this.waterNeeds = waterNeeds;
        this.waterLimit = waterLimit;
        this.fertilizerNeeds = fertilizerNeeds;
        this.fertilizerLimit = fertilizerLimit;
        this.minProductsProduced = minProductsProduced;
        this.maxProductsProduced = maxProductsProduced;
        this.seedCost = seedCost;
        this.sellingPrice = sellingPrice;
        this.expYield = expYield;
    }
    
    public String getName() {
        return this.name;
    }

    public String getCropType() {
        return this.cropType;
    }

    public int getHarvestTime() {
        return this.harvestTime;
    }

    public int getWaterNeeds() {
        return this.waterNeeds;
    }

    public int getWaterLimit() {
        return this.waterLimit;
    }

    public int getFertilizerNeeds() {
        return this.fertilizerNeeds;
    }

    public int getFertilizerLimit() {
        return this.fertilizerLimit;
    }
    
    public int getMinProductsProduced() {
        return this.minProductsProduced;
    }

    public int getMaxProductsProduced() {
        return this.maxProductsProduced;
    }

    public int getSeedCost() {
        return this.seedCost;
    }

    public int getSellingPrice() {
        return this.sellingPrice;
    }

    public double getExpYield() {
        return this.expYield;
    }

}
