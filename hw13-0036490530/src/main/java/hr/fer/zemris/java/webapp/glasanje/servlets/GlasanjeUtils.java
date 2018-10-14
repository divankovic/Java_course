package hr.fer.zemris.java.webapp.glasanje.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.zemris.java.webapp.glasanje.MusicalBand;

/**
 * The class provides static methods used for loading {@link MusicalBand}'s definitions and current
 * votes.
 * @author Dorian Ivankovic
 *
 */
public class GlasanjeUtils {

	/**
	 * Loads the bands from the file specified by path and return a map where
	 * each {@link MusicalBand} is mapped to it's id.
	 * @param path - path of the file with the definitions of the band
	 * @return a map where each band is mapped to it's id
	 * @throws IOException - if the definition file couldn't be opened
	 */
	public static HashMap<Integer, MusicalBand> loadBands(Path path) throws IOException{
		List<String> lines = Files.readAllLines(path);
		
		HashMap<Integer, MusicalBand> bands = new HashMap<>();
		
		for(String line:lines) {
			String bandData[] = line.split("\t");
			int id = Integer.parseInt(bandData[0]);
			String name = bandData[1];
			String songUrl = bandData[2];
			
			bands.put(id,new MusicalBand(id, name, songUrl));
		}
		
		return bands;
	}
	
	
	/**
	 * Loads the definition of the bands from the file given by path <code>definition</code>
	 * and the current votes from the file given by path <code>votingResults</code> and
	 * returns a list of {@link MusicalBand}'s.
	 * @param definition - path to the bands definition file
	 * @param votingResults - path to the bands current votes file
	 * @return list of bands
	 * @throws IOException if the definition file or votes file couldn't be opened
	 */
	public static List<MusicalBand> loadVotingData(Path definition, Path votingResults) throws IOException{
		
		HashMap<Integer,MusicalBand> bandMap = GlasanjeUtils.loadBands(definition);

		if (Files.exists(votingResults)) {
			List<String> lines = Files.readAllLines(votingResults, StandardCharsets.UTF_8);

			for (String line : lines) {
				String[] votingData = line.split("\t");
				int id = Integer.parseInt(votingData[0]);
				long votes = Long.parseLong(votingData[1]);
				bandMap.get(id).setVoteCount(votes);
			}
		}
		
		List<MusicalBand> results = bandMap.entrySet().stream().map(entry -> entry.getValue())
										   .sorted((b1, b2) -> Long.compare(b2.voteCount, b1.voteCount))
										   .collect(Collectors.toList());
		
		return results;
	}
}
