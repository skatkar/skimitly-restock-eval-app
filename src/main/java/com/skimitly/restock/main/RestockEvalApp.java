/**
 * 
 */
package com.skimitly.restock.main;

import java.util.Map;

import com.skimitly.restock.service.RestockEvalService;

/**
 * Entry point into the application
 * @author Swapnil
 *
 */
public class RestockEvalApp {

	/**
	 * This method will collect the necessary data, evaluates it and displays the result based on that
	 * @param args
	 */
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		RestockEvalService service  = new RestockEvalService();
		Map<String, int[]> restocksByMonths;
		
		try {
			restocksByMonths = service.loadRestocksFromFile("restocks.json");
			
			System.out.println("Restock stats");
			restocksByMonths.forEach((key,value) ->{
				System.out.print(key + " :[ ");
				for(int i:value) {
					System.out.print(i + ",");
				}
				System.out.println(" ]");
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("\nTime took: " + (end - start) + " (ms)");
	}

}
