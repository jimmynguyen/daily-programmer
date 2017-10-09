% /r/dailyprogrammer challenge #334 - intermediate
%
% https://www.reddit.com/r/dailyprogrammer/comments/748ba7/20171004_challenge_334_intermediate_carpet/
function challenge_334_intermediate(file_name)
	[num_iterations, rules] = get_inputs(file_name);
	carpet = [0];
	for i = 1:num_iterations
		carpet = iterate(carpet, rules);
	end
	show_image(carpet);

function show_image(carpet)
	carpet = uint8(carpet./max(max(carpet)).*255);
	close all;
	imshow(carpet);

function new_carpet = iterate(carpet, rules)
	new_carpet = [];
	for i = 1:size(carpet, 1)
		new_carpet_row = [];
		for j = 1:size(carpet, 2)
			new_carpet_row = [new_carpet_row, get_carpet(rules(carpet(i, j)+1, :))];
		end
		new_carpet = [new_carpet; new_carpet_row];
	end

function carpet = get_carpet(rule)
	carpet = reshape(rule, 3, 3);

function [num_iterations, rules] = get_inputs(file_name)
	fh = fopen(file_name);
	[num_colors, num_iterations] = strtok(fgetl(fh));
	[num_colors, num_iterations] = deal(str2num(num_colors), str2num(num_iterations));
	rules = [];
	for i = 1:num_colors
		rules(i, 1:9) = cellfun(@str2num, strsplit(fgetl(fh)), 'UniformOutput', true);
	end
	fclose(fh);
