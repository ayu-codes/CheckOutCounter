package com.ayu.CheckOutCounter.service;

import java.util.Map;

import com.ayu.CheckOutCounter.model.Product;
import com.ayu.CheckOutCounter.service.exception.InvalidProductException;

public interface ProductScanService {
	public Product scanProduct(String productCategory, String productName, double productCost)
			throws InvalidProductException;
	public Map<String, Product> addBillingItems(Map<String, Product> items, String productName, double productCost,
			String productCategory) throws InvalidProductException;
}
