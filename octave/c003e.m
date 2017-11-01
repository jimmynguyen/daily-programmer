% Solution to /r/dailyprogrammer Challenge #3 Easy
% Jimmy Nguyen
%
% Prompt:
%     Write a program that can encrypt texts with an alphabetical Caesar
% cipher. The cipher can ignore numbers, symbols, and whitespace.
%
% Bonus:
%     For extra credit, add a "decrypt" function to your program
%
% Link to challenge:
%     https://www.reddit.com/r/dailyprogrammer/comments/pkw2m/2112012_challenge_3_easy/
function message = c003e(message, n, is_decrypt)
	if is_decrypt
		n = -n;
	end
	upper_mask = message >= 'A' & message <= 'Z';
	lower_mask = message >= 'a' & message <= 'z';
	message(upper_mask) = mod(message(upper_mask) - 'A' + n, 26) + 'A';
	message(lower_mask) = mod(message(lower_mask) - 'a' + n, 26) + 'a';
