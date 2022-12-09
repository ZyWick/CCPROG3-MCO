import java.util.ArrayList;

public class FarmSimulatorGUI{
    public static void main (String[] args) {
        FarmView farmView = new FarmView();
        MyFarm farm = new MyFarm();
        Player player = new Player(farm);
        farmView.setOnTileMessageListener(new OnViewMessageListener() {
            @Override
            public void onMessagePlant(Coordinates coordinates, String seedName) {
                System.out.println("Plant " + seedName + " at coordinate " + coordinates);
            }

            @Override
            public void onMessageUseTool(Coordinates coordinates, String toolName) {
                System.out.println("Use " + " at coordinate " + coordinates);
            }

            @Override
            public void onMessageHarvestCrop(Coordinates coordinates) {
                System.out.println("Harvest at coordinate " + coordinates);
            }

            @Override
            public void onMessageRegisterFarmer() {
                System.out.println("Player wants to register");
                try {
                    player.registerUp();
                } catch(Exception e) {
                    farmView.reportError(e);
                }
            }

            @Override
            public ArrayList<FarmSeeds> requestFarmSeedsWithBonuses() {
                return null;
            }
        });

        farmView.setPlayerStats(player.getPlayerStats());
        //farmView.reportError(new Exception("this is a test error"));
    }

}
