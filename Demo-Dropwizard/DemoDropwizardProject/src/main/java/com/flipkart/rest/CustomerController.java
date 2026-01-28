/**
 * 
 */
package com.flipkart.rest;

import com.flipkart.bean.Customer;

/**
 * 
 */
@Path("/customer");
public class CustomerController {
	@GET
	@Path("/details")
	@Produces("application/json")
	public Customer customerDetails(){
		Customer c1=new Customer();
		c1.setCustomerID(101);
		c1.setCustomerName("Harsha");
		c1.setCustomerAddress("Bengaluru");
		return c1;
	}
}
//Reference link to create, post, put, delete: https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example/#1-introduction-to-dropwizard

