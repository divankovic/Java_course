package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;

/**
 * Utils class that provides a function for command argument parsing.
 * 
 * @author Dorian Ivankovic
 *
 */
public class CommandUtils {
	
	/**
	 * Parses command arguments.
	 * @param arguments - single line containing command arguments
	 * @param environment - command environment
	 * @return list of parsed arguments
	 */
	public static List<String> getArguments(String arguments, Environment environment) {
		arguments = arguments.trim();

		List<String> args = new ArrayList<>();

		int currentIndex = 0;
		char[] argsData = arguments.toCharArray();
		
		boolean escape = true;

		while (currentIndex < argsData.length) {

			while (argsData[currentIndex] == ' ') currentIndex++;
			if (currentIndex == argsData.length) break;

			if (argsData[currentIndex] == '\"') {

				StringBuilder argBuilder = new StringBuilder();
				boolean closed = false;
				currentIndex++;

				while (currentIndex < argsData.length) {
					if (argsData[currentIndex] == '\"') {
						closed = true;
						currentIndex++;
						break;
					} else if (argsData[currentIndex] == '\\' && escape) {
						if (currentIndex + 1 < argsData.length && (argsData[currentIndex + 1] == '"' || argsData[currentIndex + 1] == '\\')) {
								argBuilder.append(argsData[currentIndex + 1]);
								currentIndex += 2;
						}else {
							argBuilder.append(argsData[currentIndex++]);
						}
					} else {
						argBuilder.append(argsData[currentIndex++]);
					}

				}

				if (!closed) {
					environment.writeln("Argument must be closed by \"");
					return null;
				}

				String arg = argBuilder.toString();
				if (arg.startsWith("\\") || arg.startsWith("/"))
					arg = "." + arg;
				args.add(arg);

			} else {

				int end = currentIndex;

				while (end < argsData.length && argsData[end] != ' ') end++;

				String arg = new String(argsData, currentIndex, end - currentIndex);
				if (arg.startsWith("\\") || arg.startsWith("/")) arg = "." + arg;
				args.add(arg);
				currentIndex = end;

			}
			
			if(args.size()>2) escape = false;
		}

		return args;
	}

}
