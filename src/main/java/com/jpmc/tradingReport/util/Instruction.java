package com.jpmc.tradingReport.util;

import java.util.HashMap;
import java.util.Map;

public enum Instruction {

	Buy("B"),
	Sell("S");
	
	private String value;
	
	Instruction(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
	
	 private static final Map<String, Instruction> lookup = new HashMap<String, Instruction>();
	  
	    //Populate the lookup table on loading time
	    static
	    {
	        for(Instruction ind : Instruction.values())
	        {
	            lookup.put(ind.getValue(), ind);
	        }
	    }
	  
	    //This method can be used for reverse lookup purpose
	    public static Instruction get(String value)
	    {
	        return lookup.get(value);
	    }

	
}
