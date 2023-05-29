package de.felixbublitz.casestudy.service.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.json.JSONObject;

import de.felixbublitz.casestudy.service.ApplicationData;

/*
 * Represents a tree node.
 * All train stations that are on or beneeth the node are represented in the containingItems List
 * 	 * From the given node, all stations are grouped by the next character and stored in the HashMap.

 */

public class DatabaseNode {
	final List<Character> UMLAUTS = Arrays.asList('Ä', 'Ö', 'Ü');
	private HashMap<Character, DatabaseNode> children = new HashMap<>();
	private List<String[]> containingItems = new ArrayList<String[]>();

	public DatabaseNode() {
	}

	/*
	 * @param rawData CSV Data stored as Queue
	 * 
	 * @param charPosition Represent which of the n-letters is regarded by the node
	 */
	public DatabaseNode(Queue<String[]> rawData, int charPosition) {

		containingItems = new ArrayList<String[]>(rawData);

		// processes all train stations without umlauts
		Queue<String[]> itemsWithUmlauts = processList(rawData, charPosition, UMLAUTS);

		// processes all train stations with umlauts
		processList(itemsWithUmlauts, charPosition);

	}

	private Queue<String[]> processList(Queue<String[]> list, int charPosition) {
		return processList(list, charPosition, new ArrayList<Character>());
	}

	/*
	 * Processes the CSV Data and creates a child node for each coming letter after
	 * the current
	 * 
	 * @param processList CSV Data stored as Queue
	 * 
	 * @param charPosition Represent which of the n-letters is regarded by the node
	 * 
	 * @param exclude List of chars that should not be regarded
	 * 
	 * @return List of items that were skipped
	 */
	private Queue<String[]> processList(Queue<String[]> list, int charPosition, List<Character> exclude) {
		char groupChar = '\0';
		Queue<String[]> group = new LinkedList<String[]>();
		Queue<String[]> skippedItems = new LinkedList<String[]>();

		while (list.size() != 0) {

			String[] currentItem = list.poll();

			String currentStationName = currentItem[ApplicationData.CSV_NAME];

			if (currentStationName.length() - 1 < charPosition)
				continue;

			char currentChar = Character.toUpperCase(currentStationName.charAt(charPosition));
			if (groupChar == '\0')
				groupChar = currentChar;

			if (exclude.contains(currentChar)) {
				skippedItems.add(currentItem);
				continue;
			}

			if (currentChar != groupChar) {
				children.put(groupChar, new DatabaseNode(group, charPosition + 1));
				groupChar = currentChar;
			}

			group.add(currentItem);

		}

		if (group.size() > 0)
			children.put(groupChar, new DatabaseNode(group, charPosition + 1));

		return skippedItems;

	}

	/*
	 * Returns all search results for the given search term
	 * 
	 * @param term Search term
	 * 
	 * @return Search result
	 */
	public JSONObject getSearchResult(String term) {
		if (term.length() == 0)
			return formatItems(containingItems);

		DatabaseNode child = children.get(Character.toUpperCase(term.charAt(0)));
		if (child == null)
			return formatItems(new ArrayList<String[]>());
		return child.getSearchResult(term.substring(1));
	}

	/*
	 * Creates a formated result from the given train station items
	 * 
	 * @param items Trains stations
	 */
	private JSONObject formatItems(List<String[]> items) {
		JSONObject out = new JSONObject();
		out.put(ApplicationData.JSON_STATION_SIZE, items.size());

		List<String> formattedStations = new ArrayList<String>();
		for (String[] item : items) {
			formattedStations.add(String.format("%s - %s - %s", item[ApplicationData.CSV_EVA],
					item[ApplicationData.CSV_DS], item[ApplicationData.CSV_NAME]));
		}

		out.put(ApplicationData.JSON_STATION_LIST, formattedStations);
		return out;
	}
}
