import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FarmSimulatorGUI{
    public static void main (String[] args) {
        FarmView farmView = new FarmView();
        farmView.setOnTileClickListener(new OnTileClickListener() {
            @Override
            public void onClick(Coordinates coordinates) {
                System.out.println("Recv click from " + coordinates);
            }
        });
    }

}
