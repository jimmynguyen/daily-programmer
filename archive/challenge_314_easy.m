% /r/dailyprogrammer challenge #314 - easy
%
% https://www.reddit.com/r/dailyprogrammer/comments/69y21t/20170508_challenge_314_easy_concatenated_integers/
function output = challenge_314_easy(array)
	sorted_array = custom_quicksort(array);
	max_integer = arrayfun(@(x) num2str(x), sorted_array, 'UniformOutput', false);
	min_integer = arrayfun(@(x) num2str(x), sorted_array(end:-1:1), 'UniformOutput', false);
	output = [str2num([min_integer{:}]), str2num([max_integer{:}])];

function array = custom_quicksort(array)
	if length(array) > 1
		array = [custom_quicksort(less_than(array, array(1))), equal_to(array, array(1)), custom_quicksort(greater_than(array, array(1)))];
	end

function equal_to_array = equal_to(array, element)
	equal_to_array = [];
	for i = 1:length(array)
		if compare(array(i), element) == 0
			equal_to_array(end+1) = array(i);
		end
	end

function greater_than_array = greater_than(array, element)
	greater_than_array = [];
	for i = 1:length(array)
		if compare(array(i), element) < 0
			greater_than_array(end+1) = array(i);
		end
	end

function less_than_array = less_than(array, element)
	less_than_array = [];
	for i = 1:length(array)
		if compare(array(i), element) > 0
			less_than_array(end+1) = array(i);
		end
	end

function output = compare(a, b)
	[a, b] = deal(num2str(a), num2str(b));
	output = str2num([a, b]) - str2num([b, a]);

function output = challenge_314_easy_inefficient(array)
	permutations = arrayfun(@num2str, perms(array), 'UniformOutput', false);
	integers = [];
	for i = 1:size(permutations, 1)
		integers(i) = str2num([permutations{i, :}]);
	end
	sorted_integers = sort(integers);
	output = sorted_integers([1, end]);
