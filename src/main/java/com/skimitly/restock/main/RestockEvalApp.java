/**
 * 
 */
package com.skimitly.restock.main;

import java.util.List;
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
		
		String inputPath = getInputPath(args);
		
		RestockEvalService service  = new RestockEvalService();
		Map<String, int[]> restocksByMonths;
		Map<String, int[]> ordersByMonths;
		
		try {
			restocksByMonths = service.loadRestocksFromFile(inputPath + "restocks.json");
			
			/*System.out.println("Restock stats");
			restocksByMonths.forEach((key,value) ->{
				System.out.print(key + " :[ ");
				for(int i:value) {
					System.out.print(i + ",");
				}
				System.out.println(" ]");
			});*/
			
			ordersByMonths = service.loadOrdersFromFile(inputPath + "orders.json");
			
			/*System.out.println("Order stats");
			ordersByMonths.forEach((key,value) ->{
				System.out.print(key + " :[ ");
				for(int i:value) {
					System.out.print(i + ",");
				}
				System.out.println(" ]");
			});*/
			
			System.out.println("***** Going for evaluation **********");
			List<String> responseStats = service.evalRestock(ordersByMonths, restocksByMonths);
			
			responseStats.forEach((stat) -> System.out.println(stat));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("\nTime took: " + (end - start) + " (ms)");
	}
	
	private static String getInputPath(String[] args) {
		String inputPath = "";
		if(args.length == 0) {
			System.out.println("Path for input files not provided.");
			System.out.println("Moving forward with default files");
		}else	
			inputPath = args[0] + "//";
		return inputPath;
	}

}
