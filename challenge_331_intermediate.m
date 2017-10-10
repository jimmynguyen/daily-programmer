% /r/dailyprogrammer challenge #331 - intermediate
%
% https://www.reddit.com/r/dailyprogrammer/comments/6zvjre/20170913_challenge_331_intermediate_sum_of_digits/
function output = challenge_331_intermediate(base, power)
	output = 0;
	product = base.^power;
	while product > 0
		output = output + mod(product, 10);
		product = floor(product./10);
	end
