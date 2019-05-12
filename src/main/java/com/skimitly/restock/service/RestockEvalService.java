/**
 * 
 */
package com.skimitly.restock.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Service class - It serves various methods required by the main method for evaluation
 * @author Swapnil
 *
 */
public class RestockEvalService {


	/**
	 * Method to parse the restocks.json file and create the map
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public Map<String, int[]> loadRestocksFromFile(String fileName) throws Exception{
		
		//Map structure - Item name as a key & restock quantities per month as a value
		Map<String, int[]> restocksByMonths = new HashMap<>();
		JSONParser parser = new JSONParser();
		
		try {
			
			JSONArray restocksJson = (JSONArray)parser.parse(new FileReader(fileName));
						
			for(Object restockJson : restocksJson) {
				
				// Collect the necessary data from the file
				String item_name = ((JSONObject)restockJson).get("item_stocked").toString();
				int item_quantity = Integer.parseInt((String) ((JSONObject)restockJson).get("item_quantity"));
				int restock_month = ZonedDateTime.parse((String)((JSONObject)restockJson).get("restock_date")).getMonthValue();
				
				// If the map does not contain the mapping for a item yet then create a new array of size 12
				// Else use an existing array and update the quantity for the correct month 
				if(restocksByMonths.get(item_name) == null) {
					int[] num_of_restocks = new int[12];
					num_of_restocks[restock_month - 1] = item_quantity;
					restocksByMonths.put(item_name, num_of_restocks);
				}else {
					int[] num_of_restocks = restocksByMonths.get(item_name);
					num_of_restocks[restock_month - 1] += item_quantity;
				}
			}
			
		}catch(FileNotFoundException ex) {
			System.out.println("Restocks.json file not found.");
			throw ex;
		}catch (IOException | ParseException ex) {
			System.out.println("Exception while processing Restocks.json file.");
			throw ex;
		}
		
		return restocksByMonths;
	}
}
