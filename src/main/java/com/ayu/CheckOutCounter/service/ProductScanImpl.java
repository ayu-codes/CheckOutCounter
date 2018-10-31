package com.ayu.CheckOutCounter.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;

import com.ayu.CheckOutCounter.model.Product;
import com.ayu.CheckOutCounter.service.exception.InvalidProductException;

@Service
@PropertySources(@PropertySource("classpath:checkoutCounter.properties"))
public class ProductScanImpl implements ProductScanService {

	@Value("#{${Category}}")
	public Map<String, String> productCategories;

	@Override
	public Product scanProduct(String productCategory, String productName, double productCost)
			throws InvalidProductException {
		if (productCategory == null || productName == null || productCost < 0) {
			throw new InvalidProductException("Invalid product details entered.");
		} else if (productCategories == null || productCategories.get(productCategory) == null) {
			throw new InvalidProductException("Product categories missing, please maintain product categories.");
		}
		return new Product(productName, productCost, productCategory,
				Double.valueOf(productCategories.get(productCategory)), 1);
	}

	public Map<String, Product> addBillingItems(Map<String, Product> items, String productName, double productCost,
			String productCategory) throws InvalidProductException {
		Product product = scanProduct(productCategory.toUpperCase(), productName.toUpperCase(), productCost);
		if (items.containsKey(productName.toUpperCase())) {
			int quantity = items.get(productName.toUpperCase()).getQuantity();
			product.setQuantity(quantity + 1);
			items.put(productName.toUpperCase(), product);
		} else {
			items.put(productName.toUpperCase(), product);
		}
		return items;
	}
}
