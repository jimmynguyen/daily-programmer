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
function encrypted_message = c003i(message, keyword)
	GROUP_SIZE = 4;
	alphabet = unique([upper(keyword), 'A':'Z']);
	message = upper(message);
	message(message < 'A' | message > 'Z') = [];
	message = alphabet(message - 'A' + 1);
	n = length(message);
	encrypted_message = '';
	for i = 1:GROUP_SIZE:n
		j = i+GROUP_SIZE-1;
		if j > n
			j = n;
		end
		encrypted_message = [encrypted_message, message(i:j)];
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
