package me.trevor1134.adminfun.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;

public class PlayerchatCommand extends CommandBase {

	public PlayerchatCommand(AdminFun pl) {
		super(pl, "playerchat", "playerchat <player> <message>");

	}

	@Override
	public boolean onCommand(CommandSender s, String[] args) {
		if (isAuthorized(s)) {
			if (args.length >= 2) {
				Player vic = getPlayer(args[0]);
				if (isValidPlayer(vic)) {
					vic.chat(ChatColor.translateAlternateColorCodes('&', arrayToString(args, 1, false)));
					return true;
				} else {
					s.sendMessage(getNotFoundMessage(args[0]));
					return false;
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