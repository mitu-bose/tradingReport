package com.jpmc.tradingReport;


import static org.assertj.core.api.Assertions.assertThat;
import java.net.URI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.jpmc.tradingReport.domain.Report;
import com.jpmc.tradingReport.domain.Transaction;
import com.jpmc.tradingReport.domain.WrapperTransaction;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTests{

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Test
	public void getTransactionReport_Return200() throws Exception {
		
		final String baseUrl = "/transactionReport";
	    URI uri = new URI(baseUrl);
	        
	    Transaction transaction = new Transaction("foo", "B", 0.50, "SGP", "01 Jan 2016", "03 Jan 2016", new Long(200), 100.25);
	    Transaction transactions[]=new Transaction[1];
	    transactions[0] = transaction;
	    WrapperTransaction wrapperTransaction = new WrapperTransaction(transactions);
	    
	    ResponseEntity<Report> responseEntity = this.testRestTemplate.postForEntity(uri, wrapperTransaction, Report.class);
	    Report report = responseEntity.getBody();
	    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
	    assertThat(report).isNotNull();
	    assertThat(report.getDailySettlementList()).isNotNull().isNotEmpty();
	    assertThat(report.getDailySettlementList().get("04 Jan 2016").getOutgoingAmount()).isEqualTo(10025);
	    assertThat(report.getOutgoingEntityRankList().get(0).getRankNo()).isEqualTo(1);
	    assertThat(report.getOutgoingEntityRankList().get(0).getEntity()).isEqualTo("foo");
	    assertThat(report.getOutgoingEntityRankList().get(0).getTotalAmount()).isEqualTo(10025);
	}
	
	@Test
	public void getReport_NotFound_Returns404() throws Exception {
		
		final String baseUrl = "/transactionReport";
	    URI uri = new URI(baseUrl);
	        
	    WrapperTransaction wrapperTransaction = new WrapperTransaction();
				
		ResponseEntity<Report> responseEntity = this.testRestTemplate.postForEntity(uri, wrapperTransaction, Report.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

}
