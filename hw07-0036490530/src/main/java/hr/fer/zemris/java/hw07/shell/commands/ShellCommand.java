package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.MyShell;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Represents a command used in {@link MyShell}.
 * @author Dorian Ivankovic
 *
 */
public interface ShellCommand {
	
	/**
	 * Executes the command using arguments.
	 * @param environment - environment of the {@link MyShell}
	 * @param arguments - arguments of the command
	 * @return {@link ShellStatus} of the command
	 */
	ShellStatus executeCommand(Environment environment, String arguments);
	
	/**
	 * Returns the name of the command.
	 * @return name of the command.
	 */
	String getCommandName();
	
	/**
	 * Returns a description about what the command does
	 * and of the command usage. 
	 * @return command description
	 */
	List<String> getCommandDescription();
}
