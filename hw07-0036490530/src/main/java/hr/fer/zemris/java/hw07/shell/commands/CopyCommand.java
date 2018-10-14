package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
 * Copies the source file to destination file. <br>
 * If the destination file exists asks the user is it allowed to overwrite it.
 * <br>
 * If the destination is a directory then the source file is copied into that
 * directory using original file name.
 * 
 * @author Dorian Ivankovic
 *
 */
public class CopyCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		List<String> args = CommandUtils.getArguments(arguments, environment);
		if (args == null)
			return ShellStatus.CONTINUE;

		if (args.size() != 2) {
			environment.writeln("Illegal arguments, 2 arguments required, use \"help copy\" for usage instructions.");
			return ShellStatus.CONTINUE;
		}

		Path source = environment.getCurrentDirectory().resolve(Paths.get(args.get(0))).normalize();
		if (Files.isDirectory(source)) {
			environment.writeln("Not a file.");
			return ShellStatus.CONTINUE;
		}

		if (!Files.exists(source)) {
			environment.writeln("File doesn't exist.");
			return ShellStatus.CONTINUE;
		}

		Path destination = environment.getCurrentDirectory().resolve(Paths.get(args.get(1))).normalize();

		if (!Files.isDirectory(destination)) {
			if (Files.exists(destination)) {
				while (true) {
					environment.write("Do you want to overwrite the file " + destination.getFileName() + " (yes/no)? ");
					String answer = environment.readLine();
					if (answer.toLowerCase().equals("no")) {
						environment.writeln("0 files copied.");
						return ShellStatus.CONTINUE;
					} else if (answer.toLowerCase().equals("yes")) {
						break;
					}
				}
			}
		} else {
			destination = Paths.get(destination.toAbsolutePath().toString() + source.getFileName());
		}

		try (BufferedInputStream is = new BufferedInputStream(Files.newInputStream(source));
				BufferedOutputStream os = new BufferedOutputStream(Files.newOutputStream(destination))) {

			byte[] buff = new byte[1024];

			while (true) {
				int r = is.read(buff);
				if (r < 1) break;

				os.write(buff, 0, r);
			}

			environment.writeln("1 file copied");

		} catch (IOException e) {
			environment.writeln("Couldn't open file " + e.getLocalizedMessage());
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {

		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - copy");
		instructions.add("Arguments - path to the source file and a path to the destination file, if path contains spaces it must be enclosed by \" and \", ");
		instructions.add("            and \" in path must then be escaped by \\.");
		instructions.add("Copies the source file to destination file.");
		instructions.add("If the destination is a directory, a file of the same name as source file is created there.");

		return Collections.unmodifiableList(instructions);

	}

}
