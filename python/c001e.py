# Solution to /r/dailyprogrammer Challenge #1 Easy
# Jimmy Nguyen
#
# Prompt:
#     Create a program that will ask the user's name, age, and reddit username.
# Have it tell them the information back, in the format:
# =============================================================================
# your name is (blank), you are (blank) years old, and your username is (blank)
# =============================================================================
#
# Bonus:
#     For extra credit, have the program log this information in a file to be
# accessed later.
#
# Link to challenge:
#     https://www.reddit.com/r/dailyprogrammer/comments/pih8x/easy_challenge_1/
def c001e():
	# ask for user's information
	name     = raw_input('Name: ')
	age      = raw_input('Age : ')
	username = raw_input('Reddit Username: ')

	# format the output string
	output = 'your name is {}, you are {} years old, and your username is {}\n'.format(name, age, username)

	# print the output string
	print('\n' + output + '\n')

	# save the output string to a file
	saveToFile(output)

def saveToFile(output):
	fh = open('info.log', 'a')
	fh.write(output)
	fh.close()
	print('Your information has been saved in the file "info.log" in the current directory\n')

if __name__ == '__main__':
	c001e()
