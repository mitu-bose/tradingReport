package com.jpmc.tradingReport.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

public class WrapperTransaction {

	@Valid
	private final List<Transaction> transactions;

	public WrapperTransaction() {
		transactions = new ArrayList<>();
	}
	
	public WrapperTransaction(final Transaction... transactions) {
		this.transactions = Arrays.asList(transactions);
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}
}
