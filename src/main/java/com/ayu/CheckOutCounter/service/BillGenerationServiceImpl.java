package com.ayu.CheckOutCounter.service;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.ayu.CheckOutCounter.model.Product;

public class BillGenerationServiceImpl implements BillGenerationService {

	@Override
	public double calculateSalesTax(Product product) {
		return product.getSalesTaxPercent() / 100 * product.getProductCost();
	}

	@Override
	public StringBuilder printItems(Locale locale, HashMap<String, Product> items) {
		NumberFormat nf = NumberFormat.getInstance(locale);
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		StringBuilder sb = new StringBuilder();
		sb.append("ProductName \t Quantity \t ProductCost \t SalesTaxPercent \t Salestax \t WithTaxCost \n");
		for (Map.Entry<String, Product> entry : items.entrySet()) {
			sb.append(entry.getValue().getProductName() + "\t" + entry.getValue().getQuantity() + "\t"
					+ nf.format(entry.getValue().getProductCost()) + "\t"
					+ nf.format(entry.getValue().getSalesTaxPercent()) + "\t"
					+ nf.format(calculateSalesTax(entry.getValue())) + "\t" + nf.format(getWithTaxCost(entry)) + "\n");
		}
		return sb;
	}

	@Override
	public double printTotalCosts(HashMap<String, Product> items) {
		double sum = 0;
		for (Map.Entry<String, Product> entry : items.entrySet()) {
			sum += getWithTaxCost(entry);
		}
		return sum;
	}

	@Override
	public boolean clearItemList(HashMap<String, Product> items) {
		items = new HashMap<>();
		return true;
	}

	private double getWithTaxCost(Map.Entry<String, Product> entry) {
		return entry.getValue().getQuantity()
				* (entry.getValue().getProductCost() * (1 + entry.getValue().getSalesTaxPercent() / 100));

	}

}
