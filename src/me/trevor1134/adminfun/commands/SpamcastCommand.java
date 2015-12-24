package me.trevor1134.adminfun.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;

public class SpamcastCommand extends CommandBase {

	private final String prefix = ChatColor.GOLD + "[" + ChatColor.RED + "Spam" + ChatColor.GREEN + "cast"
			+ ChatColor.GOLD + "] ";

	public SpamcastCommand(AdminFun plugin) {
		super(plugin, "spamcast", "spamcast <message>");
	}

	@Override
	public boolean onCommand(final CommandSender s, final String[] args) {
		if (isAuthorized(s)) {
			if (args.length >= 1) {
				final String message = ChatColor.translateAlternateColorCodes('&', arrayToString(args, 0, false));

				for (Player p : Bukkit.getOnlinePlayers()) {
					if (!isExempt(p)) {
						for (int i = 0; i < 20; i++) {
							p.sendMessage(prefix + ChatColor.DARK_AQUA + message);
						}
					}
				}
				s.sendMessage(ChatColor.AQUA + "Message has been spammed!");
			} else {
				s.sendMessage(getUsageMessage());
			}
		} else {
			denyAccess(s);
		}
		return false;
	}
}
