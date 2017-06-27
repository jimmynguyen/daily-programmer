% /r/dailyprogrammer challenge #1 - intermediate
%
% https://www.reddit.com/r/dailyprogrammer/comments/pihtx/intermediate_challenge_1/
function challenge_001_intermediate
	events = struct([]);
	is_terminated = false;
	window_type = 'home';
	message = '';

	while ~is_terminated
		[options, events, window_type, message] = show_window(window_type, events, message);

		if ~isempty(options)
			if ~isempty(message)
				fprintf('    %s\n\n', message);
				message = '';
			end
			selected_option = input('    Choose: ');
			[window_type, message, is_terminated] = select_option(options, selected_option, window_type, is_terminated);
		end
	end

	fprintf('\n==================================================\n');
	fprintf('Program Terminated...\n\n');

function [options, events, window_type, message] = show_window(window_type, events, message)
	print_header;
	switch window_type
		case 'add'
			event.name = input('    Event Name        : ', 's');
			event.time = input('    Event Time (XX:XX): ', 's');
			events(end+1) = event;

			options = {};
			window_type = 'home';
			message = sprintf('Added event \"%s\" successfully', event.name);
		case 'edit'
			events = print_events(events, false);

			selected_event = input('    Choose: ');
		case 'delete'
			events = print_events(events, false);
			if ~isempty(message)
				fprintf('    %s\n\n', message);
				message = '';
			end
			selected_event = input('    Choose: ');
			[events, event, message] = delete_event(events, selected_event);
			options = {};
			if isempty(message)
				window_type = 'home';
				message = sprintf('Deleted event \"%s\% successfully', event.name);
			else
				window_type = 'edit';
			end
		case 'home'
			events = print_events(events, true);
			options = print_menu(events);
			fprintf('\n');
	end

function [window_type, message, is_terminated] = select_option(options, selected_option, window_type, is_terminated)
	if ~isnumeric(selected_option) || selected_option > length(options)
		message = 'Please enter a valid option!';
	elseif selected_option == length(options)
		is_terminated = true;
	else
		window_type = options{selected_option};
		message = '';
	end

function [events, event, message] = delete_event(events, selected_event)
	if ~isnumeric(selected_event) || selected_event > length(events)
		message = 'Please enter a valid event number!';
		event = [];
	else
		message = '';
		event = events(selected_event);
		events(selected_event) = [];
	end

function print_header
	clear;
	clc;
	fprintf('==================================================\n');
	fprintf('Event Tracker\n');
	fprintf('==================================================\n\n');

function events = print_events(events, is_menu)
	if isempty(events)
		fprintf('    You have no events!\n');
	else
		[~, ndx] = sort({events.time});
		events = events(ndx);

		for i = 1:length(events)
			if is_menu
				print_event(events(i), char(i + 'a' - 1));
			else
				print_event(events(i), num2str(i));
			end
		end
	end
	fprintf('\n');

function print_event(event, event_number)
	fprintf('    %s: %s - %s\n', event_number, event.time, event.name);

function options = print_menu(events)
	options = print_menu_option('    %d: %s Event\n', 'add', {});

	if ~isempty(events)
		options = print_menu_option('    %d: %s Event\n', 'edit', options);

		options = print_menu_option('    %d: %s Event\n', 'delete', options);
	end

	options = print_menu_option('    %d: %s\n', 'exit', options);

function options = print_menu_option(message, option, options)
	options{end+1} = option;
	fprintf(message, length(options), capitalize(option));

function str = capitalize(str)
	str(1) = upper(str(1));
