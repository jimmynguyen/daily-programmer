/*
 * Solution to /r/dailyprogrammer Challenge #4 Easy
 * Jimmy Nguyen
 *
 * Prompt:
 *     Create a random password generator
 *
 * Bonus:
 *     For extra credit, allow the user to specify the amount of passwords to
 * generate. For extra extra credit, allow the user to specify the length of the
 * strings he wants to generate
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pm6oj/2122012_challenge_4_easy/
 *
 * Usage:
 *     java c004e -h|-help
 *         - prints the help menu
 *
 *     java c004e
 *         - prints a random alphanumeric password with max length 50
 *
 *     java c004e <len_password>
 *         - prints a random alphanumeric password with length <len_password>
 *
 *     java c004e <len_password> <num_passwords>
 *         - prints <num_passwords> random alphanumeric passwords with length
 *           <len_password>
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class c004e {
	private final static String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private final static int MAX_PASSWORD_LENGTH = 50;
	private final static Random rand = new Random();

	private static void print_help_menu() {
		System.out.printf(
				"\nUsage:" +
				"\n    java c004e -h|-help" +
				"\n        - prints the help menu" +
				"\n    java c004e" +
				"\n        - prints a random alphanumeric password with max length 50" +
				"\n    java c004e <len_password>" +
				"\n        - prints a random alphanumeric password with length <len_password>" +
				"\n    java c004e <len_password> <num_passwords>" +
				"\n        - prints <num_passwords> random alphanumeric passwords with length <len_password>" +
				"\n\n");
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

	private static List<List<Integer>> randi(int max, int num_rows, int num_cols) {
		List<List<Integer>> nums_list = new ArrayList<List<Integer>>(num_rows);
		List<Integer> nums;
		for (int i = 0; i < num_rows; i++) {
			nums = new ArrayList<Integer>(num_cols);
			for (int j = 0; j < num_cols; j++)
				nums.add(rand.nextInt(max));
			nums_list.add(nums);
		}
		return nums_list;
	}

	public static void main(String[] args) {
		int len_password, num_passwords;

		if (args.length >= 1) {
			if (Arrays.asList("-h", "-help").contains(args[0])) {
				print_help_menu();
				return;
			} else if (!is_integer(args[0])) {
				System.out.printf("\nInvalid len_password \"%s\". len_password must be an integer\n", args[0]);
				print_help_menu();
				return;
			}
			len_password = Integer.parseInt(args[0]);
		} else
			len_password = rand.nextInt(MAX_PASSWORD_LENGTH) + 1;

		if (args.length >= 2) {
			if (!is_integer(args[1])) {
				System.out.printf("\nInvalid num_passwords \"%s\". num_passwords must be an integer\n", args[1]);
				print_help_menu();
				return;
			}
			num_passwords = Integer.parseInt(args[1]);
		} else
			num_passwords = 1;

		List<List<Integer>> nums_list = randi(ALPHABET.length(), num_passwords, len_password);
		String password;
		for (List<Integer> nums : nums_list) {
			password = "";
			for (int num : nums) {
				password += ALPHABET.charAt(num);
			}
			System.out.printf("\n%s", password);
		}
		System.out.printf("\n\n");
	}
}
