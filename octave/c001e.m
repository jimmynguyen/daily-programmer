% Solution to /r/dailyprogrammer Challenge #1 Easy
% Jimmy Nguyen
%
% Prompt:
%     Create a program that will ask the user's name, age, and reddit username.
% Have it tell them the information back, in the format:
% =============================================================================
% your name is (blank), you are (blank) years old, and your username is (blank)
% =============================================================================
% 
% Bonus:
%     For extra credit, have the program log this information in a file to be
% accessed later.
%
% Link to challenge:
%     https://www.reddit.com/r/dailyprogrammer/comments/pih8x/easy_challenge_1/
function c001e
	% ask for the user's information
	name     = input('Name: ', 's');
	age      = input('Age : ', 's');
	username = input('Reddit Username: ', 's');

	% format the output string
	output   = sprintf('your name is %s, you are %s years old, and your username is %s\n', name, age, username);

	% print the output string
	fprintf('\n%s\n', output);

	% save the output string to a file
	fh = fopen('info.log', 'a');
	fprintf(fh, output);
	fclose(fh);
	fprintf('Your information has been saved in the file "info.log" in the current directory\n');
