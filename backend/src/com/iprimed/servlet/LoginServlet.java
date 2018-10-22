package com.iprimed.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iprimed.model.Customer;
import com.iprimed.utility.DbManager;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DbManager dbManager = new DbManager();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Reading the request from client
		String data;
		String line;

		StringBuilder builder = new StringBuilder();

		BufferedReader reader = request.getReader();

		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}

		data = builder.toString();

		// ----------------------------Convert request to json---------------
		JsonParser parser = new JsonParser();
		JsonObject loginObj = parser.parse(data).getAsJsonObject();
		System.out.println(loginObj);
		
		// getting email and password from JSON object
		String email = loginObj.get("email").getAsString();
		String password = loginObj.get("password").getAsString();
		
		//validating user
		int result = dbManager.login(email,password);
		
	
		
		//sending response back to customer
		PrintWriter out = response.getWriter();
		JsonObject messageObj;
		
		if (result == 1) {
			//getting customer object via email
			Customer customer = dbManager.getCustomerByEmail(email);
			System.out.println(customer);
			messageObj = new JsonObject();
			messageObj.addProperty("message", 1);
			messageObj.add("customer", new Gson().toJsonTree(customer));
			out.write(messageObj.toString());
			
		} else {
			messageObj = new JsonObject();
			messageObj.addProperty("message", 0);
			out.write(messageObj.toString());
		}
		
	}

}
