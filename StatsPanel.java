import javax.swing.*;

public class StatsPanel extends JLabel {
    public StatsPanel(){

    }

    public void setPlayerStats(PlayerStats stats) {
        this.setText("<html>" +
                     "<span style=\"color: #33b5e5\"> Type: " + stats.getType() + "</span> <br>" +
                     "<span style=\"color: #33b5e5\"> Exp: " + stats.getExp() + "</span> <br>" +
                     "<span style=\"color: #33b5e5\"> Level: " + stats.getLevel() + "</span> <br>" +
                     "<span style=\"color: #33b5e5\"> Coins: " + stats.getObjectCoins() + "</span> <br>" +
                     "</html>");
    }
}
