package com.iprimed.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iprimed.model.CustomerProduct;
import com.iprimed.utility.DbManager;

@WebServlet("/CustomerProductServlet")
public class CustomerProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DbManager dbManager;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Reading the request from client
		String email = request.getParameter("email");
		System.out.println(email);
		dbManager = new DbManager();
		List<CustomerProduct> list = dbManager.getCustomerProducts(email);
		System.out.println(list);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
