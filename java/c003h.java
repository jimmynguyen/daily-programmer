/*
 * Solution to /r/dailyprogrammer Challenge #3 Hard
 * Jimmy Nguyen
 *
 * Prompt:
 *     Write a program that will take a list of scrambled words and compare
 * them against a list of words to unscramble them. See the resources folder
 * for text files with pattern "c003h_*.txt" for a file of scrambled words and
 * a file with a list of words
 *
 * Bonus:
 *     For extra credit, sort the words by length when finished
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pkwgf/2112012_challenge_3_difficult/
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class c003h {
	private static void print_help_menu() {
		System.out.printf(
				"\nUsage: java c003h [f1] [f2]" +
				"\n    [f1]    name of file of scrambled words" +
				"\n    [f1]    name of file of word list\n\n");
	}

	private static List<String> sort_letters_in_words(List<String> words) {
		List<String> sorted_words = new ArrayList<String>(words.size());
		for (String word : words) {
			char[] letters = word.toCharArray();
			Arrays.sort(letters);
			sorted_words.add(new String(letters));
		}
		return sorted_words;
	}

	private static Map<Integer, List<String>> generate_map(
			List<String> sorted_scrambled_words,
			List<String> sorted_word_list,
			List<String> word_list
	) {
		Map<Integer, List<String>> word_length_to_words_map = new HashMap<Integer, List<String>>();
		String unscrambled_word;
		int n;
		for (String sorted_scrambled_word : sorted_scrambled_words) {
			unscrambled_word = word_list.get(sorted_word_list.indexOf(sorted_scrambled_word));
			n = unscrambled_word.length();
			if (!word_length_to_words_map.containsKey(n))
				word_length_to_words_map.put(n, new ArrayList<String>());
			word_length_to_words_map.get(n).add(unscrambled_word);
		}
		return word_length_to_words_map;
	}

	private static List<String> get_unscrambled_words(
			Map<Integer, List<String>> word_length_to_words_map
	) {
		List<String> unscrambled_words = new ArrayList<String>();
		word_length_to_words_map.values().forEach(words -> unscrambled_words.addAll(words));
		return unscrambled_words;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		if (args.length < 2) {
			System.out.printf("\nInvalid number of arguments \"%d\"\n", args.length);
			print_help_menu();
		} else {
			List<String> scrambled_words = Files.readAllLines(Paths.get(args[0]));
			List<String> word_list = Files.readAllLines(Paths.get(args[1]));

			List<String> sorted_scrambled_words = sort_letters_in_words(scrambled_words);
			List<String> sorted_word_list = sort_letters_in_words(word_list);

			Map<Integer, List<String>> word_length_to_words_map = generate_map(sorted_scrambled_words, sorted_word_list, word_list);

			List<String> unscrambled_words = get_unscrambled_words(word_length_to_words_map);

			System.out.println(Arrays.toString(unscrambled_words.toArray()));
		}
	}
}
