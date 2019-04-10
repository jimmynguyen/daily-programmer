% Solution to /r/dailyprogrammer Challenge #2 Easy
% Jimmy Nguyen
%
% Prompt:
%     Create a calculator program that can compute a formula: F = M * A
%
% Bonus:
%     For extra credit, the program should also be able to compute M and A
%
% Link to challenge:
%     https://www.reddit.com/r/dailyprogrammer/comments/pjbj8/easy_challenge_2/
function c002e()
	is_terminate = false;
	while !is_terminate
		print_menu();
		is_terminate = select_formula(input('', 's'));
	end

function print_menu()
	fprintf('\n==================================================\n');
	fprintf('\n\t1. F = M * A');
	fprintf('\n\t2. M = F / A');
	fprintf('\n\t3. A = F / M');
	fprintf('\n\tq. exit');
	fprintf('\n\n\t-------------------------\n');
	fprintf('\n\t>> ');

function is_terminate = select_formula(command)
	is_terminate = false;
	switch command
		case '1'
			prompt_and_compute('F');
		case '2'
			prompt_and_compute('M');
		case '3'
			prompt_and_compute('A');
		case 'q'
			is_terminate = true;
	end

function prompt_and_compute(desired_variable)
	variables = {'F', 'M', 'A'};
	variables(strcmp(variables, desired_variable)) = [];
	fprintf('\n');
	input1 = str2num(input(sprintf('\t%s = ', variables{1}), 's'));
	input2 = str2num(input(sprintf('\t%s = ', variables{2}), 's'));
	switch desired_variable
		case {'M', 'A'}
			output = input1 ./ input2;
		otherwise
			output = input1 .* input2;
	end
	fprintf('\n\t%s = %.2f', desired_variable, output);
	fprintf('\n\n\tPress ENTER to go back to the menu...');
	input('');
