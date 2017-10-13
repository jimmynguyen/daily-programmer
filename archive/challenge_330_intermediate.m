% /r/dailyprogrammer challenge #330 - intermediate
%
% https://www.reddit.com/r/dailyprogrammer/comments/6yep7x/20170906_challenge_330_intermediate_check_writer/
function challenge_330_intermediate(amount)

function words = num2words(num)
	words = {};
	under_twenty_words = {'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine', 'ten', 'eleven', 'twelve', 'thirteen', 'fourteen', 'fifteen', 'sixteen', 'seventeen', 'eighteen', 'nineteen'};
	tens_words = {'twenty', 'thirty', 'fourty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'};
	place_words = {'hundred', 'thousand', 'million', 'billion', 'trillion'};
	if tens_digit <= 1
		if ones_digit == 0
			words = [words, 'zero']
		words = under_twenty_words{ones_digit};
	end
	if tens_
