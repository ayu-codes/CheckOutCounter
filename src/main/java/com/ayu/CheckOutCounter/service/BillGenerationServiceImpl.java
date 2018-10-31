package com.ayu.CheckOutCounter.service;

import java.util.HashMap;
import java.util.Map;

import com.ayu.CheckOutCounter.model.Product;

public class BillGenerationServiceImpl implements BillGenerationService {

	@Override
	public double calculateSalesTax(Product product) {
		return product.getSalesTaxPercent() / 100 * product.getProductCost();
	}

	@Override
	public StringBuilder printItems(HashMap<String, Product> items) {
		StringBuilder sb = new StringBuilder();
		sb.append("ProductName \t Quantity \t ProductCost \t SalesTaxPercent \t Salestax \t WithTaxCost \n");
		for (Map.Entry<String, Product> entry : items.entrySet()) {
			sb.append(entry.getValue().getProductName() + "\t" + entry.getValue().getQuantity() + "\t"
					+ String.format("%.2f", entry.getValue().getProductCost()) + "\t"
					+ entry.getValue().getSalesTaxPercent() + "\t"
					+ String.format("%.2f", calculateSalesTax(entry.getValue())) + "\t"
					+ String.format("%.2f", getWithTaxCost(entry)) + "\n");
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
