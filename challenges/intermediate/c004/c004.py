# Solution to /r/dailyprogrammer Challenge #4 Intermediate
# Jimmy Nguyen
#
# Prompt:
#     Create a calculator program that will take an input (5*5+4) and give an
# answer (29). This calculator should use all four operators.
#
# Bonus:
#     For extra credit, add other operators (6(4+3), 3 ** 3, etc.)
#
# Link to challenge:
#     https://www.reddit.com/r/dailyprogrammer/comments/pm6sq/2122012_challenge_4_intermediate/
#
# Usage:
#     python c004i.py -h|-help
#         - prints the help menu
#
#     python c004i.py test
#         - runs through all preset test cases and throws an error if one
#           fails
#
#     python c004i.py <expression>
#         - computes the input mathematical expression and prints the answer
#
# Note:
#     The calculator implementation below recognizes parentheses and brackets.
# Valid mathematical operations are:
#         + : addition
#         - : subtraction
#         * : multiplication
#         / : division
#         % : modulo
#         ^ : exponentiation
#
# Strategy:
#     1. compute and replace all substrings of form
#             "<bracket><val1><operator><val2><bracket>",
#         where
#             <bracket> can be parentheses () or square brackets [],
#             <val1> and <val2> can be positive or negative integers,
#             and <operator> is one of operations listed above
#     2. compute all operations based on the following priority list. if two
#         operations have the same priority, they will be computed left to
#         right
#             1: ^
#             2: *, /, %
#             3: +, -
import math
import re
import sys

BRACKET_PATTERN = re.compile('[\(\[][0-9+\-*\/\%^]*[\)\]]')

class Operation:
	ADDITION = '+'
	SUBTRACTION = '-'
	MULTIPLICATION = '*'
	DIVISION = '/'
	MODULO = '%'
	EXPONENTIATION = '^'

	@staticmethod
	def values():
		return [Operation.ADDITION, \
				Operation.SUBTRACTION, \
				Operation.MULTIPLICATION, \
				Operation.DIVISION, \
				Operation.MODULO, \
				Operation.EXPONENTIATION]

class ValueHalfPair:
	def __init__(self, value, half):
		self.value = value
		self.half = half

def print_help_menu():
	print( \
			'\nUsage:' + \
			'\n    python c004i.py -h|-help' + \
			'\n        - prints the help menu' + \
			'\n' + \
			'\n    python c004i.py test' + \
			'\n        - runs through all preset test cases and throws an error if one' + \
			'\n          fails' + \
			'\n' + \
			'\n    python c004i.py <expression>' + \
			'\n        - computes the input mathematical expression and prints the answer' + \
			'\n' + \
			'\nNote:' + \
			'\n    The calculator implementation below recognizes parentheses and brackets.' + \
			'\nValid mathematical operations are:' + \
			'\n        + : addition' + \
			'\n        - : subtraction' + \
			'\n        * : multiplication' + \
			'\n        / : division' + \
			'\n        % : modulo' + \
			'\n        ^ : exponentiation' + \
			'\n' + \
			'\nStrategy:' + \
			'\n    1. compute and replace all substrings of form' + \
			'\n            "<bracket><val1><operator><val2><bracket>",' + \
			'\n        where' + \
			'\n            <bracket> can be parentheses () or square brackets [],' + \
			'\n            <val1> and <val2> can be positive or negative integers,' + \
			'\n            and <operator> is one of operations listed above' + \
			'\n    2. compute all operations based on the following priority list. if two' + \
			'\n        operations have the same priority, they will be computed left to' + \
			'\n        right' + \
			'\n            1: ^' + \
			'\n            2: *, /, %' + \
			'\n            3: +, -' + \
			'\n')

def run_tests():
	tests = [
		['1'                 , '1'   ],
		['1+1'               , '2'   ],
		['2-3'               , '-1'   ],
		['4+5*5'             , '29'  ],
		['1+2^10'            , '1025'],
		['(1+1)^10'          , '1024'],
		['(1+1)^(5*2)'       , '1024'],
		['(1+-1)^(5*2)'      , '0'   ],
		['-(1+1)^3'          , '-8'  ],
		['-1*(2-3)+(1+-1)*-1', '1'   ]
	]
	print('\n==============\nRunning tests:\n==============')
	for test in tests:
		answer = c004i(test[0], True)
		assert answer == test[1], test[0] + " = " + test[1] + "\nExpected: " + test[1] + "\nActual  : " + answer;

