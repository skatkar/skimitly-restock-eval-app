/**
 * 
 */
package com.skimitly.restock.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import com.skimitly.restock.fixture.RestockEvalFixture;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;


/**
 * @author Swapnil
 *
 */
public class RestockEvalServiceTest {

	@Tested
	RestockEvalService service;

	@Mocked
	JSONParser parser;
	
	@Mocked
	FileReader fileReader;
	
	@Test
	public void test_loadRestocksFromFile() throws Exception {

		new Expectations() {
			{
				new FileReader(anyString);
				result=fileReader;
				parser.parse((FileReader) any);
				result = (JSONArray) RestockEvalFixture.getRestocksList();
			}
		};

		Map<String, int[]> restocksByMonths = service.loadRestocksFromFile("restocks.json");

				
		assertNotNull(restocksByMonths);
		assertEquals(5,restocksByMonths.size());
		
		assertNotNull(restocksByMonths.get("shovel"));
		assertEquals(12,restocksByMonths.get("shovel").length);
		
		assertNotNull(restocksByMonths.get("snowblower"));
		assertEquals(12,restocksByMonths.get("snowblower").length);
		
		assertNotNull(restocksByMonths.get("tire"));
		assertEquals(12,restocksByMonths.get("tire").length);
		
		assertNotNull(restocksByMonths.get("skis"));
		assertEquals(12,restocksByMonths.get("skis").length);
		
		assertNotNull(restocksByMonths.get("sled"));
		assertEquals(12,restocksByMonths.get("sled").length);
		
		assertEquals(25,restocksByMonths.get("shovel")[0]);
		assertEquals(5,restocksByMonths.get("snowblower")[1]);
		assertEquals(5,restocksByMonths.get("tire")[2]);
		assertEquals(5,restocksByMonths.get("skis")[3]);
		assertEquals(15,restocksByMonths.get("sled")[4]);
	}
	
	
	@Test(expected = FileNotFoundException.class)
	public void test_loadRestocksFromFile_FileNotFoundException() throws Exception {
		new Expectations() {
			{
				new FileReader(anyString);
				result = new FileNotFoundException();
			}
		};

		Map<String, int[]> restockMonthmap = service.loadRestocksFromFile("restocks.json");
		assertNotNull(restockMonthmap);
	}
	
	
	@Test(expected = IOException.class)
	public void test_loadRestocksFromFile_IOException() throws Exception {
		new Expectations() {
			{
				new FileReader(anyString);
				result = new IOException();
			}
		};

		Map<String, int[]> restockMonthmap = service.loadRestocksFromFile("restocks.json");
		assertNotNull(restockMonthmap);
	}
	
	@Test
	public void test_loadOrdersFromFile() throws Exception {

		new Expectations() {
			{
				new FileReader(anyString);
				result=fileReader;
				parser.parse((FileReader) any);
				result = (JSONArray) RestockEvalFixture.getOrdersList();
			}
		};

		Map<String, int[]> ordersByMonths = service.loadOrdersFromFile("orders.json");

		assertNotNull(ordersByMonths);
		assertEquals(2,ordersByMonths.size());
		assertNotNull(ordersByMonths.get("tire"));
		assertNotNull(ordersByMonths.get("sled"));
		assertEquals(12,ordersByMonths.get("tire").length);
		assertEquals(12,ordersByMonths.get("sled").length);
		assertEquals(20,ordersByMonths.get("tire")[1]);
		assertEquals(6, ordersByMonths.get("sled")[2]);
	}

	
	@Test(expected = FileNotFoundException.class)
	public void test_loadOrdersFromFile_FileNotFoundException() throws Exception {
		new Expectations() {
			{
				new FileReader(anyString);
				result = new FileNotFoundException();
			}
		};

		Map<String, int[]> orderMonthmap = service.loadOrdersFromFile("orders.json");
		assertNotNull(orderMonthmap);
	}

	
	@Test(expected = IOException.class)
	public void test_loadOrdersFromFile_IOException() throws Exception {

		new Expectations() {
			{
				new FileReader(anyString);
				result = new IOException();
			}
		};

		Map<String, int[]> orderMonthmap = service.loadOrdersFromFile("orders.json");
		assertNotNull(orderMonthmap);
	}
	
	@Test
	public void test_evalRestock_Success() {

		List<String> stocks = service.evalRestock(RestockEvalFixture.getOrdersByMonthsMap(),
				RestockEvalFixture.getRestocksByMonthsMap());

		assertNotNull(stocks);
		assertEquals("SUCCESS", stocks.get(0));
		assertEquals("4 shovel",stocks.get(1));
		assertEquals("4 snowblower",stocks.get(2));
		assertEquals("2 sled",stocks.get(3));
		assertEquals("2 tires",stocks.get(4));
	}
	
	
	@Test
	public void test_evalRestock_out_of_stock() {

		List<String> stocks = service.evalRestock(RestockEvalFixture.getOrdersByMonthsMap(),
				RestockEvalFixture.getRestocksByMonthsMap_out_of_stock());

		assertNotNull(stocks);
		assertEquals("OUT OF STOCK", stocks.get(0));
		
	}
	
	
	@Test
	public void test_evalRestock_No_order() {
		List<String> stocks = service.evalRestock(RestockEvalFixture.getOrdersByMonthsMap_No_order(),
				RestockEvalFixture.getRestocksByMonthsMap());

		assertNotNull(stocks);
		assertEquals("120 skis",stocks.get(1));
		assertEquals("130 shovel",stocks.get(2));
		assertEquals("105 snowblower",stocks.get(3));
		assertEquals("96 sled",stocks.get(4));
		assertEquals("100 tires",stocks.get(5));
	}
	
	@Test
	public void test_evalRestocks_Empty() {
		List<String> stocks = service.evalRestock(RestockEvalFixture.getOrdersByMonthsMap(),
				RestockEvalFixture.getRestocksByMonthsMap_Empty());
		assertEquals("No restocks", stocks.get(0));
	}
}
