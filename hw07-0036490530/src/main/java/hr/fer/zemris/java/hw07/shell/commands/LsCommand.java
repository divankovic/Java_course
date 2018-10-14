package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Writes a directory listing(not recursive), and basic information about all
 * children directories and files.
 * 
 * @author Dorian Ivankovic
 *
 */
public class LsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		List<String> args = CommandUtils.getArguments(arguments, environment);
		if (args == null)
			return ShellStatus.CONTINUE;

		if (args.size() != 1) {
			environment.writeln("Only one argument required, use \"help ls\" for usage instructions.");
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

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Files.list(path).forEach(p -> {
				BasicFileAttributeView faView = Files.getFileAttributeView(p, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
				try {
					BasicFileAttributes attributes = faView.readAttributes();
					environment.writeln(String.format("%c%c%c%c %10d %s %s", attributes.isDirectory() ? 'd' : '-',
																			 Files.isReadable(p) ? 'r' : '-', Files.isWritable(p) ? 'w' : '-',
																			 Files.isExecutable(p) ? 'x' : '-', attributes.size(),
																			 dateFormat.format(new Date(attributes.creationTime().toMillis())), 
																			 p.getFileName()));
				} catch (IOException e) {
					environment.writeln("Couldn't open directory " + p);
				}

			});
		} catch (IOException e) {
			environment.writeln("Couldn't open directory " + path);
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();
		
		instructions.add("Usage - ls");
		instructions.add("Arguments - path to a directory, if path contains spaces it must be enclosed by \" and \", ");
		instructions.add("            and \" in path must then be escaped by \\.");
		instructions.add("Writes a directory listing(not recursive).");
		instructions.add("The output consists of 4 columns.");
		instructions.add("First column indicates if current object is directory (d), readable (r), writable (w) and executable (x).");
		instructions.add("Second column contains object size in bytes.");
		instructions.add("Third column contains file creation date and time.");
		instructions.add("Fourth column contains file name.");
		
		return Collections.unmodifiableList(instructions);
	}

}
