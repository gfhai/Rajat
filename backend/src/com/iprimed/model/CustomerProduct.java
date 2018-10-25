package com.iprimed.model;

public class CustomerProduct {

	private int productId;
	private String productName;
	private String date;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "CustomerProduct [productId=" + productId + ", productName=" + productName + ", date=" + date + "]";
	}
	
	
}
