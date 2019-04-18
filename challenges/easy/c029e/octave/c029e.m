% Solution to /r/dailyprogrammer Challenge #29 Easy
% Jimmy Nguyen
%
% Link to challenge:
%     https://www.reddit.com/r/dailyprogrammer/comments/r8a70/3222012_challenge_29_easy/
function is_palindrome = c029e(str, is_file)
    if is_file
        str = strjoin(textread(str, '%s'), '');
    endif
    str = lower(str);
    str = str(str >= 'a' & str <= 'z');
    is_palindrome = strcmp(str, str(end:-1:1));
endfunction