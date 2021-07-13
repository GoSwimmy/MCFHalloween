package us.mcfriendly.halloween;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HalloweenCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			if (args.length == 2) {
				Player t = Bukkit.getPlayer(args[0]);
				if (t == null) {
					sender.sendMessage(Util.color(Util.prefix() + "&cThat player is not online!"));
				} else {
					try {
						int num = Integer.parseInt(args[1]);
						if(num <= 8 && num > 0) {
							HalloweenManager.collectReward(sender, t, num);
						} else {
							sender.sendMessage(Util.color(Util.prefix() + "&cValid numbers: 1-8"));
						}
					} catch(IllegalArgumentException e) {
						sender.sendMessage(Util.color(Util.prefix() + "&cInvalid usage, try /halloween <player> <number>"));
					}
				}
			} else {
				sender.sendMessage(Util.color(Util.prefix() + "&cInvalid usage, try /halloween <player> <number>"));
			}
		}
		return false;
	}
}
