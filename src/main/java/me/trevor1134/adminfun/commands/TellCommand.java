package me.trevor1134.adminfun.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;

public class TellCommand extends CommandBase {

	public TellCommand(AdminFun pl) {
		super(pl, "tell", "tell <identity> <message>");
	}

	@Override
	public boolean onCommand(CommandSender s, String[] args) {
		if (isAuthorized(s)) {
			if (args.length > 1) {
				for (Player p : s.getServer().getOnlinePlayers()) {
					if (!p.getName().equalsIgnoreCase(s.getName())) {
						p.sendMessage(getFormat(args[0], arrayToString(args, 1, false)));

						// If exempt, alert it was fake
						if (isExempt(p)) {
							p.sendMessage(ChatColor.RED + "The previous message was sent via AdminFun.");
						}
					}
				}
				s.sendMessage(ChatColor.AQUA + "Message sent to all players!");
				return true;
			} else {
				s.sendMessage(getUsageMessage());
				return false;
			}
		} else {
			denyAccess(s);
			return false;
		}
	}

	private String getFormat(String ident, String message) {
		return ChatColor.translateAlternateColorCodes('&', getPlugin().getConfig().getString("tell.format")
				.replaceAll("%FROM%", ident).replaceAll("%MSG", message));
	}

}
