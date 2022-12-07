// read only coordinates class
public class Coordinates {
    private int row, col;
    public Coordinates(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Coordinates)
            return this.row == ((Coordinates)o).row && this.col == ((Coordinates)o).col;
        else
            return false;
    }

    @Override
    public String toString() {
        return "(row: " + row + " col: " + col + ")";
    }
}
