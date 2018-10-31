package com.ayu.CheckOutCounter.controller;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ayu.CheckOutCounter.constants.Constants;
import com.ayu.CheckOutCounter.model.CheckOutCounterStatus;
import com.ayu.CheckOutCounter.model.Product;
import com.ayu.CheckOutCounter.service.BillGenerationService;
import com.ayu.CheckOutCounter.service.BillGenerationServiceImpl;
import com.ayu.CheckOutCounter.service.ProductScanImpl;
import com.ayu.CheckOutCounter.service.exception.InvalidProductException;
import com.ayu.CheckOutCounter.utils.CheckOutCounterUtil;
import com.ayu.CheckOutCounter.utils.ProductJsonParser;

@RestController
public class CheckOutCounterController {

	@Autowired
	private ProductJsonParser jsonParser;

	@Autowired
	CheckOutCounterUtil checkOutCounterUtil;

	@Autowired
	ProductScanImpl productScan;

	private HashMap<String, Product> items = new HashMap<>();

	@PostMapping(value = { Constants.BILLING_URL, Constants.BILLING_URL_LANG }, headers = "Accept=application/json")
	public ResponseEntity<?> billing(@RequestBody String productDetailsJson, @PathVariable Optional<String> lang,
			@PathVariable Optional<String> country) {
		Locale locale = new Locale("en", "IN");
		if (lang.isPresent() && country.isPresent()) {
			locale = new Locale(lang.get(), country.get());
		}

		String productName = "";
		double productCost = 0;
		String productCategory = "";
		CheckOutCounterStatus checkOutCounterStatus = new CheckOutCounterStatus();
		try {
			productName = jsonParser.getProductName(productDetailsJson);
			productCost = jsonParser.getProductCost(productDetailsJson);
			productCategory = jsonParser.getProductCategory(productDetailsJson);
			if (productName == null || productName.isEmpty()) {
				checkOutCounterStatus.setDescription(checkOutCounterUtil.getMessage(locale, "coc.bad_request"));
				checkOutCounterStatus.setStatus(HttpStatus.BAD_REQUEST.toString());
				return new ResponseEntity<CheckOutCounterStatus>(checkOutCounterStatus, HttpStatus.BAD_REQUEST);
			}
			if (productCost < 0) {
				checkOutCounterStatus.setDescription(checkOutCounterUtil.getMessage(locale, "coc.bad_request"));
				checkOutCounterStatus.setStatus(HttpStatus.BAD_REQUEST.toString());
				return new ResponseEntity<CheckOutCounterStatus>(checkOutCounterStatus, HttpStatus.BAD_REQUEST);
			}
			if (productCategory == null || productCategory.isEmpty()) {
				checkOutCounterStatus.setDescription(checkOutCounterUtil.getMessage(locale, "coc.bad_request"));
				checkOutCounterStatus.setStatus(HttpStatus.BAD_REQUEST.toString());
				return new ResponseEntity<CheckOutCounterStatus>(checkOutCounterStatus, HttpStatus.BAD_REQUEST);
			}

			Map<String, Product> itemsToCheckOut = productScan.addBillingItems(items, productName, productCost,
					productCategory, locale);
			String itemsToCheckOutString = jsonParser.getMapper().writeValueAsString(itemsToCheckOut);
			return new ResponseEntity<String>(itemsToCheckOutString, HttpStatus.OK);
		} catch (InvalidProductException ipe) {
			checkOutCounterStatus.setDescription(ipe.getMessage());
			checkOutCounterStatus.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			return new ResponseEntity<CheckOutCounterStatus>(checkOutCounterStatus, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			checkOutCounterStatus.setDescription(e.getMessage());
			checkOutCounterStatus.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			return new ResponseEntity<CheckOutCounterStatus>(checkOutCounterStatus, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = { Constants.GET_BILL_URL, Constants.GET_BILL_URL_LANG }, headers = "Accept=application/json")
	public ResponseEntity<?> generateBill(@PathVariable Optional<String> lang, @PathVariable Optional<String> country) {
		Locale locale = new Locale("en", "IN");
		if (lang.isPresent() && country.isPresent()) {
			locale = new Locale(lang.get(), country.get());
		}
		NumberFormat nf = NumberFormat.getInstance(locale);
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		BillGenerationService billGenerationService = new BillGenerationServiceImpl();
		CheckOutCounterStatus checkOutCounterStatus = new CheckOutCounterStatus();
		if (items != null && items.size() > 0) {
			// billGenerationService.clearItemList(items);
			StringBuilder sb = new StringBuilder();
			sb.append(billGenerationService.printItems(locale, items));
			sb.append("Total Cost :: " + Currency.getInstance(locale).getSymbol() + " "
					+ nf.format(billGenerationService.printTotalCosts(items)));
			items = new HashMap<>();
			return new ResponseEntity<String>(sb.toString(), HttpStatus.OK);
		} else {
			checkOutCounterStatus.setDescription(checkOutCounterUtil.getMessage(locale, "coc.no_items"));
			checkOutCounterStatus.setStatus(HttpStatus.BAD_REQUEST.toString());
			return new ResponseEntity<CheckOutCounterStatus>(checkOutCounterStatus, HttpStatus.BAD_REQUEST);
		}
	}
}
