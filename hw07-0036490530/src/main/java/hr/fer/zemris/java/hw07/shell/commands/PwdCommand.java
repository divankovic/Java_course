package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Prints the current working directory.
 * @author Dorian Ivankovic
 *
 */
public class PwdCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		arguments = arguments.trim();
		if(arguments.length()!=0) {
			environment.writeln("Command takes no arguments.");
			return ShellStatus.CONTINUE;
		}
		
		environment.writeln(environment.getCurrentDirectory().toString());
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pwd";
	}

	@Override
	public List<String> getCommandDescription() {

		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - pwd");
		instructions.add("Arguments - none");
		instructions.add("Shows current shell directory.");

		return Collections.unmodifiableList(instructions);
	
	}

}
