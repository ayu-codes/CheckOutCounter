package com.ayu.CheckOutCounter.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ayu.CheckOutCounter.constants.Constants;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CheckOutCounterControllerTest {
	private HttpHeaders httpHeaders;
	@Autowired
	MockMvc mockMvc;

	@Before
	public void setup() {
		createHeaders();
	}

	private void createHeaders() {
		httpHeaders = new HttpHeaders();
		httpHeaders.add("Accept", "application/json");
	}

	@Test
	public void validateIfProductNamePassedIsNull() {
		String jsonReq = "{" + "\"productCost\":200.23," + "\"productCategory\":\"B\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(Constants.BILLING_URL).headers(httpHeaders)
				.content(jsonReq);
		MvcResult mvcResult;
		try {
			mvcResult = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = mvcResult.getResponse();
			assertEquals(400, response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void validateIfProductNamePassedIsEmpty() {
		String jsonReq = "{\"productName\":\"\",\r\n" + "\"productCost\":200.23,\r\n" + "\"productCategory\":\"B\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(Constants.BILLING_URL).headers(httpHeaders)
				.content(jsonReq);
		try {
			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = mvcResult.getResponse();
			assertEquals(400, response.getStatus());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void validateIfProductCategoryPassedIsNull() {
		String jsonReq = "{\"productName\":\"Banana\"," + "\"productCost\":200.23 }";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(Constants.BILLING_URL).headers(httpHeaders)
				.content(jsonReq);
		try {
			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = mvcResult.getResponse();
			assertEquals(400, response.getStatus());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void validateIfProductCategoryPassedIsEmpty() {
		String jsonReq = "{\"productName\":\"\"," + "\"productCost\":200.23,\r\n" + "\"productCategory\":\"\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(Constants.BILLING_URL).headers(httpHeaders)
				.content(jsonReq);
		try {
			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = mvcResult.getResponse();
			assertEquals(400, response.getStatus());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void validateIfProductCostPassedIsNegative() {
		String jsonReq = "{\"productName\":\"Banana\"," + "\"productCost\": -200.23,"
				+ "\"productCategory\":\"B\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(Constants.BILLING_URL).headers(httpHeaders)
				.content(jsonReq);
		try {
			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = mvcResult.getResponse();
			assertEquals(400, response.getStatus());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void whenCategoryPassedForProductDoesNotExist() {
		String jsonReq = "{\"productName\":\"Banana\"," + "\"productCost\": 200.23,"
				+ "\"productCategory\":\"D\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(Constants.BILLING_URL).headers(httpHeaders)
				.content(jsonReq);
		try {
			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = mvcResult.getResponse();
			assertEquals(500, response.getStatus());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void whenProductDetailsPassedAreValid() {
		String jsonReq = "{\"productName\":\"Banana\",\r\n" + "\"productCost\": 200.23,\r\n"
				+ "\"productCategory\":\"B\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(Constants.BILLING_URL).headers(httpHeaders)
				.content(jsonReq);
		try {
			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = mvcResult.getResponse();
			assertEquals(200, response.getStatus());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void whenItemsNotPresentForBilling() {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(Constants.GET_BILL_URL).headers(httpHeaders);
		try {
			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = mvcResult.getResponse();
			assertEquals(400, response.getStatus());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void whenItemsPresentForBilling() {
		try {
			RequestBuilder requestBuilder = MockMvcRequestBuilders.get(Constants.GET_BILL_URL).headers(httpHeaders);
			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = mvcResult.getResponse();
			assertEquals(200, response.getStatus());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
