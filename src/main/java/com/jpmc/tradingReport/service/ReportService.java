package com.jpmc.tradingReport.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.jpmc.tradingReport.domain.DailySettlement;
import com.jpmc.tradingReport.domain.EntityRank;
import com.jpmc.tradingReport.domain.Report;
import com.jpmc.tradingReport.domain.Transaction;
import com.jpmc.tradingReport.util.Instruction;
import com.jpmc.tradingReport.util.MalFormedJsonRequestException;

@Service
public class ReportService {
	
	public static final String []  EXCEPTIONAL_CURRENCIES = {"AED", "SAR"};
	public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
	
	@Autowired
    private ResourceBundleMessageSource resourceBundleMessageSource;
	
	public Report getTransactionReport(List<Transaction> transactionList) {
		
		Report report = processedTransaction(transactionList);
		return report;
	}
	
	public Report processedTransaction(List<Transaction> transactionList) {
		
		Map<String, DailySettlement> dailySettlementMap = new HashMap<>();
		LinkedList<EntityRank> incomingEntityRankList = new LinkedList<>();
		LinkedList <EntityRank> outgoingEntityRankList =  new LinkedList<>();
		Map<String, Double> incomingEntityMap = new HashMap<>();
		Map<String, Double> outgoingEntityMap = new HashMap<>();
		Double totalIncomingAmount = 0.0;
		Double totalOutgoingAmount = 0.0;
		Double settlementAmountPerDay = 0.0;
		
		for(Transaction tr: transactionList) {
			
			String settlementWorkingDate = getSettlementWorkingDate(tr.getCurrency(), tr.getInstructionDate(), tr.getSettlementDate());
			
			DailySettlement dailySettlement = dailySettlementMap.get(settlementWorkingDate); 
			if(dailySettlement == null) {
				dailySettlement = new DailySettlement();
			}
			
			settlementAmountPerDay = tr.getPricePerUnit() * tr.getUnits() * tr.getAgreedFx();
			
			if(Instruction.get(tr.getInstruction()) != null && Instruction.get(tr.getInstruction()) == Instruction.Buy) {
				
				dailySettlement.setOutgoingAmount(dailySettlement.getOutgoingAmount() + settlementAmountPerDay);
				
				totalOutgoingAmount = outgoingEntityMap.get(tr.getEntity());
				if(totalOutgoingAmount == null) {
					totalOutgoingAmount = 0.0;
				}
				outgoingEntityMap.put(tr.getEntity(), totalOutgoingAmount + settlementAmountPerDay );
				
			}
			else if(Instruction.get(tr.getInstruction()) != null && Instruction.get(tr.getInstruction()) == Instruction.Sell) {
				
				dailySettlement.setIncomingAmount(dailySettlement.getIncomingAmount() + settlementAmountPerDay);
				
				totalIncomingAmount = incomingEntityMap.get(tr.getEntity());
				if(totalIncomingAmount == null) {
					totalIncomingAmount = 0.0;
				}
				incomingEntityMap.put(tr.getEntity(), totalIncomingAmount + settlementAmountPerDay );
			}else {
				throw new MalFormedJsonRequestException(resourceBundleMessageSource.getMessage("malFormedJsonRequestException.instructionType", null, Locale.ENGLISH));
			}
			dailySettlementMap.put(settlementWorkingDate, dailySettlement);
		}
		
		outgoingEntityMap.entrySet().stream()
         .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
         .forEachOrdered(x -> {
        	 if(outgoingEntityRankList.isEmpty()){
        		 outgoingEntityRankList.add(new EntityRank(1, x.getKey(), x.getValue()));
        	 }else {
        		 int currentRank = outgoingEntityRankList.getLast().getRankNo() + 1;
        		 outgoingEntityRankList.add(new EntityRank(currentRank, x.getKey(), x.getValue()));
        	 }
         });
		
		incomingEntityMap.entrySet().stream()
        .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
        .forEachOrdered(x -> {
	       	 if(incomingEntityRankList.isEmpty()){
	       		incomingEntityRankList.add(new EntityRank(1, x.getKey(), x.getValue()));
	       	 }else {
	       		 int currentRank = incomingEntityRankList.getLast().getRankNo() + 1;
	       		 incomingEntityRankList.add(new EntityRank(currentRank, x.getKey(), x.getValue()));
	       	 }
        });
		
		return new Report(dailySettlementMap, incomingEntityRankList, outgoingEntityRankList);
	}
	
	
	public String getSettlementWorkingDate(String currency, String instructionDate, String settlementDate) {
		
		String result = "";
		try {
			LocalDate instructionDateObj = LocalDate.parse(instructionDate, dtf);
			LocalDate settlementDateObj = LocalDate.parse(settlementDate, dtf);
			
			if(instructionDateObj.isAfter(settlementDateObj)) {
				throw new MalFormedJsonRequestException(resourceBundleMessageSource.getMessage("malFormedJsonRequestException.instructionDateAfterSettlementDate", null, Locale.ENGLISH)); 
			}
			
			DayOfWeek dayOfWeek = settlementDateObj.getDayOfWeek();
			
		    switch (dayOfWeek) {
		    	case THURSDAY:
		    	  if(Arrays.asList(EXCEPTIONAL_CURRENCIES).contains(currency)) {
		    		  settlementDateObj = settlementDateObj.plusDays(3);
		    	  }
		        break;
		      case FRIDAY:
		    	  if(Arrays.asList(EXCEPTIONAL_CURRENCIES).contains(currency)) {
		    		  settlementDateObj = settlementDateObj.plusDays(2);
		    	  }else {
		    		  settlementDateObj = settlementDateObj.plusDays(3);
		    	  }
		        break;
		      case SATURDAY:
		    	  if(Arrays.asList(EXCEPTIONAL_CURRENCIES).contains(currency)) {
		    		  settlementDateObj = settlementDateObj.plusDays(1);
		    	  }else {
		    		  settlementDateObj = settlementDateObj.plusDays(2);
		    	  }
		        break;
		      case SUNDAY:
		    	  if(!Arrays.asList(EXCEPTIONAL_CURRENCIES).contains(currency)) {
		    		  settlementDateObj = settlementDateObj.plusDays(1);
		    	  }
		        break;
		      default:
		        break;
		    }
		    result = settlementDateObj.format(dtf);
		}catch(DateTimeParseException e) {
			throw new MalFormedJsonRequestException(resourceBundleMessageSource.getMessage("malFormedJsonRequestException.dateFormat", null, Locale.ENGLISH));
		}	    
	    return result;
	}

}
