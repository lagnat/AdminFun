package me.trevor1134.adminfun.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.trevor1134.adminfun.AdminFun;

/**
 * The Class CommandBase.
 */
public abstract class CommandBase {

	/** The command. */
	private String command;

	/** The command usage. */
	private String commandUsage;

	/** The plugin. */
	private AdminFun plugin;

	/**
	 * Instantiates a new command base.
	 *
	 * @param pl
	 *            AdminFu
	 * @param cmd
	 *            the command
	 * @param usage
	 *            the proper usage for the command
	 */
	public CommandBase(AdminFun pl, String cmd, String usage) {
		command = cmd;
		commandUsage = usage;
		plugin = pl;
	}

	/**
	 * On command.
	 *
	 * @param s
	 *            the CommandSender
	 * @param args
	 *            the arguments
	 * @return true, if successful
	 */
	public abstract boolean onCommand(CommandSender s, String[] args);

	/**
	 * Gets the command.
	 *
	 * @return the command
	 */
	protected String getCommand() {
		return command;
	}

	/**
	 * Gets the raw usage.
	 *
	 * @return the raw usage
	 */
	protected String getRawUsage() {
		return commandUsage;
	}

	/**
	 * Array to string.
	 *
	 * @param array
	 *            the array
	 * @param startingIndex
	 *            the starting index
	 * @param useCommas
	 *            the use commas
	 * @return the string
	 */
	protected String arrayToString(String[] array, int startingIndex, boolean useCommas) {
		String string = "";
		for (int i = startingIndex; i < array.length; i++) {
			if (i == (array.length - 1)) {
				string += array[i];
			} else {
				string += array[i] + (useCommas ? ", " : " ");
			}
		}
		return string;
	}

	/**
	 * Checks if is authorized.
	 *
	 * @param s
	 *            the s
	 * @return true, if is authorized
	 */
	protected boolean isAuthorized(CommandSender s) {
		return s.hasPermission("adminfun." + getCommand().trim()) || s.isOp();
	}

	/**
	 * Gets the plugin.
	 *
	 * @return the plugin
	 */
	protected AdminFun getPlugin() {
		return plugin;
	}

	/**
	 * Checks if player is exempt.
	 *
	 * @param s
	 *            the s
	 * @return true, if is exempt
	 */
	protected boolean isExempt(CommandSender s) {
		return s.hasPermission("adminfun." + getCommand().trim() + ".exempt");
	}

	/**
	 * Gets the exempt message.
	 *
	 * @param p
	 *            the p
	 * @return the exempt message
	 */
	protected String getExemptMessage(String p) {
		return ChatColor.DARK_RED + p + ChatColor.RED + " is exempt from this command!";
	}

	/**
	 * Gets the usage message.
	 *
	 * @return the usage message
	 */
	protected String getUsageMessage() {
		return ChatColor.RED + "Usage: " + ChatColor.GRAY + "/adminfun " + commandUsage;
	}

	/**
	 * Gets the not found message.
	 *
	 * @param player
	 *            the player
	 * @return the not found message
	 */
	protected String getNotFoundMessage(String player) {
		return (ChatColor.RED + "Player \"" + ChatColor.DARK_RED + player + ChatColor.RED + "\" was not found!");
	}

	/**
	 * Gets the player.
	 *
	 * @param s
	 *            the s
	 * @return the player
	 */
	protected Player getPlayer(String s) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().equalsIgnoreCase(s)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Checks if is valid player.
	 *
	 * @param p
	 *            the p
	 * @return true, if is valid player
	 */
	protected boolean isValidPlayer(Player p) {
		return (p != null) && p.isOnline();
	}

	protected void denyAccess(CommandSender s) {
		s.sendMessage(ChatColor.RED + "You do not have access to this command!");
		getPlugin().getLogger().info("Player " + s.getName() + " was denied access to an AdminFun command.");
	}
}