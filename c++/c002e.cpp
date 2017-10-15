/*
 * Solution to /r/dailyprogrammer Challenge #2 Easy
 * Jimmy Nguyen
 *
 * Prompt:
 *     Create a calculator program that can compute a formula: F = M * A
 *
 * Bonus:
 *     For extra credit, the program should also be able to compute M and A
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pjbj8/easy_challenge_2/
 */
#include <iostream>
#include <math.h>
#include <vector>

void print_menu()
{
	std::cout << "\n==================================================\n";
	std::cout << "\n\t1. F = M * A";
	std::cout << "\n\t2. M = F / A";
	std::cout << "\n\t3. A = F / M";
	std::cout << "\n\tq. exit";
	std::cout << "\n\n\t-------------------------\n";
	std::cout << "\n\t>> ";
}

std::string input(std::string prompt)
{
	std::string input;
	std::cout << prompt;
	std::getline(std::cin, input);
	return input;
}

void prompt_and_compute(std::string desired_variable)
{
	std::vector<std::string> variables = {"F", "M", "A"};
	for (int i = 0; i < variables.size(); i++)
	{
		if (variables[i] == desired_variable)
		{
			variables.erase(variables.begin() + i);
			break;
		}
	}
	std::cout << std::endl;
	printf("\t%s = ", variables[0].c_str());
	double input1 = std::stod(input(""));
	printf("\t%s = ", variables[1].c_str());
	double input2 = std::stod(input(""));
	double output;
	if (desired_variable == "M" || desired_variable == "A")
	{
		output = input1 / input2;
	}
	else
	{
		output = input1 * input2;
	}
	printf("\n\t%s = %.2f", desired_variable.c_str(), output);
	printf("\n\n\tPress ENTER to go back to the menu...");
	input("");
}

bool select_formula(std::string command)
{
	bool is_terminate = false;
	if (command == "1") {
		prompt_and_compute("F");
	} else if (command == "2") {
		prompt_and_compute("M");
	} else if (command == "3") {
		prompt_and_compute("A");
	} else if (command == "q") {
		is_terminate = true;
	}
	return is_terminate;
}

int main(int argc, char *argv[])
{
	bool is_terminate = false;
	while (!is_terminate)
	{
		print_menu();
		is_terminate = select_formula(input(""));
	}
	return 0;
}
