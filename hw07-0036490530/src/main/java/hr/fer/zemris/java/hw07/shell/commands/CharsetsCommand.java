package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Lists names of supported charsets for current Java platform.
 * 
 * @author Dorian Ivankovic
 * 
 */
public class CharsetsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {

		if (!arguments.isEmpty()) {
			environment.writeln("Command takes no arguments");
			return ShellStatus.CONTINUE;
		}

		SortedMap<String, Charset> charsets = Charset.availableCharsets();
		charsets.forEach((name, charset) -> environment.writeln(name));
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {

		List<String> instructions = new ArrayList<>();
		instructions.add("Usage - charsets");
		instructions.add("Arguments - none");
		instructions.add("Lists names of supported charsets for your Java platform.");

		return Collections.unmodifiableList(instructions);
	}

}
