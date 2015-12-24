package me.trevor1134.adminfun.thread;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.commands.ShowCommand;

public class ShowThread implements Runnable {
	private int i = 5;
	private final Location l;
	private final Player p;
	private final AdminFun pl;
	private int task;

	public ShowThread(AdminFun pl, Location l, Player p) {
		this.pl = pl;
		this.l = l;
		this.p = p;
	}

	@Override
	public void run() {
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {

			@Override
			public void run() {

				ShowCommand.waiting.remove(p.getUniqueId());
				final ItemStack it = ShowCommand.getShowAxe();
				it.setItemMeta(ShowCommand.getAxeMeta());

				p.getInventory().remove(it);
				if (i != 0) {
					Bukkit.broadcastMessage(ChatColor.AQUA + "Firework show in: " + ChatColor.DARK_AQUA + i);
					i--;
				} else {
					for (int a = 0; a < pl.getConfig().getInt("fireworkShow.amount"); a++) {
						// Spawn the Firework, get the FireworkMeta.
						final Firework fw = (Firework) p.getWorld().spawnEntity(l, EntityType.FIREWORK);
						final FireworkMeta fwm = fw.getFireworkMeta();

						// Our random generator
						final Random r = new Random();

						// Get the type
						final int rt = r.nextInt(4) + 1;
						Type type = Type.BALL;
						if (rt == 1) {
							type = Type.BALL;
						}
						if (rt == 2) {
							type = Type.BALL_LARGE;
						}
						if (rt == 3) {
							type = Type.BURST;
						}
						if (rt == 4) {
							type = Type.CREEPER;
						}
						if (rt == 5) {
							type = Type.STAR;
						}

						// Get our random colours
						final int r1i = r.nextInt(17) + 1;
						final int r2i = r.nextInt(17) + 1;
						final Color c1 = getColor(r1i);
						final Color c2 = getColor(r2i);

						// Create our effect with this
						final FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1)
								.withFade(c2).with(type).trail(r.nextBoolean()).build();

						// Then apply the effect to the meta
						fwm.addEffect(effect);

						// Generate some random power and set it
						final int rp = r.nextInt(2) + 1;
						fwm.setPower(rp);

						// Then apply this to our rocket
						fw.setFireworkMeta(fwm);

					}
					stopTask();
				}
			}
		}, 0, 20);
	}

	private Color getColor(int i) {
		Color c = null;
		if (i == 1) {
			c = Color.AQUA;
		}
		if (i == 2) {
			c = Color.BLACK;
		}
		if (i == 3) {
			c = Color.BLUE;
		}
		if (i == 4) {
			c = Color.FUCHSIA;
		}
		if (i == 5) {
			c = Color.GRAY;
		}
		if (i == 6) {
			c = Color.GREEN;
		}
		if (i == 7) {
			c = Color.LIME;
		}
		if (i == 8) {
			c = Color.MAROON;
		}
		if (i == 9) {
			c = Color.NAVY;
		}
		if (i == 10) {
			c = Color.OLIVE;
		}
		if (i == 11) {
			c = Color.ORANGE;
		}
		if (i == 12) {
			c = Color.PURPLE;
		}
		if (i == 13) {
			c = Color.RED;
		}
		if (i == 14) {
			c = Color.SILVER;
		}
		if (i == 15) {
			c = Color.TEAL;
		}
		if (i == 16) {
			c = Color.WHITE;
		}
		if (i == 17) {
			c = Color.YELLOW;
		}

		return c;
	}

	private void stopTask() {
		Bukkit.getScheduler().cancelTask(task);
	}
}
