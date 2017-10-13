# /r/dailyprogrammer challenge #319 - easy
#
# https://www.reddit.com/r/dailyprogrammer/comments/6grwny/20170612_challenge_319_easy_condensing_sentences/
def challenge319easy(sentence):
	words = sentence.split(' ')
	output_words = []
	for i in range(len(words)-1):
		is_found = True
		substr = ''
		letter_index = 0
		while is_found and letter_index < len(words[i+1]):
			substr += words[i+1][letter_index]
			if substr in words[i]:
				letter_index += 1
			else:
				is_found = False
		if letter_index > 0 and words[i][-letter_index:] == words[i+1][:letter_index]:
			words[i+1] = words[i] + words[i+1][letter_index:]
		else:
			output_words.append(words[i])
	output_words.append(words[len(words)-1])
	output_sentence = ' '.join(output_words)
	return output_sentence
