import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TileView extends JButton {
    public static final int TILE_WIDTH = 40;
    public static final int TILE_HEIGHT = 40;
    public static final int TILE_SPACING = 10;

    private Coordinates coordinates;
    public TileView(Coordinates coordinates){
        super();

        this.coordinates = coordinates;

        this.setPreferredSize(new Dimension(TILE_WIDTH, TILE_HEIGHT));
        this.setBackground(new Color(0x57412F));
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
