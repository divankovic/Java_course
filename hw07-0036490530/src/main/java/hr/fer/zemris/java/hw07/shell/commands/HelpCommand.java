package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.MyShell;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Help command used in {@link MyShell}. <br>
 * Shows usage and description of a desired command or lists all commands if
 * called without arguments.
 * 
 * @author Dorian Ivankovic
 *
 */
public class HelpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {

		arguments = arguments.trim();

		if (arguments.split("\\s+").length > 1) {
			environment.writeln("Command takes 0 or 1 argument.");
			return ShellStatus.CONTINUE;
		}

		if (arguments.isEmpty()) {
			SortedMap<String, ShellCommand> supportedCommands = environment.commands();
			supportedCommands.forEach((name, command) -> environment.writeln(name));
		} else {

			ShellCommand command = environment.commands().get(arguments);
			if (command == null) {
				environment.writeln("Command " + arguments + " doesn't exist. Use \"help\" to see available commands.");
				return ShellStatus.CONTINUE;
			}

			command.getCommandDescription().forEach(d -> environment.writeln(d));
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {

		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - help");
		instructions.add("Arguments - none or command of which information is required.");
		instructions.add("Shows usage and description of a desired command or lists all commands if "
				+ "called without arguments.");

		return Collections.unmodifiableList(instructions);
	}

}
