package me.trevor1134.adminfun.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;

public class ReloadCommand extends CommandBase {

	public ReloadCommand(AdminFun pl) {
		super(pl, "reload", "reload");

	}

	@Override
	public boolean onCommand(CommandSender s, String[] args) {
		if (isAuthorized(s)) {
			if (args.length == 0) {

				getPlugin().saveDefaultConfig();

				s.sendMessage(ChatColor.AQUA + "You have successfully reloaded the " + ChatColor.DARK_AQUA + "AdminFun "
						+ ChatColor.AQUA + "configuration.");
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