def c004i(expression, is_test):
	stripped_expression = expression.replace(' ', '')
	computed_expression = compute(stripped_expression)
	print(('{} = {}\n' if is_test else '\n{} = {}\n').format(stripped_expression, computed_expression))
	return computed_expression

def compute(expression):
	return simplify(simplify_brackets(expression))

def simplify_brackets(expression):
	matcher = BRACKET_PATTERN.search(expression)
	while matcher is not None:
		sub_expression = expression[matcher.start()+1:matcher.end()-1]
		expression = expression.replace(matcher.group(), compute(sub_expression))
		matcher = BRACKET_PATTERN.search(expression)
	return expression

def simplify(expression):
	while any_operator(expression):
		if Operation.EXPONENTIATION in expression:
			expression = simplify_operation(expression, [ \
					Operation.EXPONENTIATION])
		elif Operation.MULTIPLICATION in expression \
				or Operation.DIVISION in expression \
				or Operation.MODULO in expression:
			expression = simplify_operation(expression, [ \
					Operation.MULTIPLICATION, \
					Operation.DIVISION, \
					Operation.MODULO])
		else:
			expression = simplify_operation(expression, [ \
					Operation.ADDITION,
					Operation.SUBTRACTION])
	return expression

def any_operator(expression):
	for operation in Operation.values():
		if Operation.SUBTRACTION != operation and operation in expression:
			return True
	if Operation.SUBTRACTION in expression:
		ndx = expression.index(Operation.SUBTRACTION)
		if ndx == 0:
			expression = expression[1:]
			if Operation.SUBTRACTION in expression:
				return True
		else:
			return True
	return False

def simplify_operation(expression, operations):
	operation_ndx = get_first_operation_index(expression, operations)
	if operation_ndx is None:
		return expression
	l_half = expression[:operation_ndx]
	r_half = expression[operation_ndx+1:]
	operation = expression[operation_ndx]
	l_pair = get_first_value(l_half, operations, expression, operation_ndx)
	r_pair = get_second_value(r_half)
	answer = perform_operation(operation, l_pair.value, r_pair.value)
	return l_pair.half + answer + r_pair.half

def get_first_operation_index(expression, operations):
	count = 0
	while expression and expression[0] in operations:
		expression = expression[1:]
		count += 1
	min_ndx = None
	for operation in operations:
		if operation in expression and (min_ndx is None or min_ndx > expression.index(operation)):
			min_ndx = expression.index(operation)
	if min_ndx is not None:
		min_ndx += count
	return min_ndx

def get_first_value(l_half, operations, expression, operation_ndx):
	ndx = get_first_operation_index(l_half[::-1], Operation.values())
	if ndx is None:
		value = l_half
		l_half = ''
	else:
		ndx = len(l_half) - ndx
		value = l_half[ndx:]
		l_half = l_half[:ndx]
	if Operation.SUBTRACTION in operations:
		l_half = expression[:operation_ndx - len(value)]
	if len(l_half) == 1 and l_half == '-' \
			or len(l_half) > 1 and l_half[-2] in Operation.values():
		value = '-' + value
		l_half = l_half[:-1]
	return ValueHalfPair(value, l_half)

def get_second_value(r_half):
	ndx = get_first_operation_index(r_half, Operation.values())
	if ndx is None:
		value = r_half
		r_half = ''
	else:
		value = r_half[:ndx]
		r_half = r_half[ndx:]
	return ValueHalfPair(value, r_half)

def perform_operation(operation, a, b):
	a_float = float(a)
	b_float = float(b)
	if Operation.ADDITION == operation:
		answer = a_float + b_float
	elif Operation.SUBTRACTION == operation:
		answer = a_float - b_float
	elif Operation.MULTIPLICATION == operation:
		answer = a_float * b_float
	elif Operation.DIVISION == operation:
		answer = a_float / b_float
	elif Operation.MODULO == operation:
		answer = a_float % b_float
	elif Operation.EXPONENTIATION == operation:
		answer = a_float ** b_float
	if answer == math.floor(answer):
		return '{}'.format(int(answer))
	else:
		return '{:.2f}'.format(answer)

if __name__ == '__main__':
	args = sys.argv[1:]
	if not args or args[0] in ['-h', '-help']:
		print_help_menu()
	elif args[0] == 'test':
		run_tests()
	else:
		c004i(args[0], False)
