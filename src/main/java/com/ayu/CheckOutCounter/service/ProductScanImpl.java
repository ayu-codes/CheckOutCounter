package com.ayu.CheckOutCounter.service;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ayu.CheckOutCounter.model.Product;
import com.ayu.CheckOutCounter.service.exception.InvalidProductException;
import com.ayu.CheckOutCounter.utils.CheckOutCounterUtil;

@Service
public class ProductScanImpl implements ProductScanService {

	@Autowired
	CheckOutCounterUtil checkOutCounterUtil;

	@Value("#{${Category}}")
	public Map<String, String> productCategories;

	public Product scanProduct(String productCategory, String productName, double productCost, Locale locale)
			throws InvalidProductException {
		if (productCategory == null || productName == null || productCost < 0) {
			throw new InvalidProductException(checkOutCounterUtil.getMessage(locale, "coc.invalid_product"));
		} else if (productCategories == null || productCategories.get(productCategory) == null) {
			throw new InvalidProductException(checkOutCounterUtil.getMessage(locale, "coc.missing_category"));
		}
		return new Product(productName, productCost, productCategory,
				Double.valueOf(productCategories.get(productCategory)), 1);
	}

	public Map<String, Product> addBillingItems(Map<String, Product> items, String productName, double productCost,
			String productCategory, Locale locale) throws InvalidProductException {
		Product product = scanProduct(productCategory.toUpperCase(), productName.toUpperCase(), productCost, locale);
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
