public class Experience {
    private double exp = 0.0;
    private int level = 0;

    /**
     *
     * Initializes an experience counter
     */
    public Experience() {
    }

    /**
     * Adds experience to the experience counter
     *
     * @param expToAdd number of experience points to add
     */
    public void addExp(double expToAdd) {
        this.exp += expToAdd;
        this.level = (int)(this.exp / 100);
    }

    /**
     * Returns the number of experience points
     *
     * @return the number of experience points
     */
    public double getExp() {
        return this.exp;
    }

    /**
     * Returns the level.
     * The level is calculated from the number of experience points with the formula level = floor(exp / 100)
     *
     * @return the level
     */
    public int getLevel() {
        return this.level;
    }
} 
