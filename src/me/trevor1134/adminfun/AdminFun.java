package me.trevor1134.adminfun;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.trevor1134.adminfun.command.CommandEnum;
import me.trevor1134.adminfun.command.Commands;
import me.trevor1134.adminfun.listeners.FreezeListener;
import me.trevor1134.adminfun.listeners.LockListener;
import me.trevor1134.adminfun.listeners.ShowListener;
import me.trevor1134.adminfun.util.Updater;
import me.trevor1134.adminfun.util.Updater.UpdateResult;
import me.trevor1134.adminfun.util.Updater.UpdateType;

/**
 * The Class AdminFun.
 */
public class AdminFun extends JavaPlugin {

	private boolean active = true;

	@Override
	public void onEnable() {

		// Register CommandListener
		getCommand("adminfun").setExecutor(new CommandManager(this));

		// Register Commands
		Commands.registerCommand("announce", CommandEnum.ANNOUNCE, "Broadcast a message to the server");
		Commands.registerCommand("bgod", CommandEnum.BGOD, "Broadcast a message as god.");
		Commands.registerCommand("console", CommandEnum.CONSOLE, "Broadcast a message as console.");
		Commands.registerCommand("dropparty", CommandEnum.DROPPARTY, "Host a dropparty at your location.");
		Commands.registerCommand("enchant", CommandEnum.ENCHANT, "Get a god kit.");
		Commands.registerCommand("explode", CommandEnum.EXPLODE, "Explode the target player.");
		Commands.registerCommand("fakejoin", CommandEnum.FAKEJOIN, "Pretend you or a user joined the game.");
		Commands.registerCommand("fakeop", CommandEnum.FAKEOP, "Pretend to OP a player.");
		Commands.registerCommand("fakequit", CommandEnum.FAKEQUIT, "Pretend you or a user left the game.");
		Commands.registerCommand("firework", CommandEnum.FIREWORK, "Turn a user into a firework.");
		Commands.registerCommand("freeze", CommandEnum.FREEZE, "Freeze a player in place.");
		Commands.registerCommand("invlock", CommandEnum.INVLOCK, "Lock a player's inventory.");
		Commands.registerCommand("invsee", CommandEnum.INVSEE, "Look in another player's inventory.");
		Commands.registerCommand("playerchat", CommandEnum.PLAYERCHAT, "Make a user say something!");
		Commands.registerCommand("randomtp", CommandEnum.RANDOMTP, "Teleport a player to a random location.");
		Commands.registerCommand("reload", CommandEnum.RELOAD, "Reload the configuration.");
		Commands.registerCommand("rocket", CommandEnum.ROCKET, "Turn a user into a rocket.");
		Commands.registerCommand("show", CommandEnum.SHOW, "Create a firework show.");
		Commands.registerCommand("slap", CommandEnum.SLAP, "Slap a player.");
		Commands.registerCommand("spamcast", CommandEnum.SPAMCAST, "Spam a message.");
		Commands.registerCommand("tell", CommandEnum.TELL, "Tell all users a message.");
		Commands.registerCommand("xpparty", CommandEnum.XPPARTY, "Host an XP party at your location.");
		Commands.registerCommand("zap", CommandEnum.ZAP, "Strike a bolt of lightning down onto a player.");
		Commands.registerCommand("help", CommandEnum.HELP, "View this page.");
		Commands.registerCommand("sound", CommandEnum.SOUND, "Play a sound to a player.");

		// Register Listeners
		Bukkit.getPluginManager().registerEvents(new FreezeListener(), this);
		Bukkit.getPluginManager().registerEvents(new ShowListener(this), this);
		Bukkit.getPluginManager().registerEvents(new LockListener(), this);

		saveDefaultConfig();
		manageUpdater();

	}

	/**
	 * Checks if AdminFun has been disabled.
	 *
	 * @return true, if is disabled
	 * @since 2.3.3
	 */
	public boolean isDisabled() {
		return !active;
	}

	/**
	 * Disable AdminFun.
	 *
	 * @since 2.3.3
	 */
	public void disable() {
		active = false;
	}

	/**
	 * Enable AdminFun.
	 *
	 * @since 2.3.3
	 */
	public void enable() {
		active = true;
	}

	/**
	 * Gets the version of AdminFun.
	 *
	 * @return the version
	 */
	public String getVersion() {
		return getDescription().getVersion();
	}

	/*
	 * Manage the updater implemented into AdminFun.
	 */
	private void manageUpdater() {
		if (getConfig().getBoolean("adminfun.check-for-updates")) {
			Updater updater = new Updater(this, 44074, getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
			if (updater.getResult() == UpdateResult.UPDATE_AVAILABLE) {
				final String title = "============================================";
				final String space = "                                            ";
				getLogger().info(title);
				try {
					getLogger().info(
							space.substring(0, ((44 / 2) - 8) + 3) + "AdminFun" + space.substring(0, (44 / 2) - 8));

				} catch (final Exception ex) {
					getLogger().info("AdminFun");
				}
				getLogger().info(title);
				getLogger().log(Level.INFO, "A new version is available: {0}", updater.getLatestName());
				getLogger().log(Level.INFO, "Your current version: AdminFun v{0}", getDescription().getVersion());
				if (getConfig().getBoolean("adminfun.automatic-updates")) {
					getLogger().log(Level.INFO, "Downloading {0}...", updater.getLatestName());
					updater = new Updater(this, 44074, getFile(), UpdateType.NO_VERSION_CHECK, false);
					final UpdateResult updateResult = updater.getResult();
					if (updateResult == UpdateResult.FAIL_APIKEY) {
						getLogger().info(
								"Download failed: Improperly configured the server's API key in the configuration");
					} else if (updateResult == UpdateResult.FAIL_DBO) {
						getLogger().info("Download failed: Could not connect to BukkitDev.");
					} else if (updateResult == UpdateResult.FAIL_DOWNLOAD) {
						getLogger().info("Download failed: Could not download the file.");
					} else if (updateResult == UpdateResult.FAIL_NOVERSION) {
						getLogger().info("Download failed: The latest version has an incorrect title.");
					} else {
						getLogger().info("The latest version of AdminFun has been downloaded.");
					}
				} else {
					getLogger().log(Level.INFO, "Download it from: {0}", updater.getLatestFileLink());
				}
			}
		}
	}

	public static boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
