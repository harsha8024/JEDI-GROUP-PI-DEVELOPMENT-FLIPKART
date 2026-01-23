package com.flipkart.business;

import java.util.ArrayList;
import java.util.List;

import com.flipkart.bean.Customer;

public class CustomerImpl implements CustomerInterface {

	List<Customer> salesForceCustomer = new ArrayList<Customer>();

	@Override
	public void createCustomer() {
		// TODO Auto-generated method stub
		// Add with Collection

		// record 1
		Customer customer1 = new Customer();

		customer1.setCustomerID(101);
		customer1.setCustomerName("Bhavesh");
		customer1.setCustomerAddress("Delhi");
		salesForceCustomer.add(customer1);

		// Record 2
		Customer customer2 = new Customer();
		customer2.setCustomerID(102);
		customer2.setCustomerName("amy");
		customer2.setCustomerAddress("Blore");
		salesForceCustomer.add(customer2);
		// Record 3

		Customer customer3 = new Customer();
		customer3.setCustomerID(101);
		customer3.setCustomerName("Bhavesh");
		customer3.setCustomerAddress("Delhi");
		salesForceCustomer.add(customer3);
//		System.out.println("The create customer method.");
	}

	@Override
	public boolean deleteCustomer(int id) {
		// TODO Auto-generated method stub
		System.out.println("The delete by ID Methods-->" + id);
		return true;
	}

	@Override
	public boolean updateCustomer(int id) {
		// TODO Auto-generated method stub
		System.out.println("Update by ID--->" + id);
		return true;
	}

	@Override
	public void listCustomer() {
		// TODO Auto-generated method stub
		System.out.println("List of customers");
		for (Customer customer : salesForceCustomer) {

			System.out.println("Customer Details-->" + customer.getCustomerID() + "--" + customer.getCustomerName()
					+ "--" + customer.getCustomerAddress());
		}

	}

}
