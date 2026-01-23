package com.flipkart.io;

import java.util.Scanner;

public class Userinputexample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);

        System.out.print("Enter your name: ");
        // 2. Read the name (a full line of text)
        String name = scan.nextLine();

        System.out.print("Enter your age: ");
        // 3. Read the age (an integer)
        int age = scan.nextInt();

        System.out.print("Enter your salary: ");
        // 4. Read the salary (a double)
        double salary = scan.nextDouble();

        // 5. Print the collected data
        System.out.println("\n--- User Details ---");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Salary: Rs. " + salary);

        // 6. Close the scanner to prevent resource leaks
        scan.close();

	}

}
