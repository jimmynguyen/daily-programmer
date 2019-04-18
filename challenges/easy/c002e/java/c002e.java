/*
 * Solution to /r/dailyprogrammer Challenge #2 Easy
 * Jimmy Nguyen
 *
 * Prompt:
 *     Create a calculator program that can compute a formula: F = M * A
 *
 * Bonus:
 *     For extra credit, the program should also be able to compute M and A
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pjbj8/easy_challenge_2/
 */
import java.util.Arrays;
import java.util.Scanner;

import org.apache.commons.lang3.ArrayUtils;

public final class c002e {
	private static Scanner scanner;

	private static void print_menu() {
		System.out.printf("\n==================================================\n");
		System.out.printf("\n\t1. F = M * A");
		System.out.printf("\n\t2. M = F / A");
		System.out.printf("\n\t3. A = F / M");
		System.out.printf("\n\tq. quit");
		System.out.printf("\n\n\t-------------------------\n");
		System.out.printf("\n\t>> ");
	}

	private static String input(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine();
	}

	private static void prompt_and_compute(String desired_variable) {
		String[] variables = new String[] {"F", "M", "A"};
		for (String variable : variables) {
			if (variable.equals(desired_variable)) {
				variables = ArrayUtils.removeElement(variables, variable);
				break;
			}
		}
		System.out.println();
		double input1 = Double.valueOf(input(String.format("\t%s = ", variables[0])));
		double input2 = Double.valueOf(input(String.format("\t%s = ", variables[1])));
		double output;
		if (Arrays.asList("M", "A").contains(desired_variable)) {
			output = input1 / input2;
		} else {
			output = input1 * input2;
		}
		System.out.printf("\n\t%s = %.2f", desired_variable, output);
		System.out.printf("\n\n\tPress ENTER to go back to the menu...");
		input("");
	}

	private static boolean select_formula(String command) {
		boolean is_terminate = false;
		if ("1".equals(command)) {
			prompt_and_compute("F");
		} else if ("2".equals(command)) {
			prompt_and_compute("M");
		} else if ("3".equals(command)) {
			prompt_and_compute("A");
		} else if ("q".equals(command)) {
			is_terminate = true;
		}
		return is_terminate;
	}

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		boolean is_terminate = false;
		while (!is_terminate) {
			print_menu();
			is_terminate = select_formula(input(""));
		}
	}
}