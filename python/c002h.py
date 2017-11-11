# Solution to /r/dailyprogrammer Challenge #2 Hard
# Jimmy Nguyen
#
# Prompt:
#     Create a stopwatch program. This program should have start, stop, and lap
# options, and it should write out to a file to be viewed later.
#
# Link to challenge:
#     https://www.reddit.com/r/dailyprogrammer/comments/pjsdx/difficult_challenge_2/
#
# Usage:
#     python c002h.py
#         - launches program
import datetime

FILENAME = '../trash/c002h.log';

def write_to_file(message):
	with open(FILENAME, 'a') as file:
		file.write('{}\n'.format(message))
	print message

def validate_command(prompt_user, command):
	if prompt_user:
		print '> invalid command "{}". please enter a valid command:'.format(command)
	else:
		prompt_user = True
	return prompt_user

def c002h():
	print ''
	print '=================================================='
	print '> STOPWATCH ({})'.format(FILENAME)
	print '=================================================='
	is_terminate = False
	prompt_user = False
	start_time = None
	command = ''
	while not is_terminate:
		if prompt_user:
			command = raw_input('> ');
			print '--------------------------------------------------'
		if start_time is None:
			if command == 'q':
				is_terminate = True
			elif command == 's':
				start_time = datetime.datetime.now()
				write_to_file('started: {}'.format(start_time.strftime('%m/%d/%Y %H:%M:%S')))
				prompt_user = False
			else:
				prompt_user = validate_command(prompt_user, command)
				print '> enter s to start'
				print '> enter q to quit'
				print '--------------------------------------------------'
		else:
			end_time = datetime.datetime.now()
			end_datestr = end_time.strftime('%m/%d/%Y %H:%M:%S')
			seconds_passed = (end_time - start_time).total_seconds()
			if command == 'e':
				write_to_file('ended  : {} - {:.4f} seconds passed'.format(end_datestr, seconds_passed))
				start_time = None
				prompt_user = False
			elif command == 'l':
				write_to_file('lapped : {} - {:.4f} seconds passed'.format(end_datestr, seconds_passed))
				start_time = end_time
			else:
				prompt_user = validate_command(prompt_user, command)
			if not start_time is None:
				print '> enter e to stop'
				print '> enter l to lap'
				print '--------------------------------------------------'
	print ''

if __name__ == '__main__':
	c002h()
