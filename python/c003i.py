# Solution to /r/dailyprogrammer Challenge #3 Intermediate
# Jimmy Nguyen
#
# Prompt:
#     Write a program that can encrypt texts with an alphabetical substitution
# cipher. The cipher can ignore numbers, symbols, and whitespace.
#
# Bonus (not implemented):
#     For extra credit, make it encrypt whitespace, numbers, and symbols
#
# Link to challenge:
#     https://www.reddit.com/r/dailyprogrammer/comments/pkwb1/2112012_challenge_3_intermediate/
#
# Note:
#     The following implementation performs a simple substitution with groups
# of size 4:
#     https://en.wikipedia.org/wiki/Substitution_cipher#Simple_substitution
import re
import sys

GROUP_SIZE = 4

def print_help_menu():
	print( \
			'\nUsage: python c003i.py [message] [keyword]' + \
			'\n    [message]    message to encrypt' + \
			'\n    [keyword]    keyword for cipher\n')

def unique(l):
	s = []
	for e in l:
		if e not in s:
			s.append(e)
	return s

def get_alphabet(keyword):
	original_alphabet = list(map(chr, range(ord('A'), ord('Z')+1)))
	return unique(list(keyword.upper()) + original_alphabet)

def encrypt(message, keyword):
	message = re.sub("[^A-Z]", "", message.upper())
	alphabet = get_alphabet(keyword)
	print(alphabet)
	encrypted_message = ''
	n = len(message)
	for i in range(0, n, GROUP_SIZE):
		j = i+GROUP_SIZE-1
		if j >= n:
			j = n-1
		for k in range(i, j+1):
			encrypted_message += alphabet[ord(message[k]) - ord('A')]
		if j < n-1:
			encrypted_message += ' '
	return encrypted_message

def c003i(args):
	if len(args) < 2:
		print('\nInvalid number of arguments "{}"'.format(len(args)))
		print_help_menu()
	else:
		message = args[0]
		keyword = args[1]
		print('\nPlain : {}\nCipher: {}\n'.format(message, encrypt(message, keyword)))

if __name__ == '__main__':
	c003i(sys.argv[1:])
