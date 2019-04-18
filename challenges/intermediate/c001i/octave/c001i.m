% Solution to /r/dailyprogrammer Challenge #1 Intermediate
% Jimmy Nguyen
%
% Prompt:
%     Create a menu driven program that will allow the user to add, edit, and
% delete events
%
% Link to challenge:
%     https://www.reddit.com/r/dailyprogrammer/comments/pihtx/intermediate_challenge_1/
function c001i()
	events = struct([]);
	command = '';
	while ~strcmp('q', command)
		printEvents(events);
		printOptions(events);
		command = input(sprintf('\n\n\t>> '), 's');
		if strcmp('a', command)
			events = addEvent(events);
		elseif strcmp('e', command)
			events = editEvent(events);
		elseif strcmp('d', command)
			events = deleteEvent(events);
		end
	end

function events = addEvent(events)
	printEvents(events);
	fprintf('\n\t>> add event <<\n\n');
	event.name = input(sprintf('\tevent name: '), 's');
	event.time = input(sprintf('\tevent time: '), 's');
	events = [events, event];
	[~, ndx] = sort({events.time});
	events = events(ndx);

function events = editEvent(events)
	printEvents(events);
	fprintf('\n\t>> edit event <<\n\n');
	i = input(sprintf('\tevent number: '));
	fprintf('\n\told event name: %s', events(i).name);
	fprintf('\n\told event time: %s\n\n', events(i).time);
	events(i).name = input(sprintf('\tnew event name: '), 's');
	events(i).time = input(sprintf('\tnew event time: '), 's');
	[~, ndx] = sort({events.time});
	events = events(ndx);

function events = deleteEvent(events)
	printEvents(events);
	fprintf('\n\t>> delete event <<\n\n');
	i = input(sprintf('\tevent number: '));
	events(i) = [];

function printOptions(events)
	fprintf('\n\ta: add event');
	fprintf('\n\td: delete event');
	if ~isempty(events)
		fprintf('\n\te: edit event');
	end
	fprintf('\n\tq: exit');

function printEvents(events)
	fprintf('\n==================================================\n');
	if isempty(events)
		fprintf('\n\tNo events found\n');
	else
		for i = 1:length(events)
			printEvent(toString(i, length(events)), events(i));
		end
	end
	fprintf('\n\t-------------------------\n');

function val_str = toString(val, max_val)
	val_str = [blanks(length(num2str(max_val)) - length(num2str(val))), num2str(val)];

function printEvent(event_number, event)
	fprintf('\n\t%s: %s - %s', event_number, event.time, event.name);
