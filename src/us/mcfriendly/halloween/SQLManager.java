package us.mcfriendly.halloween;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

public class SQLManager {

	public static Connection con;

	// connect
	public static boolean connect() {
		String host = "192.99.134.150";
		String port = "3306";
		String database = "s367_halloween";
		String username = "u367_hehVLBoqht";
		String password = "gb0U@EJmZbVIvrfSfhkDA!C=";
		String ssl = "false";
		if (!isConnected()) {
			try {
				con = DriverManager.getConnection(
						"jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=" + ssl,
						username, password);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	// disconnect
	public static void disconnect() {
		if (isConnected()) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// isConnected
	public static boolean isConnected() {
		return (con == null ? false : true);
	}

	// getConnection
	public static Connection getConnection() {
		return con;
	}
	
	public static void initializeNewPlayer(Player p) {
		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					String sql = "SELECT * FROM `data` WHERE uuid = ?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, p.getUniqueId().toString());
					ResultSet res = stmt.executeQuery();
					if (!res.next()) {
						try {
							String roundstable = "INSERT INTO `data` (uuid) VALUES (?)";
							PreparedStatement roundstablestmt = connection.prepareStatement(roundstable);
							roundstablestmt.setString(1, p.getUniqueId().toString());
							roundstablestmt.executeUpdate();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}