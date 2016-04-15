package me.trevor1134.adminfun;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.trevor1134.adminfun.command.CommandBase;
import me.trevor1134.adminfun.command.CommandEnum;
import me.trevor1134.adminfun.command.Commands;

public class CommandManager implements CommandExecutor {

	private AdminFun plugin;
	private int cmdPerPage = 6;
	private int totalPages;

	public CommandManager(AdminFun pl) {
		plugin = pl;
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {

		if (args.length == 0) {
			if (!plugin.isDisabled()) {
				if (s.hasPermission("adminfun.help")) {
					sendHelpPage(s, 1);
					return true;
				} else {
					s.sendMessage(ChatColor.RED + "You do not have access to this command!");
					return false;
				}
			} else {
				s.sendMessage(ChatColor.DARK_RED + "AdminFun " + ChatColor.RED + "is disabled.");
				s.sendMessage(ChatColor.RED + "Type \"/adminfun enable\" to enable it.");
				return false;
			}
		}
		// END

		// This code group handles the toggling of the plugin
		// BEGIN
		if (args[0].equalsIgnoreCase("disable")) {
			if (args.length == 1) {
				if (s.hasPermission("adminfun.toggle")) {
					if (plugin.isDisabled()) {
						s.sendMessage(ChatColor.DARK_RED + "AdminFun " + ChatColor.RED + "is already disabled!");
						return true;
					}

					plugin.disable();
					s.sendMessage(ChatColor.DARK_RED + "AdminFun " + ChatColor.RED + "has been disabled.");
					return true;
				} else {
					s.sendMessage(ChatColor.RED + "You do not have access to this command!");
					return false;
				}
			} else {
				s.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "/adminfun disable");
				return false;
			}
		} else if (args[0].equalsIgnoreCase("enable")) {
			if (args.length == 1) {
				if (s.hasPermission("adminfun.toggle")) {
					if (!plugin.isDisabled()) {
						s.sendMessage(ChatColor.DARK_AQUA + "AdminFun " + ChatColor.AQUA + "is already enabled!");
						return true;
					}

					plugin.enable();
					s.sendMessage(ChatColor.DARK_AQUA + "AdminFun " + ChatColor.AQUA + "has been enabled.");
					return true;
				} else {
					s.sendMessage(ChatColor.RED + "You do not have access to this command!");
					return false;
				}
			} else {
				s.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "/adminfun enable");
				return false;
			}
		}
		// END

		if (!plugin.isDisabled()) {
			if (args[0].equalsIgnoreCase("help")) {
				if (s.hasPermission("adminfun.help")) {
					if (args.length == 1) {
						sendHelpPage(s, 1);
						return true;
					} else if (args.length == 2) {
						if (AdminFun.isNumber(args[1])) {
							int page = Integer.parseInt(args[1]);

							if ((page > 0) && (page <= totalPages)) {
								sendHelpPage(s, page);
								return true;
							} else {
								sendHelpPage(s, 1);
								return true;
							}

						} else if (isStringCommand(args[1]) != null) {
							CommandEnum cmdEnum = isStringCommand(args[1]);
							s.sendMessage(ChatColor.GOLD + "Command: " + ChatColor.GRAY + cmdEnum.getCommand());
							s.sendMessage(
									ChatColor.GOLD + "Alias(s): " + ChatColor.GRAY + cmdEnum.getAliases().toString());
							s.sendMessage(ChatColor.GOLD + "Description: " + ChatColor.GRAY
									+ Commands.registeredDescriptions.get(cmdEnum.getCommand()));
							return true;
						} else {

							s.sendMessage(ChatColor.RED + "Unknown page/command \"" + args[1] + "\".");
							return false;
						}
					} else {
						s.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "/adminfun help [page]");
						return false;
					}
				} else {
					s.sendMessage(ChatColor.RED + "You do not have access to this command!");
					return false;
				}
			} else {
				if (plugin.isDisabled()) {
					s.sendMessage("§4AdminFun §cis disabled!");
					return true;
				}
				final CommandEnum cmdEnum = Commands.getEnumByCommand(args[0].toLowerCase());
				if (cmdEnum == CommandEnum.NULL) {
					s.sendMessage(
							ChatColor.RED + "Unknown AdminFun command: " + ChatColor.DARK_RED + args[0].toLowerCase());
				} else {
					final String[] cmdArgs = new String[args.length - 1];
					if (args.length > 1) {
						System.arraycopy(args, 1, cmdArgs, 0, args.length - 1);
					}
					if (cmdEnum.getCommandClass() == null) {
						s.sendMessage(ChatColor.RED + "An error was detected upon executed the command: "
								+ ChatColor.GRAY + cmdEnum.getCommand());
						return false;
					}

					return proccessClass(s, cmdEnum, cmdArgs);
				}

			}
		} else {
			s.sendMessage(ChatColor.DARK_RED + "AdminFun " + ChatColor.RED + "is disabled");
			return false;
		}

		return false;
	}

	/**
	 * Send a help page to a user.
	 *
	 * @param s
	 *            the CommandSender
	 * @param page
	 *            the page number to send
	 */
	private void sendHelpPage(CommandSender s, int page) {
		final List<String> commandList = new ArrayList<>(Commands.registeredCommands.keySet());
		final List<String> descriptionList = new ArrayList<>();
		int size = commandList.size();
		totalPages = size / cmdPerPage;

		if ((size - (cmdPerPage * totalPages)) > 0) {
			totalPages++;
		}

		if ((page < 1) || (page > totalPages)) {
			page = 1;
		}

		for (final Entry<String, String> entrySet : Commands.registeredDescriptions.entrySet()) {
			descriptionList.add(entrySet.getValue());
		}

		s.sendMessage("§5------ §dAdminFun §ev" + plugin.getVersion() + " §5------");

		final int startIndex = (page - 1) * cmdPerPage, endIndex = cmdPerPage * page;
		for (int i = startIndex; i < endIndex; i++) {

			if (commandList.size() == i) {
				break;
			}

			s.sendMessage(Theme.COMMAND + "/adminfun " + commandList.get(i) + Theme.SPLITTER + " | " + Theme.DESCRIPTION
					+ descriptionList.get(i) + " ");

		}
		s.sendMessage(ChatColor.DARK_GRAY + "Type \"/adminfun help <command>\" to get information on a command.");
		s.sendMessage(ChatColor.GOLD + "Page number: " + ChatColor.DARK_BLUE + page + "/" + totalPages);
	}

	/**
	 * Iterate through possible command classes and attempt to create an
	 * instance with provided parameters.
	 *
	 * @param s
	 *            - The Command Sender
	 * @param cmdEnum
	 *            - The CommandEnum value.
	 * @param cmdArgs
	 *            - The array of arguments.
	 * @return true - if creation was successful.
	 */
	private boolean proccessClass(CommandSender s, CommandEnum cmdEnum, String[] cmdArgs) {
		Constructor<? extends CommandBase> ca = null;
		try {
			ca = cmdEnum.getCommandClass().getConstructor(AdminFun.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		try {
			Validate.notNull(ca);
			return ca.newInstance(plugin).onCommand(s, cmdArgs);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			s.sendMessage(ChatColor.RED + "An error was detected upon executed the command: " + ChatColor.GRAY
					+ cmdEnum.getCommand());
			return false;
		}
	}

	private CommandEnum isStringCommand(String s) {
		for (CommandEnum cmd : CommandEnum.values()) {
			if (cmd.getCommand().equalsIgnoreCase(s) || cmd.getAliases().contains(s)) {
				return cmd;
			}
		}
		return CommandEnum.NULL;
	}
}