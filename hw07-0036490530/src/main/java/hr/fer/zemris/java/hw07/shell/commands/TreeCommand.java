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
 * Writes a directory listing(recursive) as a tree.
 * 
 * @author Dorian Ivankovic
 *
 */
public class TreeCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		List<String> args = CommandUtils.getArguments(arguments, environment);
		if (args == null)
			return ShellStatus.CONTINUE;

		if (args.size() != 1) {
			environment.writeln("Only one argument required, use \"help tree\" for usage instructions.");
			return ShellStatus.CONTINUE;
		}

		Path path = environment.getCurrentDirectory().resolve(Paths.get(args.get(0))).normalize();
		if(!Files.exists(path)) {
			environment.writeln("Directory doesn't exist.");
			return ShellStatus.CONTINUE;
		}
		
		if (!Files.isDirectory(path)) {
			environment.writeln(path + " is not a directory.");
			return ShellStatus.CONTINUE;
		}

		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

				int indentation = 1;

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException ex) throws IOException {
					indentation--;
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attributes) throws IOException {
					environment.writeln(String.format("%" + 2 * indentation + "s%s", " ", dir.getFileName()));
					indentation++;
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
					environment.writeln(String.format("%" + 2 * indentation + "s%s", " ", file.getFileName()));
					return FileVisitResult.CONTINUE;
				}

			});
		} catch (IOException e) {
			environment.writeln("Couldn't open file " + e.getLocalizedMessage());
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {

		List<String> instructions = new ArrayList<>();
		
		instructions.add("Usage - tree");
		instructions.add("Arguments - path to a directory, if path contains spaces it must be enclosed by \" and \", ");
		instructions.add("            and \" in path must then be escaped by \\.");
		instructions.add("Writes a directory listing(recursive).");
		
		return Collections.unmodifiableList(instructions);

	}
}
