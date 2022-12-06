import javax.swing.*;
import java.awt.*;

public class TileView extends JButton {
    public static final int TILE_WIDTH = 40;
    public static final int TILE_HEIGHT = 40;
    public static final int TILE_SPACING = 10;
    public TileView(){
        super();

        setPreferredSize(new Dimension(TILE_WIDTH, TILE_HEIGHT));
        setBackground(new Color(0xC28340));
    }
}
