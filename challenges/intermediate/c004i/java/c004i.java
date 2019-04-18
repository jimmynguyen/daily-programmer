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
 *     javac c004i.java
 *
 * Usage:
 *     java c004i -h|-help
 *         - prints the help menu
 *
 *     java -ea c004i test
 *         - runs through all preset test cases and throws an error if one
 *           fails
 *
 *     java c004i <expression>
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
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class c004i {
	private static final Pattern BRACKET_PATTERN =
			Pattern.compile("[\\(\\[][0-9+\\-*\\/\\%^]*[\\)\\]]");

	private static enum Operation {
		ADDITION("+"),
		SUBTRACTION("-"),
		MULTIPLICATION("*"),
		DIVISION("/"),
		MODULO("%"),
		EXPONENTIATION("^");

		public final String symbol;

		Operation(String symbol) {
			this.symbol = symbol;
		}

		public String getSymbol() {
			return symbol;
		}

		public static Operation get_operation(char symbol) {
			for (Operation operation : Operation.values())
				if (operation.symbol.equals(String.valueOf(symbol)))
					return operation;
			return null;
		}
	}

	private static class ValueHalfPair {
		public String value;
		public String half;

		public ValueHalfPair(String value, String half) {
			this.value = value;
			this.half = half;
		}
	}

	private static void print_help_menu() {
		System.out.printf(
				"\nCompilation:" +
				"\n    javac c004i.java" +
				"\n" +
				"\nUsage:" +
				"\n    java c004i -h|-help" +
				"\n        - prints the help menu" +
				"\n    java c004i <expression>" +
				"\n        - computes the input mathematical expression and prints the answer" +
				"\n" +
				"\nNote:" +
				"\n    This calculator implementation recognizes parentheses and brackets." +
				"\nValid mathematical operations are:" +
				"\n        + : addition" +
				"\n        - : subtraction" +
				"\n        * : multiplication" +
				"\n        / : division" +
				"\n        % : modulo" +
				"\n        ^ : exponentiation" +
				"\n" +
				"\nStrategy:" +
				"\n    1. compute and replace all substrings of form" +
				"\n            \"<bracket><val1><operator><val2><bracket>\"," +
				"\n        where" +
				"\n            <bracket> can be parentheses () or square brackets []," +
				"\n            <val1> and <val2> can be positive or negative integers," +
				"\n            and <operator> is one of operations listed above" +
				"\n    2. compute all operations based on the following priority list. if two" +
				"\n        operations have the same priority, they will be computed left to" +
				"\n        right" +
				"\n            1: ^" +
				"\n            2: *, /, %" +
				"\n            3: +, -" +
				"\n\n");
	}

	private static void run_tests() {
		String[][] tests = new String[][] {
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
		System.out.printf("\n==============\nRunning tests:\n==============\n");
		String answer;
		for (int i = 0; i < tests.length; i++) {
			answer = c004i(tests[i][0], true);
			assert answer.equals(tests[i][1]):tests[i][0] + " = " + tests[i][1] + "\nExpected: " + tests[i][1] + "\nActual  : " + answer;
		}
	}

	private static String c004i(String expression, boolean is_test) {
		String stripped_expression = strip_all_whitespace(expression);
		String computed_expression = compute(stripped_expression);
		System.out.printf(is_test ? "%s = %s\n\n" : "\n%s = %s\n\n"
				, stripped_expression
				, computed_expression);
		return computed_expression;
	}

	private static String strip_all_whitespace(String expression) {
		return expression.replaceAll("\\s","");
	}

	private static String compute(String expression) {
		return simplify(simplify_brackets(expression));
	}

	private static String simplify_brackets(String expression) {
		Matcher matcher = BRACKET_PATTERN.matcher(expression);
		String sub_expression;
		while (matcher.find()) {
			sub_expression = expression.substring(matcher.start()+1
					, matcher.end()-1);
			expression = expression.replace(matcher.group()
					, compute(sub_expression));
			matcher = BRACKET_PATTERN.matcher(expression);
		}
		return expression;
	}

	private static String simplify(String expression) {
		while (any_operator(expression)) {
			if (expression.contains(Operation.EXPONENTIATION.symbol))
				expression = simplify_operation(expression
						, Operation.EXPONENTIATION);
			else if (expression.contains(Operation.MULTIPLICATION.symbol)
					|| expression.contains(Operation.DIVISION.symbol)
					|| expression.contains(Operation.MODULO.symbol))
				expression = simplify_operation(expression
						, Operation.MULTIPLICATION
						, Operation.DIVISION
						, Operation.MODULO);
			else
				expression = simplify_operation(expression
						, Operation.ADDITION
						, Operation.SUBTRACTION);
		}
		return expression;
	}

	private static boolean any_operator(String expression) {
		for (Operation operation : Operation.values())
			if (Operation.SUBTRACTION != operation
					&& expression.contains(operation.symbol))
				return true;
		if (expression.contains(Operation.SUBTRACTION.symbol)) {
			int ndx = expression.indexOf(Operation.SUBTRACTION.symbol);
			if (ndx == 0) {
				expression = expression.substring(1);
				ndx = expression.indexOf(Operation.SUBTRACTION.symbol);
			}
			if (ndx != -1)
				return true;
		}
		return false;
	}

	private static String simplify_operation(String expression
			, Operation... operations_array) {
		Collection<Operation> operations = Arrays.asList(operations_array);
		Integer operation_ndx = get_first_operation_index(expression, operations);
		if (operation_ndx == null)
			return expression;
		String l_half = expression.substring(0, operation_ndx);
		String r_half = expression.substring(operation_ndx+1);
		Operation operation = Operation.get_operation(expression.charAt(operation_ndx));
		ValueHalfPair l_pair = get_first_value(l_half, operations, expression, operation_ndx);
		ValueHalfPair r_pair = get_second_value(r_half);
		String answer = perform_operation(operation, l_pair.value, r_pair.value);
		return l_pair.half + answer + r_pair.half;
	}

	private static Integer get_first_operation_index(String expression
			, Collection<Operation> operations) {
		int count = 0;
		while (!expression.isEmpty() && operations.contains(Operation.get_operation(expression.charAt(0)))) {
			expression = expression.substring(1);
			count++;
		}
		Integer min_ndx = null;
		int ndx;
		for (Operation operation : operations) {
			ndx = expression.indexOf(operation.symbol);
			if (ndx != -1 && (min_ndx == null || min_ndx > ndx))
				min_ndx = Integer.valueOf(ndx);
		}
		if (min_ndx != null)
			min_ndx += count;
		return min_ndx;
	}

	private static ValueHalfPair get_first_value(String l_half
			, Collection<Operation> operations
			, String expression
			, int operation_ndx) {
		String value;
		Integer ndx = get_first_operation_index(reversed(l_half), Arrays.asList(Operation.values()));
		// l_half has form "<val>" -> ndx = null
		// l_half has form "-<val>" -> ndx = 0
		// l_half has form "...<operator><val>" -> ndx = <index of operator>
		// l_half has form "...<operator>-<val>" -> ndx = <index of "-">
		if (ndx == null) {
			value = l_half;
			l_half = "";
		} else {
			ndx = l_half.length() - ndx;
			value = l_half.substring(ndx);
			l_half = l_half.substring(0, ndx);
		}
		if (operations.contains(Operation.SUBTRACTION))
			l_half = expression.substring(0, operation_ndx - value.length());
		if (l_half.length() == 1 && l_half.equals("-")
				|| l_half.length() > 1 && any_operator(l_half.substring(l_half.length()-2,l_half.length()-1))) {
			value = '-' + value;
			l_half = l_half.substring(0, l_half.length()-1);
		}
		return new ValueHalfPair(value, l_half);
	}

	private static String reversed(String str) {
		return new StringBuilder(str).reverse().toString();
	}

	private static ValueHalfPair get_second_value(String r_half) {
		String value;
		Integer ndx = get_first_operation_index(r_half, Arrays.asList(Operation.values()));
		if (ndx == null) {
			value = r_half;
			r_half = "";
		} else {
			value = r_half.substring(0, ndx);
			r_half = r_half.substring(ndx);
		}
		return new ValueHalfPair(value, r_half);
	}

	private static String perform_operation(Operation operation, String a, String b) {
		double a_double = Double.valueOf(a);
		double b_double = Double.valueOf(b);
		double answer = 0.0;
		if (Operation.ADDITION == operation)
			answer = a_double + b_double;
		else if (Operation.SUBTRACTION == operation)
			answer = a_double - b_double;
		else if (Operation.MULTIPLICATION == operation)
			answer = a_double * b_double;
		else if (Operation.DIVISION == operation)
			answer = a_double / b_double;
		else if (Operation.MODULO == operation)
			answer = a_double % b_double;
		else if (Operation.EXPONENTIATION == operation)
			answer = Math.pow(a_double, b_double);
		if (answer == Math.floor(answer))
			return String.format("%d", (int) answer);
		else
			return String.format("%.2f", answer);
	}

	public static void main(String[] args) {
		if (args.length < 1 || Arrays.asList("-h", "-help").contains(args[0]))
			print_help_menu();
		else if ("test".equals(args[0]))
			run_tests();
		else
			c004i(args[0], false);
	}
}
