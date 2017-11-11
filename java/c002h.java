/*
 * Solution to /r/dailyprogrammer Challenge #2 Hard
 * Jimmy Nguyen
 *
 * Prompt:
 *     Create a stopwatch program. This program should have start, stop, and lap
 * options, and it should write out to a file to be viewed later.
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pjsdx/difficult_challenge_2/
 *
 * Usage:
 *     c002h
 *         - launches program
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class c002h {
	private final static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private final static String FILENAME = "../trash/c002h.log";
	private static Scanner scanner;

	private static String input(String prompt) {
		if (null == scanner)
			scanner = new Scanner(System.in);
		System.out.print(prompt);
		return scanner.nextLine();
	}

	private static void write_to_file(String message) throws IOException {
		new File(FILENAME).createNewFile();
		Files.write(Paths.get(FILENAME), String.format("%s\n", message).getBytes(), StandardOpenOption.APPEND);
		System.out.printf(message);
	}

	private static boolean validate_command(boolean prompt_user, String command) {
		if (prompt_user)
			System.out.printf("\n> invalid command \"%s\". please enter a valid command:", command);
		else
			prompt_user = true;
		return prompt_user;
	}

	public static void main(String[] args) throws IOException {
		System.out.printf("\n==================================================");
		System.out.printf("\n> STOPWATCH (%s)", FILENAME);
		System.out.printf("\n==================================================");
		boolean is_terminate = false,
				prompt_user  = false;
		Date start_time = null, end_time;
		String command = null, end_datestr;
		double seconds_passed;
		while (!is_terminate) {
			if (prompt_user) {
				command = input("> ");
				System.out.println("--------------------------------------------------");
			}
			if (null == start_time) {
				if ("q".equals(command))
					is_terminate = true;
				else if ("s".equals(command)) {
					start_time = new Date();
					write_to_file(String.format("started: %s", DATE_FORMATTER.format(start_time)));
					prompt_user = false;
				} else {
					prompt_user = validate_command(prompt_user, command);
					System.out.printf("\n> enter s to start");
					System.out.printf("\n> enter q to quit");
					System.out.printf("\n--------------------------------------------------");
					System.out.printf("\n");
				}
			} else {
				end_time = new Date();
				end_datestr = DATE_FORMATTER.format(start_time);
				seconds_passed = (double)(end_time.getTime() - start_time.getTime()) / 1000;
				if ("e".equals(command)) {
					write_to_file(String.format("ended  : %s - %.4f seconds passed", end_datestr, seconds_passed));
					start_time = null;
					prompt_user = false;
				} else if ("l".equals(command)) {
					write_to_file(String.format("lapped : %s - %.4f seconds passed", end_datestr, seconds_passed));
					start_time = end_time;
				} else
					prompt_user = validate_command(prompt_user, command);
				if (null != start_time) {
					System.out.printf("\n> enter e to stop");
					System.out.printf("\n> enter l to lap");
					System.out.printf("\n--------------------------------------------------");
					System.out.printf("\n");
				}
			}
		}
		System.out.printf("\n");
	}
}
