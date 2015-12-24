package me.trevor1134.adminfun.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.trevor1134.adminfun.commands.AnnounceCommand;
import me.trevor1134.adminfun.commands.BGodCommand;
import me.trevor1134.adminfun.commands.ConsoleCommand;
import me.trevor1134.adminfun.commands.DropPartyCommand;
import me.trevor1134.adminfun.commands.EnchantCommand;
import me.trevor1134.adminfun.commands.ExplodeCommand;
import me.trevor1134.adminfun.commands.FakeJoinCommand;
import me.trevor1134.adminfun.commands.FakeOpCommand;
import me.trevor1134.adminfun.commands.FakeQuitCommand;
import me.trevor1134.adminfun.commands.FireworkCommand;
import me.trevor1134.adminfun.commands.FreezeCommand;
import me.trevor1134.adminfun.commands.InvLockCommand;
import me.trevor1134.adminfun.commands.InvseeCommand;
import me.trevor1134.adminfun.commands.PlayerchatCommand;
import me.trevor1134.adminfun.commands.RandomTpCommand;
import me.trevor1134.adminfun.commands.ReloadCommand;
import me.trevor1134.adminfun.commands.RocketCommand;
import me.trevor1134.adminfun.commands.ShowCommand;
import me.trevor1134.adminfun.commands.SlapCommand;
import me.trevor1134.adminfun.commands.SoundCommand;
import me.trevor1134.adminfun.commands.SpamcastCommand;
import me.trevor1134.adminfun.commands.TellCommand;
import me.trevor1134.adminfun.commands.XPPartyCommand;
import me.trevor1134.adminfun.commands.ZapCommand;

public enum CommandEnum {

	ANNOUNCE("announce", null, AnnounceCommand.class),
	BGOD("bgod", Arrays.asList("god"), BGodCommand.class),
	CONSOLE("console", null, ConsoleCommand.class),
	DROPPARTY("dropparty", null, DropPartyCommand.class),
	ENCHANT("enchant", null, EnchantCommand.class),
	EXPLODE("explode", null, ExplodeCommand.class),
	FAKEJOIN("fakejoin", Arrays.asList("join"), FakeJoinCommand.class),
	FAKEOP("fakeop", Arrays.asList("op"), FakeOpCommand.class),
	FAKEQUIT("fakequit", Arrays.asList("quit"), FakeQuitCommand.class),
	FIREWORK("firework", null, FireworkCommand.class),
	FREEZE("freeze", null, FreezeCommand.class),
	INVLOCK("invlock", Arrays.asList("lock"), InvLockCommand.class),
	INVSEE("invsee", null, InvseeCommand.class),
	NULL("null", null, null),
	PLAYERCHAT("playerchat", Arrays.asList("pchat"), PlayerchatCommand.class),
	RANDOMTP("randomtp", Arrays.asList("tp"), RandomTpCommand.class),
	RELOAD("reload", Arrays.asList("rl"), ReloadCommand.class),
	ROCKET("rocket", null, RocketCommand.class),
	SHOW("show", null, ShowCommand.class),
	SLAP("slap", null, SlapCommand.class),
	SPAMCAST("spamcast", Arrays.asList("spam"), SpamcastCommand.class),
	TELL("tell", null, TellCommand.class),
	XPPARTY("xpparty", Arrays.asList("xp"), XPPartyCommand.class),
	ZAP("zap", null, ZapCommand.class),
	HELP("help", null, null),
	SOUND("sound", null, SoundCommand.class);

	private List<String> commandAliases = new ArrayList<>();
	private final String commandName;
	private Class<? extends CommandBase> exec;

	private CommandEnum(final String cmdName, List<String> commandAliases, Class<? extends CommandBase> e) {
		commandName = cmdName;
		exec = e;
		if (commandAliases != null) {
			this.commandAliases = commandAliases;
		} else {
			commandAliases = new ArrayList<String>();
		}
	}

	public List<String> getAliases() {
		return commandAliases;
	}

	public String getCommand() {
		return commandName;
	}

	public Class<? extends CommandBase> getCommandClass() {
		return exec;
	}

	public boolean isAlias(final String command) {
		for (final String alias : commandAliases) {
			if (command.equalsIgnoreCase(alias)) {
				return true;
			}
		}
		return false;
	}
}