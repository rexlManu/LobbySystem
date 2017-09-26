package de.rexlmanu.lobby.knocki;

import de.rexlmanu.lobby.Main;
import de.rexlmanu.lobby.methods.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Created by rexlManu on 26.08.2017.
 * Plugin by rexlManu
 * https://rexlGames.de
 * Coded with IntelliJ
 */
public class StatsAPI {

    public static HashMap<Player, StatsAPI> map = new HashMap<>();

    private Player p;
    private String uuid;
    private int kills;
    private int deaths;
    private int rank;
    private String kd;

    public StatsAPI(Player p) {
        this.p = p;
        this.uuid = p.getUniqueId().toString().replace("-", "");
        isExisting();
        getStatsFromMySql();
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getRank() {
        return rank;
    }

    public String getKd() {
        return kd;
    }

    public Player getPlayer() {
        return p;
    }

    private void getStatsFromMySql() {
        try {
            this.kills = getInt("Kills");
            this.deaths = getInt("Deaths");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.kd = getKDLayout();
        this.rank = getRanking();
    }

    public StatsAPI updateKills(int kills){
        this.kills = this.kills + kills;
        return this;
    }
    public StatsAPI updateDeaths(int deaths){
        this.deaths = this.deaths + deaths;
        return this;
    }
    public StatsAPI updateKD(){
        this.kd = getKDLayout();
        return this;
    }

    public void save(){
        try {
            setInt("Kills", this.kills);
            setInt("Deaths", this.deaths);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setInt(String category, int i) throws SQLException {
        PreparedStatement st = MySQL.con.prepareStatement("UPDATE knockiStats SET "+category+" = ? WHERE UUID = ?");
        st.setInt(1, i);
        st.setString(2, this.uuid);
        st.executeUpdate();
    }

    private int getInt(String category) throws SQLException {
        PreparedStatement st;
        st = MySQL.con.prepareStatement("SELECT " + category + " FROM knockiStats WHERE UUID = ?");
        st.setString(1, this.uuid);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return rs.getInt(category);
        }
        return 0;
    }

    private String getKDLayout() {

        if (this.kills == 0) {
            return "0.0";
        } else if (this.deaths == 0) {
            return this.kills + ".0";
        } else {
            double kd = (double) kills / (double) deaths;
            DecimalFormat format = new DecimalFormat("#.##");
            format.format(kd);
            return kd+"";
        }
    }

    private int getRanking() {
        boolean done = false;
        int i = 0;
        try {
            ResultSet rs = Main.mysql.query("SELECT UUID FROM knockiStats ORDER BY KILLS DESC;");
            while ((rs.next()) && (!done)) {
                i++;
                if (rs.getString(1).equalsIgnoreCase(this.uuid)) {
                    done = true;
                }
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Ranking Fehler");
        }
        return Integer.valueOf(i);
    }
    private boolean isExisting(){
        try {
            ResultSet rs = Main.mysql.query("SELECT * FROM knockiStats WHERE UUID= '" + uuid + "'");
            if (rs.next()) {
                return rs.getString("UUID") != null;
            }
            Main.mysql.update("INSERT INTO knockiStats (UUID, Kills, Deaths) VALUES ('" + uuid+ "', '0', '0');");
            return false;
        } catch (SQLException e) {
        }
        Main.mysql.update("INSERT INTO knockiStats (UUID, Kills, Deaths) VALUES ('" + uuid+ "', '0', '0');");
        return false;
    }
}
