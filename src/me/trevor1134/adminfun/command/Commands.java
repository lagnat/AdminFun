package me.trevor1134.adminfun.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Commands {

	public final static Map<String, CommandEnum> registeredCommands = new HashMap<>();
	public final static Map<String, String> registeredDescriptions = new HashMap<>();

	public static CommandEnum getEnumByCommand(final String command) {
		for (final Entry<String, CommandEnum> entrySet : Commands.registeredCommands.entrySet()) {
			if (entrySet.getValue().getCommand().equalsIgnoreCase(command) || entrySet.getValue().isAlias(command)) {
				return entrySet.getValue();
			}
		}
		return CommandEnum.NULL;
	}

	public static boolean registerCommand(final String command, final CommandEnum cmdEnum, final String description) {
		try {
			if (!Commands.registeredCommands.containsKey(command)
					&& !Commands.registeredDescriptions.containsKey(command)) {
				Commands.registeredCommands.put(command, cmdEnum);
				Commands.registeredDescriptions.put(command, description);
				return true;
			}
		} catch (final Exception ignored) {
		}
		return false;
	}

	public static boolean registerCommand(final String command, final String description) {
		return Commands.registerCommand(command, CommandEnum.NULL, description);
	}
}