package de.felixbublitz.casestudy.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import org.json.JSONObject;

public class Database {

	DatabaseNode rootNode;
	private double NS_TO_MS = 0.000000001;

	public Database(String fileName) throws Exception {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)));

		Stack<String[]> rawData = new Stack<String[]>();
		String line;

		reader.readLine(); // skip title line in csv
		while ((line = reader.readLine()) != null) {
			String[] values = line.split(";");
			rawData.push(values);
		}

		rootNode = new DatabaseNode(rawData, 0);
	}

	public JSONObject getSearchResult(String term) {
		long startTime = System.nanoTime();

		JSONObject out = new JSONObject();

		JSONObject result = rootNode.getSearchResult(term);
		out.put(ApplicationData.JSON_STATION_LIST, result.getJSONArray(ApplicationData.JSON_STATION_LIST));
		out.put(ApplicationData.JSON_STATION_SIZE, result.getInt(ApplicationData.JSON_STATION_SIZE));

		long deltaTime = System.nanoTime() - startTime;
		out.put("time_taken", String.format("%f ms", deltaTime * NS_TO_MS).replace(',', '.'));
		return out;

	}

}
