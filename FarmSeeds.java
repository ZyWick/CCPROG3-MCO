/**
 * The type Farm seeds.
 */
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

    /**
     * Instantiates a new Farm seeds.
     *
     * @param name                the name
     * @param cropType            the crop type
     * @param harvestTime         the harvest time
     * @param waterNeeds          the water needs
     * @param waterLimit          the water limit
     * @param fertilizerNeeds     the fertilizer needs
     * @param fertilizerLimit     the fertilizer limit
     * @param minProductsProduced the min products produced
     * @param maxProductsProduced the max products produced
     * @param seedCost            the seed cost
     * @param sellingPrice        the selling price
     * @param expYield            the exp yield
     */
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

    /**
     * Gets the name of the crop.
     *
     * @return the name of the crop
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets crop type.
     *
     * @return the crop type
     */
    public String getCropType() {
        return this.cropType;
    }

    /**
     * Gets harvest time.
     *
     * @return the harvest time
     */
    public int getHarvestTime() {
        return this.harvestTime;
    }

    /**
     * Gets water needs.
     *
     * @return the water needs
     */
    public int getWaterNeeds() {
        return this.waterNeeds;
    }

    /**
     * Gets water limit.
     *
     * @return the water limit
     */
    public int getWaterLimit() {
        return this.waterLimit;
    }

    /**
     * Gets fertilizer needs.
     *
     * @return the fertilizer needs
     */
    public int getFertilizerNeeds() {
        return this.fertilizerNeeds;
    }

    /**
     * Gets fertilizer limit.
     *
     * @return the fertilizer limit
     */
    public int getFertilizerLimit() {
        return this.fertilizerLimit;
    }

    /**
     * Gets min products produced.
     *
     * @return the min products produced
     */
    public int getMinProductsProduced() {
        return this.minProductsProduced;
    }

    /**
     * Gets max products produced.
     *
     * @return the max products produced
     */
    public int getMaxProductsProduced() {
        return this.maxProductsProduced;
    }

    /**
     * Gets seed cost.
     *
     * @return the seed cost
     */
    public int getSeedCost() {
        return this.seedCost;
    }

    /**
     * Gets selling price.
     *
     * @return the selling price
     */
    public int getSellingPrice() {
        return this.sellingPrice;
    }

    /**
     * Gets the experience yield.
     *
     * @return the experience yield
     */
    public double getExpYield() {
        return this.expYield;
    }

}
