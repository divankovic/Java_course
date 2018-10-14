package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A simple search engine based on <a href="https://en.wikipedia.org/wiki/Tf%E2%80%93idf">tf-idf</a> techique.
 * The program takes the path to documents directory as input(command line argument).
 * The documents are parsed and tf-idf vectors are constructed.
 * 
 * The program interacts with the user as a simple shell application.
 * Supported commands are:
 * <ul> <li>"query" - takes in key words to search in the documents
 * 					  and outputs best matched documents.</li>
 * 		<li>"type" - takes in index of the result of the query and writes out it content</li>
 * 		<li>"results" - displays the best results of the query</li>
 * 		<li>"exit" - exits the application
 * </ul>
 * @author Dorian Ivankovic
 *
 */
public class Konzola {

	/**
	 * Path to stop words. These words are ignored - not included in keywords.
	 */
	public static String STOP_WORDS_PATH = "src/main/resources/hrvatski_stoprijeci.txt";
	
	/**
	 * Maximal number of query results to display.
	 */
	public static final int RESULTS_COUNT = 10;
	
	/**
	 * Values under this threshold are considered to be equal to zero.
	 */
	public static final double SIMILARITY_THRESHOLD = 1E-4;
	
	/**
	 * Key words retrieved from all documents.
	 */
	private static HashSet<String> vocabulary;
	
	/**
	 * Retrieved stop words from <code>STOP_WORDS_PATH</code>.
	 */
	private static HashSet<String> stopWords;

	/**
	 * Parsed documents.
	 */
	private static List<DocumentVector> documents;
	
	/**
	 * Idf vector.
	 */
	private static DocumentVector idfVector;

	/**
	 * Search results.
	 */
	private static List<SearchResult> searchResults;
	
	/**
	 * This method is called once the program is run.
	 * @param args - command line arguments, path to documents directory expected
	 */
	public static void main(String[] args){
		if (args.length != 1) {
			System.out.println("One argument expected - path to textual files directory.");
			return;
		}

		Path path = Paths.get(args[0]).toAbsolutePath().normalize();
		if (!Files.isDirectory(path)) {
			System.out.println(path + " is not a directory.");
			return;
		}

		try {
			loadStopWords();
			loadVocabulary(path);

			buildTfDocumentVectors(path);
			buildIdfVector();

			buildTfIdfVectors();
		} catch (IOException ex) {
			System.out.println(ex.getLocalizedMessage());
			return;
		}
		
		System.out.println("Dictionary size is "+vocabulary.size()+" words.");
 
		try(Scanner sc = new Scanner(System.in)){
loop:		while(true) {
				System.out.print("Enter command > ");
				if(sc.hasNextLine()) {
					String line = sc.nextLine();
					
					if(line.isEmpty()) {
						continue;
					}
					
					String[] lineElements = line.split("\\s+");
					
					String command = lineElements[0].toLowerCase();
					
					switch(command) {
					case "query":
						if(lineElements.length<2) {
							System.out.println("At least one argument is requested for query.");
							continue;
						}
						
						DocumentVector searchVector = new DocumentVector();
						searchVector.init(vocabulary);
						
						List<String> keyWords = new ArrayList<>();
						for(String keyWord:lineElements) {
							if(vocabulary.contains(keyWord)) {
								searchVector.increment(keyWord);
								keyWords.add(keyWord);
							}
						}

						if(keyWords.isEmpty()) {
							System.out.println("No keywords in query!");
							continue;
						}
						
						System.out.print("Query is: [");
						for(int i=0,n=keyWords.size();i<n;i++) {
							System.out.print(keyWords.get(i));
							if(i!=n-1) System.out.print(", ");
						}
						System.out.print("]\n");
						
						searchVector.multiply(idfVector);
						searchVector.normalize();
						
						executeQuery(searchVector);
						break;
					case "type":
						if(searchResults==null) {
							System.out.println("No results available.");
							continue;
						}
						
						if(lineElements.length!=2) {
							System.out.println("One argument expected - index of the result.");
							continue;
						}
						
						try {
							
							int idx = Integer.parseInt(lineElements[1]);
							SearchResult result = searchResults.get(idx);
							System.out.println("Document: "+result.getDocumentPath());
							
							List<String> lines = Files.readAllLines(result.getDocumentPath());
							lines.forEach(System.out::println);
							
						}catch(NumberFormatException ex) {
							System.out.println("Index must be a whole number");
							continue;
						}catch(IndexOutOfBoundsException ex) {
							System.out.println("Invalid index, must be in range [0, "+(searchResults.size()-1)+"].");
							continue;
						}catch(IOException ignorable) {
						}
						
						break;
					case "results":
						if(lineElements.length!=1) {
							System.out.println("No arguments expected");
							continue;
						}
						
						printResults();
						break;
					case "exit":
						break loop;
					default:
						System.out.println("Unknown command");
					}
				}
			}
		}

	}

