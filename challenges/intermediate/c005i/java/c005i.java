/*
 * Solution to /r/dailyprogrammer Challenge #5 Intermediate
 * Jimmy Nguyen
 *
 * Prompt:
 *     Your challenge today is to write a program that can find the number of
 * anagrams within a .txt file. For example, "snap" would be an anagram of
 * "pans", and "skate" would be an anagram of "stake".
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pnhtj/2132012_challenge_5_intermediate/
 *
 * Compilation:
 *.    javac c005i.java
 *
 * Usage:
 *     java c005i -h|-help
 *         - prints the help menu
 *
 *     java c005i -t|-test
 *         - runs through all preset test cases and throws an error if one
 *           fails
 *
 *     java c005i -f <filename>
 *         - prints the number of anagrams found in the input file
 *
 *     java c005i -f <filename> -d
 *         - prints the number of anagrams found in the input file and prints
 *           the anagrams
 *
 * Strategy:
 *     1. read file <file>
 *     2. remove punctuation
 *     3. get list of unique words in file <words>
 *     4. sort letters in words alphabetically <sorted_words>
 *     5. get list of unique sorted words <unique_sorted_words>
 *     6. for each unique sorted word <sorted_word>:
 *            a. find the indices of other instances of <sorted_word>
 *            b. print non-sorted words at those same indices in <words>
 *            c. remove instances of <sorted_word> from <sorted_words>
 *            d. increment <num_anagrams>
 */
public class c005i {
	private static boolean is_debug = false;

	public static void main(String[] args) {
		if (args.length == 0 || Arrays.asList("-h", "-help").contains(args[0]))
			print_help_menu();
		else if (Arrays.asList("-t", "-test").contains(args[0]))
			run_tests();
		else {

		}
	}
}
