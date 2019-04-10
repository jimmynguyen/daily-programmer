/*
 * Solution to /r/dailyprogrammer Challenge #3 Easy
 * Jimmy Nguyen
 *
 * Prompt:
 *     Write a program that can encrypt texts with an alphabetical Caesar
 * cipher. The cipher can ignore numbers, symbols, and whitespace.
 *
 * Bonus:
 *     For extra credit, add a "decrypt" function to your program
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pkw2m/2112012_challenge_3_easy/
 */
#include <iostream>
#include <stdlib.h>
#include <string>

class CaesarCipher
{
	public:
		CaesarCipher() {};
		std::string encrypt(std::string message, int n);
		std::string decrypt(std::string message, int n);
};

std::string CaesarCipher::encrypt(std::string message, int n)
{
	std::string encrypted_message = "";
	for (unsigned int i = 0; i < message.length(); i++)
	{
		char letter = message[i];
		if (letter >= 'A' && letter <= 'Z')
			encrypted_message += (char)(((letter - 'A' + n) % 26) + 'A');
		else if (letter >= 'a' && letter <= 'z')
			encrypted_message += (char)(((letter - 'a' + n) % 26) + 'a');
		else
			encrypted_message += letter;
	}
	return encrypted_message;
}

std::string CaesarCipher::decrypt(std::string message, int n)
{
	return this->encrypt(message, -n);
}

void print_help_menu()
{
	std::cout << std::endl << "Usage: ./a.out [option] [message] [n]";
	std::cout << std::endl << "    [option]     -d: decrypt";
	std::cout << std::endl << "                 -e: encrypt";
	std::cout << std::endl << "    [message]    The message to encrypt or decrypt";
	std::cout << std::endl << "    [n]          n is the integer to shift by. A";
	std::cout << std::endl << "                 negative n will shift left";
	std::cout << std::endl << std::endl;
}

bool is_integer(std::string n)
{
	return n.find_first_not_of("0123456789") == std::string::npos;
}

std::string compile_message(char *argv[], int argc)
{
	std::string message = "";
	for (unsigned int i = 2; i < argc - 1; i++)
	{
		message += argv[i];
		message += i == argc - 2 ? "" : " ";
	}
	return message;
}

int main(int argc, char *argv[])
{
	if (argc < 4)
	{
		std::cout << std::endl << "Invalid number of arguments" << std::endl;
		print_help_menu();
	}
	else if (strcmp("-d", argv[1]) != 0 && strcmp("-e", argv[1]) != 0)
	{
		std::cout << std::endl << "Invalid option \"" << argv[1] << "\"" << std::endl;
		print_help_menu();
	}
	else if (!is_integer(argv[argc-1]))
	{
		std::cout << std::endl << "Invalid n \"" << argv[argc-1] << "\". n must be an integer" << std::endl;
		print_help_menu();
	}
	else
	{
		CaesarCipher caesar_cipher;
		std::string ciphered_message;
		std::string message = compile_message(argv, argc);
		int n = std::atoi(argv[argc-1]);
		if (strcmp("-d", argv[1]) == 0)
			ciphered_message = caesar_cipher.decrypt(message, n);
		else
			ciphered_message = caesar_cipher.encrypt(message, n);
		std::cout << std::endl << "Plain : " << message;
		std::cout << std::endl << "Cipher: " << ciphered_message;
		std::cout << std::endl << std::endl;
	}

	return 0;
}
