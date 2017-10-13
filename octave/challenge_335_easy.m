% /r/dailyprogrammer challenge #335 - easy
%
% https://www.reddit.com/r/dailyprogrammer/comments/759fha/20171009_challenge_335_easy_consecutive_distance/
function challenge_335_easy(file_name, varargin)
    clc;
    if nargin > 1
        gap = varargin{1};
    end
    fh = fopen(file_name);
    line = fgetl(fh);
    [num_sequences, rest_line] = strtok(line);
    num_sequences = str2num(num_sequences);
    if sum(line == ' ') > 1
        [~, gap] = strtok(rest_line);
        gap = str2num(gap);
    end
    for i = 1:num_sequences
        sequence = cellfun(@str2num, strsplit(fgetl(fh)), 'UniformOutput', true);
        disp(computeDistanceRating(sequence, gap));
    end
    fclose(fh);

function distance = computeDistanceRating(sequence, gap)
    distance = 0;
    [sorted_sequence, ndx] = sort(sequence);
    sorted_sequence_diff = diff(sorted_sequence);
    for i = 1:length(sorted_sequence_diff)
        j = i;
        while j <= length(sorted_sequence_diff) && sum(sorted_sequence_diff(i:j)) <= gap
            if (sum(sorted_sequence_diff(i:j)) == gap)
                distance = distance + abs(diff(ndx([i, j+1])));
            end
            j = j+1;
        end
    end
