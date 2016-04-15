package me.trevor1134.adminfun.commands;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;

public class RandomTpCommand extends CommandBase {

	public RandomTpCommand(final AdminFun plugin) {
		super(plugin, "randomtp", "randomtp <player>");
	}

	@Override
	public boolean onCommand(final CommandSender s, final String[] args) {
		if (isAuthorized(s)) {
			if (args.length == 1) {
				final Player target = getPlayer(args[0]);
				if (isValidPlayer(target)) {
					if (!isExempt(target)
							|| (target.getName().equalsIgnoreCase(s.getName()) && (s instanceof Player))) {
						final Random rand = new Random();
						int randX = rand.nextInt(10000);
						int randY = rand.nextInt(Math.round(target.getWorld().getMaxHeight() / 2));
						int randZ = rand.nextInt(10000);
						try {
							Block blockAtPos = target.getWorld().getBlockAt(randX, randY, randZ);
							Block blockBelowPos = null;
							if ((randY - 1) > 0) {
								blockBelowPos = target.getWorld().getBlockAt(randX, randY - 1, randZ);
							}
							int timeout = 1;
							if (!isAir(blockAtPos) || isAir(blockBelowPos)) {
								while (!isAir(blockAtPos) || isAir(blockBelowPos)) {
									if ((timeout % 1300) > 0) {
										randX = rand.nextInt(10000);
										randY = rand.nextInt(Math.round(target.getWorld().getMaxHeight() / 2));
										randZ = rand.nextInt(10000);
										blockAtPos = target.getWorld().getBlockAt(randX, randY, randZ);
										if ((randY - 1) > 0) {
											blockBelowPos = target.getWorld().getBlockAt(randX, randY - 1, randZ);
										}
										timeout++;
									} else {
										break;
									}
								}
							}
							final Location targetLocation = new Location(target.getWorld(), randX, randY, randZ);
							target.teleport(targetLocation, TeleportCause.PLUGIN);
						} catch (final Exception ex) {
							s.sendMessage(ChatColor.RED + "Command failed to run, an unexpected error occured.");
						}
					} else {
						s.sendMessage(getExemptMessage(target.getName()));
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
		return true;
	}

	private boolean isAir(final Block block) {
		return (block == null) || (block.getType() == Material.AIR);
	}

}
