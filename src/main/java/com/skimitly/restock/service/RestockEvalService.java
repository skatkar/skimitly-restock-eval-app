/**
 * 
 */
package com.skimitly.restock.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	final String[] months = new DateFormatSymbols().getMonths();
	
	List<String> successful_Stocks = new ArrayList<>();
	List<String> out_of_order_Stocks = new ArrayList<>();

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
	
	
	
	/**
	 * Method to parse the orders.json file and create the map
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public Map<String, int[]> loadOrdersFromFile(String fileName) throws Exception{
		
		Map<String, int[]> ordersByMonths = new HashMap<>();
		JSONParser parser = new JSONParser();
		
		try {
			
			JSONArray ordersJson = (JSONArray)parser.parse(new FileReader(fileName));
			
			
			for(Object orderJson : ordersJson) {
				
				// Collect the necessary data from the file
				String item_name = ((JSONObject)orderJson).get("item_ordered").toString();
				int item_quantity = Integer.parseInt((String) ((JSONObject)orderJson).get("item_quantity"));
				int order_month = LocalDateTime.parse((String)((JSONObject)orderJson).get("order_date")).getMonthValue();
				
				// If the map does not contain the mapping for a item yet then create a new array of size 12
				// Else use an existing array and update the quantity for the correct month
				if(ordersByMonths.get(item_name) == null) {
					int[] num_of_orders = new int[12];
					num_of_orders[order_month - 1] = item_quantity;
					ordersByMonths.put(item_name, num_of_orders);
				}else {
					int[] num_of_orders = ordersByMonths.get(item_name);
					num_of_orders[order_month - 1] += item_quantity;
				}
			}
			
		} catch(FileNotFoundException ex) {
			System.out.println("Orders.json file not found.");
			throw ex;
		} catch (IOException | ParseException ex) {
			System.out.println("Exception while processing Orders.json file.");
			throw ex;
		}
	
		return ordersByMonths;
	}
	

	/**
	 * Method to evaluate restock algorithm
	 * @param ordersByMonths
	 * @param restocksByMonths
	 * @return Restock status
	 */
	public List<String> evalRestock(Map<String,int[]> ordersByMonths, Map<String,int[]> restocksByMonths) {
		
		restocksByMonths.forEach((key,value)-> {
			String item_name = key;
			int[] num_of_restocks = value;
			
			// If the restocked item is ordered at least once, go for finding out whether the restock was successful or not
			// Else do the summation of restock quantity in each month for a given item
			if(ordersByMonths.containsKey(item_name)) {
				int[] num_of_orders = ordersByMonths.get(item_name);
				
				processData(item_name,num_of_restocks,num_of_orders);
			}else {
				calculateTotalStock(item_name, num_of_restocks);
			}
		});
		
		return generateRestocksEvalStatus();

	}
	
	private List<String> generateRestocksEvalStatus() {
		if(!out_of_order_Stocks.isEmpty()) {
			out_of_order_Stocks.add(0, "OUT OF STOCK");
			return out_of_order_Stocks;
		}
		
		if(!successful_Stocks.isEmpty()) {
			successful_Stocks.add(0, "SUCCESS");
			return successful_Stocks;
		}
		
		successful_Stocks.add("No restocks");
		return successful_Stocks;
	}

	private void processData(String item_name,int[] num_of_restocks, int[] num_of_orders) {
		int old_stock_quantity = 0;
		
		for(int i=0; i < num_of_restocks.length; i++) {
			if(old_stock_quantity < 0) {
				out_of_order_Stocks.add(item_name + " for the month of " + months[i-1]);
				return;
			}else {
				old_stock_quantity = old_stock_quantity + num_of_restocks[i] - num_of_orders[i];
			}
		}
		
		if(old_stock_quantity != 0)
			successful_Stocks.add(old_stock_quantity + " " + item_name);
	}

	private void calculateTotalStock(String item_name, int[] restockItems) {
		int total = 0;
		for(int restockItem : restockItems) {
			total += restockItem;
		}
		
		successful_Stocks.add(total + " " + item_name);
	}
}
