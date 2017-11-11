/*
 * Solution to /r/dailyprogrammer Challenge #2 Hard
 * Jimmy Nguyen
 *
 * Prompt:
 *     Create a stopwatch program. This program should have start, stop, and lap
 * options, and it should write out to a file to be viewed later.
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pjsdx/difficult_challenge_2/
 *
 * Compilation:
 *     g++ c002h.cpp
 *
 * Usage:
 *     ./a.out c002h
 *         - launches program
 */
#include <fstream>
#include <iomanip>
#include <iostream>
#include <sstream>
#include <sys/time.h>

#define FILENAME "../trash/c002h.log"

std::string input(std::string prompt)
{
	std::string input;
	std::cout << prompt;
	std::getline(std::cin, input);
	return input;
}

std::string to_string(time_t time)
{
	char buffer [20];
	std::strftime(buffer, 20, "%m/%d/%Y %H:%M:%S", std::localtime(&time));
	std::string time_str(buffer);
	return time_str;
}

std::string to_string(double d)
{
	std::stringstream ss;
	ss << std::fixed << std::setprecision(4) << d;
	return ss.str();
}

void write_to_file(std::string message)
{
	std::ofstream file;
	file.open(FILENAME, std::ios_base::app);
	file << message << std::endl;
	std::cout << std::endl << message;
}

bool validate_command(bool prompt_user, std::string command)
{
	if (prompt_user)
		std::cout << std::endl
				<< "> invalid command \""
				<< command
				<< "\". please enter a valid command:";
	else
		prompt_user = true;
	return prompt_user;
}

int main(int argc, char *argv[])
{
	std::cout << std::endl << "==================================================";
	std::cout << std::endl << "> STOPWATCH (" << FILENAME << ")";
	std::cout << std::endl << "==================================================";
	bool is_terminate = false;
	bool prompt_user = false;
	struct timeval start_time_tv, end_time_tv;
	time_t start_time = -1, end_time;
	std::string command, end_datestr;
	double milliseconds_passed;
	while (!is_terminate)
	{
		if (prompt_user)
		{
			command = input("> ");
			std::cout << "--------------------------------------------------";
		}
		if (start_time == -1)
		{
			if (command.compare("q") == 0)
				is_terminate = true;
			else if (command.compare("s") == 0)
			{
				gettimeofday(&start_time_tv, NULL);
				start_time = start_time_tv.tv_sec;
				write_to_file("started: " + to_string(start_time));
				prompt_user = false;
			}
			else
			{
				prompt_user = validate_command(prompt_user, command);
				std::cout << std::endl << "> enter s to start";
				std::cout << std::endl << "> enter q to quit";
				std::cout << std::endl << "--------------------------------------------------";
				std::cout << std::endl;
			}
		}
		else
		{
			gettimeofday(&end_time_tv, NULL);
			end_time = end_time_tv.tv_sec;
			end_datestr = to_string(end_time);
			milliseconds_passed = (end_time_tv.tv_sec - start_time_tv.tv_sec)*1000 + (end_time_tv.tv_usec - start_time_tv.tv_usec)/1000;
			if (command.compare("e") == 0)
			{
				write_to_file("ended  : " + end_datestr + " - " + to_string(milliseconds_passed/1000) + " seconds passed");
				start_time = -1;
				prompt_user = false;
			}
			else if (command.compare("l") == 0)
			{
				write_to_file("lapped : " + end_datestr + " - " + to_string(milliseconds_passed/1000) + " seconds passed");
				start_time = end_time;
			}
			else
				prompt_user = validate_command(prompt_user, command);
			if (start_time != -1)
			{
				std::cout << std::endl << "> enter e to stop";
				std::cout << std::endl << "> enter l to lap";
				std::cout << std::endl << "--------------------------------------------------";
				std::cout << std::endl;
			}
		}
	}
	std::cout << std::endl << std::endl;
	return 0;
}
