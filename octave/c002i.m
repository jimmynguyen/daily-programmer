% Solution to /r/dailyprogrammer Challenge #2 Intermediate
% Jimmy Nguyen
%
% Prompt:
%     Create a short text adventure that will call the user by their name. The
% text adventure should use standard text adventure commands ("l, n, s, e, i,
% etc.")
%
% Bonus:
%     For extra credit, make sure the program does not fault, quit, glitch,
% fail, or loop no matter what is put in, even empty text or spaces
%
% Link to challenge:
%     https://www.reddit.com/r/dailyprogrammer/comments/pjbuj/intermediate_challenge_2/
function c002i(file_name)
	validate_file(file_name);
	paths = read_story(file_name);
	greet_user();
	is_terminate = false;
	while ~is_terminate
		path = get_path(paths, 'START');
		while ~isempty(path.options) || ~isempty(path.next_path_id)
			print_description(path);
			if ~isempty(path.options)
				path = get_next_path(paths, path);
			else
				path = get_path(paths, path.next_path_id);
			end
		end
		print_description(path);
		is_terminate = prompt_termination();
	end

function validate_file(file_name)
	if ~exist(file_name, 'file')
		error('File does not exist');
	end

function file = read_file(file_name)
	fh = fopen(file_name);
	file = textscan(fh, '%s', 'Delimiter', '\n');
	fclose(fh);
	file = file{1};

function option = parse_option(line)
	[key, option_line] = strtok(line, '- ');
	[description, option_line] = strtok(regexprep(option_line, '^(-| )+', ''), '-');
	path_id = strtok(option_line, '- ');
	option = struct('key', key, 'description', description, 'path_id', path_id, 'line', line);

function paths = read_story(file_name)
	file = read_file(file_name);
	paths = struct([]);
	for i = 1:length(file)
		line = strtrim(file{i});
		if ~isempty(line)
			switch line(1)
				case '@'
					paths(end+1).path_id = regexprep(line, '^(@| )+', '');
					paths(end).description = '';
				case '>'
					paths(end).description = sprintf('%s%s\n', paths(end).description, line);
				case '-'
					paths(end).options(end+1) = parse_option(line);
				case '='
					paths(end).next_path_id = regexprep(line, '^(=| )+', '');
			end
		end
	end

function greet_user()
	fprintf('\n==================================================\n');
	name = input(sprintf('\n> What is your name?\n> '), 's');
	fprintf('\n> Welcome %s! Let the adventure begin...\n', name);

function print_description(path)
	if ~isempty(path.description)
		fprintf('\n%s', path.description);
	end

function print_options(path)
	for i = 1:length(path.options)
		fprintf('\n%s', path.options(i).line);
	end

function path = get_next_path(paths, path)
	key = '';
	options = path.options;
	while ~any(strcmpi(key, {options.key}))
		print_options(path);
		key = input(sprintf('\n\n> '), 's');
		if any(strcmpi(key, {options.key}))
			path = get_path(paths, options(strcmpi(key, {options.key})).path_id);
		else
			fprintf('\n> Invalid option. Please select one of the following options:');
		end
	end

function path = get_path(paths, path_id)
	if ~any(strcmpi(path_id, {paths.path_id}))
		error('Error in .story file. Path "%s" not found', path_id);
	end
	path = paths(strcmpi(path_id, {paths.path_id}));

function is_terminate = prompt_termination()
	is_terminate = false;
	command = -1;
	while isnumeric(command) || ~any(strcmpi(command, {'y', 'n'}))
		if ischar(command)
			fprintf('\n> Invalid option. Please enter a valid option.\n');
		end
		command = input(sprintf('\n> Do you want to start over? (y|n)\n> '), 's');
	end
	if strcmpi(command, 'n')
		is_terminate = true;
	end
