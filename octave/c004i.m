% Solution to /r/dailyprogrammer Challenge #4 Intermediate
% Jimmy Nguyen
%
% Prompt:
%     Create a calculator program that will take an input (5*5+4) and give an
% answer (29). This calculator should use all four operators.
%
% Bonus:
%     For extra credit, add other operators (6(4+3), 3 ** 3, etc.)
%
% Link to challenge:
%     https://www.reddit.com/r/dailyprogrammer/comments/pm6sq/2122012_challenge_4_intermediate/
%
% Note:
%     The calculator implementation below recognizes parentheses and brackets.
% Valid mathematical operations are:
%         + : addition
%         - : subtraction
%         * : multiplication
%         / : division
%         % : modulo
%         ^ : exponentiation
%
% Strategy:
%     1. compute and replace all substrings of form
%             "<bracket><val1><operator><val2><bracket>",
%         where
%             <bracket> can be parentheses () or square brackets [],
%             <val1> and <val2> can be positive or negative integers,
%             and <operator> is one of operations listed above
%     2. compute all operations based on the following priority list. if two
%         operations have the same priority, they will be computed left to
%         right
%             1: ^
%             2: *, /, %
%             3: +, -
%
% Usage:
%     c004i(expression)
%         - computes the input mathematical expression and prints the answer
function answer = c004i(expression)
	expression = strip_all_whitespace(expression);
	answer = compute(expression);
	disp([expression, ' = ', answer]);

function expression = strip_all_whitespace(expression)
	expression = expression(~isspace(expression));

function answer = compute(expression)
	% implementation of simplify_brackets using regex to find the brackets.
	% the regex implementation is faster the majority of the time. the
	% disadvantage of the regex implementation is that does not check for
	% mismatch brackets
	expression = simplify_brackets_regexp(expression);
	% expression = simplify_brackets(expression);
	answer = simplify(expression);

function expression = simplify_brackets_regexp(expression)
	[ndx_begin_list, ndx_end_list] = regexp(expression, '[\(\[][0-9+\-*\/\%^]*[\)\]]');
	while ~isempty(ndx_begin_list)
		ndx_begin = ndx_begin_list(1);
		ndx_end = ndx_end_list(1);
		expression = strrep(expression, expression(ndx_begin:ndx_end), compute(expression(ndx_begin+1:ndx_end-1)));
		[ndx_begin_list, ndx_end_list] = regexp(expression, '[\(\[][0-9.+\-*\/\%^]*[\)\]]');
	end

% function expression = simplify_brackets(expression)
% 	[ndx_begin, ndx_end] = find_brackets(expression);
% 	while ndx_begin ~= 0
% 		expression = strrep(expression, expression(ndx_begin:ndx_end), compute(expression(ndx_begin+1:ndx_end-1)))
% 		[ndx_begin, ndx_end] = find_brackets(expression);
% 	end

% function [ndx_begin, ndx_end] = find_brackets(expression)
% 	ndx_begin = 0;
% 	ndx_end = 0;
% 	mask = expression == '(' | expression == '[';
% 	if any(mask)
% 		ndx = find(mask);
% 		ndx_begin = ndx(1);
% 		ndx_end = ndx_begin + find_matching_bracket(expression(ndx_begin+1:end), expression(ndx_begin));
% 	end

% function ndx = find_matching_bracket(expression, open_bracket)
% 	close_bracket = get_close_bracket(open_bracket);
% 	mask = expression == close_bracket;
% 	if any(mask)
% 		stack = '';
% 		for i = 1:length(expression)
% 			c = expression(i);
% 			switch c
% 				case {'(', '['}
% 					stack = [stack, get_close_bracket(c)];
% 				case {')', ']'}
% 					if isempty(stack) && c == close_bracket
% 						ndx = i;
% 						break;
% 					elseif ~isempty(stack) && c == stack(end)
% 						stack(end) = [];
% 					else
% 						error('Invalid expression. Parentheses mismatch');
% 					end
% 			end
% 		end
% 	else
% 		error('Invalid expression. Parentheses mismatch');
% 	end

% function close_bracket = get_close_bracket(open_bracket_type)
% 	switch open_bracket_type
% 		case '('
% 			close_bracket = ')';
% 		case '['
% 			close_bracket = ']';
% 	end

function expression = simplify(expression)
	while any_operator(expression)
		if any(expression == '^')
			expression = simplify_operation(expression, '^');
		elseif any(expression == '*' | expression == '/' | expression == '%')
			expression = simplify_operation(expression, '*/%');
		else
			expression = simplify_operation(expression, '+-');
		end
	end

function is_found = any_operator(expression)
	is_found = any(expression == '+' | expression == '*' | expression == '/' | expression == '%' | expression == '^');
	if ~is_found
		[~, r_half] = strtok(expression, '-');
		is_found = any(r_half == '-');
	end

function expression = simplify_operation(expression, operations)
	[l_half, r_half] = strtok(expression, operations);
	operation = r_half(1);
	[a, l_half] = get_first_value(l_half, operations, operation, expression);
	[b, r_half] = get_second_value(r_half);
	expression = [l_half, perform_operation(operation, a, b), r_half];

function [value, l_half] = get_first_value(l_half, operations, operation, expression)
	[value, l_half] = strtok(l_half(end:-1:1), '+-*/%^');
	[value, l_half] = deal(value(end:-1:1), l_half(end:-1:1));
	% if "-" is one of the operators and value is negative, then the "-" won't
	% be returned in l_half so we need to get l_half
	if any(operations == '-')
		indices = find(expression == operation);
		ndx = indices(1)-1;
		l_half = expression(1:ndx-length(value));
	end
	% value is negative if
	%     1. l_half has form "-"
	%     2. l_half has form "...<operator>-"
	if length(l_half) == 1 && l_half == '-' || length(l_half) > 1 && any('+-*/%^' == l_half(end-1))
		l_half(end) = [];
		value = ['-', value];
	end

function [value, r_half_rest] = get_second_value(r_half)
	[value, r_half_rest] = strtok(r_half, '+-*/%^');
	% value is negative if
	%     1. r_half has form "<operator>-<val>..."
	if r_half(2) == '-'
		value = ['-', value];
	end

function answer = perform_operation(operation, a, b)
	a = str2num(a);
	b = str2num(b);
	switch operation
		case '+'
			answer = a + b;
		case '-'
			answer = a - b;
		case '*'
			answer = a * b;
		case '/'
			answer = a / b;
		case '^'
			answer = a ^ b;
		case '%'
			answer = mod(a, b);
		otherwise
			error(sprintf('Invalid operation "%s"', operation));
	end
	if answer < eps && answer > -eps
		answer = 0;
	end
	answer = num2str(answer);

%!assert(c004i('1')                 , '1'   )
%!assert(c004i('1+1')               , '2'   )
%!assert(c004i('4+5*5')             , '29'  )
%!assert(c004i('1+2^10')            , '1025')
%!assert(c004i('(1+1)^10')          , '1024')
%!assert(c004i('(1+1)^(5*2)')       , '1024')
%!assert(c004i('(1+-1)^(5*2)')      , '0'   )
%!assert(c004i('-(1+1)^3')          , '-8'  )
%!assert(c004i('-1*(2-3)+(1+-1)*-1'), '1'   )
