package us.mcfriendly.halloween;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public void onEnable() {
		for(Player all : Bukkit.getOnlinePlayers()) {
			SQLManager.initializeNewPlayer(all);
		}
		
		Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
		
		DataManager.setup(this);
		
		getCommand("halloween").setExecutor(new HalloweenCommand());
	}
	
}
