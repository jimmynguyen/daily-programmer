% /r/dailyprogrammer challenge #329 - easy
%
% https://www.reddit.com/r/dailyprogrammer/comments/6wjscp/2017828_challenge_329_easy_nearest_lucky_numbers/
function challenge_329_easy(n)
	lucky_numbers = getLuckyNumbers(n);
	printOutput(n, lucky_numbers);

function lucky_numbers = getLuckyNumbers(n)
	numbers = 1:2:2*n;
	lucky_numbers = [];
	i = 2;
	while i < length(numbers)
		numbers(numbers(i):numbers(i):end) = [];
		i = i+1;
	end
	if ~any(numbers == n)
		lower_ndx = find(numbers < n);
		upper_ndx = find(numbers > n);
		lucky_numbers(end+1:end+2) = numbers([lower_ndx(end), upper_ndx(1)]);
	end

function printOutput(n, lucky_numbers)
	if isempty(lucky_numbers)
		fprintf('%d is a lucky number\n', n);
	else
		fprintf('%d < %d < %d\n', lucky_numbers(1), n, lucky_numbers(2));
	end
