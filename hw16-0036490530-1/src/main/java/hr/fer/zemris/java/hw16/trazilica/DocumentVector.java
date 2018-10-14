package hr.fer.zemris.java.hw16.trazilica;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

/**
 * Represents the document and its vector of key word that that represents it's tf-idf vector.
 * @author Dorian Ivankovic
 *
 */
public class DocumentVector {
	
	/**
	 * tf-idf vector.
	 */
	private HashMap<String, Double> wordVector;
	
	/**
	 * Path to the document.
	 */
	private Path documentPath;
	
	/**
	 * Default constructor.
	 */
	public DocumentVector() {
		this(null);
	}
	
	/**
	 * Constructor that takes path to the document.
	 * @param documentPath - path to the document
	 */
	public DocumentVector(Path documentPath) {
		this.documentPath = documentPath;
		wordVector = new HashMap<>();
	}

	/**
	 * Initializes the tf-vector by setting values for all key words in vocabulary to 0.
	 * @param vocabulary - key words
	 */
	public void init(HashSet<String> vocabulary) {
		for(String word : vocabulary) {
			wordVector.put(word, 0.0);
		}
	}
	
	/**
	 * Returns the value of the vector associated with <code>word</code>.
	 * @param word - word to get value of
	 * @return value associated with <code>word</code>
	 */
	public Double getValue(String word) {
		return wordVector.get(word);
	}
	
	/**
	 * Sets the value of the vector associated with <code>word</code>.
	 * @param word - value associated with this word is updated
	 * @param value - new value
	 */
	public void setValue(String word, double value) {
		wordVector.put(word, value);
	}
	
	/**
	 * Increments the value associated with <code>word</code> by 1.
	 * @param word - word to increase value of
	 */
	public void increment(String word) {
		if(wordVector.get(word)!=null) {
			wordVector.put(word, wordVector.get(word)+1);
		}
	}
	
	/**
	 * Calculates the similarity coefficient beetween this and other {@link DocumentVector}
	 * by calculating the vector scalar product.
	 * It is expected that both vectors are normalized.
	 * @param other - {@link DocumentVector} to calculate similarity with
	 * @return similarity coefficient
	 */
	public double similarity(DocumentVector other) {
		double cos = 0;
		
		for(Entry<String, Double> entry:wordVector.entrySet()) {
			cos+=entry.getValue()*other.getValue(entry.getKey());
		}
		
		return cos;
	}
	
	/**
	 * Multiplies this <code>wordVector</code> with <code>idfVector</code> elementwise.
	 * @param idfVector - vector to multiply with
	 */
	public void multiply(DocumentVector idfVector) {
		wordVector.forEach((word, value)-> wordVector.put(word, value*idfVector.getValue(word)));
	}
	
	
	/**
	 * The method normalizes the <code>wordVector</code>.
	 */
	public void normalize(){
		double norm = 0;
		for(Entry<String,Double> entry : wordVector.entrySet()) {
			norm+=entry.getValue()*entry.getValue();
		}
		
		if(norm==0) return;
		final double normF = Math.sqrt(norm);
		wordVector.replaceAll((word, value) -> value/normF);
	}
	
	
	/**
	 * The method returns the document path.
	 * @return the document path
	 */
	public Path getDocumentPath() {
		return documentPath;
	}

	/**
	 * The method sets the document path.
	 * @param documentPath - new document path
	 */
	public void setDocumentPath(Path documentPath) {
		this.documentPath = documentPath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((wordVector == null) ? 0 : wordVector.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentVector other = (DocumentVector) obj;
		if (wordVector == null) {
			if (other.wordVector != null)
				return false;
		} else if (!wordVector.equals(other.wordVector))
			return false;
		return true;
	}
	
	
	
}
