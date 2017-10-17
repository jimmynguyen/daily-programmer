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
import java.util.Scanner;

public final class c002i {
	private static Scanner scanner;

	private static class Path {
		public String path_id;
		public String description;
		public List<Option> options;
		public String next_path_id;
		public Path(String path_id) {
			this.path_id = path_id;
			this.description = "";
			this.options = new ArrayList<Option>();
			this.next_path_id = null;
		}
	}

	private static class Option {
		public String key;
		public String description;
		public String path_id;
		public String line;
		public Option(String line) {
			String[] parts = line.split(" - ", 3);
			this.line = line;
			this.key = parts[0].replaceAll("^(-| )+", "");
			this.description = parts[1];
			this.path_id = parts[2];
		}
	}

	private static Map<String, Path> read_story(String file_name) throws FileNotFoundException, IOException {
		if (!(new File(file_name)).exists()) {
			throw new FileNotFoundException();
		}
		List<String> lines = Files.readAllLines(Paths.get(file_name));
		Map<String, Path> paths = new HashMap<String, Path>();
		String path_id = null;
		Path path = null;
		for (String line : lines) {
			if (!line.isEmpty()) {
				if (line.charAt(0) == '@') {
					if (null != path) {
						paths.put(path.path_id, path);
					}
					path = new Path(line.replaceAll("^(@| )+", ""));
				} else if (line.charAt(0) == '>') {
					path.description += line + "\n";
				} else if (line.charAt(0) == '-') {
					path.options.add(new Option(line));
				} else if (line.charAt(0) == '=') {
					path.next_path_id = line.replaceAll("^(=| )+", "");
				}
			}
		}
		return paths;
	}

	private static String input(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		if (args.length < 1)
			System.out.printf("\n> File name missing");
		read_story(args[0]);
		// scanner = new Scanner(System.in);
		// boolean is_terminate = false;
		// while (!is_terminate) {
		// 	print_menu();
		// 	is_terminate = select_formula(input(""));
		// }
	}
}
