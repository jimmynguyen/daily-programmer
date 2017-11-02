/*
 * Solution to /r/dailyprogrammer Challenge #3 Easy
 * Jimmy Nguyen
 *
 * Prompt:
 *     Write a program that can encrypt texts with an alphabetical Caesar
 * cipher. The cipher can ignore numbers, symbols, and whitespace.
 *
 * Bonus:
 *     For extra credit, add a "decrypt" function to your program
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pkw2m/2112012_challenge_3_easy/
 */
import java.util.Arrays;

public class c003e {
	private static class CaesarCipher {
		private CaesarCipher() {}
		public static String encrypt(String message, int n) {
			String encrypted_message = "";
			for (char letter : message.toCharArray()) {
				if (letter >= 'A' && letter <= 'Z')
					encrypted_message += (char)(((letter - 'A' + n) % 26) + 'A');
				else if (letter >= 'a' && letter <= 'z')
					encrypted_message += (char)(((letter - 'a' + n) % 26) + 'a');
				else
					encrypted_message += letter;
			}
			return encrypted_message;
		}
		public static String decrypt(String message, int n) {
			return encrypt(message, -n);
		}
	}

	private static void print_help_menu() {
		System.out.printf(
				"\nUsage: java c003e [option] [message] [n]" +
				"\n    [option]     -d: decrypt" +
				"\n                 -e: encrypt" +
				"\n    [message]    The message to encrypt or decrypt" +
				"\n    [n]          n is the integer to shift by. A" +
				"\n                 negative n will shift left\n\n");
	}

	private static String compile_message(String[] parts) {
		String message = "";
		for (int i = 1; i < parts.length - 1; i++) {
			message += parts[i] + (i == parts.length - 2 ? "" : " ");
		}
		return message;
	}

	private static boolean is_integer(String n) {
		boolean integer = true;
		try {
			Integer.parseInt(n);
		} catch (NumberFormatException nfe) {
			integer = false;
		}
		return integer;
	}

	public static void main(String[] args) {
		if (args.length < 3) {
			print_help_menu();
		} else if (!Arrays.asList("-d", "-e").contains(args[0])) {
			System.out.printf("\nInvalid option \"%s\"\n", args[0]);
			print_help_menu();
		} else if (!is_integer(args[args.length-1])) {
			System.out.printf("\nInvalid n \"%s\". n must be an integer\n\n", args[2]);
		} else {
			String ciphered_message;
			String message = compile_message(args);
			int n = Integer.parseInt(args[args.length-1]);
			if ("-d".equals(args[0]))
				ciphered_message = CaesarCipher.decrypt(message, n);
			else
				ciphered_message = CaesarCipher.encrypt(message, n);
			System.out.printf("\nPlain : %s\nCipher: %s\n\n", message, ciphered_message);
		}
	}
}
