package com.flipkart.business;

public class CustomerImpl implements CustomerInterface {

	@Override
	public void createCustomer() {
		// TODO Auto-generated method stub
		System.out.println("The create customer method.");
	}

	@Override
	public boolean deleteCustomer(int id) {
		// TODO Auto-generated method stub
		System.out.println("The delete by ID Methods-->"+id);
		return true;
	}

	@Override
	public boolean updateCustomer(int id) {
		// TODO Auto-generated method stub
		System.out.println("Update by ID--->"+id);
		return true;
	}

	@Override
	public void listCustomer() {
		// TODO Auto-generated method stub
		System.out.println("List of customers");
		
	}

}
