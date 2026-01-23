/**
 * 
 */
package com.flipkart.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class ArrayListdemo1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Declare an array collection and perform CRUD
		List<String> list=new ArrayList<String>();
		//Collections are always used with generics (Java 8 onwards) and it makes the collection type safe
		
		// Add the Object    :-- creation method
		System.out.println(list.size());
		list.add("Bhavesh");
		list.add("adbul");
		list.add("amy");
		list.add("yabbado");
		list.add("hello");
		System.out.println(list.size());
		//Delete records
		list.remove("Bhavesh");
		System.out.println(list.size());
		//Iterate through the collection
		for(String value: list) {
					
					System.out.println("All data-->" +value);
		}
		//There is no implicit method for updation in collection, have to do it the way we do with arrays
		


	}

}
