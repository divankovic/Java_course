package hr.fer.zemris.java.hw07.shell;

import java.io.BufferedInputStream;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.*;

/**
 * Shell environment, a concrete implementation of {@link Environment}.
 * 
 * @author Dorian Ivankovic
 *
 */
public  class ShellEnvironment implements Environment {

	/**
	 * Current working directory
	 */
	private Path currentDirectory;
	
	/**
	 * Shared data of the <code>ShellEnvironment</code>
	 */
	private HashMap<String, Object> sharedData;
	
	/**
	 * Map of supported commands.
	 */
	private SortedMap<String, ShellCommand> commands;

	/**
	 * Current prompt symbol.
	 */
	private char PROMPTSYMBOL = '>';

	/**
	 * Current morelines symbol.
	 */
	private char MORELINESSYMBOL = '\\';

	/**
	 * Current multilines symbol.
	 */
	private char MULTILINESYMBOL = '|';

	/**
	 * Reader used for reading user's inputs.
	 */
	private BufferedReader reader;

	/**
	 * Writer used for writing appropriate messages to the user.
	 */
	private BufferedWriter writer;

	/**
	 * Constructs a new <code>ShellEnvironment</code> using the input and output
	 * stream that will be used for reading and writing data to the user.
	 * 
	 * @param is
	 *            - input stream used for reading commands from the user
	 * @param os
	 *            - output stream used for writing data to the user
	 */
	public ShellEnvironment(InputStream is, OutputStream os) {
		reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
		writer = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(os)));

		commands = new TreeMap<String, ShellCommand>();

		commands.put("help", new HelpCommand());
		commands.put("exit", new ExitCommand());
		commands.put("symbol", new SymbolCommand());
		commands.put("cat", new CatCommand());
		commands.put("charsets", new CharsetsCommand());
		commands.put("copy", new CopyCommand());
		commands.put("hexdump", new HexdumpCommand());
		commands.put("ls", new LsCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("tree", new TreeCommand());
		
		commands.put("pwd", new PwdCommand());
		commands.put("cd", new CdCommand());
		commands.put("pushd", new PushDCommand());
		commands.put("popd", new PopDCommand());
		commands.put("listd", new ListDCommand());
		commands.put("dropd", new DropDCommand());
		
		commands.put("rmtree", new RmTreeCommand());
		commands.put("cptree", new CpTreeCommand());
		
		commands.put("massrename", new MassrenameCommand());
		
		sharedData = new HashMap<>();

	}

	@Override
	public String readLine() throws ShellIOException {

		try {
			String line = reader.readLine();
			return line;
		} catch (IOException e) {
			throw new ShellIOException();
		}

	}

	@Override
	public void write(String text) throws ShellIOException {

		try {
			writer.write(text);
			writer.flush();
		} catch (IOException e) {
			throw new ShellIOException();
		}

	}

	@Override
	public void writeln(String text) throws ShellIOException {

		try {
			writer.write(text);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			throw new ShellIOException();
		}

	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return MULTILINESYMBOL;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		MULTILINESYMBOL = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return PROMPTSYMBOL;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		PROMPTSYMBOL = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return MORELINESSYMBOL;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		MORELINESSYMBOL = symbol;
	}

	/**
	 * Tries to close the input and output streams once called.
	 */
	public void terminate() {
		try {
			reader.close();
			writer.close();
		} catch (IOException ignorable) {
		}
	}

	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}

	@Override
	public void setCurrentDirectory(Path path) {
		if(!Files.exists(path)) {
			throw new IllegalArgumentException("Directory doesn't exist.");
		}
		if(!Files.isDirectory(path)) {
			throw new IllegalArgumentException("Not a directory.");
		}
		currentDirectory = path.toAbsolutePath().normalize();
	}

	@Override
	public Object getSharedData(String key) {
		return sharedData.get(key);
	}

	@Override
	public void setSharedData(String key, Object value) {
		sharedData.put(key,value);
		
	}

}
