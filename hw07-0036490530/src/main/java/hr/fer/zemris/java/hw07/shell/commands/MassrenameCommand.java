package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.MyShell;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.commands.namebuilder.NameBuilder;
import hr.fer.zemris.java.hw07.shell.commands.namebuilder.NameBuilderInfo;
import hr.fer.zemris.java.hw07.shell.commands.namebuilder.NameBuilderInformer;
import hr.fer.zemris.java.hw07.shell.commands.namebuilder.NameBuilderParser;
import hr.fer.zemris.java.hw07.shell.commands.namebuilder.NameBuilderParserException;

/**
 * The command is used for massive renaming/moving files from source to
 * destination directory. Subcommands :
 * <ul>
 * <li>filter : prints out files selected with MASK");
 * <li>group : prints out all groups for selected files using MASK");
 * <li>show : uses extra argument EXPR, prints out selected names and new
 * names.");
 * <li>execute : uses extra argument EXPR, handles moving and renaming of files
 * from DIR1 to DIR2.");
 * </ul>
 * More information about this and other {@link ShellCommand} can be obtained by
 * using the {@link HelpCommand}.
 * 
 * @author Dorian Ivankovic
 *
 */
public class MassrenameCommand implements ShellCommand {

	/**
	 * Available sub types of the command
	 */
	public static final List<String> massrenameCommandTypes = new ArrayList<String>();

	static {
		massrenameCommandTypes.add("filter");
		massrenameCommandTypes.add("groups");
		massrenameCommandTypes.add("show");
		massrenameCommandTypes.add("execute");
	}

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		List<String> args = CommandUtils.getArguments(arguments, environment);
		if (args == null)
			return ShellStatus.CONTINUE;

		if (args.size() != 4 && args.size() != 5) {
			environment.writeln("Invalid arguments, use \"help massrename\" to get usage instructions.");
			return ShellStatus.CONTINUE;
		}

		String command = args.get(2);
		if (!massrenameCommandTypes.contains(command)) {
			environment.writeln("Invalid massrename CMD");
			return ShellStatus.CONTINUE;
		}

		if ((command.equals("filter") || command.equals("groups"))) {
			if (args.size() == 5) {
				environment.writeln("Command takes 4 arguments.");
				return ShellStatus.CONTINUE;
			}
		} else {
			if (args.size() == 4) {
				environment.writeln("Command takes 5 arguments.");
				return ShellStatus.CONTINUE;
			}
		}

		Path source = environment.getCurrentDirectory().resolve(Paths.get(args.get(0))).normalize();
		Path destination = environment.getCurrentDirectory().resolve(Paths.get(args.get(1))).normalize();

		if (!checkDirectory(source, "DIR1", environment) || !checkDirectory(destination, "DIR2", environment)) {
			return ShellStatus.CONTINUE;
		}

