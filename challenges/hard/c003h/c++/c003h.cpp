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
#include <algorithm>
#include <fstream>
#include <iostream>
#include <map>
#include <string>
#include <vector>

void print_help_menu()
{
	std::cout << std::endl << "Usage: ./a.out [f1] [f2]";
	std::cout << std::endl << "    [f1]    name of file of scrambled words";
	std::cout << std::endl << "    [f2]    name of file of word list";
	std::cout << std::endl << std::endl;
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

std::vector<std::string> sort_letters_in_words(std::vector<std::string> words)
{
	std::vector<std::string> sorted_words;
	std::vector<std::string>::iterator word;
	for(word = words.begin(); word != words.end(); ++word) {
		std::string sorted_word = *word;
		std::sort(sorted_word.begin(), sorted_word.end());
		sorted_words.push_back(sorted_word);
	}
	return sorted_words;
}

std::map< int, std::vector<std::string> > generate_map(
		std::vector<std::string> sorted_scrambled_words,
		std::vector<std::string> sorted_word_list,
		std::vector<std::string> word_list)
{
	std::map<int, std::vector<std::string> > word_length_to_words_map;
	std::map<int, std::vector<std::string> >::iterator word_length_to_words_map_it;
	std::vector<std::string>::iterator sorted_scrambled_word;
	std::string unscrambled_word;
	int ndx, len;
	for (sorted_scrambled_word = sorted_scrambled_words.begin();
			sorted_scrambled_word != sorted_scrambled_words.end();
			++sorted_scrambled_word)
	{
		ndx = std::find(sorted_word_list.begin(), sorted_word_list.end(), *sorted_scrambled_word) - sorted_word_list.begin();
		unscrambled_word = word_list[ndx];
		len = unscrambled_word.length();
		word_length_to_words_map_it = word_length_to_words_map.find(len);
		if (word_length_to_words_map_it != word_length_to_words_map.end())
			word_length_to_words_map.insert(
					std::make_pair(len, std::vector<std::string>()));
		word_length_to_words_map[len].push_back(unscrambled_word);
	}
	return word_length_to_words_map;
}

std::vector<std::string> get_unscrambled_words(
		std::map< int, std::vector<std::string> > word_length_to_words_map)
{
	std::vector<std::string> unscrambled_words;
	std::map<int, std::vector<std::string> >::iterator word_length_to_words_map_it;
	for (word_length_to_words_map_it = word_length_to_words_map.begin();
			word_length_to_words_map_it != word_length_to_words_map.end();
			++word_length_to_words_map_it)
	{
		unscrambled_words.insert(unscrambled_words.end(),
				word_length_to_words_map_it->second.begin(),
				word_length_to_words_map_it->second.end());
	}
	return unscrambled_words;
}

void print(std::vector<std::string> words)
{
	std::cout << std::endl;
	for(std::vector<std::string>::iterator word = words.begin(); word != words.end(); ++word) {
		std::cout << *word << ' ';
	}
	std::cout << std::endl;
}

int main(int argc, char *argv[])
{
	if (argc < 3)
	{
		std::cout << std::endl << "Invalid number of arguments \"" << argc-1 << "\"" << std::endl;
		print_help_menu();
	}
	else
	{
		std::vector<std::string> scrambled_words = read_all_lines(argv[1]);
		std::vector<std::string> word_list = read_all_lines(argv[2]);

		std::vector<std::string> sorted_scrambled_words = sort_letters_in_words(scrambled_words);
		std::vector<std::string> sorted_word_list = sort_letters_in_words(word_list);

		std::map< int, std::vector<std::string> > word_length_to_words_map =
				generate_map(sorted_scrambled_words, sorted_word_list, word_list);

		std::vector<std::string> unscrambled_words =
				get_unscrambled_words(word_length_to_words_map);

		print(unscrambled_words);
	}
	return 0;
}
