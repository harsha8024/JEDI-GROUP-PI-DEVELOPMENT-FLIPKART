/**
 * 
 */
package com.flipkart.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
/**
 * 
 */
@Path("/Hello")
public class HelloController {
	//GET implementation
	@GET
	@Path("/Fetch")
	public String helloWorld() {
		return "This is my first service.";
	}
	

}
