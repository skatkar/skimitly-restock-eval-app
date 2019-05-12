/**
 * 
 */
package com.skimitly.restock.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
}
