/*
 * Solution to /r/dailyprogrammer Challenge #1 Easy
 * Jimmy Nguyen
 *
 * Prompt:
 *     Create a program that will ask the user's name, age, and reddit username.
 * Have it tell them the information back, in the format:
 * =============================================================================
 * your name is (blank), you are (blank) years old, and your username is (blank)
 * =============================================================================
 *
 * Bonus:
 *     For extra credit, have the program log this information in a file to be
 * accessed later.
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pih8x/easy_challenge_1/
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public final class c001e {
	private static final String FILE_PATH = "../../trash/info.log";
	private static Scanner scanner;

	private static String input(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine();
	}

	private static void save_to_file(String output) {
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH, true));
			bufferedWriter.write(output);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			System.out.printf("Your information has been saved in the file \"%s\" in the current directory\n", FILE_PATH);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		scanner = new Scanner(System.in);

		// ask for the user's information
		String name     = input("Name: ");
		String age      = input("Age : ");
		String username = input("Reddit Username: ");

		// format the output string
		String output = String.format("your name is %s, you are %s years old, and your username is %s", name, age, username);

		// print the output string
		System.out.println("\n" + output + "\n");

		// save the output string to a file
		save_to_file(output);
	}
}
