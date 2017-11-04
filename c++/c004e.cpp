/*
 * Solution to /r/dailyprogrammer Challenge #4 Easy
 * Jimmy Nguyen
 *
 * Prompt:
 *     Create a random password generator
 *
 * Bonus:
 *     For extra credit, allow the user to specify the amount of passwords to
 * generate. For extra extra credit, allow the user to specify the length of the
 * strings he wants to generate
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pm6oj/2122012_challenge_4_easy/
 *
 * Usage:
 *     ./a.out -h|-help
 *         - prints the help menu
 *
 *     ./a.out
 *         - prints a random alphanumeric password with max length 50
 *
 *     ./a.out <len_password>
 *         - prints a random alphanumeric password with length <len_password>
 *
 *     ./a.out <len_password> <num_passwords>
 *         - prints <num_passwords> random alphanumeric passwords with length
 *           <len_password>
 */
#include <iostream>

#define ALPHABET "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
#define MAX_PASSWORD_LENGTH 50

void print_help_menu()
{
	std::cout << std::endl << "Usage:";
	std::cout << std::endl << "    ./a.out -h|-help";
	std::cout << std::endl << "        - prints the help menu";
	std::cout << std::endl << "    ./a.out";
	std::cout << std::endl << "        - prints a random alphanumeric password with max length 50";
	std::cout << std::endl << "    ./a.out <len_password>";
	std::cout << std::endl << "        - prints a random alphanumeric password with length <len_password>";
	std::cout << std::endl << "    ./a.out <len_password> <num_passwords>";
	std::cout << std::endl << "        - prints <num_passwords> random alphanumeric passwords with length";
	std::cout << std::endl << "          <len_password>";
	std::cout << std::endl << std::endl;
}

bool is_integer(std::string n)
{
	return n.find_first_not_of("0123456789") == std::string::npos;
}

int randi(int max)
{
	return rand() % static_cast<int>(max + 1);
}

int** randi(int max, int num_rows, int num_cols)
{
	int** nums = new int*[num_rows];
	for (int i = 0; i < num_rows; i++)
	{
		nums[i] = new int[num_cols];
		for (int j = 0; j < num_cols; j++)
			nums[i][j] = randi(max);
	}
	return nums;
}

void c004e(int argc, char *argv[])
{
	int len_password, num_passwords;

	if (argc >= 2)
	{
		if (std::strcmp("-h", argv[1]) == 0 || std::strcmp("-help", argv[1]) == 0)
		{
			print_help_menu();
			return;
		}
		else if (!is_integer(argv[1]))
		{
			std::cout << std::endl << "Invalid len_password \"" << argv[1] << "\". len_password must be an integer" << std::endl;
			print_help_menu();
			return;
		}
		len_password = std::atoi(argv[1]);
	}
	else
		len_password = randi(MAX_PASSWORD_LENGTH);

	if (argc >= 3)
	{
		if (!is_integer(argv[2]))
		{
			std::cout << std::endl << "Invalid num_passwords \"" << argv[2] << "\". num_passwords must be an integer" << std::endl;
			print_help_menu();
			return;
		}
		num_passwords = std::atoi(argv[2]);
	} else
		num_passwords = 1;

	int** nums = randi(std::strlen(ALPHABET), num_passwords, len_password);
	std::string password;
	for (int i = 0; i < num_passwords; i++)
	{
		password = "";
		for (int j = 0; j < len_password; j++)
			password += ALPHABET[nums[i][j]];
		std::cout << std::endl << password;
	}
	std::cout << std::endl << std::endl;
}

int main(int argc, char *argv[])
{
	c004e(argc, argv);
	return 0;
}
