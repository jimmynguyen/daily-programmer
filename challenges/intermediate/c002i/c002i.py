# Solution to /r/dailyprogrammer Challenge #2 Intermediate
# Jimmy Nguyen
#
# Prompt:
#     Create a short text adventure that will call the user by their name. The
# text adventure should use standard text adventure commands ("l, n, s, e, i,
# etc.")
#
# Bonus:
#     For extra credit, make sure the program does not fault, quit, glitch,
# fail, or loop no matter what is put in, even empty text or spaces
#
# Link to challenge:
#     https://www.reddit.com/r/dailyprogrammer/comments/pjbuj/intermediate_challenge_2/
import os.path
import re
import sys

START_PATH_ID = "START"

class Path:
	def __init__(self, path_id):
		self.path_id = path_id
		self.description = ''
		self.options = {}
		self.next_path_id = None
	def add_option(self, line):
		parts = line.split(' - ')
		key = re.sub('^(-| )+', '', parts[0]).lower()
		description = parts[1].strip()
		path_id = parts[2].strip()
		self.options[key] = Option(key, description, path_id, line)
	def print_description(self):
		if self.description:
			print('{}'.format(self.description))
	def print_options(self):
		for _, option in self.options.items():
			print('{}'.format(option.line))

class Option:
	def __init__(self, key, description, path_id, line):
		self.key = key
		self.description = description
		self.path_id = path_id
		self.line = line

def validate_file_name(args):
	if not args:
		raise Exception('File name missing')

def validate_file(file_name):
	if not os.path.exists(file_name):
		raise FileNotFoundError()

def read_story(file_name):
	validate_file(file_name)
	lines = [line.strip() for line in open(file_name, 'r')]
	paths = {}
	path  = None
	for line in lines:
		if line:
			if line[0] is '@':
				if None is not path:
					paths[path.path_id] = path
				path = Path(re.sub('^(@| )+', '', line))
			elif line[0] is '>':
				path.description = '{}{}\n'.format(path.description, line)
			elif line[0] is '-':
				path.add_option(line)
			elif line[0] is '=':
				path.next_path_id = re.sub('^(=| )+', '', line)
	if None is not path:
		paths[path.path_id] = path
	return paths

def greet_user():
	print('\n==================================================\n')
	name = raw_input('> What is your name?\n> ')
	print('\n> Welcome {}! Let the adventure begin...\n'.format(name))

def get_next_path(paths, path):
	key = ''
	options = path.options
	while not key in options:
		path.print_options()
		key = raw_input('\n> ').lower()
		if key in options:
			print
			path = paths[path.options[key].path_id]
		else:
			print('\n> Invalid option. Please select one of the following options:\n')
	return path

def prompt_termination():
	is_terminate = False
	command = None
	while None is command or ('y' != command and 'n' != command):
		if None is not command:
			print('> Invalid option. Please enter a valid option.\n')
		command = raw_input('> Do you want to start over? (y|n)\n> ').lower()
		print
	if 'n' == command:
		is_terminate = True
	return is_terminate

def c002i(args):
	validate_file_name(args)
	paths = read_story(args[0])
	greet_user()
	is_terminate = False
	while not is_terminate:
		path = paths[START_PATH_ID]
		while path.options or None is not path.next_path_id:
			path.print_description()
			if path.options:
				path = get_next_path(paths, path)
			else:
				path = paths[path.next_path_id]
		path.print_description()
		is_terminate = prompt_termination()

if __name__ == '__main__':
	c002i(sys.argv[1:])
