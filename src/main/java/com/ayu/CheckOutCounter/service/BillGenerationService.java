package com.ayu.CheckOutCounter.service;

import java.util.HashMap;
import java.util.Locale;

import com.ayu.CheckOutCounter.model.Product;

public interface BillGenerationService {
	public double calculateSalesTax(Product product);

	public StringBuilder printItems(Locale locale, HashMap<String, Product> items);

	public double printTotalCosts(HashMap<String, Product> items);

	public boolean clearItemList(HashMap<String, Product> items);
}
