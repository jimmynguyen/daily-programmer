# Solution to /r/dailyprogrammer Challenge #380 Easy
# Jimmy Nguyen
#
# Link to challenge:
#     https://www.reddit.com/r/dailyprogrammer/comments/cmd1hb/20190805_challenge_380_easy_smooshed_morse_code_1/
#
# Usage:
#     python c380e.py -m <message>
#         - prints smooshed morse code
#     python c380e.py [-t or --test]
#         - runs through default test cases and raises an assertion error if they fail
import sys

def build_letter_to_morse_dict():
    morse_alphabet = '.- -... -.-. -.. . ..-. --. .... .. .--- -.- .-.. -- -. --- .--. --.- .-. ... - ..- ...- .-- -..- -.-- --..'.split(' ')
    morse_dict = {}
    for i, morse_letter in enumerate(morse_alphabet):
        morse_dict[chr(ord('a') + i)] = morse_letter
    return morse_dict

def smorse(msg):
    letter_to_morse_dict = build_letter_to_morse_dict()
    out = ''
    for letter in msg:
        out += letter_to_morse_dict[letter]
    return out

if __name__ == '__main__':
    if len(sys.argv) <= 1:
        print('ERROR: Missing arguments')
    elif sys.argv[1] == '--test' or sys.argv[1] == '-t':
        assert smorse("sos") == "...---..."
        assert smorse("daily") == "-...-...-..-.--"
        assert smorse("programmer") == ".--..-.-----..-..-----..-."
        assert smorse("bits") == "-.....-..."
        assert smorse("three") == "-.....-..."
    elif len(sys.argv) == 3 and sys.argv[1] == '-m':
        msg = sys.argv[1]
        print('smorse("' + msg + '") => "' + smorse(msg) + '"')
    else:
        print('ERROR: Invalid options')
        print('usage: python c380e.py -m <message> | python c380e.py [-t or --test]')