package me.trevor1134.adminfun.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;

public class BGodCommand extends CommandBase {

	public BGodCommand(AdminFun pl) {
		super(pl, "bgod", "bgod <message>");

	}

	@Override
	public boolean onCommand(CommandSender s, String[] args) {
		if (isAuthorized(s)) {
			if (args.length >= 1) {
				Bukkit.broadcastMessage(ChatColor.GREEN + "[GOD] " + ChatColor.LIGHT_PURPLE
						+ ChatColor.translateAlternateColorCodes('&', arrayToString(args, 0, false)));
				return true;
			} else {
				s.sendMessage(getUsageMessage());
			}
		} else {
			denyAccess(s);
		}
		return false;
	}

}
