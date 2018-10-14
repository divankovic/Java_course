package hr.fer.zemris.java.hw03.prob1;

import static hr.fer.zemris.java.hw03.prob1.TokenType.*;
import static hr.fer.zemris.java.hw03.prob1.LexerState.*;

/**
 * A simple lexical analyzer able to recognize types from <code>TokenType</code>.
 * @author Dorian Ivankovic
 *
 */
public class Lexer {
	
	/**
	 * Input data of the lexer used for parsing.
	 */
	private char[] data;
	
	/**
	 * Current recognized token.
	 */
	private Token token;
	
	/**
	 * Index of the first unprocessed symbol.
	 */
	private int currentIndex;
	
	/**
	 * Current state of the lexer.
	 */
	private LexerState state;
	
	/**
	 * Constructor using text for lexer parsing.
	 * @param text - text to parse
	 */
	public Lexer(String text) {
		if(text == null) {
			throw new IllegalArgumentException("Input text must not be null.");
		}
		this.data = text.toCharArray();
		currentIndex = 0;
		state = BASIC;
	}
	
	/**
	 * Generates and returns the next token of the input text.
	 * @return next token of the input text
	 */
	public Token nextToken() {
		extractNextToken();
		return token;
	}
	
	/**
	 * Returns the last generated token.
	 * @return the last generated token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Sets the state of the lexer.
	 * @param state - new state of the lexer
	 * @throws IllegalArgumentException - if state is null
	 */
	public void setState(LexerState state) {
		if(state == null) {
			throw new IllegalArgumentException("State must not be null");
		}
		
		this.state = state;
	}
	
	/**
	 * The method extracts the next token from the input text depending on the current
	 * state of the lexer.
	 * @throws LexerException - if no more tokens are available
	 */
	private void extractNextToken() {
		if(token!=null && token.getType()==EOF) {
			throw new LexerException("No tokens available.");
		}
		
		skipBlanks();
		
		if(currentIndex>=data.length) {
			token = new Token(EOF, null);
			return;
		}
		
		if(state==BASIC) {
			basicExtraction();
		}else {
			extendedExtraction();
		}
		
	}
	
	/**
	 * The method extracts the next token from the input in compliance
	 * with the rules of <code>LexerState.EXTENDED</code> state of the lexer.
	 */
	private void extendedExtraction() {
		if(data[currentIndex]=='#') {
			token = new Token(SYMBOL,'#');
			currentIndex++;
			return;
		}
		
		int end = currentIndex;
		
		while(end<data.length) {
			char current = data[end];
			if(current == '#' || current==' '){
				break;
			}else {
				end++;
			}
		}
		
		token = new Token(WORD,new String(data,currentIndex,end-currentIndex));
		currentIndex = end;
	}

	/**
	 * The method extracts the next token from the input in compliance
	 * with the rules of <code>LexerState.BASIC</code> state of the lexer.
	 * @throws LexerException - if the number in the input is too big
	 */
	private void basicExtraction() {
		char current = data[currentIndex];
		
		if(Character.isLetter(current) || (current=='\\'&&checkEscape(currentIndex)) ) {
			StringBuilder word = new StringBuilder();
			
			int end = currentIndex;
			
			while(end<data.length) {
				char c = data[end];
				if(Character.isLetter(c)){
					word.append(c);
					end++;
				}else if(c=='\\' && checkEscape(end)) {
					word.append(data[++end]);
					end++;
				}else {
					break;
				}
				
			}
			
			token = new Token(WORD,word.toString());
			currentIndex = end;
			
		}else if(Character.isDigit(current)) {
			int end = currentIndex+1;
			while(end<data.length && Character.isDigit(data[end])) {
				end++;
			}
			
			String number = new String(data,currentIndex,end-currentIndex);
			try {
				long num = Long.parseLong(number);
				token = new Token(NUMBER,num);
				currentIndex = end;
			}catch(NumberFormatException ex) {
				throw new LexerException("Number "+number+" is too big.");
			}
			
		}else{
			token = new Token(SYMBOL,current);
			currentIndex++;
		}
	}

	/**
	 * The method checks if the escape character in the input is legal, it is only
	 * if it is followed by \ or by a digit.
	 * @param end - index of the escape character to check
	 * @return true if the escape character is legal
	 * @throws LexerExcption - if the escape character is not followed by \ or a digit
	 */
	private boolean checkEscape(int end) {
		end++;
		
		if(end>=data.length || (data[end]!='\\' && !Character.isDigit(data[end]))) {
			throw new LexerException("\\ must be followed by \\ or by a digit.");
		}
		
		return true;
		
	}

	/**
	 * The method skips blanks in the input text - \r, \n, \t and spaces.
	 */
	private void skipBlanks() {
		
		while(currentIndex<data.length) {
			char c = data[currentIndex];
			if(c=='\r' || c=='\n' || c=='\t' || c==' ') {
				currentIndex++;
			}else {
				break;
			}
		}
		
	}
	
}
