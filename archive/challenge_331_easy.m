% /r/dailyprogrammer challenge #331 - easy
%
% https://www.reddit.com/r/dailyprogrammer/comments/6ze9z0/20170911_challenge_331_easy_the_adding_calculator/
function output = challenge_331_easy(input)
	operator = get_operator(input);
	[l_value, r_value] = get_values(input, operator);
	output = perform_operation(l_value, r_value, operator);

function output = perform_operation(l_value, r_value, operator)
	switch operator
		case '+'
			output = l_value + r_value;
		case '-'
			output = l_value + negate(r_value);
		case '*'
			output = multiply(l_value, r_value);
		case '/'
			output = divide(l_value, r_value);
		case '^'
			output = powerOf(l_value, r_value);
	end

function output = powerOf(l_value, r_value)
	if r_value < 0
		output = 'Non-integral answer';
	elseif r_value == 0
		output = 1;
	else
		output = l_value;
		for i = 1:r_value-1
			output = multiply(output, l_value);
		end
	end

function output = divide(l_value, r_value)
	if r_value == 0
		output = 'Not-defined';
	else
		output = 0;
		increment = 1;
		if l_value < 0 && r_value < 0
			[l_value, r_value] = deal(negate(l_value), negate(r_value));
		elseif l_value < 0
			l_value = negate(l_value);
			increment = -1;
		elseif r_value < 0
			r_value = negate(r_value);
			increment = -1;
		end
		while l_value > 0
			l_value = l_value + negate(r_value);
			output = output + increment;
		end
		if l_value ~= 0
			output = 'Non-integral answer';
		end
	end

function output = multiply(l_value, r_value)
	output = 0;
	if l_value < 0 && r_value < 0
		[l_value, r_value] = deal(negate(l_value), negate(r_value));
	elseif l_value < 0 && r_value < 0
		[l_value, r_value] = deal(max([l_value, r_value]), min([l_value, r_value]));
	end
	for i = 1:l_value
		output = output + r_value;
	end

function output = negate(input)
	output = 0;
	if input < 0
		increment = 1;
	else
		increment = -1;
	end
	while input + output ~= 0
		output = output + increment;
	end

function [l_value, r_value] = get_values(input, operator)
	if operator ~= '-'
		[l_value, input] = strtok(input, operator);
		r_value = strtok(input, operator);
	else
		is_alpha_numeric = isstrprop(input, 'alphanum');
		for i = 1:length(input)
			if isstrprop(input(i), 'alphanum')
				while isstrprop(input(i), 'alphanum')
					i = i+1;
				end
				l_value = input(1:i-1);
				r_value = input(i:end);
				ndx = find(r_value == '-');
				r_value = r_value(ndx(1)+1:end);
				break;
			end
		end
	end
	[l_value, r_value] = deal(str2num(l_value), str2num(r_value));

function operator = get_operator(input)
	operator = '-';
	operators = {'+', '*', '/', '^'};
	for i = 1:length(operators)
		if any(input == operators{i})
			operator = operators{i};
			return;
		end
	end
