package de.felixbublitz.casestudy.service.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import org.json.JSONObject;

import de.felixbublitz.casestudy.service.ApplicationData;

/**
 * Database allows to store train station data and efficiently search for them
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
		long startTime3 = System.nanoTime();

		JSONObject out = new JSONObject();

		long deltaTime3 = System.nanoTime() - startTime3;

		out.put("time_taken3", deltaTime3 * NS_TO_MS + " ms");

		long startTime2 = System.nanoTime();

		JSONObject result = rootNode.getSearchResult(term);
		long deltaTime2 = System.nanoTime() - startTime2;

		out.put("time_taken2", deltaTime2 * NS_TO_MS + " ms");

		out.put(ApplicationData.JSON_STATION_LIST, result.getJSONArray(ApplicationData.JSON_STATION_LIST));
		out.put(ApplicationData.JSON_STATION_SIZE, result.getInt(ApplicationData.JSON_STATION_SIZE));

		long deltaTime = System.nanoTime() - startTime;
		out.put("time_taken", deltaTime * NS_TO_MS + " ms");
		return out;

	}

	/**
	 * Read CSV File as Queue
	 * 
	 * @param fileName Name of csv file in ressources directory
	 * @return Read lines represented as Queue
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
