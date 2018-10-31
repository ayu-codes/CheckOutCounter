package com.ayu.CheckOutCounter.utils;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.ayu.CheckOutCounter.model.Product;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

@Component
public class ProductJsonParser {
	private static ObjectMapper mapper;
	private static ObjectReader reader;
	static {
		ProductJsonParser.mapper = new ObjectMapper();
		ProductJsonParser.reader = ProductJsonParser.mapper.readerFor(JsonNode.class);
	}

	public String getProductName(String productDetailsJson) {
		String productName = "";
		try {
			JsonNode node = ProductJsonParser.reader.readTree(productDetailsJson);
			JsonNode prodNameNode = node.get("productName");
			if (prodNameNode != null) {
				productName = prodNameNode.asText();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return productName;
	}

	public String getProductCategory(String productDetailsJson) {
		String productName = "";
		try {
			JsonNode node = ProductJsonParser.reader.readTree(productDetailsJson);
			JsonNode productCategoryNode = node.get("productCategory");
			if (productCategoryNode != null) {
				productName = productCategoryNode.asText();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return productName;
	}

	public double getProductCost(String productDetailsJson) {
		double productCost = 0;
		try {
			JsonNode node = ProductJsonParser.reader.readTree(productDetailsJson);
			JsonNode productCostNode = node.get("productCost");
			if (productCostNode != null) {
				productCost = productCostNode.asDouble();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return productCost;
	}
	
	public ObjectMapper getMapper() {
		return ProductJsonParser.mapper;
	}
}
