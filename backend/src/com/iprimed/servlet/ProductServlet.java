package com.iprimed.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iprimed.model.Product;
import com.iprimed.utility.DbManager;
import com.iprimed.utility.JsonManager;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		JsonManager jsonManager = new JsonManager();
		DbManager dbManager =  new DbManager();
		PrintWriter out = response.getWriter();
		
		// get all products from DB and storing it in List
		List<Product> productList = dbManager.getAllProducts();
		
		//converting productList to JsonObject and writing it in the form of String
		out.write(jsonManager.productListToJson(productList));
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
