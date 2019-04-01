% Solution to /r/dailyprogrammer Challenge #5 Intermediate
% Jimmy Nguyen
%
% Prompt:
%     Your challenge today is to write a program that can find the number of
% anagrams within a .txt file. For example, "snap" would be an anagram of
% "pans", and "skate" would be an anagram of "stake".
%
% Link to challenge:
%     https://www.reddit.com/r/dailyprogrammer/comments/pnhtj/2132012_challenge_5_intermediate/
%
% Usage:
%     help c005i
%         - prints the help menu
%
%     test c005i
%         - runs through all preset test cases and throws an error if one
%           fails
%
%     num_anagrams = c005i(filename)
%         - returns the number of anagrams found in the input file
%
%     num_anagrams = c005i(filename, is_debug)
%         - returns the number of anagrams found in the input file and if
%           is_debug is true, then print the anagrams
%
% Strategy:
%     1. read file <file>
%     2. remove punctuation
%     3. get list of unique words in file <words>
%     4. sort letters in words alphabetically <sorted_words>
%     5. get list of unique sorted words <unique_sorted_words>
%     6. for each unique sorted word <sorted_word>:
%            a. find the indices of other instances of <sorted_word>
%            b. print non-sorted words at those same indices in <words>
%            c. remove instances of <sorted_word> from <sorted_words>
%            d. increment <num_anagrams>
function num_anagrams = c005i(filename, varargin)
	if nargin > 1 && is_logical(varargin{1})
		is_debug = varargin{1};
	else
		is_debug = false;
	end
	% 1. read file
	file = read_file(filename);
	% 2. remove punctuation
	file = file(cellfun(@(word) ~isempty(word(word >= 'A' & word <= 'Z' | word >= 'a' & word <= 'z')), file, 'UniformOutput', true));
	file = cellfun(@(word) lower(word(word >= 'A' & word <= 'Z' | word >= 'a' & word <= 'z')), file, 'UniformOutput', false);
	% 3. get list of unique words in file
	words = unique(file);
	% 4. sort letters in words alphabetically
	sorted_words = cellfun(@sort, words, 'UniformOutput', false);
	% 5. get list of unique sorted words <unique_sorted_words>
	unique_sorted_words = unique(sorted_words);
	% 6. for each unique sorted word <sorted_word>:
	%        a. find the indices of other instances of <sorted_word>
	%        b. print non-sorted words at those same indices in <words>
	%        d. increment <num_anagrams>
	num_anagrams = 0;
	for i = 1:length(unique_sorted_words)
		sorted_word = unique_sorted_words{i};
		mask = strcmpi(sorted_words, sorted_word);
		if sum(mask) > 1
			if is_debug
				fprintf('%s\n', strjoin(words(mask), ', '));
			end
			num_anagrams = num_anagrams + 1;
		end
	end

function file = read_file(filename)
	fh = fopen(filename, 'r');
	file = textscan(fh, '%s');
	file = file{1};
	fclose(fh);

%!assert(c005i('../resources/c005i_gettysburg_address.txt')            , 0)
%!assert(c005i('../resources/c005i_us_declaration_of_independence.txt'), 4)
%!assert(c005i('../resources/c005i_i_have_a_dream.txt')                , 3)
