package me.trevor1134.adminfun.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;

public class AnnounceCommand extends CommandBase {

	public AnnounceCommand(AdminFun pl) {
		super(pl, "announce", "announce <message>");

	}

	@Override
	public boolean onCommand(CommandSender s, String[] args) {
		if (isAuthorized(s)) {
			if (args.length >= 1) {
				String format = getPlugin().getConfig().getString("announce.format");
				if ((format != null) && format.contains("%MSG%")) {
					s.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',
							format.replace("%MSG%", arrayToString(args, 0, false))));
					return true;
				} else {
					s.getServer().broadcastMessage(arrayToString(args, 0, false));
					s.sendMessage(ChatColor.RED
							+ "The config node \"announce.format\" is either null or does not contain \"%MSG%\"");
					return true;
				}
			} else {
				s.sendMessage(getUsageMessage());
				return false;
			}
		} else {
			denyAccess(s);
			return false;
		}
	}

}
