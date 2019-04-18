/*
 * Solution to /r/dailyprogrammer Challenge #3 Intermediate
 * Jimmy Nguyen
 *
 * Prompt:
 *     Write a program that can encrypt texts with an alphabetical substitution
 * cipher. The cipher can ignore numbers, symbols, and whitespace.
 *
 * Bonus (not implemented):
 *     For extra credit, make it encrypt whitespace, numbers, and symbols
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pkwb1/2112012_challenge_3_intermediate/
 *
 * Note:
 *     The following implementation performs a simple substitution with groups
 * of size 4:
 *     https://en.wikipedia.org/wiki/Substitution_cipher#Simple_substitution
 */
public class c003i {
	private final static int GROUP_SIZE = 4;

	private static void print_help_menu() {
		System.out.printf(
				"\nUsage: java c003i [message] [keyword]" +
				"\n    [message]    message to encrypt" +
				"\n    [keyword]    keyword for cipher\n\n");
	}

	private static String unique(String str) {
		String unique_str = "";
		for (char letter : str.toCharArray())
			if (unique_str.indexOf(letter) < 0)
				unique_str += letter;
		return unique_str;
	}

	private static String get_alphabet(String keyword) {
		String original_alphabet = "";
		for (char letter = 'A'; letter <= 'Z'; letter++)
			original_alphabet += letter;
		return unique(keyword.toUpperCase() + original_alphabet);
	}

	private static String encrypt(String message, String keyword) {
		message = message.toUpperCase().replaceAll("[^A-Z]", "");
		String alphabet = get_alphabet(keyword);
		String encrypted_message = "";
		int n = message.length();
		for (int i = 0, j; i < n; i+=GROUP_SIZE) {
			j = i+GROUP_SIZE-1;
			if (j >= n)
				j = n-1;
			for (int k = i; k <= j; k++)
				encrypted_message += alphabet.charAt(message.charAt(k) - 'A');
			if (j < n-1)
				encrypted_message += ' ';
		}
		return encrypted_message;
	}

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.printf("\nInvalid number of arguments \"%d\"\n", args.length);
			print_help_menu();
		} else {
			String message = args[0];
			String keyword = args[1];
			System.out.printf("\nPlain : %s\nCipher: %s\n\n", message, encrypt(message, keyword));
		}
	}
}
