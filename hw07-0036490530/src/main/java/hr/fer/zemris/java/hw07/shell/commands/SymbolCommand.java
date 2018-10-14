package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * The command is used to find out currently used action symbols - PROMPT,
 * MORELINES, MULTILINE, <br> or to set them to a new symbol.
 * 
 * @author Dorian Ivankovic
 *
 */
public class SymbolCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {

		String[] args = arguments.trim().split("\\s+");

		char symbol;
		if (args.length > 2 || args.length < 1 || args[0].isEmpty()) {
			environment.writeln(
					"Illegal arguments, use \"help symbol\" to find out more about the usage of this command.");
			return ShellStatus.CONTINUE;
		}

		Supplier<Character> getter = null;
		Consumer<Character> setter = null;
		String action = args[0];

		switch (action) {
		case "PROMPT":
			getter = (() -> environment.getPromptSymbol());
			setter = (c -> environment.setPromptSymbol(c));
			break;
		case "MORELINES":
			getter = (() -> environment.getMorelinesSymbol());
			setter = (c -> environment.setMorelinesSymbol(c));
			break;
		case "MULTILINE":
			getter = (() -> environment.getMultilineSymbol());
			setter = (c -> environment.setMultilineSymbol(c));
			break;
		default:
			environment.writeln(
					"Unknown action symbol, use \"help symbol\" to find out more about the usage of this command.");
			return ShellStatus.CONTINUE;
		}

		if (args.length == 2) {
			if (args[1].length() > 1 || (((int) args[1].charAt(0)) < 33 || ((int) args[1].charAt(0)) > 127)) {
				environment.writeln("Symbol can be set to a single character with ascii value [33,127].");
				return ShellStatus.CONTINUE;
			}
			symbol = args[1].charAt(0);
			environment.writeln("Symbol for " + action + " changed from '" + getter.get() + "' to '" + symbol + "'");
			setter.accept(symbol);
		} else {
			environment.writeln("Symbol for " + action + " is '" + getter.get() + "'");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - symbol");
		instructions.add("Used to find out or change the desired action symbol.");
		instructions.add("Arguments - PROMPT - prompt symbol");
		instructions.add("          - MORELINES - more lines symbol");
		instructions.add("          - MULTILINE - multi line symbol");
		instructions.add("          - additional argument - sets the desired action symbol");
		instructions.add("Example usage - \"symbol PROMPT #\" sets the prompt symbol to #.");

		return Collections.unmodifiableList(instructions);

	}

}
