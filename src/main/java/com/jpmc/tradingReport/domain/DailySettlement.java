package com.jpmc.tradingReport.domain;

public class DailySettlement {

	private Double incomingAmount;
	private Double outgoingAmount;
	
	public DailySettlement() {
		incomingAmount = 0.0;
		outgoingAmount = 0.0;
	}
		
	public DailySettlement(Double incomingAmount, Double outgoingAmount) {
		super();
		this.incomingAmount = incomingAmount;
		this.outgoingAmount = outgoingAmount;
	}

	public Double getIncomingAmount() {
		return incomingAmount;
	}
	public void setIncomingAmount(Double incomingAmount) {
		this.incomingAmount = incomingAmount;
	}
	public Double getOutgoingAmount() {
		return outgoingAmount;
	}
	public void setOutgoingAmount(Double outgoingAmount) {
		this.outgoingAmount = outgoingAmount;
	}
	
	
}
