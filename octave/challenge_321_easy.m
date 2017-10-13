% /r/dailyprogrammer challenge #321 - easy
%
% https://www.reddit.com/r/dailyprogrammer/comments/6jr76h/20170627_challenge_321_easy_talking_clock/
function time_string = challenge_321_easy(time)
    [hours, minutes] = strtok(time, ':');
    [hours, minutes] = deal(str2num(hours), str2num(minutes(2:end)));
    am_pm = 'am';
    if hours > 12
        am_pm = 'pm';
        hours = hours - 12;
    elseif hours == 0
        hours = 12;
    end
    [hours_string, minutes_string] = deal(num2words(hours, false), num2words(minutes, true));
    time_string = sprintf('It''s %s %s%s', hours_string, minutes_string, am_pm);

function words = num2words(num, is_minutes)
    less_than_20_dictionary = {'one', 'two', 'three', 'four', 'five', ...
        'six', 'seven', 'eight', 'nine', 'ten', 'eleven', 'twelve', ...
        'thirteen', 'fourteen', 'fifteen', 'sixteen', 'seventeen', ...
        'eighteen', 'nineteen'};
    tens_dictionary = {'twenty', 'thirty', 'forty', 'fifty'};
    if is_minutes
        ones_digit = mod(num, 10);
        tens_digit = floor(num./10);
        if tens_digit == 0
            if ones_digit == 0
                words = '';
            else
                words = sprintf('oh %s ', less_than_20_dictionary{ones_digit});
            end
        elseif tens_digit == 1
            words = sprintf('%s ', less_than_20_dictionary{num});
        else
            words = sprintf('%s ', tens_dictionary{tens_digit-1});
            if ones_digit ~= 0
                words = sprintf('%s%s ', words, less_than_20_dictionary{ones_digit});
            end
        end
    else
        words = less_than_20_dictionary{num};
    end
