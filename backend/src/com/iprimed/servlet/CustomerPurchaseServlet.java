package com.iprimed.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
import com.iprimed.utility.PdfManager;

@WebServlet("/CustomerPurchaseServlet")
public class CustomerPurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DbManager dbManager;
	EmailManager emailManager;
	PdfManager pdfManager;

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
		JsonObject purchaseObj = parser.parse(data).getAsJsonObject();
		System.out.println(purchaseObj);

		// getting email, productName and productId from JSON object
		String email = purchaseObj.get("email").getAsString();
		String productName = purchaseObj.get("productName").getAsString();
		int productId = purchaseObj.get("productId").getAsInt();

		// inserting purchase record into the DB
		dbManager = new DbManager();
		List list = dbManager.userPurchaseEntry(email, productId);

		// getting the sim/dongle number from list
		long number = (long) list.get(0);

		// getting purchase date of product
		String date = (String) list.get(2);

		// sending response back in form of json
		PrintWriter out = response.getWriter();
		JsonObject responseObj;

		if ((int) list.get(1) == 1) {

			// getting customer object via his/her emailId
			Customer customer = dbManager.getCustomerByEmail(email);

			// creating invoice PDF of purchased product
			pdfManager = new PdfManager();
			String fileName = pdfManager.productPurchaseInvoicePdf(customer, number, date, productName);

			// sending invoice to the customer
			emailManager = new EmailManager();
			emailManager.sendPurchaseInvoice(customer, number, date, productName, fileName);

			// sending response back to customer in JSON format
			responseObj = new JsonObject();
			responseObj.addProperty("message", 1);
			out.write(responseObj.toString());
		} else {
			responseObj = new JsonObject();
			responseObj.addProperty("message", 0);
			out.write(responseObj.toString());
		}

	}

}
