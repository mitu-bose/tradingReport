package com.jpmc.tradingReport.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jpmc.tradingReport.domain.Report;
import com.jpmc.tradingReport.domain.WrapperTransaction;
import com.jpmc.tradingReport.service.ReportService;
import com.jpmc.tradingReport.util.MalFormedJsonRequestException;
import com.jpmc.tradingReport.util.ReportNotFoundException;


@RestController
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
    private ResourceBundleMessageSource resourceBundleMessageSource;

	@PostMapping(path= "/transactionReport")
    public Report getTransactionReport(@Valid @RequestBody WrapperTransaction wrapperTransaction) throws Exception
    {      
		if(wrapperTransaction == null || wrapperTransaction.getTransactions().isEmpty()) {
			throw new MalFormedJsonRequestException(resourceBundleMessageSource.getMessage("malFormedJsonRequestException.emptyRequest", null, Locale.ENGLISH));
		}
		
		Report report = this.reportService.getTransactionReport( wrapperTransaction.getTransactions());
		if (report == null) {
			throw new ReportNotFoundException(resourceBundleMessageSource.getMessage("reportNotFoundException.reportEmty", null, Locale.ENGLISH));
		}
		return report;
    }

}
