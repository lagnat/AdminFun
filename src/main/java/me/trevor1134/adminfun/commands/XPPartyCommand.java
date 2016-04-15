package me.trevor1134.adminfun.commands;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.PlayerCommandBase;

public class XPPartyCommand extends PlayerCommandBase {

	public XPPartyCommand(final AdminFun plugin) {
		super(plugin, "xpparty", "xpparty <orbamount> [<silent>]");
	}

	@Override
	public boolean onPlayerCommand(CommandSender s, String[] args) {
		Player p = (Player) s;
		if (isAuthorized(p)) {
			if ((args.length == 1) || (args.length == 2)) {
				if (AdminFun.isNumber(args[0])) {
					if (Integer.parseInt(args[0]) > 750) {
						p.sendMessage(ChatColor.RED
								+ "You cannot spawn over 750 xp orbs, this may crash other player's clients.");
					} else {
						if (args.length == 2) {
							if (!args[1].equalsIgnoreCase("silent")) {
								p.getServer().broadcastMessage(

										ChatColor.DARK_AQUA + p.getDisplayName() + ChatColor.AQUA
												+ " is hosting a xp party at location: " + ChatColor.BLUE
												+ (int) p.getLocation().getX() + ", " + (int) p.getLocation().getY()
												+ ", " + (int) p.getLocation().getZ() + " in world: "
												+ p.getWorld().getName());
							}
						} else {
							p.getServer()
									.broadcastMessage(ChatColor.DARK_AQUA + p.getDisplayName() + ChatColor.AQUA
											+ " is hosting a xp party at location: " + ChatColor.BLUE
											+ (int) p.getLocation().getX() + ", " + (int) p.getLocation().getY() + ", "
											+ (int) p.getLocation().getZ() + " in world: " + p.getWorld().getName());
						}
						final int orbAmount = Integer.parseInt(args[0]);
						for (int i = 0; i < orbAmount; i++) {
							p.getWorld().spawnEntity(getRandomLocation(p.getLocation()), EntityType.THROWN_EXP_BOTTLE);
						}
					}
				} else {
					p.sendMessage(ChatColor.RED + "The amount of XP Orbs must be numeric.");
				}
			} else {
				p.sendMessage(getUsageMessage());
			}
		} else {
			denyAccess(p);
		}
		return true;
	}

	private Location getRandomLocation(final Location pLocation) {
		final Random rand = new Random();
		int x = rand.nextInt(8);
		int y = rand.nextInt(4);
		int z = rand.nextInt(8);
		x++;
		y++;
		z++;
		if (Math.random() > 0.5) {
			x *= -1;
		}
		if (Math.random() > 0.5) {
			y *= -1;
		}
		if (Math.random() > 0.5) {
			z *= -1;
		}
		if (y < 4) {
			y = 4;
		}
		pLocation.setX(pLocation.getX() + x);
		pLocation.setY(pLocation.getY() + y);
		pLocation.setZ(pLocation.getZ() + z);

		return pLocation;
	}

}
