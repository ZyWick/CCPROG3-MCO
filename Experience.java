public class Experience {
    private double exp = 0.0;
    private int level = 0;

    public Experience() {
    }

    public void addExp(double d) {
        int prev = level;
        exp += d;
        level = (int)(exp / 100);

        if (prev < level)
            System.out.println("\n...player leveled up!");
    }

    public double getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }
} 
