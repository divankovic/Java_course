package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Paths;

import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;

/**
 * Simple shell program, supports basic shell operations - help, symbol, exit,
 * mkdir, cat, ls, tree, ... <br>
 * To find out more about supported operations use command help. <br>
 * To write multi line commands, write more lines symbol at the end of the line.
 * <br>
 * Special symbols can be found out by using command symbol. <br>
 * For more information about shells see
 * <a href="https://en.wikipedia.org/wiki/Shell_(computing)">Shell</a>.
 * 
 * @author Dorian Ivankovic
 *
 */
public class MyShell {

	/**
	 * This method is called once the program is run.
	 * 
	 * @param args
	 *            - command line arguments
	 */
	public static void main(String[] args) {

		ShellEnvironment environment = new ShellEnvironment(System.in, System.out);
		environment.setCurrentDirectory(Paths.get("."));
		
		try {
			environment.writeln("Welcome to MyShell v 1.0");

			ShellStatus status = ShellStatus.CONTINUE;
			do {
				environment.write(environment.getPromptSymbol() + " ");

				String line = environment.readLine();
				if (line.isEmpty()) continue;

				StringBuilder commandBuilder = new StringBuilder();

				if (line.endsWith(environment.getMorelinesSymbol().toString())) {

					commandBuilder.append(line.substring(0, line.lastIndexOf(environment.getMorelinesSymbol())));

					while (true) {
						environment.write(environment.getMultilineSymbol() + " ");
						line = environment.readLine();

						if (line.endsWith(environment.getMorelinesSymbol().toString())) {
							commandBuilder.append(line.substring(0, line.lastIndexOf(environment.getMorelinesSymbol())));
						} else {
							commandBuilder.append(line);
							break;
						}
					}

				} else {
					commandBuilder.append(line);
				}

				String commandArgs = commandBuilder.toString().trim();
				if (commandArgs.isEmpty()) continue;

				ShellCommand command = environment.commands().get(commandArgs.split("\\s+")[0]);
				if (command == null) {
					environment.writeln("Command doesn't exist. Use help to see available commands.");
					status = ShellStatus.CONTINUE;
				} else {
					status = command.executeCommand(environment, commandArgs.replaceFirst(command.getCommandName(), ""));
				}

			} while (status != ShellStatus.TERMINATE);

		} catch (ShellIOException ex) {
		} finally {
			environment.terminate();
		}
	}

}
