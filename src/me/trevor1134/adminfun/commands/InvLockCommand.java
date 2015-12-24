package me.trevor1134.adminfun.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;

public class InvLockCommand extends CommandBase {

	private static List<UUID> locked = new ArrayList<>();

	public InvLockCommand(AdminFun pl) {
		super(pl, "invlock", "invlock <player>");

	}

	@Override
	public boolean onCommand(CommandSender s, String[] args) {
		if (isAuthorized(s)) {
			if (args.length == 1) {
				Player vic = getPlayer(args[0]);
				if (isValidPlayer(vic)) {
					if (!isExempt(vic)) {

						if (locked.contains(vic.getUniqueId())) {
							locked.remove(vic.getUniqueId());
							s.sendMessage(ChatColor.DARK_AQUA + vic.getName() + "'s" + ChatColor.AQUA
									+ " inventory has been unlocked!");

							return true;
						} else {
							locked.add(vic.getUniqueId());
							s.sendMessage(ChatColor.DARK_AQUA + vic.getName() + "'s" + ChatColor.AQUA
									+ " inventory has been locked!");
							return true;
						}
					} else {
						s.sendMessage(getExemptMessage(args[0]));
					}
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

	public static List<UUID> getLocked() {
		return locked;
	}
}
