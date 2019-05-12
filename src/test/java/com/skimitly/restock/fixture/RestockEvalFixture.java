/**
 * 
 */
package com.skimitly.restock.fixture;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author Swapnil
 *
 */
public class RestockEvalFixture {

	@SuppressWarnings("unchecked")
	public static JSONArray getRestocksList() {
		JSONArray jsonArray = new JSONArray();

		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("restock_date", "2018-01-01T04:00:00Z");
		jsonObject1.put("item_stocked", "shovel");
		jsonObject1.put("item_quantity", "25");
		jsonObject1.put("manufacturer", "Knightly Shovels");
		jsonObject1.put("wholesale_price", " Inc.");
		
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("restock_date", "2018-02-01T04:00:00Z");
		jsonObject2.put("item_stocked", "snowblower");
		jsonObject2.put("item_quantity", "5");
		jsonObject2.put("manufacturer", "Shiveria Manufacturing");
		jsonObject2.put("wholesale_price", " Inc.");
		
		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("restock_date", "2018-03-01T04:00:00Z");
		jsonObject3.put("item_stocked", "tire");
		jsonObject3.put("item_quantity", "5");
		jsonObject3.put("manufacturer", "Great Glacier Goods");
		jsonObject3.put("wholesale_price", " Inc.");
		
		JSONObject jsonObject4 = new JSONObject();
		jsonObject4.put("restock_date", "2018-04-01T04:00:00Z");
		jsonObject4.put("item_stocked", "skis");
		jsonObject4.put("item_quantity", "5");
		jsonObject4.put("manufacturer", "Ice Cap Zone Corp.");
		jsonObject4.put("wholesale_price", " Inc.");
		
		JSONObject jsonObject5 = new JSONObject();
		jsonObject5.put("restock_date", "2018-05-01T04:00:00Z");
		jsonObject5.put("item_stocked", "sled");
		jsonObject5.put("item_quantity", "15");
		jsonObject5.put("manufacturer", "Rosebud");
		jsonObject5.put("wholesale_price", " Inc.");
		
		JSONObject jsonObject6 = new JSONObject();
		jsonObject6.put("restock_date", "2018-03-01T04:00:00Z");
		jsonObject6.put("item_stocked", "snowblower");
		jsonObject6.put("item_quantity", "6");
		jsonObject6.put("manufacturer", "Shiveria Manufacturing");
		jsonObject6.put("wholesale_price", " Inc.");
		
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);
		jsonArray.add(jsonObject3);
		jsonArray.add(jsonObject4);
		jsonArray.add(jsonObject5);
		jsonArray.add(jsonObject6);
		
		return jsonArray;
	}
}
