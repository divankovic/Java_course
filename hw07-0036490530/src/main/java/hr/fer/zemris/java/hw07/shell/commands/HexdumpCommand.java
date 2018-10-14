package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.crypto.Util;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.MyShell;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Produces a hex output of a file from given filepath.
 * 
 * @author Dorian Ivankovic
 *
 */
public class HexdumpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment environment, String arguments) {
		List<String> args = CommandUtils.getArguments(arguments, environment);
		if (args == null)
			return ShellStatus.CONTINUE;

		if (args.size() != 1) {
			environment.writeln("Illegal arguments, 1 argument expected, use \"help hexdump\" for usage instructions.");
			return ShellStatus.CONTINUE;
		}

		Path file = environment.getCurrentDirectory().resolve(Paths.get(args.get(0))).normalize();
		if (Files.isDirectory(file)) {
			environment.writeln(file.getFileName() + " is not a file.");
			return ShellStatus.CONTINUE;
		}

		if (!Files.exists(file)) {
			environment.writeln("File doesn't exist.");
			return ShellStatus.CONTINUE;
		}

		try (BufferedInputStream is = new BufferedInputStream(Files.newInputStream(file))) {

			byte[] buffer = new byte[1024];

			int totalCount = 0;
			while (true) {
				int r = is.read(buffer);
				if (r < 1)
					break;
				formatOutput(environment, buffer, totalCount, r);
				totalCount += r;
			}

		} catch (IOException e) {
			environment.writeln("Cant open file " + e.getLocalizedMessage());
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Creates a formatted hex output.
	 * @param environment - environment of the {@link MyShell}
	 * @param buffer - read bytes
	 * @param totalCount - total bytes read, used for row numbering
	 * @param r - number of read bytes in buffer
	 */
	private void formatOutput(Environment environment, byte[] buffer, int totalCount, int r) {

		int count = 0;
		while (count < r) {
			int end = count + 16 < r ? count + 16 : r;
			byte[] bytes = Arrays.copyOfRange(buffer, count, end);
			String textChunk = new String(bytes);
			textChunk = retainPrintable(textChunk);

			String start = Util.bytetohex(ByteBuffer.allocate(4).putInt(totalCount + count).array()).toUpperCase();
			String hex = formathex(Util.bytetohex(bytes)).toUpperCase().trim();

			String hex1 = "";
			String hex2 = "";

			if (bytes.length > 8) {
				hex1 = hex.substring(0, 23);
				hex2 = hex.substring(24);
			} else {
				hex1 = hex;
			}

			environment.writeln(String.format("%s: %-23s|%-24s| %s", start, hex1, hex2, textChunk));

			count += 16;
		}
	}

	/**
	 * Formats a hex String to be separated by two hex symbols by space -> AABB - AA BB.
	 * @param hex - hex text
	 * @return formatted hex text
	 */
	private String formathex(String hex) {
		char[] data = hex.toCharArray();
		StringBuilder formatHexBuilder = new StringBuilder();

		for (int i = 0; i < data.length; i += 2) {
			formatHexBuilder.append(data[i]).append(data[i + 1]).append(" ");
		}

		return formatHexBuilder.toString();
	}

	/**
	 * Retains printable charcters (value [32, 127]) in text.
	 * @param text - input text to filter
	 * @return filtered text where all unprintable characters all replaced by "."
	 */
	private String retainPrintable(String text) {
		char[] data = text.toCharArray();
		StringBuilder printableBuilder = new StringBuilder();

		for (char c : data) {
			if (c < 32 || c > 127) printableBuilder.append('.');
			else printableBuilder.append(c);
		}

		return printableBuilder.toString();
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();

		instructions.add("Usage - hexdump");
		instructions.add("Arguments - path od the desired file to produce hex-output from, if path contains spaces it must be enclosed by \" and \", ");
		instructions.add("            and \" in path must then be escaped by \\.");
		instructions.add("Produces hex-output of the file.");

		return Collections.unmodifiableList(instructions);

	}

}
