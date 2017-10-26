/*
 * Solution to /r/dailyprogrammer Challenge #2 Intermediate
 * Jimmy Nguyen
 *
 * Prompt:
 *     Create a short text adventure that will call the user by their name. The
 * text adventure should use standard text adventure commands ("l, n, s, e, i,
 * etc.")
 *
 * Bonus:
 *     For extra credit, make sure the program does not fault, quit, glitch,
 * fail, or loop no matter what is put in, even empty text or spaces
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pjbuj/intermediate_challenge_2/
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Scanner;

public final class c002i {
	private static final String START_PATH_ID = "START";
	private static Scanner scanner;
	private static String name;

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
		public void add_option(String line) {
			String[] parts = line.split(" - ", 3);
			String key = parts[0].replaceAll("^(-| )+", "").toLowerCase();
			String description = parts[1].trim();
			String path_id = parts[2].trim();
			this.options.put(key, new Option(key, description, path_id, line));
		}
		public void print_description() {
			if (!this.description.isEmpty())
				System.out.printf("\n%s", this.description);
		}
		public void print_options() {
			for (Option option : this.options.values())
				System.out.printf("\n%s", option.line);
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

	private static void validate_file_name(String[] args) throws Exception {
		if (args.length == 0)
			throw new Exception("File name missing");
	}

	private static void validate_file(String file_name) throws FileNotFoundException {
		if (!(new File(file_name)).exists())
			throw new FileNotFoundException();
	}

	private static Map<String, Path> read_story(String file_name) throws FileNotFoundException, IOException {
		validate_file(file_name);
		List<String> lines = Files.readAllLines(Paths.get(file_name));
		Map<String, Path> paths = new HashMap<String, Path>();
		Path path = null;
		for (String line : lines) {
			if (!line.isEmpty()) {
				if (line.charAt(0) == '@') {
					path = new Path(line.replaceAll("^(@| )+", ""));
					paths.put(path.path_id, path);
				} else if (line.charAt(0) == '>')
					path.description += line + "\n";
				else if (line.charAt(0) == '-')
					path.add_option(line);
				else if (line.charAt(0) == '=')
					path.next_path_id = line.replaceAll("^(=| )+", "");
			}
		}
		return paths;
	}

	private static String input(String prompt) {
		if (null == scanner)
			scanner = new Scanner(System.in);
		System.out.print(prompt);
		return scanner.nextLine();
	}

	private static void greet_user() {
		System.out.printf("\n==================================================\n");
		name = input(String.format("\n> What is your name?\n> "));
		System.out.printf("\n> Welcome %s! Let the adventure begin...\n", name);
	}

	private static Path get_next_path(Map<String, Path> paths, Path path) {
		String key = "";
		Map<String, Option> options = path.options;
		while (!options.containsKey(key)) {
			path.print_options();
			key = input(String.format("\n\n> ")).toLowerCase();
			if (options.containsKey(key))
				path = paths.get(path.options.get(key).path_id);
			else
				System.out.printf("\n> Invalid option. Please select one of the following options:");
		}
		return path;
	}

	private static boolean prompt_termination() {
		boolean is_terminate = false;
		String command = null;
		while (null == command || !Arrays.asList("y", "n").contains(command.toLowerCase())) {
			if (null != command)
				System.out.printf("\n> Invalid option. Please enter a valid option.\n");
			command = input(String.format("\n> Do you want to start over? (y|n)\n> "));
		}
		if ("n".equals(command.toLowerCase()))
			is_terminate = true;
		return is_terminate;
	}

	public static void main(String[] args) throws Exception, FileNotFoundException, IOException {
		validate_file_name(args);
		Map<String, Path> paths = read_story(args[0]);
		greet_user();
		boolean is_terminate = false;
		while (!is_terminate) {
			Path path = paths.get(START_PATH_ID);
			while (null != path && (!path.options.isEmpty() || null != path.next_path_id)) {
				path.print_description();
				if (!path.options.isEmpty())
					path = get_next_path(paths, path);
				else
					path = paths.get(path.next_path_id);
			}
			path.print_description();
			is_terminate = prompt_termination();
		}
	}
}
