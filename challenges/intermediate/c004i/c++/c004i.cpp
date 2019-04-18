/*
 * Solution to /r/dailyprogrammer Challenge #4 Intermediate
 * Jimmy Nguyen
 *
 * Prompt:
 *     Create a calculator program that will take an input (5*5+4) and give an
 * answer (29). This calculator should use all four operators.
 *
 * Bonus:
 *     For extra credit, add other operators (6(4+3), 3 ** 3, etc.)
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pm6sq/2122012_challenge_4_intermediate/
 *
 * Compilation:
 *     g++ -std=c++11 c004i.cpp
 *
 * Usage:
 *     ./a.out -h|-help
 *         - prints the help menu
 *
 *     ./a.out test
 *         - runs through all preset test cases and throws an error if one
 *           fails
 *
 *     ./a.out <expression>
 *         - computes the input mathematical expression and prints the answer
 *
 * Note:
 *     This calculator implementation recognizes parentheses and brackets.
 * Valid mathematical operations are:
 *         + : addition
 *         - : subtraction
 *         * : multiplication
 *         / : division
 *         % : modulo
 *         ^ : exponentiation
 *
 * Strategy:
 *     1. compute and replace all substrings of form
 *             "<bracket><val1><operator><val2><bracket>",
 *         where
 *             <bracket> can be parentheses () or square brackets [],
 *             <val1> and <val2> can be positive or negative integers,
 *             and <operator> is one of operations listed above
 *     2. compute all operations based on the following priority list. if two
 *         operations have the same priority, they will be computed left to
 *         right
 *             1: ^
 *             2: *, /, %
 *             3: +, -
 */
#include <algorithm>
#include <assert.h>
#include <climits>
#include <iostream>
#include <math.h>
#include <regex>
#include <string>

std::string compute(std::string expression);

enum class Operation : char
{
	ADDITION = '+',
	SUBTRACTION = '-',
	MULTIPLICATION = '*',
	DIVISION = '/',
	MODULO = '%',
	EXPONENTIATION = '^'
};

static const std::vector<Operation> OPERATIONS = {
	Operation::ADDITION,
	Operation::SUBTRACTION,
	Operation::MULTIPLICATION,
	Operation::DIVISION,
	Operation::MODULO,
	Operation::EXPONENTIATION
};

class ValueHalfPair
{
	public:
		ValueHalfPair(std::string _value, std::string _half) : value(_value), half(_half) {}
		std::string value;
		std::string half;
};

void print_help_menu()
{
	std::cout << std::endl << "Compilation:";
	std::cout << std::endl << "g++ -std=c++11 c004i.cpp";
	std::cout << std::endl;
	std::cout << std::endl << "Usage:";
	std::cout << std::endl << "    ./a.out -h|-help";
	std::cout << std::endl << "        - prints the help menu";
	std::cout << std::endl;
	std::cout << std::endl << "    ./a.out test";
	std::cout << std::endl << "        - runs through all preset test cases and throws an error if one";
	std::cout << std::endl << "          fails";
	std::cout << std::endl;
	std::cout << std::endl << "    ./a.out <expression>";
	std::cout << std::endl << "        - computes the input mathematical expression and prints the answer";
	std::cout << std::endl;
	std::cout << std::endl << "Note:";
	std::cout << std::endl << "    This calculator implementation recognizes parentheses and brackets.";
	std::cout << std::endl << "Valid mathematical operations are:";
	std::cout << std::endl << "        + : addition";
	std::cout << std::endl << "        - : subtraction";
	std::cout << std::endl << "        * : multiplication";
	std::cout << std::endl << "        / : division";
	std::cout << std::endl << "        % : modulo";
	std::cout << std::endl << "        ^ : exponentiation";
	std::cout << std::endl;
	std::cout << std::endl << "Strategy:";
	std::cout << std::endl << "    1. compute and replace all substrings of form";
	std::cout << std::endl << "            \"<bracket><val1><operator><val2><bracket>\",";
	std::cout << std::endl << "        where";
	std::cout << std::endl << "            <bracket> can be parentheses () or square brackets [],";
	std::cout << std::endl << "            <val1> and <val2> can be positive or negative integers,";
	std::cout << std::endl << "            and <operator> is one of operations listed above";
	std::cout << std::endl << "    2. compute all operations based on the following priority list. if two";
	std::cout << std::endl << "        operations have the same priority, they will be computed left to";
	std::cout << std::endl << "        right";
	std::cout << std::endl << "            1: ^";
	std::cout << std::endl << "            2: *, /, %";
	std::cout << std::endl << "            3: +, -";
	std::cout << std::endl << std::endl;
}

