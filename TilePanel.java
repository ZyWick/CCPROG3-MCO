import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TilePanel extends JPanel {
    private HashMap<Coordinates, TileView> tileViews;
    private int row, col;

    public TilePanel(int row, int col, Dimension windowDimensions) {
        super();

        GridLayout panelLayout = new GridLayout(row, col, TileView.TILE_SPACING, TileView.TILE_SPACING);
        this.setOpaque(false);
        this.setLayout(panelLayout);

        this.tileViews = new HashMap<>();
        this.row = row;
        this.col = col;


        this.rescale(windowDimensions);

        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                Coordinates coords = new Coordinates(i, j);

                System.out.println("adding tile " + coords);
                TileView tileView = new TileView(coords);
                this.add(tileView);
                this.tileViews.put(coords, tileView);
            }
        }
    }

    // source: https://codereview.stackexchange.com/questions/275971/function-to-resize-an-image-whilst-maintaining-aspect-ratio
    public void rescale(Dimension windowDimensions){
        double original_width = (col * TileView.TILE_WIDTH) +
                (col * (TileView.TILE_SPACING - 1));

        double original_height = (row * TileView.TILE_WIDTH) +
                (row * (TileView.TILE_SPACING - 1));

        double target_width = windowDimensions.width * 0.75;
        double target_height = windowDimensions.height * 0.75;

        double new_width, new_height;

        System.out.println("Orginal size: " + new Dimension((int)original_width, (int)original_height));

        if(target_width / target_height > original_width / original_height) {
            new_width = target_height * original_width / original_height;
            new_height = target_height;
        } else {
            new_width = target_width;
            new_height = target_width * original_height / original_width;
        }

        Dimension d = new Dimension((int)new_width, (int)new_height);
        System.out.println("New size: " + d);

        this.setMinimumSize(d);
        this.setPreferredSize(d);
        this.setMaximumSize(d);
    }

    /*
    In this case, we want to add the listener to the tiles it holds
     */
    public void setOnTileClickListener(OnTileClickListener onTileClickListener) {
        for(Map.Entry<Coordinates, TileView> entry : tileViews.entrySet()) {
            entry.getValue().setOnTileClickListener(onTileClickListener);
        }
    }
}
