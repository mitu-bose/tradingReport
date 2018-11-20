package com.jpmc.tradingReport.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class Transaction{
	
	@NotEmpty(message = "Entity Cannot be Null or Empty.")
	private String entity;
	
	@NotEmpty(message = "Instruction Cannot be Null or Empty.")
	private String instruction;
	
	@NotNull(message = "Agreed FX Cannot be Null or Empty.")
	@Positive(message = "Agreed FX should be more than zero")
	private Double agreedFx;
	
	@NotEmpty(message = "Currency Cannot be Null or Empty.")
	private String currency;
	
	@NotEmpty(message = "Instruction Date Cannot be Null or Empty.")
	private String instructionDate;
	
	@NotEmpty(message = "Settlement Date Cannot be Null or Empty.")
	private String settlementDate;	
	
	@NotNull(message = "Units Cannot be Null or Empty.")
	@Positive(message = "Units should be more than zero")
	private Long units;
	
	@NotNull(message = "PricePerUnit Cannot be Null or Empty.")
	@Positive(message = "PricePerUnit should be more than zero")
	private Double pricePerUnit;
	
	
	public Transaction() {
		this.entity = "";
		this.instruction = "";
		this.agreedFx = 0.0;
		this.currency = "";
		this.instructionDate = null;
		this.settlementDate = null;
		this.units = new Long(0);
		this.pricePerUnit = 0.0;
	}
	
			
	public Transaction(String entity, String instruction, Double agreedFx, String currency, String instructionDate,
			String settlementDate, Long units, Double pricePerUnit) {		
		this.entity = entity;
		this.instruction = instruction;
		this.agreedFx = agreedFx;
		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.units = units;
		this.pricePerUnit = pricePerUnit;
	}


	public String getEntity() {
		return entity;
	}


	public void setEntity(String entity) {
		this.entity = entity;
	}


	public String getInstruction() {
		return instruction;
	}


	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}


	public Double getAgreedFx() {
		return agreedFx;
	}


	public void setAgreedFx(Double agreedFx) {
		this.agreedFx = agreedFx;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getInstructionDate() {
		return instructionDate;
	}


	public void setInstructionDate(String instructionDate) {
		this.instructionDate = instructionDate;
	}


	public String getSettlementDate() {
		return settlementDate;
	}


	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}


	public Long getUnits() {
		return units;
	}


	public void setUnits(Long units) {
		this.units = units;
	}


	public Double getPricePerUnit() {
		return pricePerUnit;
	}


	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

}
