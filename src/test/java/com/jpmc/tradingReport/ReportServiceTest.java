package com.jpmc.tradingReport;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.jpmc.tradingReport.domain.Report;
import com.jpmc.tradingReport.domain.Transaction;
import com.jpmc.tradingReport.service.ReportService;


@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {
	
	@Mock
	private ReportService reportService;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before	
	public void setUp() throws Exception {
		reportService = new ReportService();
	}
	
	@Test
	public void getIncomingOutgoingAmountPerDay_returnsDailySettlementInfo() {
		
		List<Transaction> trList = new ArrayList<>();
		Transaction tr = new Transaction("foo", "B", 0.50, "SGP", "01 Jan 2016", "03 Jan 2016", new Long(200), 100.0);
		trList.add(tr);
		tr = new Transaction("foo", "S", 0.30, "SGP", "01 Jan 2016", "03 Jan 2016", new Long(100), 100.0);
		trList.add(tr);
		
		Report report = reportService.processedTransaction(trList);
		
		assertThat(report.getDailySettlementList()).isNotNull().isNotEmpty();
		assertThat(report.getDailySettlementList().get("04 Jan 2016").getOutgoingAmount()).isEqualTo(10000);
		assertThat(report.getDailySettlementList().get("04 Jan 2016").getIncomingAmount()).isEqualTo(3000);

	}
	
	@Test
	public void getEntityRank_returnsRankInfo() {
		
		List<Transaction> trList = new ArrayList<>();
		Transaction tr = new Transaction("foo", "B", 0.50, "SGP", "01 Jan 2016", "03 Jan 2016", new Long(200), 100.0);
		trList.add(tr);
		tr = new Transaction("Bar", "B", 0.30, "SGP", "01 Jan 2016", "03 Jan 2016", new Long(100), 100.0);
		trList.add(tr);
		
		Report report = reportService.processedTransaction(trList);
		
		assertThat(report.getIncomingEntityRankList()).isEmpty();
		assertThat(report.getOutgoingEntityRankList()).isNotNull().isNotEmpty();
		
		assertThat(report.getOutgoingEntityRankList().get(0).getRankNo()).isEqualTo(1);
		assertThat(report.getOutgoingEntityRankList().get(0).getEntity()).isEqualTo("foo");
		assertThat(report.getOutgoingEntityRankList().get(0).getTotalAmount()).isEqualTo(10000);
		
		assertThat(report.getOutgoingEntityRankList().get(1).getRankNo()).isEqualTo(2);
		assertThat(report.getOutgoingEntityRankList().get(1).getEntity()).isEqualTo("Bar");
		assertThat(report.getOutgoingEntityRankList().get(1).getTotalAmount()).isEqualTo(3000);
	}
	
	@Test
	public void testSettlementWorkingDay_ForWeekend() {
		
		String settlementWorkinDay = reportService.getSettlementWorkingDate("SGP", "10 Nov 2018", "17 Nov 2018");
		
		assertThat(settlementWorkinDay).isEqualTo("19 Nov 2018");
	}
	
	@Test
	public void testSettlementWorkingDay_ForExceptionalCurrency() {
		
		String settlementWorkinDay = reportService.getSettlementWorkingDate("AED", "10 Nov 2018", "17 Nov 2018");
		
		assertThat(settlementWorkinDay).isEqualTo("18 Nov 2018");
	}
	
	@Test
	public void testSettlementWorkingDayThursday_ForExceptionalCurrency() {
		
		String settlementWorkinDay = reportService.getSettlementWorkingDate("AED", "10 Nov 2018", "22 Nov 2018");
		
		assertThat(settlementWorkinDay).isEqualTo("25 Nov 2018");
	}
	
	@Test
	public void testSettlementWorkingDayThursday_ExceptExceptionalCurrency() {
		
		String settlementWorkinDay = reportService.getSettlementWorkingDate("INR", "10 Nov 2018", "22 Nov 2018");
		
		assertThat(settlementWorkinDay).isEqualTo("22 Nov 2018");
	}


}
