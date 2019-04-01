% Solution to /r/dailyprogrammer Challenge #3 Hard
% Jimmy Nguyen
%
% Prompt:
%     Write a program that will take a list of scrambled words and compare
% them against a list of words to unscramble them. See the resources folder
% for text files with pattern "c003h_*.txt" for a file of scrambled words and
% a file with a list of words
%
% Bonus:
%     For extra credit, sort the words by length when finished
%
% Link to challenge:
%     https://www.reddit.com/r/dailyprogrammer/comments/pkwgf/2112012_challenge_3_difficult/
function c003h(scrambled_words_file_name, word_list_file_name)
	scrambled_words = read_file(scrambled_words_file_name);
	word_list = read_file(word_list_file_name);

	sorted_scrambled_words = sort_letters_in_words(scrambled_words);
	sorted_word_list = sort_letters_in_words(word_list);

	word_length_to_words_map = generate_map(sorted_scrambled_words, sorted_word_list, word_list);

	unscrambled_words = get_unscrambled_words(word_length_to_words_map);

	disp(unscrambled_words);

function file = read_file(file_name)
	fh = fopen(file_name, 'r');
	file = textscan(fh, '%s');
	file = file{1};
	fclose(fh);

function sorted_words = sort_letters_in_words(words)
	sorted_words = cellfun(@sort, words, 'UniformOutput', false);

function word_length_to_words_map = generate_map(sorted_scrambled_words, sorted_word_list, word_list)
	word_length_to_words_map = {};
	for i = 1:length(sorted_scrambled_words)
		unscrambled_word = word_list{strcmp(sorted_word_list, sorted_scrambled_words{i})};
		n = length(unscrambled_word);
		if length(word_length_to_words_map) < n
			word_length_to_words_map{n} = {unscrambled_word};
		else
			word_length_to_words_map{n} = [word_length_to_words_map{n}, unscrambled_word];
		end
	end

function unscrambled_words = get_unscrambled_words(word_length_to_words_map)
	unscrambled_words = [word_length_to_words_map{:}];
