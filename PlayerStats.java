public class PlayerStats {
    private String type;
    private double objectCoins;
    private double exp;
    private int level;
    private int day;

    public PlayerStats(String type, double objectCoins, double exp, int level, int day) {
        this.type = type;
        this.objectCoins = objectCoins;
        this.exp = exp;
        this.level = level;
        this.day = day;
    }

    public String getType() {
        return type;
    }

    public double getObjectCoins() {
        return objectCoins;
    }

    public double getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }

    public int getDay() {
        return day;
    }


}
