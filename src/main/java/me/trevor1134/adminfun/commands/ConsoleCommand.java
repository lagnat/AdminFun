package me.trevor1134.adminfun.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;

public class ConsoleCommand extends CommandBase {

	public ConsoleCommand(AdminFun pl) {
		super(pl, "console", "console <message>");

	}

	@Override
	public boolean onCommand(CommandSender s, String[] args) {
		if (isAuthorized(s)) {
			if (args.length >= 1) {
				Bukkit.broadcastMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "[CONSOLE: "
						+ ChatColor.RESET.toString() + ChatColor.GRAY
						+ ChatColor.translateAlternateColorCodes('&', arrayToString(args, 0, false)) + ChatColor.ITALIC
						+ "]");
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
