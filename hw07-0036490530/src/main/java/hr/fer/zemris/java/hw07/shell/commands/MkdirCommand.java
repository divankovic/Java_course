package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Creates the desired directory structure from given path argument.
 * 
 * @author Dorian Ivankovic
 *
 */
public class MkdirCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		List<String> args = CommandUtils.getArguments(arguments, environment);
		if (args == null) return ShellStatus.CONTINUE;

		if (args.size() != 1) {
			environment.writeln("Illegal arguments, 1 argument expected, use \"help mkdir\" for usage instructions.");
			return ShellStatus.CONTINUE;
		}

		Path dir = environment.getCurrentDirectory().resolve(Paths.get(args.get(0))).normalize();
		if (Files.isRegularFile(dir)) {
			environment.writeln("Not a valid directory.");
			return ShellStatus.CONTINUE;
		}

		if (Files.exists(dir)) {
			environment.writeln("Directory already exists.");
			return ShellStatus.CONTINUE;
		}

		try {
			Files.createDirectories(dir);
			environment.writeln("Created directory");
		} catch (IOException e) {
			environment.writeln("Directory creation failed");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - mkdir");
		instructions.add("Arguments - path od the desired directory structure to create, if path contains spaces it must be enclosed by \" and \", ");
		instructions.add("            and \" in path must then be escaped by \\.");
		instructions.add("Creates the desired directory structure.");

		return Collections.unmodifiableList(instructions);

	}

}