std::string strip_all_whitespace(std::string expression)
{
	expression.erase(std::remove_if(expression.begin(), expression.end(), ::isspace), expression.end());
	return expression;
}

std::string simplify_brackets(std::string expression)
{
	std::smatch matcher;
	std::regex bracket_pattern("[\\(\\[][0-9+\\-*\\/\\%^]*[\\)\\]]");
	while (std::regex_search(expression, matcher, bracket_pattern))
	{
		std::string group = matcher[0];
		expression = matcher.prefix().str() + compute(group.substr(1, group.length()-2)) + matcher.suffix().str();
	}
	return expression;
}

bool any_operator(std::string expression)
{
	std::vector<Operation>::const_iterator operation;
	for (operation = OPERATIONS.begin(); operation != OPERATIONS.end(); operation++)
	{
		if (Operation::SUBTRACTION != *operation && expression.find((char)*operation) != std::string::npos)
			return true;
	}
	int ndx = expression.find((char)Operation::SUBTRACTION);
	if (ndx != std::string::npos)
	{
		if (ndx == 0)
			ndx = expression.find((char)Operation::SUBTRACTION, 1);
		if (ndx != std::string::npos)
			return true;
	}
	return false;
}

int get_first_operation_index(std::string expression, std::vector<Operation> operations)
{
	int count = 0;
	while (!expression.empty() && std::find(operations.begin(), operations.end(), (Operation) expression[count]) != operations.end())
		count++;
	int min_ndx = INT_MAX, ndx;
	std::vector<Operation>::iterator operation;
	for (operation = operations.begin(); operation != operations.end(); operation++)
	{
		ndx = expression.find((char)*operation, count);
		if (ndx != std::string::npos && (min_ndx == INT_MAX || min_ndx > ndx))
			min_ndx = ndx;
	}
	return min_ndx;
}

ValueHalfPair get_first_value(std::string l_half, std::vector<Operation> operations, std::string expression, int operation_ndx)
{
	std::string l_half_reversed(l_half), value;
	std::reverse(l_half_reversed.begin(), l_half_reversed.end());
	int ndx = get_first_operation_index(l_half_reversed, OPERATIONS);
	if (ndx == INT_MAX)
	{
		value = l_half;
		l_half = "";
	}
	else
	{
		ndx = l_half.length() - ndx;
		value = l_half.substr(ndx);
		l_half = l_half.substr(0, ndx);
	}
	if (std::find(operations.begin(), operations.end(), Operation::SUBTRACTION) != operations.end())
		l_half = expression.substr(0, operation_ndx - value.length());
	if ((l_half.length() == 1 && l_half == "-")
			|| (l_half.length() > 1 && std::find(OPERATIONS.begin(), OPERATIONS.end(), (Operation) l_half[l_half.length()-2]) != OPERATIONS.end()))
	{
		value = '-' + value;
		l_half = l_half.substr(0, l_half.length()-1);
	}
	return ValueHalfPair(value, l_half);
}

ValueHalfPair get_second_value(std::string r_half)
{
	std::string value;
	int ndx = get_first_operation_index(r_half, OPERATIONS);
	if (ndx == INT_MAX)
	{
		value = r_half;
		r_half = "";
	}
	else
	{
		value = r_half.substr(0, ndx);
		r_half = r_half.substr(ndx);
	}
	return ValueHalfPair(value, r_half);
}

