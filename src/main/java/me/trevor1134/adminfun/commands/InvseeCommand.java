package me.trevor1134.adminfun.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.PlayerCommandBase;

public class InvseeCommand extends PlayerCommandBase {

	public InvseeCommand(AdminFun pl) {
		super(pl, "invsee", "invsee <player>");

	}

	@Override
	public boolean onPlayerCommand(CommandSender s, String[] args) {
		if (isAuthorized(s)) {
			if (args.length == 1) {
				Player vic = getPlayer(args[0]);
				if ((vic != null) && vic.isOnline()) {
					if (!isExempt(vic)) {
						Player p = (Player) s;
						p.openInventory(vic.getInventory());
						return true;
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
		}
		return false;
	}

}
