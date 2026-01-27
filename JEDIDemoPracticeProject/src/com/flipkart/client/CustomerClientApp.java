/**
 * 
 */
package com.flipkart.client;

import com.flipkart.dao.CustomerDAO;

/**
 * 
 */
public class CustomerClientApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	        CustomerDAO manager = new CustomerDAO();

	        // Perform CRUD operations
	        manager.insertCustomer(103,"Alice Smith", "alice@example.com", "1234567890");
	        manager.insertCustomer(102,"Bob Jones", "bob@example.com", "0987654321");

	        manager.selectAllCustomers();

	        manager.updateCustomerContact(102, "1112223333");

	        manager.selectAllCustomers();

	        manager.deleteCustomer(102);

	        manager.selectAllCustomers();
	}

	

}
