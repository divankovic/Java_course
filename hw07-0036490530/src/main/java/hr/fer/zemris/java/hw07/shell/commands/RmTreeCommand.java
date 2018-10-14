package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Deletes the specified directory and all its contents (recursive).
 * 
 * @author Dorian Ivankovic
 *
 */
public class RmTreeCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		List<String> args = CommandUtils.getArguments(arguments, environment);
		if (args == null)
			return ShellStatus.CONTINUE;

		if (args.size() != 1) {
			environment.writeln("Illegal arguments, 1 argument expected, use \"help rmtree\" for usage instructions.");
			return ShellStatus.CONTINUE;
		}

		Path dir = environment.getCurrentDirectory().resolve(Paths.get(args.get(0))).normalize();
		if (!Files.isDirectory(dir)) {
			environment.writeln("Not a directory.");
			return ShellStatus.CONTINUE;
		}

		if (environment.getCurrentDirectory().startsWith(dir)) {
			environment.writeln("Cannot delete current working directory.");
			return ShellStatus.CONTINUE;
		}

		while (true) {
			environment.writeln("Are you sure you want to delete " + dir + " ?");
			environment.write("(yes/no): ");
			String answer = environment.readLine();
			if (answer.toLowerCase().equals("no")) {
				return ShellStatus.CONTINUE;
			} else if (answer.toLowerCase().equals("yes")) {
				break;
			}
		}

		try {
			Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException ex) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

			});
		} catch (IOException e) {
			environment.writeln("Couldn't open file " + e.getLocalizedMessage());
		}

		environment.writeln("Directory successfully deleted.");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "rmtree";
	}

	@Override
	public List<String> getCommandDescription() {

		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - rmtree");
		instructions.add("Arguments - path to a directory to delete.");
		instructions.add("Deletes the specified directory and all its contents.");
		return Collections.unmodifiableList(instructions);

	}

}
