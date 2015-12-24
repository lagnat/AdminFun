package me.trevor1134.adminfun.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;

public class FakeOpCommand extends CommandBase {

	public FakeOpCommand(AdminFun pl) {
		super(pl, "fakeop", "fakeop <player>");

	}

	@Override
	public boolean onCommand(CommandSender s, String[] args) {
		if (isAuthorized(s)) {
			if (args.length == 1) {
				Player vic = getPlayer(args[0]);
				if (isValidPlayer(vic)) {
					vic.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou are now op!"));

					s.sendMessage(ChatColor.DARK_AQUA + vic.getName() + ChatColor.AQUA + " has been fake opped!");
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
