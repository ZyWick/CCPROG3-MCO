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
     * @param waterLimit          the water bonus limit
     * @param fertilizerNeeds     the fertilizer needs
     * @param fertilizerLimit     the fertilizer bonus limit
     * @param minProductsProduced the minimum amount of products produced
     * @param maxProductsProduced the maximum amount of products produced
     * @param seedCost            the seed cost
     * @param sellingPrice        the selling price
     * @param expYield            the experience gained by harvesting
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
     * Gets the name of the seed
     *
     * @return the name of the seed
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the type of the seed. The type can affect the needs and earnings of the crop
     *
     * @return the crop type
     */
    public String getCropType() {
        return this.cropType;
    }

    /**
     * Gets the harvest time of the seed
     *
     * @return the harvest time
     */
    public int getHarvestTime() {
        return this.harvestTime;
    }

    /**
     * Gets the minimum amount of water the crop needs to become harvestable
     *
     * @return the amount of water the crop needs
     */
    public int getWaterNeeds() {
        return this.waterNeeds;
    }

    /**
     * Gets the maximum amount of times a crop can be watered for additional earnings
     *
     * @return the water bonus limit
     */
    public int getWaterLimit() {
        return this.waterLimit;
    }

    /**
     * Gets the minimum amount of fertilizer the crop needs to become harvestable
     *
     * @return the amount of fertilizer the crop needs
     */
    public int getFertilizerNeeds() {
        return this.fertilizerNeeds;
    }

    /**
     * Gets the maximum amount of times a crop can be fertilized for additional earnings
     *
     * @return the fertilizer bonus limit
     */
    public int getFertilizerLimit() {
        return this.fertilizerLimit;
    }

    /**
     * Gets minimum amount of products produced when harvesting the crop
     *
     * @return the minimum amount of products produced
     */
    public int getMinProductsProduced() {
        return this.minProductsProduced;
    }

    /**
     * Gets maximum amount of products produced when harvesting the crop
     *
     * @return the maximum amount of products produced
     */
    public int getMaxProductsProduced() {
        return this.maxProductsProduced;
    }

    /**
     * Gets the cost of the seed
     *
     * @return the seed cost
     */
    public int getSeedCost() {
        return this.seedCost;
    }

    /**
     * Gets the base selling price of each piece produced
     *
     * @return the selling price
     */
    public int getSellingPrice() {
        return this.sellingPrice;
    }

    /**
     * Gets the amount of experience gained from harvesting the crop
     *
     * @return the experience yield
     */
    public double getExpYield() {
        return this.expYield;
    }

}
