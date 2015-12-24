package me.trevor1134.adminfun.commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.PlayerCommandBase;

public class DropPartyCommand extends PlayerCommandBase {

	private final Random rand = new Random();

	private int dropPartyCanBeRun = -1;
	private int dropPartyTime;

	public DropPartyCommand(final AdminFun plugin) {
		super(plugin, "dropparty", "dropparty <starttime> [<silent>]");
	}

	@Override
	public boolean onPlayerCommand(CommandSender s, final String[] args) {
		if (isAuthorized(s)) {
			Player p = (Player) s;
			if ((args.length == 1) || (args.length == 2)) {
				if (dropPartyCanBeRun == -1) {
					if (AdminFun.isNumber(args[0])) {
						if (Integer.parseInt(args[0]) <= 20) {
							handleDropPartyCommand(p, args);
							return true;
						} else {
							p.sendMessage(ChatColor.RED + "You cannot set the commence time to more than 20.");
						}
					} else {
						p.sendMessage(ChatColor.RED + "The drop party commence time should be numeric.");
					}
				} else {
					p.sendMessage(ChatColor.RED + "A drop party is already going on!");
				}
			} else {
				p.sendMessage(getUsageMessage());
			}
		} else {
			denyAccess(s);
		}
		return true;
	}

	private void createDropParty(final Location dpLocation) {
		final World world = dpLocation.getWorld();
		final int r = getPlugin().getConfig().getInt("drop-party.amount");
		world.playEffect(dpLocation, Effect.MOBSPAWNER_FLAMES, 1);
		world.playSound(dpLocation, Sound.WITHER_SPAWN, 5F, 1F);
		for (int i = 0; i < r; i++) {
			ItemStack randomItem = getRandomItem();
			if (randomItem != null) {
				world.dropItem(getRandomLocation(dpLocation.clone()), randomItem);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private ItemStack getRandomItem() {
		int r = rand.nextInt(getPlugin().getConfig().getList("drop-party.items").size());
		return new ItemStack((Integer) getPlugin().getConfig().getList("drop-party.items").get(r));
	}

	private Location getRandomLocation(final Location pLocation) {
		int x = rand.nextInt(8) + 1;
		int y = rand.nextInt(4) + 1;
		int z = rand.nextInt(8) + 1;
		if (Math.random() >= 0.5) {
			x *= -1;
		}
		if (Math.random() >= 0.5) {
			y *= -1;
		}
		if (Math.random() >= 0.5) {
			z *= -1;
		}
		return pLocation.add(x, y, z);
	}

	private void handleDropPartyCommand(final Player p, final String[] args) {
		dropPartyTime = Integer.parseInt(args[0]);
		final Server pServer = p.getServer();
		final Location pLoc = p.getLocation();
		if (args.length == 2) {
			if (!args[1].equalsIgnoreCase("silent")) {
				pServer.broadcastMessage(ChatColor.GREEN + "[AdminFun] " + ChatColor.RED + p.getDisplayName()
						+ ChatColor.BLUE + " is hosting a drop party at: " + ChatColor.AQUA
						+ (int) p.getLocation().getX() + ", " + (int) p.getLocation().getY() + ", "
						+ (int) p.getLocation().getZ() + " in the world " + p.getWorld().getName() + "!");
				pServer.broadcastMessage(ChatColor.GREEN + "[AdminFun] " + ChatColor.RED + "Drop party in "
						+ dropPartyTime + " second" + (dropPartyTime != 1 ? "s" : ""));
			}
		} else {
			p.getServer().broadcastMessage(ChatColor.GREEN + "[AdminFun] " + ChatColor.RED + "Drop party in "
					+ dropPartyTime + " second" + (dropPartyTime != 1 ? "s" : ""));
		}
		dropPartyCanBeRun = Bukkit.getScheduler().runTaskTimer(getPlugin(), new Runnable() {
			@Override
			public void run() {
				try {
					dropPartyTime--;
					if (dropPartyTime > 0) {
						if (args.length == 2) {
							if (!args[1].equalsIgnoreCase("silent")) {
								pServer.broadcastMessage(
										ChatColor.GREEN + "[AdminFun] " + ChatColor.RED + "Drop party in "
												+ dropPartyTime + " second" + (dropPartyTime != 1 ? "s" : ""));
							}
						} else {
							pServer.broadcastMessage(ChatColor.GREEN + "[AdminFun] " + ChatColor.RED + "Drop party in "
									+ dropPartyTime + " second" + (dropPartyTime != 1 ? "s" : ""));
						}
					} else {
						createDropParty(pLoc);
						Bukkit.getScheduler().cancelTask(dropPartyCanBeRun);
						dropPartyCanBeRun = -1;
					}
				} catch (final Exception ex) {
					ex.printStackTrace();
					Bukkit.getScheduler().cancelTask(dropPartyCanBeRun);
					dropPartyCanBeRun = -1;
				}
			}
		}, 20L, 20L).getTaskId();
	}

}