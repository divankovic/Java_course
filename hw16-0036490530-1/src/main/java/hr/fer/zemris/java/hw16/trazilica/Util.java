package hr.fer.zemris.java.hw16.trazilica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * The class provides a utility method for reading keywords from the document.
 * All words in <code>stopWords</code> are ignored.
 * @author Dorian Ivankovic
 *
 */
public class Util {

	/**
	 * The method parses alphabetic groups of symbols and ignoes stop words.
	 * @param text - text to parse
	 * @param stopWords - words to ignore during parsing
	 * @return list of keywords
	 */
	public static List<String> extractWords(char[] text, HashSet<String> stopWords){
		List<String> words = new ArrayList<>();
		
		int idx = 0;

		while (idx < text.length) {

			if (Character.isAlphabetic(text[idx])) {
				int start = idx;
				int end = start;
				while (end < text.length && Character.isAlphabetic(text[end])) {
					end++;
				}
				
				String word = new String(text, start, end - start).toLowerCase();
				
				if(!stopWords.contains(word)) {
					words.add(word);
				}
				
				idx = end;
			} else {
				while (idx<text.length && !Character.isAlphabetic(text[idx])) {
					idx++;
				}
			}

		}
		
		return words;
	}
}
