/*
 * Solution to /r/dailyprogrammer Challenge #7 Easy
 * Jimmy Nguyen
 * 
 * Link to challenge:
 *     https://www.reddit.com/r/dailyprogrammer/comments/pr2xr/2152012_challenge_7_easy/
 * 
 * Usage:
 *     java -ea c007 test
 *         - runs assertion tests
 * 
 *     java c007 <morse_string>
 *         - prints translated text
 */
import java.lang.IllegalArgumentException;
import java.util.HashMap;
import java.util.Map;

public class c007 {
	private static final Map<String, String> morseCodeMap = new HashMap<>();
	static {
		morseCodeMap.put(".-", "A");
		morseCodeMap.put("-...", "B");
		morseCodeMap.put("-.-.", "C");
		morseCodeMap.put("-..", "D");
		morseCodeMap.put(".", "E");
		morseCodeMap.put("..-.", "F");
		morseCodeMap.put("--.", "G");
		morseCodeMap.put("....", "H");
		morseCodeMap.put("..", "I");
		morseCodeMap.put(".---", "J");
		morseCodeMap.put("-.-", "K");
		morseCodeMap.put(".-..", "L");
		morseCodeMap.put("--", "M");
		morseCodeMap.put("-.", "N");
		morseCodeMap.put("---", "O");
		morseCodeMap.put(".--.", "P");
		morseCodeMap.put("--.-", "Q");
		morseCodeMap.put(".-.", "R");
		morseCodeMap.put("...", "S");
		morseCodeMap.put("-", "T");
		morseCodeMap.put("..-", "U");
		morseCodeMap.put("...-", "V");
		morseCodeMap.put(".--", "W");
		morseCodeMap.put("-..-", "X");
		morseCodeMap.put("-.--", "Y");
		morseCodeMap.put("--..", "Z");
		morseCodeMap.put(".----", "1");
		morseCodeMap.put("..---", "2");
		morseCodeMap.put("...--", "3");
		morseCodeMap.put("....-", "4");
		morseCodeMap.put(".....", "5");
		morseCodeMap.put("-....", "6");
		morseCodeMap.put("--...", "7");
		morseCodeMap.put("---..", "8");
		morseCodeMap.put("----.", "9");
		morseCodeMap.put("-----", "0");
	}
	private static String morseToText(String morse) {
		String sentence = "";
		for (String morseWord : morse.split(" /")) {
			for (String morseCharacter : morseWord.split(" ")) {
				String character = " ";
				if (!morseCharacter.isEmpty()) {
					character = morseCodeMap.get(morseCharacter);
					if (character == null) {
						throw new IllegalArgumentException(String.format("Invalid morse character \"%s\" found", morseCharacter));
					}
				}
				sentence += character;
			}
		}
		return sentence;
	}
	public static void main(String[] args) {
		if (args.length == 0) {
			throw new IllegalArgumentException("Missing argument");
		} else if (args[0].equals("test")) {
			String input = ".... . .-.. .-.. --- / -.. .- .. .-.. -.-- / .--. .-. --- --. .-. .- -- -- . .-. / --. --- --- -.. / .-.. ..- -.-. -.- / --- -. / - .... . / -.-. .... .- .-.. .-.. . -. --. . ... / - --- -.. .- -.--";
			String expectedOutput = "HELLO DAILY PROGRAMMER GOOD LUCK ON THE CHALLENGES TODAY";
			String actualOutput = morseToText(input);
			assert actualOutput.equals(expectedOutput) : String.format("morseToText(\"%s\") returned \"%s\", but expected \"%s\"", input, actualOutput, expectedOutput);
			System.out.println(String.format("morseToText(\"%s\") = \"%s\"", input, actualOutput));
		} else {
			System.out.println(String.format("morseToText(\"%s\") = \"%s\"", args[0], morseToText(args[0])));
		}
	}
}