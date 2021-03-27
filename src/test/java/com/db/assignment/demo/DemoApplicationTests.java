package com.db.assignment.demo;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}
	@LocalServerPort
	int port;

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testTradeStore_InvalidMaturityDate() throws Exception {

		String urlStr = String.format("http://localhost:%s/ws/trade", port);
		String tradeData1="{\n"
				+ "	\"tradeId\": \"T2\",\n"
				+ "	\"version\": \"1\",\n"
				+ "	\"partyId\": \"CP-2\",\n"
				+ "	\"bookId\": \"B1\",\n"
				+ "	\"maturityDt\": \"24/03/2021\",\n"
				+ "	\"createdDt\": \"27/03/2021\"\n"
				+ "}";
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlStr)
			.accept(MediaType.APPLICATION_JSON)
			.content(tradeData1)
			.contentType(MediaType.APPLICATION_JSON))
			.andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
	}
	
	@Test
	public void testTradeStore_OK() throws Exception {

		String urlStr = String.format("http://localhost:%s/ws/trade", port);
		String tradeData1="{\n"
				+ "	\"tradeId\": \"T2\",\n"
				+ "	\"version\": \"1\",\n"
				+ "	\"partyId\": \"CP-2\",\n"
				+ "	\"bookId\": \"B1\",\n"
				+ "	\"maturityDt\": \"24/05/2022\",\n"
				+ "	\"createdDt\": \"21/03/2021\"\n"
				+ "}";
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlStr)
			.accept(MediaType.APPLICATION_JSON)
			.content(tradeData1)
			.contentType(MediaType.APPLICATION_JSON))
			.andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void testTradeStore_InvalidVersion() throws Exception {

		String urlStr = String.format("http://localhost:%s/ws/trade", port);
		String tradeData1="{\n"
				+ "	\"tradeId\": \"T2\",\n"
				+ "	\"version\": \"2\",\n"
				+ "	\"partyId\": \"CP-2\",\n"
				+ "	\"bookId\": \"B1\",\n"
				+ "	\"maturityDt\": \"24/03/2021\",\n"
				+ "	\"createdDt\": \"21/03/2021\"\n"
				+ "}";
		String tradeData2="{\n"
				+ "	\"tradeId\": \"T2\",\n"
				+ "	\"version\": \"1\",\n"
				+ "	\"partyId\": \"CP-2\",\n"
				+ "	\"bookId\": \"B1\",\n"
				+ "	\"maturityDt\": \"24/03/2021\",\n"
				+ "	\"createdDt\": \"21/03/2021\"\n"
				+ "}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(urlStr)
			.accept(MediaType.APPLICATION_JSON)
			.content(tradeData1)
			.contentType(MediaType.APPLICATION_JSON))
			.andReturn();
		MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.post(urlStr)
				.accept(MediaType.APPLICATION_JSON)
				.content(tradeData2)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		MockHttpServletResponse response = result1.getResponse();
		assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
	}
}
