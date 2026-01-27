// TODO: Auto-generated Javadoc
package com.flipfit.bean;

/**
 * The Class GymAdmin.
 *
 * @author team pi
 * @ClassName "GymAdmin"
 */
public class GymAdmin extends User {

	/**
	 * Instantiates a new gym admin.
	 */
	public GymAdmin() {
		super();
		this.setActive(true);
	}

	/**
	 * Instantiates a new gym admin with the specified details.
	 *
	 * @param userID      the user ID
	 * @param name        the name
	 * @param email       the email
	 * @param phoneNumber the phone number
	 * @param city        the city
	 * @param password    the password
	 * @param role        the role
	 * @param isActive    the active status
	 */
	public GymAdmin(String userID, String name, String email, String phoneNumber, String city, String password,
			Role role, boolean isActive) {
		super(userID, name, email, phoneNumber, city, password, role, isActive);
		this.setActive(true);
	}

}
