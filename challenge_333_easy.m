% /r/dailyprogrammer challenge #333 - easy
%
% https://www.reddit.com/r/dailyprogrammer/comments/72ivih/20170926_challenge_333_easy_packet_assembler/
function challenge_333_easy(file_name)
    clc;
    messages = struct([]);
    fh = fopen(file_name);
    line = fgetl(fh);
    while ischar(line)
        if ~isempty(line)
            [message_id, packet_id, num_packets] = parsePacket(line);
            if isempty(messages) || ~any([messages.id] == message_id)
                messages(end+1).id = message_id;
                messages(end).packet_count = 0;
            end
            ndx = find([messages.id] == message_id);
            messages(ndx).packets{packet_id+1} = line;
            messages(ndx).packet_count = messages(ndx).packet_count + 1;
            if messages(ndx).packet_count == num_packets
                for i = 1:num_packets
                    display(messages(ndx).packets{i});
                end
            end
        end
        line = fgetl(fh);
    end
    fclose(fh);

function [message_id, packet_id, num_packets] = parsePacket(packet)
    [message_id , packet] = strtok(packet);
    [packet_id  , packet] = strtok(packet);
    [num_packets, ~]      = strtok(packet);
    message_id  = str2num(message_id);
    packet_id   = str2num(packet_id);
    num_packets = str2num(num_packets);
