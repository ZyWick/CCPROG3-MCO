// read only coordinates class
public class Coordinates {
    private int row, col;

    /**
     * Creates a read-only coordinates object
     * @param row - row position
     * @param col - column position
     */
    public Coordinates(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * Check if two coordinates objects have the same row and col value
     * @param o other object
     * @return true if objects are considered equal, false if not
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Coordinates)
            return this.row == ((Coordinates)o).row && this.col == ((Coordinates)o).col;
        else
            return false;
    }

    /**
     * Calculate the hash of the object
     * @return hash
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * Return the string representation of the coordinates
     * @return String with row and column
     */
    @Override
    public String toString() {
        return "(row: " + row + " col: " + col + ")";
    }

    /**
     * Get the row value
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the column value
     * @return column
     */
    public int getCol() {
        return col;
    }

}
