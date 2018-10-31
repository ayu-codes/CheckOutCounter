package com.ayu.CheckOutCounter.model;

public class Product {
	private String productName;
	private double productCost;
	private String productCategory;
	private double salesTaxPercent;
	private int quantity;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product(String productName, double productCost, String productCategory, double salesTaxPercent, int quantity) {
		super();
		this.productName = productName;
		this.productCost = productCost;
		this.productCategory = productCategory;
		this.salesTaxPercent = salesTaxPercent;
		this.quantity = quantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductCost() {
		return productCost;
	}

	public void setProductCost(double productCost) {
		this.productCost = productCost;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public double getSalesTaxPercent() {
		return salesTaxPercent;
	}

	public void setSalesTaxPercent(double salesTaxPercent) {
		this.salesTaxPercent = salesTaxPercent;
	}
}
