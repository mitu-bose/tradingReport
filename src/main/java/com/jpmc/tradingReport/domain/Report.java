package com.jpmc.tradingReport.domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Report {
	
	private Map<String, DailySettlement> dailySettlementList;
	private LinkedList<EntityRank> incomingEntityRankList;
	private LinkedList<EntityRank> outgoingEntityRankList;
	
	public Report() {
		this.dailySettlementList = new HashMap<>();
		this.incomingEntityRankList = new LinkedList<>();
		this.outgoingEntityRankList = new LinkedList<>();
	}

	public Report(Map<String, DailySettlement> dailySettlementList, LinkedList<EntityRank> incomingEntityRankList,
			LinkedList<EntityRank> outgoingEntityRankList) {		
		this.dailySettlementList = dailySettlementList;
		this.incomingEntityRankList = incomingEntityRankList;
		this.outgoingEntityRankList = outgoingEntityRankList;
	}

	public Map<String, DailySettlement> getDailySettlementList() {
		return dailySettlementList;
	}

	public void setDailySettlementList(Map<String, DailySettlement> dailySettlementList) {
		this.dailySettlementList = dailySettlementList;
	}

	public LinkedList<EntityRank> getIncomingEntityRankList() {
		return incomingEntityRankList;
	}

	public void setIncomingEntityRankList(LinkedList<EntityRank> incomingEntityRankList) {
		this.incomingEntityRankList = incomingEntityRankList;
	}

	public LinkedList<EntityRank> getOutgoingEntityRankList() {
		return outgoingEntityRankList;
	}

	public void setOutgoingEntityRankList(LinkedList<EntityRank> outgoingEntityRankList) {
		this.outgoingEntityRankList = outgoingEntityRankList;
	}	

}