std::string perform_operation(Operation operation, std::string a, std::string b)
{
	double a_double = std::stod(a);
	double b_double = std::stod(b);
	double answer;
	if (Operation::ADDITION == operation)
		answer = a_double + b_double;
	else if (Operation::SUBTRACTION == operation)
		answer = a_double - b_double;
	else if (Operation::MULTIPLICATION == operation)
		answer = a_double * b_double;
	else if (Operation::DIVISION == operation)
		answer = a_double / b_double;
	else if (Operation::MODULO == operation)
		answer = fmod(a_double, b_double);
	else if (Operation::EXPONENTIATION == operation)
		answer = pow(a_double, b_double);
	if (answer == floor(answer))
		return std::to_string((int) answer);
	else
		return std::to_string(answer);
}

std::string simplify_operation(std::string expression, std::vector<Operation> operations)
{
	int operation_ndx = get_first_operation_index(expression, operations);
	if (operation_ndx == INT_MAX)
		return expression;
	std::string l_half = expression.substr(0, operation_ndx);
	std::string r_half = expression.substr(operation_ndx+1);
	Operation operation = (Operation) expression[operation_ndx];
	ValueHalfPair l_pair = get_first_value(l_half, operations, expression, operation_ndx);
	ValueHalfPair r_pair = get_second_value(r_half);
	std::string answer = perform_operation(operation, l_pair.value, r_pair.value);
	return l_pair.half + answer + r_pair.half;
}

std::string simplify(std::string expression)
{
	std::vector<Operation> operations;
	while (any_operator(expression))
	{
		if (expression.find((char)Operation::EXPONENTIATION) != std::string::npos)
			operations.push_back(Operation::EXPONENTIATION);
		else if (expression.find((char)Operation::MULTIPLICATION) != std::string::npos
				|| expression.find((char)Operation::DIVISION) != std::string::npos
				|| expression.find((char)Operation::MODULO) != std::string::npos)
		{
			operations.push_back(Operation::MULTIPLICATION);
			operations.push_back(Operation::DIVISION);
			operations.push_back(Operation::MODULO);
		}
		else
		{
			operations.push_back(Operation::ADDITION);
			operations.push_back(Operation::SUBTRACTION);
		}
		expression = simplify_operation(expression, operations);
	}
	return expression;
}

std::string compute(std::string expression)
{
	return simplify(simplify_brackets(expression));
}

std::string c004i(std::string expression, bool is_test)
{
	std::string stripped_expression = strip_all_whitespace(expression);
	std::string computed_expression = compute(stripped_expression);
	if (!is_test)
		std::cout << std::endl;
	std::cout << stripped_expression << " = " << computed_expression;
	std::cout << std::endl << std::endl;
	return computed_expression;
}

void run_tests()
{
	int num_tests = 10;
	std::string tests[][2] = {
		{"1"                 , "1"   },
		{"1+1"               , "2"   },
		{"2-3"               , "-1"  },
		{"4+5*5"             , "29"  },
		{"1+2^10"            , "1025"},
		{"(1+1)^10"          , "1024"},
		{"(1+1)^(5*2)"       , "1024"},
		{"(1+-1)^(5*2)"      , "0"   },
		{"-(1+1)^3"          , "-8"  },
		{"-1*(2-3)+(1+-1)*-1", "1"   }
	};
	std::cout << std::endl << "==============";
	std::cout << std::endl << "Running tests:";
	std::cout << std::endl << "==============";
	std::cout << std::endl;
	std::string actual_answer, expected_answer;
	for (int i = 0; i < num_tests; i++)
	{
		actual_answer = c004i(tests[i][0], true);
		expected_answer = tests[i][1];
		if (actual_answer.compare(expected_answer) != 0)
		{
			std::cout << "Expression : " << tests[i][0] << std::endl;
			std::cout << "Expected   : " << expected_answer << std::endl;
			std::cout << "Actual     : " << actual_answer << std::endl;
		}
		assert(actual_answer.compare(expected_answer) == 0);
	}
}

int main(int argc, char *argv[])
{
	if (argc < 2 || std::strcmp("-h", argv[1]) == 0 || std::strcmp("-help", argv[1]) == 0)
		print_help_menu();
	else if (std::strcmp("test", argv[1]) == 0)
		run_tests();
	else
		c004i(argv[1], false);
}
