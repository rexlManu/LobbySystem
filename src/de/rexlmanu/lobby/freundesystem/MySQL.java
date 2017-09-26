package de.rexlmanu.lobby.freundesystem;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import de.rexlmanu.lobby.Main;



public class MySQL {


	private FriendsGUI friends;
	private static String pr = "§3FileManager§8> ";
	private static String HOST = "";
	private static String DATABASE = "";
	private static String USER = "";
	private static String PASSWORD = "";
	private static String PORT = "";

	@SuppressWarnings("static-access")
	private static Connection con = Main.mysql.con;
	private boolean ic;
	
	
	public MySQL(FriendsGUI friends){
		this.friends = friends;
	}
	
	
	public boolean isConnected(){
		return ic;
	}
	
	public void createMySQLFile(String path){
		friends.getAPI().createNewFile("mysql.yml", path);
		File f = friends.getAPI().getFile("mysql.yml", path);
		FileConfiguration cfg = friends.getAPI().getConfiguration("mysql.yml", path);
		cfg.options().copyDefaults(true);
		cfg.addDefault("username", "root");
		cfg.addDefault("password", "password");
		cfg.addDefault("database", "localhost");
		cfg.addDefault("host", "localhost");
		cfg.addDefault("port", "3306");
		try {
			cfg.save(f);
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(pr+"§cCould not save file 'mysql.yml'.");
		}
		USER = cfg.getString("username");
		PASSWORD = cfg.getString("password");
		DATABASE = cfg.getString("database");
		HOST = cfg.getString("host");
		PORT = cfg.getString("port");
		connect();
	}

	public void connect() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?autoReconnect=true", USER, PASSWORD);
			Bukkit.getConsoleSender().sendMessage(pr+"§aSuccessfully connected to MySQL-Database.");
			ic = true;
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage(pr+"§cCould not connect to MySQL-Database, please check your MySQL-Settings.");
			ic = false;
		}
	}
	
	public void close() {
		try {
			if(con != null) {
				con.close();
				Bukkit.getConsoleSender().sendMessage(pr+"§aSuccessfully closed MySQL-Connection.");
				ic = false;
			}else{
				Bukkit.getConsoleSender().sendMessage(pr+"§cThere is no running connection to MySQL-Database.");
			}
		} catch (SQLException e) {
			Bukkit.getConsoleSender().sendMessage(pr+"§cCould not close MySQL-Connection, ERROR-CODE: "+e.getMessage());
		}
	}
	
	public void update(String qry) {
		try {
			Statement st = con.createStatement();
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	
	public ResultSet getResult(String qry) {
		ResultSet rs = null;
		
		try {
			Statement st = con.createStatement();
			rs = st.executeQuery(qry);
		} catch (SQLException e) {
			System.err.println(e);
		}
		return rs;
	}
	
}
