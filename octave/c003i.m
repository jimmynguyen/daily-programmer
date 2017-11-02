% Solution to /r/dailyprogrammer Challenge #3 Intermediate
% Jimmy Nguyen
%
% Prompt:
%     Write a program that can encrypt texts with an alphabetical substitution
% cipher. The cipher can ignore numbers, symbols, and whitespace.
%
% Bonus (not implemented):
%     For extra credit, make it encrypt whitespace, numbers, and symbols
%
% Link to challenge:
%     https://www.reddit.com/r/dailyprogrammer/comments/pkwb1/2112012_challenge_3_intermediate/
%
% Note:
%     The following implementation performs a simple substitution with groups
% of size 4:
%     https://en.wikipedia.org/wiki/Substitution_cipher#Simple_substitution
function encrypted_message = c003i(message, keyword)
	GROUP_SIZE = 4;
	message = upper(message);
	message(message < 'A' | message > 'Z') = [];
	alphabet = unique([upper(keyword), 'A':'Z']);
	encrypted_message = '';
	n = length(message);
	for i = 1:GROUP_SIZE:n
		j = i+GROUP_SIZE-1;
		if j > n
			j = n;
		end
		encrypted_message = [encrypted_message, alphabet(message(i:j) - 'A' + 1)];
		if j < n
			encrypted_message = [encrypted_message, ' '];
		end
	end

function unique_str = unique(str)
	unique_str = '';
	for letter = str
		if ~any(unique_str == letter)
			unique_str = [unique_str, letter];
		end
	end
