import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.min;

public class MyFarm {
    private ArrayList<Tile> lot = new ArrayList<Tile>();
    private FarmSystem game = new FarmSystem();

    public MyFarm() {
        int x;

        for(x = 0; x <1; x++)
                //if indicated
                lot.add(new Tile(false));
                //lot.add(new Tile(true));
            
    }

    public void display () {
        int x, y, row = 1, column = 1, tileIndex = 0;

        for(x = 0; x < row; x++) {
            for(y = 0; y < column; y++) 
                System.out.print("* - - - - - ");
            System.out.println('*');

            for(y = 0; y < column; y++)
                System.out.print("|           ");
            System.out.println('|');
                
            for(y = 0; y < column; y++) {
                System.out.print("|     ");
                System.out.print(game.whatToPrint(lot.get(tileIndex)));
                System.out.print("     ");
                tileIndex = tileIndex + 1;
            }
            System.out.println('|');

            for(y = 0; y < column; y++)
                System.out.print("|           ");
            System.out.println('|');
        }        
        for(y = 0; y < column; y++) 
            System.out.print("* - - - - - ");
        System.out.println('*');
    }

    public void displayTools (int tileIndex, int objectCoins) {
        for (FarmTools tool : game.getTools()) {
            if(canUseTool(tool, tileIndex, objectCoins))
                System.out.print("| / | ");
            else
                System.out.print("| x | ");

            System.out.println(game.getTools().indexOf(tool) + " - " + tool.getName());
        }
    }

    public void displaySeeds (int PlayerObjectCoins) {
        for (FarmSeeds seed : game.getSeeds()) {
            if(canUseSeed(seed, PlayerObjectCoins))
                System.out.print("| / | ");
            else
                System.out.print("| x | ");

            System.out.println(game.getSeeds().indexOf(seed) + " - " + seed.getName());
        }
    }
    
    public void ageLot() {    
        game.addDay();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
        for (Tile TheTile : lot) 
            if(TheTile.getSeeds() != null) 
                TheTile.addDay();
    }

    public boolean canUseTool(FarmTools selectedTool, int tileIndex, int objectCoins) {
        Tile TheTile = lot.get(tileIndex);
        if (objectCoins >= selectedTool.getUsageCost()) {
            if (selectedTool.getName().equals("Plow") && TheTile.canPlow())
                return true;
            else if ((selectedTool.getName().equals("Watering Can") || selectedTool.getName().equals("Fertilizer"))
                     && TheTile.canWaterOrFertilize())
                return true;      
            else if (selectedTool.getName().equals("Pickaxe") && TheTile.isRock())
                return true;
            else if (selectedTool.getName().equals("Shovel"))
                return true;
        }       
    
        return false;
    }

    public int canPlantSeed (int tileIndex) {
        if (lot.get(tileIndex).isPlowed() == false) 
            return 2;
        if (lot.get(tileIndex).getSeeds() == null)
            return 3;

        return 1;
    }

    public boolean canUseSeed(FarmSeeds seed, int PlayerObjectCoins) {
        if (PlayerObjectCoins >= seed.getSeedCost())
            return true;

        return false;
    }

    public boolean canHarvest(int tileIndex) {
        Tile TheTile = lot.get(tileIndex);
        if (TheTile.getDay() == TheTile.getSeeds().getHarvestTime() &&
                TheTile.getWaterTimes() >= TheTile.getSeeds().getWaterNeeds() &&
                TheTile.getFertilizerTimes() >= TheTile.getSeeds().getFertilizerNeeds())
            return true;
        
        return false;
    }

    public int getTileIndex (Scanner sc) {
        int x, y, tileIndex;

        System.out.print("\ninput tile coordinates: ");
        x = sc.nextInt();
        y = sc.nextInt();
        tileIndex = (x - 1) * 10 + (y - 1) ;

        return tileIndex;
    }

    public int getSeedChoice(Scanner sc, int tileIndex, int objectCoins) {
        int choice;
        if (lot.get(tileIndex).isPlowed()) {
            System.out.println("\nWhich seed do you want to plant?");
            displaySeeds(objectCoins);
            System.out.print("Choice: ");
            choice = sc.nextInt();
        } else 
            choice = -1;

        return choice;
    }

    public int getToolChoice(Scanner sc, int tileIndex, int objectCoins) {
        int choice;
        System.out.println("\nWhich tool do you want to use?");
        displayTools(tileIndex, objectCoins);
        System.out.print("Choice: ");
        choice = sc.nextInt();

        return choice;
    }

    public int getProductsProduced(int tileIndex) {
        int productsProduced;
        FarmSeeds seeds = lot.get(tileIndex).getSeeds();
        
        productsProduced = ThreadLocalRandom.current().nextInt(seeds.getMinProductsProduced(), seeds.getMaxProductsProduced() + 1);

        return productsProduced;
    }

    public void plowTile(int tileIndex) {
        lot.get(tileIndex).setPlowed(true);
    }

    public void waterTile(int tileIndex) {
        lot.get(tileIndex).addWaterTimes();
    }

    public void fertilizeTile(int tileIndex) {
        lot.get(tileIndex).addFertilizerTimes();   
    }

    public void removeRock(int tileIndex) {
        lot.get(tileIndex).removeRock();
    }

    public void removeWithered (int tileIndex) {
        lot.get(tileIndex).reset();
    }

    public void plantCrop(int tileIndex, int seedIndex) {
        lot.get(tileIndex).setSeeds(game.getSeeds().get(seedIndex));
   } 

   public double[] harvestCrop(int tileIndex, int farmerTypeIndex){
        Tile TheTile = lot.get(tileIndex);
        FarmSeeds TheSeed = TheTile.getSeeds();
        int productsProduced = getProductsProduced(tileIndex);
        double harvestTotal, waterBonus, fertilizerBonus;      
            
        harvestTotal = productsProduced * (TheSeed.getSellingPrice() + game.getType().get(farmerTypeIndex).getBonusEarning());
        waterBonus = harvestTotal * 0.2 * (min(TheTile.getWaterTimes(), TheSeed.getWaterLimit()) - 1);
        fertilizerBonus = harvestTotal * 0.5 * min(TheTile.getFertilizerTimes(), TheSeed.getFertilizerLimit());
        harvestTotal = harvestTotal + waterBonus + fertilizerBonus;

        TheTile.reset();
        double[] yield = new double[2];
        yield[0] = harvestTotal;
        yield[1] = TheSeed.getExpYield();
        
        return yield;
   }

   public int identifyHarvestError(int tileIndex) {
        return lot.get(tileIndex).isWithered();
   }

    public FarmSystem getGame() {
        return this.game;
    }
    
}
