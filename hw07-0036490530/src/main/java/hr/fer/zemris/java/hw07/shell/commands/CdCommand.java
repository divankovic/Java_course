package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.MyShell;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * The command is used to change the current working directory in {@link MyShell}.
 * @author Dorian Ivankovic
 *
 */
public class CdCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		List<String> args = CommandUtils.getArguments(arguments, environment);
		if (args == null)
			return ShellStatus.CONTINUE;

		if (args.size() != 1) {
			environment.writeln("Illegal arguments, 1 argument expected, use \"help cd\" for usage instructions.");
			return ShellStatus.CONTINUE;
		}

		Path dir = environment.getCurrentDirectory().resolve(Paths.get(args.get(0))).normalize();
		
		try {
			environment.setCurrentDirectory(dir);
		}catch(IllegalArgumentException ex) {
			environment.writeln(ex.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cd";
	}

	@Override
	public List<String> getCommandDescription() {
		
		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - cd");
		instructions.add("Arguments - directory : new working directory.");
		instructions.add("Sets the new working directory.");

		return Collections.unmodifiableList(instructions);
	
	}

}
