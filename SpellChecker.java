
public class SpellChecker {


	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
	}

	public static String tail(String str) {
		// Your code goes here
		//check if it is an empty string.
		if (str.length() == 1) {
			return "";
		}
		//crete a new string from str excluding the first character.
		return str.substring(1);
	}

	public static int levenshtein(String word1, String word2) {
		// Your code goes here

		//To convert the word1 AND word2 to lowercase
		String fixWord1 = word1.toLowerCase();
		String fixWord2 = word2.toLowerCase();

		int distance1 = fixWord1.length();
		int distance2 = fixWord2.length();

		if(distance1 == 0 && distance2 == 0) {
			return 0;
		}else if(distance1 == 0) {
			return distance2;
		} else if(distance2 == 0) {
			return distance1;
		}

		char headA = fixWord1.charAt(0);
		char headB = fixWord2.charAt(0);

		if (headA == headB) {
			return levenshtein(tail(fixWord1), tail(fixWord2));
		} 

		int insert = levenshtein(fixWord1, tail(fixWord2));
   		int delete = levenshtein(tail(fixWord1), fixWord2);
    	int replace = levenshtein(tail(fixWord1), tail(fixWord2));
    	return 1 + Math.min(insert, Math.min(delete, replace));
		//return 1 + Math.min(levenshtein(tail(fixWord1), fixWord2), (Math.min(levenshtein(fixWord1, tail(fixWord2)), levenshtein(tail(fixWord1), tail(fixWord2)))));
	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];
		In in = new In(fileName);
		// Your code here
		for (int i = 0; i < 3000; i++) {
			dictionary[i] = in.readString();
		}
		return dictionary; 
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
		// Your code goes here
		int minDistance = levenshtein(word, dictionary[0]);
		String res = dictionary[0];

		for (int i = 1; i < dictionary.length; i++) {
			int currentDistance = levenshtein(word, dictionary[i]);
			if (currentDistance < minDistance) {
				minDistance = currentDistance;
				res = dictionary[i];
			}
		}

		if (minDistance > threshold) {
			return word;
		}

		return res;

	}
}
