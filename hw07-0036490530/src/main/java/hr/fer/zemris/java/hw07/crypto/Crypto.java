package hr.fer.zemris.java.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * The program is used to check the 
 * <a href="https://en.wikipedia.org/wiki/Cryptographic_hash_function">SHA-256 file digest</a>
 * <br> and to encrypt/decrypt given file using 
 * <a href="https://en.wikipedia.org/wiki/Advanced_Encryption_Standard">AES crypto-algorithm</a>.
 * <br>The user specifies which algorithm to use through command line arguments.
 * 			<ul><li>checksha file - file is the path to a file to check sha from
 * 				<li> encrypt original crypted - original is the path to the original file, 
 * 					and crypted path to the crypted one
 * 				<li> decrypt crypted original - crypted is the path to the crypted file
 * 					 and original path to the decrypted one
 * 			</ul>
 * The program requires user to provide password and initialization vector
 * as hex encoded strings when using encryption/decryption.
 * @author Dorian Ivankovic
 *
 */
public class Crypto {

	/**
	 * This method is called once the program is run.
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Arguments expected : \n checksha file - where file is the path"
					+ " of the file of which the sha-256 digest is computed");
			System.out.println(" encrypt file cryptedfile - where file is the path to of file to encrypt, and"
					+ " crypted file is a path of the crypted file.");
			return;
		}

		String mode = args[0].toLowerCase();

		if (mode.equals("checksha")) {
			if (args.length != 2) {
				System.out.println("One argument expected - path to the file to check sha-256 digest.");
				return;
			}
			chechSha(args[1]);

		} else if (mode.equals("encrypt")) {
			if (args.length != 3) {
				System.out.println("Two arguments expected - path to the file to encrypt and path to the crypted file.");
				return;
			}

			crypt(args[1], args[2], true);

		} else if (mode.equals("decrypt")) {
			if (args.length != 3) {
				System.out.println("Two arguments expected - path to the file to decrypt and path to the decrypted file.");
				return;
			}

			crypt(args[1], args[2], false);

		} else {
			System.out.println("Unavailable mode, available : checksha, encrypt, decrypt.");
		}
	}

	/**
	 * The method requires the user to input the expected sha and
	 * then calculates the sha from file given by path and prints
	 * out the appropriate message.
	 * @param path - path to the file to check sha of
	 */
	private static void chechSha(String path) {

		System.out.println("Please provide expected sha-256 digest for " + path + ":");

		String expectedDigest;

		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.print("> ");
				if (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					if (line.isEmpty())
						continue;
					expectedDigest = line;
					break;
				}
			}
		}

		Path p = Paths.get(path);

		try (InputStream is = new BufferedInputStream(Files.newInputStream(p, StandardOpenOption.READ))) {

			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			byte[] buff = new byte[1024];

			while (true) {
				int read = is.read(buff);
				if (read < 1)
					break;
				sha.update(buff, 0, read);
			}

			byte[] hash = sha.digest();
			String digest = Util.bytetohex(hash);

			if (digest.equals(expectedDigest.toLowerCase())) {
				System.out.println("Digesting completed. Digest of " + path + " matches expected digest.");
			} else {
				System.out.println("Digest completed. Digest of " + path + " does not match"
						+ " the expected digest\nDigest was : " + digest);
			}

		} catch (IOException ex) {
			System.out.println("Can't open file " + path);
		} catch (NoSuchAlgorithmException ignorable) {
		}

	}

	/**
	 * The method crypts/decrypts the original file using the aes algorithm.
	 * The user is expected to input password and initialization vectors as hex encoded strings.
	 * @param original - file to encrypt/decrypt
	 * @param cripted - crypted/decrypted file to be created or overwritten if already exists
	 * @param encrypt - determines whether to encrypt or decrypt
	 */
	private static void crypt(String original, String cripted, boolean encrypt) {
		
		String keyText = "";
		String initVector = "";
		
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Please provide password as hex-encoded text (16bytes, i.e. 32 hex-digits):");
			keyText = getHexInput(scanner);

			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			initVector = getHexInput(scanner);
		}

		try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(original), StandardOpenOption.READ));
			 OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get(cripted)))) {

			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(initVector));

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

			byte[] buff = new byte[1024];

			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;

				byte[] cipherData = cipher.update(buff, 0, r);
				os.write(cipherData);
			}
			os.write(cipher.doFinal());

			if (encrypt) {
				System.out.print("Encryption completed. ");
			} else {
				System.out.print("Decryption completed. ");
			}

			System.out.println("Generated file " + cripted + " based on file " + original);

		} catch (IOException exc) {
			System.out.println("Couldn't open file : " + exc.getLocalizedMessage());

		} catch (NoSuchAlgorithmException ignorable) {
		} catch (NoSuchPaddingException ignorable) {
		} catch (InvalidKeyException ignorable) {
		} catch (InvalidAlgorithmParameterException ignorable) {
		} catch (IllegalBlockSizeException ignorable) {
		} catch (BadPaddingException ignorable) {
		}

	}

	/**
	 * Obtains a 32 hex string input from the user.
	 * @param scanner - to read from standard input
	 * @return 32 hex string
	 */
	private static String getHexInput(Scanner scanner) {

		while (true) {
			System.out.print("> ");

			if (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.isEmpty())
					continue;

				if (!check32hex(line)) {
					System.out.println("Illegal format - 32 hex-digits required.");
				} else {
					return line;
				}
			}
		}

	}

	/**
	 * The method checks if line is a 32 hex string.
	 * @param line - line to check
	 * @return true if line is a 32 hex string, false otherwise
	 */
	private static boolean check32hex(String line) {
		if (line.length() != 32)
			return false;

		return line.matches("[0-9a-fA-F]+");
	}

}
