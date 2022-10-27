import java.util.ArrayList;

public class MyFarm {
    private ArrayList<Tile> lot;
    private System god;

    public MyFarm() {
        int x, y;

        for(x = 1; x <=5; x++)
            for(y = 1; y <= 10; y++) {
                //if indicated
                lot.add(new Tile(false, new coordinates(x, y)));
                lot.add(new Tile(true, new coordinates(x, y)));
            }
    }



    
}
