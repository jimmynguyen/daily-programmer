/*
 * Solution to /r/dailyprogrammer Challenge #110 Easy
 * Jimmy Nguyen
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/12k3xr/1132012_challenge_110_easy_keyboard_shift/
 *
 * Usage:
 *     java -ea c110e [-t or --test]
 *         - runs assertion tests
 *
 *     java c110e <string>
 *         - prints shifted text
 */
import java.lang.IllegalArgumentException;
import java.util.HashMap;
import java.util.Map;

public class c110e {
	private static final String SRC = "wertyuiop[]sdfghjkl;'xcvbnm,.WERTYUIOP{}SDFGHJKL:\"XCVBNM<>";
	private static final String DST = "qwertyuiop[asdfghjkl;zxcvbnm,QWERTYUIOP{ASDFGHJKL:ZXCVBNM<";
	private static String keyboardShift(String msg) {
		char[] arr = msg.toCharArray();
		for (int i = 0; i < arr.length; i++)
			if (arr[i] != ' ')
				arr[i] = DST.charAt(SRC.indexOf(arr[i]));
		return String.valueOf(arr);
	}
	public static void main(String[] args) {
		if (args.length == 0)
			throw new IllegalArgumentException("Missing argument");
		else if (args[0].equals("--test") || args[0].equals("-t")) {
			String[][] tests = {
				{ "Jr;;p Ept;f", "Hello World" },
				{ "Lmiyj od ,u jrtp", "Knuth is my hero" }
			};
			for (String[] test : tests) {
				String input = test[0];
				String expectedOutput = test[1];
				String actualOutput = keyboardShift(input);
				assert actualOutput.equals(expectedOutput) : String.format("keyboardShift(\"%s\") returned \"%s\", but expected \"%s\"", input, actualOutput, expectedOutput);
				System.out.println(String.format("keyboardShift(\"%s\") = \"%s\"", input, actualOutput));
			}
		} else
			System.out.println(String.format("keyboardShift(\"%s\") = \"%s\"", args[0], keyboardShift(args[0])));
	}
}