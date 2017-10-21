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
	private static final String START_PATH_ID = "START";
	private static Scanner scanner;

	private static class Path {
		public String path_id;
		public String description;
		public Map<String, Option> options;
		public String next_path_id;
		public Path(String path_id) {
			this.path_id = path_id;
			this.description = "";
			this.options = new HashMap<String, Option>();
			this.next_path_id = null;
		}
		public void addOption(String line) {
			String[] parts = line.split(" - ", 3);
			String key = parts[0].replaceAll("^(-| )+", "");
			String description = parts[1];
			String path_id = parts[2];
			options.put(key, new Option(key, description, path_id, line));
		}
		public void print() {
			System.out.printf("\n\n-----\npath_id: \"%s\"\ndescription\n-----\n\n")
		}
	}

	private static class Option {
		public String key;
		public String description;
		public String path_id;
		public String line;
		public Option(String key, String description, String path_id, String line) {
			this.key = key;
			this.description = description;
			this.path_id = path_id;
			this.line = line;
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
					path.addOption(line);
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
		Map<String, Path> paths = read_story(args[0]);
		scanner = new Scanner(System.in);
		System.out.printf("\n==================================================\n");
		String name = input(String.format("\n> What is your name?\n> "));
		System.out.printf("\n> Welcome %s! Let the adventure begin...\n", name);
		boolean is_terminate = false;
		while (!is_terminate) {
			Path path = paths.get(START_PATH_ID);
			while (!path.options.isEmpty() || null != path.next_path_id) {
				if (!path.description.isEmpty()) {
					System.out.printf("\n%s", path.description);
				}
				if (!path.options.isEmpty()) {
					String key = "";
					while (!path.options.containsKey(key)) {
						for (Option option : path.options.values()) {
							System.out.printf("\n%s", option.line);
						}
						key = input(String.format("\n\n> "));
						if (path.options.containsKey(key)) {
							path = paths.get(path.options.get(key).path_id);
						} else {
							System.out.printf("\n> Invalid option. Please select one of the following options:");
						}
					}
				} else {
					path = paths.get(path.next_path_id);
				}
			}
			System.out.printf("\n%s", path.description);
			String command = null;
			while (null != command && !"y".equals(command.toLowerCase()) && !"n".equals(command.toLowerCase())) {
				if (command != null) {
					System.out.printf("\n> Invalid option. Please enter a valid option:");
				}
				command = input(String.format("\n> Do you want to start over? (y|n)\n> "));
			}
			if ("n".equals(command.toLowerCase())) {
				is_terminate = true;
			}
		}
	}
}
