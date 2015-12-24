package me.trevor1134.adminfun.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;

public class FreezeCommand extends CommandBase {

	private static List<UUID> frozen = new ArrayList<>();

	public FreezeCommand(AdminFun pl) {
		super(pl, "freeze", "freeze <player>");

	}

	@Override
	public boolean onCommand(CommandSender s, String[] args) {
		if (isAuthorized(s)) {
			if (args.length == 1) {
				Player vic = getPlayer(args[0]);
				if (isValidPlayer(vic)) {
					if (!isExempt(vic)) {
						// Decide to freeze or unfreeze

						if (frozen.contains(vic.getUniqueId())) {
							frozen.remove(vic.getUniqueId());
							s.sendMessage(ChatColor.DARK_AQUA + vic.getName() + ChatColor.AQUA + " has been unfrozen!");
							vic.sendMessage(ChatColor.AQUA + "You have been unfrozen!");
							return true;
						} else {
							frozen.add(vic.getUniqueId());
							s.sendMessage(ChatColor.DARK_AQUA + vic.getName() + ChatColor.AQUA + " has been frozen!");
							vic.sendMessage(ChatColor.RED + "You have been frozen!");
							return true;
						}
					} else {
						s.sendMessage(getExemptMessage(vic.getName()));
						return false;
					}
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

	public static List<UUID> getFrozen() {
		return frozen;
	}
}