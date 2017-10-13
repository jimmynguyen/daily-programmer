% /r/dailyprogrammer challenge #1 - easy
%
% https://www.reddit.com/r/dailyprogrammer/comments/pih8x/easy_challenge_1/
function challenge_001_easy
	clear;
	clc;

	name     = input('Name: ', 's');
	age      = input('Age : ', 's');
	username = input('Reddit Username: ', 's');
	output   = sprintf('your name is %s, you are %s years old, and your username is %s\n', name, age, username);

	fprintf('\n%s\n', output);

	fh = fopen('info.log', 'a');
	fprintf(fh, output);
	fclose(fh);
