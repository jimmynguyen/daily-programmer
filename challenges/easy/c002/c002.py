# Solution to /r/dailyprogrammer Challenge #2 Easy
# Jimmy Nguyen
#
# Prompt:
#     Create a calculator program that can compute a formula: F = M * A
#
# Bonus:
#     For extra credit, the program should also be able to compute M and A
#
# Link to challenge:
#     https://www.reddit.com/r/dailyprogrammer/comments/pjbj8/easy_challenge_2/
def c002e():
	is_terminate = False
	while not is_terminate:
		print_menu()
		is_terminate = select_formula(raw_input('\t>> '));

def print_menu():
	print('\n==================================================\n')
	print('\t1. F = M * A');
	print('\t2. M = F / A');
	print('\t3. A = F / M');
	print('\tq. exit');
	print('\n\t-------------------------\n');

def select_formula(command):
	is_terminate = False
	if command == '1':
		prompt_and_compute('F')
	elif command == '2':
		prompt_and_compute('M')
	elif command == '3':
		prompt_and_compute('A')
	elif command == 'q':
		is_terminate = True
	return is_terminate

def prompt_and_compute(desired_variable):
	variables = ['F', 'M', 'A']
	for _, variable in enumerate(variables):
		if variable == desired_variable:
			variables.remove(variable)
			break
	print
	input1 = float(raw_input('\t{} = '.format(variables[0])))
	input2 = float(raw_input('\t{} = '.format(variables[1])))
	if desired_variable == 'M' or desired_variable == 'A':
		output = input1 / input2
	else:
		output = input1 * input2
	print('\n\t{} = {:.2f}'.format(desired_variable, output))
	raw_input('\n\tPress ENTER to go back to the menu...')

if __name__ == '__main__':
	c002e()