	/**
	 * The method executed the query by searching the most similar documents using method {@link DocumentVector#similarity(DocumentVector)}
	 * @param searchVector - vector of searched keywords
	 */
	private static void executeQuery(DocumentVector searchVector) {
		searchResults = new ArrayList<>();
		
		for(DocumentVector documentVector:documents) {
			searchResults.add(new SearchResult(documentVector.similarity(searchVector), documentVector.getDocumentPath()));
		}
		
		Collections.sort(searchResults, (r1, r2)->Double.compare(r2.getSimilarity(), r1.getSimilarity()));
		
		searchResults = searchResults.stream().filter(res -> res.getSimilarity()>SIMILARITY_THRESHOLD).collect(Collectors.toList());
	
		printResults();
	}

	/**
	 * The method print out the results of the search query.
	 */
	private static void printResults() {
		if(searchResults==null || searchResults.isEmpty()) {
			System.out.println("No results available");
			return;
		}
		
		System.out.println(searchResults.size()+" best results: ");
		
		for(int i = 0,n=searchResults.size();i<n;i++) {
			SearchResult result = searchResults.get(i);
			System.out.format("[%d] (%.4f) %s\n", i, result.getSimilarity(), result.getDocumentPath());
		}
		
	}

	/**
	 * The method load stop words from the file given by path <code>STOP_WORDS_PATH</code>.
	 * @throws IOException - if the file containing stop words cannot be opened
	 */
	private static void loadStopWords() throws IOException {
		List<String> words = Files.readAllLines(Paths.get(STOP_WORDS_PATH));
		stopWords = new HashSet<>(words);
	}

	
	/**
	 * The method loads the vocabulary from documents inside the directory given by path <code>path</code>
	 * using {@link SimpleFileVisitor}.
	 * @param path - path to documents directory
	 * @throws IOException - if the directory <code>path</code> cannot be opened
	 */
	private static void loadVocabulary(Path path) throws IOException {
		vocabulary = new HashSet<>();
		
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path document, BasicFileAttributes arg1) throws IOException {
				char[] text = new String(Files.readAllBytes(document), StandardCharsets.UTF_8).toCharArray();

				vocabulary.addAll(Util.extractWords(text, stopWords));

				return FileVisitResult.CONTINUE;
			}

		});
		
	}

	
	/**
	 * The method builds tfVector for all documents using the <code>vocabulary</code>.
	 * @param path - path to documents directory
	 * @throws IOException - if the directory <code>path</code> cannot be opened
	 */
	private static void buildTfDocumentVectors(Path path) throws IOException {
		documents = new ArrayList<>();
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path document, BasicFileAttributes arg1) throws IOException {
				char[] text = new String(Files.readAllBytes(document), StandardCharsets.UTF_8).toCharArray();

				DocumentVector documentVector = new DocumentVector(document);
				documentVector.init(vocabulary);

				List<String> words = Util.extractWords(text, stopWords);
				words.forEach(word -> documentVector.increment(word));

				documents.add(documentVector);
				return FileVisitResult.CONTINUE;
			}

		});
	}

	/**
	 * The method builds the idfVector.
	 */
	private static void buildIdfVector() {

		idfVector = new DocumentVector();
		idfVector.init(vocabulary);

		int documentsCount = documents.size();

		for (String word : vocabulary) {
			int documentsWithWord = countDocumentsWithWord(word);

			idfVector.setValue(word, Math.log10(documentsCount / (double) documentsWithWord));
		}

	}

	/**
	 * The method counts the documents containg the <code>word</code> keyword.
	 * @param word - the word that is searched in all documents
	 * @return number of documents containing the word <code>word</code>
	 */
	private static int countDocumentsWithWord(String word) {

		int documentsWithWord = 0;

		for (DocumentVector document : documents) {
			Double value = document.getValue(word);
			if (value != null && value != 0) {
				documentsWithWord++;
			}
		}

		return documentsWithWord;
	}

	/**
	 * The method builds the tf-idf vectors of all documents.
	 */
	private static void buildTfIdfVectors() {
		for (DocumentVector document : documents) {
			document.multiply(idfVector);
			document.normalize();
		}
	}
}
