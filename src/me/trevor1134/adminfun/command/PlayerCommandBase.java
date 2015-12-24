package me.trevor1134.adminfun.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.trevor1134.adminfun.AdminFun;

public abstract class PlayerCommandBase extends CommandBase {

	public PlayerCommandBase(AdminFun pl, String cmd, String usage) {
		super(pl, cmd, usage);

	}

	@Override
	public boolean onCommand(CommandSender s, String[] args) {
		if (!(s instanceof Player)) {
			s.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return true;
		}
		return onPlayerCommand(s, args);
	}

	public abstract boolean onPlayerCommand(CommandSender s, String[] args);

}
