package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.MyShell;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * The command terminates {@link MyShell}.
 * 
 * @author Dorian Ivankovic
 *
 */
public class ExitCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {

		if (!arguments.isEmpty()) {
			environment.writeln("Command takes no arguments");
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - exit");
		instructions.add("Arguments - none");
		instructions.add("Terminates the shell program.");

		return Collections.unmodifiableList(instructions);

	}

}
