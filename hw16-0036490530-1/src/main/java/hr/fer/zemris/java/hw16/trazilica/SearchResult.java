package hr.fer.zemris.java.hw16.trazilica;

import java.nio.file.Path;

/**
 * Represents a result of the search performed by query in {@link Konzola}.
 * Contains a path to the documents and a similarity coefficients
 * computed by {@link DocumentVector#similarity(DocumentVector)}
 * @author Dorian Ivankovic
 *
 */
public class SearchResult {
	
	/**
	 * Similarity coefficient.
	 */
	private double similarity;
	
	/**
	 * Document path.
	 */
	private Path documentPath;
	
	/**
	 * Constructor.
	 * @param similarity - similarity coefficient
	 * @param documentPath - path to the document
	 */
	public SearchResult(double similarity, Path documentPath) {
		super();
		this.similarity = similarity;
		this.documentPath = documentPath;
	}

	/**
	 * Returns the similarity coefficient.
	 * @return the similarity coefficient
	 */
	public double getSimilarity() {
		return similarity;
	}

	/**
	 * Sets the similarity coefficient
	 * @param similarity - new similarity coefficient
	 */
	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	/**
	 * Returns the document path.
	 * @return the document path
	 */
	public Path getDocumentPath() {
		return documentPath;
	}

	/**
	 * Sets the documents path.
	 * @param documentPath - new document path
	 */
	public void setDocumentPath(Path documentPath) {
		this.documentPath = documentPath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentPath == null) ? 0 : documentPath.hashCode());
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
		SearchResult other = (SearchResult) obj;
		if (documentPath == null) {
			if (other.documentPath != null)
				return false;
		} else if (!documentPath.equals(other.documentPath))
			return false;
		return true;
	}
}
