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
#include <fstream>
#include <iostream>
#include <map>
#include <sys/stat.h>
#include <vector>

std::string input(std::string prompt)
{
	std::string input;
	std::cout << prompt;
	std::getline(std::cin, input);
	return input;
}

void error(std::string message)
{
	std::cout << std::endl << "ERROR: " << message << std::endl;
}

// source: https://stackoverflow.com/questions/25829143/trim-whitespace-from-a-string
std::string trim(const std::string& str, char delimiter)
{
	size_t first = str.find_first_not_of(delimiter);
	if (std::string::npos == first)
	{
		return str;
	}
	size_t last = str.find_last_not_of(delimiter);
	return str.substr(first, (last - first + 1));
}

char lower_char(char c)
{
	if (c >= 'A' && c <= 'Z')
		c -= ('Z' - 'z');
	return c;
}

std::string lower(const std::string& str)
{
	std::string new_str = "";
	for (unsigned int i = 0; i < str.length(); i++)
	{
		new_str += lower_char(str[i]);
	}
	return new_str;
}

class Option
{
	public:
		Option() {};
		Option(std::string key, std::string description, std::string path_id, std::string line);
		std::string key;
		std::string description;
		std::string path_id;
		std::string line;
};

Option::Option(std::string _key, std::string _description, std::string _path_id, std::string _line)
{
	key = _key;
	description = _description;
	path_id = _path_id;
	line = _line;
}

class Path
{
	public:
		Path() {};
		Path(std::string path_id);
		std::string path_id;
		std::string description;
		std::map<std::string, Option> options;
		std::string next_path_id;
		void add_option(std::string line);
		void print_description();
		void print_options();
};

Path::Path(std::string _path_id)
{
	path_id = _path_id;
	description = "";
	options = std::map<std::string, Option>();
	next_path_id = "";
}

void Path::add_option(std::string line)
{
	std::string delimiter = " - ";
	std::string line_copy = line;
	std::string key = lower(trim(trim(line.substr(0, line.find(delimiter)), '-'), ' '));
	line = line.substr(line.find(delimiter) + delimiter.length(), line.length());
	std::string description = trim(line.substr(0, line.find(delimiter)), ' ');
	line = line.substr(line.find(delimiter) + delimiter.length(), line.length());
	std::string path_id = trim(line.substr(0, line.find(delimiter)), ' ');
	options.insert(std::make_pair(key, Option(key, description, path_id, line_copy)));
}

void Path::print_description()
{
	if (!description.empty())
		std::cout << std::endl << description;
}

void Path::print_options()
{
	std::map<std::string, Option>::iterator it;
	for (it = options.begin(); it != options.end(); it++)
		std::cout << std::endl << it->second.line;
}

void validate_file_name(int argc)
{
	if (argc < 2)
		throw 0;
}

void validate_file(char *file_name)
{
	struct stat buffer;
	if (stat(file_name, &buffer) != 0)
		throw 1;

}

std::vector<std::string> read_all_lines(char *file_name)
{
	std::ifstream file(file_name);
	std::vector<std::string> lines = std::vector<std::string>();
	std::string line;
	while (std::getline(file, line))
	{
		lines.push_back(line);
	}
	return lines;
}

std::map<std::string, Path> read_story(char *file_name)
{
	validate_file(file_name);
	std::vector<std::string> lines = read_all_lines(file_name);
	std::map<std::string, Path> paths = std::map<std::string, Path>();
	std::vector<std::string>::iterator it;
	std::string path_id;
	for (it = lines.begin(); it != lines.end(); it++)
	{
		std::string line(*it);
		if (!line.empty())
		{
			if (line[0] == '@')
			{
				path_id = trim(trim(line, '@'), ' ');
				paths.insert(std::make_pair(path_id, Path(path_id)));
			}
			else if (line[0] == '>')
				paths[path_id].description += line + "\n";
			else if (line[0] == '-')
				paths[path_id].add_option(line);
			else if (line[0] == '=')
				paths[path_id].next_path_id = trim(trim(line, '='), ' ');
		}
	}
	return paths;
}

void greet_user()
{
	std::cout << std::endl << "==================================================" << std::endl;
	std::string name = input("\n> What is your name?\n> ");
	std::cout << std::endl << "> Welcome " << name << "! Let the adventure begin..." << std::endl;
}

Path get_next_path(std::map<std::string, Path> paths, Path path)
{
	std::string key = "";
	std::map<std::string, Option> options = path.options;
	while (options.find(key) == options.end())
	{
		path.print_options();
		key = lower(input("\n\n> "));
		if (options.find(key) != options.end())
		{
			path = paths[path.options[key].path_id];
		}
		else
			std::cout << std::endl << "> Invalid option. Please select one of the following options:" << std::endl;
	}
	return path;
}

bool prompt_termination()
{
	bool is_terminate = false;
	bool is_first_pass = true;
	std::string command = "";
	while (is_first_pass || (command.compare("y") != 0 && command.compare("n") != 0))
	{
		if (is_first_pass)
			is_first_pass = false;
		else
			std::cout << std::endl << "> Invalid option. Please enter a valid option." << std::endl;
		command = lower(input("\n> Do you want to start over? (y|n)\n> "));
	}
	if (command.compare("n") == 0)
		is_terminate = true;
	return is_terminate;
}

int main(int argc, char *argv[])
{
	try
	{
		validate_file_name(argc);
		std::map<std::string, Path> paths = read_story(argv[1]);
		greet_user();
		bool is_terminate = false;
		while (!is_terminate)
		{
			Path path = paths["START"];
			while (!path.options.empty() || !path.next_path_id.empty())
			{
				path.print_description();
				if (!path.options.empty())
					path = get_next_path(paths, path);
				else
					path = paths[path.next_path_id];
			}
			path.print_description();
			is_terminate = prompt_termination();
		}
	}
	catch (int n)
	{
		if (0 == n) {
			error("File name missing");
		} else if (1 == n) {
			char error_message [50];
			sprintf(error_message, "File \"%s\" not found", argv[1]);
			error(error_message);
		}
	}
	return 0;
}