		Pattern pattern = Pattern.compile(args.get(3), Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

		switch (command) {
		case "filter":
			filter(environment, source, pattern);
		case "groups":
			groups(environment, source, pattern);
		case "show":
			execute(environment, source, destination, pattern, args.get(4), false);
		case "execute":
			execute(environment, source, destination, pattern, args.get(4), true);
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Filters and displays all files from source directory matching regular
	 * expression given by pattern.
	 * 
	 * @param environment
	 *            - used for writing outputs to shell
	 * @param source
	 *            - source directory
	 * @param pattern
	 *            - regular expression to check file names
	 */
	private void filter(Environment environment, Path source, Pattern pattern) {
		try {
			Files.list(source).forEach(path -> {
				Matcher matcher = pattern.matcher(path.getFileName().toString());
				if (matcher.matches()) {
					environment.writeln(path.getFileName().toString());
				}
			});
		} catch (IOException e) {
			environment.writeln("Couldn't open directory " + e.getLocalizedMessage());
		}

	}

	/**
	 * Filters and displays all files from source directory matching regular
	 * expression given by pattern, and aditionally displays matcher groups of each
	 * file name that can be used for referencing when specyfing a new file name.
	 * 
	 * @param environment
	 *            - used for writing outputs to shell
	 * @param source
	 *            - source directory
	 * @param pattern
	 *            - regular expression to check file names
	 */
	private void groups(Environment environment, Path source, Pattern pattern) {
		try {
			Files.list(source).forEach(path -> {

				Matcher matcher = pattern.matcher(path.getFileName().toString());
				if (matcher.matches()) {
					List<String> groups = getGroups(matcher);
					environment.write(path.getFileName().toString());

					for (int i = 0, n = groups.size(); i < n; i++) {
						environment.write(String.format(" %d: %s", i, groups.get(i)));
					}

					environment.write("\n");

				}
			});
		} catch (IOException e) {
			environment.writeln("Couldn't open directory " + e.getLocalizedMessage());
		}
	}

	/**
	 * The method retrieves groups from the matcher.
	 * 
	 * @param matcher
	 *            - matcher
	 * @return list of obtained groups
	 */
	private List<String> getGroups(Matcher matcher) {
		List<String> groups = new ArrayList<>();
		groups.add(matcher.group());

		for (int i = 1; i <= matcher.groupCount(); i++) {
			groups.add(matcher.group(i));
		}

		return groups;
	}

	/**
	 * Executes the <code>MassrenameCommand</code> if execute is true, just shows
	 * what would be the result of the operation otherwise.
	 * 
	 * @param environment
	 *            - used for writing outputs to shell
	 * @param source
	 *            - source directory
	 * @param destination
	 *            - destination directory to move files into
	 * @param pattern
	 *            - regular expression to check file names
	 * @param expression
	 *            - expression parsed using {@link NameBuilderParser} to create a
	 *            new file name
	 * @param execute
	 *            - the method performs the movement of the files if this argument
	 *            is true, and just shows the potencial results otherwise
	 */
	private void execute(Environment environment, Path source, Path destination, Pattern pattern, String expression,
			boolean execute) {
		try {
			NameBuilderParser parser = new NameBuilderParser(expression);
			NameBuilder builder = parser.getNameBuilder();

			Files.list(source).forEach(path -> {

				Matcher matcher = pattern.matcher(path.getFileName().toString());

				if (matcher.matches()) {
					List<String> groups = getGroups(matcher);
					NameBuilderInfo info = new NameBuilderInformer(new StringBuilder(), groups);
					builder.execute(info);

					String newName = info.getStringBuilder().toString();

					if (execute) {
						try {
							Files.move(source.resolve(path), destination.resolve(newName));
						} catch (IOException e) {
							environment.writeln("Couldn't move file " + path);
							return;
						}
					}

					environment.writeln(path.getFileName().toString() + " => " + newName);

				}

			});
		} catch (NameBuilderParserException ex) {
			environment.writeln(ex.getMessage());
		} catch (IOException e) {
			environment.writeln("Couldn't open directory " + e.getLocalizedMessage());
		} catch (IndexOutOfBoundsException ex) {
			environment.writeln("Group index out of bounds.");
		}
	}

	/**
	 * The method checks if path is a directory, and writes out an appropriate
	 * message to {@link MyShell}.
	 * 
	 * @param path
	 *            - path to a potential directory
	 * @param message
	 *            - message to write out to {@link MyShell}
	 * @param environment
	 *            - environment used for interaction with the actual {@link MyShell}
	 * @return true if path is a directory, false otherwise
	 */
	private boolean checkDirectory(Path path, String message, Environment environment) {
		if (!Files.isDirectory(path)) {
			environment.writeln(message + " is not a directory.");
			return false;
		}
		return true;
	}

	@Override
	public String getCommandName() {
		return "massrename";
	}

	@Override
	public List<String> getCommandDescription() {

		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - massrename");
		instructions.add("Arguments - DIR1 DIR2 CMD MASK (EXPR)");
		instructions.add("			- DIR1 : source directory");
		instructions.add("			- DIR2 : destination directory");
		instructions.add("			- CMD  : filter  : prints out files selected with MASK");
		instructions.add("			-      : group   : prints out all groups for selected files using MASK");
		instructions.add("			-      : show    : uses extra argument EXPR, prints out selected names and new names.");
		instructions.add("			-      : execute : uses extra argument EXPR, handles moving and renaming of files from DIR1 to DIR2.");
		instructions.add("			- MASK : regular expression, used to select files from DIR1");
		instructions.add("			- EXPR : defines how the new name is generated, tag ${ must contain group name and optional minSize and padding.");
		instructions.add("                 : example ${2,05} : group index = 2, minlength = 5, padded with zeros if less.");
		instructions.add("The command is used for massive renaming/moving files from dir1 to dir2.");

		return Collections.unmodifiableList(instructions);

	}

}
