public class Flower extends FarmSeeds{
    
    public Flower(String name, /*String cropType,*/ int harvestTime, int waterNeeds, int waterLimit, int fertilizerNeeds, int fertilizerLimit, int minProductsProduced, int maxProductsProduced, int seedCost, int sellingPrice, double expYield) {
        super(name, "Flower", harvestTime, waterNeeds, waterLimit, fertilizerNeeds, fertilizerLimit, minProductsProduced, maxProductsProduced, seedCost, sellingPrice, expYield);
    }

    public double getPremiumFactor() {
        return 1.1;
    }

}
