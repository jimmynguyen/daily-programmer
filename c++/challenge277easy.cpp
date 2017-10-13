/* /r/dailyprogrammer challenge #277 - easy
 *
 * https://www.reddit.com/r/dailyprogrammer/comments/4uhqdb/20160725_challenge_277_easy_simplifying_fractions/
 */
#include <iostream>
#include <stdlib.h>
#include <string>

// define gcd prototype
int gcd(int a, int b);

// main function
int main(int argc, char *argv[]) {
	// check that there are 2 program arguments (numerator, denominator)
	if (argc != 3) {
		// print usage if program was called incorrectly
		std::cout << "usage: " << argv[0] << " <numerator> <denominator>" << std::endl;
	} else {
		// convert program arguments to int
		int numerator   = std::stoi(argv[1]);
		int denominator = std::stoi(argv[2]);
		// get greatest common divisor
		int greatest_common_divisor = gcd(abs(numerator), abs(denominator));
		// simplify fraction until it cannot be simplified
		while (greatest_common_divisor > 1) {
			// simplify numerator and denominator
			numerator   /= greatest_common_divisor;
			denominator /= greatest_common_divisor;
			// get next greatest common divisor
			greatest_common_divisor = gcd(abs(numerator), abs(denominator));
		}
		// print output
		std::cout << numerator << " " << denominator << std::endl;
	}
	return 0;
}

// define gcd function
int gcd(int a, int b) {
	return b == 0 ? a : gcd(b, a % b);
}
