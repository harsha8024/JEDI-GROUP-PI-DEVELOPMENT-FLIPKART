/**
 * 
 */
package com.flipkart.business;

import com.flipkart.exception.InsufficientFundException;

/**
 * 
 */
public class BankService {
	// declare the properties here 
	
		private double balance;
		private int number;
		
		public BankService() {
			
			this.number=number;
		}
		
		// service methods for deposit & withdrawal 
		
		
		public void deposit(double amount) {
			
			balance +=amount;
		}
		
		
		public void withdraw(double amount) throws InsufficientFundException {
			
			if(amount<=balance) {
				
				    balance -= amount;	
			}else {
				
				double needs=amount-balance;
				
				throw new InsufficientFundException(needs);
			}
		}

}
