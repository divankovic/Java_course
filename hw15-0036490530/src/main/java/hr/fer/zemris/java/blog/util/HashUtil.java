package hr.fer.zemris.java.blog.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.blog.model.BlogUser;

/**
 * Util class used for calculating hashes of {@link BlogUser}'s passwords for storing into database.
 * @author Dorian Ivankovic
 *
 */
public class HashUtil {

	/**
	 * Non numeric hex symbols.
	 */
	public static final List<Character> hexChars = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f');

	
	/**
	 * Calculates the hash of the given <code>password</code> using 
	 * <a href="https://en.wikipedia.org/wiki/SHA-1">SHA-1 algorithm</a>.
	 * @param password - password to hash
	 * @return hashed password
	 */
	public static String hashPassword(String password) {
		String hashPassword = null;
		
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] passBytes = password.getBytes(StandardCharsets.UTF_8);
			passBytes = sha.digest(passBytes);
			hashPassword = bytetohex(passBytes);
		} catch (NoSuchAlgorithmException e) {
		}
		
		return hashPassword;
	}
	
	
	
	/**
	 * Converts a byte array into a hexadecimal string. Each byte is represented by
	 * two hex symbols.
	 * 
	 * @param bytes
	 *            - bytes to convert to hexadecimal
	 * @return hex string
	 */
	public static String bytetohex(byte[] bytes) {

		if (bytes.length == 0)
			return "";

		StringBuilder hexBuilder = new StringBuilder();

		for (int i = 0; i < bytes.length; i++) {
			char cUpper = getChar((byte) (bytes[i] >> 4) & HashUtil.hextobyte("0f")[0]);
			char cLower = getChar(bytes[i] & HashUtil.hextobyte("0f")[0]);
			hexBuilder.append(cUpper).append(cLower);
		}

		return hexBuilder.toString();

	}
	
	/**
	 * The method converts a hexadecimal string into a byte array.
	 * 
	 * @param keyText
	 *            - hexadecimal string
	 * @return byte array from hex input
	 * @throws IllegalArgumentException
	 *             if the string length is not divisible by 2 (each symbol in hex string is 4bits) 
	 *             or if the string contains a non hex symbols
	 */
	public static byte[] hextobyte(String keyText) {

		if (keyText.isEmpty()) return new byte[0];

		int len = keyText.length();

		if (len % 2 != 0) {
			throw new IllegalArgumentException("Input string must have an even number of hex characters.");
		}

		byte[] bytes = new byte[len / 2];

		for (int i = 0; i < len; i += 2) {
			byte upper = getByte(keyText.charAt(i));
			byte lower = getByte(keyText.charAt(i + 1));
			bytes[i / 2] = (byte) (upper << 4 | lower);
		}

		return bytes;
	}
	
	/**
	 * Converts a numeric value in base 10 created from a byte into a hex char.
	 * 
	 * @param i - numeric value of the byte
	 * @return hex symbol represented by the byte
	 */
	private static char getChar(int i) {

		if (i < 10) {
			return (char) (i + '0');
		} else {
			return hexChars.get(i - 10);
		}

	}
	
	/**
	 * Helper method used to extract binary values of each hex symbol.
	 * 
	 * @param c - hex symbol
	 * @return byte containing a bit representation of hex symbol in the bottom 4 bits
	 * @throws IllegalArgumentException if c is not a hex symbol
	 */
	private static byte getByte(char c) {

		c = Character.toLowerCase(c);

		if (Character.isDigit(c)) {
			return (byte) (c - '0');
		} else if (hexChars.contains(c)) {
			return (byte) (c - 'a' + 10);
		} else {
			throw new IllegalArgumentException("Invalid character : " + c);
		}

	}
}
