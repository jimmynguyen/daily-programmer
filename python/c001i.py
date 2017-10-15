# Solution to /r/dailyprogrammer Challenge #1 Intermediate
# Jimmy Nguyen
#
# Prompt:
#     Create a menu driven program that will allow the user to add, edit, and
# delete events
#
# Link to challenge:
#     https://www.reddit.com/r/dailyprogrammer/comments/pihtx/intermediate_challenge_1/
def c001i():
	events = []
	command = ''
	while command != 'q':
		printEvents(events)
		printOptions(events)
		command = raw_input('\n\t>> ')
		if command == 'a':
			events = addEvent(events)
		elif command == 'e':
			events = editEvent(events)
		elif command == 'd':
			events = deleteEvent(events)

def addEvent(events):
	printEvents(events)
	print('\n\t>> add event <<\n')
	event = Event()
	event.name = raw_input('\tevent name: ')
	event.time = raw_input('\tevent time: ')
	events.append(event)
	events.sort(key=getTime)
	return events

def editEvent(events):
	printEvents(events)
	print('\n\t>> edit event <<\n')
	i = int(raw_input('\tevent number: '))-1
	print('\n\told event name: {}'.format(events[i].name))
	print('\told event time: {}\n'.format(events[i].time))
	events[i].name = raw_input('\tnew event name: ')
	events[i].time = raw_input('\tnew event time: ')
	events.sort(key=getTime)
	return events

def getTime(event):
	return event.time

def deleteEvent(events):
	printEvents(events)
	print('\n\t>> delete event <<\n')
	i = int(raw_input('\tevent number: '))-1
	del events[i]
	return events

def printOptions(events):
	print('\n\ta: add event')
	print('\td: delete event')
	if events:
		print('\te: edit event')
	print('\tq: exit')

def printEvents(events):
	print('\n==================================================\n')
	if not events:
		print('\tNo events found')
	else:
		for i in range(0,len(events)):
			printEvent(toString(i+1, len(events)), events[i])
	print('\n\t-------------------------')

def toString(val, max_val):
	val_str = ''
	for _ in range(len(str(max_val)) - len(str(val))):
		val_str += ' '
	val_str += str(val)
	return val_str

def printEvent(event_number, event):
	print('\t{}: {} - {}'.format(event_number, event.time, event.name))

class Event:
	name = None
	time = None

if __name__ == '__main__':
	c001i()
