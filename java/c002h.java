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
public class c002h {
	private final static String FILENAME = "../trash/c002h.log";
	private final static int DAYS_TO_SECONDS = 24*3600;

	public static void main(String[] args) {
		System.out.printf("\n==================================================");
		System.out.printf("\n> STOPWATCH (%s)", FILENAME);
		System.out.printf("\n==================================================");
		boolean is_terminate = false,
				prompt_user  = false;
		Datetime start_time = null;
		String command = null;
		while (!is_terminate) {
			
		}
	}
}
