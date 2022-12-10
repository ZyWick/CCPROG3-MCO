public class RootCrop extends FarmSeeds{
    
    /** Creates a RootCrop object
     * 
     * @param name                the name
     * @param cropType            the crop type
     * @param harvestTime         the harvest time
     * @param waterNeeds          the minimum amount water needed
     * @param waterLimit          the water bonus limit
     * @param fertilizerNeeds     the minimum amount of fertilizer needed
     * @param fertilizerLimit     the fertilizer bonus limit
     * @param minProductsProduced the minimum amount of products produced
     * @param maxProductsProduced the maximum amount of products produced
     * @param seedCost            the seed cost
     * @param sellingPrice        the selling price
     * @param expYield            the experience gained by harvesting
     */
    public RootCrop(String name, /*String cropType,*/ int harvestTime, int waterNeeds, int waterLimit, int fertilizerNeeds, int fertilizerLimit, int minProductsProduced, int maxProductsProduced, int seedCost, int sellingPrice, double expYield) {
        super(name, "Root Crop", harvestTime, waterNeeds, waterLimit, fertilizerNeeds, fertilizerLimit, minProductsProduced, maxProductsProduced, seedCost, sellingPrice, expYield);
    }

}
