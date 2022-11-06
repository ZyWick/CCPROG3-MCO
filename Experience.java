public class Experience {
    private double exp = 0.0;
    private int level = 0;

    public Experience() {
    }

    /**
     * Adds experience to the experience counter
     * @param exp number of experience points to add
     */
    public void addExp(double exp) {
        exp += exp;
        level = (int)(exp / 100);
    }

    /**
     * Returns the number of experience points
     * @return the number of experience points
     */
    public double getExp() {
        return exp;
    }

    /**
     * Returns the level.
     * The level is calculated from the number of experience points with the formula level = floor(exp / 100)
     * @return the level
     */
    public int getLevel() {
        return level;
    }
} 
