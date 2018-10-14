package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Prints all directories stored on the directory stack using
 * {@link PushDCommand}.
 * 
 * @author Dorian Ivankovic
 *
 */
public class ListDCommand implements ShellCommand {

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
		} else {
			Stack<Path> stack = (Stack<Path>) value;
			List<Path> elements = new ArrayList<>();
			stack.forEach(path -> elements.add(0, path));

			elements.forEach(element -> environment.writeln(element.toString()));

		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "listd";
	}

	@Override
	public List<String> getCommandDescription() {

		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - listd");
		instructions.add("Arguments - none");
		instructions.add("Prints out all directories stored on directory stack.");
		return Collections.unmodifiableList(instructions);

	}

}
