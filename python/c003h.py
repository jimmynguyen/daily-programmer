# Solution to /r/dailyprogrammer Challenge #3 Hard
# Jimmy Nguyen
#
# Prompt:
#     Write a program that will take a list of scrambled words and compare
# them against a list of words to unscramble them. See the resources folder
# for text files with pattern "c003h_*.txt" for a file of scrambled words and
# a file with a list of words
#
# Bonus:
#     For extra credit, sort the words by length when finished
#
# Link to challenge:
#     https://www.reddit.com/r/dailyprogrammer/comments/pkwgf/2112012_challenge_3_difficult/
import sys

def print_help_menu():
	print( \
			'\nUsage: python c003h.py [f1] [f2]' + \
			'\n    [f1]    name of file of scrambled words' + \
			'\n    [f2]    name of file of word list\n\n')

def sort_letters_in_words(words):
	return [''.join(sorted(word)) for word in words]

def generate_map(sorted_scrambled_words, sorted_word_list, word_list):
	word_length_to_words_map = {}
	for sorted_scrambled_word in sorted_scrambled_words:
		unscrambled_word = word_list[sorted_word_list.index(sorted_scrambled_word)]
		n = len(unscrambled_word)
		if n not in word_length_to_words_map:
			word_length_to_words_map[n] = []
		word_length_to_words_map[n].append(unscrambled_word)
	return word_length_to_words_map

def get_unscrambled_words(word_length_to_words_map):
	unscrambled_words = []
	for key in sorted(word_length_to_words_map.keys()):
		unscrambled_words += word_length_to_words_map[key]
	return unscrambled_words

def c003h(args):
	if len(args) < 2:
		print('\nInvalid number of arguments "{}"'.format(len(args)))
		print_help_menu()
	else:
		scrambled_words = [line.strip() for line in open(args[0], 'r')]
		word_list = [line.strip() for line in open(args[1], 'r')]

		sorted_scrambled_words = sort_letters_in_words(scrambled_words)
		sorted_word_list = sort_letters_in_words(word_list)

		word_length_to_words_map = generate_map(sorted_scrambled_words, sorted_word_list, word_list)

		unscrambled_words = get_unscrambled_words(word_length_to_words_map)

		print unscrambled_words

if __name__ == '__main__':
	c003h(sys.argv[1:])
