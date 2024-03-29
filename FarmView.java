import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FarmView {
    private JFrame frame;
    private JPanel baseCanvas;

    private JLabel statsPanel, feedbackPanel;
    private TilePanel tilePanel;
    private JButton registerFarmer;
    private OnViewMessageListener messageListener;

    private ArrayList<String> toolsList;

    /**
     * Instantiates a FarmView
     * @param farmSize size of the farm
     * @param toolsList list of tools the FarmView can display
     */
    public FarmView(Coordinates farmSize, ArrayList<String> toolsList) {
        this.toolsList = toolsList;
        frame = new JFrame("Farm");

        baseCanvas = new JPanel();
        baseCanvas.setBackground(new Color(0xB99C6B));
        baseCanvas.setLayout(new GridBagLayout());


        frame.add(baseCanvas);

        GridBagConstraints c = new GridBagConstraints();

        statsPanel = new JLabel();
        feedbackPanel = new JLabel();

        // (0, 0) with height of 1 row
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        //c.fill = GridBagConstraints.BOTH;
        baseCanvas.add(statsPanel, c);

        tilePanel = new TilePanel(farmSize.getRow(), farmSize.getCol(), new Dimension(1024, 768));

        baseCanvas.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                tilePanel.rescale(new Dimension(e.getComponent().getWidth(), e.getComponent().getHeight()));

                // update all views to account for the potential change in their sizes
                // source: https://stackoverflow.com/a/7630604
                SwingUtilities.updateComponentTreeUI(frame);

                // icons need to be redrawn and rescaled when the scaling changes
                // messageListener is null at the constructor
                if(messageListener != null)
                    messageListener.requestViewUpdate();
            }

        });

        // (0, 1) with height of 10 rows
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        //c.fill = GridBagConstraints.BOTH;
        baseCanvas.add(tilePanel, c);

        // (0, 2)
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = 1;
        //c.fill = GridBagConstraints.BOTH;
        baseCanvas.add(feedbackPanel, c);

        // (0, 3)
        c.gridx = 0;
        c.gridy = 6;
        c.gridheight = 1;
        //c.fill = GridBagConstraints.BOTH;

        JButton advanceDay = new JButton("Advance Day");
        advanceDay.addActionListener(actionEvent -> messageListener.onMessageAdvanceDay());
        baseCanvas.add(advanceDay, c);

        // (0, 3)
        c.gridx = 0;
        c.gridy = 7;
        c.gridheight = 1;
        //c.fill = GridBagConstraints.BOTH;

        registerFarmer = new JButton("Upgrade farmer type");
        registerFarmer.addActionListener(actionEvent -> messageListener.onMessageRegisterFarmer());
        baseCanvas.add(registerFarmer, c);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1024, 768));

        // display it
        frame.pack();
        frame.setVisible(true);

        ActionListener tileClickListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Object source = actionEvent.getSource();

                if(source instanceof TileView) {
                    Coordinates coordinates = ((TileView)source).getCoordinates();

                    System.out.println("Recv click from " + coordinates);
                    showTileMenu(coordinates);
                }

            }
        };

        tilePanel.addTileClickListener(tileClickListener);
    }

    /**
     * Shows the tile menu for a tile
     * @param coordinates coordinates of the tile
     */
    private void showTileMenu(Coordinates coordinates) {
        JPopupMenu menu = new JPopupMenu();

        // LinkedHashMap preserves order of insertion
        LinkedHashMap<String, String> actions = new LinkedHashMap<>();
        actions.put(messageListener.requestTileStatus(coordinates), "info");

        // add non-tools
        actions.put("Plant", "plant");
        actions.put("Harvest", "harvest");

        // add tools
        for (String name : toolsList) {
            actions.put(name, name);
        }

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() instanceof JMenuItem) {
                    JMenuItem entry = (JMenuItem) actionEvent.getSource();

                    String action = actions.get(entry.getText());
                    if(action != null) {
                        switch (action) {
                            case "info":      break;
                            case "plant":     showPlantMenu(coordinates); break;
                            case "harvest":   messageListener.onMessageHarvestCrop(coordinates); break;
                            default:          messageListener.onMessageUseTool(coordinates, action); break;
                        }
                    }
                }
            }
        };

        for(Map.Entry<String, String> hashMapEntry : actions.entrySet()) {
            String menuEntryText = hashMapEntry.getKey();
            JMenuItem menuEntry = new JMenuItem(menuEntryText);
            menuEntry.addActionListener(listener);
            menu.add(menuEntry);
        }

        showMenuAtCursor(menu);
    }

    /**
     * Shows list of seeds available
     * @param coordinates coordinates of the tile
     */
    private void showPlantMenu(Coordinates coordinates) {
        JPopupMenu menu = new JPopupMenu();

        // get seed and seed cost from Player
        ArrayList<FarmSeeds> seeds = messageListener.requestFarmSeedsWithBonuses();
        ArrayList<String> menuEntries = new ArrayList<>();
        HashMap<String, String> translateMenuStringToSeed = new HashMap<>();

        for (FarmSeeds seed : seeds) {
            String menuEntry = "<html>" +
                    seed.getName() + " (" + seed.getCropType() + ")" + "<br>" +
                    "cost: " + seed.getSeedCost() + "<br>" +
                    "</html>";
            menuEntries.add(menuEntry);
            translateMenuStringToSeed.put(menuEntry, seed.getName());
        }

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() instanceof JMenuItem) {
                    String entryName = ((JMenuItem) actionEvent.getSource()).getText();
                    String seedName = translateMenuStringToSeed.get(entryName);

                    if(seedName != null){
                        messageListener.onMessagePlant(coordinates, seedName);
                    }
                }
            }
        };

        for(String menuEntryStr : menuEntries) {
            JMenuItem menuEntry = new JMenuItem(menuEntryStr);
            menuEntry.addActionListener(listener);
            menu.add(menuEntry);
        }

        showMenuAtCursor(menu);
    }

    /**
     * Shows a menu at the user's cursor location
     * @param menu menu to show
     */
    private void showMenuAtCursor(JPopupMenu menu) {
        Point mouseLocation = frame.getMousePosition();
        int x = (int) mouseLocation.getX();
        int y = (int) mouseLocation.getY();

        menu.show(frame, x, y);
    }

    /**
     * Sets the messageListener to use
     * @param messageListener OnViewMessageListener created by the controller
     */
    public void setOnTileMessageListener(OnViewMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    /**
     * Updates the UI using the updated player stats
     * @param stats updated player stats
     */
    public void setPlayerStats(PlayerStats stats) {
        String playerTypeMsg = "";

        if(stats.getEligibleForPlayerType() != null){
            playerTypeMsg = "can register to " + stats.getEligibleForPlayerType().getName() +
                    " (cost: " + stats.getEligibleForPlayerType().getRegistrationFee() + " ObjectCoins)";
            registerFarmer.setVisible(true);
        } else {
            registerFarmer.setVisible(false);
        }

        statsPanel.setText("<html>" +
                "<span style=\"color: #5A2729\"> Type: " + stats.getType() + "</span> <br>" +
                "<span style=\"color: #5A2729\"> Exp: " + stats.getExp() + "</span> <br>" +
                "<span style=\"color: #5A2729\"> Level: " + stats.getLevel() + "</span> <br>" +
                "<span style=\"color: #5A2729\"> Coins: " + stats.getObjectCoins() + "</span> <br>" +
                "<span style=\"color: #5A2729\"> Day: " + stats.getDay() + "</span> <br>" +
                "<span style=\"color: #5A2729\"> " + playerTypeMsg + "</span> <br>" +
                "</html>");
    }

    /**
     * Change the UI of the tiles to account for the change of the tile states
     * @param states the latest state of each tile
     */
    public void updateTileStates(HashMap<Coordinates, TileState> states) {
        tilePanel.updateTileStates(states);
    }

    /**
     * Displays text on the UI
     * @param text text to display, can have HTML formatting
     */
    public void reportFeedback(String text){
        feedbackPanel.setText("<html><span style=\"color: #5A2729\">" + text + "</span></html>");
    }

    /**
     * Shows the game over dialog.
     * Warning: The window will be closed regardless of the user's choice (since the game is over).
     * @return true if user wants to play again, otherwise false
     */
    public boolean endGame() {
        int response = JOptionPane.showConfirmDialog(frame, "Game Over. Do you want to play again?",
                "", JOptionPane.YES_NO_OPTION);

        frame.dispose();

        return response == JOptionPane.YES_OPTION;
    }
}
