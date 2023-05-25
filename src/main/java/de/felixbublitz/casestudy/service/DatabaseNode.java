package de.felixbublitz.casestudy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.json.JSONObject;

public class DatabaseNode {
	
	
	private HashMap<Character, DatabaseNode> children = new HashMap<>();
	private List<String[]> containingItems = new ArrayList<String[]>();
	
	
	public DatabaseNode() {
		
	}
	
 
	
	public DatabaseNode(Stack<String[]> rawData, int charPosition) {
		
		containingItems =  new ArrayList<String[]>(rawData);

		
		char groupChar = '\0';
		Stack<String[]> group = new Stack<String[]>();
		
		while(rawData.size() != 0) {
			
			String[] currentItem = rawData.pop();
			
			String currentStationName = currentItem[ApplicationData.CSV_NAME];
			
			
			if(currentStationName.length()-1 < charPosition) continue;
						

			char currentChar = Character.toUpperCase(currentStationName.charAt(charPosition));
			if(groupChar == '\0') groupChar = currentChar;

						
			if(isUmlaut(currentChar))continue;
			
			if(currentChar != groupChar) {
				addNewGroup(groupChar, group, charPosition+1);
				groupChar = currentChar;
			}
			
			group.add(currentItem);

		}
		
		if(group.size() > 0) {
			addNewGroup(groupChar,group, charPosition+1);
		}
	
		
	}
	
	private void addNewGroup(char c, Stack<String[]> characterGroup, int pos) {
		children.put(c, new DatabaseNode(characterGroup, pos));
	}
	
	public boolean isUmlaut(char c) {
		return (c == 'Ä' || c == 'Ö' || c == 'Ü') ? true : false;
	}
	

	public DatabaseNode getChild(char c) {
		return children.getOrDefault(c, new DatabaseNode());
	}
	
	
	public JSONObject getSearchResult(String term) {
		if(term.length() == 0) return getItemList();
		return children.get(term.charAt(0)).getSearchResult(term.substring(1));
	}
	
	public JSONObject getItemList() {
		
		JSONObject out = new JSONObject();

		out.put(ApplicationData.JSON_STATION_SIZE, containingItems.size());
		
		List<String> formattedStations = new ArrayList<String>();
		for(String[] item : containingItems) {
			formattedStations.add(String.format("%s - %s - %s", item[ApplicationData.CSV_EVA], item[ApplicationData.CSV_DS], item[ApplicationData.CSV_NAME]));
		}
		
		out.put(ApplicationData.JSON_STATION_LIST,  formattedStations);
		return out;
	}
	
	

}
