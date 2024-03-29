import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.exit;

public class FarmController {
    private FarmView farmView;
    private MyFarm farm;
    private Player player;
    private Coordinates farmSize;

    /**
     * constructor for FarmController
     */
    public FarmController() {
        init();
    }

    /**
     * initiates farm controller
     */
    public void init() {
        int row, col;
        try {
            Path farmSizePath = Path.of("farmSize.txt");
            String farmSize = Files.readString(farmSizePath);
            String[] rc = farmSize.split(" ");
            row = Integer.parseInt(rc[0]);
            col = Integer.parseInt(rc[1]);
        } catch (IOException e) {
            System.out.println("Failed to read farmSize cfg");
            row = 5;
            col = 10;
        }
        farmSize = new Coordinates(row, col);


        farm = new MyFarm(farmSize);
        player = new Player(farm);

        ArrayList<String> toolsList = new ArrayList<>();
        for (FarmTools tool : player.getTools()) {
            toolsList.add(tool.getName());
        }
        farmView = new FarmView(farmSize, toolsList);

        // NOTE: actions that cause tiles/stats to change must call updateFarmView
        farmView.setOnTileMessageListener(new OnViewMessageListener() {
            @Override
            public void onMessagePlant(Coordinates coordinates, String seedName) {
                System.out.println("Plant " + seedName + " at coordinate " + coordinates);
                
                int error = 0;
                for (FarmSeeds seed : farm.getGame().getSeeds())
                    if (seed.getName().equals(seedName)) {
                        FarmSeeds choice = seed;

                        if (choice != null) {
                            error = farm.checkPlantInTile(coordinates, choice);
                            if(error == 0){
                                if (player.canAffordSeed(choice.getSeedCost()))
                                    player.plantCrop(coordinates, choice);
                                else
                                    error = 3; //game.throwInsufficientObjectCoins();
                            }
                        } 
                    }
                if (error != 0)
                    farmView.reportFeedback(farm.getGame().throwPlantError(error));
                else
                    farmView.reportFeedback(seedName + " successfully planted");

                updateFarmView();
            }

            @Override
            public void onMessageUseTool(Coordinates coordinates, String toolName) {
                System.out.println("Use " + toolName + " at coordinate " + coordinates);
                FarmTools choice = null;
                for (FarmTools tool : player.getTools())
                        if (tool.getName().equals(toolName))
                            choice = tool;

                if (choice != null) {
                    int error = 0;
                    if (player.getObjectCoins() >= choice.getUsageCost())
                        error = choice.canUseTool(farm, coordinates);
                    else
                        error = 6;

                    if (error == 0) {
                        player.useTool(coordinates, choice);
                        farmView.reportFeedback(toolName + " used successfully");
                    } else {
                        farmView.reportFeedback(farm.getGame().throwToolError(error));
                    }
                } else
                    farmView.reportFeedback(farm.getGame().throwOutOfBoundsError());

                updateFarmView();
            }

            @Override
            public void onMessageHarvestCrop(Coordinates coordinates) {
                System.out.println("Harvest at coordinate " + coordinates);
                farmView.reportFeedback(player.harvestCrop(coordinates));

                updateFarmView();
            }

            @Override
            public void onMessageRegisterFarmer() {
                System.out.println("Player wants to register");
                String result = "";
                FarmerType zType = farm.getGame().getNextFarmerType(player.getType());

                if(zType != null) {
                    if (player.checkLevelReq(zType)) {
                        if (player.getObjectCoins() >= zType.getRegistrationFee()) {
                            player.registerUp();
                            result = "\n...you are now a " + player.getType().getName();
                            result += "| ObjectCoins expended: " + player.getType().getRegistrationFee();
                        } else {
                            result = farm.getGame().throwInsufficientObjectCoins();
                        }
                    } else {
                        result = farm.getGame().throwRegisterError();
                    }
                } else {
                    result = farm.getGame().throwMaxFarmerTypeError();
                }

                farmView.reportFeedback(result);

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
                                                        seed.getWaterLimit() + type.getFertilizerBonusIncrease(),
                                                        seed.getFertilizerNeeds(),
                                                        seed.getFertilizerLimit() + type.getFertilizerBonusIncrease(),
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

            @Override
            public void onMessageAdvanceDay() {
                player.advanceDay();
                farmView.reportFeedback("...success, a day has passed");

                updateFarmView();
            }

            @Override
            public void requestViewUpdate() {
                updateFarmView();
            }
        });

        updateFarmView();
        farmView.reportFeedback("welcome");
    }

    /**
     * updates the view of the farm
     */
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
        for (int i = 0; i<farmSize.getRow(); i++) {
            for (int j=0; j<farmSize.getCol(); j++) {
                states.put(new Coordinates(i, j), farm.getTileState(new Coordinates(i, j)));
            }
        }
        farmView.updateTileStates(states);
    }
}
