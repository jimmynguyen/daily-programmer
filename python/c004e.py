# Solution to /r/dailyprogrammer Challenge #4 Easy
# Jimmy Nguyen
#
# Prompt:
#     Create a random password generator
#
# Bonus:
#     For extra credit, allow the user to specify the amount of passwords to
# generate. For extra extra credit, allow the user to specify the length of the
# strings he wants to generate
#
# Link to challenge:
#     https://www.reddit.com/r/dailyprogrammer/comments/pm6oj/2122012_challenge_4_easy/
#
# Usage:
#     python c004e.py -h|-help
#         - prints the help menu
#
#     python c004e.py
#         - prints a random alphanumeric password with max length 50
#
#     python c004e.py <len_password>
#         - prints a random alphanumeric password with length <len_password>
#
#     python c004e.py <len_password> <num_passwords>
#         - prints <num_passwords> random alphanumeric passwords with length
#           <len_password>
from __future__ import print_function
import random
import sys


ALPHABET = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
MAX_PASSWORD_LENGTH = 50

def print_help_menu():
	print( \
			'\nUsage:' + \
			'\n    python c004e.py -h|-help' + \
			'\n        - prints the help menu' + \
			'\n    python c004e.py' + \
			'\n        - prints a random alphanumeric password with max length 50' + \
			'\n    python c004e.py <len_password>' + \
			'\n        - prints a random alphanumeric password with length <len_password>' + \
			'\n    python c004e.py <len_password> <num_passwords>' + \
			'\n        - prints <num_passwords> random alphanumeric passwords with length' + \
			'\n          <len_password>\n')

def is_integer(n):
	integer = True;
	try:
		int(n)
	except ValueError:
		integer = False
	return integer

def randi(max, num_rows=1, num_cols=1):
	nums_list = []
	for i in range(num_rows):
		nums = []
		for j in range(num_cols):
			nums.append(random.randint(1, max))
		nums_list.append(nums)
	return nums_list

def c004e(args):
	if len(args) >= 1:
		if args[0] in ['-h', '-help']:
			print_help_menu()
			return
		elif not is_integer(args[0]):
			print('\nInvalid len_password "{}". len_password must be an integer'.format(args[0]))
			print_help_menu()
			return
		len_password = int(args[0])
	else:
		len_password = random.randint(1, MAX_PASSWORD_LENGTH)

	if len(args) >= 2:
		if not is_integer(args[1]):
			print('\nInvalid num_passwords "{}". num_passwords must be an integer'.format(args[1]))
			print_help_menu()
			return
		num_passwords = int(args[1])
	else:
		num_passwords = 1

	nums_list = randi(len(ALPHABET)-1, num_passwords, len_password)
	print()
	for i in range(num_passwords):
		password = ''
		for j in range(len_password):
			password += ALPHABET[nums_list[i][j]]
		print('{}'.format(password))
	print()

if __name__ == '__main__':
	c004e(sys.argv[1:])
