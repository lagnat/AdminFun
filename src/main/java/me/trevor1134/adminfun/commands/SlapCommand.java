package me.trevor1134.adminfun.commands;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;

public class SlapCommand extends CommandBase {

	private String maxValue;

	public SlapCommand(final AdminFun plugin) {
		super(plugin, "slap", "slap <player> [damage]");
		maxValue = getPlugin().getConfig().getString("slap.max-damage");
	}

	@Override
	public boolean onCommand(final CommandSender s, final String[] args) {
		if (isAuthorized(s)) {
			if (args.length == 1) {
				final Player vic = getPlayer(args[0]);

				if (isValidPlayer(vic)) {

					slap(vic, 2.0);
					s.sendMessage(ChatColor.BLUE + "You have slapped " + vic.getName() + ".");
					vic.sendMessage(ChatColor.RED + "You have been slapped!");
					return true;

				} else {
					s.sendMessage(getNotFoundMessage(args[0]));
				}
			} else if (args.length == 2) {

				final Player vic = getPlayer(args[0]);

				if (isValidPlayer(vic)) {
					if (AdminFun.isNumber(args[1])) {
						final Integer i = Integer.parseInt(args[1]);
						final Double d = (double) i;
						if (AdminFun.isNumber(maxValue)) {
							final int m = Integer.parseInt(maxValue);
							if (d <= m) {
								slap(vic, d);
								s.sendMessage(
										ChatColor.DARK_AQUA + vic.getName() + ChatColor.AQUA + " has been slapped.");
								vic.sendMessage(ChatColor.RED + "You have been slapped!");
								return true;
							} else {
								s.sendMessage(ChatColor.RED + "Damage must be less than or equal to " + m + ".\n"
										+ " Player slapped with " + m + " damage.");
								slap(vic, m);
								s.sendMessage(
										ChatColor.DARK_AQUA + vic.getName() + ChatColor.AQUA + " has been slapped.");
								vic.sendMessage(ChatColor.RED + "You have been slapped!");
								return true;
							}
						}
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

	private void slap(final Player p, final double damage) {
		p.damage(damage);
		final Random random = new Random();
		final Vector v = new Vector(random.nextGaussian(), random.nextDouble(), random.nextGaussian());
		p.setVelocity(v);
	}
}
