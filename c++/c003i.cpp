/*
 * Solution to /r/dailyprogrammer Challenge #3 Intermediate
 * Jimmy Nguyen
 *
 * Prompt:
 *     Write a program that can encrypt texts with an alphabetical substitution
 * cipher. The cipher can ignore numbers, symbols, and whitespace.
 *
 * Bonus (not implemented):
 *     For extra credit, make it encrypt whitespace, numbers, and symbols
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pkwb1/2112012_challenge_3_intermediate/
 *
 * Note:
 *     The following implementation performs a simple substitution with groups
 * of size 4:
 *     https://en.wikipedia.org/wiki/Substitution_cipher#Simple_substitution
 */
#include <iostream>
#define GROUP_SIZE 4

void print_help_menu()
{
	std::cout << std::endl << "Usage: java c003i [message] [keyword]";
	std::cout << std::endl << "    [message]    message to encrypt";
	std::cout << std::endl << "    [keyword]    keyword for cipher";
	std::cout << std::endl << std::endl;
}

char upper_char(char c)
{
	if (c >= 'a' && c <= 'z')
		c -= ('a' - 'A');
	return c;
}

std::string upper(std::string r)
{
	std::string s = "";
	for (unsigned int i = 0; i < r.length(); i++)
		s += upper_char(r[i]);
	return s;
}

std::string strip_nonalpha_chars(std::string r)
{
	std::string s = "";
	for (unsigned int i = 0; i < r.length(); i++)
		if (r[i] >= 'A' && r[i] <= 'Z')
			s += r[i];
	return s;
}

std::string unique(std::string r)
{
	std::string s = "";
	for (unsigned int i = 0; i < r.length(); i++)
		if (s.find(r[i]) == std::string::npos)
			s += r[i];
	return s;
}

std::string get_alphabet(std::string keyword)
{
	std::string original_alphabet = "";
	for (char letter = 'A'; letter <= 'Z'; letter++)
		original_alphabet += letter;
	return unique(upper(keyword) + original_alphabet);
}

std::string encrypt(std::string message, std::string keyword)
{
	message = strip_nonalpha_chars(upper(message));
	std::string alphabet = get_alphabet(keyword);
	std::string encrypted_message = "";
	int n = message.length();
	for (unsigned int i = 0, j; i < n; i+=GROUP_SIZE)
	{
		j = i+GROUP_SIZE-1;
		if (j >= n)
			j = n-1;
		for (unsigned int k = i; k <= j; k++)
			encrypted_message += alphabet[message[k] - 'A'];
		if (j < n-1)
			encrypted_message += ' ';
	}
	return encrypted_message;
}

int main(int argc, char *argv[])
{
	if (argc < 3)
	{
		std::cout << std::endl << "Invalid number of arguments \"" << argc-1 << "\"" << std::endl;
		print_help_menu();
	}
	else
	{
		std::string message(argv[1]);
		std::string keyword(argv[2]);
		std::cout << std::endl << "Plain : " << message;
		std::cout << std::endl << "Cipher: " << encrypt(message, keyword);
		std::cout << std::endl << std::endl;
	}
}
