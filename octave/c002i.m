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
	start_path_id = '@ START';
	paths = read_story(file_name);
	fprintf('\n==================================================\n');
	name = input(sprintf('\n\t> What is your name?\n\t> '), 's');
	fprintf('\n\t> Welcome %s! Let the adventure begin...\n', name);
	is_terminate = false;
	while ~is_terminate
		path = get_path(paths, start_path_id);
		while ~isempty(path.options) || ~isempty(path.next_path_id)
			if ~isempty(path.description)
				fprintf('\n\t%s', path.description);
			end
			if ~isempty(path.options)
				options = parse_options(path.options);
				key = '';
				while ~any(strcmpi(key, {options.key}))
					for i = 1:length(path.options)
						fprintf('\n\t%s', path.options{i});
					end
					key = input(sprintf('\n\n\t> '), 's');
					if any(strcmpi(key, {options.key}))
						path = get_path(paths, options(strcmpi(key, {options.key})).path_id);
					else
						fprintf('\n\t> Invalid option. Please select one of the following options:');
					end
				end
			else
				path.next_path_id(1) = '@';
				path = get_path(paths, path.next_path_id);
			end
		end
		fprintf('\n\t%s', path.description);
		command = input(sprintf('\n\t> Do you want to start over? (y|n)\n\t> '), 's');
		while ~any(strcmpi(command, {'y', 'n'}))
			fprintf('\n\t> Invalid option. Please enter a valid option:');
			command = input(sprintf('\n\t> Do you want to start over? (y|n)\n\t> '), 's');
		end
		if strcmpi(command, 'n')
			is_terminate = true;
		end
	end

function paths = read_story(file_name)
	fh = fopen(file_name);
	file = textscan(fh, '%s', 'Delimiter', '\n');
	file = file{1};
	ndx = find(cellfun(@(x) ~isempty(strfind(x, '@')), file, 'UniformOutput', true));
	paths = {};
	for i = 1:length(ndx)
		start_ndx = ndx(i)+1;
		if i == length(ndx)
			end_ndx = length(file);
		else
			end_ndx = ndx(i+1)-1;
		end
		paths = [paths; [{file{ndx(i)}}, {file(start_ndx:end_ndx)}]];
	end
	fclose(fh);

function path = get_path(paths, path_id)
	if path_id(1) ~= '@'
		path_id = sprintf('@ %s', path_id);
	end
	for i = 1:length(paths)
		if strcmpi(paths{i, 1}, path_id)
			path = parse_path_lines(paths{i, 2});
			break;
		end
	end

function path = parse_path_lines(path_lines)
	path = struct('description', '', 'options', {{}}, 'next_path_id', '');
	for i = 1:length(path_lines)
		switch path_lines{i}(1)
			case '>'
				path.description = sprintf('%s%s\n\t', path.description, path_lines{i});
			case '-'
				path.options{end+1} = path_lines{i};
			case '='
				path.next_path_id = path_lines{i};
		end
	end

function options = parse_options(option_lines)
	options = struct([]);
	for i = 1:length(option_lines)
		option_line = option_lines{i};
		[key, option_line] = strtok(option_line, '- ');
		[description, option_line] = strtok(option_line(3:end), '-');
		[path_id, option_line] = strtok(option_line, '- ');
		options(end+1) = struct('key', key, 'description', description, 'path_id', path_id);
	end
