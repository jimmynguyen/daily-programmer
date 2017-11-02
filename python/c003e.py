# Solution to /r/dailyprogrammer Challenge #3 Easy
# Jimmy Nguyen
#
# Prompt:
#     Write a program that can encrypt texts with an alphabetical Caesar
# cipher. The cipher can ignore numbers, symbols, and whitespace.
#
# Bonus:
#     For extra credit, add a "decrypt" function to your program
#
# Link to challenge:
#     https://www.reddit.com/r/dailyprogrammer/comments/pkw2m/2112012_challenge_3_easy/
import sys

class CaesarCipher:
	def encrypt(self, message, n):
		A_ord = ord('A')
		a_ord = ord('a')
		encrypted_message = ''
		for letter in message:
			letter_ord = ord(letter)
			if letter >= 'A' and letter <= 'Z':
				encrypted_message += chr(((letter_ord - A_ord + n) % 26) + A_ord)
			elif letter >= 'a' and letter <= 'z':
				encrypted_message += chr(((letter_ord - a_ord + n) % 26) + a_ord)
			else:
				encrypted_message += letter
		return encrypted_message
	def decrypt(self, message, n):
		return self.encrypt(message, -n)

def print_help_menu():
	print( \
			'\nUsage: python c003e.py [option] [message] [n]' + \
			'\n    [option]     -d: decrypt' + \
			'\n                 -e: encrypt' + \
			'\n    [message]    The message to encrypt or decrypt' + \
			'\n    [n]          n is the integer to shift by. A' + \
			'\n                 negative n will shift left\n')

def is_integer(n):
	integer = True;
	try:
		int(n)
	except ValueError:
		integer = False
	return integer

def c003e(args):
	if len(args) < 3:
		print('\nInvalid number of arguments')
		print_help_menu()
	elif args[0] not in ['-d', '-e']:
		print('\nInvalid option "{}"'.format(args[0]))
		print_help_menu()
	elif not is_integer(args[-1]):
		print('\nInvalid n "{}". n must be an integer'.format(args[-1]))
		print_help_menu()
	else:
		caesar_cipher = CaesarCipher()
		message = ' '.join(args[1:-1])
		n = int(args[-1])
		if '-d' == args[0]:
			ciphered_message = caesar_cipher.decrypt(message, n)
		else:
			ciphered_message = caesar_cipher.encrypt(message, n)
		print('\nPlain : {}\nCipher: {}\n'.format(message, ciphered_message))

if __name__ == '__main__':
	c003e(sys.argv[1:])
