public class Experience {
    private double exp = 0.0;
    private int level = 0;

    public Experience() {
    }


    public void addExp(double d) {
        exp += d;
        level = (int)(exp / 100);
    }

    public double getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }
} 
