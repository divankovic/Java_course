package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Pops the last directory from directory stack if the stack is not empty.
 * 
 * @author Dorian Ivankovic
 *
 */
public class DropDCommand implements ShellCommand {

	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		arguments = arguments.trim();
		if (arguments.length() != 0) {
			environment.writeln("Command takes no arguments.");
			return ShellStatus.CONTINUE;
		}

		Object value = environment.getSharedData("cdstack");
		if (value == null || ((Stack<Path>) value).isEmpty()) {
			environment.writeln("Directory stack is empty.");
			return ShellStatus.CONTINUE;
		}

		((Stack<Path>) value).pop();

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "dropd";
	}

	@Override
	public List<String> getCommandDescription() {

		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - dropd");
		instructions.add("Arguments - none");
		instructions.add("Pops the path from the stack if the stack is not empty.");
		instructions.add("The current directory is not changed.");
		
		return Collections.unmodifiableList(instructions);

	}

}
