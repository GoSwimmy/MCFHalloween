package us.mcfriendly.halloween;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HalloweenManager {

	public static void collectReward(CommandSender sender, Player t, int num) {
		if (SQLManager.connect()) {
			if (SQLManager.isConnected()) {
				Connection connection = SQLManager.getConnection();
				try {
					String sql = "SELECT * FROM data WHERE uuid = ?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					stmt.setString(1, t.getUniqueId().toString());
					ResultSet res = stmt.executeQuery();
					res.next();
					if (res.getInt(getEntry(num)) == 1) {
						t.sendMessage(Util.color(Util.prefix() + "&cHey! I've already seen you here!"));
						t.playSound(t.getLocation(), Sound.AMBIENT_CAVE, 1, 1);
					} else {
						try {
							String roundstable = "UPDATE data SET "+getEntry(num)+" = ? WHERE uuid = ?";
							PreparedStatement roundstablestmt = connection.prepareStatement(roundstable);
							roundstablestmt.setInt(1, 1);
							roundstablestmt.setString(2, res.getString("uuid"));
							roundstablestmt.executeUpdate();
							giveRandomReward(t);
						} catch (Exception e) {
							e.printStackTrace();
							t.sendMessage(Util.color(
									Util.prefix() + "&cThere was a trick in our system. Please contact an admin!"));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					t.sendMessage(
							Util.color(Util.prefix() + "&cThere was a trick in our system. Please contact an admin!"));
				}
			} else {
				t.sendMessage(
						Util.color(Util.prefix() + "&cThere was a trick in our system. Please contact an admin!"));
			}
		} else {
			t.sendMessage(Util.color(Util.prefix() + "&cThere was a trick in our system. Please contact an admin!"));
		}
	}

	public static String getEntry(int num) {
		switch(num) {
		case 1:
			return "a";
		case 2:
			return "b";
		case 3:
			return "c";
		case 4:
			return "d";
		case 5:
			return "e";
		case 6:
			return "f";
		case 7:
			return "g";
		case 8:
			return "h";
		default:
			return "a";
		}
	}
	
	@SuppressWarnings("unused")
	public static void giveRandomReward(Player t) {
		FileConfiguration c = DataManager.config;
		int max = 0;
		for (String key : c.getConfigurationSection("rewards").getKeys(false)) {
			max++;
		}
		int newkey = getRandom(1, max);
		t.playSound(t.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
		t.sendMessage(Util.color(Util.prefix() + c.getString("rewards." + newkey + ".message")));
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.getString("rewards." + newkey + ".command").replace("%player%", t.getName()));
	}

	public static int getRandom(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}
