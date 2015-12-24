package me.trevor1134.adminfun.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;

public class ZapCommand extends CommandBase {

	public ZapCommand(AdminFun pl) {
		super(pl, "zap", "zap <player>");

	}

	@Override
	public boolean onCommand(CommandSender s, String[] args) {
		if (isAuthorized(s)) {
			if (args.length == 1) {
				Player vic = getPlayer(args[0]);
				if (isValidPlayer(vic)) {
					vic.getWorld().strikeLightning(vic.getLocation());
					s.sendMessage(
							ChatColor.DARK_AQUA + vic.getName() + ChatColor.AQUA + " has been struck by lightning!");
					return true;
				} else {
					s.sendMessage(getNotFoundMessage(args[0]));
				}
			} else {
				s.sendMessage(getUsageMessage());
			}
		} else {
			denyAccess(s);
		}
		return false;
	}
}
