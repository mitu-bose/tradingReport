package com.jpmc.tradingReport;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import com.jpmc.tradingReport.domain.Transaction;

public class TransactionValidationTest {

	private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    public void testTransactionSuccess() {
    	Transaction transaction = new Transaction("foo", "B", 0.50, "SGP", "01 Jan 2016", "03 Jan 2016", new Long(200), 100.25);
        
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        assertThat(violations).isEmpty();
    }
    
    @Test
    public void test_Entity_Empty() {
    	String message = "";
    	Transaction transaction = new Transaction("", "B", 0.50, "SGP", "01 Jan 2016", "03 Jan 2016", new Long(200), 100.25);        
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        for(ConstraintViolation<Transaction> ct: violations) {
        	message = ct.getMessageTemplate();
        }
        assertThat(violations).isNotEmpty();
        assertThat(message).isEqualTo("Entity Cannot be Null or Empty.");
    }
    
    @Test
    public void test_Instruction_Empty() {
    	String message = "";
    	Transaction transaction = new Transaction("foo", "", 0.50, "SGP", "01 Jan 2016", "03 Jan 2016", new Long(200), 100.25);        
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        for(ConstraintViolation<Transaction> ct: violations) {
        	message = ct.getMessageTemplate();
        }
        assertThat(violations).isNotEmpty();
        assertThat(message).isEqualTo("Instruction Cannot be Null or Empty.");
    }
    
    @Test
    public void test_AgreedFx_Positive() {
    	String message = "";
    	Transaction transaction = new Transaction("foo", "B", -1.0, "SGP", "01 Jan 2016", "03 Jan 2016", new Long(200), 100.25);        
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        for(ConstraintViolation<Transaction> ct: violations) {
        	message = ct.getMessageTemplate();
        }
        assertThat(violations).isNotEmpty();
        assertThat(message).isEqualTo("Agreed FX should be more than zero");
    }
    
    @Test
    public void test_Currency_Empty() {
    	String message = "";
    	Transaction transaction = new Transaction("foo", "B", 0.50, "", "01 Jan 2016", "03 Jan 2016", new Long(200), 100.25);        
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        for(ConstraintViolation<Transaction> ct: violations) {
        	message = ct.getMessageTemplate();
        }
        assertThat(violations).isNotEmpty();
        assertThat(message).isEqualTo("Currency Cannot be Null or Empty.");
    }
    
    @Test
    public void test_InstructionDate_Empty() {
    	String message = "";
    	Transaction transaction = new Transaction("foo", "B", 0.50, "SGP", "", "03 Jan 2016", new Long(200), 100.25);        
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        for(ConstraintViolation<Transaction> ct: violations) {
        	message = ct.getMessageTemplate();
        }
        assertThat(violations).isNotEmpty();
        assertThat(message).isEqualTo("Instruction Date Cannot be Null or Empty.");
    }
    
    @Test
    public void test_SettlementDate_Empty() {
    	String message = "";
    	Transaction transaction = new Transaction("foo", "B", 0.50, "SGP", "01 Jan 2016", "", new Long(200), 100.25);        
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        for(ConstraintViolation<Transaction> ct: violations) {
        	message = ct.getMessageTemplate();
        }
        assertThat(violations).isNotEmpty();
        assertThat(message).isEqualTo("Settlement Date Cannot be Null or Empty.");
    }
    
    @Test
    public void test_Units_Empty() {
    	String message = "";
    	Transaction transaction = new Transaction("foo", "B", 0.50, "SGP", "01 Jan 2016", "03 Jan 2016", null, 100.25);        
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        for(ConstraintViolation<Transaction> ct: violations) {
        	message = ct.getMessageTemplate();
        }
        assertThat(violations).isNotEmpty();
        assertThat(message).isEqualTo("Units Cannot be Null or Empty.");
    }
    
    @Test
    public void test_PricePerUnit_Positive() {
    	String message = "";
    	Transaction transaction = new Transaction("foo", "B", 0.50, "SGP", "01 Jan 2016", "03 Jan 2016", new Long(200), -100.25);        
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
        for(ConstraintViolation<Transaction> ct: violations) {
        	message = ct.getMessageTemplate();
        }
        assertThat(violations).isNotEmpty();
        assertThat(message).isEqualTo("PricePerUnit should be more than zero");
    }
    
}
