/**
 * 
 */
package com.flipkart.exception;

/**
 * 
 */
public class InsufficientFundException extends Exception {
	
	// declare the domain variable 
	
    private double amount;
    
    public InsufficientFundException(double amount) {
		// TODO Auto-generated constructor stub
    	this.amount=amount;
    		    	
	}
		
    // custom method which is return the less amount how much less for withdrawal	

     public double getAmount() {
    	 
    	 return amount;
     }

}
