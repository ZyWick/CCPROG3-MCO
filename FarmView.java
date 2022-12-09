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
    private OnViewMessageListener messageListener;

    public FarmView() {
        frame = new JFrame("Farm");

        baseCanvas = new JPanel();
        baseCanvas.setBackground(new Color(0x477A1E));
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

        tilePanel = new TilePanel(5, 10, new Dimension(1024, 768));

        baseCanvas.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                tilePanel.rescale(new Dimension(e.getComponent().getWidth(), e.getComponent().getHeight()));

                // update all views to account for the potential change in their sizes
                // source: https://stackoverflow.com/a/7630604
                SwingUtilities.updateComponentTreeUI(frame);
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

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1024, 768));

        // display it
        frame.pack();
        frame.setVisible(true);

        OnTileClickListener tileClickListener = new OnTileClickListener() {
            @Override
            public void onClick(Coordinates coordinates) {
                System.out.println("Recv click from " + coordinates);
                showTileMenu(coordinates);
            }
        };

        tilePanel.setOnTileClickListener(tileClickListener);
    }

    private void showTileMenu(Coordinates coordinates) {
        JPopupMenu menu = new JPopupMenu();

        // LinkedHashMap preserves order of insertion
        LinkedHashMap<String, String> actions = new LinkedHashMap<>();
        actions.put(messageListener.requestTileStatus(coordinates), "nothing");
        actions.put("Plant", "plant");
        actions.put("Harvest", "harvest");
        actions.put("Plow", "plow");
        actions.put("Water", "water");
        actions.put("Fertilize", "fertilize");
        actions.put("Pickaxe", "pickaxe");
        actions.put("Shovel", "shovel");

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() instanceof JMenuItem) {
                    JMenuItem entry = (JMenuItem) actionEvent.getSource();

                    String action = actions.get(entry.getText());
                    if(action != null) {
                        switch (action) {
                            case "plant": showPlantMenu(coordinates); break;
                            case "harvest":   messageListener.onMessageHarvestCrop(coordinates); break;
                            case "plow":      messageListener.onMessageUseTool(coordinates, "plow"); break;
                            case "water":     messageListener.onMessageUseTool(coordinates, "water"); break;
                            case "fertilize": messageListener.onMessageUseTool(coordinates, "fertilize"); break;
                            case "pickaxe":   messageListener.onMessageUseTool(coordinates, "pickaxe"); break;
                            case "shovel":    messageListener.onMessageUseTool(coordinates, "shovel"); break;
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
    private void showPlantMenu(Coordinates coordinates) {
        JPopupMenu menu = new JPopupMenu();

        // get seed and seed cost from Player
        ArrayList<FarmSeeds> seeds = messageListener.requestFarmSeedsWithBonuses();
        ArrayList<String> menuEntries = new ArrayList<>();
        HashMap<String, String> translateMenuStringToSeed = new HashMap<>();

        for (FarmSeeds seed : seeds) {
            String menuEntry = seed.getName() + " (cost: " + seed.getSeedCost() + ")";
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

    private void showMenuAtCursor(JPopupMenu menu) {
        Point mouseLocation = frame.getMousePosition();
        int x = (int) mouseLocation.getX();
        int y = (int) mouseLocation.getY();

        menu.show(frame, x, y);
    }
    public void setOnTileMessageListener(OnViewMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void setPlayerStats(PlayerStats stats) {
        statsPanel.setText("<html>" +
                "<span style=\"color: #33b5e5\"> Type: " + stats.getType() + "</span> <br>" +
                "<span style=\"color: #33b5e5\"> Exp: " + stats.getExp() + "</span> <br>" +
                "<span style=\"color: #33b5e5\"> Level: " + stats.getLevel() + "</span> <br>" +
                "<span style=\"color: #33b5e5\"> Coins: " + stats.getObjectCoins() + "</span> <br>" +
                "</html>");
    }

    public void setTileStates(HashMap<Coordinates, TileState> states) {
        tilePanel.setTileStates(states);
    }

    public void reportFeedback(String text){
        feedbackPanel.setText("<html><span style=\"color: #ffffff\">" + text + "</span></html>");
    }

    /**
     * Shows the game over dialog
     * @return true if user wants to play again, otherwise false
     */
    public boolean endGame() {
        int response = JOptionPane.showConfirmDialog(frame, "Game Over. Do you want to play again?",
                "", JOptionPane.YES_NO_OPTION);

        return response == JOptionPane.YES_OPTION;
    }
}
