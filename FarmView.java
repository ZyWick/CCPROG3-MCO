import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FarmView {
    private JFrame frame;
    private JPanel baseCanvas;

    private StatsPanel statsPanel;
    private TilePanel tilePanel;
    private OnViewMessageListener messageListener;

    public FarmView() {
        frame = new JFrame("Farm");

        baseCanvas = new JPanel();
        baseCanvas.setBackground(new Color(0x477A1E));
        baseCanvas.setLayout(new GridBagLayout());


        frame.add(baseCanvas);

        GridBagConstraints c = new GridBagConstraints();

        statsPanel = new StatsPanel();

        // (0, 0) with height of 1 row
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
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
        c.fill = GridBagConstraints.BOTH;
        baseCanvas.add(tilePanel, c);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1024, 768));

        // display it
        frame.pack();
        frame.setVisible(true);

        OnTileClickListener listener = new OnTileClickListener() {
            @Override
            public void onClick(Coordinates coordinates) {
                System.out.println("Recv click from " + coordinates);
            }
        };

        tilePanel.setOnTileClickListener(listener);
    }

    public void setOnTileMessageListener(OnViewMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void setPlayerStats(PlayerStats stats) {
        statsPanel.setPlayerStats(stats);
    }

    public void reportError(Exception e){
        JOptionPane.showMessageDialog(frame, e.getMessage());
    }
}
