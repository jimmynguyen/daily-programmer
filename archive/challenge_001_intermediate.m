% /r/dailyprogrammer challenge #1 - intermediate
%
% https://www.reddit.com/r/dailyprogrammer/comments/pihtx/intermediate_challenge_1/
function challenge_001_intermediate
	warning off;
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

	warning on;

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
		case { 'edit', 'delete' }
			events = print_events(events);
			if ~isempty(message)
				fprintf('    %s\n\n', message);
				message = '';
			end
			selected_event = input('    Choose: ');
			if strcmp(window_type, 'edit')
				[events, event, message] = edit_event(events, selected_event);
			else
				[events, event, message] = delete_event(events, selected_event);
			end
			options = {};
			if isempty(message)
				if isstruct(event)
					if strcmp(window_type, 'edit')
						ending = 'ed';
					else
						ending = 'd';
					end
					message = sprintf('%s%s event "%s" successfully', capitalize(window_type), ending, event.name);
				end
				window_type = 'home';
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

function [events, event, message] = edit_event(events, selected_event)
	event = [];
	if ~isnumeric(selected_event) || selected_event > length(events) + 1
		message = 'Please enter a valid event number!';
	else
		message = '';
		if selected_event <= length(events)
			events(selected_event).name = input('    Event Name        : ', 's');
			events(selected_event).time = input('    Event Time (XX:XX): ', 's');
			event = events(selected_event);
		end
	end

function [events, event, message] = delete_event(events, selected_event)
	event = [];
	if ~isnumeric(selected_event) || selected_event > length(events) + 1
		message = 'Please enter a valid event number!';
	else
		message = '';
		if selected_event <= length(events)
			event = events(selected_event);
			events(selected_event) = [];
		end
	end

function print_header
	clear;
	clc;
	fprintf('==================================================\n');
	fprintf('Event Tracker\n');
	fprintf('==================================================\n\n');

function events = print_events(events, varargin)
	is_menu = false;
	if nargin >= 2
		is_menu = varargin{1};
	end
	if isempty(events)
		fprintf('    You have no events!\n');
	else
		[~, ndx] = sort({events.time});
		events = events(ndx);

		for i = 1:length(events)
			if is_menu
				print_event(events(i));
			else
				print_event(events(i), num2str(i));
			end
		end

		if ~is_menu
			fprintf('    %d: Go back to menu\n', length(events)+1);
		end

	end
	fprintf('\n');

function print_event(event, varargin)
	if nargin == 2
		event_number = varargin{1};
		fprintf('    %s: %s - %s\n', event_number, event.time, event.name);
	else
		fprintf('    %s - %s\n', event.time, event.name);
	end

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
