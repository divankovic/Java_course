package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Pushes the current working directory on the stack and sets the new working
 * directory.
 * 
 * @author Dorian Ivankovic
 *
 */
public class PushDCommand implements ShellCommand {

	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		List<String> args = CommandUtils.getArguments(arguments, environment);
		if (args == null)
			return ShellStatus.CONTINUE;

		if (args.size() != 1) {
			environment.writeln("Illegal arguments, 1 argument expected, use \"help pushd\" for usage instructions.");
			return ShellStatus.CONTINUE;
		}

		Path dir = environment.getCurrentDirectory().resolve(Paths.get(args.get(0))).normalize();
		if (!Files.isDirectory(dir)) {
			environment.writeln("Not a directory.");
			return ShellStatus.CONTINUE;
		}

		Object value = environment.getSharedData("cdstack");
		if (value == null) {
			Stack<Path> stack = new Stack<>();
			stack.push(environment.getCurrentDirectory());
			environment.setSharedData("cdstack", stack);
		} else {
			Stack<Path> stack = (Stack<Path>) value;
			stack.push(environment.getCurrentDirectory());
		}

		environment.setCurrentDirectory(dir);

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pushd";
	}

	@Override
	public List<String> getCommandDescription() {

		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - pushd");
		instructions.add("Arguments - directory : new working directory.");
		instructions.add("Pushes the current working directory on the stack and sets the new working directory.");

		return Collections.unmodifiableList(instructions);

	}

}
