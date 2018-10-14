package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Pops the path from the stack if the stack is not empty; and sets the current
 * directory to the popped one if it exists.
 * 
 * @author Dorian Ivankovic
 *
 */
public class PopDCommand implements ShellCommand {

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

		try {
			environment.setCurrentDirectory(((Stack<Path>) value).pop());
		} catch (IllegalArgumentException ignorable) {
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "popd";
	}

	@Override
	public List<String> getCommandDescription() {

		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - popd");
		instructions.add("Arguments - none");
		instructions.add("Pops the path from the stack if the stack is not empty, ");
		instructions.add("and sets the current directory to the popped one if it exists.");
		
		return Collections.unmodifiableList(instructions);

	}

}
