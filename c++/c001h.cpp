/*
 * Solution to /r/dailyprogrammer Challenge #1 Hard
 * Jimmy Nguyen
 *
 * Prompt:
 *     Create a program that will guess numbers between 1 and 100, and respond
 * appropriately based on whether the users say that number is too high or too
 * low
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pii6j/difficult_challenge_1/
 */
#include <iostream>
#include <math.h>
#include <vector>

#define MIN_VAL 0
#define MAX_VAL 100
#define HI_CHAR 'h'
#define LO_CHAR 'l'

class Guess
{
	public:
		Guess(int val, char hi_or_lo);
		int val;
		char hi_or_lo;
};

Guess::Guess(int _val, char _hi_or_lo)
{
	val = _val;
	hi_or_lo = _hi_or_lo;
}

int compute_guess(int hi, int lo)
{
	return (int (round((double (hi - lo)) / 2))) + lo;
}

std::string input(std::string prompt)
{
	std::string input;
	std::cout << prompt;
	std::getline(std::cin, input);
	return input;
}

int* get_most_recent_high_and_low_guesses(std::vector<Guess> guesses)
{
	static int hi_and_lo [2] = {};
	for (int i = 0; i < guesses.size(); i++)
	{
		if (guesses[i].hi_or_lo == HI_CHAR)
		{
			hi_and_lo[0] = guesses[i].val;
		}
		else
		{
			hi_and_lo[1] = guesses[i].val;
		}
	}
	return hi_and_lo;
}

int main(int argc, char *argv[])
{
	bool is_correct = false;
	std::vector<Guess> guesses = {};
	int guess_val = compute_guess(MAX_VAL, MIN_VAL);
	input("\n==================================================\n\nPick a number between 1 and 100, inclusive. Once\nyou have chosen an number. Press ENTER to continue");
	std::string answer;
	while (!is_correct)
	{
		printf("\n==================================================\n\nIs %d your number? (y or yes|h or high|l or low)\n>> ", guess_val);
		answer = input("");
		if (answer == "y" || answer == "yes")
		{
			is_correct = true;
			sprintf(format_str, "\n==================================================\n\nYour number is %d\n", guess_val);
			std::cout << format_str;
		}
		else if (answer == "h" || answer == "high")
		{
			if (guesses.empty())
			{
				guesses.push_back(Guess(MIN_VAL, LO_CHAR));
			}
			guesses.push_back(Guess(guess_val, HI_CHAR));
			int* hi_and_lo = get_most_recent_high_and_low_guesses(guesses);
			guess_val = compute_guess(hi_and_lo[0], hi_and_lo[1]);
		}
		else if (answer == "l" || answer == "low")
		{
			if (guesses.empty())
			{
				guesses.push_back(Guess(MAX_VAL, HI_CHAR));
			}
			guesses.push_back(Guess(guess_val, LO_CHAR));
			int* hi_and_lo = get_most_recent_high_and_low_guesses(guesses);
			guess_val = compute_guess(hi_and_lo[0], hi_and_lo[1]);
		}
	}
	return 0;
}
