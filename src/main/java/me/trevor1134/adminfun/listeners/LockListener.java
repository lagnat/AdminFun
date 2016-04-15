package me.trevor1134.adminfun.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import me.trevor1134.adminfun.commands.InvLockCommand;

public class LockListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (InvLockCommand.getLocked().contains(e.getWhoClicked().getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (InvLockCommand.getLocked().contains(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}
}
