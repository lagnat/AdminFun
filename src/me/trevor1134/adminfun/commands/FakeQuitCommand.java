package me.trevor1134.adminfun.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;
import net.md_5.bungee.api.ChatColor;

public class FakeQuitCommand extends CommandBase {

	public FakeQuitCommand(AdminFun pl) {
		super(pl, "fakequit", "fakequit [player]");

	}

	@Override
	public boolean onCommand(CommandSender s, String[] args) {
		if (isAuthorized(s)) {
			if (args.length == 0) {
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
						getPlugin().getConfig().getString("fakequitMsg").replaceAll("%player%", s.getName())));
				return true;
			} else if (args.length == 1) {
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
						getPlugin().getConfig().getString("fakequitMsg").replaceAll("%player%", args[0])));
				return true;
			} else {
				s.sendMessage(getUsageMessage());
				return false;
			}
		} else {
			denyAccess(s);
		}
		return false;
	}

}
