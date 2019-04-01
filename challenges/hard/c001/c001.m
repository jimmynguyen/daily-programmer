% Solution to /r/dailyprogrammer Challenge #1 Hard
% Jimmy Nguyen
%
% Prompt:
%     Create a program that will guess numbers between 1 and 100, and respond
% appropriately based on whether the users say that number is too high or too
% low
%
% Link to challenge:
%     https://www.reddit.com/r/dailyprogrammer/comments/pii6j/difficult_challenge_1/
function c001i()
	min_val = 0;
	max_val = 100;
	is_correct = false;
	guesses = {};
	guess = computeGuess(max_val, min_val);
	input(sprintf('\n==================================================\n\nPick a number between 1 and 100, inclusive. Once\nyou have chosen an number. Press ENTER to continue'));
	while ~is_correct
		answer = input(sprintf('\n==================================================\n\nIs %d your number? (y or yes|h or high|l or low)\n>> ', guess), 's');
		if any(strcmpi({'y', 'yes'}, answer))
			is_correct = true;
			fprintf('\n==================================================\n\nYour number is %d\n', guess);
		elseif any(strcmpi({'h', 'high'}, answer))
			if isempty(guesses)
				guesses = {min_val, 'l'};
			end
			guesses = [guesses; {guess, 'h'}];
			[hi, lo] = getMostRecentHighAndLowGuesses(guesses);
			guess = computeGuess(hi, lo);
		elseif any(strcmpi({'l', 'low'}, answer))
			if isempty(guesses)
				guesses = {max_val, 'h'};
			end
			guesses = [guesses; {guess, 'l'}];
			[hi, lo] = getMostRecentHighAndLowGuesses(guesses);
			guess = computeGuess(hi, lo);
		end
	end

function guess = computeGuess(hi, lo)
	guess = round((hi - lo) ./ 2) + lo;

function [hi, lo] = getMostRecentHighAndLowGuesses(guesses)
	hi_locs = find(strcmp(guesses(:, 2), 'h'));
	lo_locs = find(strcmp(guesses(:, 2), 'l'));
	hi = guesses{hi_locs(end), 1};
	lo = guesses{lo_locs(end), 1};
