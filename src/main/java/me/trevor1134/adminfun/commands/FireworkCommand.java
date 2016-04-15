package me.trevor1134.adminfun.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;

public class FireworkCommand extends CommandBase {

	private int task = -1;

	public FireworkCommand(final AdminFun plugin) {
		super(plugin, "firework", "firework <player>");
	}

	@Override
	public boolean onCommand(final CommandSender s, final String[] args) {
		if (isAuthorized(s)) {

			if (args.length == 1) {
				final Player p = getPlayer(args[0]);

				if (isValidPlayer(p)) {
					boolean isOutside = true;
					final int yPos = (int) p.getLocation().getY();

					for (int i = yPos + 1; i < p.getWorld().getMaxHeight(); i++) {
						final Location yLoc = new Location(p.getLocation().getWorld(), (int) p.getLocation().getX(), i,
								(int) p.getLocation().getZ());

						if (yLoc.getBlock() != null) {
							if (yLoc.getBlock().getType() != Material.AIR) {
								isOutside = false;
								break;
							}
						}
					}

					if (isOutside) {

						final Vector vel = p.getVelocity().setY(10);
						p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1F, 1F);
						p.setVelocity(vel);

						s.sendMessage(ChatColor.BLUE + "You have turned " + ChatColor.DARK_BLUE + p.getName()
								+ ChatColor.BLUE + " into a firework!");

						task = Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {

							@Override
							public void run() {
								p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, 1F, 1F);
								p.getWorld().createExplosion(p.getLocation(), 3F, false);
								FireworkCommand.this.stopTask();
							}

						}, 25);
						return true;
					} else {
						s.sendMessage(ChatColor.RED + "Player " + ChatColor.DARK_RED + p.getName() + ChatColor.RED
								+ " is not outside!");
						return true;
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

	private void stopTask() {
		Bukkit.getScheduler().cancelTask(task);
	}
}
