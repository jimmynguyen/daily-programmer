/*
 * Solution to /r/dailyprogrammer Challenge #1 Hard
 * Jimmy Nguyen
 *
 * Prompt:
 *     Create a program that will guess numbers between 1 and 100, and respond
 * appropriately based on whether the users say that number is too high or too
 * low
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pii6j/difficult_challenge_1/
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public final class c001h {
	private static final int MIN_VAL = 0;
	private static final int MAX_VAL = 100;
	private static final char LO_CHAR = 'l';
	private static final char HI_CHAR = 'h';

	private static Scanner scanner;

	private static class Guess {
		public int val;
		public char hiOrLo;
		public Guess(int val, char hiOrLo) {
			this.val = val;
			this.hiOrLo = hiOrLo;
		}
	}

	private static int computeGuess(int hi, int lo) {
		return ((int)Math.round(((double)hi - lo) / 2)) + lo;
	}

	private static String input(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine();
	}

	private static int[] getMostRecentHighAndLowGuesses(List<Guess> guesses) {
		int[] hiAndLo = new int[2];
		Guess guess;
		for (int i = 0; i < guesses.size(); i++) {
			guess = guesses.get(i);
			if (guess.hiOrLo == HI_CHAR) {
				hiAndLo[0] = guess.val;
			} else {
				hiAndLo[1] = guess.val;
			}
		}
		return hiAndLo;
	}

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		boolean isCorrect = false;
		List<Guess> guesses = new ArrayList<Guess>();
		int guessValue = computeGuess(MAX_VAL, MIN_VAL);
		input(String.format("\n==================================================\n\nPick a number between 1 and 100, inclusive. Once\nyou have chosen an number. Press ENTER to continue"));
		String answer;
		while (!isCorrect) {
			answer = input(String.format("\n==================================================\n\nIs %d your number? (y or yes|h or high|l or low)\n>> ", guessValue));
			if (Arrays.asList("y", "yes").contains(answer)) {
				isCorrect = true;
				System.out.printf("\n==================================================\n\nYour number is %d\n", guessValue);
			} else if (Arrays.asList("h", "high").contains(answer)) {
				if (guesses.isEmpty()) {
					guesses.add(new Guess(MIN_VAL, LO_CHAR));
				}
				guesses.add(new Guess(guessValue, HI_CHAR));
				int[] hiAndLo = getMostRecentHighAndLowGuesses(guesses);
				guessValue = computeGuess(hiAndLo[0], hiAndLo[1]);
			} else if (Arrays.asList("l", "low").contains(answer)) {
				if (guesses.isEmpty()) {
					guesses.add(new Guess(MAX_VAL, HI_CHAR));
				}
				guesses.add(new Guess(guessValue, LO_CHAR));
				int[] hiAndLo = getMostRecentHighAndLowGuesses(guesses);
				guessValue = computeGuess(hiAndLo[0], hiAndLo[1]);
			}
		}
	}
}