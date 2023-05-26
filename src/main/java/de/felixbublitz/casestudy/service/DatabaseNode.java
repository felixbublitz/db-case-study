package de.felixbublitz.casestudy.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.json.JSONObject;

public class DatabaseNode {

	private HashMap<Character, DatabaseNode> children = new HashMap<>();
	private List<String[]> containingItems = new ArrayList<String[]>();
	final List<Character> UMLAUTS = Arrays.asList('Ä', 'Ö', 'Ü');


	public DatabaseNode() {}

	public DatabaseNode(Queue<String[]> rawData, int charPosition) {

		containingItems = new ArrayList<String[]>(rawData);
		
		//proccess all train stations without umlauts
		Queue<String[]> itemsWithUmlauts = processList(rawData, charPosition, UMLAUTS);
		
		//proccess all train stations with umlauts
		processList(itemsWithUmlauts, charPosition);
		
	}
	
	
	private Queue<String[]> processList(Queue<String[]> list, int charPosition){
		return processList(list, charPosition, new ArrayList<Character>());
	}
	
	private Queue<String[]> processList(Queue<String[]> list, int charPosition, List<Character> exclude){
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
				skippedItems.add (currentItem);
				continue;
			}

			if (currentChar != groupChar) {
				children.put(groupChar, new DatabaseNode(group, charPosition + 1));
				groupChar = currentChar;
			}

			group.add(currentItem);

		}

		if (group.size() > 0) children.put(groupChar, new DatabaseNode(group, charPosition + 1));
		
		return skippedItems;

	}



	public boolean isUmlaut(char c) {
		return (c == 'Ä' || c == 'Ö' || c == 'Ü') ? true : false;
	}

	public DatabaseNode getChild(char c) {
		return children.getOrDefault(c, new DatabaseNode());
	}

	public JSONObject getSearchResult(String term) {
		if (term.length() == 0) return getJSONResult(containingItems);
		
		DatabaseNode child = children.get(Character.toUpperCase(term.charAt(0)));
		if(child == null) return getJSONResult(new ArrayList<String[]>());
		return child.getSearchResult(term.substring(1));
	}
	
	private JSONObject getJSONResult(List<String[]> items) {
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
