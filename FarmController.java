import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.exit;

public class FarmController {
    private FarmView farmView;
    private MyFarm farm;
    private Player player;

    public FarmController() {
        init();
    }

    public void init() {
        farmView = new FarmView();
        farm = new MyFarm();
        player = new Player(farm);

        // NOTE: actions that cause tiles/stats to change must call updateFarmView
        farmView.setOnTileMessageListener(new OnViewMessageListener() {
            @Override
            public void onMessagePlant(Coordinates coordinates, String seedName) {
                System.out.println("Plant " + seedName + " at coordinate " + coordinates);
                
                int error = farm.checkPlantInTile(coordinates);
                if(error == 0) {
                    int choice = 999;
                    for (FarmSeeds seed : farm.getGame().getSeeds())
                        if (seed.getName().equals(seedName))
                            choice = farm.getGame().getSeeds().indexOf(seed);

                    error = farm.checkPlantCrop(coordinates, player.getType().getSeedCostReduction(), choice, player.getObjectCoins());
                    if (error == 0) 
                        player.plantCrop(coordinates, choice);
                } 
                if (error != 0)
                    farm.getGame().throwPlantError(error);

                updateFarmView();
            }

            @Override
            public void onMessageUseTool(Coordinates coordinates, String toolName) {
                System.out.println("Use " + toolName + " at coordinate " + coordinates);
                FarmTools choice = null;
                for (FarmTools tool : player.getTools())
                        if (tool.getClass().getSimpleName().equals(toolName))
                            choice = tool;

                if (choice != null) {
                    int error = farm.canUseTool(coordinates, toolName, choice.getUsageCost(), player.getObjectCoins());

                    if (error == 0) {
                        player.useTool(coordinates, choice);

                    } else 
                        farm.getGame().throwToolError(error);
                } else
                    farm.getGame().throwOutOfBoundsError();

                updateFarmView();
            }

            @Override
            public void onMessageHarvestCrop(Coordinates coordinates) {
                System.out.println("Harvest at coordinate " + coordinates);

                int error = farm.checkHarvest(coordinates);
                if (error == 0) {
                    player.harvestCrop(coordinates);
                } else 
                    farm.getGame().throwHarvestError(error);

                updateFarmView();
            }

            @Override
            public void onMessageRegisterFarmer() {
                System.out.println("Player wants to register");
                try {
                    player.registerUp();
                } catch(Exception e) {
                    farmView.reportFeedback(e.getMessage());
                }

                updateFarmView();
            }

            @Override
            public ArrayList<FarmSeeds> requestFarmSeedsWithBonuses() {
                ArrayList<FarmSeeds> modifiedFarmSeeds = new ArrayList<>();
                FarmerType type = player.getType();

                System.out.println("TODO: modify the seed (check if modified)");
                for(FarmSeeds seed : farm.getGame().getSeeds()) {
                    modifiedFarmSeeds.add(new FarmSeeds(seed.getName(),
                                                        seed.getCropType(),
                                                        seed.getHarvestTime(),
                                                        seed.getWaterNeeds(),
                                                        seed.getWaterLimit(),
                                                        seed.getFertilizerNeeds(),
                                                        seed.getFertilizerLimit(),
                                                        seed.getMinProductsProduced(),
                                                        seed.getMaxProductsProduced(),
                                                        seed.getSeedCost() + type.getSeedCostReduction(),
                                                        seed.getSellingPrice() + type.getBonusEarning(),
                                                        seed.getExpYield()));
                }
                return modifiedFarmSeeds;
            }

            @Override
            public String requestTileStatus(Coordinates coordinates) {
                return farm.displayTileStatus(coordinates);
            }
        });

        updateFarmView();
        farmView.reportFeedback("welcome");
    }

    private void updateFarmView() {
        if(player.endGame()){
            // game over
            if(farmView.endGame()){
                // play again
                init();
            } else {
                // exit
                exit(0);
            }
        }

        farmView.setPlayerStats(player.getPlayerStats());

        HashMap<Coordinates, TileState> states = new HashMap<>();
        int row = 5;
        int col = 10;
        for (int i = 0; i<row; i++) {
            for (int j=0; j<col; j++) {
                // get tile condition

                // TODO: replace this
                states.put(new Coordinates(i, j), new TileState("", TileState.ROCK));
            }
        }
        farmView.setTileStates(states);
    }
}
