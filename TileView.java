import javax.swing.*;
import java.awt.*;

public class TileView extends JButton {
    public static final int TILE_WIDTH = 40;
    public static final int TILE_HEIGHT = 40;
    public static final int TILE_SPACING = 10;

    private Coordinates coordinates;

    /**
     * Instantiates a TileView
     * It is a button that uses the coordinates of a tile as its unique identifier.
     * @param coordinates coordinates of the tile it represents
     */
    public TileView(Coordinates coordinates){
        super();

        this.coordinates = coordinates;

        this.setPreferredSize(new Dimension(TILE_WIDTH, TILE_HEIGHT));
    }

    /**
     * Get the coordinates the tile represents
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }
}
