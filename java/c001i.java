/*
 * Solution to /r/dailyprogrammer Challenge #1 Intermediate
 * Jimmy Nguyen
 *
 * Prompt:
 *     Create a menu driven program that will allow the user to add, edit, and
 * delete events
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pihtx/intermediate_challenge_1/
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public final class c001i {
	private static Scanner scanner;
	private static List<Event> events;

	private static class Event {
		public String name;
		public String time;
		public Event() {}
		public Event(String name, String time) {
			this.name = name;
			this.time = time;
		}
		public String getTime() {
			return time;
		}
	}

	private static void printEvents() {
		System.out.printf("\n==================================================\n");
		if (events.isEmpty()) {
			System.out.printf("\n\tNo events found\n");
		} else {
			for (int i = 0; i < events.size(); i++) {
				printEvent(toString(i+1, events.size()), events.get(i));
			}
		}
		System.out.printf("\n\t-------------------------\n");
	}

	private static String toString(int val, int max_val) {
		String val_str = "";
		for (int i = 0; i < String.valueOf(max_val).length() - String.valueOf(val).length(); i++) {
			val_str += " ";
		}
		val_str += String.valueOf(val);
		return val_str;
	}

	private static void printEvent(String event_number, Event event) {
		System.out.printf("\n\t%s: %s - %s", event_number, event.time, event.name);
	}

	private static void printOptions() {
		System.out.printf("\n\ta: add event");
		System.out.printf("\n\td: delete event");
		if (!events.isEmpty()) {
			System.out.printf("\n\te: edit event");
		}
		System.out.printf("\n\tq: exit");
	}

	private static String input(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine();
	}

	private static void addEvents() {
		printEvents();
		System.out.printf("\n\t>> add event <<\n\n");
		Event event = new Event();
		event.name = input(String.format("\tevent name: "));
		event.time = input(String.format("\tevent time: "));
		events.add(event);
		Collections.sort(events, Comparator.comparing(Event::getTime));
	}

	private static void editEvents() {
		printEvents();
		System.out.printf("\n\t>> edit event <<\n\n");
		int i = Integer.valueOf(input(String.format("\tevent number: ")))-1;
		System.out.printf("\n\told event name: %s", events.get(i).name);
		System.out.printf("\n\told event time: %s\n\n", events.get(i).time);
		events.get(i).name = input(String.format("\tnew event name: "));
		events.get(i).time = input(String.format("\tnew event time: "));
		Collections.sort(events, Comparator.comparing(Event::getTime));
	}

	private static void deleteEvents() {
		printEvents();
		System.out.printf("\n\t>> delete event <<\n\n");
		int i = Integer.valueOf(input(String.format("\tevent number: ")))-1;
		events.remove(i);
	}

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		events = new ArrayList<Event>();
		String command = null;
		while (!"q".equals(command)) {
			printEvents();
			printOptions();
			command = input(String.format("\n\n\t>> "));
			if ("a".equals(command)) {
				addEvents();
			} else if ("e".equals(command)) {
				editEvents();
			} else if ("d".equals(command)) {
				deleteEvents();
			}
		}
	}
}
