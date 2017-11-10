% Solution to /r/dailyprogrammer Challenge #2 Hard
% Jimmy Nguyen
%
% Prompt:
%     Create a stopwatch program. This program should have start, stop, and lap
% options, and it should write out to a file to be viewed later.
%
% Link to challenge:
%     https://www.reddit.com/r/dailyprogrammer/comments/pjsdx/difficult_challenge_2/
%
% Usage:
%     help c002h
%         - prints help menu
%
%     c002h
%         - launches program
function c002h()
	filename = '../trash/c002h.log';
	fprintf('\n==================================================');
	fprintf('\n> STOPWATCH (%s)', filename);
	fprintf('\n==================================================');
	days_to_seconds = 24*3600;
	is_terminate = false;
	prompt_user = false;
	start_time = [];
	command = '';
	while ~is_terminate
		if prompt_user
			command = input('> ', 's');
			fprintf('--------------------------------------------------');
		end
		if isempty(start_time)
			if strcmpi(command, 'q')
				is_terminate = true;
			elseif strcmp(command, 's')
				start_time = now();
				write_to_file(filename, sprintf('started: %s', date_str(start_time)));
				prompt_user = false;
			else
				prompt_user = validate_command(prompt_user, command);
				fprintf('\n> enter s to start');
				fprintf('\n> enter q to quit');
				fprintf('\n--------------------------------------------------');
				fprintf('\n');
			end
		else
			end_time = now();
			end_datestr = date_str(end_time);
			seconds_passed = (end_time - start_time)*days_to_seconds;
			if strcmpi(command, 'e')
				write_to_file(filename, sprintf('ended  : %s - %.4f seconds passed', end_datestr, seconds_passed));
				start_time = [];
				prompt_user = false;
			elseif strcmpi(command, 'l')
				write_to_file(filename, sprintf('lapped : %s - %.4f seconds passed', end_datestr, seconds_passed));
				start_time = end_time;
			else
				prompt_user = validate_command(prompt_user, command);
			end
			if ~isempty(start_time)
				fprintf('\n> enter e to stop');
				fprintf('\n> enter l to lap');
				fprintf('\n--------------------------------------------------');
				fprintf('\n');
			end
		end
	end
	fprintf('\n');
	fprintf('\n');

function date_num_str = date_str(date_num)
	date_num_str = datestr(date_num, 'mm/dd/yyyy HH:MM:SS');

function write_to_file(filename, message)
	fh = fopen(filename, 'a');
	fprintf(fh, '%s\n', message);
	fclose(fh);
	fprintf('\n%s', message);

function prompt_user = validate_command(prompt_user, command)
	if ~prompt_user
		prompt_user = true;
	else
		fprintf('\n> invalid command "%s". please enter a valid command:', command);
	end
