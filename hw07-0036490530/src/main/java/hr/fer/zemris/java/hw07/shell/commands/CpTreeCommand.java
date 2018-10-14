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
 * The command copies the source directory into destination directory
 * (recursively) i.e the whole directory structure under source directory is
 * copied to destination. <br>
 * If the destination directory doesn't exist, but destination parent directory
 * exists, then destination directory is created and all contents of source
 * directory all copied inside it.
 * 
 * @author Dorian Ivankovic
 *
 */
public class CpTreeCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		List<String> args = CommandUtils.getArguments(arguments, environment);
		if (args == null)
			return ShellStatus.CONTINUE;

		if (args.size() != 2) {
			environment.writeln("Illegal arguments, 2 arguments required, use \"help cptree\" for usage instructions.");
			return ShellStatus.CONTINUE;
		}

		Path source = environment.getCurrentDirectory().resolve(Paths.get(args.get(0))).normalize();
		if (!Files.isDirectory(source)) {
			environment.writeln("Source path is not a directory.");
			return ShellStatus.CONTINUE;
		}

		Path destination = environment.getCurrentDirectory().resolve(Paths.get(args.get(1))).normalize();

		if (Files.isDirectory(destination)) {
			try {
				destination = destination.resolve(source.getFileName());
				Files.createDirectory(destination);
			} catch (IOException ex) {
				environment.writeln("Could't create destination directory.");
				return ShellStatus.CONTINUE;
			}
			
		} else if (!Files.isDirectory(destination) && Files.isDirectory(destination.getParent())) {
			try {
				Files.createDirectory(destination);
			} catch (IOException ex) {
				environment.writeln("Could't create destination directory.");
				return ShellStatus.CONTINUE;
			}
			
		} else {
			environment.writeln("Neither destination directory nor destination parent directory exist.");
			return ShellStatus.CONTINUE;
		}

		final Path dest = destination;
		try {
			Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					if (dir.equals(source))
						return FileVisitResult.CONTINUE;
					
					Path destDir = dest.resolve(source.relativize(dir));
					Files.copy(dir, destDir);
					
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Path destFile = dest.resolve(source.relativize(file));
					Files.copy(file, destFile);
					return FileVisitResult.CONTINUE;
				}

			});
		} catch (IOException e) {
			environment.writeln("Couldn't open source directory.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cptree";
	}

	@Override
	public List<String> getCommandDescription() {

		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - cptree");
		instructions.add("Arguments - path to a directory to copy and path to the destination directory.");
		instructions.add("Copies content of the source path to destination.");
		instructions.add("If the destination directory doesn't exist, but destination parent directory exists, ");
		instructions.add(
				"then destination directory is created and all contents of source directory all copied inside it.");
		
		return Collections.unmodifiableList(instructions);

	}

}
