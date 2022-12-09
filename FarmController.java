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

                updateFarmView();
            }

            @Override
            public void onMessageUseTool(Coordinates coordinates, String toolName) {
                System.out.println("Use " + toolName + " at coordinate " + coordinates);

                updateFarmView();
            }

            @Override
            public void onMessageHarvestCrop(Coordinates coordinates) {
                System.out.println("Harvest at coordinate " + coordinates);

                updateFarmView();
            }

            @Override
            public void onMessageRegisterFarmer() {
                System.out.println("Player wants to register");
                try {
                    player.registerUp();
                } catch(Exception e) {
                    farmView.reportError(e);
                }

                updateFarmView();
            }

            @Override
            public ArrayList<FarmSeeds> requestFarmSeedsWithBonuses() {
                return null;
            }

            @Override
            public String requestTileStatus(Coordinates coordinates) {
                return farm.displayTileStatus(coordinates);
            }
        });

        updateFarmView();
        //farmView.reportError(new Exception("this is a test error"));
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
