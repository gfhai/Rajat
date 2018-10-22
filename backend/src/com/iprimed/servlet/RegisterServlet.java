package com.iprimed.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iprimed.model.Customer;
import com.iprimed.utility.DbManager;
import com.iprimed.utility.EmailManager;
import com.iprimed.utility.JsonManager;
import com.iprimed.utility.PdfManager;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	JsonManager jsonManager = new JsonManager();
	DbManager dbManager = new DbManager();
	PdfManager pdfManager;
	Customer customer;
	EmailManager emailManager;

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

		System.out.println(data);
		JsonParser parser = new JsonParser();
		JsonObject regObj = parser.parse(data).getAsJsonObject();
		int i = dbManager.registerCustomer(jsonManager.JsonToCustomer(regObj));
		System.out.println(i);

		// sending response back in form of json
		PrintWriter out = response.getWriter();
		JsonObject responseObj;

		if (i == 1) {
			responseObj = new JsonObject();
			responseObj.addProperty("message", 1);

			// sending response back
			out.write(responseObj.toString());

			// getting current registered customer object via his/her email
			customer = dbManager.getCustomerByEmail(regObj.get("email").getAsString());
			pdfManager = new PdfManager();

			// creating welcome pdf by passing customer object to the method so that his/her
			// info can be fetched in the method
			String fileName = pdfManager.registrationWelcomePdf(customer);
			
			// sending welcome message to customer
			emailManager = new EmailManager();
			emailManager.sendWelcomeMail(customer,fileName);
		} else {
			responseObj = new JsonObject();
			responseObj.addProperty("message", 0);
			out.write(responseObj.toString());
		}

	}

}
