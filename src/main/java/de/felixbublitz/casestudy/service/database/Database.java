package de.felixbublitz.casestudy.service.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import org.json.JSONObject;

import de.felixbublitz.casestudy.service.ApplicationData;

/**
 * Database allows to store train station data in a tree structure and efficiently search for them
 * 
 */

public class Database {
	DatabaseNode rootNode;
	private double NS_TO_MS = 0.000001;
	private boolean loaded;

	public Database(String fileName)  {
		Queue<String[]> rawData = new LinkedList<String[]>();
		try {
			rawData = readCSVFile(fileName);
			loaded = true;
		}catch(Exception e) {
		}
		rootNode = new DatabaseNode(rawData, 0);
	}
	
	public boolean isLoaded() {
		return loaded;
	}

	/**
	 * Search for all stations beginning with the search term
	 * 
	 * @param term Search term
	 * @return JSONObject containing all stations, number of stations and time taken
	 *         for the search.
	 */
	public JSONObject getSearchResult(String term) {
		long startTime = System.nanoTime();

		JSONObject result = rootNode.getSearchResult(term);

		long deltaTime = System.nanoTime() - startTime;
		result.put(ApplicationData.JSON_TIME, (Math.ceil(deltaTime*NS_TO_MS *100.0)/100.0) + " ms");
		return result;

	}

	/**
	 * Read CSV File as Queue
	 * 
	 * @param fileName Name of csv file in ressources directory
	 * @return Read lines represented as Queue (list of lines)
	 */
	private Queue<String[]> readCSVFile(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)));
		Queue<String[]> out = new LinkedList<String[]>();
		String line;

		reader.readLine(); // skip title line in csv
		while ((line = reader.readLine()) != null) {
			String[] values = line.split(ApplicationData.CSV_DELIMITER);
			out.add(values);
		}
		return out;
	}
}
