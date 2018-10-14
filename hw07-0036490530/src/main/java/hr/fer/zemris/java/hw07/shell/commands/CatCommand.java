package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * The command is used to open the given file and write out its content.
 * 
 * @author Dorian Ivankovic
 *
 */
public class CatCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {

		List<String> args = CommandUtils.getArguments(arguments, environment);
		if (args == null)
			return ShellStatus.CONTINUE;

		if (args.size() < 1 || args.size() > 2) {
			environment.writeln("Illegal arguments, use \"help cat\" for usage instructions.");
			return ShellStatus.CONTINUE;
		}

		try {
			Path path = environment.getCurrentDirectory().resolve(Paths.get(args.get(0))).normalize();

			if (Files.isDirectory(path)) {
				environment.writeln("Not a file.");
				environment.writeln("Use \"help cat\" to find out more about the usage of this command.");
				return ShellStatus.CONTINUE;
			}
			if (!Files.exists(path)) {
				environment.writeln("File doesn't exist.");
				environment.writeln("Use \"help cat\" to find out more about the usage of this command.");
				return ShellStatus.CONTINUE;
			}

			Charset charset;
			if (args.size() == 2)
				charset = Charset.forName(args.get(1));
			else
				charset = Charset.defaultCharset();

			try (BufferedInputStream is = new BufferedInputStream(Files.newInputStream(path))) {

				byte[] buffer = new byte[1024];

				while (true) {
					int r = is.read(buffer);
					if (r < 1)
						break;
					int count = 0;

					while (count < r) {
						int end = count + 64 < r ? count + 64 : r;
						byte[] bytes = Arrays.copyOfRange(buffer, count, end);
						environment.write(new String(bytes, charset));
						count = end;
					}
				}
				environment.write("\n");

			} catch (IOException e) {
				environment.writeln("Couldn't open file " + path);
			}

		} catch (IllegalCharsetNameException | UnsupportedCharsetException ex) {
			environment.writeln("Illegal charset.");
			environment.writeln("Use command charsets to see available charsets.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - cat");
		instructions.add("Arguments - path to a file, if path contains spaces it must be enclosed by \" and \", ");
		instructions.add("            and \" in path must then be escaped by \\.");
		instructions.add("          - charset (optional) to be used when interpreting the file.");
		instructions.add("            If not provided the default platform charset is used.");
		instructions.add("Opens the given file and writes out its content.");

		return Collections.unmodifiableList(instructions);
	}

}
