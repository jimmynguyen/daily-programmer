# Solution to /r/dailyprogrammer Challenge #1 Hard
# Jimmy Nguyen
#
# Prompt:
#     Create a program that will guess numbers between 1 and 100, and respond
# appropriately based on whether the users say that number is too high or too
# low
#
# Link to challenge:
#     https://www.reddit.com/r/dailyprogrammer/comments/pii6j/difficult_challenge_1/
HI_CHAR = 'h'
LO_CHAR = 'l'

def c001h():
	MIN_VAL = 0
	MAX_VAL = 100
	is_correct = False
	guesses = []
	guess = computeGuess(MAX_VAL, MIN_VAL)
	raw_input('\n==================================================\n\nPick a number between 1 and 100, inclusive. Once\nyou have chosen an number. Press ENTER to continue'.format())
	while not is_correct:
		answer = raw_input('\n==================================================\n\nIs {} your number? (y or yes|h or high|l or low)\n>> '.format(guess))
		if answer == 'y' or answer == 'yes':
			is_correct = True
			print('\n==================================================\n\nYour number is {}\n'.format(guess))
		elif answer == 'h' or answer == 'high':
			if not guesses:
				guesses.append([MIN_VAL, LO_CHAR])
			guesses.append([guess, HI_CHAR])
			hi, lo = getMostRecentHighAndLowGuesses(guesses)
			guess = computeGuess(hi, lo)
		elif answer == 'l' or answer == 'low':
			if not guesses:
				guesses.append([MAX_VAL, HI_CHAR])
			guesses.append([guess, LO_CHAR])
			hi, lo = getMostRecentHighAndLowGuesses(guesses)
			guess = computeGuess(hi, lo)

def computeGuess(hi, lo):
	return int(round(float(hi - lo) / 2) + lo)

def getMostRecentHighAndLowGuesses(guesses):
	hi = None
	lo = None
	for _, guess in enumerate(guesses):
		if guess[1] == HI_CHAR:
			hi = guess[0]
		else:
			lo = guess[0]
	return hi, lo

if __name__ == '__main__':
	c001h()
