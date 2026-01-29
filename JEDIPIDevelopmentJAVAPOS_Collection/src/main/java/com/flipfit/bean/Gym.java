// TODO: Auto-generated Javadoc
package com.flipfit.bean;

/**
 * The Class Gym.
 *
 * @author team pi
 * @ClassName "Gym"
 */
public class Gym {

	/** The gym id. */
	private String gymId;

	/** The gym name. */
	private String gymName;

	/** The location. */
	private String location;

	/** The gym owner id. */
	private String gymOwnerId;

	/** The is approved. */
	private boolean isApproved;

	/**
	 * Instantiates a new gym.
	 */
	public Gym() {

	}

	/**
	 * Instantiates a new gym with the specified parameters.
	 *
	 * @param gymId      the unique identifier for the gym
	 * @param gymName    the name of the gym
	 * @param location   the location of the gym
	 * @param gymOwnerId the identifier of the gym owner
	 * @param isApproved whether the gym is approved by the admin
	 */
	public Gym(String gymId, String gymName, String location, String gymOwnerId, boolean isApproved) {
		super();
		this.gymId = gymId;
		this.gymName = gymName;
		this.location = location;
		this.gymOwnerId = gymOwnerId;
		this.isApproved = isApproved;
	}

	/**
	 * Gets the gym id.
	 *
	 * @return the gym id
	 */
	public String getGymId() {
		return gymId;
	}

	/**
	 * Sets the gym id.
	 *
	 * @param gymId the new gym id
	 */
	public void setGymId(String gymId) {
		this.gymId = gymId;
	}

	/**
	 * Gets the gym name.
	 *
	 * @return the gym name
	 */
	public String getGymName() {
		return gymName;
	}

	/**
	 * Sets the gym name.
	 *
	 * @param gymName the new gym name
	 */
	public void setGymName(String gymName) {
		this.gymName = gymName;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Checks if is approved.
	 *
	 * @return true, if is approved
	 */
	public boolean isApproved() {
		return isApproved;
	}

	/**
	 * Sets the approved status.
	 *
	 * @param isApproved the new approved status
	 */
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	/**
	 * Gets the gym owner id.
	 *
	 * @return the gym owner id
	 */
	public String getGymOwnerId() {
		return gymOwnerId;
	}

	/**
	 * Sets the gym owner id.
	 *
	 * @param gymOwnerId the new gym owner id
	 */
	public void setGymOwnerId(String gymOwnerId) {
		this.gymOwnerId = gymOwnerId;
	}

}
