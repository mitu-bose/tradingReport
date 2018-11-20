package com.jpmc.tradingReport;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.RequestDispatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getTransactionReport() throws Exception {
		
		this.mockMvc.perform(post("/transactionReport").contentType(MediaType.APPLICATION_JSON_UTF8)
	               .content("{ \"transactions\": [{ \"entity\": \"foo\", "
	               		+ "\"instruction\": \"B\", "
	               		+ "\"agreedFx\": 0.50 , "
	               		+ "\"currency\": \"SGP\", "
	               		+ "\"instructionDate\": \"01 Jan 2016\", "
	               		+ "\"settlementDate\": \"03 Jan 2016\", "
	               		+ "\"units\": 200, "
	               		+ "\"pricePerUnit\": 100.25 "
	               		+ " }, { \"entity\": \"bar\", "
	               		+ "\"instruction\": \"B\", "
	               		+ "\"agreedFx\": 0.50 , "
	               		+ "\"currency\": \"SGP\", "
	               		+ "\"instructionDate\": \"01 Jan 2016\", "
	               		+ "\"settlementDate\": \"03 Jan 2016\", "
	               		+ "\"units\": 1000, "
	               		+ "\"pricePerUnit\": 200.25 } "
	               		+ "]}").characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			        .andExpect(jsonPath("$.dailySettlementList", Matchers.hasKey("04 Jan 2016")))
			        .andExpect(jsonPath("$.dailySettlementList.['04 Jan 2016'].incomingAmount", is(0.0)))
			        .andExpect(jsonPath("$.dailySettlementList.['04 Jan 2016'].outgoingAmount", is(110150.0)))
			        .andExpect(jsonPath("$.outgoingEntityRankList.[0].rankNo", is(1)))
			        .andExpect(jsonPath("$.outgoingEntityRankList.[0].entity", is("bar")))
			        .andExpect(jsonPath("$.outgoingEntityRankList.[0].totalAmount", is(100125.0)))
					.andExpect(jsonPath("$.outgoingEntityRankList.[1].rankNo", is(2)))
			        .andExpect(jsonPath("$.outgoingEntityRankList.[1].entity", is("foo")))
			        .andExpect(jsonPath("$.outgoingEntityRankList.[1].totalAmount", is(10025.0)));
	}
	
	
	@Test
	public void getReport_NotFound_Returns404() throws Exception {
		
		this.mockMvc.perform(post("/transactionReport").contentType(MediaType.APPLICATION_JSON)
	               .content("[]")
	               .accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void test_instructionDateAfterSettlementDate() throws Exception {
		
		this.mockMvc.perform(post("/transactionReport").contentType(MediaType.APPLICATION_JSON_UTF8)
	               .content("{ \"transactions\": [{ \"entity\": \"foo\", "
	               		+ "\"instruction\": \"B\", "
	               		+ "\"agreedFx\": 0.50 , "
	               		+ "\"currency\": \"SGP\", "
	               		+ "\"instructionDate\": \"04 Jan 2016\", "
	               		+ "\"settlementDate\": \"03 Jan 2016\", "
	               		+ "\"units\": 200, "
	               		+ "\"pricePerUnit\": 100.25 "
	               		+ " }]}").accept(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isBadRequest());
	}
	
	@Test
	public void test_instructionDateFormat() throws Exception {
		
		this.mockMvc.perform(post("/transactionReport").contentType(MediaType.APPLICATION_JSON_UTF8)
	               .content("{ \"transactions\": [{ \"entity\": \"foo\", "
	               		+ "\"instruction\": \"B\", "
	               		+ "\"agreedFx\": 0.50 , "
	               		+ "\"currency\": \"SGP\", "
	               		+ "\"instructionDate\": \"01 Jan 2016\", "
	               		+ "\"settlementDate\": \"03/01/2016\", "
	               		+ "\"units\": 200, "
	               		+ "\"pricePerUnit\": 100.25 "
	               		+ " }]}").accept(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isBadRequest());
	}
	
	@Test
	public void test_instructionTypeNotCorrect() throws Exception {
		
		this.mockMvc.perform(post("/transactionReport").contentType(MediaType.APPLICATION_JSON_UTF8)
	               .content("{ \"transactions\": [{ \"entity\": \"foo\", "
	               		+ "\"instruction\": \"C\", "
	               		+ "\"agreedFx\": 0.50 , "
	               		+ "\"currency\": \"SGP\", "
	               		+ "\"instructionDate\": \"01 Jan 2016\", "
	               		+ "\"settlementDate\": \"03 Jan 2016\", "
	               		+ "\"units\": 200, "
	               		+ "\"pricePerUnit\": 100.25 "
	               		+ " }]}").accept(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isBadRequest());
	}
	
	
	@Test
	public void errorExampleForInstrumentDateAfterSettlementDate() throws Exception {
		this.mockMvc.perform(get("/error")
		        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
		        .requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/transactionReport")
		        .requestAttr(RequestDispatcher.ERROR_MESSAGE,
		                "Instruction Date should not be after Settlement Date"))
		.andDo(print()).andExpect(status().isBadRequest())
		 .andExpect(jsonPath("error", is("Bad Request")));
	}
	
	@Test
	public void errorExampleForDateFormat() throws Exception {
		this.mockMvc.perform(get("/error")
		        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
		        .requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/transactionReport")
		        .requestAttr(RequestDispatcher.ERROR_MESSAGE,
		                "Date Format is not correct. It should be dd MMM yyyy. For Example: 03 Jan 2016"))
			.andDo(print()).andExpect(status().isBadRequest())
			.andExpect(jsonPath("error", is("Bad Request")));
	}
	
}
