% Solution to /r/dailyprogrammer Challenge #4 Easy
% Jimmy Nguyen
%
% Prompt:
%     Create a random password generator
%
% Bonus:
%     For extra credit, allow the user to specify the amount of passwords to
% generate. For extra extra credit, allow the user to specify the length of the
% strings he wants to generate
%
% Link to challenge:
%     https://www.reddit.com/r/dailyprogrammer/comments/pm6oj/2122012_challenge_4_easy/
%
% Usage:
%     c004e()
%         - prints a random alphanumeric password with max length 50
%
%     c004e(len_password)
%         - prints a random alphanumeric password with length <len_password>
%
%     c004e(len_password, num_passwords)
%         - prints <num_passwords> random alphanumeric passwords with length
%           <len_password>
function c004e(varargin)
	ALPHABET = ['a':'z', 'A':'Z', '0':'9'];
	MAX_PASSWORD_LENGTH = 50;
	if nargin >= 1
		len_password = varargin{1};
	else
		len_password = randi(MAX_PASSWORD_LENGTH);
	end
	if nargin >= 2
		num_passwords = varargin{2};
	else
		num_passwords = 1;
	end
	nums = randi(length(ALPHABET), [num_passwords, len_password]);
	for i = 1:num_passwords
		disp(ALPHABET(nums(i, :)));
	end
