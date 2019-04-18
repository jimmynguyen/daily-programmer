/*
 * Solution to /r/dailyprogrammer Challenge #29 Easy
 * Jimmy Nguyen
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/r8a70/3222012_challenge_29_easy/
 * 
 * Usage:
 *     bash test_kotlin.sh [option] [file_name or string]
 *         [option]
 *             -f    indicates next argument will be file name
 *             -s    indicates next argument will be string
 *         [file_name or string]
 *             input to check if is palindrome
 */
import java.io.File

fun main(args: Array<String>) {
	if (args.isEmpty()) {
		throw IllegalArgumentException("Missing program arguments")
	}
	val flag: String = args[0]
	var text: String
	if (flag == "-f") {
		text = File(args[1]).readLines().joinToString("")
	} else if (flag == "-s") {
		text = args[1]
	} else {
		throw IllegalArgumentException("Invalid option \"${flag}\"")
	}
	println(isPalindrome(text))
}

fun isPalindrome(text: String): Boolean {
	var filteredText = text.filter(Char::isLetter)
	return filteredText.equals(filteredText.reversed(), true)
}