/*
 * Solution to /r/dailyprogrammer Challenge #1 Easy
 * Jimmy Nguyen
 *
 * Prompt:
 *     Create a program that will ask the user's name, age, and reddit username.
 * Have it tell them the information back, in the format:
 * =============================================================================
 * your name is (blank), you are (blank) years old, and your username is (blank)
 * =============================================================================
 *
 * Bonus:
 *     For extra credit, have the program log this information in a file to be
 * accessed later.
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pih8x/easy_challenge_1/
 */
#include <iostream>
#include <vector>
#include <string>

class Event
{
	public:
		std::string name;
		std::string time;
};

std::string toString(int val, int max_val)
{
	std::string val_str = "";
	for (int i = 0; i < std::to_string(max_val).length() - std::to_string(val).length(); i++)
	{
		val_str += " ";
	}
	val_str += std::to_string(val);
	return val_str;
}

void printEvent(std::string event_number, Event event)
{
	std::cout << "\n\t" << event_number << ": " << event.time << " - " << event.name;
}

void printEvents(std::vector<Event> events)
{
	std::cout << "\n==================================================\n";
	if (events.empty())
	{
		std::cout << "\n\tNo events found\n";
	}
	else
	{
		for (int i = 0; i < events.size(); i++)
		{
			printEvent(toString(i+1, events.size()), events[i]);
		}
	}
	std::cout << "\n\t-------------------------\n";
}

void printOptions(std::vector<Event> events)
{
	std::cout << "\n\ta: add event";
	std::cout << "\n\td: delete event";
	if (!events.empty())
	{
		std::cout << "\n\te: edit event";
	}
	std::cout << "\n\tq: exit";
}

std::string input(std::string prompt)
{
	std::string input;
	std::cout << prompt;
	std::getline(std::cin, input);
	return input;
}

std::vector<Event> sortEvents(std::vector<Event> events)
{
	sort(events.begin(), events.end(), [](const Event& a, const Event& b)
	{
		return a.time < b.time;
	});
	return events;
}

std::vector<Event> addEvent(std::vector<Event> events)
{
	printEvents(events);
	std::cout << "\n\t>> add event <<\n\n";
	Event event;
	event.name = input("\tevent name: ");
	event.time = input("\tevent time: ");
	events.push_back(event);
	return sortEvents(events);
}

std::vector<Event> editEvent(std::vector<Event> events)
{
	printEvents(events);
	std::cout << "\n\t>> edit event <<\n\n";
	int i = std::stoi(input("\tevent number: "))-1;
	std::cout << "\n\told event name: " << events[i].name;
	std::cout << "\n\told event time: " << events[i].time << "\n\n";
	events[i].name = input("\tnew event name: ");
	events[i].time = input("\tnew event time: ");
	return sortEvents(events);
}

std::vector<Event> deleteEvent(std::vector<Event> events)
{
	printEvents(events);
	std::cout << "\n\t>> delete event <<\n\n";
	int i = std::stoi(input("\tevent number: "))-1;
	events.erase(events.begin() + i);
	return events;
}

int main(int argc, char *argv[])
{
	std::vector<Event> events = {};
	std::string command = "";
	while (command != "q")
	{
		printEvents(events);
		printOptions(events);
		command = input("\n\n\t>> ");
		if (command == "a")
		{
			events = addEvent(events);
		}
		else if (command == "e")
		{
			events = editEvent(events);
		}
		else if (command == "d")
		{
			events = deleteEvent(events);
		}
	}
	return 0;
}
