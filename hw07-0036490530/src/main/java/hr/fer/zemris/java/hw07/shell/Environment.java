package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Path;
import java.util.SortedMap;
import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;

/**
 * Environment which all {@link ShellCommand}'s use to communicate with the user
 * when using {@link MyShell}.
 * @author Dorian Ivankovic
 *
 */
public interface Environment {
	
	/**
	 * Read a single line from input.
	 * @return read line
	 * @throws ShellIOException - if reading from input is not possible
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Writes out text to the output.
	 * @param text - text to write to the output
	 * @throws ShellIOException - if writing to the output is not possible
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Writes out a new line containing text to the output.
	 * @param text - text to write to the output
	 * @throws ShellIOException - if writing to the output is not possible
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Returns a collections of supported {@link ShellCommand}'s
	 * used in {@link MyShell}.
	 * @return supported commands
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Returns the multiline symbol used in {@link MyShell}.
	 * @return multiline symbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets the multiline symbol used in {@link MyShell}.
	 * @param symbol - new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Returns the prompt symbol used in {@link MyShell}.
	 * @return prompt symbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets the prompt symbol used in {@link MyShell}.
	 * @param symbol - new prompt symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Returns the morelines symbol used in {@link MyShell}.
	 * @return morelines symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets the morelines symbol used in {@link MyShell}.
	 * @param symbol - new morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);
	
	/**
	 * Returns the current directory
	 * @return current directory
	 */
	Path getCurrentDirectory();
	
	/**
	 * Sets the current directory
	 * @param path - path to the new directory
	 */
	void setCurrentDirectory(Path path);
	
	/**
	 * Returns object mapped to key stored in shared data
	 * @param key - key to look for mapped object in shared data
	 * @return object mapped to key in shared data
	 */
	Object getSharedData(String key);
	
	/**
	 * Sets the value associated with key in shared data
	 * @param key - key to map value to
	 * @param value - value associated with key
	 */
	void setSharedData(String key, Object value);
	
}
