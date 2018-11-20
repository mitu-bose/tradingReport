package com.jpmc.tradingReport.domain;

public class EntityRank{
	
	private int rankNo;
	private String entity;
	private double totalAmount;
	
	public EntityRank() {
		this.rankNo = 0;
		this.entity = "";
		this.totalAmount = 0.0;
	}
	
	public EntityRank(int rankNo, String entity, double totalAmount) {
		this.rankNo = rankNo;
		this.entity = entity;
		this.totalAmount = totalAmount;
	}

	public int getRankNo() {
		return rankNo;
	}

	public void setRankNo(int rankNo) {
		this.rankNo = rankNo;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}	

}
