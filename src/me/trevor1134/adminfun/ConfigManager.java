package me.trevor1134.adminfun;

import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
	private AdminFun pl;

	public ConfigManager(AdminFun pl) {
		this.pl = pl;
	}

	private static String getConfigurationHeader() {
		final String[] header = { "AdminFun Main Configuration", "By: Trevor1134", "Version: 2.0.0" };

		final StringBuilder config = new StringBuilder();

		config.append("# +----------------------------------------------------+ #\n");

		for (final String line : header) {

			if (line.length() > 50) {
				continue;
			}

			final int lenght = (50 - line.length()) / 2;
			final StringBuilder finalLine = new StringBuilder(line);

			for (int i = 0; i < lenght; i++) {
				finalLine.append(" ");
				finalLine.reverse();
				finalLine.append(" ");
				finalLine.reverse();
			}

			if ((line.length() % 2) != 0) {
				finalLine.append(" ");
			}

			config.append("# | ").append(finalLine).append(" | #\n");

		}

		config.append("# +----------------------------------------------------+ #\n");

		return config.toString();
	}

	public void loadConfiguration() {
		pl.saveConfig();
		FileConfiguration config = pl.getConfig();
		final List<Integer> DPItems = Arrays.asList(276, 264, 267, 266, 310, 312, 311, 313, 322, 314, 317, 316, 315,
				278, 266);

		config.options().header(getConfigurationHeader());

		// Updater
		config.addDefault("adminfun.check-for-updates", true);
		config.addDefault("adminfun.automatic-updates", true);

		// Drop Party
		config.addDefault("drop-party.amount", 15);
		config.addDefault("drop-party.items", DPItems);

		// Announce
		config.addDefault("announce.format", "[Announcement] %MSG%");

		// Tell
		config.addDefault("tell.format", "&6[%FROM% -> me]: &f%MSG%");

		// Spamcast
		config.addDefault("spamcast.message-count", 10);

		// Slap
		config.addDefault("slap.max-damage", 5);

		// Possession
		config.addDefault("possession.cancel-damage", true);

		// Show
		config.addDefault("fireworkShow.amount", 15);

		config.options().copyDefaults(true);
		config.options().copyHeader(true);
		pl.saveConfig();
		return;
	}

}
