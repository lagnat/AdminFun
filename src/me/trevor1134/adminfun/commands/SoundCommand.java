package me.trevor1134.adminfun.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;
import net.md_5.bungee.api.ChatColor;

public class SoundCommand extends CommandBase {

	public SoundCommand(AdminFun pl) {
		super(pl, "sound", "sound <player> <sound>");

	}

	@Override
	public boolean onCommand(CommandSender s, String[] args) {
		if (isAuthorized(s)) {
			if (args.length == 2) {
				Player tar = Bukkit.getPlayer(args[0]);
				if (isValidPlayer(tar)) {

					Sound s1 = null;

					for (Sound a : Sound.values()) {
						if (a.toString().equalsIgnoreCase(args[1])) {
							s1 = a;
							break;
						}
					}

					if (s1 == null) {
						s.sendMessage(ChatColor.RED + "Sound not found!");
						return false;
					}

					tar.playSound(tar.getLocation(), s1, 1, 1);
					s.sendMessage(ChatColor.AQUA + "Sound played!");
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

}
