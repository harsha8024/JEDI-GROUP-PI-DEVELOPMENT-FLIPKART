/**
 * 
 */
package com.flipkart.client;

import com.flipkart.business.CustomerImpl;
import com.flipkart.business.CustomerInterface;

/**
 * 
 */
public class CustomerClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Common client class is FlipFit application
		//Create the instance of class here
		
		CustomerInterface customer=new CustomerImpl();
		customer.createCustomer();
		customer.listCustomer();
//		System.out.println("Update customer-->"+customer.updateCustomer(101));
//		System.out.println("Delete customer-->"+customer.deleteCustomer(101));

	}

}
