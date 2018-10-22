package com.iprimed.utility;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iprimed.model.Customer;
import com.iprimed.model.Product;

public class JsonManager {

	JsonObject jsonObject;

	public Customer JsonToCustomer(JsonObject custJsonObj) {
		Customer customer = new Customer();

		customer.setFirstName(custJsonObj.get("firstName").getAsString());
		customer.setLastName(custJsonObj.get("lastName").getAsString());
		customer.setEmail(custJsonObj.get("email").getAsString());
		customer.setAadhaarNo(custJsonObj.get("aadhaarNo").getAsLong());
		customer.setPassword(custJsonObj.get("password").getAsString());
		customer.setMobileNumber(custJsonObj.get("mobileNumber").getAsLong());
		customer.setAddress(custJsonObj.get("address").getAsString());
		customer.setCity(custJsonObj.get("city").getAsString());
		customer.setState(custJsonObj.get("state").getAsString());
		customer.setPincode(custJsonObj.get("pincode").getAsLong());
		return customer;

	}

	public String productListToJson(List<Product> productList) {

		JsonArray productListJsonArray = new JsonArray();

		for (Product product : productList) {
			jsonObject = new JsonObject();
			jsonObject.addProperty("productId", product.getProductId());
			jsonObject.addProperty("productName", product.getProductName());
			jsonObject.addProperty("productPrice", product.getProductPrice());
			jsonObject.addProperty("productDesc", product.getProductDesc());

			productListJsonArray.add(jsonObject);

		}
		return productListJsonArray.toString();

	}

}
