/*
 * Solution to /r/dailyprogrammer Challenge #5 Easy
 * Jimmy Nguyen
 *
 * Prompt:
 *     Your challenge for today is to create a program in which is password
 * protected, and won't open unless the correct username and password is given.
 *
 * For extra credit, have the user and password in a seperate .txt file.
 * 
 * For even more extra credit, break into your own program :)
 *
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pnhyn/2122012_challenge_5_easy/
 * 
 * Usage:
 *     bash test_kotlin.sh [file_name]
 *         [file_name] - file of logins where each line is formatted as "username password"
 */
import java.io.File

fun main(args: Array<String>) {
	if (args.isEmpty()) {
		println("provide a file of valid usernames and passwords")
		return
	}
	val filename: String = args[0]
	while (true) {
		if (runProgram(filename)) {
			break;
		}
	}
}

fun runProgram(filename: String): Boolean {
	val username: String = askUser("username")
	val password: String = askUser("password")
	return if (verify(filename, username, password)) {
		println("you're in")
		true
	} else {
		println("invalid login. try again")
		false
	}
}

fun askUser(question: String): String {
	print(String.format("%s: ", question))
	return readLine()!!
}

fun verify(filename: String, username: String, password: String): Boolean {
	var valid: Boolean = false
	File(filename).forEachLine {
		if (it == String.format("%s %s", username, password)) {
			valid = true
			return@forEachLine
		}
	}
	return valid
}