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
function output = c001e(varargin)
	if nargin < 3
		[name, age, username] = prompt_user();
	else
		[name, age, username] = deal(varargin{1}, varargin{2}, varargin{3});
	end
	output = print_and_save(name, age, username);

function [name, age, username] = prompt_user()
	name     = input('Name: ', 's');
	age      = input('Age : ', 's');
	username = input('Reddit Username: ', 's');

function output = print_and_save(name, age, username)
	output = sprintf(['your name is %s, you are %s years old, and your ', ...
			'username is %s\n'], name, age, username);
	fprintf('\n%s\n', output);
	save_to_file(output);

function save_to_file(output)
	file_path = '../trash/info.log';
	fh = fopen(file_path, 'a');
	fprintf(fh, output);
	fclose(fh);
	fprintf(['Your information has been saved in the file "%s"\n'], file_path);

%!assert(c001e('NAME', 'AGE', 'USERNAME'), ['your name is NAME, you are AGE years old, and your username is USERNAME', sprintf('\n')])
