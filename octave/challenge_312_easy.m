% /r/dailyprogrammer challenge #312 - easy
%
% https://www.reddit.com/r/dailyprogrammer/comments/67dxts/20170424_challenge_312_easy_l33tspeak_translator/
function message = challenge_312_easy(message, to_leetspeak)
	letter_mapping = {'a', '4'; 'b', '6'; 'e', '3'; 'i', '1'; 'l', '1'; 'm', '(V)'; 'n', '(\)'; 'o', '0'; 's', '5'; 't', '7'; 'v', '\/'; 'w', '`//'};
	if to_leetspeak
		message = upper(english_to_leet(lower(message), letter_mapping));
	else
		message = lower(leet_to_english(message, letter_mapping));
	end

function message = english_to_leet(message, letter_mapping)
	for i = 1:size(letter_mapping, 1)
		message = strrep(message, letter_mapping{i, 1}, letter_mapping{i, 2});
	end

function message = leet_to_english(message, letter_mapping)
	for i = 1:size(letter_mapping, 1)
		message = strrep(message, letter_mapping{i, 2}, letter_mapping{i, 1});
	end
