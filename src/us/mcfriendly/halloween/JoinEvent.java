package us.mcfriendly.halloween;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		SQLManager.initializeNewPlayer(e.getPlayer());
	}
}
