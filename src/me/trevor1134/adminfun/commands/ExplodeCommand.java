package me.trevor1134.adminfun.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;

public class ExplodeCommand extends CommandBase {

	public ExplodeCommand(AdminFun pl) {
		super(pl, "explode", "explode <player>");

	}

	@Override
	public boolean onCommand(CommandSender s, String[] args) {
		if (isAuthorized(s)) {
			if (args.length == 1) {
				Player vic = getPlayer(args[0]);
				if (isValidPlayer(vic)) {
					vic.getWorld().createExplosion(vic.getLocation(), 1);
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
