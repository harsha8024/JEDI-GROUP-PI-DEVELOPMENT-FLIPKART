package com.flipkart.business;

//Interface is used for declaration of services
public interface CustomerInterface {
	
	public void createCustomer();
	public boolean deleteCustomer(int id);
	public boolean updateCustomer(int id);
	public void listCustomer();
}
