package com.iprimed.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.iprimed.model.Customer;
import com.iprimed.model.CustomerProduct;
import com.iprimed.model.Product;

public class DbManager {
	Connection connection;
	Statement statement;
	PreparedStatement preparedStatement;
	ResultSet resultSet;

	// Database parameter
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userName = "niyaz";
	String password = "niyaz@123";

	// Model class
	Product product;
	CustomerProduct customerProduct;

	// for getting database connection
	public Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	// register customer
	public int registerCustomer(Customer customer) {

		int i = 0;
		// creating connection
		connection = getConnection();
		try {
			// auto_increment.nextval -> sequence created in oracle DB for auto incrementing
			// custId
			preparedStatement = connection
					.prepareStatement("insert into customer values(auto_increment.nextval,?,?,?,?,?,?,?,?,?,?)");

			// setting all fields
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getLastName());
			preparedStatement.setString(3, customer.getEmail());
			preparedStatement.setLong(4, customer.getAadhaarNo());
			preparedStatement.setString(5, customer.getPassword());
			preparedStatement.setLong(6, customer.getMobileNumber());
			preparedStatement.setString(7, customer.getAddress());
			preparedStatement.setString(8, customer.getCity());
			preparedStatement.setString(9, customer.getState());
			preparedStatement.setLong(10, customer.getPincode());

			// executing the query after setting all the fields
			i = preparedStatement.executeUpdate();

			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;

	}

	// validating customer
	public int login(String email, String password) {
		int i = 0;

		// creating connection
		connection = getConnection();

		try {
			preparedStatement = connection.prepareStatement("select * from customer where email = ? and password = ?");
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// setting i to 1 if user is a legitimate user
				i = 1;
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;

	}

	// fetching current logged in customer object
	public Customer getCustomerByEmail(String email) {
		Customer customer = new Customer();

		// creating connection
		connection = getConnection();

		try {
			preparedStatement = connection.prepareStatement("select * from customer where email = ?");
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				customer.setCustId(resultSet.getInt(1));
				customer.setFirstName(resultSet.getString(2));
				customer.setLastName(resultSet.getString(3));
				customer.setEmail(email);
				customer.setAadhaarNo(resultSet.getLong(5));
				customer.setPassword(resultSet.getString(6));
				customer.setMobileNumber(resultSet.getLong(7));
				customer.setAddress(resultSet.getString(8));
				customer.setCity(resultSet.getString(9));
				customer.setState(resultSet.getString(10));
				customer.setPincode(resultSet.getLong(11));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return customer;
	}

	// getting all products from DB
	public List<Product> getAllProducts() {

		// getting connection
		connection = getConnection();
		// instantiating list with ArrayList
		List<Product> productList = new ArrayList<>();

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from product");

			// fetching all products from DB by calling .next()
			while (resultSet.next()) {
				product = new Product();
				product.setProductId(resultSet.getInt(1));
				product.setProductName(resultSet.getString(2));
				product.setProductPrice(resultSet.getInt(3));
				product.setProductDesc(resultSet.getString(4));

				// adding product object in to array list
				productList.add(product);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// returning the the list which contains all the objects of product class
		return productList;
	}

	public List userPurchaseEntry(String email, int productId) {

		
		// creating list for returning
		List list = new ArrayList();
		connection = getConnection();
		int i = 0;
		try {
			// purchase_increment.nextval -> sequence created in oracle DB for auto incrementing
			preparedStatement = connection.prepareStatement(
					"insert into customerpurchasedetails values(purchase_increment.nextval,?,?,?,?) ");

			preparedStatement.setString(1, email);
			preparedStatement.setInt(2, productId);
			
			//getting 10 digit sim/dongle number
			long number = generate10DigitNumber();
			preparedStatement.setLong(3,number);
			
			//getting current date
			String date = getCurrentDate();
			preparedStatement.setString(4, date);

			i = preparedStatement.executeUpdate();
			
			// adding 10 digit number, date and i(return of executeUpdate) to the list
			list.add(number);
			list.add(i);
			list.add(date);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	
	public List<CustomerProduct> getCustomerProducts(String email) {
		// getting connection
				connection = getConnection();
				// instantiating list with ArrayList
				List<CustomerProduct> customerProductList = new ArrayList<>();

				try {
					
					preparedStatement = connection.prepareStatement("select * from customerpurchasedetails, product  where customerpurchasedetails.productid = product.productid and customeremail = ?");
					preparedStatement.setString(1, email);
					resultSet = preparedStatement.executeQuery();
					// fetching all customer products from DB by calling .next()
					while (resultSet.next()) {
						customerProduct = new CustomerProduct();
						customerProduct.setProductId(resultSet.getInt(3));
						customerProduct.setDate(resultSet.getString(5));
						customerProduct.setProductName(resultSet.getString("productname"));

						// adding product object in to array list
						customerProductList.add(customerProduct);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// returning the the list which contains all the objects of product class
				return customerProductList;
	}
	
	// for generating 10 digit number
	public long generate10DigitNumber() {
		Random random = new Random();
		char[] digits = new char[10];
		digits[0] = '9';
		for (int i = 1; i < digits.length; i++) {
			digits[i] = (char) (random.nextInt(10) + '0');
		}
		return Long.parseLong(new String(digits));
	}

	// current date in yyyy-mm-dd format
	public String getCurrentDate() {
		java.util.Date date = new java.util.Date();
		return (new SimpleDateFormat("yyyy/MM/dd").format(date));
	}

	
}
