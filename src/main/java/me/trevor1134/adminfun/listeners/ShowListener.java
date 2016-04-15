package me.trevor1134.adminfun.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.commands.ShowCommand;
import me.trevor1134.adminfun.thread.ShowThread;

public class ShowListener implements Listener {
	private final AdminFun pl;

	public ShowListener(AdminFun pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (ShowCommand.getWaiting().contains(e.getPlayer().getUniqueId())) {
			if (e.getItem().getType() == Material.WOOD_AXE) {
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if (e.getItem().getItemMeta().getDisplayName().equals("Show Selector")) {
						// All ready to start

						final Location l = e.getClickedBlock().getLocation();
						final ShowThread s = new ShowThread(pl, l, e.getPlayer());

						// Generate new Thread for event
						final Thread t = new Thread(s);
						t.start();
						t.interrupt();

					}
				}
			}
		}
	}

}
